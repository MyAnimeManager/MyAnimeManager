package util.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import main.AnimeIndex;
import util.AnimeData;
import util.ConnectionManager;
import util.FileManager;
import util.Filters;
import util.MAMUtil;
import util.SortedListModel;
import util.window.AddAnimeDialog;
import util.window.WishlistDialog;

public class MALSynchronizationTask extends SwingWorker
{
	private final String MAL_ANIMELIST_URL = "http://myanimelist.net/malappinfo.php?u=";
	private final String ALL_ANIME = "&status=all&type=anime";
	private String username;
	private static ArrayList<JsonObject> conflictedAnime = new ArrayList<JsonObject>();
	private static ArrayList<JsonObject>  completedAnime = new ArrayList<JsonObject>();
	private static ArrayList<JsonObject>  airingAnime = new ArrayList<JsonObject>();
	private static ArrayList<JsonObject>  droppedAnime = new ArrayList<JsonObject>();
	private static ArrayList<JsonObject>  notFoundedAnime = new ArrayList<JsonObject>();
	private static ArrayList<JsonObject>  wishlistAnime = new ArrayList<JsonObject>();
	private JsonArray animesJson;
	public float totalAnimeNumber;
	public float currentAnimeNumber = 0;
	
	
	public MALSynchronizationTask(String username)
	{
		this.username = username;
	}
	
	@Override
	protected Object doInBackground() throws Exception
	{
		getAnimeList();
		synchronizeAiringAnime();
		synchronizeAnimeFromJson(notFoundedAnime);
		synchronizeAnimeFromJson(completedAnime);
		synchronizeDropListAnime();
		synchronizeWishListAnime();
		return null;
	}
	
	@Override
	protected void done()
	{
		synchronizeConflictedAnime();
	}
	
	private void getAnimeList()
	{
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
	        String json = xmlJSONObj.toString(4);
	        
	        JsonParser parser = new JsonParser();
	        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
	        JsonObject root = jsonObj.get("myanimelist").getAsJsonObject();
	        animesJson = root.get("anime").getAsJsonArray();
	        totalAnimeNumber = animesJson.size();
	        for (JsonElement anime : animesJson)
	        {
	        	int status = anime.getAsJsonObject().get("my_status").getAsInt();
//	        	1/watching, 2/completed, 3/onhold, 4/dropped, 6/plantowatch
	        	switch (status)
				{
					case 1:
						airingAnime.add(anime.getAsJsonObject());
						break;
					case 2:
						completedAnime.add(anime.getAsJsonObject());
						break;
					case 3:
						airingAnime.add(anime.getAsJsonObject());
						break;
					case 4:
						droppedAnime.add(anime.getAsJsonObject());
						break;
					case 6:
						wishlistAnime.add(anime.getAsJsonObject());
						break;					
					default:
						airingAnime.add(anime.getAsJsonObject());
						break;
				}
			}	        
		}
		catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
			if (e.getStatusCode() == 302) 
			{
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
		setProgress(0);
	}
	
	private void synchronizeAiringAnime()
	{
		for (JsonObject obj: airingAnime)
		{
			String title = obj.get("series_title").getAsString();
			HashMap<String,Integer> map = ConnectionManager.SearchAnime(title);
			if (map.size() == 1)
			{
				String anime = (map.keySet().toArray(new String[]{}))[0];
				int id = map.get(anime);
				anime = anime.replace("\\", "\\\\");
				anime = anime.replace("!", "\\!");
				if(anime.startsWith("."))
					anime = anime.replaceFirst(".", "");
				automaticAdd(anime, id, "anime in corso");
				currentAnimeNumber++;
				int progress =(int) ((currentAnimeNumber/totalAnimeNumber) * 100);
				setProgress(progress);
			}
			else if (map.size() > 1)
			{
				boolean founded = false; 
				for (Entry<String,Integer> entry : map.entrySet())
				{					
					if (!founded)
					{
						String mapTitle = entry.getKey();
						if (title.equalsIgnoreCase(mapTitle))
						{
							founded = true;
							int id = map.get(mapTitle);
							mapTitle = mapTitle.replace("\\", "\\\\");
							mapTitle = mapTitle.replace("!", "\\!");
							if(mapTitle.startsWith("."))
								mapTitle = mapTitle.replaceFirst(".", "");
							automaticAdd(mapTitle, id, "anime in corso");
							currentAnimeNumber++;
							int progress =(int) ((currentAnimeNumber/totalAnimeNumber) * 100);
							setProgress(progress);
						} 
					}
					
				}
				if (!founded)
					conflictedAnime.add(obj);
			}
			else if (map.size() == 0)
			{
				boolean found = false;
				if(!obj.get("series_synonyms").isJsonObject())
				{	
					String[] synonyms = obj.get("series_synonyms").getAsString().split("; ");
					for(String syn: synonyms)
					{
						if(!found)
						{
							map = ConnectionManager.SearchAnime(syn);
							if (map.size() == 1)
							{
								String anime = (map.keySet().toArray(new String[]{}))[0];
								int id = map.get(anime);
								anime = anime.replace("\\", "\\\\");
								anime = anime.replace("!", "\\!");
								if(anime.startsWith("."))
									anime = anime.replaceFirst(".", "");
								automaticAdd(anime, id, "anime in corso");
								found = true;
								currentAnimeNumber++;
								int progress =(int) ((currentAnimeNumber/totalAnimeNumber) * 100);
								setProgress(progress);
							}
							else if (map.size() > 1)
							{
								found = true;
								boolean founded = false; 
								for (Entry<String,Integer> entry : map.entrySet())
								{					
									if (!founded)
									{
										String mapTitle = entry.getKey();
										if (title.equalsIgnoreCase(mapTitle))
										{
											founded = true;
											int id = map.get(mapTitle);
											mapTitle = mapTitle.replace("\\", "\\\\");
											mapTitle = mapTitle.replace("!", "\\!");
											if(mapTitle.startsWith("."))
												mapTitle = mapTitle.replaceFirst(".", "");
											automaticAdd(mapTitle, id, "anime in corso");
											currentAnimeNumber++;
											int progress =(int) ((currentAnimeNumber/totalAnimeNumber) * 100);
											setProgress(progress);
										} 
									}
									
								}
								if (!founded)
									conflictedAnime.add(obj);
							}
						}
					}
				}
				if(!found)
					notFoundedAnime.add(obj);
			}
		}
		
	}
	
	private void synchronizeAnimeFromJson(ArrayList<JsonObject> list)
	{
		for (JsonObject obj: list)
		{
			String name = obj.get("series_title").getAsString();
			boolean match = false;
			if(!obj.get("series_synonyms").isJsonObject())
			{	
				String[] synonyms = obj.get("series_synonyms").getAsString().split("; ");
				for(String syn: synonyms)
					if(!match)
						if (AnimeIndex.completedMap.containsKey(syn) || AnimeIndex.airingMap.containsKey(syn))
							match = true;
			}
			if (!match && !AnimeIndex.completedMap.containsKey(name) && !AnimeIndex.airingMap.containsKey(name))
			{
				String totEp = obj.get("series_episodes").getAsString();
				String currentEp = obj.get("my_watched_episodes").getAsString();
				Integer animeTypeID = obj.get("series_type").getAsInt();
				String releaseDate = obj.get("series_start").getAsString();
				String finishDate = obj.get("series_end").getAsString();
				String durationEp = "?? min";
				if (totEp != null && !totEp.isEmpty())
					if (totEp.equals("null") || totEp.equals("0"))
						totEp = "??";
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
				String animeType = "";
				switch (animeTypeID)
				{
					case 1:
						animeType = "TV";
						break;
					case 2:
						animeType = "OVA";
						break;
					case 3:
						animeType = "Movie";
						break;
					case 4:
						animeType = "Special";
						break;
					case 5:
						animeType = "ONA";
						break;
					default:
						break;
				}
				String imageLink = obj.get("series_image").getAsString();
				String imageName = addCompletedAnimeSaveImage(name, imageLink);
				String note = "";				
				AnimeData data = null;
				if(!finishDate.contains("?"))
				{
					GregorianCalendar fDate = MAMUtil.getDate(finishDate);
					String todayDay = MAMUtil.today();
					GregorianCalendar todayDate = MAMUtil.getDate(todayDay);
					if(fDate.before(todayDate) || fDate.equals(todayDate))
					{
						String exitDay = "Concluso";
						data = new AnimeData(currentEp, totEp, "", note, imageName + ".png", exitDay, "", "", "", animeType, releaseDate, finishDate, durationEp, false);

					}
					else if (fDate.after(todayDate))
					{
						String exitDay = "?????";
						data = new AnimeData(currentEp, totEp, "", note, imageName + ".png", exitDay, "", "", "", animeType, releaseDate, finishDate, durationEp, false);

					}
				}		
				String listToAdd = checkDataConflict(finishDate, animeType, "Anime Completati");
				checkAnimeAlreadyAdded(name, listToAdd, data);	
			}
			currentAnimeNumber++;
			int progress =(int) ((currentAnimeNumber/totalAnimeNumber) * 100);
			setProgress(progress);
		}
	}

	private void synchronizeWishListAnime()
	{
		for (JsonObject obj: wishlistAnime)
		{
			String name = obj.get("series_title").getAsString();
			int id = obj.get("series_animedb_id").getAsInt();
			boolean match = false;
			if(!obj.get("series_synonyms").isJsonObject())
			{	
				String[] synonyms = obj.get("series_synonyms").getAsString().split("; ");
				for(String syn: synonyms)
					if (!match && AnimeIndex.wishlistMap.containsKey(syn))
						match = true;
			}
			if (!AnimeIndex.wishlistMALMap.containsKey(name) && !AnimeIndex.wishlistMap.containsKey(name) && !match)
			{
				AnimeIndex.wishlistMALMap.put(name, id);
				WishlistDialog.wishListModel.addElement(name);
			}
			currentAnimeNumber++;
			int progress =(int) ((currentAnimeNumber/totalAnimeNumber) * 100);
			setProgress(progress);
		}
	}
	
	private void synchronizeDropListAnime()
	{
		for (JsonObject obj: droppedAnime)
		{
			String name = obj.get("series_title").getAsString();
			int id = obj.get("series_animedb_id").getAsInt();
			boolean match = false;
			if(!obj.get("series_synonyms").isJsonObject())
			{	
				String[] synonyms = obj.get("series_synonyms").getAsString().split("; ");
				for(String syn: synonyms)
					if (!match && AnimeIndex.droppedMap.containsKey(syn))
						match = true;
			}
			if (!match && !AnimeIndex.droppedMALMap.containsKey(name) && !AnimeIndex.droppedMap.containsKey(name))
			{
				AnimeIndex.droppedMALMap.put(name, id);
				WishlistDialog.dropListModel.addElement(name);
			}
			currentAnimeNumber++;
			int progress =(int) ((currentAnimeNumber/totalAnimeNumber) * 100);
			setProgress(progress);
		}
	}
	//util

	private void synchronizeConflictedAnime()
	{
		for (JsonObject obj : conflictedAnime)
		{
			String title = obj.get("series_title").getAsString();
			HashMap<String,Integer> map = ConnectionManager.SearchAnime(title);
			if (map.size() > 1)
			{
				String[] titles = map.keySet().toArray(new String[]{});
				String anime = (String)JOptionPane.showInputDialog(AnimeIndex.frame.SynchroDial, "Scegli il titolo corretto per : \"" + title +"\"", "Conflitto tra titoli", JOptionPane.WARNING_MESSAGE, null, titles, titles[0]);
				if (anime != null)
				{
					int id = map.get(anime);
					anime = anime.replace("/", "\\/");
					anime = anime.replace("!", "\\!");
					if(anime.startsWith("."))
						anime = anime.replaceFirst(".", "");
					automaticAdd(anime, id, "anime in corso");
				}
				currentAnimeNumber++;
				int progress =(int) ((currentAnimeNumber/totalAnimeNumber) * 100);
				setProgress(progress);
			}
		}
	}
	private void automaticAdd(String anime, int id, String listToAdd)
	{
		AnimeIndex.addToPreviousList = listToAdd;
		

		JsonObject jo = ConnectionManager.getAnimeData(id);
		
		String name = jo.get("title").getAsJsonObject().get("romaji").getAsString();
		String totEp = jo.get("duration").getAsString();
		String currentEp = "1";
		String fansub = "";
		String animeType = jo.get("format").getAsString();
		JsonObject releaseDateJson = jo.get("startDate").getAsJsonObject();
		String releaseDate = "";
		if (releaseDateJson.get("day").isJsonNull())
			releaseDate = "??/";
		else
		{
			releaseDate = releaseDateJson.get("day").getAsString();
			if (releaseDate.length() == 1)
				releaseDate = "0" + releaseDate + "/";
		}
		
		if (releaseDateJson.get("month").isJsonNull())
			releaseDate = releaseDate + "??/";
		else
		{
			String releaseDateMonth = releaseDateJson.get("month").getAsString();
			if (releaseDateMonth.length() == 1)
				releaseDateMonth = "0" + releaseDateMonth;
			releaseDate = releaseDate + releaseDateMonth + "/";
		}
		if (releaseDateJson.get("year").isJsonNull())
			releaseDate = releaseDate + "????";
		else
			releaseDate = releaseDate + releaseDateJson.get("year").getAsString();
		
		JsonObject finishDateJson = jo.get("endDate").getAsJsonObject();
		String finishDate = "";
		if (finishDateJson.get("day").isJsonNull())
			finishDate = "??/";
		else
		{
			finishDate = finishDateJson.get("day").getAsString();
			if (finishDate.length() == 1)
				finishDate = "0" + finishDate + "/";
		}
		
		if (finishDateJson.get("month").isJsonNull())
			finishDate = finishDate + "??/";
		else
		{
			String finishDateMonth = releaseDateJson.get("month").getAsString();
			if (finishDateMonth.length() == 1)
				finishDateMonth = "0" + finishDateMonth;
			finishDate = finishDate + finishDateMonth + "/";
		}
		
		if (finishDateJson.get("year").isJsonNull())
			finishDate = finishDate + "????";
		else
			finishDate = finishDate + finishDateJson.get("year").getAsString();
		
		String duration = jo.get("duration").getAsString();
		String exitDay = "?????";

		if (totEp != null && !totEp.isEmpty())
			if (totEp.equals("null") || totEp.equals("0"))
				totEp = "??";
				
		if (duration != null && !duration.isEmpty())
			if (duration.equals("null") || duration.equals("0"))
				duration = "?? min";
			else
				duration += " min";
		if (totEp.equals("1"))
			finishDate = releaseDate;
			
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
		String imageLink = jo.get("coverImage").getAsJsonObject().get("large").getAsString();
		String imageName = AddAnimeDialog.addSaveImage(name, imageLink, list);
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
		AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png", exitDay, Integer.toString(id), "", "", animeType, releaseDate, finishDate, duration, false);
		checkAnimeAlreadyAdded(name, list, data);
		AnimeIndex.lastSelection = anime;
	}

	private String checkDataConflict(String finishDate, String type, String listName)
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

	private void checkAnimeAlreadyAdded(String name, String listName, AnimeData data)
	{
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
			}
			if (AnimeIndex.filtro != 9)
				Filters.removeFilters();
	}

	private String addCompletedAnimeSaveImage(String name, String imageLink)
	{
		imageLink = imageLink.replaceAll("\\\\/", "/");
		String imageName = name.replaceAll("\\\\", "_");
		imageName = imageName.replaceAll("/", "_");
		imageName = imageName.replaceAll(":", "_");
		imageName = imageName.replaceAll("\\*", "_");
		imageName = imageName.replaceAll("\\?", "_");
		imageName = imageName.replaceAll("\"", "_");
		imageName = imageName.replaceAll(">", "_");
		imageName = imageName.replaceAll("<", "_");
		FileManager.saveImage(imageLink, imageName, "Completed");
		return imageName;
	}
	
}
