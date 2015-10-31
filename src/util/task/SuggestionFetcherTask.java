package util.task;

import javax.swing.SwingWorker;

import util.SuggestionHelper;
import util.window.SuggestionDialog;


public class SuggestionFetcherTask extends SwingWorker {
	
	@Override
	protected Object doInBackground() throws Exception
	{
		String data = SuggestionHelper.getData();
		for (int i = 0; i < 5; i++)
			SuggestionDialog.storeSuggestion(i, data);
		return null;
	}
	
	@Override
	public void done()
	{
		SuggestionDialog.dataAlreadyFetched = true;
		SuggestionDialog.waitDialog.dispose();
	}
	
}
