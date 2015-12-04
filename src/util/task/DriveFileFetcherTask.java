package util.task;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.DriveUtil;

public class DriveFileFetcherTask extends SwingWorker
{

	private TreeMap<String, ArrayList<String>> map;

	@Override
	protected Object doInBackground() throws Exception
	{
		map = DriveUtil.getMusicFolderChildren();
		return null;
	}

	@Override
	protected void done()
	{
		AnimeIndex.musicDialog.songsMap = map;
	}

}
