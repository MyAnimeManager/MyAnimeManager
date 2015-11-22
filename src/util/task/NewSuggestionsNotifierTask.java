package util.task;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.SuggestionHelper;

public class NewSuggestionsNotifierTask extends SwingWorker{
	
	public static String packNumber;
	
	@Override
	protected Object doInBackground() throws Exception
	{
		String data = SuggestionHelper.getData();
		packNumber = SuggestionHelper.getPackVersion(data);
		return null;
	}
	
	@Override
	public void done()
	{
		if(!packNumber.equalsIgnoreCase(AnimeIndex.appProp.getProperty("Suggestions_Pack_Number")))
		{
			JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Nuovi anime sono disponibili\n\rnella sezione \"Anime Consigliati\" !!!", "Nuovi Anime !!!", JOptionPane.INFORMATION_MESSAGE);
			AnimeIndex.appProp.setProperty("Suggestions_Pack_Number", packNumber);
		}
	}	
}

