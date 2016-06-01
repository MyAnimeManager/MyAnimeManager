package util.task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.SwingWorker;
import main.AnimeIndex;
import org.apache.commons.lang3.StringEscapeUtils;
import util.MAMUtil;


public class MAMTeamAdvert extends SwingWorker
{
	private String advert;
	@Override
	protected Object doInBackground() throws Exception
	{
		advert = loadMAMTeamNews();
		return null;
	}

	@Override
	protected void done()
	{
		AnimeIndex.lblMAMNews.setText(advert);
	}
	private String loadMAMTeamNews()
	{
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		BufferedReader rr; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		String address = "http://myanimemanagerupdate.webstarts.com/advertising.html";
		try
		{
			url = new URL(address);
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
		result = result.substring(result.indexOf("[advert]") + 8, result.indexOf("[/advert]"));
		return result;
	}
}