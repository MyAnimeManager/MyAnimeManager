package util.window;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import util.task.UpdateAnimeDataTask;

public class UpdatingAnimeDataDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	UpdateAnimeDataTask task = new UpdateAnimeDataTask();
	private JProgressBar progressBar;
	private JLabel lblControlloInCorso;

	/**
	 * Create the dialog.
	 */
	public UpdatingAnimeDataDialog() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(DownloadingDialog.class.getResource("/image/autorefresh-icon15.png")));
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
		setTitle("Controllo Dati...");
		setBounds(100, 100, 328, 76);
		
		String labelString = "Controllo Dati in corso...";
		getContentPane().setLayout(new MigLayout("", "[320.00px]", "[14px][14px]"));
		lblControlloInCorso = new JLabel("Controllo Dati in corso...");
		lblControlloInCorso.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblControlloInCorso, "cell 0 0,growx,aligny center");
		
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		getContentPane().add(progressBar, "cell 0 1,growx,aligny center");

	}
}
