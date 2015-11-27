package util;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;


public class DriveQuickstart {
	
    /** Global instance of the scopes required by this quickstart. */
    private static final List<String> SCOPES =
        Arrays.asList(DriveScopes.DRIVE);

//TODO Spostare in metodi di utilità google drive
    
//    private static InputStream downloadFile(Drive service, File file) {
//        if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
//          try {
//            // uses alt=media query parameter to request content
//            return service.files().get(file.getId()).executeMediaAsInputStream();
//          } catch (IOException e) {
//            // An error occurred.
//            e.printStackTrace();
//            return null;
//          }
//        }
//		// The file doesn't have any content stored on Drive.
//          return null;
//      }
    
    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     */
	public static Drive getDriveService(URI secretKeyFile) throws GeneralSecurityException,
	IOException {
	HttpTransport httpTransport = new NetHttpTransport();
	JacksonFactory jsonFactory = new JacksonFactory();
	
	GoogleCredential credential = new GoogleCredential.Builder()
	  .setTransport(httpTransport)
	  .setJsonFactory(jsonFactory)
	  .setServiceAccountId("account-1@my-anime-manager.iam.gserviceaccount.com")
	  .setServiceAccountScopes(SCOPES)
	  .setServiceAccountPrivateKeyFromP12File(
	      new java.io.File(secretKeyFile))
	  .build();
	Drive service = new Drive.Builder(httpTransport, jsonFactory, null)
	  .setHttpRequestInitializer(credential).setApplicationName("appl name").build();
	return service;
	}

    public static void main(String[] args) throws IOException, URISyntaxException {
        // Build a new authorized API client service.
        Drive service = null;
		try
		{
			service = getDriveService(new URI("C:\\Users\\Samu\\Downloads\\My Anime Manager-ab416509d5a5.p12"));
		}
		catch (GeneralSecurityException e)
		{
			// TODO Auto-generated catch block
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}

        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list()
             .setMaxResults(10)
             .execute();
        List<File> files = result.getItems();
        if (files == null || files.size() == 0) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getTitle(), file.getId());
//                FileUtils.copyInputStreamToFile(downloadFile(service, file), new java.io.File("C:\\Users\\Samu\\Dekstop\\prova.txt"));
            }
        }
        
        System.out.println("fine");
    }

}

