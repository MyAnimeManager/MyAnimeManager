package util.task;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import main.AnimeIndex;
import util.Feed;
import util.FeedMessage;

public class RSSNewsTask extends SwingWorker {

	private URL url;
	private LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

	private static final String TITLE = "title";
	private static final String DESCRIPTION = "description";
	private static final String CHANNEL = "channel";
	private static final String LANGUAGE = "language";
	private static final String COPYRIGHT = "copyright";
	private static final String LINK = "link";
	private static final String AUTHOR = "creator";
	private static final String ITEM = "item";
	private static final String PUB_DATE = "pubDate";
	private static final String GUID = "guid";
	private static final String RAD_TOPIC = "http://redanimedatabase.forumcommunity.net/?t=";
	private static final String RAD_FB = "www.facebook.com/RedAnimeDatabase";

	public RSSNewsTask(String feedUrl) {
		try {
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Object doInBackground() throws Exception {
		try {
			Feed rss = readFeed();
			for (FeedMessage message : rss.getMessages()) {
				String title = message.getTitle();
				String desc = message.getDescription();
				if (!title.startsWith("RAD - RedAnimeDatabase:")) {
					title = title.substring(title.indexOf(":") + 1).trim();
					if (title.endsWith("...")) {
						title = title.substring(0, title.length() - 3);
						int startIndex = desc.indexOf(title);
						int endIndex = desc.lastIndexOf("</b></span>");
						if (startIndex != -1 && endIndex != -1)
							title = desc.substring(startIndex, endIndex);
						if (title.contains("By <img"))
						{
							int index = title.indexOf("By <img");
							title = title.substring(0,index);
						}
						if (title.contains("<img"))
						{
							int index = title.indexOf("<img");
							title = title.substring(0,index);
						}
					}
					if (title.contains(":RAD:"))
						title = title.replace(":RAD:", "RAD");
					int  startIndex = -1;
					if (desc.contains(RAD_TOPIC))
						startIndex = desc.indexOf(RAD_TOPIC);
					else if (desc.contains(RAD_FB))
						startIndex = desc.indexOf(RAD_FB);
					
					int endIndex = desc.indexOf("target=_blank>");

					String link = desc.substring(startIndex, endIndex);
					link = link.replace("target=_blank>", "").trim();
					map.put(title, link);
				}
			}
			System.out.println("fine");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void done()
	{
		AnimeIndex.newsBoardDialog.setMap(map);
	}
	
	private Feed readFeed() {
		Feed feed = null;
		try {
			boolean isFeedHeader = true;
			// Set header values intial to the empty string
			String description = "";
			String title = "";
			String link = "";
			String language = "";
			String copyright = "";
			String author = "";
			String pubdate = "";
			String guid = "";

			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			inputFactory.setProperty(XMLInputFactory.IS_COALESCING, true);
			// Setup a new eventReader
			String xml = readAndFixXml();
			byte[] byteArray = xml.getBytes("windows-1252");
			ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// read the XML document
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					String localPart = event.asStartElement().getName().getLocalPart();
					switch (localPart) {
					case ITEM:
						if (isFeedHeader) {
							isFeedHeader = false;
							feed = new Feed(title, link, description, language,
									copyright, pubdate);
						}
						event = eventReader.nextEvent();
						break;
					case TITLE:
						title = getCharacterData(event, eventReader);
						break;
					case DESCRIPTION:
						description = getCharacterData(event, eventReader);
						break;
					case LINK:
						link = getCharacterData(event, eventReader);
						break;
					case GUID:
						guid = getCharacterData(event, eventReader);
						break;
					case LANGUAGE:
						language = getCharacterData(event, eventReader);
						break;
					case AUTHOR:
						author = getCharacterData(event, eventReader);
						break;
					case PUB_DATE:
						pubdate = getCharacterData(event, eventReader);
						break;
					case COPYRIGHT:
						copyright = getCharacterData(event, eventReader);
						break;
					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
						FeedMessage message = new FeedMessage();
						message.setAuthor(author);
						message.setDescription(description);
						message.setGuid(guid);
						message.setLink(link);
						message.setTitle(title);
						feed.getMessages().add(message);
						event = eventReader.nextEvent();
						continue;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return feed;
	}

	private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
			throws XMLStreamException {
		String result = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}
		else if (event instanceof StartElement)
		{
			boolean hasLink = false;
			while (!hasLink)
			{
				event = eventReader.nextEvent();
				if ((event instanceof Characters) && (event.asCharacters().getData().contains(RAD_TOPIC) || event.asCharacters().getData().contains(RAD_FB)))
				{
					hasLink = true;
					result = event.asCharacters().getData();
				}
			}
		}
		return result;
	}

	private String readAndFixXml() 
	{
		HttpURLConnection conn; // The actual connection to the web page
		BufferedReader rr; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		try
		{
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("User-Agent", "My Anime Manager");
			rr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rr.readLine()) != null)
				result += line + "\r\n";
			rr.close();
		}
		catch (java.net.SocketTimeoutException timeout)
		{
			JOptionPane.showMessageDialog(AnimeIndex.frame, "Errore durante la connessione alla Newsboard! Potrebbe dipendere dalla tua connessione o dal sito di Anilist.", "Errore!", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		result = result.replaceAll("& ", "&amp; ");
		result = result.replaceAll("&saved", "");
		return result;
	}




}