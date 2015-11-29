package util;

import java.awt.Component;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import util.window.MusicDialog;

public class JTreeIcons extends DefaultTreeCellRenderer {
	
	private static final String MUSICS_PATH = System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
	private ImageIcon present = new ImageIcon(MusicDialog.class.getResource("/image/accept.png"));
	private ImageIcon absent = new ImageIcon(MusicDialog.class.getResource("/image/DeleteRed.png"));
	private ImageIcon notAll = new ImageIcon(MusicDialog.class.getResource("/image/notAll.png"));

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        
        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)value;
        if(leaf)
    	{
    		if(new File(MUSICS_PATH+currentNode.getUserObject()+".mp3").isFile())
    			setIcon(present);
    		else
    			setIcon(absent);
    	}
        else
    	{
    		int albumChildNum = currentNode.getChildCount();
    		int count = 0;
    		for(int j=0; j<albumChildNum; j++)
    		{
    			currentNode = (DefaultMutableTreeNode)currentNode.getChildAt(j);
    			if(new File(MUSICS_PATH+currentNode.getUserObject()+".mp3").exists())
    			{
    				count++;
    			}
    		}
    		System.out.println(albumChildNum);
    		if(count == albumChildNum)
    		{
    			setIcon(present);
    		}
    		else if(count == 0)
    		{
    			setIcon(absent);
    		}
    		else
    		{
    			setIcon(notAll);
    		}

    	}
        return this;
    }
}
