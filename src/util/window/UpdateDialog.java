package util.window;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import main.AnimeIndex;

import org.apache.commons.io.FileUtils;

import util.AnimeData;
import util.AnimeIndexProperties;
import util.ExternalProgram;
import util.FileManager;
import util.Updater;

	public class UpdateDialog extends JFrame{

	    private JEditorPane infoPane;
	    private JScrollPane scp;
	    private JButton ok;
	    private JButton cancel;
	    private JPanel pan1;
	    private JPanel pan2;
	    private JLabel lblNewLabel;
	    private JLabel lblNovit;
	    private static final String NEW_VERSION = "My Anime Manager Updated.exe";

	    public UpdateDialog(String info) {
	    	setIconImage(Toolkit.getDefaultToolkit().getImage(UpdateDialog.class.getResource("/image/Update.png")));
	    	setResizable(false);
	    	setType(Type.POPUP);
	        initComponents();
	        lblNewLabel.setText(info);
	        
	        lblNovit = new JLabel("Novit\u00E0");
	        lblNovit.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        lblNovit.setHorizontalAlignment(SwingConstants.CENTER);
	        scp.setColumnHeaderView(lblNovit);
	    }

	    private void initComponents() {

	        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	        this.setTitle("Ricerca aggiornamenti");
	        pan1 = new JPanel();
	        pan1.setLayout(new BorderLayout());

	        pan2 = new JPanel();

	        scp = new JScrollPane();
	        scp.setViewportView(infoPane);

	        ok = new JButton("Aggiorna");
	        ok.addActionListener( new ActionListener(){

	            public void actionPerformed(ActionEvent e) {
	                update();
	                UpdateDialog.this.dispose();
	                int shouldCancel = JOptionPane.showConfirmDialog(AnimeIndex.mainFrame, "Installare ora l'aggiornamento?", "Installare ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(shouldCancel==0)
					{
						save();
						ExternalProgram ext = new ExternalProgram(System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator + "Update" + File.separator + NEW_VERSION);
						ext.run();
					}
	            }
	        });

	        cancel = new JButton("Annulla");
	        cancel.addActionListener( new ActionListener(){

	            public void actionPerformed(ActionEvent e) {
	                UpdateDialog.this.dispose();
	            }
	        });
	        pan2.setLayout(new GridLayout(0, 2, 0, 0));
	        pan2.add(ok);
	        pan2.add(cancel);
	        pan1.add(pan2, BorderLayout.SOUTH);
	        pan1.add(scp, BorderLayout.CENTER);
	        
	        lblNewLabel = new JLabel();
	        scp.setViewportView(lblNewLabel);
	        getContentPane().add(pan1);
//	        pack();
	        this.setSize(293, 200);
	    }
	    private void update()
	    {
	       try 
	       {
	    	   setCursor(new Cursor(Cursor.WAIT_CURSOR));
	    	   String urlString = Updater.getDownloadLink();
		       URL url = new URL(urlString);
		       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    	   int bytes = conn.getContentLength();
		       DownloadingDialog download = new DownloadingDialog(bytes, url);
		       setCursor(Cursor.getDefaultCursor());
		       
//		       System.out.println("Download completato");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    private void save()
	    {
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

