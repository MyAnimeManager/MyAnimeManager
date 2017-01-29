package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;


public class RecommendationSystem {
	private String username;
	public RecommendationSystem(String username) {
		// TODO Auto-generated constructor stub
		this.username = username;
	}
	public boolean esiste() throws JSONException {
		String output = request();
		JSONObject jsonObj = new JSONObject(output);
		return (jsonObj.getJSONArray("Data").length() != 0);
	}
	public String request() {
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		BufferedReader rr; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		try
		{
		
			url = new URL("http://affinity.animesos.net/api/result/?username="+username);
			
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
			
		}
		result = StringEscapeUtils.unescapeJava(result);
		return result;
	}
}
