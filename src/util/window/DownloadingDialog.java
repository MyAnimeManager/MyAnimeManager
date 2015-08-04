package util.window;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import util.task.DownloadUpdateTask;

public class DownloadingDialog extends JDialog
{

	DownloadUpdateTask task = new DownloadUpdateTask();
	private JProgressBar progressBar;
	private JLabel label;
	/**
	 * Create the dialog.
	 */
	public DownloadingDialog(String link)
	{
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
			        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			        task.execute();
			}
			});	
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setTitle("Scaricando Aggiornamento...");
		setBounds(100, 100, 328, 76);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		String labelString = "Scaricando...";
		label = new JLabel(labelString);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridwidth = 5;
		gbc_label.fill = GridBagConstraints.HORIZONTAL;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		getContentPane().add(label, gbc_label);
		
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.gridwidth = 5;
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.insets = new Insets(0, 0, 0, 5);
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 1;
		getContentPane().add(progressBar, gbc_progressBar);

		
		
}	
}