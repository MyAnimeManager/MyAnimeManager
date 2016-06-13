package util.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.AnimeData;
import util.ConnectionManager;
import util.MAMUtil;


public class SynchronizeToMALTask extends SwingWorker
{
	private String username;
	private String password;
	private List animeToAdd;
	private ArrayList<String> conflictedAnime = new ArrayList<String>();
	
	public SynchronizeToMALTask(String username, String password, List animeToAdd)
	{
		this.username = username;
		this.password = password;
		this.animeToAdd = animeToAdd;
	}
	@Override
	protected Object doInBackground()
	{
		try
		{
			for (Object obj : animeToAdd)
			   {		
				   String animeName = (String) obj;
				   System.out.println(animeName);
				   HashMap<String, Integer> map = ConnectionManager.getAnimeSearchedMAL(username, password, animeName);
				   if (map.size() == 1)
				   {
					   int id = map.get(animeName);
						String episode = "";
						String status = "";
						String comments = "";
						if (AnimeIndex.completedMap.containsKey(animeName))
						{
							AnimeData data = AnimeIndex.completedMap.get(animeName);
							episode = data.getCurrentEpisode();
							status = "2";
							comments = data.getNote();
						}
						else if (AnimeIndex.airingMap.containsKey(animeName))
						{
							AnimeData data = AnimeIndex.airingMap.get(animeName);
							episode = data.getCurrentEpisode();
							status = "1";
							comments = data.getNote();
						}
						else if (AnimeIndex.ovaMap.containsKey(animeName))
						{
							AnimeData data = AnimeIndex.ovaMap.get(animeName);
							episode = data.getCurrentEpisode();
							String episodeTotal = data.getTotalEpisode();
							if (episode.equals(episodeTotal))
								status = "2";
							else
								status = "1";
							comments = data.getNote();
						}
						else if (AnimeIndex.filmMap.containsKey(animeName))
						{
							AnimeData data = AnimeIndex.completedMap.get(animeName);
							episode = data.getCurrentEpisode();
							
							String today = MAMUtil.today();
							GregorianCalendar todayDate = MAMUtil.getDate(today);
							String release = data.getReleaseDate();
							GregorianCalendar releaseDate = MAMUtil.getDate(release);
							if (releaseDate.before(todayDate) || releaseDate.equals(todayDate))
								status = "1";
							else
								status = "6";
							comments = data.getNote();
						}
						else if (AnimeIndex.completedToSeeMap.containsKey(animeName))
						{
							AnimeData data = AnimeIndex.completedToSeeMap.get(animeName);
							episode = data.getCurrentEpisode();
							status = "1";
							comments = data.getNote();
						}
						try
						{
							System.out.println("Aggiungo" + animeName);
							ConnectionManager.addAnimeMAL(username, password, id, episode, status, comments);
						}
						catch (IOException e)
						{
							MAMUtil.writeLog(e);
							e.printStackTrace();
						}
				   }
				   else if (map.size() > 1)
					   conflictedAnime.add(animeName);
				   else if (map.size() == 0)
					   System.out.println(animeName + " non trovato");
			   }

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	protected void done()
	{
		for (String anime : conflictedAnime)
		{
			HashMap<String, Integer> map = ConnectionManager.getAnimeSearchedMAL(username, password, anime);
			if (map.size() > 1)
			{
				String[] animeArray = map.keySet().toArray(new String[0]);
				String animeName = (String) JOptionPane.showInputDialog(AnimeIndex.frame, "Scegli l'anime da aggiungere per \"" + anime + "\" :", "Conflitto trovato", JOptionPane.QUESTION_MESSAGE, null, animeArray, animeArray[0]);
				if (animeName != null)
				{
					int id = map.get(animeName);
					String episode = "";
					String status = "";
					String comments = "";
					if (AnimeIndex.completedMap.containsKey(anime))
					{
						AnimeData data = AnimeIndex.completedMap.get(anime);
						episode = data.getCurrentEpisode();
						status = "2";
						comments = data.getNote();
					}
					else if (AnimeIndex.airingMap.containsKey(anime))
					{
						AnimeData data = AnimeIndex.airingMap.get(anime);
						episode = data.getCurrentEpisode();
						status = "1";
						comments = data.getNote();
					}
					else if (AnimeIndex.ovaMap.containsKey(anime))
					{
						AnimeData data = AnimeIndex.ovaMap.get(anime);
						episode = data.getCurrentEpisode();
						String episodeTotal = data.getTotalEpisode();
						if (episode.equals(episodeTotal))
							status = "2";
						else
							status = "1";
						comments = data.getNote();
					}
					else if (AnimeIndex.filmMap.containsKey(anime))
					{
						AnimeData data = AnimeIndex.completedMap.get(anime);
						episode = data.getCurrentEpisode();
						
						String today = MAMUtil.today();
						GregorianCalendar todayDate = MAMUtil.getDate(today);
						String release = data.getReleaseDate();
						GregorianCalendar releaseDate = MAMUtil.getDate(release);
						if (releaseDate.before(todayDate) || releaseDate.equals(todayDate))
							status = "1";
						else
							status = "6";
						comments = data.getNote();
					}
					else if (AnimeIndex.completedToSeeMap.containsKey(anime))
					{
						AnimeData data = AnimeIndex.completedToSeeMap.get(anime);
						episode = data.getCurrentEpisode();
						status = "1";
						comments = data.getNote();
					}
					try
					{
						System.out.println("status = " + status);
						ConnectionManager.addAnimeMAL(username, password, id, episode, status, comments);
					}
					catch (IOException e)
					{
						MAMUtil.writeLog(e);
						e.printStackTrace();
					}
				}
				
			}
		}
	}
	
}
