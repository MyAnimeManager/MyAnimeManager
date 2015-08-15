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
import main.AnimeIndex;
import net.miginfocom.swing.MigLayout;

import java.awt.Toolkit;

public class DownloadingDialog extends JDialog
{

	DownloadUpdateTask task = new DownloadUpdateTask();
	private JProgressBar progressBar;
	private JLabel lblDownloadInCorso;
	/**
	 * Create the dialog.
	 */
	public DownloadingDialog(String link)
	{
		super(AnimeIndex.frame,true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DownloadingDialog.class.getResource("/image/Update.png")));
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
		setTitle("Download Aggiornamento...");
		setBounds(100, 100, 328, 76);
		
		String labelString = "Scaricando...";
		getContentPane().setLayout(new MigLayout("", "[320.00px]", "[14px][14px]"));
		lblDownloadInCorso = new JLabel("Download in corso...");
		lblDownloadInCorso.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblDownloadInCorso, "cell 0 0,growx,aligny center");
		
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		getContentPane().add(progressBar, "cell 0 1,growx,aligny center");

		
		
}	
}