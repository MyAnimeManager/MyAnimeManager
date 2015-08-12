package util.task;

import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.FileManager;

public class LoadingTask extends SwingWorker
{

	@Override
	protected Object doInBackground() throws Exception
	{
		FileManager.loadExclusionList();
		FileManager.loadAnime("completed.txt" , AnimeIndex.completedModel, AnimeIndex.completedMap);
		FileManager.loadAnime("airing.txt", AnimeIndex.airingModel, AnimeIndex.airingMap);
		FileManager.loadAnime("ova.txt", AnimeIndex.ovaModel, AnimeIndex.ovaMap);
		FileManager.loadAnime("film.txt", AnimeIndex.filmModel, AnimeIndex.filmMap);
		FileManager.loadAnime("toSee.txt", AnimeIndex.completedToSeeModel, AnimeIndex.completedToSeeMap);
		AnimeIndex.completedList.repaint();
		AnimeIndex.airingList.repaint();
		AnimeIndex.ovaList.repaint();
		AnimeIndex.filmList.repaint();
		AnimeIndex.completedToSeeList.repaint();
		return null;
	}

}
