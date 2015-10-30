package util.window;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import main.AnimeIndex;
import net.miginfocom.swing.MigLayout;
import util.task.SuggestionFetcherTask;

public class SuggestionWaitDialog extends JDialog
{

	SuggestionFetcherTask task = new SuggestionFetcherTask();
	private JProgressBar progressBar;
	private JLabel lblDownloadInCorso;
	/**
	 * Create the dialog.
	 */
	public SuggestionWaitDialog()
	{
		super(AnimeIndex.frame,true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SuggestionWaitDialog.class.getResource("/image/Update.png")));
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
		setTitle("Scarico dati...");
		setBounds(100, 100, 328, 85);
		
		getContentPane().setLayout(new MigLayout("", "[320.00px]", "[14px][14px]"));
		lblDownloadInCorso = new JLabel("Ricezione dati in corso...");
		lblDownloadInCorso.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblDownloadInCorso, "cell 0 0,growx,aligny center");
		
		progressBar = new JProgressBar(0,100);
		progressBar.setIndeterminate(true);
		getContentPane().add(progressBar, "cell 0 1,growx,aligny center");		
}	
}