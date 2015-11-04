package util;

import java.awt.CardLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import javax.swing.ImageIcon;

import main.AnimeIndex;

public class Filters {

	public static void toFileteredList()
	{
		AnimeIndex.saveModifiedInformation();
		AnimeIndex.filterList.clearSelection();
		AnimeIndex.animeInformation.setBlank();
		AnimeIndex.filterList.setEnabled(true);
		AnimeIndex.filtro = 9;
		for(int j = 0; j<9; j++)
		{
			if(AnimeIndex.filterArray[j]==true)
				AnimeIndex.filtro = j;
		}
		if(AnimeIndex.filtro!=9)
		{
		Object[] modelArray = AnimeIndex.getModel().toArray();
		AnimeIndex.filterModel.clear();
		String dato="";
		TreeMap<String,AnimeData> map = AnimeIndex.getMap();
		for(int i=0; i<modelArray.length; i++)
		{
			AnimeData data = map.get(modelArray[i]);
			if(AnimeIndex.filtro==0)
			{
				dato = data.getAnimeType();
				if(dato.equalsIgnoreCase("Blu-ray"))
					AnimeIndex.filterModel.addElement((String)modelArray[i]);
			}
			else if(AnimeIndex.filtro==1)
			{
				dato = data.getDay();
				if(dato.equalsIgnoreCase("Sospesa"))
					AnimeIndex.filterModel.addElement((String)modelArray[i]);
			}
			else if(AnimeIndex.filtro==2)
			{
				dato = data.getDay();
				if(dato.equalsIgnoreCase("Irregolare") && !data.getAnimeType().equalsIgnoreCase("Blu-ray"))
					AnimeIndex.filterModel.addElement((String)modelArray[i]);
			}
			else if(AnimeIndex.filtro==3)
			{
				dato = data.getFansub();
				if(dato.equalsIgnoreCase("Dynit")||dato.equalsIgnoreCase("Yamato")||dato.equalsIgnoreCase("Yamato Video")||dato.equalsIgnoreCase("Yamato Animation")||dato.equalsIgnoreCase("YV")||dato.equalsIgnoreCase("YA")||dato.equalsIgnoreCase("YamatoVideo")||dato.equalsIgnoreCase("YamatoAnimation")||dato.equalsIgnoreCase("Crunchyroll")||dato.equalsIgnoreCase("CR")||dato.equalsIgnoreCase("Viewster"))
					AnimeIndex.filterModel.addElement((String)modelArray[i]);
			}
			else if(AnimeIndex.filtro==4)
			{
				dato=data.getAnimeType();
				if(!data.getReleaseDate().equalsIgnoreCase("??/??/????") && !data.getTotalEpisode().equalsIgnoreCase("??") && !data.getCurrentEpisode().equalsIgnoreCase(data.getTotalEpisode()))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getReleaseDate();
					if(animeData.substring(0, 6).equalsIgnoreCase("??/??/"))
					{
						if(dato.equalsIgnoreCase("OVA")||dato.equalsIgnoreCase("ONA")||dato.equalsIgnoreCase("Special"))
						{	
							if(currentYear>Integer.parseInt(animeData.substring(6, 10)))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else if(animeData.substring(0, 3).equalsIgnoreCase("??/"))
					{
						if(dato.equalsIgnoreCase("OVA")||dato.equalsIgnoreCase("ONA")||dato.equalsIgnoreCase("Special"))
						{	
							if(currentYear>Integer.parseInt(animeData.substring(6, 10)) || (currentYear==Integer.parseInt(animeData.substring(6, 10)) && currentMonth>Integer.parseInt(animeData.substring(3, 5))))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else
					{
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
				}
				else if (!data.getFinishDate().equalsIgnoreCase("??/??/????"))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getFinishDate();
					if(animeData.substring(0, 6).equalsIgnoreCase("??/??/"))
					{
						if(dato.equalsIgnoreCase("OVA")||dato.equalsIgnoreCase("ONA")||dato.equalsIgnoreCase("Special"))
						{	
							if(currentYear>Integer.parseInt(animeData.substring(6, 10)))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else if(animeData.substring(0, 3).equalsIgnoreCase("??/"))
					{
						if(dato.equalsIgnoreCase("OVA")||dato.equalsIgnoreCase("ONA")||dato.equalsIgnoreCase("Special"))
						{	
							if(currentYear>Integer.parseInt(animeData.substring(6, 10)) || (currentYear==Integer.parseInt(animeData.substring(6, 10)) && currentMonth>Integer.parseInt(animeData.substring(3, 5))))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else
					{
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
			}
			else if(AnimeIndex.filtro==5)
			{
				dato=data.getAnimeType();
				if(!data.getReleaseDate().equalsIgnoreCase("??/??/????") && !data.getTotalEpisode().equalsIgnoreCase("??") && !data.getCurrentEpisode().equalsIgnoreCase(data.getTotalEpisode()))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getReleaseDate();
					if(animeData.substring(0, 6).equalsIgnoreCase("??/??/"))
					{
						if(dato.equalsIgnoreCase("OVA")||dato.equalsIgnoreCase("ONA")||dato.equalsIgnoreCase("Special"))
						{	
							if(currentYear<=Integer.parseInt(animeData.substring(6, 10)))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else if(animeData.substring(0, 3).equalsIgnoreCase("??/"))
					{
						if(dato.equalsIgnoreCase("OVA")||dato.equalsIgnoreCase("ONA")||dato.equalsIgnoreCase("Special"))
						{	
							if(currentYear<Integer.parseInt(animeData.substring(6, 10)) || (currentYear==Integer.parseInt(animeData.substring(6, 10)) && currentMonth<=Integer.parseInt(animeData.substring(3, 5))))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else
					{
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
				}
				else if (!data.getFinishDate().equalsIgnoreCase("??/??/????"))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getFinishDate();
					if(animeData.substring(0, 6).equalsIgnoreCase("??/??/"))
					{
						if(dato.equalsIgnoreCase("OVA")||dato.equalsIgnoreCase("ONA")||dato.equalsIgnoreCase("Special"))
						{	
							if(currentYear<=Integer.parseInt(animeData.substring(6, 10)))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else if(animeData.substring(0, 3).equalsIgnoreCase("??/"))
					{
						if(dato.equalsIgnoreCase("OVA")||dato.equalsIgnoreCase("ONA")||dato.equalsIgnoreCase("Special"))
						{	
							if(currentYear<Integer.parseInt(animeData.substring(6, 10)) || (currentYear==Integer.parseInt(animeData.substring(6, 10)) && currentMonth<=Integer.parseInt(animeData.substring(3, 5))))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else
					{
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
				}
				else
					AnimeIndex.filterModel.addElement((String)modelArray[i]);
			}
			else if(AnimeIndex.filtro==6)
			{
				dato=data.getAnimeType();
				if(!data.getReleaseDate().equalsIgnoreCase("??/??/????") && !data.getTotalEpisode().equalsIgnoreCase("??") && !data.getCurrentEpisode().equalsIgnoreCase(data.getTotalEpisode()))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getReleaseDate();
					if(animeData.substring(0, 6).equalsIgnoreCase("??/??/"))
					{
						if(dato.equalsIgnoreCase("Movie"))
						{	
							if(currentYear>Integer.parseInt(animeData.substring(6, 10)))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else if(animeData.substring(0, 3).equalsIgnoreCase("??/"))
					{
						if(dato.equalsIgnoreCase("Movie"))
						{	
							if(currentYear>Integer.parseInt(animeData.substring(6, 10)) || (currentYear==Integer.parseInt(animeData.substring(6, 10)) && currentMonth>Integer.parseInt(animeData.substring(3, 5))))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else
					{
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
				else if (!data.getFinishDate().equalsIgnoreCase("??/??/????"))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getFinishDate();
					if(animeData.substring(0, 6).equalsIgnoreCase("??/??/"))
					{
						if(dato.equalsIgnoreCase("Movie"))
						{	
							if(currentYear>Integer.parseInt(animeData.substring(6, 10)))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else if(animeData.substring(0, 3).equalsIgnoreCase("??/"))
					{
						if(dato.equalsIgnoreCase("Movie"))
						{	
							if(currentYear>Integer.parseInt(animeData.substring(6, 10)) || (currentYear==Integer.parseInt(animeData.substring(6, 10)) && currentMonth>Integer.parseInt(animeData.substring(3, 5))))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else
					{
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
			}
			else if(AnimeIndex.filtro==7)
			{
				dato=data.getAnimeType();
				if(!data.getReleaseDate().equalsIgnoreCase("??/??/????") && !data.getTotalEpisode().equalsIgnoreCase("??") && !data.getCurrentEpisode().equalsIgnoreCase(data.getTotalEpisode()))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getReleaseDate();
					if(animeData.substring(0, 6).equalsIgnoreCase("??/??/"))
					{
						if(dato.equalsIgnoreCase("Movie"))
						{	
							if(currentYear<=Integer.parseInt(animeData.substring(6, 10)))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else if(animeData.substring(0, 3).equalsIgnoreCase("??/"))
					{
						if(dato.equalsIgnoreCase("Movie"))
						{	
							if(currentYear<Integer.parseInt(animeData.substring(6, 10)) || (currentYear==Integer.parseInt(animeData.substring(6, 10)) && currentMonth<=Integer.parseInt(animeData.substring(3, 5))))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else
					{
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
				}
				else if (!data.getFinishDate().equalsIgnoreCase("??/??/????"))
				{
					GregorianCalendar calendar = new GregorianCalendar();
					int currentDay = calendar.get(Calendar.DATE);
					int currentMonth = calendar.get(Calendar.MONTH)+1;
					int currentYear = calendar.get(Calendar.YEAR);
					String animeData = data.getFinishDate();
					if(animeData.substring(0, 6).equalsIgnoreCase("??/??/"))
					{
						if(dato.equalsIgnoreCase("Movie"))
						{	
							if(currentYear<=Integer.parseInt(animeData.substring(6, 10)))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else if(animeData.substring(0, 3).equalsIgnoreCase("??/"))
					{
						if(dato.equalsIgnoreCase("Movie"))
						{	
							if(currentYear<Integer.parseInt(animeData.substring(6, 10)) || (currentYear==Integer.parseInt(animeData.substring(6, 10)) && currentMonth<=Integer.parseInt(animeData.substring(3, 5))))
								AnimeIndex.filterModel.addElement((String)modelArray[i]);
						}
					}
					else
					{
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
				
				if(dato.equalsIgnoreCase(giorno))
					AnimeIndex.filterModel.addElement((String)modelArray[i]);
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
						if(c.equals(calendar))
							AnimeIndex.filterModel.addElement((String)modelArray[i]);
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
					if(c.equals(calendar))
						AnimeIndex.filterModel.addElement((String)modelArray[i]);
				}
			}
		}
		if (AnimeIndex.filterModel.isEmpty())
		{
			AnimeIndex.filterModel.addElement("Nessun Anime Corrispondente");
			AnimeIndex.animeInformation.setBlank();
			AnimeIndex.filterList.setEnabled(false);
		}
		CardLayout cl = (CardLayout)(AnimeIndex.cardContainer.getLayout());
        cl.show(AnimeIndex.cardContainer, "Filtri");
		}
	}
	public static void setFilter(int i)
	{
		AnimeIndex.setFilterButton.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/ellipse_icon1.png")));
		AnimeIndex.filtro = i;
		for(int j = 0; j<9; j++)
		{
			if(j == i)
				AnimeIndex.filterArray[j]=true;
			 else
				AnimeIndex.filterArray[j]=false;
		}
		toFileteredList();
	}
	public static void removeFilters()
	{
		AnimeIndex.filtro = 9;
		for(int j = 0; j<9; j++)
			AnimeIndex.filterArray[j]=false;
		AnimeIndex.setFilterButton.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/ellipse_icon3.png")));
		String listName = AnimeIndex.getList();
		CardLayout cl = (CardLayout)(AnimeIndex.cardContainer.getLayout());
        cl.show(AnimeIndex.cardContainer, listName);
	}
}
