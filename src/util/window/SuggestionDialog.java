package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import main.AnimeIndex;
import util.SuggestionTaskPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class SuggestionDialog extends JDialog {
	private SuggestionTaskPane suggestionOne = new SuggestionTaskPane();
	private SuggestionTaskPane suggestionTwo = new SuggestionTaskPane();
	private SuggestionTaskPane suggestionThree = new SuggestionTaskPane();
	private SuggestionTaskPane suggestionFuor = new SuggestionTaskPane();
	private SuggestionTaskPane suggestionFive = new SuggestionTaskPane();
	private SuggestionTaskPane[] taskPaneArray = {suggestionOne, suggestionTwo, suggestionThree, suggestionFuor, suggestionFive};
	
	/**
	 * Create the dialog.
	 */
	public SuggestionDialog()
	{
		super(AnimeIndex.frame, true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				//TODO prendere dal sito i vari dati
			}
		});
		setTitle("Anime Consigliati");
		setBounds(100, 100, 450, 225);
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
			buttonPane.add(btnOpen);
			
			JButton btnClose = new JButton("Chiudi\r\n");
			buttonPane.add(btnClose);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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
	
}
