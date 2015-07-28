package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.AnimeIndex;
import util.AnimeData;
import util.AnimeIndexProperties;
import util.FileManager;

public class ExitSaveDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public ExitSaveDialog()
	{
		setTitle("Attenzione");
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 389, 100);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		{
			JLabel lblVuoiSalvareLe = new JLabel("Vuoi salvare le modifiche prima di uscire?");
			contentPanel.add(lblVuoiSalvareLe);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER, 50, 5);
			buttonPane.setLayout(fl_buttonPane);
			{
				{
					JButton btnSaveAndExit = new JButton("Salva ed Esci");
					btnSaveAndExit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							//salva
							try {
								FileManager.saveFansubList();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
								FileManager.saveAnimeList("completed.txt", AnimeIndex.completedModel, AnimeIndex.completedMap);
								FileManager.saveAnimeList("airing.txt", AnimeIndex.airingModel, AnimeIndex.airingMap);
								FileManager.saveAnimeList("ova.txt", AnimeIndex.ovaModel, AnimeIndex.ovaMap);
								FileManager.saveAnimeList("film.txt", AnimeIndex.filmModel, AnimeIndex.filmMap);
								FileManager.saveAnimeList("toSee.txt", AnimeIndex.completedToSeeModel, AnimeIndex.completedToSeeMap);
							
								deleteUselessImage(AnimeIndex.completedDeletedAnime, AnimeIndex.completedMap, "Completed");
								deleteUselessImage(AnimeIndex.airingDeletedAnime, AnimeIndex.airingMap, "Airing");
								deleteUselessImage(AnimeIndex.ovaDeletedAnime, AnimeIndex.ovaMap, "Oav");
								deleteUselessImage(AnimeIndex.filmDeletedAnime, AnimeIndex.filmMap, "Film");
								deleteUselessImage(AnimeIndex.completedToSeeDeletedAnime, AnimeIndex.completedToSeeMap, "Completed to See");
							AnimeIndexProperties.saveProperties(AnimeIndex.appProp);
							System.exit(0);
						}
					});
					JButton btnExit = new JButton("Esci");
					btnExit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							AnimeIndexProperties.saveProperties(AnimeIndex.appProp);
							
							deleteUselessImage(AnimeIndex.completedSessionAnime, AnimeIndex.completedMap, "Completati");
							deleteUselessImage(AnimeIndex.airingSessionAnime, AnimeIndex.airingMap, "Anime in Corso");
							deleteUselessImage(AnimeIndex.ovaSessionAnime, AnimeIndex.ovaMap, "Oav");
							deleteUselessImage(AnimeIndex.filmSessionAnime, AnimeIndex.filmMap, "Film");
							deleteUselessImage(AnimeIndex.completedToSeeSessionAnime, AnimeIndex.completedToSeeMap, "Completati da Vedere");
							System.exit(0);
						}
					});
					buttonPane.add(btnExit);
					buttonPane.add(btnSaveAndExit);
				}
			}
			{
				JButton cancelButton = new JButton("Annulla");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton but = (JButton) e.getSource();
						JDialog dialog = (JDialog) but.getTopLevelAncestor();
						dialog.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void deleteUselessImage(ArrayList<String> arrayList, TreeMap<String, AnimeData> map, String listName)
	{
		for (int i = 0; i < arrayList.size(); i++)
		{
			String animeImagePath = arrayList.get(i);
			File image = new File(animeImagePath);
			try {
				FileManager.deleteData(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
