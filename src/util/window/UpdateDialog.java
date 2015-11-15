package util.window;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import main.AnimeIndex;
import util.AnimeIndexProperties;
import util.ExternalProgram;
import util.FileManager;

	public class UpdateDialog extends JDialog{

	    private JEditorPane infoPane;
	    private JScrollPane scp;
	    private JButton ok;
	    private JButton cancel;
	    private JPanel pan1;
	    private JPanel pan2;
	    private JLabel lblNewLabel;
	    private JLabel lblNovit;
	    public DownloadingDialog dial; 

	    public UpdateDialog(String info) {
	    	super(AnimeIndex.frame,true);
	    	setModal(true);
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

	        this.setTitle("Ricerca aggiornamenti");
	        pan1 = new JPanel();
	        pan1.setLayout(new BorderLayout());

	        pan2 = new JPanel();

	        scp = new JScrollPane();
	        scp.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
	        scp.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
	        scp.setViewportView(infoPane);

	        ok = new JButton("Aggiorna");
	        ok.addActionListener( new ActionListener(){

	            public void actionPerformed(ActionEvent e) {
	            	dial = new DownloadingDialog();
	            	UpdateDialog.this.dispose();
	            	dial.setLocationRelativeTo(AnimeIndex.mainFrame);
	            	dial.setVisible(true);
					save();
					ExternalProgram ext = new ExternalProgram(FileManager.getAppDataPath() + File.separator + "Update" + File.separator + AnimeIndex.NEW_VERSION);
					ext.run();
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

	        this.setSize(293, 200);
	    }
	    
	    private void save()
	    {
	    	try {
				FileManager.saveFansubList();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
				FileManager.saveAnimeList("completed.txt", AnimeIndex.completedMap);
				FileManager.saveAnimeList("airing.txt", AnimeIndex.airingMap);
				FileManager.saveAnimeList("ova.txt", AnimeIndex.ovaMap);
				FileManager.saveAnimeList("film.txt", AnimeIndex.filmMap);
				FileManager.saveAnimeList("toSee.txt", AnimeIndex.completedToSeeMap);
			
				deleteUselessImage(AnimeIndex.completedDeletedAnime);
				deleteUselessImage(AnimeIndex.airingDeletedAnime);
				deleteUselessImage(AnimeIndex.ovaDeletedAnime);
				deleteUselessImage(AnimeIndex.filmDeletedAnime);
				deleteUselessImage(AnimeIndex.completedToSeeDeletedAnime);
				AnimeIndexProperties.saveProperties(AnimeIndex.appProp);
		}
	    
	private void deleteUselessImage(ArrayList<String> arrayList)
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

