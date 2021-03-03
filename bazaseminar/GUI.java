package bazaseminar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;

public class GUI {

	public JFrame frmEvidencija;
	public String frmQuery;
	public JTable frmTable;
	public JScrollPane frmScrollpane;
	public DBController dbc = new DBController();
	public String tableName = "Hardver";
	public Map<String, String[]> atributi = new HashMap<>();

	/**
	 * Launch the application.
	 */
	public void load(Object[][] data, String[] col) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI(data, col);
					window.frmEvidencija.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI(Object[][] data, String[] col) {
		initialize(data, col);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Object[][] data, String[] col) {
		String[] atrHardver = { "Hardver_ID", "Hardver_Naziv", "Hardver_Proizvodjac", "Tip_ID", "Skladiste_ID" };
		String[] atrSoftver = { "Softver_ID", "Softver_Naziv" };
		String[] atrProizvodjac = { "Proizvodjac_ID", "Proizvodjac_Naziv" };
		String[] atrTip = { "Tip_ID", "Tip_Naziv" };
		String[] atrSkladiste = { "Skladiste_ID", "Skladiste_Naziv", "Skladiste_Adresa", "Skladiste_Kapacitet" };

		atributi.put("Hardver", atrHardver);
		atributi.put("Softver", atrSoftver);
		atributi.put("Tip", atrTip);
		atributi.put("Proizvodjac", atrProizvodjac);
		atributi.put("Skladiste", atrSkladiste);

		frmEvidencija = new JFrame();
		frmEvidencija.setBackground(Color.WHITE);
		frmEvidencija.getContentPane().setBackground(SystemColor.inactiveCaption);
		frmEvidencija.setTitle("eViDeNciJA");
		frmEvidencija.setBounds(100, 100, 546, 395);
		frmEvidencija.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEvidencija.getContentPane().setLayout(null);

		JButton dodajBtn = new JButton("Dodaj");
		dodajBtn.setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.PINK));
		dodajBtn.setBackground(SystemColor.control);
		dodajBtn.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
		dodajBtn.setBounds(10, 285, 89, 31);
		frmEvidencija.getContentPane().add(dodajBtn);

		JButton dodajViseBtn = new JButton("Dodaj vi\u0161e");
		dodajViseBtn.setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.PINK));
		dodajViseBtn.setBackground(SystemColor.control);
		dodajViseBtn.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
		dodajViseBtn.setBounds(109, 285, 114, 31);
		frmEvidencija.getContentPane().add(dodajViseBtn);

		JButton filtrirajBtn = new JButton("Filtriraj");
		filtrirajBtn.setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.PINK));
		filtrirajBtn.setBackground(SystemColor.control);
		filtrirajBtn.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
		filtrirajBtn.setBounds(233, 285, 89, 31);
		frmEvidencija.getContentPane().add(filtrirajBtn);

		JButton sortirajBtn = new JButton("Sortiraj");
		sortirajBtn.setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.PINK));
		sortirajBtn.setBackground(SystemColor.control);
		sortirajBtn.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
		sortirajBtn.setBounds(431, 285, 89, 31);
		frmEvidencija.getContentPane().add(sortirajBtn);

		JButton ukloniBtn = new JButton("Ukloni");
		ukloniBtn.setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.PINK));
		ukloniBtn.setBackground(SystemColor.control);
		ukloniBtn.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 12));
		ukloniBtn.setBounds(332, 285, 89, 31);
		frmEvidencija.getContentPane().add(ukloniBtn);

		frmEvidencija.getContentPane().setLayout(new BorderLayout());

		this.frmTable = new JTable(data, col);
		frmTable.setColumnSelectionAllowed(true);
		frmTable.setRowSelectionAllowed(true);
		this.frmScrollpane = new JScrollPane(this.frmTable);
		frmEvidencija.getContentPane().add(this.frmScrollpane);

		JMenuBar menuBar = new JMenuBar();
		JMenu pregledMenu = new JMenu("Pregled/Osvjezi");
		JMenu izmijeniPodatke = new JMenu("Izmijeni podatke");

		JMenuItem hrdMenu = new JMenuItem("Hardver");
		JMenuItem sftMenu = new JMenuItem("Softver");
		JMenuItem sklMenu = new JMenuItem("Skladista");
		JMenuItem tipMenu = new JMenuItem("Tipovi hardvera");
		JMenuItem przMenu = new JMenuItem("Proizvodjaci");
		JMenuItem potvrdi = new JMenuItem("Potvrdi");
		menuBar.add(pregledMenu);
		pregledMenu.add(hrdMenu);
		pregledMenu.add(sftMenu);
		pregledMenu.add(sklMenu);
		pregledMenu.add(tipMenu);
		pregledMenu.add(przMenu);
		menuBar.add(izmijeniPodatke);
		izmijeniPodatke.add(potvrdi);
		frmEvidencija.setJMenuBar(menuBar);

		class showSoftver implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dodajViseBtn.setEnabled(false);
				filtrirajBtn.setEnabled(false);
				tableName = "Softver";
				String sftQuery = "select * from Softver";
				Object[][] data2 = dbc.populate(sftQuery, 2);
				String[] col2 = { "ID", "Naziv" };
				frmEvidencija.remove(frmScrollpane);
				frmTable = new JTable(data2, col2);
				frmTable.setColumnSelectionAllowed(true);
				frmTable.setRowSelectionAllowed(true);
				frmScrollpane = new JScrollPane(frmTable);
				frmEvidencija.add(frmScrollpane);
				SwingUtilities.updateComponentTreeUI(frmEvidencija);

			}
		}

		class showHardver implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dodajViseBtn.setEnabled(true);
				filtrirajBtn.setEnabled(true);
				tableName = "Hardver";
				String hrdQuery = "select * from Hardver";
				Object[][] data3 = dbc.populate(hrdQuery, 5);
				String[] col3 = { "ID", "Naziv", "Proizvodjac", "Tip_ID", "Skladiste_ID" };
				frmEvidencija.remove(frmScrollpane);
				frmTable = new JTable(data3, col3);
				frmTable.setColumnSelectionAllowed(true);
				frmTable.setRowSelectionAllowed(true);
				frmScrollpane = new JScrollPane(frmTable);
				frmEvidencija.add(frmScrollpane);
				SwingUtilities.updateComponentTreeUI(frmEvidencija);

			}
		}

		class showTip implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dodajViseBtn.setEnabled(false);
				filtrirajBtn.setEnabled(false);
				tableName = "Tip";
				String tipQuery = "select * from Tip";
				Object[][] data3 = dbc.populate(tipQuery, 2);
				String[] col3 = { "ID", "Naziv" };
				frmEvidencija.remove(frmScrollpane);
				frmTable = new JTable(data3, col3);
				frmTable.setColumnSelectionAllowed(true);
				frmTable.setRowSelectionAllowed(true);
				frmScrollpane = new JScrollPane(frmTable);
				frmEvidencija.add(frmScrollpane);
				SwingUtilities.updateComponentTreeUI(frmEvidencija);

			}
		}

		class showProizvodjac implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dodajViseBtn.setEnabled(false);
				filtrirajBtn.setEnabled(false);
				tableName = "Proizvodjac";
				String przQuery = "select * from Proizvodjac";
				Object[][] data3 = dbc.populate(przQuery, 2);
				String[] col3 = { "ID", "Naziv" };
				frmEvidencija.remove(frmScrollpane);
				frmTable = new JTable(data3, col3);
				frmTable.setColumnSelectionAllowed(true);
				frmTable.setRowSelectionAllowed(true);
				frmScrollpane = new JScrollPane(frmTable);
				frmEvidencija.add(frmScrollpane);
				SwingUtilities.updateComponentTreeUI(frmEvidencija);

			}
		}

		class showSkladiste implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dodajViseBtn.setEnabled(false);
				filtrirajBtn.setEnabled(false);
				tableName = "Skladiste";
				String sklQuery = "select * from Skladiste";
				Object[][] data3 = dbc.populate(sklQuery, 4);
				String[] col3 = { "ID", "Naziv", "Adresa", "Kapacitet" };
				frmEvidencija.remove(frmScrollpane);
				frmTable = new JTable(data3, col3);
				frmTable.setColumnSelectionAllowed(true);
				frmTable.setRowSelectionAllowed(true);
				frmScrollpane = new JScrollPane(frmTable);
				frmEvidencija.add(frmScrollpane);
				SwingUtilities.updateComponentTreeUI(frmEvidencija);
			}
		}

		class dodajNovo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (tableName.equals("Hardver")) {
					UnosHardvera.main(null);
				} else if (tableName.equals("Tip")) {
					UnosTipa.main(null);
				} else if (tableName.equals("Softver")) {
					UnosSoftvera.main(null);
				} else if (tableName.equals("Proizvodjac")) {
					UnosProizvodjaca.main(null);
				} else if (tableName.equals("Skladiste")) {
					UnosSkladista.main(null);
				}

				SwingUtilities.updateComponentTreeUI(frmEvidencija);
			}
		}

		class dodajVise implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				UnosViseHardvera.main(null);
				SwingUtilities.updateComponentTreeUI(frmEvidencija);
			}
		}

		class ukloniOdabrano implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (frmTable.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Odaberi nesto.");
				} else {
					dbc.ukloniIzBaze(tableName, frmTable.getValueAt(frmTable.getSelectedRow(), 0).toString());
				}

			}
		}

		class sortirajBazu implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Object[][] sortiraniPodaci = dbc.sortirajBazu(tableName, frmTable.getSelectedColumn());
				frmEvidencija.remove(frmScrollpane);
				frmTable = new JTable(sortiraniPodaci, atributi.get(tableName));
				frmTable.setColumnSelectionAllowed(true);
				frmTable.setRowSelectionAllowed(true);
				frmScrollpane = new JScrollPane(frmTable);
				frmEvidencija.add(frmScrollpane);
				SwingUtilities.updateComponentTreeUI(frmEvidencija);

			}
		}

		class filtrirajBazu implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				HashMap<Integer, Object> pozicijaVrijednost = new HashMap<>();
				for (int i : frmTable.getSelectedColumns()) {
					pozicijaVrijednost.put(i, frmTable.getValueAt(frmTable.getSelectedRow(), i));
				}
				Object[][] data = dbc.filtrirajBazu(tableName, pozicijaVrijednost);
				frmEvidencija.remove(frmScrollpane);
				frmTable = new JTable(data, atributi.get(tableName));
				frmTable.setColumnSelectionAllowed(true);
				frmTable.setRowSelectionAllowed(true);
				frmScrollpane = new JScrollPane(frmTable);
				frmEvidencija.add(frmScrollpane);
				SwingUtilities.updateComponentTreeUI(frmEvidencija);
			}
		}

		class izmijeniBazu implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String[] val = {};
				for (int i : frmTable.getSelectedColumns()) {
					val = Arrays.copyOf(val, val.length + 1);
					val[val.length - 1] = (frmTable.getValueAt(frmTable.getSelectedRow(), i)).toString();
				}
				dbc.izmijeniBazu(tableName, val);
			}
		}

		sftMenu.addActionListener(new showSoftver());
		hrdMenu.addActionListener(new showHardver());
		tipMenu.addActionListener(new showTip());
		przMenu.addActionListener(new showProizvodjac());
		sklMenu.addActionListener(new showSkladiste());
		dodajBtn.addActionListener(new dodajNovo());
		dodajViseBtn.addActionListener(new dodajVise());
		ukloniBtn.addActionListener(new ukloniOdabrano());
		sortirajBtn.addActionListener(new sortirajBazu());
		filtrirajBtn.addActionListener(new filtrirajBazu());
		potvrdi.addActionListener(new izmijeniBazu());
		sortirajBtn.addActionListener(new sortirajBazu());
	}

}
