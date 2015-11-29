package util.task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import main.AnimeIndex;
import util.DriveUtil;
import util.FileManager;
import util.Filters;
import util.MAMUtil;

public class LoadingTask extends SwingWorker
{

	@Override
	protected Object doInBackground() throws Exception
	{
		FileManager.loadFansubList();
		AnimeIndex.animeInformation.setFansubComboBox();
		FileManager.loadExclusionList();
		FileManager.loadAnime("completed.anaconda" , AnimeIndex.completedModel, AnimeIndex.completedMap);
		FileManager.loadAnime("airing.anaconda", AnimeIndex.airingModel, AnimeIndex.airingMap);
		FileManager.loadAnime("ova.anaconda", AnimeIndex.ovaModel, AnimeIndex.ovaMap);
		FileManager.loadAnime("film.anaconda", AnimeIndex.filmModel, AnimeIndex.filmMap);
		FileManager.loadAnime("toSee.anaconda", AnimeIndex.completedToSeeModel, AnimeIndex.completedToSeeMap);
		FileManager.loadWishList();
		FileManager.loadDateMap();
		return null;
	}

	protected void done()
	{	
			AnimeIndex.completedModel.update();
			AnimeIndex.airingModel.update();
			AnimeIndex.ovaModel.update();
			AnimeIndex.filmModel.update();
			AnimeIndex.completedToSeeModel.update();
		
		if (AnimeIndex.appProp.getProperty("List_to_visualize_at_start").equalsIgnoreCase("Daily"))
		{
			Filters.setFilter(8);
		}
		
		if(AnimeIndex.appProp.getProperty("Open_Wishlist").equalsIgnoreCase("true"))
		{
			AnimeIndex.wishlistDialog.setLocation(AnimeIndex.mainFrame.getLocationOnScreen().x ,AnimeIndex.mainFrame.getLocationOnScreen().y);
			AnimeIndex.wishlistDialog.setVisible(true);
			 new Timer(1, new ActionListener() {
	               public void actionPerformed(ActionEvent e) {
	            	   AnimeIndex.wishlistDialog.setLocation(AnimeIndex.wishlistDialog.getLocationOnScreen().x - 1, AnimeIndex.mainFrame.getLocationOnScreen().y);
	            	   AnimeIndex.mainFrame.requestFocus();
	            	   if (AnimeIndex.wishlistDialog.getLocationOnScreen().x == AnimeIndex.mainFrame.getLocationOnScreen().x - 181) {
	                     ((Timer) e.getSource()).stop();
	            }
	               }
	            }).start();
		}
		
		if(AnimeIndex.appProp.getProperty("Open_NewsBoard").equalsIgnoreCase("true"))
		{
			if (!AnimeIndex.newsBoardDialog.isShowing())
			{
				AnimeIndex.newsBoardDialog.setLocation(AnimeIndex.mainFrame.getLocationOnScreen().x - 1,AnimeIndex.mainFrame.getLocationOnScreen().y + AnimeIndex.mainFrame.getHeight());
				AnimeIndex.newsBoardDialog.setVisible(true);
				 new Timer(1, new ActionListener() {
	            	   int size = 0;
		               public void actionPerformed(ActionEvent e) {
		            	   AnimeIndex.mainFrame.requestFocus();
		            	   AnimeIndex.newsBoardDialog.setSize(795, size++);
		            	   if (AnimeIndex.newsBoardDialog.getHeight() == 125) {
		                     ((Timer) e.getSource()).stop();
		            }
		               }
		            }).start();
			}
		}
		try
		{
			DriveUtil.getDriveService();
		}
		catch (GeneralSecurityException e)
		{
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
		catch (IOException e)
		{
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
		catch (URISyntaxException e)
		{
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
	}
}
