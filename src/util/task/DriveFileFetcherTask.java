package util.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.swing.SwingWorker;

import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import main.AnimeIndex;
import util.DriveUtil;

public class DriveFileFetcherTask extends SwingWorker
{

	private TreeMap<String, ArrayList<String>> map;
	public double albumNumber;
	public double count;

	@Override
	protected Object doInBackground() throws Exception
	{
		map = getMusicFolderChildren();
		return null;
	}

	@Override
	protected void done()
	{
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
			count = 0;
			albumNumber = children.size();
			for (File child : children)
			{
				String childName = child.getTitle();
				System.out.println(childName);
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
				int progress = (int)((count/albumNumber) * 100);
				setProgress(progress);
			}
		}
		return fileParentMap;
	}

}
