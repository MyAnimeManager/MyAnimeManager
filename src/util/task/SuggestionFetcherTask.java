package util.task;

import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.SuggestionHelper;


public class SuggestionFetcherTask extends SwingWorker {
	
	@Override
	protected Object doInBackground() throws Exception
	{
		String data = SuggestionHelper.getData();
		for (int i = 0; i < 5; i++)
			AnimeIndex.suggestionDial.storeSuggestion(i, data);
		return null;
	}
	
	@Override
	public void done()
	{
		AnimeIndex.suggestionDial.waitDialog.dispose();
	}
	
}
