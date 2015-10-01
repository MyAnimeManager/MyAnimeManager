package util.task;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;

import main.AnimeIndex;
import util.FileManager;
import util.Updater;
import util.window.UpdateDialog;

public class DownloadUpdateTask extends SwingWorker
{    
	@Override
	protected Void doInBackground() throws Exception
	{
		downloadFile(Updater.getDownloadLink());
		return null;
	}

	private void downloadFile(String link) throws MalformedURLException, IOException
    {
		File file = new File(FileManager.getAppDataPath() + File.separator + "Update" + File.separator + AnimeIndex.NEW_VERSION);
		URL url = new URL(link);
		FileUtils.copyURLToFile(url, file);
    }
	
	@Override
	protected void done()
	{
		UpdateDialog.dial.dispose();
	}
	
}
