package util.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import main.AnimeIndex;
import util.ExternalProgram;
import util.MAMUtil;

public class UpdateDialog extends JDialog
{

	private JEditorPane infoPane;
	private JScrollPane scp;
	private JButton ok;
	private JButton cancel;
	private JPanel pan1;
	private JPanel pan2;
	private JLabel lblNewLabel;
	private JLabel lblNovit;
	public DownloadingDialog dial;

	public UpdateDialog(String info)
	{
		super(AnimeIndex.frame, true);
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

	private void initComponents()
	{

		this.setTitle("Ricerca aggiornamenti");
		pan1 = new JPanel();
		pan1.setLayout(new BorderLayout());

		pan2 = new JPanel();

		scp = new JScrollPane();
		scp.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
		scp.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
		scp.setViewportView(infoPane);

		ok = new JButton("Aggiorna");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				dial = new DownloadingDialog();
				UpdateDialog.this.dispose();
				dial.setLocationRelativeTo(AnimeIndex.mainPanel);
				dial.setVisible(true);
				if(AnimeIndex.appProp.getProperty("Ask_for_donation").equalsIgnoreCase("false"))
				{
					AnimeIndex.appProp.setProperty("Ask_for_donation", "true");
				}
				MAMUtil.saveData();
				ExternalProgram ext = new ExternalProgram(MAMUtil.getAppDataPath() + File.separator + "Update" + File.separator + AnimeIndex.NEW_VERSION);
				ext.run();
				System.exit(0);
			}
		});

		cancel = new JButton("Annulla");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
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

}
