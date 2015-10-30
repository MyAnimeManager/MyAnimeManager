package util.window;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import main.AnimeIndex;
import util.SuggestionHelper;
import util.SuggestionTaskPane;
import util.task.SuggestionFetcherTask;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SuggestionDialog extends JDialog {
	private static SuggestionTaskPane suggestionOne = new SuggestionTaskPane();
	private static SuggestionTaskPane suggestionTwo = new SuggestionTaskPane();
	private static SuggestionTaskPane suggestionThree = new SuggestionTaskPane();
	private static SuggestionTaskPane suggestionFuor = new SuggestionTaskPane();
	private static SuggestionTaskPane suggestionFive = new SuggestionTaskPane();
	private static SuggestionTaskPane[] taskPaneArray = {suggestionOne, suggestionTwo, suggestionThree, suggestionFuor, suggestionFive};
	private static String linkOne;
	private static String linkTwo;
	private static String linkThree;
	private static String linkFour;
	private static String linkFive;
	private static String[] linkArray = {linkOne, linkTwo, linkThree, linkFour, linkFive};
	public static SuggestionWaitDialog waitDialog = new SuggestionWaitDialog();
	public static boolean dataAlreadyFetched = false;
	
	/**
	 * Create the dialog.
	 */
	public SuggestionDialog()
	{
		super(AnimeIndex.frame, true);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				if (!dataAlreadyFetched)
				{
				waitDialog.setLocationRelativeTo(SuggestionDialog.this);
				waitDialog.setVisible(true);
				SuggestionFetcherTask task = new SuggestionFetcherTask();
				task.execute();
				}
			}
		});
		setTitle("Anime Consigliati");
		setBounds(100, 100, 470, 225);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(50);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JButton btnAdd = new JButton("Aggiungi");
			buttonPane.add(btnAdd);
			
			JButton btnOpen = new JButton("Apri");
			btnOpen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int taskPaneNumber = getSelectedTaskPane();
					try
					{
						URI uriLink = new URI(linkArray[taskPaneNumber]);
						Desktop.getDesktop().browse(uriLink);
					}
					catch (Exception ex)
					{
						
					}
				}
			});
			buttonPane.add(btnOpen);
			
			JButton btnClose = new JButton("Chiudi");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SuggestionDialog.this.dispose();
				}
			});
			buttonPane.add(btnClose);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
			scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			{
				JPanel panel = new JPanel();
				scrollPane.setViewportView(panel);
				GridBagLayout gbl_panel = new GridBagLayout();
				gbl_panel.columnWidths = new int[]{0, 0};
				gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
				gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
				gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				panel.setLayout(gbl_panel);
				
					suggestionOne.addPropertyChangeListener(OpenCloseListener(1));
					GridBagConstraints gbc_suggestionOne = new GridBagConstraints();
					gbc_suggestionOne.insets = new Insets(0, 0, 5, 0);
					gbc_suggestionOne.fill = GridBagConstraints.HORIZONTAL;
					gbc_suggestionOne.gridx = 0;
					gbc_suggestionOne.gridy = 0;
					panel.add(suggestionOne, gbc_suggestionOne);
				
					suggestionTwo.addPropertyChangeListener(OpenCloseListener(2));
					GridBagConstraints gbc_suggestionTwo = new GridBagConstraints();
					gbc_suggestionTwo.insets = new Insets(0, 0, 5, 0);
					gbc_suggestionTwo.fill = GridBagConstraints.HORIZONTAL;
					gbc_suggestionTwo.gridx = 0;
					gbc_suggestionTwo.gridy = 1;
					panel.add(suggestionTwo, gbc_suggestionTwo);
				
					suggestionThree.addPropertyChangeListener(OpenCloseListener(3));
					GridBagConstraints gbc_suggestionThree = new GridBagConstraints();
					gbc_suggestionThree.insets = new Insets(0, 0, 5, 0);
					gbc_suggestionThree.fill = GridBagConstraints.HORIZONTAL;
					gbc_suggestionThree.gridx = 0;
					gbc_suggestionThree.gridy = 2;
					panel.add(suggestionThree, gbc_suggestionThree);
				
					suggestionFuor.addPropertyChangeListener(OpenCloseListener(4));
					GridBagConstraints gbc_suggestionFuor = new GridBagConstraints();
					gbc_suggestionFuor.insets = new Insets(0, 0, 5, 0);
					gbc_suggestionFuor.fill = GridBagConstraints.HORIZONTAL;
					gbc_suggestionFuor.gridx = 0;
					gbc_suggestionFuor.gridy = 3;
					panel.add(suggestionFuor, gbc_suggestionFuor);
				
					suggestionFive.addPropertyChangeListener(OpenCloseListener(5));
					GridBagConstraints gbc_suggestionFive = new GridBagConstraints();
					gbc_suggestionFive.fill = GridBagConstraints.HORIZONTAL;
					gbc_suggestionFive.gridx = 0;
					gbc_suggestionFive.gridy = 4;
					panel.add(suggestionFive, gbc_suggestionFive);
				
			}
		}
	}
	
	private PropertyChangeListener OpenCloseListener(int suggestionPaneNumber)
	{
		PropertyChangeListener propListener = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equalsIgnoreCase("collapsed"))
				{
					if (!taskPaneArray[suggestionPaneNumber - 1].isCollapsed())
					{
						for (SuggestionTaskPane taskPane : taskPaneArray) 
						{
							if (!taskPane.equals(taskPaneArray[suggestionPaneNumber - 1]))
								taskPane.setCollapsed(true);
						}
					}
				}
			}
		};
		return propListener;
	}
	
	public static void storeSuggestion(int suggestionNumber)
	{
		try
		{
			String name = SuggestionHelper.getSuggestion(suggestionNumber + 1);
			String description = SuggestionHelper.getDescription(suggestionNumber + 1);
			String link = SuggestionHelper.getLink(suggestionNumber + 1);
			
			taskPaneArray[suggestionNumber].setTitle(name);
			taskPaneArray[suggestionNumber].setText(description);
			linkArray[suggestionNumber] = link;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private int getSelectedTaskPane()
	{
		for (int i = 0; i < taskPaneArray.length; i++)
		{
			if (!taskPaneArray[i].isCollapsed())
				return i;
		}
		return -1;
	}
	
}
