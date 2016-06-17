package util.window;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import main.AnimeIndex;
import util.AnimeData;
import util.AnimeIndexProperties;
import util.FileManager;
import util.MAMUtil;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class ExitSaveDialog extends JDialog
{
	private final JPanel contentPanel = new JPanel();

	public ExitSaveDialog()
	{
		super(AnimeIndex.frame, true);
		setType(Type.POPUP);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ExitSaveDialog.class.getResource("/image/icon.png")));
		setTitle("Salvare le modifiche prima di uscire?");
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 335, 156);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setAlignmentY(1.0f);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{88, 0};
		gbl_contentPanel.rowHeights = new int[]{40, 40, 34, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		JButton btnExit = new JButton("Esci");
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.fill = GridBagConstraints.BOTH;
		gbc_btnExit.insets = new Insets(0, 0, 5, 0);
		gbc_btnExit.gridx = 0;
		gbc_btnExit.gridy = 0;
		contentPanel.add(btnExit, gbc_btnExit);
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				AnimeIndexProperties.saveProperties(AnimeIndex.appProp);

				imageShifter();
				
				try
				{
					FileManager.deleteData(new File(MAMUtil.getTempDownloadPath()));
				}
				catch (IOException e1)
				{
					MAMUtil.writeLog(e1);
					e1.printStackTrace();
				}
				
				MAMUtil.deleteUselessImage(AnimeIndex.completedSessionAnime);
				MAMUtil.deleteUselessImage(AnimeIndex.airingSessionAnime);
				MAMUtil.deleteUselessImage(AnimeIndex.ovaSessionAnime);
				MAMUtil.deleteUselessImage(AnimeIndex.filmSessionAnime);
				MAMUtil.deleteUselessImage(AnimeIndex.completedToSeeSessionAnime);
				MAMUtil.deleteUselessImage(AnimeIndex.sessionAddedAnime);
				Object[] keyArr = AnimeIndex.sessionAddedAnimeImagesShiftsRegister.keySet().toArray();
				for (int i = 0; i < keyArr.length; i++)
					try
					{
						FileManager.deleteData(new File(AnimeIndex.sessionAddedAnimeImagesShiftsRegister.get(keyArr[i])));
					}
					catch (IOException e)
					{
					}
				System.exit(0);
			}
		});
		JButton btnSaveAndExit = new JButton("Salva ed Esci");
		GridBagConstraints gbc_btnSaveAndExit = new GridBagConstraints();
		gbc_btnSaveAndExit.fill = GridBagConstraints.BOTH;
		gbc_btnSaveAndExit.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveAndExit.gridx = 0;
		gbc_btnSaveAndExit.gridy = 1;
		contentPanel.add(btnSaveAndExit, gbc_btnSaveAndExit);
		btnSaveAndExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// salva
				MAMUtil.saveData();

				System.exit(0);
			}
		});
		{
			JButton button = new JButton("Annulla");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ExitSaveDialog.this.dispose();
				}
			});
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.fill = GridBagConstraints.BOTH;
			gbc_button.gridx = 0;
			gbc_button.gridy = 2;
			contentPanel.add(button, gbc_button);
		}
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(ExitSaveDialog.class.getResource("/image/Hatsune-Miku-Vocaloid...............................png")));
			lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
			getContentPane().add(lblNewLabel, BorderLayout.WEST);
		}
	}

	private void imageShifter()
	{
		Object[] arrayName = AnimeIndex.shiftsRegister.keySet().toArray();
		for (int i = 0; i < AnimeIndex.shiftsRegister.size(); i++)
		{
			String name = (String) arrayName[i];
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

			if (!AnimeIndex.sessionAddedAnime.contains(name))
			{
				if (AnimeIndex.completedMap.containsKey(name))
				{
					String type = "Anime Completati";
					TreeMap<String, AnimeData> map = AnimeIndex.completedMap;
					String imgPathFrom = map.get(name).getImagePath(type);
					String nomeImg = map.get(name).getImageName();
					imgPathTo = MAMUtil.getImageFolderPath() + folder + File.separator + nomeImg;
					File img = new File(imgPathTo);
					if (!imgPathFrom.equals(imgPathTo))
						if (img.isFile() == false)
							FileManager.moveImage(imgPathFrom, folder, nomeImg);
				}
				else if (AnimeIndex.airingMap.containsKey(name))
				{
					String type = "Anime in Corso";
					TreeMap<String, AnimeData> map = AnimeIndex.airingMap;
					String imgPathFrom = map.get(name).getImagePath(type);
					String nomeImg = map.get(name).getImageName();
					imgPathTo = MAMUtil.getImageFolderPath() + folder + File.separator + nomeImg;
					File img = new File(imgPathTo);
					if (!imgPathFrom.equals(imgPathTo))
						if (img.isFile() == false)
							FileManager.moveImage(imgPathFrom, folder, nomeImg);
				}
				else if (AnimeIndex.ovaMap.containsKey(name))
				{
					String type = "OAV";
					TreeMap<String, AnimeData> map = AnimeIndex.ovaMap;
					String imgPathFrom = map.get(name).getImagePath(type);
					String nomeImg = map.get(name).getImageName();
					imgPathTo = MAMUtil.getImageFolderPath() + folder + File.separator + nomeImg;
					File img = new File(imgPathTo);
					if (!imgPathFrom.equals(imgPathTo))
						if (img.isFile() == false)
							FileManager.moveImage(imgPathFrom, folder, nomeImg);
				}
				else if (AnimeIndex.filmMap.containsKey(name))
				{
					String type = "Film";
					TreeMap<String, AnimeData> map = AnimeIndex.filmMap;
					String imgPathFrom = map.get(name).getImagePath(type);
					String nomeImg = map.get(name).getImageName();
					imgPathTo = MAMUtil.getImageFolderPath() + folder + File.separator + nomeImg;
					File img = new File(imgPathTo);
					if (!imgPathFrom.equals(imgPathTo))
						if (img.isFile() == false)
							FileManager.moveImage(imgPathFrom, folder, nomeImg);
				}
				else if (AnimeIndex.completedToSeeMap.containsKey(name))
				{
					String type = "Completi Da Vedere";
					TreeMap<String, AnimeData> map = AnimeIndex.completedToSeeMap;
					String imgPathFrom = map.get(name).getImagePath(type);
					String nomeImg = map.get(name).getImageName();
					imgPathTo = MAMUtil.getImageFolderPath() + folder + File.separator + nomeImg;
					File img = new File(imgPathTo);
					if (!imgPathFrom.equals(imgPathTo))
						if (img.isFile() == false)
							FileManager.moveImage(imgPathFrom, folder, nomeImg);
				}

				if (folder.equalsIgnoreCase("Completed"))
					AnimeIndex.completedSessionAnime.remove(imgPathTo);
				if (folder.equalsIgnoreCase("Airing"))
					AnimeIndex.airingSessionAnime.remove(imgPathTo);
				if (folder.equalsIgnoreCase("Ova"))
					AnimeIndex.ovaSessionAnime.remove(imgPathTo);
				if (folder.equalsIgnoreCase("Film"))
					AnimeIndex.filmSessionAnime.remove(imgPathTo);
				if (folder.equalsIgnoreCase("Completed to See"))
					AnimeIndex.completedToSeeSessionAnime.remove(imgPathTo);
			}
		}
	}
}
