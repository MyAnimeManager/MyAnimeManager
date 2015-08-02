package util;

import java.awt.CardLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import main.AnimeIndex;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


public class Filters {
	
	public static int filtro;
	public static void toFileteredList()
	{
		AnimeIndex.saveModifiedInformation();
		AnimeIndex.filterList.clearSelection();
		AnimeIndex.animeInformation.setBlank();
		AnimeIndex.filterList.setEnabled(true);
		Object[] modelArray = AnimeIndex.getModel().toArray();
		AnimeIndex.filterModel.clear();
		filtro=9;
		for(int j = 0; j<9; j++)
		{
			if(AnimeIndex.filterArray[j]==true)
				filtro = j;
		}
		if(filtro!=9)
		{
		String dato="";
		TreeMap<String,AnimeData> map = AnimeIndex.getMap();
		for(int i=0; i<modelArray.length; i++)
		{
			AnimeData data = map.get((String)modelArray[i]);
			if(filtro==0)
			{
				dato = data.getAnimeType();
				if(dato.equalsIgnoreCase("Blu-ray"))
					AnimeIndex.filterModel.addElement((String)modelArray[i]);
			}
			else if(filtro==1)
			{
				dato = data.getDay();
				if(dato.equalsIgnoreCase("Sospesa"))
					AnimeIndex.filterModel.addElement((String)modelArray[i]);
			}
			else if(filtro==2)
			{
				dato = data.getDay();
				if(dato.equalsIgnoreCase("Irregolare"))
					AnimeIndex.filterModel.addElement((String)modelArray[i]);
			}
			else if(filtro==3)
			{
				dato = data.getFansub();
				if(dato.equalsIgnoreCase("Dynit")||dato.equalsIgnoreCase("Yamato")||dato.equalsIgnoreCase("Yamato Video")||dato.equalsIgnoreCase("Yamato Animation")||dato.equalsIgnoreCase("YV")||dato.equalsIgnoreCase("YA")||dato.equalsIgnoreCase("YamatoVideo")||dato.equalsIgnoreCase("YamatoAnimation")||dato.equalsIgnoreCase("Crunchyroll")||dato.equalsIgnoreCase("CR")||dato.equalsIgnoreCase("Viewster"))
					AnimeIndex.filterModel.addElement((String)modelArray[i]);
			}
			else if(filtro==4)
			{
				dato=data.getAnimeType();
				if(!data.getReleaseDate().equalsIgnoreCase("??/??/????") && !data.getReleaseDate().substring(0, 6).equalsIgnoreCase("??/??/") && !data.getReleaseDate().substring(0, 3).equalsIgnoreCase("??/") && (data.getCurrentEpisode().equalsIgnoreCase("??") || data.getCurrentEpisode().equalsIgnoreCase("0") || data.getCurrentEpisode().equalsIgnoreCase("1")))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getReleaseDate();
					String currentData = currentDay + "/" + currentMonth + "/" + currentYear;;
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		 	
					try {
						calendar.setTime(sdf.parse(currentData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					GregorianCalendar c = new GregorianCalendar();
					try {
						c.setTime(sdf.parse(animeData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					if(dato.equalsIgnoreCase("OVA")||dato.equalsIgnoreCase("ONA")||dato.equalsIgnoreCase("Special"))
					{	if(c.before(calendar) || c.equals(calendar))
							AnimeIndex.filterModel.addElement((String)modelArray[i]);
					}
				}
				else if (!data.getFinishDate().equalsIgnoreCase("??/??/????") && !data.getFinishDate().substring(0, 6).equalsIgnoreCase("??/??/") && !data.getFinishDate().substring(0, 3).equalsIgnoreCase("??/"))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getFinishDate();
					String currentData = currentDay + "/" + currentMonth + "/" + currentYear;
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		 	
					try {
						calendar.setTime(sdf.parse(currentData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					GregorianCalendar c = new GregorianCalendar();
					try {
						c.setTime(sdf.parse(animeData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					if(dato.equalsIgnoreCase("OVA")||dato.equalsIgnoreCase("ONA")||dato.equalsIgnoreCase("Special"))
					{	if(c.before(calendar) || c.equals(calendar))
							AnimeIndex.filterModel.addElement((String)modelArray[i]);
					}
				}					
			}
			else if(filtro==5)
			{
				dato=data.getAnimeType();
				if(!data.getReleaseDate().equalsIgnoreCase("??/??/????") && !data.getReleaseDate().substring(0, 6).equalsIgnoreCase("??/??/") && !data.getReleaseDate().substring(0, 3).equalsIgnoreCase("??/") && (data.getCurrentEpisode().equalsIgnoreCase("??") || data.getCurrentEpisode().equalsIgnoreCase("0") || data.getCurrentEpisode().equalsIgnoreCase("1")))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getReleaseDate();
					String currentData = currentDay + "/" + currentMonth + "/" + currentYear;
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		 	
					try {
						calendar.setTime(sdf.parse(currentData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					GregorianCalendar c = new GregorianCalendar();
					try {
						c.setTime(sdf.parse(animeData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					if(dato.equalsIgnoreCase("OVA")||dato.equalsIgnoreCase("ONA")||dato.equalsIgnoreCase("Special"))
					{	if(c.after(calendar))
							AnimeIndex.filterModel.addElement((String)modelArray[i]);
					}
				}
				else if (!data.getFinishDate().equalsIgnoreCase("??/??/????") && !data.getFinishDate().substring(0, 6).equalsIgnoreCase("??/??/") && !data.getFinishDate().substring(0, 3).equalsIgnoreCase("??/"))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getFinishDate();
					String currentData = currentDay + "/" + currentMonth + "/" + currentYear;
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		 	
					try {
						calendar.setTime(sdf.parse(currentData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					GregorianCalendar c = new GregorianCalendar();
					try {
						c.setTime(sdf.parse(animeData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					if(dato.equalsIgnoreCase("OVA")||dato.equalsIgnoreCase("ONA")||dato.equalsIgnoreCase("Special"))
					{	if(c.after(calendar))
							AnimeIndex.filterModel.addElement((String)modelArray[i]);
					}
				}
				else
					AnimeIndex.filterModel.addElement((String)modelArray[i]);
			}
			else if(filtro==6)
			{
				dato=data.getAnimeType();
				if(!data.getReleaseDate().equalsIgnoreCase("??/??/????") && !data.getReleaseDate().substring(0, 6).equalsIgnoreCase("??/??/") && !data.getReleaseDate().substring(0, 3).equalsIgnoreCase("??/") && (data.getCurrentEpisode().equalsIgnoreCase("??") || data.getCurrentEpisode().equalsIgnoreCase("0") || data.getCurrentEpisode().equalsIgnoreCase("1")))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getReleaseDate();
					String currentData = currentDay + "/" + currentMonth + "/" + currentYear;
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		 	
					try {
						calendar.setTime(sdf.parse(currentData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					GregorianCalendar c = new GregorianCalendar();
					try {
						c.setTime(sdf.parse(animeData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					if(dato.equalsIgnoreCase("Movie"))
					{	if(c.before(calendar) || c.equals(calendar))
							AnimeIndex.filterModel.addElement((String)modelArray[i]);
					}
				}
				else if (!data.getFinishDate().equalsIgnoreCase("??/??/????") && !data.getFinishDate().substring(0, 6).equalsIgnoreCase("??/??/") && !data.getFinishDate().substring(0, 3).equalsIgnoreCase("??/"))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getFinishDate();
					String currentData = currentDay + "/" + currentMonth + "/" + currentYear;
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		 	
					try {
						calendar.setTime(sdf.parse(currentData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					GregorianCalendar c = new GregorianCalendar();
					try {
						c.setTime(sdf.parse(animeData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					if(dato.equalsIgnoreCase("Movie"))
					{	if(c.before(calendar) || c.equals(calendar))
							AnimeIndex.filterModel.addElement((String)modelArray[i]);
					}
				}
			}
			else if(filtro==7)
			{
				dato=data.getAnimeType();
				if(!data.getReleaseDate().equalsIgnoreCase("??/??/????") && !data.getReleaseDate().substring(0, 6).equalsIgnoreCase("??/??/") && !data.getReleaseDate().substring(0, 3).equalsIgnoreCase("??/") && (data.getCurrentEpisode().equalsIgnoreCase("??") || data.getCurrentEpisode().equalsIgnoreCase("0") || data.getCurrentEpisode().equalsIgnoreCase("1")))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getReleaseDate();
					String currentData = currentDay + "/" + currentMonth + "/" + currentYear;
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		 	
					try {
						calendar.setTime(sdf.parse(currentData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					GregorianCalendar c = new GregorianCalendar();
					try {
						c.setTime(sdf.parse(animeData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					if(dato.equalsIgnoreCase("Movie"))
					{	if(c.after(calendar))
							AnimeIndex.filterModel.addElement((String)modelArray[i]);
					}
				}
				else if (!data.getFinishDate().equalsIgnoreCase("??/??/????") && !data.getFinishDate().substring(0, 6).equalsIgnoreCase("??/??/") && !data.getFinishDate().substring(0, 3).equalsIgnoreCase("??/"))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getFinishDate();
					String currentData = currentDay + "/" + currentMonth + "/" + currentYear;
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		 	
					try {
						calendar.setTime(sdf.parse(currentData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					GregorianCalendar c = new GregorianCalendar();
					try {
						c.setTime(sdf.parse(animeData));
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					if(dato.equalsIgnoreCase("Movie"))
					{	if(c.after(calendar))
							AnimeIndex.filterModel.addElement((String)modelArray[i]);
					}
				}
				else
					AnimeIndex.filterModel.addElement((String)modelArray[i]);
			}
			else 
			{
				String giorno = "";
				dato=data.getDay();
				GregorianCalendar calendar = new GregorianCalendar();
				GregorianCalendar c = new GregorianCalendar();
				if(!dato.equalsIgnoreCase("?????") && !dato.equalsIgnoreCase("Concluso") && !dato.equalsIgnoreCase("Sospesa") && !dato.equalsIgnoreCase("Irregolare"))
				{
				int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
				if (currentDay == Calendar.SUNDAY)
					giorno = "Domenica";
				else if (currentDay == Calendar.MONDAY)
					giorno = "Lunedì";
				else if (currentDay == Calendar.TUESDAY)
					giorno = "Martedì";
				else if (currentDay == Calendar.WEDNESDAY)
					giorno = "Mercoledì";
				else if (currentDay == Calendar.THURSDAY)
					giorno = "Giovedì";
				else if (currentDay == Calendar.FRIDAY)
					giorno = "Venerdì";
				else if (currentDay == Calendar.SATURDAY)
					giorno = "Sabato";
				}
				else
				{
					if(!data.getReleaseDate().equalsIgnoreCase("??/??/????") && !data.getReleaseDate().substring(0, 6).equalsIgnoreCase("??/??/") && !data.getReleaseDate().substring(0, 3).equalsIgnoreCase("??/") && (data.getCurrentEpisode().equalsIgnoreCase("??") || data.getCurrentEpisode().equalsIgnoreCase("0") || data.getCurrentEpisode().equalsIgnoreCase("1")))
					{
						int currentDay = calendar.get(Calendar.DATE);
						int currentMonth = calendar.get(Calendar.MONTH)+1;
						int currentYear = calendar.get(Calendar.YEAR);
						String currentData = currentDay + "/" + currentMonth + "/" + currentYear;
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						try {
							calendar.setTime(sdf.parse(currentData));
						} catch (java.text.ParseException e) {
							e.printStackTrace();
						}
						String animeData = data.getReleaseDate();
						try {
							c.setTime(sdf.parse(animeData));
						} catch (java.text.ParseException e) {
							e.printStackTrace();
						}
					}
					else if (!data.getFinishDate().equalsIgnoreCase("??/??/????") && !data.getFinishDate().substring(0, 6).equalsIgnoreCase("??/??/") && !data.getFinishDate().substring(0, 3).equalsIgnoreCase("??/"))
					{
						int currentDay = calendar.get(Calendar.DATE);
						int currentMonth = calendar.get(Calendar.MONTH)+1;
						int currentYear = calendar.get(Calendar.YEAR);
						String currentData = currentDay + "/" + currentMonth + "/" + currentYear;
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						try {
							calendar.setTime(sdf.parse(currentData));
						} catch (java.text.ParseException e) {
							e.printStackTrace();
						}
						String animeData = data.getFinishDate();
						try {
							c.setTime(sdf.parse(animeData));
						} catch (java.text.ParseException e) {
							e.printStackTrace();
						}
					}
				}
				if(dato.equalsIgnoreCase(giorno) || c.equals(calendar))
					AnimeIndex.filterModel.addElement((String)modelArray[i]);
			}
		}
		if (AnimeIndex.filterModel.isEmpty())
		{
			AnimeIndex.filterModel.addElement("Nessun Anime Corrispondente");
			AnimeIndex.animeInformation.setBlank();
			AnimeIndex.filterList.setEnabled(false);
		}
		}
	}
}
