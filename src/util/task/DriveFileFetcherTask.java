package util.task;

import java.util.TreeMap;

import javax.swing.SwingWorker;

import util.DriveUtil;


public class DriveFileFetcherTask extends SwingWorker {
	
	@Override
	protected Object doInBackground() throws Exception
	{
		TreeMap<String,String> map = DriveUtil.getAllChildren();
		System.out.println(map.size());
		return null;
	}
	
}
