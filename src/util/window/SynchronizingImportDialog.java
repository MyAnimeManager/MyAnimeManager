package util.window;

import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import main.AnimeIndex;
import net.miginfocom.swing.MigLayout;
import util.task.MALSynchronizationTask;

public class SynchronizingImportDialog extends JDialog
{

	private JProgressBar progressBar;
	private JLabel lblSynchro;
	MALSynchronizationTask task;

	/**
	 * Create the dialog.
	 */	
	public SynchronizingImportDialog(String username)
	{
		super(AnimeIndex.frame, true);
		task = new MALSynchronizationTask(username);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e)
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				task.execute();
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setTitle("Sincronizzazione dati da MyAnimeList");
		setBounds(100, 100, 328, 85);

		getContentPane().setLayout(new MigLayout("", "[320.00px]", "[14px][14px]"));
		lblSynchro = new JLabel("Sincronizzazione in corso...");
		lblSynchro.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblSynchro, "cell 0 0,growx,aligny center");

		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		progressBar.setString("Ricezione dati...");
		getContentPane().add(progressBar, "cell 0 1,growx,aligny center");

		task.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				
				if (evt.getPropertyName().equals("progress"))
				{
					if (progressBar.isIndeterminate())
					{
						progressBar.setIndeterminate(false);
						progressBar.setMinimum(0);
						progressBar.setMaximum((int)task.totalAnimeNumber);
					}
					lblSynchro.setText("Sincronizzazione in corso...");
					progressBar.setString("Anime sincronizzati " + (int)task.currentAnimeNumber + "/" + (int)task.totalAnimeNumber);
					progressBar.setValue((int)task.currentAnimeNumber);
				}
				else if (evt.getPropertyName().equals("state"))
					if (evt.getNewValue().toString().equalsIgnoreCase("done"))
						SynchronizingImportDialog.this.dispose();

			}
		});

	}
}