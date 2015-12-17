package util.task;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;

import com.google.api.services.drive.model.File;

import util.DriveUtil;
import util.MAMUtil;

public class GoogleDriveDownloadTask extends SwingWorker
{

	public double fileSize;
	public int fileNumber;
	public int totalFileNumber;
	private ArrayList<String> fileList;

	public GoogleDriveDownloadTask(String fileName)
	{
		if (DriveUtil.service == null)
		{
			try
			{
				DriveUtil.getDriveService();
			}
			catch (GeneralSecurityException | IOException | URISyntaxException e)
			{
				MAMUtil.writeLog(e);
				e.printStackTrace();
			}
		}
		ArrayList<String> fileList = new ArrayList<String>();
		fileList.add(fileName);
		this.fileList = fileList;
		this.totalFileNumber = 1;
	}

	public GoogleDriveDownloadTask(ArrayList<String> fileList)
	{
		ArrayList<String> fileToDownloadList = new ArrayList<String>();
		int fileToDownload = 0;
		for (String fileName : fileList)
		{
			java.io.File song = new java.io.File(MAMUtil.getMusicPath() + fileName + ".mp3");
			if (!song.exists())
			{
				fileToDownloadList.add(fileName);
				fileToDownload++;
			}
		}
		this.fileList = fileToDownloadList;
		this.totalFileNumber = fileToDownload;
	}

	@Override
	protected Object doInBackground() throws Exception
	{
		fileNumber = 1;
		for (String fileName : fileList)
		{
			if (!isCancelled())
			{
			if (fileName != null)
				downloadFileWithStream(fileName + ".mp3");
			setProgress(0);
			if (fileNumber < totalFileNumber)
				fileNumber++;
			}
			else
				return null;
		}
		return null;
	}

	private InputStream downloadFile(String fileName) throws IOException
	{
		File file = DriveUtil.getFileByName(fileName);

		if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0)
			try
			{
				fileSize = file.getFileSize();
				return DriveUtil.service.files().get(file.getId()).executeMediaAsInputStream();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				MAMUtil.writeLog(e);
				return null;
			}

		return null;
	}

	private void download(InputStream is, String destinationFile)
	{
		try
		{
			double currentSize = 0;
			OutputStream os = new FileOutputStream(destinationFile);

			byte[] b = new byte[4096];
			int length;
			this.setProgress(0);

			while ((length = is.read(b)) != -1)
			{
				os.write(b, 0, length);
				currentSize += length;
				setProgress((int) ((currentSize / fileSize) * 100));
			}

			is.close();
			os.close();
		}
		catch (FileNotFoundException e)
		{
			java.io.File file = new java.io.File(MAMUtil.getTempDownloadPath());
			file.mkdirs();
			download(is, destinationFile);
		}
		catch (Exception e)
		{
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
	}

	private void downloadFileWithStream(String fileName)
	{
		try
		{
			InputStream is = downloadFile(fileName);
			download(is, MAMUtil.getTempDownloadPath() + fileName);
			FileUtils.moveFile(new java.io.File(MAMUtil.getTempDownloadPath() + fileName), new java.io.File(MAMUtil.getMusicPath() + fileName));
		}
		catch (Exception e)
		{
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}

	}

}
