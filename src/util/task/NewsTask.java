package util.task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;

import main.AnimeIndex;

public class NewsTask extends SwingWorker {
	
	private String news;
	private final String RAD_URL = "http://redanimedatabase.forumcommunity.net/";
	@Override
	protected Object doInBackground() throws Exception
	{
		String htmlCode = getData();
		System.out.println(htmlCode);
		news = patternMatcherGroupHtml(htmlCode);
		System.out.println(news);
		return null;
	}
	
	protected void done()
	{
		AnimeIndex.newsBoardDialog.setNews(news);
	}
	
	private String patternMatcherGroupHtml(String htmlCode)
	  {
		String start = "<ol id=\"tagObject\" class=\"list\" style=\"height:300px;overflow:auto\">";
		String end = "</ol>";
	    // the pattern we want to search for
	    Pattern p = Pattern.compile(start + "(\\S+)" + end);
	    Matcher m = p.matcher(htmlCode);
	    String codeGroup = "";
	    // if we find a match, get the group 
	    if (m.find())
	    {
	      // get the matching group
	      codeGroup = m.group(1);	     
	    }
	    return codeGroup;
	 
	  }
	
	private String getData()
	{
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		BufferedReader rr; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		try {
			url = new URL(RAD_URL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)" );
			rr = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			while ((line = rr.readLine()) != null) {
				result += line ;
				System.out.println(line);
			}
			rr.close();
		} catch (Exception e) {
			e.printStackTrace();
	    }
		return result;
	}
}
