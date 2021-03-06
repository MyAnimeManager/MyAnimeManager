package util.task;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import javax.swing.SwingWorker;
import javax.swing.UIManager;

import main.AnimeIndex;
import util.AnimeIndexProperties;
import util.ColorProperties;
import util.DriveUtil;
import util.FileManager;
import util.MAMUtil;
import util.window.WishlistDialog;

public class LoadingTask extends SwingWorker
{

	@SuppressWarnings("deprecation")
	@Override
	protected Object doInBackground() throws Exception
	{
		
		try
		{
	
			setProgress(0);
			MAMUtil.createLogDirectory();
			setProgress((int)((1f/20f)* 100));
			AnimeIndex.appProp = AnimeIndexProperties.createProperties();
			setProgress((int)((2f/20f)* 100));
			AnimeIndex.colorProp = ColorProperties.createProperties();		
			setProgress((int)((3f/20f)* 100));		
			ColorProperties.setColor(AnimeIndex.colorProp);
			setProgress((int)((4f/20f)* 100));		
			AnimeIndex.segui = MAMUtil.loadFont();
			setProgress((int)((5f/20f)* 100));			
			
			UIManager.put("OptionPane.messageFont", AnimeIndex.segui.deriveFont(11f));
			
			FileManager.loadFansubList();
			setProgress((int)((6f/20f)* 100));

			setProgress((int)((7f/20f)* 100));
			File exclusion = new File(MAMUtil.getAnimeFolderPath() + "exclusion.JConda");
			if (exclusion.isFile())
				FileManager.loadExclusionListGson();
			else
				FileManager.loadExclusionList();
			setProgress((int)((8f/20f)* 100));

			File completed = new File(MAMUtil.getAnimeFolderPath() + "completed.JConda");
			if (completed.isFile())
				FileManager.loadAnimeGson("completed.JConda", AnimeIndex.completedModel, AnimeIndex.completedMap);
			else
				FileManager.loadAnime("completed.anaconda", AnimeIndex.completedModel, AnimeIndex.completedMap);
			setProgress((int)((9f/20f)* 100));
			
			File airing = new File(MAMUtil.getAnimeFolderPath() + "airing.JConda");
			if (airing.isFile())
				FileManager.loadAnimeGson("airing.JConda", AnimeIndex.airingModel, AnimeIndex.airingMap);
			else
				FileManager.loadAnime("airing.anaconda", AnimeIndex.airingModel, AnimeIndex.airingMap);
			setProgress((int)((10f/20f)* 100));
			
			File ova = new File(MAMUtil.getAnimeFolderPath() + "ova.JConda");
			if (ova.isFile())
				FileManager.loadAnimeGson("ova.JConda", AnimeIndex.ovaModel, AnimeIndex.ovaMap);
			else
				FileManager.loadAnime("ova.anaconda", AnimeIndex.ovaModel, AnimeIndex.ovaMap);
			setProgress((int)((11f/20f)* 100));
			
			File film = new File(MAMUtil.getAnimeFolderPath() + "film.JConda");
			if (film.isFile())
				FileManager.loadAnimeGson("film.JConda", AnimeIndex.filmModel, AnimeIndex.filmMap);
			else
				FileManager.loadAnime("film.anaconda", AnimeIndex.filmModel, AnimeIndex.filmMap);
			setProgress((int)((12f/20f)* 100));
			
			File toSee = new File(MAMUtil.getAnimeFolderPath() + "toSee.JConda");
			if (toSee.isFile())
				FileManager.loadAnimeGson("toSee.JConda", AnimeIndex.completedToSeeModel, AnimeIndex.completedToSeeMap);
			else
				FileManager.loadAnime("toSee.anaconda", AnimeIndex.completedToSeeModel, AnimeIndex.completedToSeeMap);
			setProgress((int)((13f/20f)* 100));
			
			File wishlist = new File(MAMUtil.getAnimeFolderPath() + "wishlist.JConda");
			if (wishlist.isFile())
				FileManager.loadSpecialListGson("wishlist.JConda", AnimeIndex.wishlistMap, WishlistDialog.wishListModel);
			else
				FileManager.loadSpecialList("wishlist.anaconda", AnimeIndex.wishlistMap, WishlistDialog.wishListModel);
			setProgress((int)((14f/20f)* 100));
			
			File wishlistMAL = new File(MAMUtil.getAnimeFolderPath() + "wishlistMAL.JConda");
			if (wishlistMAL.isFile())
				FileManager.loadSpecialListGson("wishlistMAL.JConda", AnimeIndex.wishlistMALMap, WishlistDialog.wishListModel);
			else
				FileManager.loadSpecialList("wishlistMAL.anaconda", AnimeIndex.wishlistMALMap, WishlistDialog.wishListModel);
			setProgress((int)((15f/20f)* 100));
			
			File droplist = new File(MAMUtil.getAnimeFolderPath() + "droplist.JConda");
			if (droplist.isFile())
				FileManager.loadSpecialListGson("droplist.JConda", AnimeIndex.droppedMALMap, WishlistDialog.dropListModel);
			else
				FileManager.loadSpecialList("droplist.anaconda", AnimeIndex.droppedMALMap, WishlistDialog.dropListModel);
			setProgress((int)((16f/20f)* 100));
			
			File droplistMAL = new File(MAMUtil.getAnimeFolderPath() + "droplistMAL.JConda");
			if (droplistMAL.isFile())
				FileManager.loadSpecialListGson("droplistMAL.JConda", AnimeIndex.droppedMap, WishlistDialog.dropListModel);
			else 
				FileManager.loadSpecialList("droplistMAL.anaconda", AnimeIndex.droppedMap, WishlistDialog.dropListModel);
			
			setProgress((int)((17f/20f)* 100));
			File dateMap = new File(MAMUtil.getAnimeFolderPath() + "date.JConda");
			if (dateMap.isFile())
				FileManager.loadDateMapGson();
			else
				FileManager.loadDateMap();
			setProgress((int)((18f/20f)* 100));
			FileManager.loadPatternList();
			setProgress((int)((19f/20f)* 100));
			
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
			setProgress((int)((20f/20f)* 100));
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
