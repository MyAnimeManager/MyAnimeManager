package util.task;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import com.google.api.services.drive.model.File;

import util.DriveUtil;
import util.MAMUtil;


public class GoogleDriveDownloadTask extends SwingWorker {
	
	public double fileSize;
	private ArrayList<String> fileList;
	
	
	public GoogleDriveDownloadTask(String fileName)
	{
		ArrayList<String> fileList = new ArrayList<String>();
		fileList.add(fileName);
		this.fileList = fileList;
	}
 
	public GoogleDriveDownloadTask(ArrayList<String> fileList)
	{
		this.fileList = fileList;
	}
	
	@Override
	protected Object doInBackground() throws Exception
	{
		for (String fileName : fileList)
		if (fileName != null)
			downloadFileWithStream(fileName);
		return null;
	}
	
	private InputStream downloadFile(String fileName) throws IOException
	{
		File file = DriveUtil.getFileByName(fileName);
        System.out.printf("%s (%s)\n", file.getTitle(), file.getId());
        System.out.println("inizio");
        
        if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) 
        {
		      try {
		      fileSize = file.getFileSize();
		      return DriveUtil.service.files().get(file.getId()).executeMediaAsInputStream();
		      } 
		      catch (IOException e) {
		        e.printStackTrace();
		        return null;
		      }
        }
	        
    	return null;
    }
	
	private void download(InputStream is, String destinationFile) {
		try {
			double currentSize = 0;
		    OutputStream os = new FileOutputStream(destinationFile);

			    byte[] b = new byte[4096];
			    int length;
			    this.setProgress(0);
			    
			    while ((length = is.read(b)) != -1) {
			        os.write(b, 0, length);
			        currentSize += length;
			        setProgress((int)((currentSize/fileSize) * 100));
			    }

			    is.close();
			    os.close();
		}
			catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void downloadFileWithStream(String fileName)
	{
		try
		{
			InputStream is = downloadFile(fileName);
			download(is, System.getProperty("user.home") + java.io.File.separator + "Desktop" + java.io.File.separator + fileName +".mp3");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}

	}

}
