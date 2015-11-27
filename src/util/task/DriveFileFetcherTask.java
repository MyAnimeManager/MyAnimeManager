package util.task;

import java.util.TreeMap;

import javax.swing.SwingWorker;

import com.google.api.services.drive.Drive;

import util.DriveUtil;


public class DriveFileFetcherTask extends SwingWorker {
	
	@Override
	protected Object doInBackground() throws Exception
	{
		Drive service = DriveUtil.getDriveService();
		TreeMap<String,String> map = DriveUtil.getAllChildren(service);
		System.out.println(map.size());
		return null;
	}
	
}
