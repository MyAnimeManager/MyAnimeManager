package util.window;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import util.MAMUtil;
import main.AnimeIndex;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class NewsBoardDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JTextPane textPane;
	
	public NewsBoardDialog()
	{
		super(AnimeIndex.frame, false);
		setUndecorated(true);
		setType(Type.UTILITY);
		setBounds(100, 100, AnimeIndex.mainFrame.getWidth()+1, 100);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBorder(new MatteBorder(0, 0, 0, 0, new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				textPane = new JTextPane();
				scrollPane.setViewportView(textPane);
				textPane.setOpaque(false);
				textPane.setEditable(false);
				textPane.setContentType("text/html");
			}
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u2191 \u2191 \u2191 \u2191 \u2191");
				okButton.setFont(MAMUtil.segui().deriveFont(12f));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new Timer(1, new ActionListener() {
				               public void actionPerformed(ActionEvent e) {

				            	   NewsBoardDialog.this.setLocation(AnimeIndex.mainFrame.getLocationOnScreen().x,  NewsBoardDialog.this.getLocationOnScreen().y - 1);
				            	   AnimeIndex.mainFrame.requestFocus();
				            	   if ( NewsBoardDialog.this.getLocationOnScreen().y == (AnimeIndex.mainFrame.getLocationOnScreen().y + AnimeIndex.mainFrame.getHeight() - NewsBoardDialog.this.getHeight())) {
				                     ((Timer) e.getSource()).stop();
				                     NewsBoardDialog.this.dispose();
				            }
				               }
				            }).start();
					}
				});
				buttonPane.setLayout(new GridLayout(0, 1, 0, 0));
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	public void setNews(String news)
	{
		textPane.setText(news);
	}
	
}
