package util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;

public class DriveUtil
{

	/** Global instance of the scopes required by this quickstart. */
	private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);
	private static final String FOLDER_MIME = "application/vnd.google-apps.folder";
	public static Drive service;

	/**
	 * Build and return an authorized Drive client service.
	 *
	 * @return an authorized Drive client service
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void getDriveService() throws GeneralSecurityException, IOException, URISyntaxException
	{
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();

		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(jsonFactory).setServiceAccountId("account-1@my-anime-manager.iam.gserviceaccount.com").setServiceAccountScopes(SCOPES).setServiceAccountPrivateKeyFromP12File(new java.io.File(DriveUtil.class.getResource("/My Anime Manager drive.p12").toURI())).build();
		Drive service = new Drive.Builder(httpTransport, jsonFactory, null).setHttpRequestInitializer(credential).setApplicationName("My Anime Manager").build();
		DriveUtil.service = service;
	}

	public static TreeMap<String, String> getAllChildren() throws Exception
	{
		TreeMap<String, String> fileParentMap = new TreeMap<String, String>();
		FileList result = service.files().list().execute();
		List<File> files = result.getItems();
		if (files == null || files.size() == 0)
			System.out.println("No files found.");
		else
			for (File file : files)
				if (!file.getMimeType().equalsIgnoreCase(FOLDER_MIME))
				{
					String title = file.getTitle();
					String name = title.substring(0, title.length() - 4);
					fileParentMap.put(name, getFirstParentName(file));
				}
		return fileParentMap;
	}

	public static TreeMap<String, ArrayList<String>> getMusicFolderChildren() throws IOException
	{
		TreeMap<String, ArrayList<String>> fileParentMap = new TreeMap<String, ArrayList<String>>();
		String musicFolderId = getFileByName("Musica").getId();
		ChildList result = service.children().list(musicFolderId).execute();
		List<ChildReference> children = result.getItems();

		if (children == null || children.size() == 0)
			System.out.println("No files found.");
		else
			for (ChildReference child : children)
			{
				ArrayList<String> childList = new ArrayList<String>();
				ChildList resultSubFolder = service.children().list(child.getId()).execute();
				List<ChildReference> childrenSubFolder = resultSubFolder.getItems();
				for (ChildReference childSubFolder : childrenSubFolder)
				{
					String childSubFolderName = service.files().get(childSubFolder.getId()).execute().getTitle();
					childList.add(childSubFolderName.substring(0, childSubFolderName.length() - 4));
				}
				String childName = service.files().get(child.getId()).execute().getTitle();
				fileParentMap.put(childName, childList);

			}
		return fileParentMap;
	}

	private static String getFirstParentName(File file) throws IOException
	{
		List<ParentReference> parentList = file.getParents();
		ParentReference parentRef = parentList.get(0);
		String parentId = parentRef.getId();
		File parentFile = service.files().get(parentId).execute();
		String parentName = parentFile.getTitle();
		return parentName;
	}

	public static File getFileByName(String fileName) throws IOException
	{
		FileList result = service.files().list().setQ("title = '" + fileName + "'").execute();
		List<File> files = result.getItems();
		if (files == null || files.size() == 0)
			System.out.println("No files found.");
		else
			for (File file : files)
				return file;
				
		return null;
	}
}
