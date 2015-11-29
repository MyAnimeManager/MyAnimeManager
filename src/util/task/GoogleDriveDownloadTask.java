package util.task;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.SwingWorker;

import com.google.api.services.drive.model.File;

import util.DriveUtil;
import util.MAMUtil;


public class GoogleDriveDownloadTask extends SwingWorker {
	
	public double fileSize;
	private String fileName;
	
	
	public GoogleDriveDownloadTask(String fileName)
	{
		this.fileName = fileName;
	}
	
	@Override
	protected Object doInBackground() throws Exception
	{
		if (fileName != null)
			downloadFileWithStream();
		return null;
	}
	
	private InputStream downloadFile() throws IOException
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
	
	private void downloadFileWithStream()
	{
		try
		{
			InputStream is = downloadFile();
			download(is, System.getProperty("user.home") + java.io.File.separator + "Desktop" + java.io.File.separator + fileName);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}

	}

}
