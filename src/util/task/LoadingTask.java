package util.task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import javax.swing.SwingWorker;
import javax.swing.Timer;

import main.AnimeIndex;
import util.DriveUtil;
import util.FileManager;
import util.MAMUtil;
import util.window.AnimeInformation;

public class LoadingTask extends SwingWorker
{

	@SuppressWarnings("deprecation")
	@Override
	protected Object doInBackground() throws Exception
	{
		FileManager.loadFansubList();
		AnimeInformation.setFansubComboBox();
		FileManager.loadExclusionList();
		
		File completed = new File(MAMUtil.getAnimeFolderPath() + "completed.JConda");
		if (completed.isFile())
			FileManager.loadAnimeGson("completed.JConda", AnimeIndex.completedModel, AnimeIndex.completedMap);
		else
		FileManager.loadAnime("completed.anaconda", AnimeIndex.completedModel, AnimeIndex.completedMap);
		
		File airing = new File(MAMUtil.getAnimeFolderPath() + "airing.JConda");
		if (airing.isFile())
			FileManager.loadAnimeGson("airing.JConda", AnimeIndex.airingModel, AnimeIndex.airingMap);
		else
		FileManager.loadAnime("airing.anaconda", AnimeIndex.airingModel, AnimeIndex.airingMap);
		
		File ova = new File(MAMUtil.getAnimeFolderPath() + "ova.JConda");
		if (ova.isFile())
			FileManager.loadAnimeGson("ova.JConda", AnimeIndex.ovaModel, AnimeIndex.ovaMap);
		else
		FileManager.loadAnime("ova.anaconda", AnimeIndex.ovaModel, AnimeIndex.ovaMap);
		
		File film = new File(MAMUtil.getAnimeFolderPath() + "film.JConda");
		if (film.isFile())
			FileManager.loadAnimeGson("film.JConda", AnimeIndex.filmModel, AnimeIndex.filmMap);
		else
		FileManager.loadAnime("film.anaconda", AnimeIndex.filmModel, AnimeIndex.filmMap);
		
		File toSee = new File(MAMUtil.getAnimeFolderPath() + "toSee.JConda");
		if (toSee.isFile())
			FileManager.loadAnimeGson("toSee.JConda", AnimeIndex.completedToSeeModel, AnimeIndex.completedToSeeMap);
		else
		FileManager.loadAnime("toSee.anaconda", AnimeIndex.completedToSeeModel, AnimeIndex.completedToSeeMap);
		
		FileManager.loadSpecialList("wishlist.anaconda", AnimeIndex.wishlistMap, AnimeIndex.wishlistDialog.wishListModel);
		FileManager.loadSpecialList("wishlistMAL.anaconda", AnimeIndex.wishlistMALMap, AnimeIndex.wishlistDialog.wishListModel);
		FileManager.loadSpecialList("droplist.anaconda", AnimeIndex.droppedMALMap, AnimeIndex.wishlistDialog.dropListModel);
		FileManager.loadSpecialList("droplistMAL.anaconda", AnimeIndex.droppedMap, AnimeIndex.wishlistDialog.dropListModel);
		FileManager.loadDateMap();
		FileManager.loadPatternList();
		return null;
	}

	@Override
	protected void done()
	{
		AnimeIndex.completedModel.update();
		AnimeIndex.airingModel.update();
		AnimeIndex.ovaModel.update();
		AnimeIndex.filmModel.update();
		AnimeIndex.completedToSeeModel.update();

		if (AnimeIndex.appProp.getProperty("Open_Wishlist").equalsIgnoreCase("true"))
		{
			AnimeIndex.wishlistDialog.setLocation(AnimeIndex.mainPanel.getLocationOnScreen().x, AnimeIndex.mainPanel.getLocationOnScreen().y);
			AnimeIndex.wishlistDialog.setVisible(true);
			new Timer(1, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e)
				{
					AnimeIndex.wishlistDialog.setLocation(AnimeIndex.wishlistDialog.getLocationOnScreen().x - 1, AnimeIndex.mainPanel.getLocationOnScreen().y);
					AnimeIndex.mainPanel.requestFocus();
					if (AnimeIndex.wishlistDialog.getLocationOnScreen().x == AnimeIndex.mainPanel.getLocationOnScreen().x - 181)
						((Timer) e.getSource()).stop();
				}
			}).start();
		}

		if (AnimeIndex.appProp.getProperty("Open_NewsBoard").equalsIgnoreCase("true"))
			if (!AnimeIndex.newsBoardDialog.isShowing())
			{
				AnimeIndex.newsBoardDialog.setLocation(AnimeIndex.mainPanel.getLocationOnScreen().x - 1, AnimeIndex.mainPanel.getLocationOnScreen().y + AnimeIndex.mainPanel.getHeight());
				AnimeIndex.newsBoardDialog.setVisible(true);
				new Timer(1, new ActionListener() {

					int size = 0;

					@Override
					public void actionPerformed(ActionEvent e)
					{
						AnimeIndex.mainPanel.requestFocus();
						AnimeIndex.newsBoardDialog.setSize(795, size++);
						if (AnimeIndex.newsBoardDialog.getHeight() == 125)
							((Timer) e.getSource()).stop();
					}
				}).start();
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
