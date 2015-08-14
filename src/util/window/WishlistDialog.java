package util.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.MatteBorder;

import util.SearchBar;
import util.SortedListModel;

import javax.swing.JTextField;

import main.AnimeIndex;

import java.awt.GridLayout;
import java.awt.Component;

import javax.swing.Box;

import net.miginfocom.swing.MigLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class WishlistDialog extends JDialog
{

	public final JPanel contentPanel = new JPanel();
	public static SortedListModel wishListModel = new SortedListModel();
	private SearchBar searchBar;
	private JButton btnDeleteAnime;
	private JButton btnAggiungiAnime;
	private JButton btnClose;
	private Component verticalStrut;
	private Component verticalStrut_1;
	private JList wishList;

	/**
	 * Create the dialog..
	 */
	public WishlistDialog()
	{
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setType(Type.UTILITY);
		setUndecorated(true);
		setResizable(false);
		setBounds(100, 100, 180, 472);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				wishList = new JList(wishListModel);
				wishList.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						btnDeleteAnime.setEnabled(true);
						
					}
				});
				wishList.setFont(AnimeIndex.segui.deriveFont(12f));
				wishList.setBorder(UIManager.getBorder("CheckBox.border"));
				scrollPane.setViewportView(wishList);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			{
				verticalStrut_1 = Box.createVerticalStrut(20);
			}
			{
				searchBar = new SearchBar();
				searchBar.setFont(AnimeIndex.segui.deriveFont(11f));
				ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(AnimeIndex.class.getResource("/image/search.png")));
		        searchBar.setIcon(icon);
		        searchBar.setDisabledTextColor(Color.LIGHT_GRAY);
				searchBar.setBackground(Color.BLACK);
				searchBar.setForeground(Color.LIGHT_GRAY);
			}
			
			JComboBox comboBox = new JComboBox();
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"                   WISHLIST"}));
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addComponent(verticalStrut_1, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addComponent(searchBar, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(verticalStrut_1, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(searchBar, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
			);
			panel.setLayout(gl_panel);
		}
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		contentPanel.add(horizontalStrut, BorderLayout.SOUTH);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnDeleteAnime = new JButton("Elimina Anime");
				btnDeleteAnime.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String name = (String) wishList.getSelectedValue(); 
						wishListModel.removeElement(name);
					}
				});
				btnDeleteAnime.setEnabled(false);
			}
			{
				btnAggiungiAnime = new JButton("Aggiungi Anime");
				btnAggiungiAnime.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String name = JOptionPane.showInputDialog(AnimeIndex.wishlistDialog, "Nome Anime", "Aggiungi alla wishlist", JOptionPane.QUESTION_MESSAGE);
						if (name != null)
							wishListModel.addElement(name);
					}
				});
			}
			{
				verticalStrut = Box.createVerticalStrut(20);
			}
			{
				btnClose = new JButton(">>");
				btnClose.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new Timer(1, new ActionListener() {
				               public void actionPerformed(ActionEvent e) {
				            	   
				            	   AnimeIndex.wishlistDialog.setLocation(AnimeIndex.wishlistDialog.getLocationOnScreen().x + 1, AnimeIndex.mainFrame.getLocationOnScreen().y);
				            	   AnimeIndex.mainFrame.requestFocus();
				            	   if (AnimeIndex.wishlistDialog.getLocationOnScreen().x == AnimeIndex.mainFrame.getLocationOnScreen().x) {
				                     ((Timer) e.getSource()).stop();
				                     AnimeIndex.wishlistDialog.dispose();
				            }
				               }
				            }).start();
					}
				});
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnDeleteAnime, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnAggiungiAnime, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addComponent(verticalStrut, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addComponent(btnClose)
						.addComponent(btnDeleteAnime)
						.addComponent(btnAggiungiAnime)
						.addComponent(verticalStrut, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE))
			);
			buttonPane.setLayout(gl_buttonPane);
		}
	}
}
