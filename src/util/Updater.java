package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringEscapeUtils;

public class Updater
{    
	   
	private final static String VERSION_URL = "http://myanimemanagerupdate.webstarts.com/version.html";
    private final static String HISTORY_URL = "http://myanimemanagerupdate.webstarts.com/history.html";
    private final static String DOWNLOAD_URL = "http://myanimemanagerupdate.webstarts.com/downloadUrl.html";
    
    
    public static String getLatestVersion() throws Exception
    {
        String data = getData(VERSION_URL);
        String latestVer = data.substring(data.indexOf("[version]")+9,data.indexOf("[/version]"));
        return latestVer;
    }
    
    
    public static String getWhatsNew()
    {
        String data = getData(HISTORY_URL);
        String whatsNew = data.substring(data.indexOf("[history]")+9,data.indexOf("[/history]"));
        whatsNew = StringEscapeUtils.unescapeHtml4(whatsNew);
        return whatsNew;
    }
    
    public static String getDownloadLink()
    {
    	String data = getData(DOWNLOAD_URL);
    	String downloadUrl = data.substring(data.indexOf("[url]")+5,data.indexOf("[/url]"));
    	return downloadUrl;
    }
    
    private static String getData(String address)
	{
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		BufferedReader rr; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		try {
			url = new URL(address);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			rr = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			while ((line = rr.readLine()) != null) {
				result += line ;
			}
			rr.close();
		} catch (Exception e) {
			System.out.println("Errore");
	    }
		result = StringEscapeUtils.unescapeJava(result);
		return result;
	}

}
