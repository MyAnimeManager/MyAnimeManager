package util.task;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import org.apache.commons.lang3.StringEscapeUtils;
import main.AnimeIndex;
import util.MAMUtil;

public class NewNotifierTask extends SwingWorker
{
	public static String packNumber;
	private boolean newSongs;
	private String lastUpdateDescription;

	@Override
	protected Object doInBackground() throws Exception
	{
//		String data = SuggestionHelper.getData();
//		packNumber = SuggestionHelper.getPackVersion(data);
		newSongs = newSongs();
		return null;
	}

	@Override
	public void done()
	{
//		if (!packNumber.equalsIgnoreCase(AnimeIndex.appProp.getProperty("Suggestions_Pack_Number")))
//		{
//			JOptionPane.showMessageDialog(AnimeIndex.mainPanel, "Nuovi anime sono disponibili\n\rnella sezione \"Anime Consigliati\" !!!", "Nuovi Anime !!!", JOptionPane.INFORMATION_MESSAGE);
//			AnimeIndex.appProp.setProperty("Suggestions_Pack_Number", packNumber);
//		}
		if (newSongs)
		{
			JTextArea textArea = new JTextArea(8, 30);
			try
			{
			    textArea.setText(lastUpdateDescription);
			}
			catch (Exception e1)
			{
				MAMUtil.writeLog(e1);
				e1.printStackTrace();
			}
			textArea.setFont(MAMUtil.loadFont().deriveFont(12f));
			textArea.setEditable(false);
			textArea.setOpaque(false);
			JScrollPane scrollPane = new JScrollPane(textArea);
		    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
			scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
		    JOptionPane.showMessageDialog(AnimeIndex.mainPanel, scrollPane, "Nuove musiche sono disponibili in \"My Anime Musics\" !!!", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	private boolean newSongs()
	{
		URL url;
		HttpURLConnection conn = null;
		BufferedReader rr;
		String line;
		String result = "";
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
		lastUpdateDescription = result.substring(result.indexOf("[lastUpdateDescription]") + 23, result.indexOf("[/lastUpdateDescription]"));
		if (!shouldNew.equalsIgnoreCase(AnimeIndex.appProp.getProperty("New_Musics")))
		{
			AnimeIndex.appProp.setProperty("New_Musics", shouldNew);
			return true;
		}
		return false;	
	}
}
