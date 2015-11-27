package util;


import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;


public class DriveUtil {
	
    /** Global instance of the scopes required by this quickstart. */
    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);
    private static final String FOLDER_MIME = "application/vnd.google-apps.folder"; 
     
    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     * @throws URISyntaxException 
     */
	public static Drive getDriveService() throws GeneralSecurityException,
	IOException, URISyntaxException {
	HttpTransport httpTransport = new NetHttpTransport();
	JacksonFactory jsonFactory = new JacksonFactory();
	
	GoogleCredential credential = new GoogleCredential.Builder()
	  .setTransport(httpTransport)
	  .setJsonFactory(jsonFactory)
	  .setServiceAccountId("account-1@my-anime-manager.iam.gserviceaccount.com")
	  .setServiceAccountScopes(SCOPES)
	  .setServiceAccountPrivateKeyFromP12File(
	      new java.io.File(DriveUtil.class.getResource("/My Anime Manager drive.p12").toURI()))
	  .build();
	Drive service = new Drive.Builder(httpTransport, jsonFactory, null)
	  .setHttpRequestInitializer(credential).setApplicationName("appl name").build();
	return service;
	}
	
	public static TreeMap<String, String> getAllChildren(Drive service) throws Exception
	{
		TreeMap<String,String> fileParentMap = new TreeMap<String,String>();
		FileList result = service.files().list().execute();
		List<File> files = result.getItems();
		if (files == null || files.size() == 0) 
        {
            System.out.println("No files found.");
        } 
		else 
        {
            System.out.println("Files:");
            for (File file : files) {
            	if (!file.getMimeType().equalsIgnoreCase(FOLDER_MIME))
            	{
            		System.out.printf("%s (%s)\n", file.getTitle(), getFirstParentName(service, file));
            		fileParentMap.put(file.getTitle(), getFirstParentName(service, file));
            	}
            	else
            	{
            		System.out.printf("%s (%s)\n", file.getTitle(), file.getId());
            	}
            		
                

            }
        }
		return fileParentMap;
	}
	
	private static String getFirstParentName(Drive service, File file) throws IOException
	{
		List<ParentReference> parentList = file.getParents();
		ParentReference parentRef = parentList.get(0);
		String parentId = parentRef.getId();
		File parentFile = service.files().get(parentId).execute();
		String parentName = parentFile.getTitle();
		return parentName;
	}
	
	public static String getFileIdByName(Drive service, String fileName) throws IOException
	{
		System.out.println("inizio ricerca");
		FileList result = service.files().list().setQ("title = '" + fileName + "'").execute();
		List<File> files = result.getItems();
		if (files == null || files.size() == 0) 
        {
            System.out.println("No files found.");
        } 
		else 
        {
            for (File file : files) 
            {
            	return file.getId();
            }
        }
		
		return "File non trovato";
	}
}

