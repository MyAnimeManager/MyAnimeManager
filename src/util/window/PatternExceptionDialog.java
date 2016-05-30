package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import javafx.util.Pair;
import main.AnimeIndex;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class PatternExceptionDialog extends JDialog
{
	
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	public DefaultTableModel patternModel = new DefaultTableModel(new String[]{"Anime","Pattern","Folder"}, 0);
	private JButton btnElimina;
	

	/**
	 * Create the dialog.
	 */
	public PatternExceptionDialog()
	{
		super(AnimeIndex.preferenceDialog, true);
		setTitle("Eccezzioni");
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				for (Map.Entry<String, Pair<String,String>> entry : AnimeIndex.patternAnimeMap.entrySet())
				{
					Pair<String,String> pair = entry.getValue();
					patternModel.addRow(new String[]{entry.getKey(), pair.getKey(), pair.getValue()});
				}
			}
		});
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setHgap(100);
		contentPanel.add(panel, BorderLayout.NORTH);

		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PatternAddingDialog dialog = new PatternAddingDialog();
				dialog.setLocationRelativeTo(PatternExceptionDialog.this);
				dialog.setVisible(true);
			}
		});
		panel.add(btnAggiungi);

		btnElimina = new JButton("Elimina");
		btnElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				patternModel.removeRow(row);

			}
		});
		panel.add(btnElimina);


		JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		table = new JTable(){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return column == 1 || column == 2;
		    };
		};
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		table.setModel(patternModel);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);

		table.setAutoCreateRowSorter(true);
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();		 
		int columnIndexToSort = 0;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.setComparator(0, String.CASE_INSENSITIVE_ORDER);
		sorter.sort();
		sorter.setSortable(1, false);
		sorter.setSortable(2, false);
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if (!e.getValueIsAdjusting())
				{
					btnElimina.setEnabled(true);
				}
				
			}
			
		});

		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(100);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton saveButton = new JButton("Salva");
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(table.getCellEditor()!=null)
							 table.getCellEditor().stopCellEditing();
						 Vector<Vector> tableVector = patternModel.getDataVector();
						 AnimeIndex.patternAnimeMap.clear();
						 for (Vector<String> rowVector : tableVector)
						 {
							 String animeName = rowVector.elementAt(0);
							 String pattern = rowVector.elementAt(1);
							 String folder = rowVector.elementAt(2);
							 if (!(pattern.equalsIgnoreCase(AnimeIndex.appProp.getProperty("General_Pattern")) || folder.equalsIgnoreCase(AnimeIndex.appProp.getProperty("Main_Folder"))) || (!pattern.equalsIgnoreCase(AnimeIndex.appProp.getProperty("General_Pattern")) && !folder.equalsIgnoreCase(AnimeIndex.appProp.getProperty("Main_Folder"))))
								 {
								 	Pair<String,String> pair = new Pair<String,String>(pattern,folder);
								 	AnimeIndex.patternAnimeMap.put(animeName, pair);
								 }
						 }
						 PatternExceptionDialog.this.dispose();
					}
				});
				saveButton.setActionCommand("OK");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PatternExceptionDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
}
