package util.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.json.XML;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import main.AnimeIndex;
import util.AnimeData;
import util.ConnectionManager;
import util.Filters;
import util.MAMUtil;
import util.SortedListModel;
import util.window.AddAnimeDialog;
import util.window.AnimeInformation;

//TODO anime non trovati, prendere dati da xml
//TODO anime completati prendere dati da xml
public class MALSynchronizationTask extends SwingWorker
{
	private final String MAL_ANIMELIST_URL = "http://myanimelist.net/malappinfo.php?u=";
	private final String ALL_ANIME = "&status=all&type=anime";
	private String username;
	private static String lastAnime;
	private static TreeMap<String,String> conflictedAnime = new TreeMap<String,String>();
	private String json;
	
	
	public MALSynchronizationTask(String username)
	{
		this.username = username;
	}
	
	@Override
	protected Object doInBackground() throws Exception
	{
		TreeMap<String,String> animeList = getAnimeList();
		synchronizeAnime(animeList);
		return null;
	}
	
	@Override
	protected void done()
	{
		for (Entry<String,String> entry : conflictedAnime.entrySet())
		{
			String title = entry.getKey();
			HashMap<String,Integer> map = ConnectionManager.AnimeSearch(title);
			if (map.size() > 1)
			{
				String[] titles = map.keySet().toArray(new String[]{});
				String anime = (String)JOptionPane.showInternalInputDialog(AnimeIndex.mainFrame, "Scegli il titolo corretto per : \"" + title +"\"", "Conflitto tra titoli", JOptionPane.WARNING_MESSAGE, null, titles, titles[0]);
				if (anime != null)
				{
					int id = map.get(anime);
					anime = anime.replace("/", "\\/");
					anime = anime.replace("!", "\\!");
					automaticAdd(anime, id, entry.getValue());
				}
			}
		}
		System.out.println("FINE");
	}
	
	private TreeMap<String,String> getAnimeList()
	{
		TreeMap<String,String> animeList = new TreeMap<String,String>();
		setProgress(0);
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http.client.protocol.ResponseProcessCookies").setLevel(java.util.logging.Level.OFF);
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		WebClient webClient = null;
		XmlPage page = null;
		try
		{
			webClient = new WebClient(BrowserVersion.FIREFOX_38);
			webClient.getCache().setMaxSize(0);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(false);
			webClient.setCssErrorHandler(new SilentCssErrorHandler());
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.waitForBackgroundJavaScriptStartingBefore(5 * 1000);
			webClient.getCookieManager().setCookiesEnabled(false);
//			webClient.getOptions().setRedirectEnabled(false);

			String url = MAL_ANIMELIST_URL + username + ALL_ANIME;
			System.out.println("Loading page now: " + url);
			page = webClient.getPage(url);
			
			String xml = page.asXml();
			
			JSONObject xmlJSONObj = XML.toJSONObject(xml);
	        json = xmlJSONObj.toString(4);
	        
	        JsonParser parser = new JsonParser();
	        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
	        JsonObject root = jsonObj.get("myanimelist").getAsJsonObject();
	        JsonArray animes =root.get("anime").getAsJsonArray();
	        int animeNumber = animes.size();
	        int currentAnime = 0;
	        for (JsonElement anime : animes)
	        {
	        	System.out.println(currentAnime/animeNumber);
	        	setProgress(currentAnime/animeNumber);
	        	String title = anime.getAsJsonObject().get("series_title").getAsString();
	        	int status = anime.getAsJsonObject().get("my_status").getAsInt();
	        	String list ="";
//	        	1/watching, 2/completed, 3/onhold, 4/dropped, 6/plantowatch
	        	switch (status)
				{
					case 1:
						list = "anime in corso";
						break;
					case 2:
						list = "anime completati";
						break;
					case 3:
						list = "anime in corso";
						break;
					case 4:
						list = "anime completati";
						break;
					case 6:
						list = "anime in corso";
						break;					
					default:
						list = "anime in corso";
						break;
				}
	        	animeList.put(title, list);
	        	currentAnime++;
	        }
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        json = gson.toJson(animes);

			BufferedWriter output;
			try
			{
				output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "prova.xml"), "UTF-8"));
				output.write(xml);
				output.close();
				output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "prova.json"), "UTF-8"));
				output.write(json);
				output.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				MAMUtil.writeLog(e);
			}
			
			
		}
		catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
			if (e.getStatusCode() == 302) {
					System.out.println("ERRORE");
					System.out.println(page.toString());			
			}
		}
		catch (Exception e)
		{
			System.out.println("Errore");
			e.printStackTrace();
		}
		webClient.close();
		return animeList;
	}
	
	private void synchronizeAnime(TreeMap<String,String> animeTitleList)
	{
		for (Entry<String,String> animeEntry: animeTitleList.entrySet())
		{
			String title = animeEntry.getKey();
			HashMap<String,Integer> map = ConnectionManager.AnimeSearch(title);
			if (map.size() == 1)
			{
				String anime = (map.keySet().toArray(new String[]{}))[0];
				int id = map.get(anime);
				anime = anime.replace("\\", "\\\\");
				anime = anime.replace("!", "\\!");
				automaticAdd(anime, id, animeEntry.getValue());
				
			}
			else if (map.size() > 1)
			{
				conflictedAnime.put(title, animeEntry.getValue());
			}
			else if (map.size() == 0)
			{
				System.out.println(title + " non trovato");
			}
		}
	}
	
	//util
	private void automaticAdd(String anime, int id, String listToAdd)
	{
		long time = System.currentTimeMillis();
		AnimeIndex.addToPreviousList = listToAdd;

		String name = ConnectionManager.getAnimeDataGson("title_romaji", id);
		String totEp = ConnectionManager.getAnimeDataGson("total_episodes", id);
		String currentEp = "1";
		String fansub = "";
		String animeType = ConnectionManager.getAnimeDataGson("type", id);
		String releaseDate = ConnectionManager.getAnimeDataGson("start_date", id);
		String finishDate = ConnectionManager.getAnimeDataGson("end_date", id);
		String durationEp = ConnectionManager.getAnimeDataGson("duration", id);

		if (totEp != null && !totEp.isEmpty())
			if (totEp.equals("null") || totEp.equals("0"))
				totEp = "??";
				
		if (durationEp != null && !durationEp.isEmpty())
			if (durationEp.equals("null") || durationEp.equals("0"))
				durationEp = "?? min";
			else
				durationEp += " min";
		if (releaseDate != null && !releaseDate.isEmpty())
			if (releaseDate.equals("null"))
				releaseDate = "??/??/????";
			else if (releaseDate.length() == 4)
				releaseDate = "??/??/" + releaseDate;
			else if (releaseDate.length() == 7)
			{
				String monthStart = releaseDate.substring(5, 7);
				String yearStart = releaseDate.substring(0, 4);
				releaseDate = "??/" + monthStart + "/" + yearStart;
			}
			else if (releaseDate.length() > 7)
			{
				String dayStart = releaseDate.substring(8, 10);
				String monthStart = releaseDate.substring(5, 7);
				String yearStart = releaseDate.substring(0, 4);
				releaseDate = dayStart + "/" + monthStart + "/" + yearStart;
			}
		if (finishDate != null && !finishDate.isEmpty())
			if (finishDate.equals("null"))
				finishDate = "??/??/????";
			else if (finishDate.length() == 4)
				finishDate = "??/??/" + finishDate;
			else if (finishDate.length() == 7)
			{
				String monthEnd = finishDate.substring(5, 7);
				String yearEnd = finishDate.substring(0, 4);
				finishDate = "??/" + monthEnd + "/" + yearEnd;
			}
			else if (finishDate.length() > 7)
			{
				String dayEnd = finishDate.substring(8, 10);
				String monthEnd = finishDate.substring(5, 7);
				String yearEnd = finishDate.substring(0, 4);
				finishDate = dayEnd + "/" + monthEnd + "/" + yearEnd;
			}
			if (totEp.equals("1"))
				finishDate = releaseDate;
			
		String exitDay = "?????";
		if (listToAdd.equalsIgnoreCase("completi da vedere"))
			exitDay = "Concluso";

		if (currentEp.equals(totEp))
			AnimeIndex.animeInformation.plusButton.setEnabled(false);
		String list = "";
		if ((AnimeIndex.appProp.getProperty("Check_Data_Conflict").equalsIgnoreCase("false")) || animeType.equalsIgnoreCase("?????"))
			list = listToAdd;
		else
			list = checkDataConflict(finishDate, animeType, listToAdd);

		if (list.equalsIgnoreCase("anime completati"))
		{
			currentEp = totEp;
			exitDay = "Concluso";
		}
		String imageName = AddAnimeDialog.addSaveImage(name, id, list);
		if(list.equalsIgnoreCase("Film") || list.equalsIgnoreCase("OAV"))
		{
			if(!releaseDate.contains("?"))
			{
				GregorianCalendar rDate = MAMUtil.getDate(releaseDate);
				String cDay = MAMUtil.today();
				GregorianCalendar tDate = MAMUtil.getDate(cDay);
//				if(animeType.equalsIgnoreCase("Movie") || animeType.equalsIgnoreCase("OVA") || animeType.equalsIgnoreCase("ONA") || animeType.equalsIgnoreCase("Special") || animeType.equalsIgnoreCase("TV Short"))
//				{
					if(rDate.before(tDate) || rDate.equals(tDate))
					{
						AnimeIndex.exitDateMap.put(name, cDay);
						exitDay = "Rilasciato";
					}
//				}
			}
		}
		AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png", exitDay, Integer.toString(id), "", "", animeType, releaseDate, finishDate, durationEp, false);
		checkAnimeAlreadyAdded(name, list, data);
		AnimeIndex.lastSelection = anime;
		time = System.currentTimeMillis()-time;
		System.out.println(time);
	}

	private static String checkDataConflict(String finishDate, String type, String listName)
	{
		String map = listName;

		String finishDay = finishDate.substring(0, 1);
		String finishMonth = finishDate.substring(3, 5);
		String finishYear = finishDate.substring(6);

		if (!(finishDay.equalsIgnoreCase("??") || finishMonth.equalsIgnoreCase("??") || finishYear.equalsIgnoreCase("????")))
		{
			if (listName.equalsIgnoreCase("anime completati"))
			{
				map = "Anime Completati";
				Date today = new Date();
				Date finish = null;

				try
				{
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					finish = format.parse(finishDate);
				}
				catch (ParseException e)
				{
					e.printStackTrace();
					MAMUtil.writeLog(e);
				}

				if (today.before(finish) && (type.equalsIgnoreCase("tv") || type.equalsIgnoreCase("tv short")))
				{
						map = "Anime in Corso";
				}

				else if (!(type.equalsIgnoreCase("tv") || type.equalsIgnoreCase("tv short")))
				{
					if (type.equalsIgnoreCase("Movie"))
					{
						map = "Film";
					}

					if (type.equalsIgnoreCase("Special") || type.equalsIgnoreCase("Ova") || type.equalsIgnoreCase("Ona"))
					{
							map = "OAV";
					}
				}
			}

			else if (listName.equalsIgnoreCase("anime in corso"))
			{
				map = "Anime in Corso";
				Date today = new Date();
				Date finish = null;

				try
				{
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					finish = format.parse(finishDate);
				}
				catch (ParseException e)
				{
					e.printStackTrace();
					MAMUtil.writeLog(e);
				}

				if (today.after(finish) && (type.equalsIgnoreCase("tv") || type.equalsIgnoreCase("tv short")))
				{
						map = "Completi Da Vedere";
				}

				else if (!(type.equalsIgnoreCase("tv") || type.equalsIgnoreCase("tv short")))
				{
					if (type.equalsIgnoreCase("Movie"))
					{
							map = "Film";
					}

					if (type.equalsIgnoreCase("Special") || type.equalsIgnoreCase("Ova") || type.equalsIgnoreCase("Ona"))
					{
							map = "OAV";
					}
				}
			}

			else if (listName.equalsIgnoreCase("oav"))
			{
				map = "OAV";
				if (!(type.equalsIgnoreCase("tv") || type.equalsIgnoreCase("tv short")))
				{
					if (type.equalsIgnoreCase("Movie"))
					{
							map = "Film";
					}
				}

				else if ((type.equalsIgnoreCase("tv") || type.equalsIgnoreCase("tv short")))
				{
					Date today = new Date();
					Date finish = null;

					try
					{
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
						finish = format.parse(finishDate);
					}
					catch (ParseException e)
					{
						e.printStackTrace();
						MAMUtil.writeLog(e);
					}

					if (today.before(finish))
					{
							map = "Anime in Corso";
					}
					else if (today.after(finish))
					{
							map = "Completi Da Vedere";
					}
				}
			}

			else if (listName.equalsIgnoreCase("film"))
			{

				map = "Film";

				if (!(type.equalsIgnoreCase("tv") || type.equalsIgnoreCase("tv short")))
				{
					if (type.equalsIgnoreCase("Special") || type.equalsIgnoreCase("Ova") || type.equalsIgnoreCase("Ona"))
					{
							map = "OAV";
					}
				}

				else if ((type.equalsIgnoreCase("tv") || type.equalsIgnoreCase("tv short")))
				{
					Date today = new Date();
					Date finish = null;

					try
					{
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
						finish = format.parse(finishDate);
					}
					catch (ParseException e)
					{
						e.printStackTrace();
						MAMUtil.writeLog(e);
					}

					if (today.before(finish))
					{
							map = "Anime in Corso";
					}
					else if (today.after(finish))
					{
							map = "Completi Da Vedere";
					}
				}
			}

			else if (listName.equalsIgnoreCase("completi da vedere"))
			{
				map = "Completi Da Vedere";
				Date today = new Date();
				Date finish = null;

				try
				{
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					finish = format.parse(finishDate);
				}
				catch (ParseException e)
				{
					e.printStackTrace();
					MAMUtil.writeLog(e);
				}

				if (today.before(finish) && (type.equalsIgnoreCase("tv") || type.equalsIgnoreCase("tv short")))
				{
						map = "Anime in Corso";
				}

				else if (!(type.equalsIgnoreCase("tv") || type.equalsIgnoreCase("tv short")))
				{
					if (type.equalsIgnoreCase("Movie"))
					{
							map = "Film";
					}

					if (type.equalsIgnoreCase("Special") || type.equalsIgnoreCase("Ova") || type.equalsIgnoreCase("Ona"))
					{
							map = "OAV";
					}
				}
			}
		}
		else if ((type.equalsIgnoreCase("tv") || type.equalsIgnoreCase("tv-short")) && !listName.equalsIgnoreCase("anime in corso"))
		{
				map = "Anime in Corso";
		}

		else if ((type.equalsIgnoreCase("OVA") || type.equalsIgnoreCase("ONA") || type.equalsIgnoreCase("Special")) && !listName.equalsIgnoreCase("oav"))
		{
				map = "OAV";
		}

		else if (type.equalsIgnoreCase("Film") && !listName.equalsIgnoreCase("film"))
		{
				map = "Film";
		}
		return map;
	}

	private static void checkAnimeAlreadyAdded(String name, String listName, AnimeData data)
	{
		JList list = AddAnimeDialog.getJList(listName);
		SortedListModel model = AddAnimeDialog.getModel(listName);
		TreeMap<String, AnimeData> map = AddAnimeDialog.getMap(listName);		
			if (!(map.containsKey(name)) == true)
			{
				if (!AnimeIndex.completedMap.containsKey(name) && !AnimeIndex.airingMap.containsKey(name) && !AnimeIndex.ovaMap.containsKey(name) && !AnimeIndex.filmMap.containsKey(name) && !AnimeIndex.completedToSeeMap.containsKey(name))
					AnimeIndex.sessionAddedAnime.add(name);

				map.put(name, data);
				model.addElement(name);
				if (AddAnimeDialog.getDeletedArrayList(listName).contains(map.get(name).getImagePath(listName)))
					AddAnimeDialog.getDeletedArrayList(listName).remove(map.get(name).getImagePath(listName));
				AddAnimeDialog.getArrayList(listName).add(map.get(name).getImagePath(listName));
//				AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
//				AnimeIndex.shouldUpdate = false;
//				list.clearSelection();
//				list.setSelectedValue(name, true);
//				AnimeIndex.shouldUpdate = true;
				//TODO
				lastAnime = name;
				AnimeInformation.fansubComboBox.setSelectedItem("?????");
			}
			if (AnimeIndex.filtro != 9)
				Filters.removeFilters();
	}
}
