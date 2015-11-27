package util.task;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.swing.SwingWorker;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import util.DriveQuickstart;
import util.MAMUtil;


public class GoogleDriveDownloadTask extends SwingWorker {
	
	public double fileSize;
	
	@Override
	protected Object doInBackground() throws Exception
	{
		downloadFileWithStream();
		return null;
	}
	
	private InputStream downloadFile() throws IOException, GeneralSecurityException, URISyntaxException 
	{
		
		Drive service = DriveQuickstart.getDriveService(GoogleDriveDownloadTask.class.getResource("/My Anime Manager drive.p12").toURI());
		
		FileList result = service.files().list()
	             .setMaxResults(10)
	             .execute();
	        List<File> files = result.getItems();
	        if (files == null || files.size() == 0) 
	        {
	            System.out.println("No files found.");
	        } 
	        else 
	        {
	            System.out.println("Files:");
	            for (File file : files) {
	                System.out.printf("%s (%s)\n", file.getTitle(), file.getId());
	                System.out.println("inizio");
	                
			        if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
			          try {
			        	  fileSize = file.getFileSize();
			            // uses alt=media query parameter to request content
			            return service.files().get(file.getId()).executeMediaAsInputStream();
			          } catch (IOException e) {
			            // An error occurred.
			            e.printStackTrace();
			            return null;
			          }
			        }
					// The file doesn't have any content stored on Drive.
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
			download(is, System.getProperty("user.home") + java.io.File.separator + "Desktop" + java.io.File.separator + "A genesis - nano.mp3");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}

	}

}
