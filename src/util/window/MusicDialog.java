package util.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import util.MAMUtil;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class MusicDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JLabel lblImage;
	
	public MusicDialog()
	{
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
					e1.printStackTrace();
				}
				lblImage.setIcon(new ImageIcon(image));
			}
		});
		setTitle("My Anime Musics");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setFont(MAMUtil.segui().deriveFont(12f));
		setBounds(100, 100, 534, 439);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{87, 93, 71, 193, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{354, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
			gbc_panel.gridwidth = 3;
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 2;
			gbc_panel.gridy = 0;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{70, 195, 70, 0};
			gbl_panel.rowHeights = new int[]{14, 335, 14, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel lblTitle = new JLabel("title");
				lblTitle.setFont(MAMUtil.segui().deriveFont(12f));
				GridBagConstraints gbc_lblTitle = new GridBagConstraints();
				gbc_lblTitle.gridwidth = 3;
				gbc_lblTitle.fill = GridBagConstraints.HORIZONTAL;
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
				gbc_progressBar.gridwidth = 3;
				gbc_progressBar.fill = GridBagConstraints.BOTH;
				gbc_progressBar.gridx = 0;
				gbc_progressBar.gridy = 2;
				panel.add(progressBar, gbc_progressBar);
			}
		}
		{
			JButton btnLoad = new JButton("Ascolta");
			GridBagConstraints gbc_btnLoad = new GridBagConstraints();
			gbc_btnLoad.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnLoad.insets = new Insets(0, 0, 0, 5);
			gbc_btnLoad.gridx = 0;
			gbc_btnLoad.gridy = 1;
			contentPanel.add(btnLoad, gbc_btnLoad);
		}
		{
			JButton btnSave = new JButton("Salva");
			GridBagConstraints gbc_btnSave = new GridBagConstraints();
			gbc_btnSave.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnSave.insets = new Insets(0, 0, 0, 5);
			gbc_btnSave.gridx = 1;
			gbc_btnSave.gridy = 1;
			contentPanel.add(btnSave, gbc_btnSave);
		}
		{
			JLabel lblCurrentTime = new JLabel("current time");
			GridBagConstraints gbc_lblCurrentTime = new GridBagConstraints();
			gbc_lblCurrentTime.insets = new Insets(0, 0, 0, 5);
			gbc_lblCurrentTime.gridx = 2;
			gbc_lblCurrentTime.gridy = 1;
			contentPanel.add(lblCurrentTime, gbc_lblCurrentTime);
		}
		{
			JButton btnPlaypause = new JButton("play/pause");
			GridBagConstraints gbc_btnPlaypause = new GridBagConstraints();
			gbc_btnPlaypause.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnPlaypause.insets = new Insets(0, 0, 0, 5);
			gbc_btnPlaypause.gridx = 3;
			gbc_btnPlaypause.gridy = 1;
			contentPanel.add(btnPlaypause, gbc_btnPlaypause);
		}
		{
			JLabel lblTotalTime = new JLabel("total time");
			GridBagConstraints gbc_lblTotalTime = new GridBagConstraints();
			gbc_lblTotalTime.gridx = 4;
			gbc_lblTotalTime.gridy = 1;
			contentPanel.add(lblTotalTime, gbc_lblTotalTime);
		}
	}
	
}
