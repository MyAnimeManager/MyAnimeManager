package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import main.AnimeIndex;
import util.task.NewsTask;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class NewsBoardDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JTextPane textPane;
	
	/**
	 * Create the dialog.
	 */
	public NewsBoardDialog()
	{
		super(AnimeIndex.frame, false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				NewsTask task = new NewsTask();
				task.execute();
			}
		});
		setUndecorated(true);
		setBounds(100, 100, AnimeIndex.mainFrame.getWidth() + 2, 100);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
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
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Chiudi");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new Timer(1, new ActionListener() {
				               public void actionPerformed(ActionEvent e) {

				            	   NewsBoardDialog.this.setLocation(AnimeIndex.mainFrame.getLocationOnScreen().x,  NewsBoardDialog.this.getLocationOnScreen().y - 1);
				            	   AnimeIndex.mainFrame.requestFocus();
				            	   if ( NewsBoardDialog.this.getLocationOnScreen().y == (AnimeIndex.mainFrame.getLocationOnScreen().y + AnimeIndex.mainFrame.getHeight() -  NewsBoardDialog.this.getHeight())) {
				                     ((Timer) e.getSource()).stop();
				                     NewsBoardDialog.this.dispose();
				            }
				               }
				            }).start();
					}
				});
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
