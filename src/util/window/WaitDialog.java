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
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

public class WaitDialog extends JDialog
{

	private JProgressBar progressBar;
	private JLabel lblDownloadInCorso;

	/**
	 * Create the dialog.
	 */
	public WaitDialog(String title, String text, SwingWorker task, JDialog parent, boolean taskHasProgress)
	{
		super(parent, true);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e)
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				task.execute();
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setTitle(title);
		setBounds(100, 100, 328, 85);

		getContentPane().setLayout(new MigLayout("", "[320.00px]", "[14px][14px]"));
		lblDownloadInCorso = new JLabel(text);
		lblDownloadInCorso.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblDownloadInCorso, "cell 0 0,growx,aligny center");

		progressBar = new JProgressBar();
		progressBar.setIndeterminate(!taskHasProgress);
		getContentPane().add(progressBar, "cell 0 1,growx,aligny center");

		task.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if (evt.getPropertyName().equals("progress"))
				{
					int progress = task.getProgress();
					progressBar.setValue(progress);
				}
				else if (evt.getPropertyName().equals("state"))
					if (evt.getNewValue().toString().equalsIgnoreCase("done"))
						WaitDialog.this.dispose();

			}
		});

	}
	
	public WaitDialog(String title, String text, SwingWorker task, boolean taskHasProgress)
	{
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e)
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				task.execute();
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setTitle(title);
		setBounds(100, 100, 328, 85);

		getContentPane().setLayout(new MigLayout("", "[320.00px]", "[14px][14px]"));
		lblDownloadInCorso = new JLabel(text);
		lblDownloadInCorso.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblDownloadInCorso, "cell 0 0,growx,aligny center");

		progressBar = new JProgressBar();
		progressBar.setIndeterminate(!taskHasProgress);
		getContentPane().add(progressBar, "cell 0 1,growx,aligny center");

		task.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				
				if (evt.getPropertyName().equals("progress"))
				{
					int progress = task.getProgress();
					progressBar.setValue(progress);
				}
				else if (evt.getPropertyName().equals("state"))
					if (evt.getNewValue().toString().equalsIgnoreCase("done"))
						WaitDialog.this.dispose();

			}
		});

	}
}