package util.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.AnimeIndex;
import util.SortedListModel;


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
						list.clearSelection();
					}
				});
				optionPanel.add(deselectAllButton);
			}
			{
				JButton completedButton = new JButton("Completi");
				completedButton.addActionListener(new ActionListener() {
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
				optionPanel.add(completedButton);
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
				list.setFont(AnimeIndex.segui.deriveFont(12f));
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
						
						
						JPanel panel = new JPanel(new BorderLayout(5, 5));
						
						panel.add(new JLabel("Inserire i dati del proprio account MyAnimeList"), BorderLayout.NORTH);
						
					    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
					    label.add(new JLabel("Username", SwingConstants.RIGHT));
					    label.add(new JLabel("Password", SwingConstants.RIGHT));
					    panel.add(label, BorderLayout.WEST);

					    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
					    JTextField usernameField = new JTextField();
					    controls.add(usernameField);
					    JPasswordField passwordField = new JPasswordField();
					    controls.add(passwordField);
					    panel.add(controls, BorderLayout.CENTER);

					    int result = JOptionPane.showConfirmDialog(AnimeIndex.frame, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					    if (result == JOptionPane.OK_OPTION)
					    {  
					       List animeToAdd = list.getSelectedValuesList();
					       String username = usernameField.getText();
					       String password = new String(passwordField.getPassword());
					       SynchronizingExportDialog dial = new SynchronizingExportDialog(username, password, animeToAdd);
					       dial.setLocationRelativeTo(MALExportDialog.this);
					       dial.setVisible(true);
					    }
						
					}
				});
				sinchronyzeButton.setEnabled(false);
				buttonPane.add(sinchronyzeButton);
			}
			{
				JButton cancelButton = new JButton("Annulla");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						MALExportDialog.this.dispose();
					}
				});
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
