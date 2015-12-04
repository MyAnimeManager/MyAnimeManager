package util.window;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import main.AnimeIndex;
import net.miginfocom.swing.MigLayout;
import util.task.ManualUpdateAnimeDataTask;

public class UpdatingAnimeDataDialog extends JDialog
{

	private JProgressBar progressBar;
	private JLabel lblControlloInCorso;

	/**
	 * Create the dialog.
	 */
	public UpdatingAnimeDataDialog(boolean[] exclusion)
	{
		super(AnimeIndex.frame, true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(UpdatingAnimeDataDialog.class.getResource("/image/refresh-icon15.png")));
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e)
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				ManualUpdateAnimeDataTask task = new ManualUpdateAnimeDataTask(exclusion);
				task.execute();
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setTitle("Controllo Dati...");
		setBounds(100, 100, 328, 76);

		getContentPane().setLayout(new MigLayout("", "[320.00px]", "[14px][14px]"));
		lblControlloInCorso = new JLabel("Controllo Dati in corso...");
		lblControlloInCorso.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblControlloInCorso, "cell 0 0,growx,aligny center");

		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		getContentPane().add(progressBar, "cell 0 1,growx,aligny center");

	}
}
