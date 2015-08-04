package util.task;

import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.Updater;
import util.window.UpdateDialog;

public class CheckUpdateTask extends SwingWorker
{
//	String updatedVersion;
	@Override
	public String doInBackground() throws Exception
	{
		String updatedVersion = null;
		try {
			updatedVersion = Updater.getLatestVersion();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
			return updatedVersion;
	}
	
	@Override
	public void done()
	{
		String updatedVersion = null;
		try {
			updatedVersion = (String) get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {

			e.printStackTrace();
		}
		if (!updatedVersion.equalsIgnoreCase(AnimeIndex.VERSION))
			{
			UpdateDialog update = new UpdateDialog(Updater.getWhatsNew());
			update.setLocationRelativeTo(AnimeIndex.mainFrame);
			update.setVisible(true);
			}
	}

}
