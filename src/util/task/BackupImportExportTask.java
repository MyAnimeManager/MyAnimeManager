package util.task;

import java.io.File;
import java.util.LinkedHashMap;

import javax.swing.SwingWorker;

import util.FileManager;
import util.MAMUtil;


public class BackupImportExportTask extends SwingWorker
{

	public static final int EXPORT_MODE = 0;
	public static final int IMPORT_MODE = 1;
	
	private int mode;
	private File dest;
	private LinkedHashMap<File,String> map;
	private File zipFile;
	
	public BackupImportExportTask(File dest, LinkedHashMap<File,String> map)
	{
		this.mode = EXPORT_MODE;
		this.dest = dest;
		this.map = map;
	}
	public BackupImportExportTask(File zipFile)
	{
		this.mode = IMPORT_MODE;
		this.zipFile = zipFile;
	}
	
	@Override
	protected Object doInBackground() throws Exception
	{
		if (mode == EXPORT_MODE)
			FileManager.createZip(dest, map);
		else if (mode == IMPORT_MODE)
			FileManager.extractZip(new File(MAMUtil.getAppDataPath()), zipFile);
		return null;
	}
	
}
