package util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

import org.jdesktop.swingx.JXCollapsiblePane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SingleSuggestionPanel extends JPanel {
	private JXCollapsiblePane collapsiblePane;
	
	/**
	 * Create the panel.
	 */
	public SingleSuggestionPanel(String name)
	{
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{55, 89, 0};
		gbl_panel.rowHeights = new int[]{23, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel(name);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		JToggleButton openCloseButton = new JToggleButton();
		openCloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (collapsiblePane.isCollapsed())
				{
					collapsiblePane.setCollapsed(false);
					openCloseButton.setIcon(new ImageIcon(this.getClass().getResource("/image/ArrowDown.png")));
				}
				else
				{
					collapsiblePane.setCollapsed(true);
					openCloseButton.setIcon(new ImageIcon(this.getClass().getResource("/image/ArrowUp.png")));
				}
			}
		});
		openCloseButton.setIcon(new ImageIcon(this.getClass().getResource("/image/ArrowDown.png")));
		GridBagConstraints gbc_openCloseButton = new GridBagConstraints();
		gbc_openCloseButton.fill = GridBagConstraints.VERTICAL;
		gbc_openCloseButton.gridx = 1;
		gbc_openCloseButton.gridy = 0;
		panel.add(openCloseButton, gbc_openCloseButton);
		
		collapsiblePane = new JXCollapsiblePane();
		add(collapsiblePane, BorderLayout.CENTER);
		collapsiblePane.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		collapsiblePane.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JTextArea txtrProvaDiUn = new JTextArea();
		txtrProvaDiUn.setText("Prova di un testo molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto molto lungo ");
		txtrProvaDiUn.setEditable(false);
		txtrProvaDiUn.setWrapStyleWord(true);
		txtrProvaDiUn.setLineWrap(true);
		scrollPane.setViewportView(txtrProvaDiUn);
	
	}
	
}
