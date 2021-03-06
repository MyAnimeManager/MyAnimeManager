package util.task;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.AnimeData;
import util.ConnectionManager;
import util.MAMUtil;
import util.SortedListModel;


public class SynchronizeToMALTask extends SwingWorker
{
	private String username;
	private String password;
	private List animeToAdd;
	private ArrayList<String> conflictedAnime = new ArrayList<String>();
	private ArrayList<String> notFoundedAnime = new ArrayList<String>();
	private ArrayList<String> animeNotSynchronized = new ArrayList<String>();
	public float totalAnimeNumber;
	public float currentAnimeNumber;
	public String currentAnime;
	boolean credentialOk;
	
	public SynchronizeToMALTask(String username, String password, List animeToAdd)
	{
		this.username = username;
		this.password = password;
		this.animeToAdd = animeToAdd;
		totalAnimeNumber = animeToAdd.size();
	}
	@Override
	protected Object doInBackground()
	{
		credentialOk = false;
		try
		{
			credentialOk = ConnectionManager.verifyCredentialsMAL(username, password);
			if (credentialOk)
			{
				setProgress(0);	
				synchronizeAnimeToMAL(animeToAdd, true);
				synchronizeAnimeToMAL(notFoundedAnime, false);
			}
		}
		catch (IOException e)
		{
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
		return null;
	}
	
	protected void done()
	{
		try
		{
			if (credentialOk)
			{
				int size = conflictedAnime.size();
				int current = 1;
				for (String anime : conflictedAnime)
				{
					currentAnime = anime;
					HashMap<String, Integer> map = ConnectionManager.getAnimeSearchedMAL(username, password, anime);
					if (map.size() > 1)
					{
						String[] animeArray = map.keySet().toArray(new String[0]);
						String animeName = (String) JOptionPane.showInputDialog(AnimeIndex.frame, "Scegli l'anime da aggiungere per \"" + anime + "\" :", "Conflitto trovato" + current + "/" + size, JOptionPane.QUESTION_MESSAGE, null, animeArray, animeArray[0]);
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
								ConnectionManager.addAnimeMAL(username, password, id, episode, status, comments);
								current++;
							}
							catch (IOException e)
							{
								MAMUtil.writeLog(e);
								e.printStackTrace();
							}
						}				
					}
				} 
			
			if (animeNotSynchronized.size() > 0)
			{
				JPanel panel = new JPanel(new BorderLayout(5, 5));
				panel.add(new JLabel("I seguenti anime non sono stati trovati:"), BorderLayout.NORTH);
				JScrollPane scroll = new JScrollPane();
				JList list = new JList();
				SortedListModel model = new SortedListModel();
				list.setModel(model);
				model.addAll(animeNotSynchronized.toArray(new String[0]));
				scroll.setViewportView(list);
				panel.add(scroll, BorderLayout.CENTER);
				JOptionPane.showMessageDialog(AnimeIndex.frame, panel, "Anime non trovati!", JOptionPane.INFORMATION_MESSAGE);
			}
			}
			else
				JOptionPane.showMessageDialog(AnimeIndex.frame, "Errore di Connessione o Credenziali Errate!", "Errore!", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e)
		{
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
	}
	
	private void synchronizeAnimeToMAL(List<String> animeToAdd, boolean shouldAddToNotFounded)
	{
		for (Object obj : animeToAdd)
		{	
			String animeName = (String) obj;
			currentAnime = animeName;
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
					ConnectionManager.addAnimeMAL(username, password, id, episode, status, comments);
					currentAnimeNumber++;
					int progress = (int)((currentAnimeNumber/totalAnimeNumber) * 100);
					setProgress(progress);
				}
				catch (IOException e)
				{
					MAMUtil.writeLog(e);
					e.printStackTrace();
				}
			}
			else if (map.size() > 1)
			{
				boolean founded = false; 
				try
				{
					for (Entry<String,Integer> entry : map.entrySet())
					{
						if (!founded)
						{
							String name = entry.getKey();
							int id = entry.getValue();
							if (AnimeIndex.completedMap.containsKey(name))
							{
								AnimeData data = AnimeIndex.completedMap.get(name);
								String episode = data.getCurrentEpisode();
								String status = "2";
								String comments = data.getNote();
								founded = true;
								ConnectionManager.addAnimeMAL(username, password, id, episode, status, comments);
								currentAnimeNumber++;
								int progress = (int)((currentAnimeNumber/totalAnimeNumber) * 100);
								setProgress(progress);
							}
							else if (AnimeIndex.airingMap.containsKey(name))
							{
								AnimeData data = AnimeIndex.airingMap.get(name);
								String episode = data.getCurrentEpisode();
								String status = "1";
								String comments = data.getNote();
								founded = true;
								ConnectionManager.addAnimeMAL(username, password, id, episode, status, comments);
								currentAnimeNumber++;
								int progress = (int)((currentAnimeNumber/totalAnimeNumber) * 100);
								setProgress(progress);
							}
							else if (AnimeIndex.ovaMap.containsKey(name))
							{
								AnimeData data = AnimeIndex.ovaMap.get(name);
								String episode = data.getCurrentEpisode();
								String episodeTotal = data.getTotalEpisode();
								String status = "";
								if (episode.equals(episodeTotal))
									status = "2";
								else
									status = "1";
								String comments = data.getNote();
								founded = true;
								ConnectionManager.addAnimeMAL(username, password, id, episode, status, comments);
								currentAnimeNumber++;
								int progress = (int)((currentAnimeNumber/totalAnimeNumber) * 100);
								setProgress(progress);
							}
							else if (AnimeIndex.filmMap.containsKey(name))
							{
								AnimeData data = AnimeIndex.completedMap.get(name);
								String episode = data.getCurrentEpisode();
								
								String today = MAMUtil.today();
								GregorianCalendar todayDate = MAMUtil.getDate(today);
								String release = data.getReleaseDate();
								GregorianCalendar releaseDate = MAMUtil.getDate(release);
								String status = "";
								if (releaseDate.before(todayDate) || releaseDate.equals(todayDate))
									status = "1";
								else
									status = "6";
								String comments = data.getNote();
								founded = true;
								ConnectionManager.addAnimeMAL(username, password, id, episode, status, comments);
								currentAnimeNumber++;
								int progress = (int)((currentAnimeNumber/totalAnimeNumber) * 100);
								setProgress(progress);
							}
							else if (AnimeIndex.completedToSeeMap.containsKey(name))
							{
								AnimeData data = AnimeIndex.completedToSeeMap.get(name);
								String episode = data.getCurrentEpisode();
								String status = "1";
								String comments = data.getNote();
								founded = true;
								ConnectionManager.addAnimeMAL(username, password, id, episode, status, comments);
								currentAnimeNumber++;
								int progress = (int)((currentAnimeNumber/totalAnimeNumber) * 100);
								setProgress(progress);
							}
						}
					}
				}
				catch (IOException e)
				{
					MAMUtil.writeLog(e);
					e.printStackTrace();
				}
				if (!founded)
					conflictedAnime.add(animeName);
			}
			else if (map.size() == 0)
			{
				System.out.println(animeName + " non trovato");
				if(shouldAddToNotFounded)
					notFoundedAnime.add(animeName.replace(": ", ":"));
				else
				{
					animeNotSynchronized.add(animeName);
					currentAnimeNumber++;
				}
			}
		}
	}
}
