package util;

import java.io.File;

import javax.swing.filechooser.FileFilter;
 
/* ImageFilter.java is used by FileChooserDemo2.java. */
public class ImageChooserFilter extends FileFilter {
 
    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
 
        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals("tiff") || extension.equals("tif") || extension.equals("gif") || extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png")) 
            {
            	return true;
            }
			return false;
        }
        return false;
    }
 
    //The description of this filter
    public String getDescription() {
        return "Tutte le Immagini";
    }
    
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
 
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}

