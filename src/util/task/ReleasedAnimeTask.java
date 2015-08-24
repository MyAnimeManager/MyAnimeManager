package util.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.AnimeData;
import util.window.ReleaseNotifierDialog;

public class ReleasedAnimeTask extends SwingWorker
{
	public static boolean enableFilm;
	public static boolean enableOav;
	
	@Override
	protected Object doInBackground() throws Exception
	{
		ReleaseNotifierDialog.ovaReleased.clear();
		ReleaseNotifierDialog.filmReleased.clear();
		Date date = new Date();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/YYYY"); // the day of the week abbreviated
		String day = simpleDateformat.format(date);
		Object[] ovaArray = AnimeIndex.ovaModel.toArray();
		for (int i = 0; i < ovaArray.length; i++)
		{
			String name = (String) ovaArray[i];
			AnimeData data = AnimeIndex.ovaMap.get(name);
			String releaseDate = "";
			if(data.getCurrentEpisode().equals(data.getTotalEpisode()) || Integer.parseInt(data.getCurrentEpisode())==(Integer.parseInt(data.getTotalEpisode())-1))
				releaseDate = data.getFinishDate();
			else
				releaseDate = data.getReleaseDate();
			if (day.equalsIgnoreCase(releaseDate))
				ReleaseNotifierDialog.ovaReleased.addElement(name);
		}

		Object[] filmArray = AnimeIndex.filmModel.toArray();
		for (int i = 0; i < filmArray.length; i++)
		{
			String name = (String) filmArray[i];
			AnimeData data = AnimeIndex.filmMap.get(name);
			String releaseDate = "";
			if(data.getCurrentEpisode().equals(data.getTotalEpisode()) || Integer.parseInt(data.getCurrentEpisode())==(Integer.parseInt(data.getTotalEpisode())-1))
				releaseDate = data.getFinishDate();
			else
				releaseDate = data.getReleaseDate();
			if (day.equalsIgnoreCase(releaseDate))
				ReleaseNotifierDialog.filmReleased.addElement(name);
		}
		if (ReleaseNotifierDialog.ovaReleased.isEmpty())
		{
			ReleaseNotifierDialog.ovaReleased.addElement("Nessun Anime Rilasciato");
			enableOav = false;
		}
		else
			enableOav = true;
		if (ReleaseNotifierDialog.filmReleased.isEmpty())
		{
			ReleaseNotifierDialog.filmReleased.addElement("Nessun Anime Rilasciato");
			enableFilm = false;
		}
		else
			enableFilm = true;
		return null;
	}

	@Override
	protected void done()
	{
		if (!ReleaseNotifierDialog.ovaReleased.contains("Nessun Anime Rilasciato") || !ReleaseNotifierDialog.filmReleased.contains("Nessun Anime Rilasciato"))
		{
			ReleaseNotifierDialog dial = new ReleaseNotifierDialog();
			dial.setLocationRelativeTo(AnimeIndex.mainFrame);
			dial.setVisible(true);
		}
		else if (AnimeIndex.openReleaseDialog==true)
		{
			AnimeIndex.openReleaseDialog = false;
			ReleaseNotifierDialog dial = new ReleaseNotifierDialog();
			dial.setLocationRelativeTo(AnimeIndex.mainFrame);
			dial.setVisible(true);
		}
	}

}
