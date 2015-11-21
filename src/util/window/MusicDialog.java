package util.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.AnimeIndex;

import org.apache.commons.io.FileUtils;

import javazoom.jl.player.advanced.AdvancedPlayer;
import util.MAMUtil;
import util.task.MusicTask;

public class MusicDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	public static AdvancedPlayer player;
	private boolean loopActive;
	private JLabel lblImage;
	private JButton btnPlaypause;
	private FileInputStream fis;
	private BufferedInputStream buff;
	private boolean isRunning;
	private boolean isPaused;
	private String currentMusicPath;
	private long pauseLocation;
	private long songTotalLength;
	
	public MusicDialog()
	{
		//super(AnimeIndex.frame, false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MusicDialog.class.getResource("/image/Headp.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				BufferedImage image = null;
				try
				{
					image = ImageIO.read(ClassLoader.getSystemResource("image/Headphone.png"));
				}
				catch (IOException e1)
				{
					MAMUtil.writeLog(e1);
					e1.printStackTrace();
				}
				lblImage.setIcon(new ImageIcon(image));
			}
			@Override
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});
		setTitle("My Anime Musics");
		setResizable(false);
		setFont(MAMUtil.segui().deriveFont(12f));
		setBounds(100, 100, 534, 448);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{87, 93, 27, 114, 33, 65, 38, 0};
		gbl_contentPanel.rowHeights = new int[]{354, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.gridwidth = 2;
			gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 0;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				JTree tree = new JTree();
				tree.setFont(MAMUtil.segui().deriveFont(12f));
				scrollPane.setViewportView(tree);
			}
		}
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridwidth = 5;
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 2;
			gbc_panel.gridy = 0;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{70, 195, 70, 0};
			gbl_panel.rowHeights = new int[]{14, 335, 14, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel lblTitle = new JLabel("title");
				lblTitle.setFont(MAMUtil.segui().deriveFont(12f));
				GridBagConstraints gbc_lblTitle = new GridBagConstraints();
				gbc_lblTitle.gridwidth = 3;
				gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
				gbc_lblTitle.gridx = 0;
				gbc_lblTitle.gridy = 0;
				panel.add(lblTitle, gbc_lblTitle);
			}
			{
				lblImage = new JLabel("");
				lblImage.setBorder(new LineBorder(new Color(40, 40, 40), 2, true));
				GridBagConstraints gbc_lblImage = new GridBagConstraints();
				gbc_lblImage.gridwidth = 3;
				gbc_lblImage.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblImage.insets = new Insets(0, 0, 5, 0);
				gbc_lblImage.gridx = 0;
				gbc_lblImage.gridy = 1;
				panel.add(lblImage, gbc_lblImage);
			}
			{
				JProgressBar progressBar = new JProgressBar();
				GridBagConstraints gbc_progressBar = new GridBagConstraints();
				gbc_progressBar.anchor = GridBagConstraints.NORTH;
				gbc_progressBar.gridwidth = 3;
				gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
				gbc_progressBar.gridx = 0;
				gbc_progressBar.gridy = 2;
				panel.add(progressBar, gbc_progressBar);
			}
		}
		{
			JButton btnLoad = new JButton("Ascolta");
			btnLoad.setToolTipText("Carica il brano per ascoltarlo");
			GridBagConstraints gbc_btnLoad = new GridBagConstraints();
			gbc_btnLoad.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnLoad.insets = new Insets(0, 0, 0, 5);
			gbc_btnLoad.gridx = 0;
			gbc_btnLoad.gridy = 1;
			contentPanel.add(btnLoad, gbc_btnLoad);
		}
		{
			JButton btnSave = new JButton("Salva");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					File choosedFile = new File("C:\\Users\\Denis\\Music\\♫OpEd Musics♫\\Lucky☆Star\\Motteke! Sailor Fuku.mp3");
					JFileChooser chooser = new JFileChooser(System.getProperty("user.home") + File.separator + "Desktop");
					chooser.setMultiSelectionEnabled(false);
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					chooser.setDialogTitle("Salva in...");
					
					int returnVal = chooser.showDialog(AnimeIndex.mainFrame, "Salva");
					
					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						File destination = chooser.getSelectedFile();
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
			});
			btnSave.setToolTipText("Salva i brani che preferisci");
			GridBagConstraints gbc_btnSave = new GridBagConstraints();
			gbc_btnSave.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnSave.insets = new Insets(0, 0, 0, 5);
			gbc_btnSave.gridx = 1;
			gbc_btnSave.gridy = 1;
			contentPanel.add(btnSave, gbc_btnSave);
		}
		{
			btnPlaypause = new JButton("");
			btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/play_icon.png")));
			btnPlaypause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!isRunning && !isPaused)
					{
						play("C:\\Users\\Denis\\Music\\♫OpEd Musics♫\\Lucky☆Star\\Motteke! Sailor Fuku.mp3");
						isRunning=true;
						btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/pause_icon.png")));
					}
					else if(!isPaused)
					{
						pause();
						isRunning=false;
						isPaused=true;
						btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/play_icon.png")));
					}
					else if(!isRunning && isPaused)
					{
						resume();
						isRunning=true;
						isPaused=false;
						btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/pause_icon.png")));
					}
				}
			});
			{
				JButton btnPrev = new JButton("");
				btnPrev.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/rev_icon.png")));
				GridBagConstraints gbc_btnPrev = new GridBagConstraints();
				gbc_btnPrev.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnPrev.insets = new Insets(0, 0, 0, 5);
				gbc_btnPrev.gridx = 2;
				gbc_btnPrev.gridy = 1;
				contentPanel.add(btnPrev, gbc_btnPrev);
			}
			GridBagConstraints gbc_btnPlaypause = new GridBagConstraints();
			gbc_btnPlaypause.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnPlaypause.insets = new Insets(0, 0, 0, 5);
			gbc_btnPlaypause.gridx = 3;
			gbc_btnPlaypause.gridy = 1;
			contentPanel.add(btnPlaypause, gbc_btnPlaypause);
		}
		{
			{
				JButton btnSucc = new JButton("");
				btnSucc.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/forward_icon.png")));
				GridBagConstraints gbc_btnSucc = new GridBagConstraints();
				gbc_btnSucc.insets = new Insets(0, 0, 0, 5);
				gbc_btnSucc.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnSucc.gridx = 4;
				gbc_btnSucc.gridy = 1;
				contentPanel.add(btnSucc, gbc_btnSucc);
			}
		}
		JButton btnRestart = new JButton("");
		btnRestart.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/restart.png")));
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
				play(currentMusicPath);
				isRunning=true;
				isPaused=false;
				btnPlaypause.setIcon(new ImageIcon(MusicDialog.class.getResource("/image/pause_icon.png")));
			}
		});
		GridBagConstraints gbc_btnRestart = new GridBagConstraints();
		gbc_btnRestart.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRestart.insets = new Insets(0, 0, 0, 5);
		gbc_btnRestart.gridx = 5;
		gbc_btnRestart.gridy = 1;
		contentPanel.add(btnRestart, gbc_btnRestart);
		{
			JButton btnLoop = new JButton("");
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
			GridBagConstraints gbc_btnLoop = new GridBagConstraints();
			gbc_btnLoop.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnLoop.gridx = 6;
			gbc_btnLoop.gridy = 1;
			contentPanel.add(btnLoop, gbc_btnLoop);
		}
	}
	private void play(String path)
	{
		
		try{
			fis = new FileInputStream(path);
			buff = new BufferedInputStream(fis);
		    player = new AdvancedPlayer(buff);
		    currentMusicPath = path+"";
		    songTotalLength=fis.available();
		    run();
			}
			catch(Exception exc){
				MAMUtil.writeLog(exc);
			    exc.printStackTrace();
			}
	}
	private void pause()
	{
		try
		{
			pauseLocation = fis.available();
			player.close();
			if(fis!=null)
				fis.close();
			if(buff!=null)
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
		try{
			fis = new FileInputStream(currentMusicPath);
			buff = new BufferedInputStream(fis);
		    player = new AdvancedPlayer(buff);
		    fis.skip(songTotalLength-pauseLocation);
		    run();
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
		try
		{
			if(fis!=null)
				fis.close();
			if(buff!=null)
				buff.close();
		}
		catch (IOException e)
		{
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
	}
	private void run()
	{
		MusicTask tsk = new MusicTask();
		tsk.execute();
	}
}
