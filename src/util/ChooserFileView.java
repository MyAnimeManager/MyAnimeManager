package util;

import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileView;

import main.AnimeIndex;
 
/* ImageFileView.java is used by FileChooserDemo2.java. */
public class ChooserFileView extends FileView {
	
    ImageIcon folderIcon = createImageIcon();
 
    public String getName(File f) {
        return null; //let the L&F FileView figure this out
    }
 
    public String getDescription(File f) {
        return null; //let the L&F FileView figure this out
    }
 
    public Boolean isTraversable(File f) {
        return null; //let the L&F FileView figure this out
    }
 
    public String getTypeDescription(File f) {
        String extension = getExtension(f);
        String type = null;
 
        if (extension != null) {
            if (extension.equals("jpeg") ||
                extension.equals("jpg")) {
                type = "JPEG Image";
            } else if (extension.equals("gif")){
                type = "GIF Image";
            } else if (extension.equals("tiff") ||
                       extension.equals("tif")) {
                type = "TIFF Image";
            } else if (extension.equals("png")){
                type = "PNG Image";
            }
        }
        return type;
    }
 
    public Icon getIcon(File f) 
    {	Icon icon = null;
    	if (f.isDirectory())
    		icon = folderIcon;
        return icon;
    }
    
    protected static ImageIcon createImageIcon() {
        java.net.URL imgURL = AnimeIndex.class.getResource("/image/folder.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } 
        else 
        {
            return null;
        }
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

