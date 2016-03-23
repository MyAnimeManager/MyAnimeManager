package util;

import java.io.File;

import javax.swing.filechooser.FileFilter;


public class ImportExportFileFilter extends FileFilter
{
	
	@Override
	public boolean accept(File f)
	{
		if (f.isDirectory())
			return false;
		String extension = getExtension(f);
		if (extension != null)
		{
			if (extension.equalsIgnoreCase("zip"))
					return true;
			return false;
		}
		return false;
	}
	
	@Override
	public String getDescription()
	{
		return "File zip";
	}
	
	private String getExtension(File f)
	{
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1)
			ext = s.substring(i + 1).toLowerCase();
		return ext;
	}
}
