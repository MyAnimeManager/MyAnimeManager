package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import main.AnimeIndex;

public class ConnectionManager
{

	private static final String BASEURL = "https://anilist.co/api/";
	private static final String AUTH = "auth/access_token?grant_type=client_credentials&client_id=samu301295-rjxvs&client_secret=PWynCpeBALVf8GnZEJl3RT";
	private static final String SEARCH = "anime/search/";
	private static final String ANIMEDATA = "anime/";
	private static String token;
	private static boolean tokenExpired = true;

	public static void ConnectAndGetToken() throws java.net.ConnectException, java.net.UnknownHostException
	{
		if (ConnectionManager.token == null || ConnectionManager.tokenExpired)
		{
			URL url; // The URL to read
			HttpURLConnection conn; // The actual connection to the web page
			BufferedReader rr; // Used to read results from the web page
			String line; // An individual line of the web page HTML
			String result = ""; // A long string containing all the HTML
			try
			{
				url = new URL(BASEURL + AUTH);
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

	@Deprecated
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

	@Deprecated
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
			url = new URL(BASEURL + SEARCH + animeQuery + "?access_token=" + ConnectionManager.token);

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

	
	@Deprecated
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
	
	@Deprecated
	private static String getAnimeInformation(int animeID)
	{
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		BufferedReader rr; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		try
		{
			url = new URL(BASEURL + ANIMEDATA + animeID + "?access_token=" + ConnectionManager.token);
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
		
	@Deprecated
	/**
	 * Prende i dati richiesti da una stringa contenente la risposta di anilist
	 * ottenuta con il metodo parseAnimeData di questa stessa classe
	 *
	 * @param dataToGet
	 *            il dato richiesto
	 * @param animeData
	 *            la stringa contenente la rispsota di anilist
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
	
	private static JsonElement getSearchedAnimeGson(String animeToSearch)
	{
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		JsonElement root = null;
		try
		{
			String animeQuery = URLEncoder.encode(animeToSearch, "UTF-8");
			url = new URL(BASEURL + SEARCH + animeQuery + "?access_token=" + ConnectionManager.token);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "My Anime Manager");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.connect();
			
			JsonParser parser = new JsonParser();
			root = parser.parse((new InputStreamReader(conn.getInputStream(), "UTF-8")));
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
					AnimeSearchGson(animeToSearch);
					
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
		return root;
	}

	public static HashMap<String, Integer> AnimeSearchGson(String anime)
	{
		HashMap<String, Integer> animeList = new HashMap<String, Integer>();
		JsonElement element = getSearchedAnimeGson(anime);
		if (element != null && element.isJsonArray())
		{
			JsonArray animes = element.getAsJsonArray();			
			for (JsonElement results : animes) 
			{
			    String title = results.getAsJsonObject().get("title_romaji").getAsString();
			    int id = results.getAsJsonObject().get("id").getAsInt();
			    animeList.put(title, id);
			}			
		}
		return animeList;
	}

	private static JsonElement getAnimeInformationGson(int animeID, String dataToGet)
	{
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		JsonElement root = null;
		try
		{
			url = new URL(BASEURL + ANIMEDATA + animeID + "?access_token=" + ConnectionManager.token);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(15 * 1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "My Anime Manager");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.connect();
			
			JsonParser parser = new JsonParser();
			root = parser.parse((new InputStreamReader(conn.getInputStream(), "UTF-8")));
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
					getAnimeDataGson(dataToGet, animeID);
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
		return root;
	}

	public static String getAnimeDataGson(String dataToGet, int animeId)
	{
		String data = null;
		JsonElement element = getAnimeInformationGson(animeId, dataToGet);
		if (element != null)
		{
			JsonElement dataElement = element.getAsJsonObject().get(dataToGet);
			if (dataElement != null && !dataElement.isJsonNull())
				data = dataElement.getAsString();
			else
				data = "null";
//			System.out.println(data);
		}
		return data;
	}
}