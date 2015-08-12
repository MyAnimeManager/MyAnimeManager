package util.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.AnimeData;
import util.window.ReleaseNotifierDialog;

public class ReleasedAnimeTask extends SwingWorker
{

	@Override
	protected Object doInBackground() throws Exception
	{
		Date date = new Date();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/YYYY"); // the day of the week abbreviated
		String day = simpleDateformat.format(date);
		System.out.println(day);
		Object[] ovaArray = AnimeIndex.ovaModel.toArray();
		for (int i = 0; i < ovaArray.length; i++)
		{
			String name = (String) ovaArray[i];
			AnimeData data = AnimeIndex.ovaMap.get(name);
			String releaseDate = data.getReleaseDate();
			if (day.equalsIgnoreCase(releaseDate))
				ReleaseNotifierDialog.ovaReleased.addElement(name);
		}
		
		Object[] filmArray = AnimeIndex.filmModel.toArray();
		for (int i = 0; i < ovaArray.length; i++)
		{
			String name = (String) ovaArray[i];
			AnimeData data = AnimeIndex.filmMap.get(name);
			String releaseDate = data.getReleaseDate();
			if (day.equalsIgnoreCase(releaseDate))
				ReleaseNotifierDialog.filmReleased.addElement(name);
		}
		
		ReleaseNotifierDialog.ovaReleasedList.repaint();
		ReleaseNotifierDialog.filmReleasedList.repaint();
		return null;
	}

}
