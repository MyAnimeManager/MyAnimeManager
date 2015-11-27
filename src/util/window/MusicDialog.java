package util.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.util.logging.Level;
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
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import main.AnimeIndex;
import util.MAMUtil;
import util.task.GoogleDriveDownload;

public class MusicDialog extends JDialog {
	//Lasciamelo che cosi non devo ogni volta fare copia e incolla
	//"C:\\Users\\Samu\\Desktop\\video musica immagini\\A Genesis - nano.mp3";
	private final JPanel contentPanel = new JPanel();
	private Player player;
	private boolean loopActive;
	private JLabel lblImage = new JLabel();
	private JLabel lblTitle;
	private JButton btnPlaypause;
	private JButton btnRestart;
	private FileInputStream fis;
	private BufferedInputStream buff;
	private boolean isRunning;
	private boolean isPaused;
	private String currentMusicPath = "C:\\Users\\Samu\\Desktop\\video musica immagini\\A Genesis - nano.mp3";
	private long pauseLocation;
	private long songTotalLength;
	private Timer timer;
	private Timer tim;
	private JProgressBar progressBar;
	private String title;
	private String ttl;
	private JButton btnLoad;
	private JButton btnSave;
	private JButton btnPrev;
	private JButton btnSucc;
	private JTabbedPane tabbedPane;
	private JPanel playlistPanel;
	private JPanel panel;
	private JButton btnElimina;
	private JPanel panel_1;
	private JButton btnInPlaylist;
	private JScrollPane playlistScrollPane;
	private JTree playlistTree;
	private long duration;
	private String time = "";
	
	public MusicDialog()
	{
		super(AnimeIndex.frame, false);
		Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MusicDialog.class.getResource("/image/Headp.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
//TODO			setDefoultImage();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				stop();
				if(timer!=null)
					timer.stop();
			}
		});
		setTitle("My Anime Musics");
		setResizable(false);
		setFont(MAMUtil.segui().deriveFont(12f));
		setBounds(100, 100, 540, 448);
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
					public void mouseReleased(MouseEvent e) {
						stop();
						timer.stop();
						pauseLocation = songTotalLength - (e.getX()*songTotalLength/progressBar.getWidth());
						progressBar.setValue((int)pauseLocation);
						resume();
						timer.start();
						isRunning=true;
						isPaused=false;
						btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/pause_icon.png")));
					}
				});
				dataPanel.add(progressBar, BorderLayout.SOUTH);
			}
			{
				JPanel titlePanel = new JPanel();
				FlowLayout flowLayout = (FlowLayout) titlePanel.getLayout();
				flowLayout.setVgap(1);
				flowLayout.setHgap(0);
				dataPanel.add(titlePanel, BorderLayout.NORTH);
				lblTitle = new JLabel("TITLE");//max 37 char
				titlePanel.add(lblTitle);
				setMusicTrack();
				lblTitle.setFont(MAMUtil.segui().deriveFont(12f));
			}
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
				gbl_buttonPanel.columnWidths = new int[]{93, 93, 45, 114, 45, 80, 54, 0};
				gbl_buttonPanel.rowHeights = new int[]{23, 0};
				gbl_buttonPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				gbl_buttonPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
				buttonPanel.setLayout(gbl_buttonPanel);
				{
					btnSucc = new JButton("");
					btnSucc.setToolTipText("Brano successivo");
					btnSucc.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/forward_icon.png")));
				}
				{
					btnPrev = new JButton("");
					btnPrev.setToolTipText("Brano precedente");
					btnPrev.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/rev_icon.png")));
				}
				{
					btnElimina = new JButton("Elimina");
					GridBagConstraints gbc_btnElimina = new GridBagConstraints();
					gbc_btnElimina.fill = GridBagConstraints.BOTH;
					gbc_btnElimina.insets = new Insets(0, 0, 0, 5);
					gbc_btnElimina.gridx = 0;
					gbc_btnElimina.gridy = 0;
					buttonPanel.add(btnElimina, gbc_btnElimina);
				}
				{
					btnSave = new JButton("Esporta");
					GridBagConstraints gbc_btnSave = new GridBagConstraints();
					gbc_btnSave.fill = GridBagConstraints.BOTH;
					gbc_btnSave.insets = new Insets(0, 0, 0, 5);
					gbc_btnSave.gridx = 1;
					gbc_btnSave.gridy = 0;
					buttonPanel.add(btnSave, gbc_btnSave);
					btnSave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							File choosedFile = new File(currentMusicPath);
							JFileChooser chooser = new JFileChooser(System.getProperty("user.home") + File.separator + "Desktop");
							chooser.setMultiSelectionEnabled(false);
							chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							chooser.setDialogTitle("Salva in...");
							
							int returnVal = chooser.showDialog(AnimeIndex.mainFrame, "Salva");
							
							if (returnVal == JFileChooser.APPROVE_OPTION)
							{
								File destination = chooser.getSelectedFile();
								try{
									FileUtils.copyFileToDirectory(choosedFile, destination);
								}
								catch (IOException e1){
									MAMUtil.writeLog(e1);
									e1.printStackTrace();
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
					btnPlaypause.setToolTipText("Play/Pausa");
					btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/play_icon.png")));
					btnPlaypause.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(!isRunning && !isPaused)
							{
								play(currentMusicPath);
								isRunning=true;
								btnRestart.setEnabled(true);
								btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/pause_icon.png")));
								progressBar.setMaximum((int) songTotalLength);
								timer = new Timer(100, new ActionListener()
                                {
                                    public void actionPerformed(ActionEvent e)
                                    {
                                        long current = 0;
                                        long currentTime = 0;
                                        try{
                                            current = songTotalLength - fis.available();
                                            if(!(duration+"").isEmpty())
                                            {
	                                            String t ="";
	                							currentTime = current*duration/songTotalLength;
	                							if(((currentTime/60)+"").length()<2)
	                							{
	                								if(((currentTime%60)+"").length()<2)
	                									t = "0" + currentTime/60 + ":0" + currentTime%60 + " / " + time;
	                								else
	                									t = "0" + currentTime/60 + ":" + currentTime%60 + " / " + time;
	                							}
	                							else
	                							{
	                								if(((currentTime%60)+"").length()<2)
	                									t = currentTime/60 + ":0" + currentTime%60 + " / " + time;
	                								else
	                									t = currentTime/60 + ":" + currentTime%60 + " / " + time;
	                							}
	                    						progressBar.setString(t);
                                            }
                                        }
                                        catch (IOException e1){
                                            timer.stop();
                                        }
                                        progressBar.setValue((int) current);
                                    }
                                    
                                });
								timer.start();
							}
							else if(!isPaused)
							{
								pause();
								timer.stop();
								isRunning=false;
								isPaused=true;
								btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/play_icon.png")));
							}
							else if(!isRunning && isPaused)
							{
								resume();
								timer.start();
								isRunning=true;
								isPaused=false;
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
					public void actionPerformed(ActionEvent e) {
						stop();
						timer.stop();
						play(currentMusicPath);
						timer.start();
						isRunning=true;
						isPaused=false;
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
					tabbedPane = new JTabbedPane();
					contentPanel.add(tabbedPane, BorderLayout.WEST);
					{
						panel = new JPanel();
						tabbedPane.addTab("Brani", null, panel, null);
						panel.setLayout(new BorderLayout(10, 0));
						JScrollPane scrollPane = new JScrollPane();
						panel.add(scrollPane);
						JTree tree = new JTree();
						tree.setRootVisible(false);
						tree.setMaximumSize(new Dimension(171, 64));
						tree.setPreferredSize(new Dimension(171, 64));
						tree.setMinimumSize(new Dimension(171, 64));
						tree.setShowsRootHandles(true);
						tree.setFont(MAMUtil.segui().deriveFont(12f));
						scrollPane.setViewportView(tree);
						{
							panel_1 = new JPanel();
							panel.add(panel_1, BorderLayout.SOUTH);
							{
								btnLoad = new JButton("Scarica");
								btnLoad.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										GoogleDriveDownload task = new GoogleDriveDownload(false);
										task.addPropertyChangeListener(new PropertyChangeListener() {
											public void propertyChange(PropertyChangeEvent evt) {
												if (evt.getPropertyName().equals("progress"))
												{
													int progress = task.getProgress();
													progressBar.setValue(progress);
												}
											}
										});
										task.execute();
									}
								});
								btnLoad.setToolTipText("Carica il brano per ascoltarlo");
							}
							{
								btnInPlaylist = new JButton("In Playlist");
							}
							panel_1.setLayout(new GridLayout(0, 2, 0, 0));
							panel_1.add(btnLoad);
							panel_1.add(btnInPlaylist);
						}
						scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
						scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
					}
					{
						{
							{
								playlistPanel = new JPanel();
								tabbedPane.addTab("Playlist", null, playlistPanel, null);
								GridBagLayout gbl_playlistPanel = new GridBagLayout();
								gbl_playlistPanel.columnWidths = new int[]{0, 0};
								gbl_playlistPanel.rowHeights = new int[]{0, 0};
								gbl_playlistPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
								gbl_playlistPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
								playlistPanel.setLayout(gbl_playlistPanel);
								{
									playlistScrollPane = new JScrollPane();
									GridBagConstraints gbc_playlistScrollPane = new GridBagConstraints();
									gbc_playlistScrollPane.fill = GridBagConstraints.BOTH;
									gbc_playlistScrollPane.gridx = 0;
									gbc_playlistScrollPane.gridy = 0;
									playlistPanel.add(playlistScrollPane, gbc_playlistScrollPane);
									{
										playlistTree = new JTree();
										playlistTree.setFont(MAMUtil.segui().deriveFont(12f));
										playlistTree.setShowsRootHandles(true);
										playlistTree.setRootVisible(false);
										playlistScrollPane.setViewportView(playlistTree);
									}
								}
							}
						}
					}
				}
				btnLoop.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(loopActive==false)
						{
							btnLoop.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/loop_red.png")));
							loopActive=true;
						}
						else if(loopActive==true)
						{
							btnLoop.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/loop.png")));
							loopActive=false;
						}
					}
				});
			}
		}
	}
	
	private void play(String path)
	{
		try{
			fis = new FileInputStream(path);
			buff = new BufferedInputStream(fis);
		    player = new Player(buff);
		    songTotalLength=fis.available();
			}
			catch(Exception exc){
				MAMUtil.writeLog(exc);
			    exc.printStackTrace();
			}
		new Thread()
		{
			public void run()
			{
				try{
					player.play();
					if(player.isComplete()&&loopActive==true)
						play(currentMusicPath);
					if(loopActive==false)
					{	btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/play_icon.png")));
						progressBar.setString(time);
					}
				}
				catch (JavaLayerException e){
					MAMUtil.writeLog(e);
					e.printStackTrace();
				}
			}
		}.start();
	}
	private void pause()
	{
		try{
			pauseLocation = fis.available();
			player.close();
			if(fis!=null)
				fis.close();
			if(buff!=null)
				buff.close();
		}
		catch (IOException e){
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
	}
	private void resume()
	{
		try{
			play(currentMusicPath);
		    fis.skip(songTotalLength-pauseLocation);
			}
		catch(Exception exc){
			MAMUtil.writeLog(exc);
		    exc.printStackTrace();
		}
	}
	private void stop()
	{
		if(player!=null)
		{
			player.close();
			isRunning=false;
			isPaused=true;
		}
		try{
			if(fis!=null)
				fis.close();
			if(buff!=null)
				buff.close();
		}
		catch (IOException e){
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
	}
	private void setMusicTrack()
	{
		AudioFile f = null;
		try
		{
			Mp3File song = new Mp3File(currentMusicPath);
			File music = new File(currentMusicPath);
			f = AudioFileIO.read(music);
			Tag tag = f.getTag();
			String currentTag;
			currentTag = tag.getFirst(FieldKey.TITLE);
			if(!currentTag.isEmpty())
				title = currentTag;
			else
			{
				title = music.getName();
				title = title.substring(0, title.length()-4);
			}
						
			currentTag = tag.getFirst(FieldKey.ARTIST);
			if(!currentTag.isEmpty())
				title +="  .:::.  " + currentTag;
			
			currentTag = tag.getFirst(FieldKey.ALBUM);
			if(!currentTag.isEmpty())
				title +="  .:::.  " + currentTag;
			
			int bitrate = song.getBitrate();
			if(!(bitrate+"").isEmpty())
				title +="  .:::.  " + bitrate + " Kbps";
			
			duration = song.getLengthInSeconds();
			if(!(duration+"").isEmpty())
			{
				if(((duration/60)+"").length()<2)
				{
					if(((duration%60)+"").length()<2)
						time = "0" + duration/60 + ":0" + duration%60;
					else
						time = "0" + duration/60 + ":" + duration%60;
				}
				else
				{
					if(((duration%60)+"").length()<2)
						time = duration/60 + ":0" + duration%60;
					else
						time = duration/60 + ":" + duration%60;
				}
			}
			progressBar.setString(time);
			
			if(title.length()<=37)
				lblTitle.setText(title);
			else
			{
				int n = title.length();
				String sb = "";
		        for (int i = 0; i < n; i++) {
		            sb+=' ';
		        }
		        ttl = sb+title+sb;
				tim = new Timer(1000/12, new ActionListener()
                {
					private int index;
                    public void actionPerformed(ActionEvent e)
                    {
                    	 index++;
                         if (index > ttl.length() - n) {
                             index = 0;
                         }
                         lblTitle.setText(ttl.substring(index, index + n));
                    }
                });
				tim.start();
			}
			byte[] img = song.getId3v2Tag().getAlbumImage();
			if(img!=null)
				lblImage.setIcon(new ImageIcon(MAMUtil.getScaledImage(ImageIO.read(new ByteArrayInputStream(img)), 335, 335)));
			else
				setDefoultImage();
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
	}
	private void setDefoultImage()
	{
		BufferedImage image = null;
		try{
			if(AnimeIndex.appProp.getProperty("Session_Number").equalsIgnoreCase("0"))
				image = ImageIO.read(ClassLoader.getSystemResource("image/Headphone.png"));
			else if((Integer.parseInt(AnimeIndex.appProp.getProperty("Session_Number"))%2)==0)
				image = ImageIO.read(ClassLoader.getSystemResource("image/Headphone.png"));
			else
				image = ImageIO.read(ClassLoader.getSystemResource("image/Headphone...png"));
		}
		catch (IOException e1){
			MAMUtil.writeLog(e1);
			e1.printStackTrace();
		}
		lblImage.setIcon(new ImageIcon(image));
	}
}
