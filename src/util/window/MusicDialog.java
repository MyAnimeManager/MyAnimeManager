package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.MAMUtil;

import java.awt.Font;
import java.awt.Dialog.ModalityType;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.JTree;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JProgressBar;


public class MusicDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	
	public MusicDialog()
	{
		setTitle("My Anime Musics");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setFont(MAMUtil.segui().deriveFont(12f));
		setBounds(100, 100, 533, 442);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{87, 93, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{354, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
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
				scrollPane.setViewportView(tree);
			}
		}
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridheight = 2;
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 2;
			gbc_panel.gridy = 0;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{70, 195, 70, 0};
			gbl_panel.rowHeights = new int[]{14, 335, 14, 23, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel lblTitle = new JLabel("title");
				GridBagConstraints gbc_lblTitle = new GridBagConstraints();
				gbc_lblTitle.gridwidth = 3;
				gbc_lblTitle.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
				gbc_lblTitle.gridx = 0;
				gbc_lblTitle.gridy = 0;
				panel.add(lblTitle, gbc_lblTitle);
			}
			{
				JLabel lblImage = new JLabel("image");
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
				gbc_progressBar.insets = new Insets(0, 0, 5, 5);
				gbc_progressBar.gridx = 0;
				gbc_progressBar.gridy = 2;
				panel.add(progressBar, gbc_progressBar);
			}
			{
				JLabel lblCurrentTime = new JLabel("current time");
				GridBagConstraints gbc_lblCurrentTime = new GridBagConstraints();
				gbc_lblCurrentTime.insets = new Insets(0, 0, 0, 5);
				gbc_lblCurrentTime.gridx = 0;
				gbc_lblCurrentTime.gridy = 3;
				panel.add(lblCurrentTime, gbc_lblCurrentTime);
			}
			{
				JButton btnPlaypause = new JButton("play/pause");
				GridBagConstraints gbc_btnPlaypause = new GridBagConstraints();
				gbc_btnPlaypause.insets = new Insets(0, 0, 0, 5);
				gbc_btnPlaypause.fill = GridBagConstraints.BOTH;
				gbc_btnPlaypause.gridx = 1;
				gbc_btnPlaypause.gridy = 3;
				panel.add(btnPlaypause, gbc_btnPlaypause);
			}
			{
				JLabel lblTotalTime = new JLabel("total time");
				GridBagConstraints gbc_lblTotalTime = new GridBagConstraints();
				gbc_lblTotalTime.gridx = 2;
				gbc_lblTotalTime.gridy = 3;
				panel.add(lblTotalTime, gbc_lblTotalTime);
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
	}
	
}
