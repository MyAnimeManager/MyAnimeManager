package util.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.MAMUtil;
import util.Updater;

public class DownloadUpdateTask extends SwingWorker
{

	public int totalSize;
	public int currentSize = 0;

	@Override
	protected Void doInBackground() throws Exception
	{
		downloadFile(Updater.getDownloadLink());
		return null;
	}

	private void downloadFile(String link) throws MalformedURLException, IOException
	{
		String file = MAMUtil.getAppDataPath() + File.separator + "Update" + File.separator + AnimeIndex.NEW_VERSION;
		URL url = new URL(link);
		// FileUtils.copyURLToFile(url, file);
		downloadUpdate(url, file);
	}

	@Override
	protected void done()
	{
		// UpdateDialog.dial.dispose();
	}

	public void downloadUpdate(URL fileUrl, String destinationFile)
	{
		// URL url;
		try
		{
			// url = new URL(fileUrl);
			URLConnection conn = fileUrl.openConnection();
			conn.setRequestProperty("User-Agent", "My Anime Index");
			totalSize = conn.getContentLength();
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(destinationFile);

			byte[] b = new byte[4096];
			int length;
			this.setProgress(0);

			while ((length = is.read(b)) != -1)
			{
				os.write(b, 0, length);
				currentSize += length;
				setProgress((int) (((double) currentSize / (double) totalSize) * 100));
			}

			is.close();
			os.close();
		}
		catch (FileNotFoundException e)
		{
			File file = new File(MAMUtil.getAppDataPath() + "Update" + File.separator);
			file.mkdirs();
			downloadUpdate(fileUrl, destinationFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			MAMUtil.writeLog(e);
		}
	}
}
