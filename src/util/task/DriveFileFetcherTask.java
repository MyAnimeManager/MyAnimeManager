package util.task;

import java.util.TreeMap;

import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.DriveUtil;


public class DriveFileFetcherTask extends SwingWorker {
	
	private TreeMap<String,String> map;
	@Override
	protected Object doInBackground() throws Exception
	{
		map = DriveUtil.getAllChildren();
		return null;
	}
	
	protected void done()
	{
		AnimeIndex.musicDialog.songsMap = map;
	}
	
}
