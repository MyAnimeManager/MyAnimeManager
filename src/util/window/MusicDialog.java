package util.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import main.AnimeIndex;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import util.FileManager;
import util.JMarqueeLabel;
import util.JTreeIcons;
import util.MAMUtil;
import util.task.DriveFileFetcherTask;
import util.task.GoogleDriveDownloadTask;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class MusicDialog extends JDialog
{

	public TreeMap<String, ArrayList<String>> songsMap = new TreeMap<String, ArrayList<String>>(String.CASE_INSENSITIVE_ORDER);
	private final JPanel contentPanel = new JPanel();
	private Player player;
	private boolean loopActive;
	private JLabel lblImage = new JLabel();
	private JMarqueeLabel lblTitle;
	private JButton btnPlaypause;
	private JButton btnRestart;
	private FileInputStream fis;
	private BufferedInputStream buff;
	private boolean isRunning;
	private boolean isPaused;
	private String currentMusicPath;
	private long pauseLocation;
	private long songTotalLength;
	private Timer timer;
	private JProgressBar progressBar;
	private String title;
	private JButton btnLoad;
	private JButton btnSave;
	private JButton btnPrev;
	private JButton btnSucc;
	private JPanel treePanel;
	private JButton btnElimina;
	private JPanel treeButtonPanel;
	private JTree songsTree;
	private long duration;
	private String time = "";
	private int counter = 0;
	private DefaultTreeModel songsTreeModel;
	private ArrayList<String> songList = new ArrayList<String>();
	private GoogleDriveDownloadTask downloadDriveTask;
	private int defImgCounter = Integer.parseInt(AnimeIndex.appProp.getProperty("Music_Dialog_Default_Image_Counter"));
	private boolean defImgStd = false;

	public MusicDialog()
	{
		super(AnimeIndex.frame, false);
		LogManager.getLogManager().reset();
		Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
		Logger.getLogger("org.jaudiotagger").setUseParentHandlers(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MusicDialog.class.getResource("/image/Headp.png")));
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e)
			{
				progressBar.setString("");
				setDefaultImage();
				if(new File(MAMUtil.getMusicPath() + "[[[music]]].anaconda").isFile())
				{
					FileManager.loadSongMap();
					createSongsTree(songsMap);
				}
				DriveFileFetcherTask task = new DriveFileFetcherTask();
				task.addPropertyChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt)
					{
						if (evt.getPropertyName().equals("progress"))
						{
							setTitle("My Anime Musics • Aggiornamento Dati Album : "+((int)task.count*100/(int)task.albumNumber)+"%");
						}
						if (evt.getPropertyName().equals("state"))
						{
							if (evt.getNewValue().toString().equalsIgnoreCase("done"))
							{
								setTitle("My Anime Musics");
								String obj = null;
								boolean expanded = false;
								try
								{
									obj = songsTree.getLastSelectedPathComponent().toString();
									expanded = songsTree.isExpanded(songsTree.getSelectionRows()[0]);
								}catch(NullPointerException e)
								{}
								createSongsTree(songsMap);
								try
								{
									TreePath nodePath = find((DefaultMutableTreeNode) (songsTree.getModel().getRoot()), obj);
									songsTree.setSelectionPath(nodePath);
									if(expanded)
										songsTree.expandPath(nodePath);
								}catch(IllegalArgumentException e)
								{}
								if(progressBar.getString().contains("Download"))
									btnLoad.setEnabled(false);
								if(!songsMap.isEmpty())
									FileManager.saveSongMap();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
							    String t = sdf.format(new Date())+"";
							    t = t.substring(0, 10)+"T"+t.substring(11);
							    AnimeIndex.appProp.setProperty("Last_Music_Check", t); 
							}
						}	
					}
				});
				task.execute();
			}

			@Override
			public void windowClosing(WindowEvent e)
			{
				stop();
				if (timer != null)
					timer.stop();
				if(downloadDriveTask!=null)
					downloadDriveTask.cancel(true);
				if(!songsMap.isEmpty())
					FileManager.saveSongMap();
				AnimeIndex.appProp.setProperty("Music_Dialog_Default_Image_Counter", (defImgCounter+""));
			}
		});
		setTitle("My Anime Musics");
		setResizable(false);
		setFont(AnimeIndex.segui.deriveFont(12f));
		setBounds(100, 100, 531, 448);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(4, 4));
		{
			JPanel dataPanel = new JPanel();
			contentPanel.add(dataPanel, BorderLayout.CENTER);
			dataPanel.setLayout(new BorderLayout(0, 3));
			{
				progressBar = new JProgressBar();
				progressBar.setFont(new Font("Tahoma", Font.PLAIN, 10));
				progressBar.setStringPainted(true);
				progressBar.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseReleased(MouseEvent e)
					{
						if (isRunning || isPaused)
						{
							stop();
							if (timer != null)
								timer.stop();
							String t = progressBar.getString();
							pauseLocation = songTotalLength - (e.getX() * songTotalLength / progressBar.getWidth());
							progressBar.setValue((int) pauseLocation);
							resume();
							progressBar.setString(t);
							if (timer != null)
								timer.start();
							btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/pause_icon.png")));
						}
					}
				});
				dataPanel.add(progressBar, BorderLayout.SOUTH);
			}
			lblTitle = new JMarqueeLabel("");
			lblTitle.setScrollDirection(0);
			lblTitle.setSpeed(15);
			lblTitle.setPreferredSize(new Dimension(24, 17));
			lblTitle.setMinimumSize(new Dimension(24, 17));
			lblTitle.setMaximumSize(new Dimension(24, 17));
			lblTitle.setTextFont(AnimeIndex.segui.deriveFont(12f));
			dataPanel.add(lblTitle, BorderLayout.NORTH);
			{
				lblImage.setMaximumSize(new Dimension(335, 335));
				lblImage.setMinimumSize(new Dimension(335, 335));
				lblImage.setPreferredSize(new Dimension(335, 335));
				lblImage.setBorder(new LineBorder(new Color(40, 40, 40), 2, true));
				dataPanel.add(lblImage, BorderLayout.CENTER);
			}
		}
		{
			JPanel buttonPanel = new JPanel();
			contentPanel.add(buttonPanel, BorderLayout.SOUTH);
			{
				GridBagLayout gbl_buttonPanel = new GridBagLayout();
				gbl_buttonPanel.columnWidths = new int[] { 88, 88, 45, 114, 45, 80, 55, 0 };
				gbl_buttonPanel.rowHeights = new int[] { 23, 0 };
				gbl_buttonPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
				gbl_buttonPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
				buttonPanel.setLayout(gbl_buttonPanel);
				{
					btnSucc = new JButton("");
					btnSucc.setEnabled(false);
					btnSucc.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e)
						{
							if (isRunning || isPaused)
							{
								stop();
								timer.stop();
							}
							counter++;
							setMusicTrack(MAMUtil.getMusicPath() + songList.get(counter) + ".mp3");
							if (!songList.isEmpty())
								songsTree.setSelectionPath(find((DefaultMutableTreeNode) (songsTree.getModel().getRoot()), songList.get(counter)));
							if (counter > 0 && counter<=songList.size())
							{
								btnPrev.setEnabled(true);
							}
							else
							{
								btnPrev.setEnabled(false);
							}
							if (counter >=(-1) && counter<songList.size()-1)
							{
								btnSucc.setEnabled(true);
							}
							else
							{
								btnSucc.setEnabled(false);
							}
						}
					});
					btnSucc.setToolTipText("Brano successivo");
					btnSucc.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/forward_icon.png")));
				}
				{
					btnPrev = new JButton("");
					btnPrev.setEnabled(false);
					btnPrev.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e)
						{
							if (isRunning || isPaused)
							{
								stop();
								timer.stop();
							}
							counter--;
							setMusicTrack(MAMUtil.getMusicPath() + songList.get(counter) + ".mp3");
							if (!songList.isEmpty())
								songsTree.setSelectionPath(find((DefaultMutableTreeNode) (songsTree.getModel().getRoot()), songList.get(counter)));
								
							if (counter > 0 && counter<=songList.size())
							{
								btnPrev.setEnabled(true);
							}
							else
							{
								btnPrev.setEnabled(false);
							}
							if (counter >=(-1) && counter<songList.size()-1)
							{
								btnSucc.setEnabled(true);
							}
							else
							{
								btnSucc.setEnabled(false);
							}
						}
					});
					btnPrev.setToolTipText("Brano precedente");
					btnPrev.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/rev_icon.png")));
				}
				{
					btnElimina = new JButton("Elimina");
					btnElimina.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e)
						{
							String musicName = (((DefaultMutableTreeNode) songsTree.getLastSelectedPathComponent()).getUserObject()).toString();
							if (songsMap.containsKey(musicName))
							{
								ArrayList<String> list = songsMap.get(musicName);
								for (String song : list)
								{	
									File file = new File(MAMUtil.getMusicPath() + song + ".mp3");
									if (file.isFile())
									{
										if(currentMusicPath.equals(file.getPath()))
										{
											if (isRunning || isPaused)
											{
												stop();
												timer.stop();
											}
										}
										try
										{
											FileManager.deleteData(file);
										}
										catch (IOException e1)
										{
											MAMUtil.writeLog(e1);
											e1.printStackTrace();
										}
										if (songList.contains(song))
										{
											int index = songList.indexOf(song);
											if(index==0 && counter==0)
												counter--;
											if (index < counter)
												counter--;
											songList.remove(song);
										}
									}
								}
							}
							else
							{	
								if (isRunning || isPaused)
								{
									stop();
									timer.stop();
								}
								File file = new File(MAMUtil.getMusicPath() + musicName + ".mp3");
								if (file.isFile())
								{
									try
									{
										FileManager.deleteData(file);										
									}
									catch (IOException e1)
									{
										MAMUtil.writeLog(e1);
										e1.printStackTrace();
									}
									if (songList.contains(musicName))
									{
										int index = songList.indexOf(musicName);
										if(index==0 && counter==0)
											counter--;
										if (index < counter)
											counter--;
										songList.remove(musicName);
									}
								}
							}
							if (counter > 0 && counter<=songList.size())
							{
								btnPrev.setEnabled(true);
							}
							else
							{
								btnPrev.setEnabled(false);
							}
							if (counter >=(-1) && counter<songList.size()-1)
							{
								btnSucc.setEnabled(true);
							}
							else
							{
								btnSucc.setEnabled(false);
							}	
							btnElimina.setEnabled(false);
							btnLoad.setEnabled(true);
							songsTree.setCellRenderer(new JTreeIcons());
							setDefaultImage();
							progressBar.setValue(0);
							progressBar.setString("");
							lblTitle.setText("");
							btnPlaypause.setEnabled(false);
							btnSave.setEnabled(false);
							btnRestart.setEnabled(false);
						}
					});
					btnElimina.setEnabled(false);
					GridBagConstraints gbc_btnElimina = new GridBagConstraints();
					gbc_btnElimina.fill = GridBagConstraints.BOTH;
					gbc_btnElimina.insets = new Insets(0, 0, 0, 5);
					gbc_btnElimina.gridx = 0;
					gbc_btnElimina.gridy = 0;
					buttonPanel.add(btnElimina, gbc_btnElimina);
				}
				{
					btnSave = new JButton("Esporta");
					btnSave.setEnabled(false);
					GridBagConstraints gbc_btnSave = new GridBagConstraints();
					gbc_btnSave.fill = GridBagConstraints.BOTH;
					gbc_btnSave.insets = new Insets(0, 0, 0, 5);
					gbc_btnSave.gridx = 1;
					gbc_btnSave.gridy = 0;
					buttonPanel.add(btnSave, gbc_btnSave);
					btnSave.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e)
						{
							JFileChooser chooser = new JFileChooser(System.getProperty("user.home") + File.separator + "Desktop");
							chooser.setMultiSelectionEnabled(false);
							chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							chooser.setDialogTitle("Salva in...");
							int returnVal = chooser.showDialog(AnimeIndex.mainFrame, "Salva");
							if (returnVal == JFileChooser.APPROVE_OPTION)
							{
								String musicName = (((DefaultMutableTreeNode) songsTree.getLastSelectedPathComponent()).getUserObject()).toString();
								if (songsMap.containsKey(musicName))
								{
									File destination = new File(chooser.getSelectedFile() + File.separator + musicName);
									for (String song : songsMap.get(musicName))
									{
										File choosedFile = new File(MAMUtil.getMusicPath() + song + ".mp3");
										if (choosedFile.isFile())
										{	
											try
											{
												FileUtils.copyFileToDirectory(choosedFile, destination);
											}
											catch (IOException e1)
											{
												MAMUtil.writeLog(e1);
												e1.printStackTrace();
											}
										}
									}
								}
								else
								{
									File destination = chooser.getSelectedFile();
									File choosedFile = new File(MAMUtil.getMusicPath() + musicName + ".mp3");
									try
									{
										FileUtils.copyFileToDirectory(choosedFile, destination);
									}
									catch (IOException e1)
									{
										MAMUtil.writeLog(e1);
										e1.printStackTrace();
									}
								}
							}
						}
					});
					btnSave.setToolTipText("Salva i brani che preferisci");
				}
				GridBagConstraints gbc_btnPrev = new GridBagConstraints();
				gbc_btnPrev.fill = GridBagConstraints.BOTH;
				gbc_btnPrev.insets = new Insets(0, 0, 0, 5);
				gbc_btnPrev.gridx = 2;
				gbc_btnPrev.gridy = 0;
				buttonPanel.add(btnPrev, gbc_btnPrev);
				{
					btnPlaypause = new JButton("");
					btnPlaypause.setEnabled(false);
					btnPlaypause.setToolTipText("Play/Pausa");
					btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/play_icon.png")));
					btnPlaypause.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e)
						{
							if (!isRunning && !isPaused)
							{
								play(currentMusicPath);
								btnRestart.setEnabled(true);
								btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/pause_icon.png")));
								progressBar.setMaximum((int) songTotalLength);
								timer = new Timer(100, new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e)
									{
										long current = 0;
										long currentTime = 0;
										try
										{
											current = songTotalLength - fis.available();
											if (!(duration + "").isEmpty())
											{
												String t = "";
												currentTime = current * duration / songTotalLength;
												if (((currentTime / 60) + "").length() < 2)
												{
													if (((currentTime % 60) + "").length() < 2)
														t = "0" + currentTime / 60 + ":0" + currentTime % 60 + " / " + time;
													else
														t = "0" + currentTime / 60 + ":" + currentTime % 60 + " / " + time;
												}
												else if (((currentTime % 60) + "").length() < 2)
													t = currentTime / 60 + ":0" + currentTime % 60 + " / " + time;
												else
													t = currentTime / 60 + ":" + currentTime % 60 + " / " + time;
												progressBar.setString(t);
											}
										}
										catch (IOException e1)
										{
											timer.stop();
										}
										progressBar.setValue((int) current);
									}
								});
								timer.start();
							}
							else if (!isPaused && isRunning)
							{
								pause();
								timer.stop();
								btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/play_icon.png")));
							}
							else if (!isRunning && isPaused)
							{
								resume();
								timer.start();
								btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/pause_icon.png")));
							}
						}
					});
				}
				GridBagConstraints gbc_btnPlaypause = new GridBagConstraints();
				gbc_btnPlaypause.fill = GridBagConstraints.BOTH;
				gbc_btnPlaypause.insets = new Insets(0, 0, 0, 5);
				gbc_btnPlaypause.gridx = 3;
				gbc_btnPlaypause.gridy = 0;
				buttonPanel.add(btnPlaypause, gbc_btnPlaypause);
				GridBagConstraints gbc_btnSucc = new GridBagConstraints();
				gbc_btnSucc.fill = GridBagConstraints.BOTH;
				gbc_btnSucc.insets = new Insets(0, 0, 0, 5);
				gbc_btnSucc.gridx = 4;
				gbc_btnSucc.gridy = 0;
				buttonPanel.add(btnSucc, gbc_btnSucc);
				btnRestart = new JButton("");
				btnRestart.setEnabled(false);
				btnRestart.setToolTipText("Ricomincia");
				btnRestart.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/restart.png")));
				btnRestart.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e)
					{
						stop();
						timer.stop();
						play(currentMusicPath);
						timer.start();
						btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/pause_icon.png")));
					}
				});
				GridBagConstraints gbc_btnRestart = new GridBagConstraints();
				gbc_btnRestart.fill = GridBagConstraints.BOTH;
				gbc_btnRestart.insets = new Insets(0, 0, 0, 5);
				gbc_btnRestart.gridx = 5;
				gbc_btnRestart.gridy = 0;
				buttonPanel.add(btnRestart, gbc_btnRestart);
				JButton btnLoop = new JButton("");
				btnLoop.setToolTipText("Loop");
				btnLoop.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/loop.png")));
				GridBagConstraints gbc_btnLoop = new GridBagConstraints();
				gbc_btnLoop.fill = GridBagConstraints.BOTH;
				gbc_btnLoop.gridx = 6;
				gbc_btnLoop.gridy = 0;
				buttonPanel.add(btnLoop, gbc_btnLoop);
				{
					treePanel = new JPanel();
					contentPanel.add(treePanel, BorderLayout.WEST);
					treePanel.setLayout(new BorderLayout(3, 3));
					JScrollPane scrollPane = new JScrollPane();
					scrollPane.setMaximumSize(new Dimension(172, 64));
					scrollPane.setPreferredSize(new Dimension(172, 64));
					scrollPane.setMinimumSize(new Dimension(172, 64));
					treePanel.add(scrollPane, BorderLayout.CENTER);
					songsTree = new JTree();
					songsTree.setModel(new DefaultTreeModel(
						new DefaultMutableTreeNode("JTree") {
							{
							}
						}
					));
					if(!new File(MAMUtil.getMusicPath() + "[[[music]]].anaconda").isFile())
					{
						songsTree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("JTree") 
						{{
							add(new DefaultMutableTreeNode("Caricamento in corso..."));
						}}));
					}
					songsTree.addMouseListener(new MouseAdapter() {

						@Override
						public void mouseReleased(MouseEvent e)
						{
							Object name = null;
							try
							{
								name = ((DefaultMutableTreeNode) songsTree.getLastSelectedPathComponent()).getUserObject();
							}
							catch (NullPointerException e2)
							{}
							if (name != null && !songsMap.containsKey(name))
							{	
								if (new File(MAMUtil.getMusicPath() + name + ".mp3").isFile())
								{
									if (!songList.isEmpty() && songList.contains(name))
										songList.remove(name);
									songList.add((String) name);
									counter = songList.size() - 1;
									if (counter > 0)
									{
										btnPrev.setEnabled(true);
									}
									else
									{
										btnPrev.setEnabled(false);
									}
									btnSucc.setEnabled(false);
								}
							}
						}
					});
					songsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
					songsTree.addTreeSelectionListener(new TreeSelectionListener() {

						@Override
						public void valueChanged(TreeSelectionEvent e)
						{
							Object name = null;
							try
							{
								name = ((DefaultMutableTreeNode) songsTree.getLastSelectedPathComponent()).getUserObject();
							}catch(NullPointerException e1)
							{}
							if(name!=null)
							{
								if (songsMap.containsKey(name))
								{
									int count = 0;
									ArrayList<String> songList = songsMap.get(name);
									for (String song : songList)
										if (new File(MAMUtil.getMusicPath() + song + ".mp3").isFile())
											count++;
									if (count == songList.size())
										btnLoad.setEnabled(false);
									else if (!progressBar.getString().contains("Download"))
										btnLoad.setEnabled(true);
									if (count != 0)
									{
										btnElimina.setEnabled(true);
										btnSave.setEnabled(true);
									}
									else
									{
										btnElimina.setEnabled(false);
										btnSave.setEnabled(false);
									}
								}
								else if (new File(MAMUtil.getMusicPath() + name + ".mp3").isFile())
								{
									if ((isPaused || isRunning) && !(MAMUtil.getMusicPath() + name + ".mp3").equals(currentMusicPath))
									{
										stop();
										timer.stop();
									}
									progressBar.setValue(0);
									setMusicTrack(MAMUtil.getMusicPath() + name + ".mp3");
									btnLoad.setEnabled(false);
								}
								else
								{
									if ((isPaused || isRunning))
									{
										stop();
										timer.stop();
									}
									lblTitle.setText("");
									setDefaultImage();
									btnLoad.setEnabled(true);
									btnPlaypause.setEnabled(false);
									btnElimina.setEnabled(false);
									btnRestart.setEnabled(false);
									btnSave.setEnabled(false);
									btnSucc.setEnabled(false);
									btnPrev.setEnabled(false);
									progressBar.setString("");
									progressBar.setValue(0);
								}
							}
						}
					});
					songsTree.setRootVisible(false);
					songsTree.setShowsRootHandles(true);
					songsTree.setFont(AnimeIndex.segui.deriveFont(12f));
					scrollPane.setViewportView(songsTree);
					scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
					scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
					{
						treeButtonPanel = new JPanel();
						treePanel.add(treeButtonPanel, BorderLayout.SOUTH);
						btnLoad = new JButton("Scarica");
						btnLoad.setMaximumSize(new Dimension(67, 20));
						btnLoad.setMinimumSize(new Dimension(67, 20));
						btnLoad.setPreferredSize(new Dimension(67, 20));
						btnLoad.setEnabled(false);
						btnLoad.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e)
							{
								btnLoad.setEnabled(false);
								btnPlaypause.setEnabled(false);
								btnElimina.setEnabled(false);
								btnRestart.setEnabled(false);
								btnSave.setEnabled(false);
								btnSucc.setEnabled(false);
								btnPrev.setEnabled(false);
								if (isRunning || isPaused)
								{
									stop();
									timer.stop();
								}
								lblTitle.setText("");
								setDefaultImage();
								String musicName = (((DefaultMutableTreeNode) songsTree.getLastSelectedPathComponent()).getUserObject()).toString();
								if (songsMap.containsKey(musicName))
									downloadDriveTask = new GoogleDriveDownloadTask(songsMap.get(musicName));
								else
									downloadDriveTask = new GoogleDriveDownloadTask(musicName);
								downloadDriveTask.addPropertyChangeListener(new PropertyChangeListener() {

									@Override
									public void propertyChange(PropertyChangeEvent evt)
									{

										if (evt.getPropertyName().equals("progress"))
										{	
											progressBar.setValue(downloadDriveTask.getProgress());
											progressBar.setString("Download File " + downloadDriveTask.fileNumber + "/" + downloadDriveTask.totalFileNumber + " : " + ((int) (progressBar.getPercentComplete() * 100)) + "%");
										}
										if (evt.getPropertyName().equals("state"))
										{
											if (evt.getNewValue().toString().equalsIgnoreCase("done"))
											{
												progressBar.setString("");
												songsTree.setCellRenderer(new JTreeIcons());
												btnLoad.setEnabled(true);
												if (songsMap.containsKey(musicName))
												{
													ArrayList<String> songs = songsMap.get(musicName);
													if (songs != null)
													{
														String song = songs.get(0);
														if (!downloadDriveTask.isCancelled())
															setMusicTrack(MAMUtil.getMusicPath() + song + ".mp3");
														if (!songList.isEmpty() && songList.contains(song))
															songList.remove(song);
														songList.add(song);
													}
												}
												else
												{
													if (!downloadDriveTask.isCancelled())
														setMusicTrack(MAMUtil.getMusicPath() + musicName + ".mp3");
													if (!songList.isEmpty() && songList.contains(musicName))
														songList.remove(musicName);
													songList.add(musicName);	
												}
												counter = songList.size() - 1;
												if (counter > 0)
												{
													btnPrev.setEnabled(true);
												}
												else
												{
													btnPrev.setEnabled(false);
												}
												btnSucc.setEnabled(false);
												songsTree.setSelectionPath(find((DefaultMutableTreeNode) (songsTree.getModel().getRoot()), songList.get(counter)));
											}
											if (evt.getNewValue().toString().equalsIgnoreCase("started"))
											{
												progressBar.setValue(0);
												progressBar.setMaximum(100);
												progressBar.setString("Download File " + downloadDriveTask.fileNumber + "/" + downloadDriveTask.totalFileNumber + " : " + ((int)progressBar.getPercentComplete() * 100) + "%");
											}
											
										}
									}
								});
								downloadDriveTask.execute();
							}
						});
						treeButtonPanel.setLayout(new GridLayout(0, 1, 0, 0));
						btnLoad.setToolTipText("Scarica il brano per ascoltarlo");
						treeButtonPanel.add(btnLoad);
					}
				}
				btnLoop.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e)
					{
						if (loopActive == false)
						{
							btnLoop.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/loop_red.png")));
							loopActive = true;
						}
						else if (loopActive == true)
						{
							btnLoop.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/loop.png")));
							loopActive = false;
						}
					}
				});
			}
		}
	}

	private void play(String path)
	{
		try
		{
			fis = new FileInputStream(path);
			buff = new BufferedInputStream(fis);
			player = new Player(buff);
			songTotalLength = fis.available();
		}
		catch (Exception exc)
		{
			MAMUtil.writeLog(exc);
			exc.printStackTrace();
		}
		new Thread() {

			@Override
			public void run()
			{
				try
				{
					isRunning = true;
					isPaused = false;
					player.play();
					if (player.isComplete() && loopActive == true)
						play(currentMusicPath);
					if (loopActive == false)
					{
						if (player.isComplete())
						{
							isRunning = false;
							isPaused = false;
						}
						btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/play_icon.png")));
						if (!isPaused)
							progressBar.setString(time);
					}
				}
				catch (JavaLayerException e)
				{
					MAMUtil.writeLog(e);
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void pause()
	{
		try
		{
			pauseLocation = fis.available();
			player.close();
			isRunning = false;
			isPaused = true;
			if (fis != null)
				fis.close();
			if (buff != null)
				buff.close();
		}
		catch (IOException e)
		{
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
	}

	private void resume()
	{
		try
		{
			play(currentMusicPath);
			fis.skip(songTotalLength - pauseLocation);
		}
		catch (Exception exc)
		{
			MAMUtil.writeLog(exc);
			exc.printStackTrace();
		}
	}

	private void stop()
	{
		if (player != null)
			player.close();
		isRunning = false;
		isPaused = false;
		try
		{
			if (fis != null)
				fis.close();
			if (buff != null)
				buff.close();
		}
		catch (IOException e)
		{
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
	}

	private void setMusicTrack(String path)
	{
		currentMusicPath = path;
		AudioFile f = null;
		Mp3File song = null;
		File music = new File(currentMusicPath);
		try
		{
			song = new Mp3File(currentMusicPath);
			f = AudioFileIO.read(music);
		}
		catch (UnsupportedTagException e1)
		{
			MAMUtil.writeLog(e1);
			e1.printStackTrace();
		}
		catch (InvalidDataException e1)
		{
			MAMUtil.writeLog(e1);
			e1.printStackTrace();
		}
		catch (CannotReadException e1)
		{
			MAMUtil.writeLog(e1);
			e1.printStackTrace();
		}
		catch (IOException e1)
		{
			MAMUtil.writeLog(e1);
			e1.printStackTrace();
		}
		catch (TagException e1)
		{
			MAMUtil.writeLog(e1);
			e1.printStackTrace();
		}
		catch (ReadOnlyFileException e1)
		{
			MAMUtil.writeLog(e1);
			e1.printStackTrace();
		}
		catch (InvalidAudioFrameException e1)
		{
			MAMUtil.writeLog(e1);
			e1.printStackTrace();
		}
		Tag tag = f.getTag();
		String currentTag = tag.getFirst(FieldKey.TITLE);
		if (!currentTag.isEmpty())
			title = currentTag;
		else
		{
			title = music.getName();
			title = title.substring(0, title.length() - 4);
		}

		currentTag = tag.getFirst(FieldKey.ARTIST);
		if (!currentTag.isEmpty())
			title += "  .:::.  " + currentTag;

		currentTag = tag.getFirst(FieldKey.ALBUM);
		if (!currentTag.isEmpty())
			title += "  .:::.  " + currentTag;

		int bitrate = song.getBitrate();
		if (!(bitrate + "").isEmpty())
			title += "  .:::.  " + bitrate + " Kbps";

		byte[] img = song.getId3v2Tag().getAlbumImage();
		if (img != null)
		{
			ByteArrayInputStream arr = new ByteArrayInputStream(img);
			BufferedImage bffImg = null;
			try
			{
				bffImg = ImageIO.read(arr);
			}
			catch (IOException e)
			{
				MAMUtil.writeLog(e);
				e.printStackTrace();
			}
			lblImage.setIcon(new ImageIcon(MAMUtil.getScaledImage(bffImg, 335, 335)));
			defImgStd = false;
		}
		else
			setDefaultImage();
		duration = song.getLengthInSeconds();
		if (!(duration + "").isEmpty())
			if (((duration / 60) + "").length() < 2)
			{
				if (((duration % 60) + "").length() < 2)
					time = "0" + duration / 60 + ":0" + duration % 60;
				else
					time = "0" + duration / 60 + ":" + duration % 60;
			}
			else if (((duration % 60) + "").length() < 2)
				time = duration / 60 + ":0" + duration % 60;
			else
				time = duration / 60 + ":" + duration % 60;
		lblTitle.setText(title);
		if(!progressBar.getString().contains("Download"))
			btnPlaypause.setEnabled(true);
		progressBar.setValue(0);
		progressBar.setString(time);
		btnElimina.setEnabled(true);
		btnSave.setEnabled(true);
	}

	private void setDefaultImage()
	{
		if(!defImgStd)
		{
			String img = "";
			switch(defImgCounter % 8)
			{
				case 0: img = "miku_mem";
						break;
				case 1: img = "Headphone..";
						break;
				case 2: img = "Hatsune-Miku-Vocaloid..";
						break;
				case 3: img = "Hatsune-Miku-Vocaloid...";
						break;
				case 4: img = "hatsune-miku-vocaloid-1715";
						break;
				case 5: img = "hmny";
						break;
				case 6: img = "Hatsune-Miku-Vocaloid";
						break;
				case 7: img = "Headphone";
						break;
			}
			BufferedImage image = null;
			try
			{
				image = ImageIO.read(ClassLoader.getSystemResource("image/"+img+".png"));
			}
			catch (IOException e1)
			{
				MAMUtil.writeLog(e1);
				e1.printStackTrace();
			}
			defImgCounter++;
			defImgStd = true;
			lblImage.setIcon(new ImageIcon(image));
		}
	}

	private void createSongsTree(TreeMap<String, ArrayList<String>> albumMap)
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		for (Map.Entry<String, ArrayList<String>> entry : albumMap.entrySet())
		{
			String album = entry.getKey();
			DefaultMutableTreeNode albumNode = new DefaultMutableTreeNode(album);
			root.add(albumNode);

			ArrayList<String> song = entry.getValue();
			for (String songName : song)
			{
				DefaultMutableTreeNode songNode = new DefaultMutableTreeNode(songName);
				albumNode.add(songNode);
			}
		}
		songsTreeModel = new DefaultTreeModel(root);
		songsTree.setModel(songsTreeModel);
		if(!progressBar.getString().contains("Download"))
			songsTree.setCellRenderer(new JTreeIcons());
	}

	private TreePath find(DefaultMutableTreeNode root, String s)
	{
		Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
		while (e.hasMoreElements())
		{
			DefaultMutableTreeNode node = e.nextElement();
			if (node.toString().equalsIgnoreCase(s))
				return new TreePath(node.getPath());
		}
		return null;
	}
}
