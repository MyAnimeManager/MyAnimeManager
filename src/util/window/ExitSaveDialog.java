package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
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
import util.ColorProperties;
import util.FileManager;
import util.MAMUtil;

public class ExitSaveDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public ExitSaveDialog()
	{
		super(AnimeIndex.frame,true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ExitSaveDialog.class.getResource("/image/icon.png")));
		setTitle("Conferma Uscita");
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
								MAMUtil.writeLog(e1);
							}
								FileManager.saveAnimeList("completed.anaconda", AnimeIndex.completedMap);
								FileManager.saveAnimeList("airing.anaconda", AnimeIndex.airingMap);
								FileManager.saveAnimeList("ova.anaconda", AnimeIndex.ovaMap);
								FileManager.saveAnimeList("film.anaconda", AnimeIndex.filmMap);
								FileManager.saveAnimeList("toSee.anaconda", AnimeIndex.completedToSeeMap);
								FileManager.saveWishList();
								FileManager.saveExclusionList();
								FileManager.saveDateMap();
								
								deleteUselessImage(AnimeIndex.completedDeletedAnime);
								deleteUselessImage(AnimeIndex.airingDeletedAnime);
								deleteUselessImage(AnimeIndex.ovaDeletedAnime);
								deleteUselessImage(AnimeIndex.filmDeletedAnime);
								deleteUselessImage(AnimeIndex.completedToSeeDeletedAnime);
								deleteUselessImage(AnimeIndex.sessionAddedAnime);
								
								AnimeIndexProperties.saveProperties(AnimeIndex.appProp);
								ColorProperties.saveProperties(AnimeIndex.colorProp);
								
							System.exit(0);
						}
					});
					JButton btnExit = new JButton("Esci");
					btnExit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							AnimeIndexProperties.saveProperties(AnimeIndex.appProp);
							
							imageShifter();
							
							deleteUselessImage(AnimeIndex.completedSessionAnime);
							deleteUselessImage(AnimeIndex.airingSessionAnime);
							deleteUselessImage(AnimeIndex.ovaSessionAnime);
							deleteUselessImage(AnimeIndex.filmSessionAnime);
							deleteUselessImage(AnimeIndex.completedToSeeSessionAnime);
							deleteUselessImage(AnimeIndex.sessionAddedAnime);
							Object[] keyArr = AnimeIndex.sessionAddedAnimeImagesShiftsRegister.keySet().toArray();
							for (int i = 0; i < keyArr.length; i++)
							{
								try
								{
									FileManager.deleteData(new File (AnimeIndex.sessionAddedAnimeImagesShiftsRegister.get(keyArr[i])));
								}
								catch (IOException e)
								{
								}
							}
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

	public static void deleteUselessImage(ArrayList<String> arrayList)
	{
		for (int i = 0; i < arrayList.size(); i++)
		{
			String animeImagePath = arrayList.get(i);
			File image = new File(animeImagePath);
			try {
				FileManager.deleteData(image);
			} catch (IOException e) {
				e.printStackTrace();
				MAMUtil.writeLog(e);
			}
		}
	}
	private void imageShifter()
	{
		Object[] arrayName = AnimeIndex.shiftsRegister.keySet().toArray();
		for (int i=0; i<AnimeIndex.shiftsRegister.size(); i++)
		{
			String name = (String)arrayName[i];
			String imgPathTo = "";
			String listName = AnimeIndex.shiftsRegister.get(name);
			String folder = "";
			if (listName.equalsIgnoreCase("anime completati"))
				folder = "Completed";
			else if (listName.equalsIgnoreCase("anime in corso"))
				folder = "Airing";
			else if (listName.equalsIgnoreCase("oav"))
				folder = "Ova";
			else if (listName.equalsIgnoreCase("film"))
				folder = "Film";
			else if (listName.equalsIgnoreCase("completi da vedere"))
				folder = "Completed to See";
			
			if(!AnimeIndex.sessionAddedAnime.contains(name))
			{
				if(AnimeIndex.completedMap.containsKey(name))
				{
					String type = "Anime Completati";
					TreeMap<String,AnimeData> map = AnimeIndex.completedMap;				
					String imgPathFrom = map.get(name).getImagePath(type);
					String nomeImg = map.get(name).getImageName();
					imgPathTo = FileManager.getImageFolderPath() + folder + File.separator + nomeImg;
					File img = new File(imgPathTo);
					if (!imgPathFrom.equals(imgPathTo))
					{
						if(img.isFile()==false)
							FileManager.moveImage(imgPathFrom, folder, nomeImg);
					}
				}
				else if(AnimeIndex.airingMap.containsKey(name))
				{
					String type = "Anime in Corso";
					TreeMap<String,AnimeData> map = AnimeIndex.airingMap;
					String imgPathFrom = map.get(name).getImagePath(type);
					String nomeImg = map.get(name).getImageName();
					imgPathTo = FileManager.getImageFolderPath() + folder + File.separator + nomeImg;
					File img = new File(imgPathTo);
					if (!imgPathFrom.equals(imgPathTo))
					{
						if(img.isFile()==false)
							FileManager.moveImage(imgPathFrom, folder, nomeImg);
					}
				}
				else if(AnimeIndex.ovaMap.containsKey(name))
				{
					String type = "OAV";
					TreeMap<String,AnimeData> map = AnimeIndex.ovaMap;
					String imgPathFrom = map.get(name).getImagePath(type);
					String nomeImg = map.get(name).getImageName();
					imgPathTo = FileManager.getImageFolderPath() + folder + File.separator + nomeImg;
					File img = new File(imgPathTo);
					if (!imgPathFrom.equals(imgPathTo))
					{
						if(img.isFile()==false)
							FileManager.moveImage(imgPathFrom, folder, nomeImg);
					}
				}
				else if(AnimeIndex.filmMap.containsKey(name))
				{
					String type = "Film";
					TreeMap<String,AnimeData> map = AnimeIndex.filmMap;
					String imgPathFrom = map.get(name).getImagePath(type);
					String nomeImg = map.get(name).getImageName();
					imgPathTo = FileManager.getImageFolderPath() + folder + File.separator + nomeImg;
					File img = new File(imgPathTo);
					if (!imgPathFrom.equals(imgPathTo))
					{
						if(img.isFile()==false)
							FileManager.moveImage(imgPathFrom, folder, nomeImg);
					}
				}
				else if(AnimeIndex.completedToSeeMap.containsKey(name))
				{
					String type = "Completi Da Vedere";
					TreeMap<String,AnimeData> map = AnimeIndex.completedToSeeMap;
					String imgPathFrom = map.get(name).getImagePath(type);
					String nomeImg = map.get(name).getImageName();
					imgPathTo = FileManager.getImageFolderPath() + folder + File.separator + nomeImg;
					File img = new File(imgPathTo);
					if (!imgPathFrom.equals(imgPathTo))
					{
						if(img.isFile()==false)
							FileManager.moveImage(imgPathFrom, folder, nomeImg);
					}
				}
				
				if(folder.equalsIgnoreCase("Completed"))
					AnimeIndex.completedSessionAnime.remove(imgPathTo);
				if(folder.equalsIgnoreCase("Airing"))
					AnimeIndex.airingSessionAnime.remove(imgPathTo);
				if(folder.equalsIgnoreCase("Ova"))
					AnimeIndex.ovaSessionAnime.remove(imgPathTo);
				if(folder.equalsIgnoreCase("Film"))
					AnimeIndex.filmSessionAnime.remove(imgPathTo);
				if(folder.equalsIgnoreCase("Completed to See"))
					AnimeIndex.completedToSeeSessionAnime.remove(imgPathTo);
			}
		}
	}
}
