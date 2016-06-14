package util.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
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
	private ArrayList<Integer> wishlistIndeces;
	private ArrayList<Integer> droplistIndeces;
	private JToggleButton completedButton;
	private JToggleButton airingButton;
	private JToggleButton ovaButton;
	private JToggleButton filmButton;
	private JToggleButton completedToSeeButton;
	private JToggleButton wishlistButton;
	private JToggleButton droplistButton;
	
	
	public MALExportDialog()
	{
		super(AnimeIndex.frame, true);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listModel.addAll(AnimeIndex.completedMap.keySet());
				listModel.addAll(AnimeIndex.airingMap.keySet());
				listModel.addAll(AnimeIndex.ovaMap.keySet());
				listModel.addAll(AnimeIndex.filmMap.keySet());
				listModel.addAll(AnimeIndex.completedToSeeMap.keySet());
				listModel.addAll(AnimeIndex.wishlistMap.keySet());
				listModel.addAll(AnimeIndex.wishlistMALMap.keySet());
				listModel.addAll(AnimeIndex.droppedMap.keySet());
				listModel.addAll(AnimeIndex.droppedMALMap.keySet());
				
				completedIndeces = getIndexOf(AnimeIndex.completedModel);
				airingIndeces = getIndexOf(AnimeIndex.airingModel);
				ovaIndeces = getIndexOf(AnimeIndex.ovaModel);
				filmIndeces = getIndexOf(AnimeIndex.filmModel);
				completedToSeeIndeces = getIndexOf(AnimeIndex.completedToSeeModel);	
				wishlistIndeces = getIndexOf(AnimeIndex.wishlistDialog.wishListModel);
				droplistIndeces = getIndexOf(AnimeIndex.wishlistDialog.wishListModel);
				
				setTitle("Selezione Anime da Esportare : 0/"+listModel.getSize());
			}
		});
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 464, 325);
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
						if (!completedButton.isSelected())
						{
							completedButton.setSelected(true);
							completedButton.setBackground(Color.GRAY);
						}
						if (!airingButton.isSelected())
						{
							airingButton.setSelected(true);
							airingButton.setBackground(Color.GRAY);
						}
						if (!ovaButton.isSelected())
						{
							ovaButton.setSelected(true);
							ovaButton.setBackground(Color.GRAY);
						}
						if (!filmButton.isSelected())
						{
							filmButton.setSelected(true);
							filmButton.setBackground(Color.GRAY);
						}
						if (!completedToSeeButton.isSelected())
						{
							completedToSeeButton.setSelected(true);
							completedToSeeButton.setBackground(Color.GRAY);
						}
						if (!wishlistButton.isSelected())
						{
							wishlistButton.setSelected(true);
							wishlistButton.setBackground(Color.GRAY);
						}
						if (!droplistButton.isSelected())
						{
							droplistButton.setSelected(true);
							droplistButton.setBackground(Color.GRAY);
						}
						
						int size = listModel.getSize();
						list.setSelectionInterval(0, size - 1);
					}
				});
				optionPanel.add(selectAllButton);
			}
			{
				JButton deselectAllButton = new JButton("Deseleziona Tutti");
				deselectAllButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (completedButton.isSelected())
						{
							completedButton.setSelected(false);
							completedButton.setBackground(UIManager.getColor(completedButton));
						}
						if (airingButton.isSelected())
						{
							airingButton.setSelected(false);
							airingButton.setBackground(UIManager.getColor(airingButton));
						}
						if (ovaButton.isSelected())
						{
							ovaButton.setSelected(false);
							ovaButton.setBackground(UIManager.getColor(ovaButton));
						}
						if (filmButton.isSelected())
						{
							filmButton.setSelected(false);
							filmButton.setBackground(UIManager.getColor(filmButton));
						}
						if (completedToSeeButton.isSelected())
						{
							completedToSeeButton.setSelected(false);
							completedToSeeButton.setBackground(UIManager.getColor(completedToSeeButton));
						}
						if (wishlistButton.isSelected())
						{
							wishlistButton.setSelected(false);
							wishlistButton.setBackground(UIManager.getColor(wishlistButton));
						}
						if (droplistButton.isSelected())
						{
							droplistButton.setSelected(false);
							droplistButton.setBackground(UIManager.getColor(droplistButton));
						}
						
						list.clearSelection();
					}
				});
				optionPanel.add(deselectAllButton);
			}
			{
				airingButton = new JToggleButton("In Corso");
				airingButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (airingButton.isSelected())
						{
							airingButton.setBackground(Color.GRAY);
							int size = airingIndeces.size();
							for (int i = 0; i < size; i++)
							{
								list.addSelectionInterval(airingIndeces.get(i), airingIndeces.get(i));
							}						
						}
						else
						{
							airingButton.setBackground(UIManager.getColor(completedButton));
							int size = airingIndeces.size();
							for (int i = 0; i < size; i++)
							{
								list.removeSelectionInterval(airingIndeces.get(i), airingIndeces.get(i));
							}	
						}
					}
				});
				{
					wishlistButton = new JToggleButton("Wishlist");
					wishlistButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (wishlistButton.isSelected())
							{
								wishlistButton.setBackground(Color.GRAY);
								int size = wishlistIndeces.size();
								for (int i = 0; i < size; i++)
								{
									list.addSelectionInterval(wishlistIndeces.get(i), wishlistIndeces.get(i));
								}						
							}
							else
							{
								wishlistButton.setBackground(UIManager.getColor(completedButton));
								int size = wishlistIndeces.size();
								for (int i = 0; i < size; i++)
								{
									list.removeSelectionInterval(wishlistIndeces.get(i), wishlistIndeces.get(i));
								}	
							}
						}
					});
					optionPanel.add(wishlistButton);
				}
				{
					droplistButton = new JToggleButton("Droplist");
					droplistButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (droplistButton.isSelected())
							{
								droplistButton.setBackground(Color.GRAY);
								int size = droplistIndeces.size();
								for (int i = 0; i < size; i++)
								{
									list.addSelectionInterval(droplistIndeces.get(i), droplistIndeces.get(i));
								}						
							}
							else
							{
								droplistButton.setBackground(UIManager.getColor(completedButton));
								int size = droplistIndeces.size();
								for (int i = 0; i < size; i++)
								{
									list.removeSelectionInterval(droplistIndeces.get(i), droplistIndeces.get(i));
								}	
							}
						}
					});
					optionPanel.add(droplistButton);
				}
				{
					completedButton = new JToggleButton("Completati");
					completedButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							if (completedButton.isSelected())
							{
								completedButton.setBackground(Color.GRAY);
								int size = completedIndeces.size();
								for (int i = 0; i < size; i++)
								{
									list.addSelectionInterval(completedIndeces.get(i), completedIndeces.get(i));
								}						
							}
							else
							{
								completedButton.setBackground(UIManager.getColor(completedButton));
								int size = completedIndeces.size();
								for (int i = 0; i < size; i++)
								{
									list.removeSelectionInterval(completedIndeces.get(i), completedIndeces.get(i));
								}	
							}
						}
					});
					optionPanel.add(completedButton);
				}
				optionPanel.add(airingButton);
			}
			{
				ovaButton = new JToggleButton("OAV");
				ovaButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (ovaButton.isSelected())
						{
							ovaButton.setBackground(Color.GRAY);
							int size = ovaIndeces.size();
							for (int i = 0; i < size; i++)
							{
								list.addSelectionInterval(ovaIndeces.get(i), ovaIndeces.get(i));
							}						
						}
						else
						{
							ovaButton.setBackground(UIManager.getColor(completedButton));
							int size = ovaIndeces.size();
							for (int i = 0; i < size; i++)
							{
								list.removeSelectionInterval(ovaIndeces.get(i), ovaIndeces.get(i));
							}	
						}
					}
				});
				optionPanel.add(ovaButton);
			}
			{
				filmButton = new JToggleButton("Film");
				filmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (filmButton.isSelected())
						{
							filmButton.setBackground(Color.GRAY);
							int size = filmIndeces.size();
							for (int i = 0; i < size; i++)
							{
								list.addSelectionInterval(filmIndeces.get(i), filmIndeces.get(i));
							}						
						}
						else
						{
							filmButton.setBackground(UIManager.getColor(completedButton));
							int size = filmIndeces.size();
							for (int i = 0; i < size; i++)
							{
								list.removeSelectionInterval(filmIndeces.get(i), filmIndeces.get(i));
							}	
						}
					}
				});
				optionPanel.add(filmButton);
			}
			{
				completedToSeeButton = new JToggleButton("Completi da Vedere");
				completedToSeeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (completedToSeeButton.isSelected())
						{
							completedToSeeButton.setBackground(Color.GRAY);
							int size = completedToSeeIndeces.size();
							for (int i = 0; i < size; i++)
							{
								list.addSelectionInterval(completedToSeeIndeces.get(i), completedToSeeIndeces.get(i));
							}						
						}
						else
						{
							completedToSeeButton.setBackground(UIManager.getColor(completedButton));
							int size = completedToSeeIndeces.size();
							for (int i = 0; i < size; i++)
							{
								list.removeSelectionInterval(completedToSeeIndeces.get(i), completedToSeeIndeces.get(i));
							}	
						}
					}
				});
				optionPanel.add(completedToSeeButton);
			}
		}
		{
			JScrollPane listPane = new JScrollPane();
			listPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
			listPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
			contentPanel.add(listPane, BorderLayout.CENTER);
			{
				list = new JList(listModel);
				list.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (!list.isSelectionEmpty())
							sinchronyzeButton.setEnabled(true);
						else
							sinchronyzeButton.setEnabled(false);
						setTitle("Selezione Anime da Esportare : "+list.getSelectedValuesList().size()+"/"+listModel.getSize());
					}
				});
				list.setFont(AnimeIndex.segui.deriveFont(12f));
				listPane.setViewportView(list);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				sinchronyzeButton = new JButton("Sincronizza");
				sinchronyzeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
						JPanel panel = new JPanel(new BorderLayout(5, 5));
						panel.add(new JLabel("Inserire i dati del proprio account MyAnimeList"), BorderLayout.NORTH);
					    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
					    label.add(new JLabel("Username :", SwingConstants.RIGHT));
					    label.add(new JLabel("Password :", SwingConstants.RIGHT));
					    panel.add(label, BorderLayout.WEST);
					    
					    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
					    JTextField usernameField = new JTextField();
					    controls.add(usernameField);
					    JPasswordField passwordField = new JPasswordField();
					    controls.add(passwordField);
					    panel.add(controls, BorderLayout.CENTER);
					    
					    JPanel rememberCheckBox = new JPanel();
					    JCheckBox rememberUsername = new JCheckBox("Ricorda Username");
					    if (!AnimeIndex.appProp.getProperty("Username").isEmpty())
					    {
					    	rememberUsername.setSelected(true);
					    	usernameField.setText(AnimeIndex.appProp.getProperty("Username"));
					    }
					    JCheckBox rememberPassword = new JCheckBox("Ricorda Password");
					    if (!AnimeIndex.appProp.getProperty("Password").isEmpty())
					    {
					    	rememberPassword.setSelected(true);		
					    	passwordField.setText(new String(Base64.getDecoder().decode(AnimeIndex.appProp.getProperty("Password"))));
					    }
					    rememberCheckBox.add(rememberUsername);
					    rememberCheckBox.add(rememberPassword);
					    panel.add(rememberCheckBox, BorderLayout.SOUTH);

					    int result = JOptionPane.showConfirmDialog(AnimeIndex.frame, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					    if (result == JOptionPane.OK_OPTION)
					    {  
					       List animeToAdd = list.getSelectedValuesList();
					       String username = usernameField.getText();
					       String password = new String(passwordField.getPassword());
					       AnimeIndex.appProp.setProperty("Username", username);
					       AnimeIndex.appProp.setProperty("Password", new String(Base64.getEncoder().encode(password.getBytes())));
					       SynchronizingExportDialog dial = new SynchronizingExportDialog(username, password, animeToAdd);
					       dial.setLocationRelativeTo(MALExportDialog.this);
					       dial.setVisible(true);
					    }
					}
				});
				buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
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
