package util.task;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.SwingWorker;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.InteractivePage;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlOrderedList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;

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
         webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
         webClient.getOptions().setCssEnabled(false);
         webClient.setCssErrorHandler(new SilentCssErrorHandler());    
         webClient.setAjaxController(new NicelyResynchronizingAjaxController());
         webClient.setJavaScriptErrorListener(new JavaScriptErrorListener(){

 			@Override
 			public void loadScriptError(InteractivePage arg0, URL arg1, Exception arg2)
 			{	
 			}

 			@Override
 			public void malformedScriptURL(InteractivePage arg0, String arg1, MalformedURLException arg2)
 			{	
 			}

 			@Override
 			public void scriptException(InteractivePage arg0, ScriptException arg1)
 			{
 			}

 			@Override
 			public void timeoutError(InteractivePage arg0, long arg1, long arg2)
 			{
 			}});

        String url = RAD_URL;
        System.out.println("Loading page now: "+url);
        HtmlPage page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScriptStartingBefore(20 * 1000); /* will wait JavaScript to execute up to 30s */
        //get divs which have a 'class' attribute of 'mainbg'
        HtmlDivision div = page.getFirstByXPath("//div[@class='mainbg']");

        System.out.println("--------------------------");
//        System.out.println(div.asText()); prende i nomi
        HtmlOrderedList orderedList = div.getFirstByXPath("//ol");
        System.out.println(orderedList.asXml());
        List<?> linkList = orderedList.getByXPath("//a[@target='_blank']");
        System.out.println(linkList.get(1));
        
//        System.out.println(orderedList.asXml());
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
