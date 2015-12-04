package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringEscapeUtils;

public class SuggestionHelper
{

	private final static String SUGGESTION_URL = "http://myanimemanagerupdate.webstarts.com/suggestion.html";

	public static String getPackVersion(String data) throws Exception
	{
		String pack = data.substring(data.indexOf("[pack]") + 6, data.indexOf("[/pack]"));
		return pack;
	}

	public static String getSuggestion(int suggestionNumber, String data) throws Exception
	{
		String suggestion = data.substring(data.indexOf("[suggestion_" + suggestionNumber + "]") + 14, data.indexOf("[/suggestion_" + suggestionNumber + "]"));
		return suggestion;
	}

	public static String getDescription(int suggestionNumber, String data) throws Exception
	{
		String description = data.substring(data.indexOf("[description_" + suggestionNumber + "]") + 15, data.indexOf("[/description_" + suggestionNumber + "]"));
		return description;
	}

	public static String getLink(int suggestionNumber, String data) throws Exception
	{
		String link = data.substring(data.indexOf("[link_" + suggestionNumber + "]") + 8, data.indexOf("[/link_" + suggestionNumber + "]"));
		return link;
	}

	public static String getId(int suggestionNumber, String data) throws Exception
	{
		String id = data.substring(data.indexOf("[id_" + suggestionNumber + "]") + 6, data.indexOf("[/id_" + suggestionNumber + "]"));
		return id;
	}

	public static String getData()
	{
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		BufferedReader rr; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		try
		{
			url = new URL(SUGGESTION_URL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			rr = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((line = rr.readLine()) != null)
				result += line;
			rr.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			MAMUtil.writeLog(e);
		}
		result = StringEscapeUtils.unescapeJava(result);
		return result;
	}

}
