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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class WishlistDialog extends JDialog
{

	public final JPanel contentPanel = new JPanel();
	public static SortedListModel wishListModel = new SortedListModel();
	private SearchBar searchBar;
	private JButton btnDeleteAnime;
	private JButton btnAggiungiAnime;
	private JButton btnClose;
	private Component verticalStrut_1;
	private JList wishList;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	private Component horizontalStrut_2;
	private Component horizontalStrut_3;
	private Component horizontalStrut_4;
	private Component verticalStrut;
	private Component horizontalStrut_6;
	private Component horizontalStrut_5;

	/**
	 * Create the dialog..
	 */
	public WishlistDialog()
	{
		super(AnimeIndex.frame, false);
		setUndecorated(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setType(Type.UTILITY);
		setResizable(false);
		setBounds(100, 100, 182, 472);
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
			
			JComboBox comboBox = new JComboBox();
			comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"                   WISHLIST"}));
			searchBar = new SearchBar();
			searchBar.setAlignmentX(Component.LEFT_ALIGNMENT);
			searchBar.setFont(AnimeIndex.segui.deriveFont(11f));
			ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(AnimeIndex.class.getResource("/image/search.png")));
			searchBar.setIcon(icon);
			searchBar.setDisabledTextColor(Color.LIGHT_GRAY);
			searchBar.setBackground(Color.BLACK);
			searchBar.setForeground(Color.LIGHT_GRAY);
			{
				horizontalStrut = Box.createHorizontalStrut(20);
			}
			{
				horizontalStrut_1 = Box.createHorizontalStrut(20);
			}
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(90)
						.addComponent(verticalStrut_1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(horizontalStrut, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(horizontalStrut_1, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)
						.addComponent(searchBar, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(verticalStrut_1, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(horizontalStrut, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(horizontalStrut_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addComponent(searchBar, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
			);
			panel.setLayout(gl_panel);
		}
		{
			horizontalStrut_6 = Box.createHorizontalStrut(7);
			contentPanel.add(horizontalStrut_6, BorderLayout.WEST);
		}
		{
			horizontalStrut_5 = Box.createHorizontalStrut(7);
			contentPanel.add(horizontalStrut_5, BorderLayout.EAST);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnDeleteAnime = new JButton("Elimina Anime");
				btnDeleteAnime.setMargin(new Insets(0, 14, 0, 14));
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
				btnAggiungiAnime.setMargin(new Insets(0, 14, 0, 14));
				btnAggiungiAnime.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String name = JOptionPane.showInputDialog(AnimeIndex.wishlistDialog, "Nome Anime", "Aggiungi alla wishlist", JOptionPane.QUESTION_MESSAGE);
						if (name != null)
						{
							wishListModel.addElement(name);
						}
					}
				});
			}
			{
				btnClose = new JButton(">>");
				btnClose.setMargin(new Insets(0, 14, 0, 14));
				btnClose.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new Timer(1, new ActionListener() {
				               public void actionPerformed(ActionEvent e) {
				            	   
				            	   AnimeIndex.wishlistDialog.setLocation(AnimeIndex.wishlistDialog.getLocationOnScreen().x + 2, AnimeIndex.mainFrame.getLocationOnScreen().y);
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
			{
				horizontalStrut_2 = Box.createHorizontalStrut(20);
			}
			{
				horizontalStrut_3 = Box.createHorizontalStrut(20);
			}
			{
				horizontalStrut_4 = Box.createHorizontalStrut(20);
			}
			{
				verticalStrut = Box.createVerticalStrut(20);
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addComponent(horizontalStrut_2, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addComponent(horizontalStrut_3, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDeleteAnime, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addComponent(horizontalStrut_4, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAggiungiAnime, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(90)
						.addComponent(verticalStrut, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING)
							.addComponent(horizontalStrut_2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING)
							.addComponent(horizontalStrut_3, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnDeleteAnime, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING)
							.addComponent(horizontalStrut_4, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnAggiungiAnime, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addComponent(verticalStrut, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE))
			);
			buttonPane.setLayout(gl_buttonPane);
		}
	}
}
