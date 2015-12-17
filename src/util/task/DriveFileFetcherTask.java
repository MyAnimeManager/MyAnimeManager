package util.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.swing.SwingWorker;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import main.AnimeIndex;
import util.DriveUtil;
import util.MAMUtil;

public class DriveFileFetcherTask extends SwingWorker
{

	private TreeMap<String, ArrayList<String>> map;
	public double albumNumber;
	public double count;

	@Override
	protected Object doInBackground() throws Exception
	{
		if (DriveUtil.service == null)
		{
			DriveUtil.getDriveService();
		}
		String date = AnimeIndex.appProp.getProperty("Last_Music_Check");
		java.io.File musicFile = new java.io.File(MAMUtil.getMusicPath() + "[[[music]]].anaconda");
		boolean forceCheck = getForceCheck();
		if (!musicFile.exists() || forceCheck || date.equalsIgnoreCase("null"))
		{
			map = getMusicFolderChildren();
		}
		else
			updateMusicList(date);
		return null;
	}

	@Override
	protected void done()
	{
		if(map!=null)
			AnimeIndex.musicDialog.songsMap.putAll(map);
	}

	private TreeMap<String, ArrayList<String>> getMusicFolderChildren() throws IOException
	{
		TreeMap<String, ArrayList<String>> fileParentMap = new TreeMap<String, ArrayList<String>>();
		FileList request = DriveUtil.service.files().list().setQ("mimeType = 'application/vnd.google-apps.folder' and title != 'Musica'").execute();
		List<File> children = request.getItems();
		if (children == null || children.size() == 0)
			System.out.println("No files found.");
		else
		{
			count = 1;
			albumNumber = children.size();
			for (File child : children)
			{
				String childName = child.getTitle();
				ArrayList<String> childList = new ArrayList<String>();
				ChildList resultSubFolder = DriveUtil.service.children().list(child.getId()).execute();
				List<ChildReference> childrenSubFolder = resultSubFolder.getItems();
				for (ChildReference childSubFolder : childrenSubFolder)
				{
					String childSubFolderName = DriveUtil.service.files().get(childSubFolder.getId()).execute().getTitle();
					childList.add(childSubFolderName.substring(0, childSubFolderName.length() - 4));
				}
				childList.sort(String.CASE_INSENSITIVE_ORDER);
				fileParentMap.put(childName, childList);
				count++;
				int progress = (int)(((count - 1)/albumNumber) * 100);
				setProgress(progress);
			}
		}
		
		return fileParentMap;
	}
	

	private void updateMusicList(String date) throws IOException
	{
		ArrayList<String> newFolderSong = new ArrayList<String>();
		//cartelle
		FileList requestFolder = DriveUtil.service.files().list().setQ("mimeType = 'application/vnd.google-apps.folder' and modifiedDate >= '" + date + "'").execute();
		List<File> newFolderList = requestFolder.getItems();
		if (newFolderList == null || newFolderList.size() == 0)
			System.out.println("No files found.");
		else
		{
			count = 1;
			albumNumber = newFolderList.size();
			for (File newFolder : newFolderList)
			{
				String albumName = newFolder.getTitle();
				ArrayList<String> list = new ArrayList<String>();
				ChildList resultSubFolder = DriveUtil.service.children().list(newFolder.getId()).execute();
				List<ChildReference> childrenSubFolder = resultSubFolder.getItems();
				for (ChildReference childSubFolder : childrenSubFolder)
				{
					String childSubFolderName = DriveUtil.service.files().get(childSubFolder.getId()).execute().getTitle();
					newFolderSong.add(childSubFolderName);
					list.add(childSubFolderName.substring(0, childSubFolderName.length() - 4));
				}
				list.sort(String.CASE_INSENSITIVE_ORDER);
				AnimeIndex.musicDialog.songsMap.put(albumName, list);
				count++;
				int progress = (int)(((count - 1)/albumNumber) * 100);
				setProgress(progress);
			}
		}
		//canzoni singole
		FileList request = DriveUtil.service.files().list().setQ("mimeType != 'application/vnd.google-apps.folder' and modifiedDate >= '" + date + "'").execute();
		List<File> newSongsList = request.getItems();
		if (newSongsList == null || newSongsList.size() == 0)
			System.out.println("No files found.");
		else
		{
			count = 1;
			albumNumber = newSongsList.size();
			for (File newSong : newSongsList)
			{
				String songName = newSong.getTitle();
				if (!newFolderSong.contains(songName))
				{
					String albumName = DriveUtil.getFirstParentName(newSong);
					if (AnimeIndex.musicDialog.songsMap.containsKey(albumName))
					{
						ArrayList<String> list = AnimeIndex.musicDialog.songsMap.get(albumName);
						list.add(songName.substring(0, songName.length()-4));
						list.sort(String.CASE_INSENSITIVE_ORDER);
						AnimeIndex.musicDialog.songsMap.put(albumName, list);
					}
					else
					{
						ArrayList<String> list = new ArrayList<String>();
						list.add(songName.substring(0, songName.length()-4));
						list.sort(String.CASE_INSENSITIVE_ORDER);
						AnimeIndex.musicDialog.songsMap.put(albumName, list);
					}
				}
				count++;
				int progress = (int)(((count - 1)/albumNumber) * 100);
				setProgress(progress);
			}
		}	
	}
	
	private boolean getForceCheck()
	{
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		BufferedReader rr; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		String address = "http://myanimemanagerupdate.webstarts.com/music.html";
		try
		{
			url = new URL(address);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			rr = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((line = rr.readLine()) != null)
				result += line;
			rr.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			MAMUtil.writeLog(e);
		}
		result = StringEscapeUtils.unescapeJava(result);
		String shouldReset = result.substring(result.indexOf("[resetList]") + 11, result.indexOf("[/resetList]"));
		if (!shouldReset.equalsIgnoreCase(AnimeIndex.appProp.getProperty("Reset_Music_List")))
		{
			AnimeIndex.appProp.setProperty("Reset_Music_List", shouldReset);
			return true;
		}
		return false;	
	}
}
