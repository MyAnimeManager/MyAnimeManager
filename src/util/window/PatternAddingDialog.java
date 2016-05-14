package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.AnimeIndex;
import util.SortedListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class PatternAddingDialog extends JDialog
{
	private SortedListModel model = new SortedListModel();
	private JList list;
	
	/**
	 * Create the dialog.
	 */
	public PatternAddingDialog()
	{
		super(AnimeIndex.frame, true);
		setTitle("Aggiungi Anime");
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				for (Object animeName : AnimeIndex.completedModel.toArray())
				{
					if (!AnimeIndex.patternAnimeMap.containsKey(animeName))
					{
						model.addElement((String)animeName);
					}
				}
				for (Object animeName : AnimeIndex.airingModel.toArray())
				{
					if (!AnimeIndex.patternAnimeMap.containsKey(animeName))
					{
						model.addElement((String)animeName);
					}
				}
				for (Object animeName : AnimeIndex.ovaModel.toArray())
				{
					if (!AnimeIndex.patternAnimeMap.containsKey(animeName))
					{
						model.addElement((String)animeName);
					}
				}
				for (Object animeName : AnimeIndex.filmModel.toArray())
				{
					if (!AnimeIndex.patternAnimeMap.containsKey(animeName))
					{
						model.addElement((String)animeName);
					}
				}
				for (Object animeName : AnimeIndex.completedToSeeModel.toArray())
				{
					if (!AnimeIndex.patternAnimeMap.containsKey(animeName))
					{
						model.addElement((String)animeName);
					}
				}
			}
		});
		setBounds(100, 100, 380, 250);
		getContentPane().setLayout(new BorderLayout());
		{
			JScrollPane scrollPane = new JScrollPane();
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			{
				list = new JList(model);
				scrollPane.setViewportView(list);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(100);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						List<String> selectedList = list.getSelectedValuesList();
						for(String anime : selectedList)
						{
							AnimeIndex.preferenceDialog.patternModel.addRow(new String[]{anime,"%T% - %N%"});
							AnimeIndex.patternAnimeMap.put(anime, "%T% - %N%");
						}
						PatternAddingDialog.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annulla");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PatternAddingDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
}
