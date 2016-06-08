package util.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.SwingWorker;

import org.apache.commons.logging.LogFactory;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.xml.XmlPage;


import util.MAMUtil;


public class MALSynchronizationTask extends SwingWorker
{
	private final String MAL_ANIMELIST_URL = "http://myanimelist.net/malappinfo.php?u=";
	private String username;
	
	
	public MALSynchronizationTask(String username)
	{
		this.username = username;
	}
	
	@Override
	protected Object doInBackground() throws Exception
	{
		getPageAfterJavaScript();
		return null;
	}
	
	@Override
	protected void done()
	{
		System.out.println("FINE");
	}
	
	private void getPageAfterJavaScript()
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
		XmlPage page = null;
		try
		{
			webClient = new WebClient(BrowserVersion.FIREFOX_38);
			webClient.getCache().setMaxSize(0);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(false);
			webClient.setCssErrorHandler(new SilentCssErrorHandler());
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.waitForBackgroundJavaScriptStartingBefore(5 * 1000);
			webClient.getCookieManager().setCookiesEnabled(false);
//			webClient.getOptions().setRedirectEnabled(false);

			String url = MAL_ANIMELIST_URL + username;
			System.out.println("Loading page now: " + url);
			page = webClient.getPage(url);
			// get divs which have a 'class' attribute of 'mainbg'
			
			System.out.println(page.asXml());
			

			BufferedWriter output;
			try
			{
				output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "prova.xml"), "UTF-8"));
				output.write(page.asXml());
				output.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				MAMUtil.writeLog(e);
			}
			
			
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
