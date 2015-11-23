package util.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
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
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
import util.MAMUtil;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class MusicDialog extends JDialog {
	
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
	private int index;
	
	public MusicDialog()
	{
		super(AnimeIndex.frame, false);
		Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MusicDialog.class.getResource("/image/Headp.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
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
		setBounds(100, 100, 487, 448);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.WEST);
			{
				JTree tree = new JTree();
				tree.setMaximumSize(new Dimension(125, 64));
				tree.setPreferredSize(new Dimension(125, 64));
				tree.setMinimumSize(new Dimension(125, 64));
				tree.setShowsRootHandles(false);
				tree.setFont(MAMUtil.segui().deriveFont(12f));
				scrollPane.setViewportView(tree);
				scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
				scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.NORTH);
				lblTitle = new JLabel("TITLE");//max 38 char
				panel_1.add(lblTitle);
				lblTitle.setFont(MAMUtil.segui().deriveFont(12f));
			}
			{
				lblImage = new JLabel("");
				lblImage.setBorder(new LineBorder(new Color(40, 40, 40), 2, true));
				panel.add(lblImage, BorderLayout.CENTER);
			}
			{
				AudioFile f = null;
				try
				{
					File music = new File(currentMusicPath);
					f = AudioFileIO.read(music);
					Tag tag = f.getTag();
					title = tag.getFirst(FieldKey.TITLE);
					if(title.length()<=38)
						lblTitle.setText(title);
					else
						{
						int n = title.length();
						StringBuilder sb = new StringBuilder(n);
				        for (int i = 0; i < n; i++) {
				            sb.append(' ');
				        }
				        ttl = sb+title+sb;
							tim = new Timer(1000/12, new ActionListener()
	                        {
	                            public void actionPerformed(ActionEvent e)
	                            {
	                            	 index++;
	                                 if (index > ttl.length() - n) {
	                                     index = 0;
	                                 }
	                                 lblTitle.setText(ttl.substring(index, index + n));
//	                            	char c = title.charAt(0);
//							        String rest = title.substring(1);
//							        title = rest + c;
//							        lblTitle.setText(title);
	                            }
	                        });
							tim.start();
						}
					
					Mp3File song = new Mp3File(currentMusicPath);
					ID3v2 id3v2tag = song.getId3v2Tag();
					byte[] imageData = id3v2tag.getAlbumImage();
					BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
					ImageIcon icon = new ImageIcon(img);
					lblImage.setIcon(icon);
					
				}
				catch (UnsupportedTagException e1)
				{
					// TODO Auto-generated catch block
					MAMUtil.writeLog(e1);
					e1.printStackTrace();
				}
				catch (InvalidDataException e1)
				{
					// TODO Auto-generated catch block
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
					// TODO Auto-generated catch block
					MAMUtil.writeLog(e1);
					e1.printStackTrace();
				}
				catch (TagException e1)
				{
					// TODO Auto-generated catch block
					MAMUtil.writeLog(e1);
					e1.printStackTrace();
				}
				catch (ReadOnlyFileException e1)
				{
					// TODO Auto-generated catch block
					MAMUtil.writeLog(e1);
					e1.printStackTrace();
				}
				catch (InvalidAudioFrameException e1)
				{
					// TODO Auto-generated catch block
					MAMUtil.writeLog(e1);
					e1.printStackTrace();
				}
			}
			{
				progressBar = new JProgressBar();
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
				panel.add(progressBar, BorderLayout.SOUTH);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.SOUTH);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{67, 59, 51, 47, 51, 47, 0, 0};
			gbl_panel.rowHeights = new int[]{23, 0};
			gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JButton btnLoad = new JButton("Ascolta");
				GridBagConstraints gbc_btnLoad = new GridBagConstraints();
				gbc_btnLoad.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnLoad.anchor = GridBagConstraints.NORTH;
				gbc_btnLoad.insets = new Insets(0, 0, 0, 5);
				gbc_btnLoad.gridx = 0;
				gbc_btnLoad.gridy = 0;
				panel.add(btnLoad, gbc_btnLoad);
				btnLoad.setToolTipText("Carica il brano per ascoltarlo");
			}
			{
				JButton btnSave = new JButton("Salva");
				GridBagConstraints gbc_btnSave = new GridBagConstraints();
				gbc_btnSave.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnSave.anchor = GridBagConstraints.NORTH;
				gbc_btnSave.insets = new Insets(0, 0, 0, 5);
				gbc_btnSave.gridx = 1;
				gbc_btnSave.gridy = 0;
				panel.add(btnSave, gbc_btnSave);
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
			{
				JButton btnPrev = new JButton("");
				GridBagConstraints gbc_btnPrev = new GridBagConstraints();
				gbc_btnPrev.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnPrev.anchor = GridBagConstraints.NORTH;
				gbc_btnPrev.insets = new Insets(0, 0, 0, 5);
				gbc_btnPrev.gridx = 2;
				gbc_btnPrev.gridy = 0;
				panel.add(btnPrev, gbc_btnPrev);
				btnPrev.setToolTipText("Brano precedente");
				btnPrev.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/rev_icon.png")));
			}
			{
				btnPlaypause = new JButton("");
				GridBagConstraints gbc_btnPlaypause = new GridBagConstraints();
				gbc_btnPlaypause.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnPlaypause.anchor = GridBagConstraints.NORTH;
				gbc_btnPlaypause.insets = new Insets(0, 0, 0, 5);
				gbc_btnPlaypause.gridx = 3;
				gbc_btnPlaypause.gridy = 0;
				panel.add(btnPlaypause, gbc_btnPlaypause);
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
                                        double current = 0;
                                        try{
                                            current = songTotalLength - fis.available();
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
			{
				JButton btnSucc = new JButton("");
				GridBagConstraints gbc_btnSucc = new GridBagConstraints();
				gbc_btnSucc.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnSucc.anchor = GridBagConstraints.NORTH;
				gbc_btnSucc.insets = new Insets(0, 0, 0, 5);
				gbc_btnSucc.gridx = 4;
				gbc_btnSucc.gridy = 0;
				panel.add(btnSucc, gbc_btnSucc);
				btnSucc.setToolTipText("Brano successivo");
				btnSucc.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/forward_icon.png")));
			}
			btnRestart = new JButton("");
			GridBagConstraints gbc_btnRestart = new GridBagConstraints();
			gbc_btnRestart.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnRestart.anchor = GridBagConstraints.NORTH;
			gbc_btnRestart.insets = new Insets(0, 0, 0, 5);
			gbc_btnRestart.gridx = 5;
			gbc_btnRestart.gridy = 0;
			panel.add(btnRestart, gbc_btnRestart);
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
			{
				JButton btnLoop = new JButton("");
				GridBagConstraints gbc_btnLoop = new GridBagConstraints();
				gbc_btnLoop.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnLoop.gridwidth = 2;
				gbc_btnLoop.gridx = 6;
				gbc_btnLoop.gridy = 0;
				panel.add(btnLoop, gbc_btnLoop);
				btnLoop.setToolTipText("Loop");
				btnLoop.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/loop.png")));
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
		{
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
						btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/play_icon.png")));			
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
}
