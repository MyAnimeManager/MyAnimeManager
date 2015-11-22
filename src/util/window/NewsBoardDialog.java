package util.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import main.AnimeIndex;
import util.MAMUtil;
import util.task.NewsTask;

public class NewsBoardDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private LinkedHashMap<String,String> animeMap;
	NewsTask task = new NewsTask();
	private JPanel newsPanel;
	
	public NewsBoardDialog()
	{
		super(AnimeIndex.frame, false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				task.execute();
			}
		});
		setUndecorated(true);
		setType(Type.UTILITY);
		setBounds(100, 100, 795, 100);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBorder(new MatteBorder(0, 0, 0, 0, new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				newsPanel = new JPanel();
				scrollPane.setViewportView(newsPanel);
				GridBagLayout gbl_newsPanel = new GridBagLayout();
				gbl_newsPanel.columnWidths = new int[]{0, 0};
				gbl_newsPanel.rowHeights = new int[]{0, 0};
				gbl_newsPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
				gbl_newsPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
				newsPanel.setLayout(gbl_newsPanel);
				{
					JLabel titleLabel = new JLabel("Red Anime Database Newsboard");
					titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
					titleLabel.setOpaque(true);
					titleLabel.setFont(AnimeIndex.segui.deriveFont(16F));
					GridBagConstraints gbc_titleLabel = new GridBagConstraints();
					gbc_titleLabel.fill = GridBagConstraints.HORIZONTAL;
					gbc_titleLabel.gridx = 0;
					gbc_titleLabel.gridy = 0;
					newsPanel.add(titleLabel, gbc_titleLabel);
				}
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
				                     AnimeIndex.newsBoardDialog.dispose();
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
		
		task.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("state"))
				{
					if(evt.getNewValue().toString().equalsIgnoreCase("done"))
						setNews();
				}

			}
		});
	}
	
	public void setMap(LinkedHashMap<String,String> newsMap)
	{
		animeMap = newsMap;
	}
	
	private void setNews()
	{
		int row = 1;
		GridBagConstraints gbc_titleLabel = new GridBagConstraints();
		gbc_titleLabel.gridx = 0;
		gbc_titleLabel.fill = GridBagConstraints.HORIZONTAL;
		for (Map.Entry<String, String> entry : animeMap.entrySet())
		{
			String name = entry.getKey();
			String link = entry.getValue();
			JLabel nameLabel = new JLabel(name);
			
			if ((row%2) == 0)
			{
				nameLabel.setOpaque(true);
				nameLabel.setBackground(Color.BLACK);
			}
			nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			nameLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			nameLabel.addMouseListener(getClickLink(link));
			nameLabel.setFont(AnimeIndex.segui.deriveFont(12f));
			gbc_titleLabel.gridy = row;
			newsPanel.add(nameLabel, gbc_titleLabel);
			row++;
		}
		newsPanel.revalidate();
		newsPanel.repaint();
	}
	
	private MouseAdapter getClickLink(String link)	
	{
		MouseAdapter mouse = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

					
					try
					{
						URI uriLink = new URI(link);
						Desktop.getDesktop().browse(uriLink);
					}
					catch (Exception e1)
					{
						MAMUtil.writeLog(e1);
						e1.printStackTrace();
					}

			}
		};
		
		return mouse;
	}
	
}
