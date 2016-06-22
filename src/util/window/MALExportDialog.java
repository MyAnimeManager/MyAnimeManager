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

import org.apache.commons.lang3.ArrayUtils;

import main.AnimeIndex;
import util.SortedListModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;


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
	
	private boolean buttonControl = false;
	private Component verticalStrut;
	private JSeparator separator;
	
	
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
				wishlistIndeces = getIndexOf(WishlistDialog.wishListModel);
				droplistIndeces = getIndexOf(WishlistDialog.dropListModel);
				
				setTitle("Selezione Anime da Esportare : 0/"+listModel.getSize());
			}
		});
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 447, 325);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel optionPanel = new JPanel();
			optionPanel.setPreferredSize(new Dimension(10, 60));
			contentPanel.add(optionPanel, BorderLayout.NORTH);
			{
				GridBagLayout gbl_optionPanel = new GridBagLayout();
				gbl_optionPanel.columnWidths = new int[]{115, 0, 72, 69, 66, 66, 0};
				gbl_optionPanel.rowHeights = new int[]{0, 23, 23, 0};
				gbl_optionPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				gbl_optionPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
				optionPanel.setLayout(gbl_optionPanel);
				{
					ovaButton = new JToggleButton("OAV");
					ovaButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonControl = true;
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
							buttonControl = false;
						}
					});
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
							buttonControl = true;
							list.setSelectionInterval(0, size - 1);
							buttonControl = false;
						}
					});
					{
						verticalStrut = Box.createVerticalStrut(0);
						GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
						gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
						gbc_verticalStrut.gridx = 0;
						gbc_verticalStrut.gridy = 0;
						optionPanel.add(verticalStrut, gbc_verticalStrut);
					}
					GridBagConstraints gbc_selectAllButton = new GridBagConstraints();
					gbc_selectAllButton.fill = GridBagConstraints.HORIZONTAL;
					gbc_selectAllButton.insets = new Insets(0, 0, 5, 5);
					gbc_selectAllButton.gridx = 0;
					gbc_selectAllButton.gridy = 1;
					optionPanel.add(selectAllButton, gbc_selectAllButton);
					{
						completedButton = new JToggleButton("Completati");
						completedButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								buttonControl = true;
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
								buttonControl = false;
							}
						});
						{
							separator = new JSeparator();
							separator.setMinimumSize(new Dimension(2, 0));
							separator.setOrientation(SwingConstants.VERTICAL);
							GridBagConstraints gbc_separator = new GridBagConstraints();
							gbc_separator.fill = GridBagConstraints.BOTH;
							gbc_separator.gridheight = 2;
							gbc_separator.insets = new Insets(0, 0, 5, 5);
							gbc_separator.gridx = 1;
							gbc_separator.gridy = 1;
							optionPanel.add(separator, gbc_separator);
						}
						GridBagConstraints gbc_completedButton = new GridBagConstraints();
						gbc_completedButton.fill = GridBagConstraints.HORIZONTAL;
						gbc_completedButton.insets = new Insets(0, 0, 5, 5);
						gbc_completedButton.gridx = 2;
						gbc_completedButton.gridy = 1;
						optionPanel.add(completedButton, gbc_completedButton);
					}
					airingButton = new JToggleButton("In Corso");
					airingButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonControl = true;
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
							buttonControl = false;
						}
					});
					GridBagConstraints gbc_airingButton = new GridBagConstraints();
					gbc_airingButton.fill = GridBagConstraints.HORIZONTAL;
					gbc_airingButton.insets = new Insets(0, 0, 5, 5);
					gbc_airingButton.gridx = 3;
					gbc_airingButton.gridy = 1;
					optionPanel.add(airingButton, gbc_airingButton);
					GridBagConstraints gbc_ovaButton = new GridBagConstraints();
					gbc_ovaButton.fill = GridBagConstraints.HORIZONTAL;
					gbc_ovaButton.insets = new Insets(0, 0, 5, 5);
					gbc_ovaButton.gridx = 4;
					gbc_ovaButton.gridy = 1;
					optionPanel.add(ovaButton, gbc_ovaButton);
				}
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
						buttonControl = true;
						list.clearSelection();
						buttonControl = false;
					}
				});
				{
					filmButton = new JToggleButton("Film");
					filmButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonControl = true;
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
							buttonControl = false;
						}
					});
					GridBagConstraints gbc_filmButton = new GridBagConstraints();
					gbc_filmButton.fill = GridBagConstraints.HORIZONTAL;
					gbc_filmButton.insets = new Insets(0, 0, 5, 0);
					gbc_filmButton.gridx = 5;
					gbc_filmButton.gridy = 1;
					optionPanel.add(filmButton, gbc_filmButton);
				}
				GridBagConstraints gbc_deselectAllButton = new GridBagConstraints();
				gbc_deselectAllButton.fill = GridBagConstraints.HORIZONTAL;
				gbc_deselectAllButton.insets = new Insets(0, 0, 0, 5);
				gbc_deselectAllButton.gridx = 0;
				gbc_deselectAllButton.gridy = 2;
				optionPanel.add(deselectAllButton, gbc_deselectAllButton);
				{
					completedToSeeButton = new JToggleButton("Completi da Vedere");
					completedToSeeButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonControl = true;
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
							buttonControl = false;
						}
					});
					wishlistButton = new JToggleButton("Wishlist");
					wishlistButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonControl = true;
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
							buttonControl = false;
						}
						
					});
					{
						droplistButton = new JToggleButton("Droplist");
						droplistButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								buttonControl = true;
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
								buttonControl = false;
							}
						});
						GridBagConstraints gbc_droplistButton = new GridBagConstraints();
						gbc_droplistButton.fill = GridBagConstraints.HORIZONTAL;
						gbc_droplistButton.insets = new Insets(0, 0, 0, 5);
						gbc_droplistButton.gridx = 2;
						gbc_droplistButton.gridy = 2;
						optionPanel.add(droplistButton, gbc_droplistButton);
					}
					GridBagConstraints gbc_wishlistButton = new GridBagConstraints();
					gbc_wishlistButton.fill = GridBagConstraints.HORIZONTAL;
					gbc_wishlistButton.insets = new Insets(0, 0, 0, 5);
					gbc_wishlistButton.gridx = 3;
					gbc_wishlistButton.gridy = 2;
					optionPanel.add(wishlistButton, gbc_wishlistButton);
					GridBagConstraints gbc_completedToSeeButton = new GridBagConstraints();
					gbc_completedToSeeButton.fill = GridBagConstraints.HORIZONTAL;
					gbc_completedToSeeButton.gridwidth = 2;
					gbc_completedToSeeButton.gridx = 4;
					gbc_completedToSeeButton.gridy = 2;
					optionPanel.add(completedToSeeButton, gbc_completedToSeeButton);
				}
			}
			{
				{
					{
					}
				}
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
						
						int[] indeces = list.getSelectedIndices();
						
						for (int index : completedIndeces)
						{

							if (!buttonControl && !ArrayUtils.contains(indeces, index))
							{
								completedButton.setSelected(false);
								completedButton.setBackground(UIManager.getColor(completedButton));
								break;
							}
						}
						
						for (int index : airingIndeces)
						{
							if (!buttonControl && !ArrayUtils.contains(indeces, index))
							{
								airingButton.setSelected(false);
								airingButton.setBackground(UIManager.getColor(completedButton));
								break;
							}
						}
						
						for (int index : ovaIndeces)
						{
							if (!buttonControl && !ArrayUtils.contains(indeces, index))
							{
								ovaButton.setSelected(false);
								ovaButton.setBackground(UIManager.getColor(completedButton));
								break;
							}
						}
						
						for (int index : filmIndeces)
						{
							if (!buttonControl && !ArrayUtils.contains(indeces, index))
							{
								filmButton.setSelected(false);
								filmButton.setBackground(UIManager.getColor(completedButton));
								break;
							}
						}
						
						for (int index : completedToSeeIndeces)
						{
							if (!buttonControl && !ArrayUtils.contains(indeces, index))
							{
								completedToSeeButton.setSelected(false);
								completedToSeeButton.setBackground(UIManager.getColor(completedButton));
								break;
							}
						}
						
						for (int index : wishlistIndeces)
						{
							if (!buttonControl && !ArrayUtils.contains(indeces, index))
							{
								wishlistButton.setSelected(false);
								wishlistButton.setBackground(UIManager.getColor(completedButton));
								break;
							}
						}
						
						for (int index : droplistIndeces)
						{
							if (!buttonControl && !ArrayUtils.contains(indeces, index))
							{
								droplistButton.setSelected(false);
								droplistButton.setBackground(UIManager.getColor(completedButton));
								break;
							}
						}
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
				GridBagLayout gbl_buttonPane = new GridBagLayout();
				gbl_buttonPane.columnWidths = new int[]{220, 220, 0};
				gbl_buttonPane.rowHeights = new int[]{23, 0};
				gbl_buttonPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
				gbl_buttonPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
				buttonPane.setLayout(gbl_buttonPane);
			}
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
				sinchronyzeButton.setEnabled(false);
				GridBagConstraints gbc_sinchronyzeButton = new GridBagConstraints();
				gbc_sinchronyzeButton.fill = GridBagConstraints.BOTH;
				gbc_sinchronyzeButton.insets = new Insets(0, 0, 0, 5);
				gbc_sinchronyzeButton.gridx = 0;
				gbc_sinchronyzeButton.gridy = 0;
				buttonPane.add(sinchronyzeButton, gbc_sinchronyzeButton);
			}
			JButton cancelButton = new JButton("Annulla");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MALExportDialog.this.dispose();
				}
			});
			GridBagConstraints gbc_cancelButton = new GridBagConstraints();
			gbc_cancelButton.fill = GridBagConstraints.BOTH;
			gbc_cancelButton.gridx = 1;
			gbc_cancelButton.gridy = 0;
			buttonPane.add(cancelButton, gbc_cancelButton);
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
