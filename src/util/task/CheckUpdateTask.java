package util.task;

import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.MAMUtil;
import util.Updater;
import util.window.UpdateDialog;

public class CheckUpdateTask extends SwingWorker
{

	@Override
	public String doInBackground() throws Exception
	{
		String updatedVersion = null;
		try
		{
			updatedVersion = Updater.getLatestVersion();
		}
		catch (java.net.ConnectException | java.net.UnknownHostException e1)
		{
			e1.printStackTrace();
			MAMUtil.writeLog(e1);
		}
		return updatedVersion;
	}

	@Override
	public void done()
	{
		String updatedVersion = null;
		try
		{
			updatedVersion = (String) get();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			MAMUtil.writeLog(e);
		}
		catch (ExecutionException e)
		{

			e.printStackTrace();
			MAMUtil.writeLog(e);
		}
		if (!updatedVersion.equalsIgnoreCase(AnimeIndex.VERSION))
		{
			UpdateDialog update = new UpdateDialog(Updater.getWhatsNew());
			update.setLocationRelativeTo(AnimeIndex.mainPanel);
			update.setVisible(true);
		}
	}

}
