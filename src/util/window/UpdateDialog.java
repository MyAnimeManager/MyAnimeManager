package util.window;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import main.AnimeIndex;

import org.apache.commons.io.FileUtils;

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

	    public UpdateDialog(String info) {
	    	setIconImage(Toolkit.getDefaultToolkit().getImage(UpdateDialog.class.getResource("/image/Update.png")));
	    	setAlwaysOnTop(true);
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
	        this.setTitle("Nuovo aggiornamento!");
	        pan1 = new JPanel();
	        pan1.setLayout(new BorderLayout());

	        pan2 = new JPanel();

	        scp = new JScrollPane();
	        scp.setViewportView(infoPane);

	        ok = new JButton("Aggiorna");
	        ok.addActionListener( new ActionListener(){

	            public void actionPerformed(ActionEvent e) {
	                update();
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
	    	   String urlString = Updater.getDownloadLink();
		       URL url = new URL(urlString);
		       System.out.println(url.toString());
		       File file = new File(FileManager.getAppDataPath() + "Update" + File.separator + "My Anime Manager Updated.exe");
		       FileUtils.copyURLToFile(url, file);
//		       System.out.println("Download completato");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
}

