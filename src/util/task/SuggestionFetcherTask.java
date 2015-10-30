package util.task;

import javax.swing.SwingWorker;

import util.window.SuggestionDialog;


public class SuggestionFetcherTask extends SwingWorker {
	
	@Override
	protected Object doInBackground() throws Exception
	{
		for (int i = 0; i < 5; i++)
			SuggestionDialog.storeSuggestion(i);
		return null;
	}
	
	@Override
	public void done()
	{
		SuggestionDialog.dataAlreadyFetched = true;
		SuggestionDialog.waitDialog.dispose();
	}
	
}
