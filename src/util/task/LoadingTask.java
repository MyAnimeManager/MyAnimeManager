package util.task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.SwingWorker;
import javax.swing.Timer;

import main.AnimeIndex;
import util.FileManager;

public class LoadingTask extends SwingWorker
{

	@Override
	protected Object doInBackground() throws Exception
	{
		FileManager.loadExclusionList();
		FileManager.loadAnime("completed.anaconda" , AnimeIndex.completedModel, AnimeIndex.completedMap);
		FileManager.loadAnime("airing.anaconda", AnimeIndex.airingModel, AnimeIndex.airingMap);
		FileManager.loadAnime("ova.anaconda", AnimeIndex.ovaModel, AnimeIndex.ovaMap);
		FileManager.loadAnime("film.anaconda", AnimeIndex.filmModel, AnimeIndex.filmMap);
		FileManager.loadAnime("toSee.anaconda", AnimeIndex.completedToSeeModel, AnimeIndex.completedToSeeMap);
		FileManager.loadWishList();
		return null;
	}

	protected void done()
	{
		AnimeIndex.completedModel.update();
		AnimeIndex.airingModel.update();
		AnimeIndex.ovaModel.update();
		AnimeIndex.filmModel.update();
		AnimeIndex.completedToSeeModel.update();
		
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
			
		String dataRelease = AnimeIndex.appProp.getProperty("Date_Release");	 	
		if(dataRelease.equalsIgnoreCase("none"))
		{
			ReleasedAnimeTask task = new ReleasedAnimeTask();
			task.execute();
		}
		else
		{
			Date date = new Date();
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/YYYY"); // the day of the week abbreviated
			String day = simpleDateformat.format(date);
			GregorianCalendar calendar = new GregorianCalendar();
			try {
				calendar.setTime(simpleDateformat.parse(day));
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			GregorianCalendar c = new GregorianCalendar();
			try {
				c.setTime(simpleDateformat.parse(dataRelease));
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			if(c.before(calendar))
			{
				ReleasedAnimeTask task = new ReleasedAnimeTask();
				task.execute();
			}
		}
	}
}
