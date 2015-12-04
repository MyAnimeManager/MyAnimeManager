package util;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.UIManager;



public class ColorProperties
{
	private final static String PROPERTIES_PATH = System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator + "color.properties";
	private static Properties applicationProps;
	public static Properties createProperties() 
	{
		// create and load default properties
		Properties defaultProps = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(PROPERTIES_PATH);
			defaultProps.load(in);
			defaultProps.put("Button_color", "null");
			in.close();
		} 
		catch (FileNotFoundException fe)
		{
			File prop = new File(PROPERTIES_PATH);
			try {
				prop.getParentFile().mkdirs();
				prop.createNewFile();
				defaultProps.put("Background_color", "null");
				defaultProps.put("Button_color", "null");
				defaultProps.put("TextField_color", "null");
				defaultProps.put("Label_color", "null");
				defaultProps.put("CheckBox_color", "null");
				defaultProps.put("Menu_color", "null");
				defaultProps.put("List_color", "null");
				defaultProps.put("Separator_color", "null");
				defaultProps.put("ProgressBar_color", "null");
				defaultProps.put("ComboBox_color", "null");
			} catch (IOException e) {
				e.printStackTrace();
				MAMUtil.writeLog(e);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			MAMUtil.writeLog(e);
		}

		applicationProps = new Properties(defaultProps);

		// now load properties 
		// from last invocation
		try {
			in = new FileInputStream(PROPERTIES_PATH);
			applicationProps.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			MAMUtil.writeLog(e);
		};
		return applicationProps;
	}
	
	public static void saveProperties(Properties prop)
	{
		FileOutputStream out;
		try {
			out = new FileOutputStream(PROPERTIES_PATH);
			prop.store(out, "---Start Option---");
			out.close();
		} catch (Exception e) {
		}
	}
	
	public static void setColor(Properties colorProperties)
	{ 
		//JPanel
		if (colorProperties.getProperty("Background_color") != null && !colorProperties.getProperty("Background_color").equalsIgnoreCase("null"))
		{
			Color color = new Color(Integer.parseInt(colorProperties.getProperty("Background_color")));
			UIManager.put("Panel.background", color);
			UIManager.put("Viewport.background", color);
			UIManager.put("OptionPane.background", color);
			UIManager.put("ScrollBar.background", color);
		}
	
		//Bottoni
		if (colorProperties.getProperty("Button_color") != null  && !colorProperties.getProperty("Button_color").equalsIgnoreCase("null"))
		{
			Color color = new Color(Integer.parseInt(colorProperties.getProperty("Button_color")));
			UIManager.put("Button.background", color);
			UIManager.put("ToggleButton.background", color);
		}
		
		//TextField		
		if (colorProperties.getProperty("TextField_color") != null && !colorProperties.getProperty("TextField_color").equalsIgnoreCase("null"))
		{
			Color color = new Color(Integer.parseInt(colorProperties.getProperty("TextField_color")));
			UIManager.put("TextField.background", color);
			UIManager.put("TextArea.background", color);
		}
		
		//Label
		if (colorProperties.getProperty("Label_color") != null  && !colorProperties.getProperty("Label_color").equalsIgnoreCase("null"))
		{
			Color color = new Color(Integer.parseInt(colorProperties.getProperty("Label_color")));
			UIManager.put("Label.background", color);
		}
		
		//CheckBox
		if (colorProperties.getProperty("CheckBox_color") != null && !colorProperties.getProperty("CheckBox_color").equalsIgnoreCase("null"))
		{
			Color color = new Color(Integer.parseInt(colorProperties.getProperty("CheckBox_color")));
			UIManager.put("CheckBox.background", color);
		}
		
		//Menu
		if (colorProperties.getProperty("Menu_color") != null && !colorProperties.getProperty("Menu_color").equalsIgnoreCase("null"))
		{
			Color color = new Color(Integer.parseInt(colorProperties.getProperty("Menu_color")));
			UIManager.put("MenuBar.background", color);
			UIManager.put("Menu.background", color);
			UIManager.put("PopupMenu.background", color);
			UIManager.put("MenuItem.background", color);
		}
		
		//Liste
		if (colorProperties.getProperty("List_color") != null && !colorProperties.getProperty("List_color").equalsIgnoreCase("null"))
		{
			Color color = new Color(Integer.parseInt(colorProperties.getProperty("List_color")));
			UIManager.put("List.background", color);
		}
		
		//Separator
		if (colorProperties.getProperty("Separator_color") != null && !colorProperties.getProperty("Separator_color").equalsIgnoreCase("null"))
		{
			Color color = new Color(Integer.parseInt(colorProperties.getProperty("Separator_color")));
			UIManager.put("Separator.background", color);
		}
		
		//LoadingBar
		if (colorProperties.getProperty("ProgressBar_color") != null && !colorProperties.getProperty("ProgressBar_color").equalsIgnoreCase("null"))
		{
			Color color = new Color(Integer.parseInt(colorProperties.getProperty("ProgressBar_color")));
			UIManager.put("ProgressBar.background", color);
		}
		
		//ComboBox
		if (colorProperties.getProperty("ComboBox_color") != null && !colorProperties.getProperty("ComboBox_color").equalsIgnoreCase("null"))
		{
			Color color = new Color(Integer.parseInt(colorProperties.getProperty("ComboBox_color")));
			UIManager.put("ComboBox.background", color);
		}
	}
}
