package util.window;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.AnimeIndex;
import net.miginfocom.swing.MigLayout;
import util.MAMUtil;
import util.SortedListModel;
import util.task.ReleasedAnimeTask;

public class ReleaseNotifierDialog extends JDialog {
	
	public SortedListModel ovaReleased = new SortedListModel();
	public SortedListModel filmReleased = new SortedListModel();
	public JList ovaReleasedList;
	public JList filmReleasedList;
	private JButton btnApriSelezionato;

	/**
	 * Create the dialog.
	 */
	public ReleaseNotifierDialog() {
		super(AnimeIndex.frame,true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
								
				if(!ReleasedAnimeTask.enableOav)
					ovaReleasedList.setEnabled(false);
				else
					ovaReleasedList.setEnabled(true);
				
				if(!ReleasedAnimeTask.enableFilm)
					filmReleasedList.setEnabled(false);
				else
					filmReleasedList.setEnabled(true);
			}
			@Override
			public void windowClosing(WindowEvent e) {
				AnimeIndex.appProp.setProperty("Date_Release", MAMUtil.today());
			}
		});
		setModal(true);

		setIconImage(Toolkit.getDefaultToolkit().getImage(ReleaseNotifierDialog.class.getResource("/image/icon.png")));
		setTitle("Nuove Uscite");
		setType(Type.POPUP);
		setResizable(false);
		setBounds(100, 100, 629, 284);
		getContentPane().setLayout(new MigLayout("", "[346.00px][-19.00,grow][339px]", "[16.00][][106.00px,grow][][]"));
		{
			JLabel lblOggiEStato = new JLabel("E' STATO RILASCIATO :");
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
				
				ovaReleasedList.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						filmReleasedList.clearSelection();
						btnApriSelezionato.setEnabled(true);
					}
				});
				ovaReleasedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
				filmReleasedList.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						ovaReleasedList.clearSelection();
						btnApriSelezionato.setEnabled(true);
					}
				});
				filmReleasedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				filmReleasedList.setFont(AnimeIndex.segui.deriveFont(12f));
				scrollPane.setViewportView(filmReleasedList);
			}
		}
		{
			btnApriSelezionato = new JButton("Visualizza l'Anime Selezionato");
			btnApriSelezionato.setEnabled(false);
			btnApriSelezionato.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (ovaReleasedList.isSelectionEmpty() && !filmReleasedList.isSelectionEmpty())
					{
						String name = (String) filmReleasedList.getSelectedValue();
						AnimeIndex.animeTypeComboBox.setSelectedItem("Film");
						AnimeIndex.filmList.setSelectedValue(name, true);
					}
					else if (filmReleasedList.isSelectionEmpty() && !ovaReleasedList.isSelectionEmpty())
					{
						String name = (String) ovaReleasedList.getSelectedValue();
						AnimeIndex.animeTypeComboBox.setSelectedItem("OAV");
						AnimeIndex.ovaList.setSelectedValue(name, true);						
					}
					Date date = new Date();
					SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/YYYY"); // the day of the week abbreviated
					String day = simpleDateformat.format(date);
					AnimeIndex.appProp.setProperty("Date_Release", day);
					JButton butt = (JButton) e.getSource();
					JDialog dialog = (JDialog) butt.getTopLevelAncestor();
					dialog.dispose();
				}
			});
			getContentPane().add(btnApriSelezionato, "cell 0 3 3 1,growx,aligny center");
		}
		{
			JButton btnOk = new JButton("OK");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AnimeIndex.appProp.setProperty("Date_Release", MAMUtil.today());
					JButton butt = (JButton) e.getSource();
					JDialog dialog = (JDialog) butt.getTopLevelAncestor();
					dialog.dispose();
				}
			});
			getContentPane().add(btnOk, "cell 0 4 3 1,growx,aligny center");
		}
	}

}
