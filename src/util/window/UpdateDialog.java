package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.Window.Type;
import java.awt.Toolkit;

	public class UpdateDialog extends JFrame{

	    private JEditorPane infoPane;
	    private JScrollPane scp;
	    private JButton ok;
	    private JButton cancel;
	    private JPanel pan1;
	    private JPanel pan2;

	    public UpdateDialog(String info) {
	    	setIconImage(Toolkit.getDefaultToolkit().getImage(UpdateDialog.class.getResource("/image/icon.png")));
	    	setAlwaysOnTop(true);
	    	setResizable(false);
	    	setType(Type.POPUP);
	        initComponents();
	        infoPane.setText(info);
	    }

	    private void initComponents() {

	        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	        this.setTitle("Nuovo aggiornamento!");
	        pan1 = new JPanel();
	        pan1.setLayout(new BorderLayout());

	        pan2 = new JPanel();

	        infoPane = new JEditorPane();
	        infoPane.setContentType("text/html");

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
	        getContentPane().add(pan1);
//	        pack();
	        this.setSize(293, 200);
	    }
	    private void update()
	    {
	        // TODO: Add my Code!
	    }

	}

