package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import java.awt.Window.Type;

public class ReleaseNotifierDialog extends JDialog {

	/**
	 * Create the dialog.
	 */
	public ReleaseNotifierDialog() {
		setTitle("Uscite del giorno");
		setType(Type.POPUP);
		setResizable(false);
		setBounds(100, 100, 629, 289);
		getContentPane().setLayout(new MigLayout("", "[346.00px][-19.00,grow][339px]", "[16.00][grow][332.00px][][][][]"));
		{
			JLabel lblOggiEStato = new JLabel("OGGI E' STATO RILASCIATO :");
			getContentPane().add(lblOggiEStato, "cell 0 0 3 1,alignx center,aligny center");
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, "cell 0 1 3 1,grow");
			panel.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JLabel lblOav = new JLabel("OAV");
				lblOav.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(lblOav);
			}
			{
				JLabel lblFilm = new JLabel("FILM");
				lblFilm.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(lblFilm);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			getContentPane().add(scrollPane, "cell 0 2,grow");
			{
				JList ovaReleasedList = new JList();
				scrollPane.setViewportView(ovaReleasedList);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			getContentPane().add(scrollPane, "cell 2 2,grow");
			{
				JList filmReleasedList = new JList();
				scrollPane.setViewportView(filmReleasedList);
			}
		}
		{
			JButton btnApriSelezionato = new JButton("Apri Selezionato");
			getContentPane().add(btnApriSelezionato, "cell 0 4 3 1,growx,aligny center");
		}
		{
			JButton btnOk = new JButton("OK");
			getContentPane().add(btnOk, "cell 0 5 3 1,growx,aligny center");
		}
	}

}
