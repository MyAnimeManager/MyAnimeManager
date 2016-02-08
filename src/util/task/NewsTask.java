package util.task;

import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlOrderedList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import main.AnimeIndex;

public class NewsTask extends SwingWorker
{

	private final String RAD_URL = "http://redanimedatabase.forumcommunity.net/";
	private LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

	@Override
	protected Object doInBackground() throws Exception
	{
		getPageAfterJavaScript();
		return null;
	}

	@Override
	protected void done()
	{
		AnimeIndex.newsBoardDialog.setMap(map);
	}

	public void getPageAfterJavaScript()
	{
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF); /*
																											 * comment
																											 * out
																											 * to
																											 * turn
																											 * off
																											 * annoying
																											 * htmlunit
																											 * warnings
																											 */
		java.util.logging.Logger.getLogger("org.apache.http.client.protocol.ResponseProcessCookies").setLevel(java.util.logging.Level.OFF);
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		WebClient webClient = null;
		HtmlPage page = null;
		try
		{
			webClient = new WebClient(BrowserVersion.EDGE);
			webClient.getCache().setMaxSize(0);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(false);
			webClient.setCssErrorHandler(new SilentCssErrorHandler());
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.waitForBackgroundJavaScriptStartingBefore(5 * 1000);
			webClient.getOptions().setRedirectEnabled(false);

			String url = RAD_URL;
			System.out.println("Loading page now: " + url);
			page = webClient.getPage(url);
			// get divs which have a 'class' attribute of 'mainbg'
			
			HtmlDivision div = page.getFirstByXPath("//div[@class='mainbg']");
			HtmlOrderedList orderedList = div.getFirstByXPath("//ol");
			List<?> linkList = orderedList.getByXPath("//ol/li//a[@target='_blank']");
			for (int i = 0; i < linkList.size(); i++)
				map.put(((HtmlAnchor) linkList.get(i)).asText(), ((HtmlAnchor) linkList.get(i)).getAttribute("href"));
		}
		catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
			if (e.getStatusCode() == 302) {
					System.out.println("ERRORE");
					System.out.println(page.toString());			
			}
		}
		catch (Exception e)
		{
			System.out.println("Errore");
			e.printStackTrace();
		}
		webClient.close();
		System.out.println("NewsBoard Completata");
	}

}
