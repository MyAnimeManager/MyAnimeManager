package util.task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.apache.commons.lang3.StringEscapeUtils;

import main.AnimeIndex;
import util.MAMUtil;
import util.SuggestionHelper;

public class NewNotifierTask extends SwingWorker
{

	public static String packNumber;
	private boolean newSongs;

	@Override
	protected Object doInBackground() throws Exception
	{
		String data = SuggestionHelper.getData();
		packNumber = SuggestionHelper.getPackVersion(data);
		newSongs = newSongs();
		return null;
	}

	@Override
	public void done()
	{
		if (!packNumber.equalsIgnoreCase(AnimeIndex.appProp.getProperty("Suggestions_Pack_Number")))
		{
			JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Nuovi anime sono disponibili\n\rnella sezione \"Anime Consigliati\" !!!", "Nuovi Anime !!!", JOptionPane.INFORMATION_MESSAGE);
			AnimeIndex.appProp.setProperty("Suggestions_Pack_Number", packNumber);
		}
		if (newSongs)
			JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Nuove musiche sono disponibili\n\rin \"My Anime Music\" !!!", "Nuove Musiche !!!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private boolean newSongs()
	{
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		BufferedReader rr; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		String address = "http://myanimemanagerupdate.webstarts.com/music.html";
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
		String shouldNew = result.substring(result.indexOf("[newMusic]") + 10, result.indexOf("[/newMusic]"));
		if (!shouldNew.equalsIgnoreCase(AnimeIndex.appProp.getProperty("New_Musics")))
		{
			AnimeIndex.appProp.setProperty("New_Musics", shouldNew);
			return true;
		}
		return false;	
	}
}
