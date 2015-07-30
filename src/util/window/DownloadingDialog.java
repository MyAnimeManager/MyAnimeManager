package util.window;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import main.AnimeIndex;

import org.apache.commons.io.FileUtils;

import util.FileManager;

public class DownloadingDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;

	/**
	 * Create the dialog.
	 */
	public DownloadingDialog(int bytes, URL url)
	{
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("Downloading...");
		setModal(true);
		setBounds(100, 100, 240, 93);
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
			
		{
			float kBytes = bytes / 1024;
			lblNewLabel = new JLabel("Sto scaricando " + kBytes + " KiloByte");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			JProgressBar progressBar = new JProgressBar();
			progressBar.setIndeterminate(true);
			GridBagConstraints gbc_progressBar = new GridBagConstraints();
			gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
			gbc_progressBar.gridx = 0;
			gbc_progressBar.gridy = 1;
			contentPanel.add(progressBar, gbc_progressBar);
		}
		
		this.setLocationRelativeTo(AnimeIndex.mainFrame);
	    this.setVisible(true);
		File file = new File(FileManager.getAppDataPath() + "Update" + File.separator + "My Anime Manager Updated.exe");
	    try {
			FileUtils.copyURLToFile(url, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.out.println("Finito");
	    this.dispose();
	}

}
