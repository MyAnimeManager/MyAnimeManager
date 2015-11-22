package util.task;

import javax.swing.SwingWorker;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlOrderedList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import main.AnimeIndex;

public class NewsTask extends SwingWorker {
	
	private String news;
	private final String RAD_URL = "http://redanimedatabase.forumcommunity.net/";
	@Override
	protected Object doInBackground() throws Exception
	{
		news = getPageAfterJavaScript();
		return null;
	}
	
	protected void done()
	{
		AnimeIndex.newsBoardDialog.setNews(news);
	}
		
	public String getPageAfterJavaScript() 
	{
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /* comment out to turn off annoying htmlunit warnings */
        java.util.logging.Logger.getLogger("org.apache.http.client.protocol.ResponseProcessCookies").setLevel(java.util.logging.Level.OFF);
        WebClient webClient = null;
        String news = "<html>";
        try
        {
         webClient = new WebClient(BrowserVersion.CHROME);
         webClient.getOptions().setJavaScriptEnabled(true);
         webClient.getOptions().setThrowExceptionOnScriptError(false);
         webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        String url = RAD_URL;
        System.out.println("Loading page now: "+url);
        HtmlPage page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(20 * 1000); /* will wait JavaScript to execute up to 30s */
        //get divs which have a 'class' attribute of 'mainbg'
        HtmlDivision div = page.getFirstByXPath("//div[@class='mainbg']");

        System.out.println("--------------------------");
        HtmlOrderedList orderedList = div.getFirstByXPath("//ol");
        news += orderedList.asXml();

        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        finally
        {
        	 webClient.close();
        }
        news += "</html>";
        return news;
    }
	
}
