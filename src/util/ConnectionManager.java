package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
	@Deprecated
	private static final String ANI_BASEURL = "https://anilist.co/api/";
	@Deprecated
	private static final String AUTH = "auth/access_token";
	@Deprecated
	static final String SEARCH = "anime/search/";
	@Deprecated
	private static final String ANIMEDATA = "anime/";
	
	private static final String ANIV2_BASEURL = "https://graphql.anilist.co";
	
	private static final String MAL_BASEURL = "http://myanimelist.net/api/";
	private static final String VERIFY_CREDENTIALS_MAL = "account/verify_credentials.xml";
	private static final String ADD_ANIME_MAL = "animelist/add/";
	private static final String UPDATE_ANIME_MAL = "animelist/update/";
	private static final String SEARCH_MAL = "anime/search.xml?q=";
	@Deprecated
	private static String token;
	@Deprecated
	private static boolean tokenExpired = true;
	@Deprecated
	private static int attempts = 0;
	
	@Deprecated
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
				OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");   
				out.write("grant_type=client_credentials&client_id=samu301295-rjxvs&client_secret=PWynCpeBALVf8GnZEJl3RT");
				out.close();
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
	@Deprecated
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
			String animeQuery = URLEncoder.encode(animeToSearch.replace(".", "\\.").replace("/", "\\ ").replace("-","\\-").replace("!","\\!"), "UTF-8");
		
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
					attempts++;
					if (attempts > 5)
					{
						ConnectAndGetToken();
						AnimeSearch(animeToSearch);
						attempts = 0;
					}
					else
						JOptionPane.showMessageDialog(AnimeIndex.frame, "Errore durante la connessione! Potrebbe dipendere dalla tua connessione o dal sito di Anilist.", "Errore!", JOptionPane.ERROR_MESSAGE);
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
	 * Ricerca l'anime indicato nel database di anilist.
	 * @param searchedAnime L'anime da cercare
	 * @return Una mappa <Nome,ID> contenente i vari risultati
	**/
	public static LinkedHashMap<String, Integer> SearchAnime(String searchedAnime)
	{
		String query = getSearchQuery(searchedAnime);
		HttpURLConnection conn = null;
		LinkedHashMap<String, Integer> animeList = new LinkedHashMap<String, Integer>();
		try {
			conn = prepareConnection();
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");   
			out.write(query);
			out.close();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuffer jsonString = new StringBuffer();
	        String line;
	        while ((line = br.readLine()) != null) {
	                jsonString.append(line);
	        }
	        br.close();
			
	        JsonParser jsonParser = new JsonParser();
	        JsonObject jo = (JsonObject)jsonParser.parse(jsonString.toString());
	        JsonObject data = jo.get("data").getAsJsonObject();
	        JsonObject page = data.get("Page").getAsJsonObject();
	        JsonArray media = page.get("media").getAsJsonArray();
	        for (JsonElement jsonElement : media) {
	        	JsonObject obj = jsonElement.getAsJsonObject();
	        	int id = obj.get("id").getAsInt();
	        	String title = obj.get("title").getAsJsonObject().get("romaji").getAsString();
				animeList.put(title, id);
			}
//	        Gson gson = new GsonBuilder().setPrettyPrinting().create();  
//	        String json = gson.toJson(media);
//	        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\Desktop/Output.json"), "UTF-8"));
//	        output.write(json);
//	        output.close();

		} catch (Exception e) {
			InputStream error = conn.getErrorStream();
			System.out.print(MAMUtil.getStringFromInputStream(error));
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
		return animeList;

	}
	
	public static JsonObject getAnimeData(int id)
	{
		String query = getDataQuery(id);
		HttpURLConnection conn = null;
		try {
			conn = prepareConnection();
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");   
			out.write(query);
			out.close();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuffer jsonString = new StringBuffer();
	        String line;
	        while ((line = br.readLine()) != null) {
	                jsonString.append(line);
	        }
	        br.close();
	        System.out.print(jsonString);
	        
	        JsonParser jsonParser = new JsonParser();
	        JsonObject jo = (JsonObject)jsonParser.parse(jsonString.toString());
	        JsonObject data = jo.get("data").getAsJsonObject();
	        JsonObject media = data.get("Media").getAsJsonObject();
	        return media;

		} catch (Exception e) {
//			InputStream error = conn.getErrorStream();
//			System.out.print(MAMUtil.getStringFromInputStream(error));
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
		return null;
	}
	
	//VECCHI METODI
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
					attempts++;
					if (attempts > 5)
					{
						ConnectAndGetToken();
						getAnimeInformation(animeID);
						attempts = 0;
					}
					else
						JOptionPane.showMessageDialog(AnimeIndex.frame, "Errore durante la connessione! Potrebbe dipendere dalla tua connessione o dal sito di Anilist.", "Errore!", JOptionPane.ERROR_MESSAGE);
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
	
	@SuppressWarnings("unused")
	public static boolean verifyCredentialsMAL(String username, String password) throws IOException
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
			int respCode = conn.getResponseCode();

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

	@SuppressWarnings("unused")
	public static void addAnimeMAL(String username, String password, int animeID, String episode, String status, String comments) throws IOException
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
			anime = URLEncoder.encode(anime, "UTF-8");
			url = new URL(MAL_BASEURL + SEARCH_MAL + anime);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "My Anime Manager");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			String auth = username + ":" + password;
			String authEncoded = new String(Base64.getEncoder().encode(auth.getBytes()));
			conn.setRequestProperty("Authorization" , "Basic " + authEncoded);
			
			rr = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
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
        if (root.has("anime") && root.get("anime").getAsJsonObject().get("entry").isJsonArray())
        {	
		    JsonArray searchedAnime = root.get("anime").getAsJsonObject().get("entry").getAsJsonArray();
		    for (JsonElement obj : searchedAnime)
		    {
		    	String name = obj.getAsJsonObject().get("title").getAsString();
		    	int id = obj.getAsJsonObject().get("id").getAsInt();
		    	map.put(name, id);
		    }
        }
        else if (root.has("anime"))
        {
        	JsonObject obj = root.get("anime").getAsJsonObject().get("entry").getAsJsonObject();
	    	int id = obj.getAsJsonObject().get("id").getAsInt();
	    	map.put(anime, id);
        }
		return map;
	}
	
	/**
	 * Prepare la connessione al server di anilist
	 * @return L'oggetto della connessione con i parametri settati
	 * @throws Exception
	 */
	private static HttpURLConnection prepareConnection() throws Exception
	{
		HttpURLConnection conn = null;
		URL url = new URL(ANIV2_BASEURL);	
		conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", "My Anime Manager");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		return conn;
	}
	
	/**
	 * Ritorna la query della ricerca.
	 * @param searchedAnime L'anime da cercare
	 * @return La stringa da inviare come query
	 */
	private static String getSearchQuery(String searchedAnime)
	{
		InputStream file = ConnectionManager.class.getResourceAsStream("/Ricerca.txt");
		Scanner scan = null;
		String query = "";
		scan = new Scanner(file);
		while (scan.hasNextLine())
		{
			query = query + scan.nextLine();
		}
		scan.close();
		query = query.replace("$ricerca$", "\\\"" + searchedAnime + "\\\"");
		return query;
	}
	
	/**
	 * Ritorna la query dei dati dell'anime.
	 * @param IDAnime L'ID dell'anime
	 * @return La stringa da inviare come query
	 */
	private static String getDataQuery(int IDAnime)
	{
		InputStream file = ConnectionManager.class.getResourceAsStream("/DatiAnime.txt");
		Scanner scan = null;
		String query = "";
		scan = new Scanner(file);
		while (scan.hasNextLine())
		{
			query = query + scan.nextLine();
		}
		scan.close();
		query = query.replace("$id$", "" + IDAnime);
		return query;
	}

}
