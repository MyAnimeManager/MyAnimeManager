package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;
import org.json.XML;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import main.AnimeIndex;

public class ConnectionManager
{

	private static final String ANI_BASEURL = "https://anilist.co/api/";
	private static final String AUTH = "auth/access_token?grant_type=client_credentials&client_id=samu301295-rjxvs&client_secret=PWynCpeBALVf8GnZEJl3RT";
	private static final String SEARCH = "anime/search/";
	private static final String ANIMEDATA = "anime/";
	
	private static final String MAL_BASEURL = "http://myanimelist.net/api/";
	private static final String VERIFY_CREDENTIALS_MAL = "account/verify_credentials.xml";
	private static final String ADD_ANIME_MAL = "animelist/add/";
	private static final String UPDATE_ANIME_MAL = "animelist/update/";
	private static final String SEARCH_MAL = "anime/search.xml?q=";
	
	private static String token;
	private static boolean tokenExpired = true;

	public static void ConnectAndGetToken() throws java.net.ConnectException, java.net.UnknownHostException
	{
		if (token == null || tokenExpired)
		{
			URL url; // The URL to read
			HttpURLConnection conn; // The actual connection to the web page
			BufferedReader rr; // Used to read results from the web page
			String line; // An individual line of the web page HTML
			String result = ""; // A long string containing all the HTML
			try
			{
				url = new URL(ANI_BASEURL + AUTH);
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("User-Agent", "My Anime Manager");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				rr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line = rr.readLine()) != null)
					result += line;
				rr.close();

			}
			catch (java.net.SocketTimeoutException timeout)
			{
				JOptionPane.showMessageDialog(AnimeIndex.frame, "Errore durante la connessione! Potrebbe dipendere dalla tua connessione o dal sito di Anilist.", "Errore!", JOptionPane.ERROR_MESSAGE);
			}
			catch (java.net.ConnectException | java.net.UnknownHostException e1)
			{
				throw e1;
			}
			catch (IOException e)
			{
				e.printStackTrace();
				MAMUtil.writeLog(e);
			}
			// System.out.println(result); java.net.ConnectException
			SaveAccessToken(result);
			ConnectionManager.tokenExpired = false;
		}
	}

	private static void SaveAccessToken(String result)
	{
		Scanner sc = new Scanner(result);
		sc.useDelimiter(":|,");
		while (sc.hasNext())
		{
			String str = sc.next();
			if (str.equals("{\"access_token\""))
			{
				String tokenString = sc.next();
				ConnectionManager.token = tokenString.substring(1, tokenString.length() - 1);
			}
		}
		sc.close();
	}

	
	public static HashMap<String, Integer> AnimeSearch(String anime)
	{
		HashMap<String, Integer> animeList = new HashMap<String, Integer>();

		String search = getSearchedAnime(anime);

		Scanner sc = new Scanner(search);
		sc.useDelimiter("\",\"|\\[\\{\"|\"\\]\\}|\":\"|\":|,\"|\\}\\]|\\},\\{\"");
		while (sc.hasNext())
		{
			String word = sc.next();
			if (word.equalsIgnoreCase("id"))
			{
				int id = Integer.parseInt(sc.next());
				String word2 = sc.next();
				if (word2.equalsIgnoreCase("title_romaji"))
				{
					String name = sc.next();
					animeList.put(name, id);
				}
			}
		}
		sc.close();
		// System.out.println(animeList);
		sc.close();
		return animeList;
	}

	
	private static String getSearchedAnime(String animeToSearch)
	{
		if (token == null)
		{
			try
			{
				ConnectAndGetToken();
			}
			catch (ConnectException e)
			{
				MAMUtil.writeLog(e);
				e.printStackTrace();
			}
			catch (UnknownHostException e)
			{
				MAMUtil.writeLog(e);
				e.printStackTrace();
			}
		}
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		BufferedReader rr; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		try
		{
			String animeQuery = URLEncoder.encode(animeToSearch, "UTF-8");
			url = new URL(ANI_BASEURL + SEARCH + animeQuery + "?access_token=" + ConnectionManager.token);

			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "My Anime Manager");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			rr = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((line = rr.readLine()) != null)
				result += line + "\r\n";
			rr.close();
		}
		catch (Exception e)
		{
			try
			{
				if (conn.getResponseCode() == 401)
				{
					System.out.println("Errore 401");
					System.out.println("Token scaduto, richiesta nuovo token");
					ConnectionManager.tokenExpired = true;
					ConnectAndGetToken();
					AnimeSearch(animeToSearch);
				}
				else
				{
					e.printStackTrace();
					MAMUtil.writeLog(e);
				}

			}
			catch (IOException e1)
			{
				MAMUtil.writeLog(e1);
				e1.printStackTrace();
			}
		}
		result = StringEscapeUtils.unescapeJava(result);
		return result;
	}

	
	
	/**
	 * ottiene la risposta contentente tutte le informazioni
	 *
	 * @param id
	 *            l'id dell'anime di cui si vogliono le informazioni
	 */
	public static String parseAnimeData(int id)
	{
		String result = "";
		String animeInformation = getAnimeInformation(id);
		Scanner sc1 = new Scanner(animeInformation);
		sc1.useDelimiter("\",\"|\\{\"|\"\\}|\":\"|\":|,\"|\\}|\\},\\{\"");
		while (sc1.hasNext())
			result += sc1.next() + "\r\n";
		sc1.close();
		return result;
	}
	
	
	private static String getAnimeInformation(int animeID)
	{
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		BufferedReader rr; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		try
		{
			url = new URL(ANI_BASEURL + ANIMEDATA + animeID + "?access_token=" + ConnectionManager.token);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(15 * 1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "My Anime Manager");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			rr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rr.readLine()) != null)
				result += line;
			rr.close();
		}
		catch (java.net.SocketTimeoutException timeout)
		{
			JOptionPane.showMessageDialog(AnimeIndex.frame, "Errore durante la connessione! Potrebbe dipendere dalla tua connessione o dal sito di Anilist.", "Errore!", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e)
		{
			try
			{
				if (conn.getResponseCode() == 401)
				{
					System.out.println("Errore 401");
					System.out.println("Token scaduto, richiesta nuovo token");
					ConnectionManager.tokenExpired = true;
					ConnectAndGetToken();
					getAnimeInformation(animeID);
				}
				else
				{
					e.printStackTrace();
					MAMUtil.writeLog(e);
				}
			}
			catch (IOException e1)
			{

				e1.printStackTrace();
				MAMUtil.writeLog(e1);
			}
		}
		result = StringEscapeUtils.unescapeJava(result);
		return result;
	}
		
	
	/**
	 * Prende i dati richiesti da una stringa contenente la risposta di anilist
	 * ottenuta con il metodo parseAnimeData di questa stessa classe
	 *
	 * @param dataToGet
	 *            il dato richiesto
	 * @param animeData
	 *            la stringa contenente la risposta di anilist
	 */
	public static String getAnimeData(String dataToGet, String animeData)
	{
		String data = null;
		Scanner sc = new Scanner(animeData);

		while (sc.hasNextLine())
		{
			String word = sc.nextLine();
			if (word.equalsIgnoreCase(dataToGet))
				data = sc.nextLine();
		}
		sc.close();
		return data;
	}
	
	private static boolean verifyCredentialsMAL(String username, String password) throws IOException
	{
		boolean validCredentials = false;
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		try
		{
			url = new URL(MAL_BASEURL + VERIFY_CREDENTIALS_MAL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "My Anime Manager");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			String auth = username + ":" + password;
			String authEncoded = new String(Base64.getEncoder().encode(auth.getBytes()));
			conn.setRequestProperty("Authorization" , "Basic " + authEncoded);
			System.out.println("Auth Success = " + (conn.getResponseCode() == 200));	

		}
		catch (java.net.SocketTimeoutException timeout)
		{
			JOptionPane.showMessageDialog(AnimeIndex.frame, "Errore durante la connessione! Potrebbe dipendere dalla tua connessione o dal sito di MyAnimeList.", "Errore!", JOptionPane.ERROR_MESSAGE);
		}
		catch (java.net.ConnectException | java.net.UnknownHostException e1)
		{
			throw e1;
		}
		catch (IOException e)
		{
			if (conn.getResponseCode() == 401)
			{
				System.out.println("Dati non validi");
				JOptionPane.showMessageDialog(AnimeIndex.frame, "Nome Utente o Password errata!", "Errore!", JOptionPane.ERROR_MESSAGE);
			}
		}
		validCredentials = true;
		return validCredentials;
	}

	public static void addAnimeMAL(String username, String password, int animeID, String episode, String status, String comments) throws IOException
	{
		boolean validCredentials = verifyCredentialsMAL(username, password);
		if (validCredentials)
		{
			URL url;// The URL to read
			HttpURLConnection conn = null;// The actual connection to the web page
			try
			{
				String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
						+ "<entry>" 
						+ "<episode>" + episode + "</episode>" 
						+ "<status>" + status + "</status>" 
						+ "<score></score>" 
						+ "<storage_type></storage_type>" 
						+ "<storage_value></storage_value>" 
						+ "<times_rewatched></times_rewatched>" 
						+ "<rewatch_value></rewatch_value>" 
						+ "<date_start></date_start>" 
						+ "<date_finish></date_finish>" 
						+ "<priority></priority>" 
						+ "<enable_discussion></enable_discussion>" 
						+ "<enable_rewatching></enable_rewatching>" 
						+ "<comments>" + comments + "</comments>" 
						+ "<fansub_group></fansub_group>" 
						+ "<tags></tags>" 
						+ "</entry>";
				String urlEncoded = URLEncoder.encode(xmlData, "utf-8");
				url = new URL(MAL_BASEURL + ADD_ANIME_MAL + animeID + ".xml" + "?data=" + urlEncoded); 
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("GET");
				conn.setRequestProperty("User-Agent", "My Anime Manager");
				conn.setRequestProperty("Content-Type", "application/xml");

				String auth = username + ":" + password;
				String authEncoded = new String(Base64.getEncoder().encode(auth.getBytes()));
				conn.setRequestProperty("Authorization", "Basic " + authEncoded);
				int respCode = conn.getResponseCode();
				System.out.println("Add Success = " + (respCode == 201) + ", Code returned = " + respCode);		
			}
			catch (java.net.SocketTimeoutException timeout)
			{
				JOptionPane.showMessageDialog(AnimeIndex.frame, "Errore durante la connessione! Potrebbe dipendere dalla tua connessione o dal sito di MyAnimeList.", "Errore!", JOptionPane.ERROR_MESSAGE);
			}
			catch (java.net.ConnectException | java.net.UnknownHostException e1)
			{
				throw e1;
			}
			catch (IOException e)
			{
				if (conn != null && conn.getResponseCode() == 401)
				{
					System.out.println("Dati non validi");
					JOptionPane.showMessageDialog(AnimeIndex.frame, "Nome Utente o Password errata!", "Errore!", JOptionPane.ERROR_MESSAGE);
				}
			} 
		}

	}
	
	public static void updateAnimeMAL(String username, String password, String animeID, String episode, String status, String score, String tags, String comments) throws IOException
	{
		boolean validCredentials = verifyCredentialsMAL(username, password);
		if (validCredentials)
		{
			URL url;// The URL to read
			HttpURLConnection conn = null;// The actual connection to the web page
			try
			{
				String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
						+ "<entry>" 
						+ "<episode>" + episode + "</episode>" 
						+ "<status>" + status + "</status>" 
						+ "<score>" + score + "</score>" 
						+ "<storage_type></storage_type>" 
						+ "<storage_value></storage_value>" 
						+ "<times_rewatched></times_rewatched>" 
						+ "<rewatch_value></rewatch_value>" 
						+ "<date_start></date_start>" 
						+ "<date_finish></date_finish>" 
						+ "<priority></priority>" 
						+ "<enable_discussion></enable_discussion>" 
						+ "<enable_rewatching></enable_rewatching>" 
						+ "<comments>" + comments + "</comments>" 
						+ "<fansub_group></fansub_group>" 
						+ "<tags>" + tags + "</tags>" 
						+ "</entry>";
				String urlEncoded = URLEncoder.encode(xmlData, "utf-8");
				url = new URL(MAL_BASEURL + UPDATE_ANIME_MAL + animeID + ".xml" + "?data=" + urlEncoded); 
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("GET");
				conn.setRequestProperty("User-Agent", "My Anime Manager");
				conn.setRequestProperty("Content-Type", "application/xml");

				String auth = username + ":" + password;
				String authEncoded = new String(Base64.getEncoder().encode(auth.getBytes()));
				conn.setRequestProperty("Authorization", "Basic " + authEncoded);
				System.out.println("Success = " + (conn.getResponseCode() == 200));	
				
			}
			catch (java.net.SocketTimeoutException timeout)
			{
				JOptionPane.showMessageDialog(AnimeIndex.frame, "Errore durante la connessione! Potrebbe dipendere dalla tua connessione o dal sito di MyAnimeList.", "Errore!", JOptionPane.ERROR_MESSAGE);
			}
			catch (java.net.ConnectException | java.net.UnknownHostException e1)
			{
				throw e1;
			}
			catch (IOException e)
			{
				if (conn != null && conn.getResponseCode() == 401)
				{
					System.out.println("Dati non validi");
					JOptionPane.showMessageDialog(AnimeIndex.frame, "Nome Utente o Password errata!", "Errore!", JOptionPane.ERROR_MESSAGE);
				}
			} 
		}

	}
	
	private static String searchAnimeMAL(String username, String password, String anime) throws IOException
	{
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		BufferedReader rr; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		try
		{	
			anime = URLEncoder.encode(anime, "utf-8");
			url = new URL(MAL_BASEURL + SEARCH_MAL + anime);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "My Anime Manager");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			String auth = username + ":" + password;
			String authEncoded = new String(Base64.getEncoder().encode(auth.getBytes()));
			conn.setRequestProperty("Authorization" , "Basic " + authEncoded);
			
			rr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rr.readLine()) != null)
				result += line;
			rr.close();

		}
		catch (java.net.SocketTimeoutException timeout)
		{
			JOptionPane.showMessageDialog(AnimeIndex.frame, "Errore durante la connessione! Potrebbe dipendere dalla tua connessione o dal sito di MyAnimeList.", "Errore!", JOptionPane.ERROR_MESSAGE);
		}
		catch (java.net.ConnectException | java.net.UnknownHostException e1)
		{
			throw e1;
		}
		catch (IOException e)
		{
			if (conn.getResponseCode() == 401)
			{
				System.out.println("Dati non validi");
				JOptionPane.showMessageDialog(AnimeIndex.frame, "Nome Utente o Password errata!", "Errore!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		return result;
	}
	
	public static HashMap<String, Integer> getAnimeSearchedMAL(String username, String password, String anime)
	{
		HashMap<String, Integer> map = new HashMap<String,Integer>();
			
		JSONObject xmlJSONObj;
		String json = "";
		String xml = "";
		try
		{
			xml = searchAnimeMAL(username, password, anime);
			xmlJSONObj = XML.toJSONObject(xml);
			json = xmlJSONObj.toString(4);
		}
		catch (Exception e)
		{
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
        JsonParser parser = new JsonParser();
        JsonObject root = parser.parse(json).getAsJsonObject();
        System.out.println(root.get("anime").getAsJsonObject().get("entry"));
        JsonArray searchedAnime = root.get("anime").getAsJsonObject().get("entry").getAsJsonArray();
        for (JsonElement obj : searchedAnime)
        {
        	String name = obj.getAsJsonObject().get("title").getAsString();
        	int id = obj.getAsJsonObject().get("id").getAsInt();
        	map.put(name, id);
        }
		return map;
	}
}