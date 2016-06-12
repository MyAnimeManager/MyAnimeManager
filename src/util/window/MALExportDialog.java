package util.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.AnimeIndex;
import util.SortedListModel;
import util.task.SynchronizeToMALTask;


public class MALExportDialog extends JDialog
{
	
	private final JPanel contentPanel = new JPanel();
	private SortedListModel listModel = new SortedListModel();
	private JButton sinchronyzeButton;
	private JList list;
	
	private ArrayList<Integer> completedIndeces;
	private ArrayList<Integer> airingIndeces;
	private ArrayList<Integer> ovaIndeces;
	private ArrayList<Integer> filmIndeces;
	private ArrayList<Integer> completedToSeeIndeces;
	
	
	public MALExportDialog()
	{
		super(AnimeIndex.frame, true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listModel.addAll(AnimeIndex.completedMap.keySet());
				listModel.addAll(AnimeIndex.airingMap.keySet());
				listModel.addAll(AnimeIndex.ovaMap.keySet());
				listModel.addAll(AnimeIndex.filmMap.keySet());
				listModel.addAll(AnimeIndex.completedToSeeMap.keySet());
				
				completedIndeces = getIndexOf(AnimeIndex.completedModel);
				airingIndeces = getIndexOf(AnimeIndex.airingModel);
				ovaIndeces = getIndexOf(AnimeIndex.ovaModel);
				filmIndeces = getIndexOf(AnimeIndex.filmModel);
				completedToSeeIndeces = getIndexOf(AnimeIndex.completedModel);
				
			}
		});
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel optionPanel = new JPanel();
			optionPanel.setPreferredSize(new Dimension(10, 60));
			contentPanel.add(optionPanel, BorderLayout.NORTH);
			{
				JButton selectAllButton = new JButton("Seleziona Tutti");
				selectAllButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int total = listModel.getSize();
						list.setSelectionInterval(0, total -1);
					}
				});
				optionPanel.add(selectAllButton);
			}
			{
				JButton deselectAllButton = new JButton("Deseleziona Tutti");
				deselectAllButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						list.setSelectedIndex(-1);
					}
				});
				optionPanel.add(deselectAllButton);
			}
			{
				JButton compeltedButton = new JButton("Completi");
				compeltedButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int size = completedIndeces.size();
						int[] indeces = new int[size];
						for (int i = 0; i < size; i++)
						{
							indeces[i] = completedIndeces.get(i);
						}
						list.setSelectedIndices(indeces);
					}
				});
				optionPanel.add(compeltedButton);
			}
			{
				JButton airingButton = new JButton("In Corso");
				airingButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int size = airingIndeces.size();
						int[] indeces = new int[size];
						for (int i = 0; i < size; i++)
						{
							indeces[i] = airingIndeces.get(i);
						}
						list.setSelectedIndices(indeces);
					}
				});
				optionPanel.add(airingButton);
			}
			{
				JButton ovaButton = new JButton("OAV");
				ovaButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int size = ovaIndeces.size();
						int[] indeces = new int[size];
						for (int i = 0; i < size; i++)
						{
							indeces[i] = ovaIndeces.get(i);
						}
						list.setSelectedIndices(indeces);
					}
				});
				optionPanel.add(ovaButton);
			}
			{
				JButton filmButton = new JButton("Film");
				filmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int size = filmIndeces.size();
						int[] indeces = new int[size];
						for (int i = 0; i < size; i++)
						{
							indeces[i] = filmIndeces.get(i);
						}
						list.setSelectedIndices(indeces);
					}
				});
				optionPanel.add(filmButton);
			}
			{
				JButton completedToSeeButton = new JButton("Completi da Vedere");
				completedToSeeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int size = completedToSeeIndeces.size();
						int[] indeces = new int[size];
						for (int i = 0; i < size; i++)
						{
							indeces[i] = completedToSeeIndeces.get(i);
						}
						list.setSelectedIndices(indeces);
					}
				});
				optionPanel.add(completedToSeeButton);
			}
		}
		{
			JScrollPane listPane = new JScrollPane();
			contentPanel.add(listPane, BorderLayout.CENTER);
			{
				list = new JList(listModel);
				list.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (!list.isSelectionEmpty())
							sinchronyzeButton.setEnabled(true);
						else
							sinchronyzeButton.setEnabled(false);
					}
				});
				listPane.setViewportView(list);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(100);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				sinchronyzeButton = new JButton("Sincronizza");
				sinchronyzeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						JPanel myPanel = new JPanel();
						JTextField usernameField = new JTextField();
					    JTextField passwordField = new JTextField();
					    myPanel.add(new JLabel("Username:"));
					    myPanel.add(usernameField);
					    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
					    myPanel.add(new JLabel("Password:"));
					    myPanel.add(passwordField);

					    int result = JOptionPane.showConfirmDialog(AnimeIndex.frame, "Inserire i dati del proprio account MyAnimeList", "Dati richiesti", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					    if (result == JOptionPane.OK_OPTION)
					    {
					       System.out.println("x value: " + usernameField.getText());
					       System.out.println("y value: " + passwordField.getText());
					       
					       List animeToAdd = list.getSelectedValuesList();
					       String username = usernameField.getText();
					       String password = passwordField.getText();
					       //TODO aggiungere waiting dialog
					       SynchronizeToMALTask task = new SynchronizeToMALTask(username, password, animeToAdd);
					       task.execute();
					    }
						
					}
				});
				sinchronyzeButton.setEnabled(false);
				sinchronyzeButton.setActionCommand("OK");
				buttonPane.add(sinchronyzeButton);
				getRootPane().setDefaultButton(sinchronyzeButton);
			}
			{
				JButton cancelButton = new JButton("Annulla");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						MALExportDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private ArrayList<Integer> getIndexOf(SortedListModel model)
	{
		Object[] modelArray = listModel.toArray();
		Object[] completedArray = model.toArray();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < modelArray.length; i++)
		{
			Object nameToCheck = modelArray[i];
			for (int j = 0; j < completedArray.length; j++)
			{
				Object name = completedArray[j];
				if (nameToCheck.equals(name))
					indices.add(i);
			}
		}
		return indices;
	}
}
