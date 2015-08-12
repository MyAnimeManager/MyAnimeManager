package util.window;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import main.AnimeIndex;
import net.miginfocom.swing.MigLayout;
import util.SortedListModel;
import util.task.ReleasedAnimeTask;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ReleaseNotifierDialog extends JDialog {
	
	public static SortedListModel ovaReleased = new SortedListModel();
	public static SortedListModel filmReleased = new SortedListModel();
	public static JList ovaReleasedList;
	public static JList filmReleasedList;

	/**
	 * Create the dialog.
	 */
	public ReleaseNotifierDialog() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				ReleasedAnimeTask task = new ReleasedAnimeTask();
				task.execute();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(ReleaseNotifierDialog.class.getResource("/image/icon.png")));
		setTitle("Uscite del giorno");
		setType(Type.POPUP);
		setResizable(false);
		setBounds(100, 100, 629, 284);
		getContentPane().setLayout(new MigLayout("", "[346.00px][-19.00,grow][339px]", "[16.00][][106.00px,grow][][]"));
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
			scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
			scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
			getContentPane().add(scrollPane, "cell 0 2,grow");
			{
				ovaReleasedList = new JList(ovaReleased);
				ovaReleasedList.setFont(AnimeIndex.segui.deriveFont(12f));
				scrollPane.setViewportView(ovaReleasedList);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
			scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
			getContentPane().add(scrollPane, "cell 2 2,grow");
			{
				filmReleasedList = new JList(filmReleased);
				filmReleasedList.setFont(AnimeIndex.segui.deriveFont(12f));
				scrollPane.setViewportView(filmReleasedList);
			}
		}
		{
			JButton btnApriSelezionato = new JButton("Apri Selezionato");
			getContentPane().add(btnApriSelezionato, "cell 0 3 3 1,growx,aligny center");
		}
		{
			JButton btnOk = new JButton("OK");
			getContentPane().add(btnOk, "cell 0 4 3 1,growx,aligny center");
		}
	}

}
