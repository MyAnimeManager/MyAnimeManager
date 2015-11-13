package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

public class ConnectionManager
{
	private static final String BASEURL = "https://anilist.co/api/";
	private static final String AUTH = "auth/access_token?grant_type=client_credentials&client_id=samu301295-rjxvs&client_secret=PWynCpeBALVf8GnZEJl3RT";
	private static final String SEARCH = "anime/search/";
	private static final String ANIMEDATA= "anime/";
	private static String token;
	private static boolean tokenExpired = true;
	
	

	public static void ConnectAndGetToken() throws java.net.ConnectException, java.net.UnknownHostException
	{
		    if (ConnectionManager.token == null || ConnectionManager.tokenExpired) {
				URL url; // The URL to read
				HttpURLConnection conn; // The actual connection to the web page
				BufferedReader rr; // Used to read results from the web page
				String line; // An individual line of the web page HTML
				String result = ""; // A long string containing all the HTML
				try {
					url = new URL(BASEURL + AUTH);
					conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("POST");
					conn.setRequestProperty("User-Agent", "My Anime Index/1.0");
					conn.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					rr = new BufferedReader(new InputStreamReader(
							conn.getInputStream()));
					while ((line = rr.readLine()) != null) {
						result += line;
					}
					rr.close();
					
				}
				catch (java.net.ConnectException | java.net.UnknownHostException e1) {
					throw e1;
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				//System.out.println(result); java.net.ConnectException
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
	
	public static HashMap<String,Integer> AnimeSearch(String anime)
	{
		HashMap<String,Integer> animeList = new HashMap<String,Integer>();
		
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
		    //System.out.println(animeList);    
		sc.close();
		return animeList;
	}
	
	private static String getSearchedAnime(String animeToSearch)
	{
		URL url; // The URL to read
	    HttpURLConnection conn = null; // The actual connection to the web page
	    BufferedReader rr; // Used to read results from the web page
	    String line; // An individual line of the web page HTML
	    String result = ""; // A long string containing all the HTML
	    try {
		    String animeQuery = URLEncoder.encode(animeToSearch, "UTF-8");
		    url = new URL(BASEURL + SEARCH + animeQuery + "?access_token=" + ConnectionManager.token);
		    	   		    
		    conn = (HttpURLConnection) url.openConnection();
	        conn.setDoOutput(true);
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("User-Agent", "My Anime Index/1.0");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        rr = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
	        while ((line = rr.readLine()) != null) {
	        	result += line + "\r\n";	      
	        }
	    rr.close();
	    } catch (Exception e) {
	    	try {
				if (conn.getResponseCode() == 401)
				{
						System.out.println("Errore 401");
						System.out.println("Token scaduto, richiesta nuovo token");
						ConnectionManager.tokenExpired = true;
						ConnectAndGetToken();
						AnimeSearch(animeToSearch);
				}
				else
					e.printStackTrace();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			} 
	    }
	    result = StringEscapeUtils.unescapeJava(result);
	    return result;
	}
	/**
	 * ottiene la risposta contentente tutte le informazioni
	 * @param id l'id dell'anime di cui si vogliono le informazioni
	 */
	
	public static JSONObject getAnimeInformation(int animeID)
	{
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		BufferedReader rr; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		try {
			url = new URL(BASEURL + ANIMEDATA + animeID + "?access_token=" + ConnectionManager.token );
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "My Anime Index/1.0");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			rr = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = rr.readLine()) != null) {
				result += line;
			}
			rr.close();
		} catch (Exception e) {
	    	try {
				if (conn.getResponseCode() == 401)
				{
						System.out.println("Errore 401");
						System.out.println("Token scaduto, richiesta nuovo token");
						ConnectionManager.tokenExpired = true;
						ConnectAndGetToken();
						getAnimeInformation(animeID);
				}
				else
					e.printStackTrace();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			} 
	    }
		//TODO fixare problema delle "
//		result = StringEscapeUtils.unescapeJava(result);
		JSONObject json = new JSONObject(result);
		return json;
	}
}