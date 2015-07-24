package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
            	try {
					BufferedImage image = ImageIO.read(f);
					int width = image.getWidth ();
					int height = image.getHeight ();
					
					if ( width == 225 && height == 310)
						return true;
					else
						return false;								
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}  
            } 
            else 
            {
                return false;
            }
        }
 
        return false;
    }
 
    //The description of this filter
    public String getDescription() {
        return "Immagini adatte";
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

