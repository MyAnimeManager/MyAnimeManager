package util.task;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;

import util.FileManager;
import util.Updater;
import util.window.UpdateDialog;

public class DownloadUpdateTask extends SwingWorker
{

    public static final String NEW_VERSION = "MyAnimeManager_v1.0.0_Setup.exe";

    
	@Override
	protected Void doInBackground() throws Exception
	{
		downloadFile(Updater.getDownloadLink());
		return null;
	}

	private void downloadFile(String link) throws MalformedURLException, IOException
    {
		File file = new File(FileManager.getAppDataPath() + File.separator + "Update" + File.separator + NEW_VERSION);
		URL url = new URL(link);
		FileUtils.copyURLToFile(url, file);
    }
	
	@Override
	protected void done()
	{
		UpdateDialog.dial.dispose();
	}
	
}
