package util.task;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import main.AnimeIndex;
import util.AnimeIndexProperties;
import util.ColorProperties;
import util.DriveUtil;
import util.FileManager;
import util.MAMUtil;
import util.window.NewsBoardDialog;
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

			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run()
				{
					AnimeIndex.wishlistDialog = new WishlistDialog();
				    AnimeIndex.newsBoardDialog = new NewsBoardDialog();			
				}
			});
			setProgress((int)((7f/20f)* 100));
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
			
			FileManager.loadSpecialList("wishlist.anaconda", AnimeIndex.wishlistMap, AnimeIndex.wishlistDialog.wishListModel);
			setProgress((int)((14f/20f)* 100));
			FileManager.loadSpecialList("wishlistMAL.anaconda", AnimeIndex.wishlistMALMap, AnimeIndex.wishlistDialog.wishListModel);
			setProgress((int)((15f/20f)* 100));
			FileManager.loadSpecialList("droplist.anaconda", AnimeIndex.droppedMALMap, AnimeIndex.wishlistDialog.dropListModel);
			setProgress((int)((16f/20f)* 100));
			FileManager.loadSpecialList("droplistMAL.anaconda", AnimeIndex.droppedMap, AnimeIndex.wishlistDialog.dropListModel);
			setProgress((int)((17f/20f)* 100));
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
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void done()
	{
//		AnimeIndex.completedModel.update();
//		AnimeIndex.airingModel.update();
//		AnimeIndex.ovaModel.update();
//		AnimeIndex.filmModel.update();
//		AnimeIndex.completedToSeeModel.update();

//		if (AnimeIndex.appProp.getProperty("Open_Wishlist").equalsIgnoreCase("true"))
//		{
//			AnimeIndex.wishlistDialog.setLocation(AnimeIndex.mainPanel.getLocationOnScreen().x, AnimeIndex.mainPanel.getLocationOnScreen().y);
//			AnimeIndex.wishlistDialog.setVisible(true);
//			new Timer(1, new ActionListener() {
//
//				@Override
//				public void actionPerformed(ActionEvent e)
//				{
//					AnimeIndex.wishlistDialog.setLocation(AnimeIndex.wishlistDialog.getLocationOnScreen().x - 1, AnimeIndex.mainPanel.getLocationOnScreen().y);
//					AnimeIndex.mainPanel.requestFocus();
//					if (AnimeIndex.wishlistDialog.getLocationOnScreen().x == AnimeIndex.mainPanel.getLocationOnScreen().x - 181)
//						((Timer) e.getSource()).stop();
//				}
//			}).start();
//		}
//
//		if (AnimeIndex.appProp.getProperty("Open_NewsBoard").equalsIgnoreCase("true"))
//			if (!AnimeIndex.newsBoardDialog.isShowing())
//			{
//				AnimeIndex.newsBoardDialog.setLocation(AnimeIndex.mainPanel.getLocationOnScreen().x - 1, AnimeIndex.mainPanel.getLocationOnScreen().y + AnimeIndex.mainPanel.getHeight());
//				AnimeIndex.newsBoardDialog.setVisible(true);
//				new Timer(1, new ActionListener() {
//
//					int size = 0;
//
//					@Override
//					public void actionPerformed(ActionEvent e)
//					{
//						AnimeIndex.mainPanel.requestFocus();
//						AnimeIndex.newsBoardDialog.setSize(795, size++);
//						if (AnimeIndex.newsBoardDialog.getHeight() == 125)
//							((Timer) e.getSource()).stop();
//					}
//				}).start();
//			}
//		try
//		{
//			DriveUtil.getDriveService();
//		}
//		catch (GeneralSecurityException e)
//		{
//			MAMUtil.writeLog(e);
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			MAMUtil.writeLog(e);
//			e.printStackTrace();
//		}
//		catch (URISyntaxException e)
//		{
//			MAMUtil.writeLog(e);
//			e.printStackTrace();
//		}
	}
}
