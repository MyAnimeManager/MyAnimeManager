package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import main.AnimeIndex;
import util.AnimeData;
import util.ConnectionManager;
import util.FileManager;

import java.awt.Window.Type;
import java.util.HashMap;
import java.util.TreeMap;

public class AnimeImageSelection extends JDialog
{
	private JButton okButton;
	private JList list;

	/**
	 * Create the dialog.
	 */
	public AnimeImageSelection(DefaultListModel model)
	{
		super(AnimeIndex.frame,true);
		setTitle("Aggiungi Immagine");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setType(Type.POPUP);
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 390, 198);
		getContentPane().setLayout(new BorderLayout());
		{
			JScrollPane scrollPane = new JScrollPane();
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			{
				list = new JList();
				list.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						okButton.setEnabled(true);
					}
				});
				scrollPane.setViewportView(list);
				if (model.isEmpty())
				{
					model.addElement("Anime non trovato");
					list.setEnabled(false);
				}
				else
					list.setModel(model);
				
			}
		}
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(100);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setEnabled(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {	
						AddImageDialog.shouldAdd = true;
						JButton but = (JButton) e.getSource();
						JDialog dialog = (JDialog) but.getTopLevelAncestor();
						dialog.dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annulla");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AddImageDialog.shouldAdd = false;
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

	public String getSelectedAnime()
	{
		return (String) list.getSelectedValue();
	}
}
