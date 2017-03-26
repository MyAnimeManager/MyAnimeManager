package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class RecommendationSystem {
	public static void main(String[] args) {
		RecommendationSystem prova = new RecommendationSystem("SirSeleucio");
		ArrayList prove = prova.provaJson();
		System.out.println(prove.size());
	}
	private String username;
	public RecommendationSystem(String username) {
		// TODO Auto-generated constructor stub
		this.username = username;
	}
	public boolean sheExist() throws JSONException {
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
		return result;
	}
	public ArrayList provaJson() {
		JsonParser parser = new JsonParser();
		  JsonElement animeList = parser.parse(request());
		  JsonObject anime = animeList.getAsJsonObject();
		  JsonArray Data = anime.getAsJsonArray("Data");
		  JsonElement PearsonEle = Data.get(0);
		  JsonObject dataObj = PearsonEle.getAsJsonObject();
		  JsonArray PearsonArr = dataObj.getAsJsonArray("Pearson");
		  ArrayList<Integer> ids = new ArrayList<Integer>();
		  for (JsonElement jsonElement : PearsonArr) {
			JsonObject obj = jsonElement.getAsJsonObject();
			Double pearson = obj.get("Pearson").getAsDouble();
			if (pearson > 0.3) {
				int id = obj.get("id").getAsInt();
					ids.add(id);
			}
		}
	      return ids;
	}
}
