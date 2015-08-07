package util.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;

import util.AnimeData;
import util.PatternFilter;
import util.SortedListModel;
import main.AnimeIndex;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class AddFansubDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JList fansubList;
	private DefaultListModel fansubModel;
	private JButton deleteButton;
	private JTextField linkAddField;
	private JTextField fansubAddField;
	private TreeMap<String,String> fansubMap = new TreeMap<String,String>();
	private JButton addButton;
	private String settedFansub;

	/**
	 * Create the dialog.
	 */
	public AddFansubDialog()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(AddFansubDialog.class.getResource("/image/Aegisub.png")));
		fansubMap.putAll(AnimeIndex.fansubMap);
		
		setResizable(false);
		setType(Type.POPUP);
		setTitle("Fansub");
		setModal(true);
		setBounds(100, 100, 289, 316);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(10, 10));
		{
			JPanel optionPanel = new JPanel();
			contentPanel.add(optionPanel, BorderLayout.WEST);
			optionPanel.setLayout(new FormLayout(new ColumnSpec[] {
					ColumnSpec.decode("73px"),},
				new RowSpec[] {
					RowSpec.decode("89px"),
					RowSpec.decode("117px"),}));
			{
				addButton = new JButton("Aggiungi");
				addButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String text = addButton.getText();
						if (text.equalsIgnoreCase("aggiungi"))
							{
							String newFansub = fansubAddField.getText();
							String newLink = linkAddField.getText();
							if ((newFansub != null && !newFansub.isEmpty()))
							{
							fansubModel.addElement(fansubAddField.getText());
							fansubMap.put(newFansub, newLink);
						    fansubAddField.setText("");
						    fansubAddField.setEnabled(true);
						    linkAddField.setText("");
						    linkAddField.setEnabled(true);
							}
							else
								JOptionPane.showMessageDialog(contentPanel, "Nome non inserito", "Errore!", JOptionPane.ERROR_MESSAGE);
							}
						
						else
						{
							String newFansub = fansubAddField.getText();
							String newLink = linkAddField.getText();
					
								fansubMap.remove(newFansub);
								fansubMap.put(newFansub, newLink);
								
							fansubAddField.setText("");
						    linkAddField.setText("");
						    fansubList.clearSelection();
						    deleteButton.setEnabled(false);
						    addButton.setText("Aggiungi");
						    fansubAddField.setEnabled(true);
						    linkAddField.setEnabled(true);
						}
					}
				});
				optionPanel.add(addButton, "1, 1");
			}
			{
				deleteButton = new JButton("Elimina");
				deleteButton.setEnabled(false);
				deleteButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (!fansubList.isSelectionEmpty())
						{
							Object element = fansubList.getSelectedValue();
							if ( element != null) {
							    fansubModel.removeElement(element);
							    fansubMap.remove(element);
							    deleteButton.setEnabled(false);
							    Object[] fansub = fansubModel.toArray();
							    AnimeIndex.setFansubList(fansub); 
							    fansubAddField.setText("");
							    fansubAddField.setEnabled(true);
							    linkAddField.setText("");
							    linkAddField.setEnabled(true);
							    addButton.setText("Aggiungi");
							}
							
						}						
					}
				});
				deleteButton.setVerticalAlignment(SwingConstants.TOP);
				optionPanel.add(deleteButton, "1, 2");
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
			scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
			{
				fansubModel = new DefaultListModel();
				String[] fansub = AnimeIndex.getFansubList();
				for (int i = 0; i < fansub.length; i++) {
					fansubModel.addElement(fansub[i]);
				}
				fansubList = new JList(fansubModel);
				fansubList.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						if (!fansubList.isSelectionEmpty() && !fansubList.getSelectedValue().equals("?????"))
						{
						linkAddField.setEnabled(true);
						deleteButton.setEnabled(true);
						addButton.setText("Salva");
						String fansub = (String) fansubList.getSelectedValue();
						String link = fansubMap.get(fansub);
						fansubAddField.setText(fansub);
						linkAddField.setText(link);
						fansubAddField.setEnabled(false);
						}
						if(!fansubList.isSelectionEmpty() && fansubList.getSelectedValue().equals("?????") && fansubList.getSelectedValue()!=null)
						{
							deleteButton.setEnabled(false);
							linkAddField.setEnabled(false);
							addButton.setText("Salva");
							fansubAddField.setEnabled(false);
						}
					}
				});
				fansubList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				scrollPane.setViewportView(fansubList);
			}
		}
		{
			JPanel fansubPanel = new JPanel();
			contentPanel.add(fansubPanel, BorderLayout.SOUTH);
			GridBagLayout gbl_fansubPanel = new GridBagLayout();
			gbl_fansubPanel.columnWidths = new int[]{42, 37, 0};
			gbl_fansubPanel.rowHeights = new int[]{20, 0, 0};
			gbl_fansubPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_fansubPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			fansubPanel.setLayout(gbl_fansubPanel);
			{
				JLabel lblNome = new JLabel("Nome : ");
				GridBagConstraints gbc_lblNome = new GridBagConstraints();
				gbc_lblNome.anchor = GridBagConstraints.WEST;
				gbc_lblNome.insets = new Insets(0, 0, 5, 5);
				gbc_lblNome.gridx = 0;
				gbc_lblNome.gridy = 0;
				fansubPanel.add(lblNome, gbc_lblNome);
			}
			{
				fansubAddField = new JTextField();
				fansubAddField.setPreferredSize(new Dimension(120, 23));
				fansubAddField.setMinimumSize(new Dimension(120, 23));
				fansubAddField.setColumns(20);
				((AbstractDocument)fansubAddField.getDocument()).setDocumentFilter( new PatternFilter("[\\p{IsAlphabetic}\\p{IsDigit}\\p{IsWhite_Space}]{0,17}"));
				GridBagConstraints gbc_textField_1 = new GridBagConstraints();
				gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField_1.insets = new Insets(0, 0, 5, 0);
				gbc_textField_1.gridx = 1;
				gbc_textField_1.gridy = 0;
				fansubPanel.add(fansubAddField, gbc_textField_1);
			}
			{
				JLabel lblLink = new JLabel("Link :");
				GridBagConstraints gbc_lblLink = new GridBagConstraints();
				gbc_lblLink.anchor = GridBagConstraints.WEST;
				gbc_lblLink.insets = new Insets(0, 0, 0, 5);
				gbc_lblLink.gridx = 0;
				gbc_lblLink.gridy = 1;
				fansubPanel.add(lblLink, gbc_lblLink);
			}
			{
				linkAddField = new JTextField();
				linkAddField.setPreferredSize(new Dimension(120, 23));
				linkAddField.setMinimumSize(new Dimension(120, 23));
				linkAddField.setColumns(25);
				GridBagConstraints gbc_linkField = new GridBagConstraints();
				gbc_linkField.fill = GridBagConstraints.HORIZONTAL;
				gbc_linkField.gridx = 1;
				gbc_linkField.gridy = 1;
				fansubPanel.add(linkAddField, gbc_linkField);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(50);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//aggiunta fansub se è presente un fansub su ok
						String text = addButton.getText();
						if (text.equalsIgnoreCase("aggiungi"))
							{
							String newFansub = fansubAddField.getText();
							String newLink = linkAddField.getText();
							if ((newFansub != null && !newFansub.isEmpty()))
							{
							fansubModel.addElement(fansubAddField.getText());
							fansubMap.put(newFansub, newLink);
						    fansubAddField.setText("");
						    fansubAddField.setEnabled(true);
						    linkAddField.setText("");
						    linkAddField.setEnabled(true);
							}
							}
						else
						{
							String newFansub = fansubAddField.getText();
							String newLink = linkAddField.getText();
					
								fansubMap.remove(newFansub);
								fansubMap.put(newFansub, newLink);
								
							fansubAddField.setText("");
						    linkAddField.setText("");
						    fansubList.clearSelection();
						    deleteButton.setEnabled(false);
						    addButton.setText("Aggiungi");
						    fansubAddField.setEnabled(true);
						    linkAddField.setEnabled(true);
						}
						
						//salva fansub
						if (!fansubModel.isEmpty())
						{
						Object[] fansub = fansubModel.toArray();
						String previousFansub = (String) AnimeIndex.animeInformation.fansubComboBox.getSelectedItem();
					    AnimeIndex.setFansubList(fansub);
					    setFansubMap(fansubMap);
					    AnimeIndex.animeInformation.fansubComboBox.setSelectedItem(previousFansub);
						String link = AnimeIndex.fansubMap.get((String)AnimeIndex.animeInformation.fansubComboBox.getSelectedItem());
						if (link != null && !link.isEmpty())
						{
							AnimeIndex.animeInformation.fansubButton.setEnabled(true);
						}

						else
							AnimeIndex.animeInformation.fansubButton.setEnabled(false);
						}
						
						JButton but = (JButton) e.getSource();
						JDialog dialog = (JDialog) but.getTopLevelAncestor();
						dialog.dispose();
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
						JButton but = (JButton) e.getSource();
						JDialog dialog = (JDialog) but.getTopLevelAncestor();
						dialog.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public static void setFansubMap(TreeMap<String, String> fansubMap) 
	{
		AnimeIndex.fansubMap.putAll(fansubMap);
	}
}
