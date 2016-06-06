package util;

import java.io.File;

import javax.swing.filechooser.FileFilter;


public class ImportExportFileFilter extends FileFilter
{
	
	@Override
	public boolean accept(File f)
	{
		if (f.isDirectory())
			return true;
		String extension = MAMUtil.getExtension(f);
		if (extension != null)
		{
			if (extension.equalsIgnoreCase(".zip"))
					return true;
			return false;
		}
		return false;
	}
	
	@Override
	public String getDescription()
	{
		return "File .zip";
	}
}
