package util.task;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import util.DriveQuickstart;


public class GoogleDriveDownload extends SwingWorker {
	
	JProgressBar bar;
	String link;
	
	protected GoogleDriveDownload()
	{
//		Drive service = DriveQuickstart.getDriveService();
//		 FileList result = service.files().list()
//	             .setMaxResults(10)
//	             .execute();
//		downloadFile(service, file);
	}
	
	public GoogleDriveDownload(String link, JProgressBar barToUpdate)
	{
		this.link = link;
		this.bar = barToUpdate;
	}
	@Override
	protected Object doInBackground() throws Exception
	{
		return null;
	}
	
	private InputStream downloadFile(Drive service, File file) {
        if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
            try {
                HttpResponse resp =
                        service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl()))
                                .execute();
                return resp.getContent();
            } catch (IOException e) {
                // An error occurred.
                e.printStackTrace();
                return null;
            }
        } else {
            // The file doesn't have any content stored on Drive.
            return null;
        }
    }
}
