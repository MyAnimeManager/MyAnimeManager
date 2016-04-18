package util;

import java.io.File;

import javax.swing.filechooser.FileFilter;


public class EpisodeChooserFilter extends FileFilter
{
	@Override
	public boolean accept(File f)
	{
		if (f.isDirectory())
			return true;

		String extension = getExtension(f);
		if (extension != null)
		{
			if (extension.equals("mkv") || extension.equals("mp4") || extension.equals("vlc"))
				return true;
			return false;
		}
		return false;
	}

	// The description of this filter
	@Override
	public String getDescription()
	{
		return "Tutte i video";
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
