package util.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
		GregorianCalendar calendar = new GregorianCalendar();
		int currentDay = calendar.get(Calendar.DATE);
		int currentMonth = calendar.get(Calendar.MONTH)+1;
		int currentYear = calendar.get(Calendar.YEAR);
		String date = currentDay + "/" + currentMonth + "/" + currentYear;
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/YYYY"); // the day of the week abbreviated
		try {
			calendar.setTime(simpleDateformat.parse(date));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		GregorianCalendar c1 = new GregorianCalendar();
		if(!AnimeIndex.appProp.getProperty("Date_Release").equalsIgnoreCase("none"))
		{
			try {
				c1.setTime(simpleDateformat.parse(AnimeIndex.appProp.getProperty("Date_Release")));
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
		else
			c1 = null;
		GregorianCalendar c = new GregorianCalendar();
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
			try {
				c.setTime(simpleDateformat.parse(releaseDate));
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			if(c1!=null)
			{
				if(c.after(c1))
				{
					if (c.before(calendar)||c.equals(calendar))
						ReleaseNotifierDialog.ovaReleased.addElement(name);
				}
			}
			else
			{
				if (c.before(calendar)||c.equals(calendar))
					ReleaseNotifierDialog.ovaReleased.addElement(name);
			}
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
			try {
				c.setTime(simpleDateformat.parse(releaseDate));
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			if(c1!=null)
			{
				if(c.after(c1))
				{
					if (c.before(calendar)||c.equals(calendar))
						ReleaseNotifierDialog.filmReleased.addElement(name);
				}
			}
			else
			{
				if (c.before(calendar)||c.equals(calendar))
					ReleaseNotifierDialog.filmReleased.addElement(name);
			}
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
