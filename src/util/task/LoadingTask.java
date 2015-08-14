package util.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.FileManager;

public class LoadingTask extends SwingWorker
{

	@Override
	protected Object doInBackground() throws Exception
	{
		FileManager.loadExclusionList();
		FileManager.loadAnime("completed.txt" , AnimeIndex.completedModel, AnimeIndex.completedMap);
		FileManager.loadAnime("airing.txt", AnimeIndex.airingModel, AnimeIndex.airingMap);
		FileManager.loadAnime("ova.txt", AnimeIndex.ovaModel, AnimeIndex.ovaMap);
		FileManager.loadAnime("film.txt", AnimeIndex.filmModel, AnimeIndex.filmMap);
		FileManager.loadAnime("toSee.txt", AnimeIndex.completedToSeeModel, AnimeIndex.completedToSeeMap);
		FileManager.loadWishList();
		AnimeIndex.completedList.repaint();
		AnimeIndex.airingList.repaint();
		AnimeIndex.ovaList.repaint();
		AnimeIndex.filmList.repaint();
		AnimeIndex.completedToSeeList.repaint();
		return null;
	}

	protected void done()
	{
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
