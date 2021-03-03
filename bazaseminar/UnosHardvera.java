package bazaseminar;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class UnosHardvera {
	public JFrame frame;
	public JButton btnOK = new JButton("OK");
	public String[] data = {};
	public DBController dbc = new DBController();

	public JLabel lbl1 = new JLabel("Naziv :");
	public JLabel lbl2 = new JLabel("Proizvodjac :");
	public JLabel lbl3 = new JLabel("Tip_ID :");
	public JLabel lbl4 = new JLabel("Skladiste_ID :");
	private final JLabel lblSoftver = new JLabel("Softver:");

	public JTextField txt1 = new JTextField("''");
	public JTextField txt2 = new JTextField("''");
	public JTextField txt3 = new JTextField();
	public JTextField txt4 = new JTextField();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UnosHardvera window = new UnosHardvera();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UnosHardvera() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Hardver");
		frame.setBounds(100, 100, 281, 420);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lbl1.setBounds(10, 22, 46, 14);
		frame.getContentPane().add(lbl1);

		txt1.setBounds(95, 22, 160, 20);
		frame.getContentPane().add(txt1);
		txt1.setColumns(10);

		lbl2.setBounds(10, 62, 75, 14);
		frame.getContentPane().add(lbl2);

		lbl3.setBounds(10, 100, 52, 14);
		frame.getContentPane().add(lbl3);

		lbl4.setBounds(10, 140, 88, 14);
		frame.getContentPane().add(lbl4);

		txt2.setColumns(10);
		txt2.setBounds(95, 62, 160, 20);
		frame.getContentPane().add(txt2);

		txt3.setColumns(10);
		txt3.setBounds(95, 100, 160, 20);
		frame.getContentPane().add(txt3);

		txt4.setColumns(10);
		txt4.setBounds(95, 140, 160, 20);
		frame.getContentPane().add(txt4);

		btnOK.setBounds(68, 320, 123, 36);
		frame.getContentPane().add(btnOK);
		lblSoftver.setBounds(10, 184, 88, 14);
		frame.getContentPane().add(lblSoftver);

		JPanel pnl = new JPanel();
		pnl.setLayout(new javax.swing.BoxLayout(pnl, javax.swing.BoxLayout.Y_AXIS));

		Object[][] softverr = dbc.populate("select softver_id,softver_naziv from softver;", 2);

		for (Object[] sftvr : softverr) {
			JCheckBox ch = new JCheckBox(sftvr[0].toString() + " " + sftvr[1].toString());
			pnl.add(ch);
		}
		JScrollPane pain = new JScrollPane(pnl);
		pain.setBounds(95, 184, 160, 115);
		frame.getContentPane().add(pain);

		class OK_click implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (txt1.getText().equals("''") || txt2.getText().equals("''") || txt3.getText().equals("")
						|| txt4.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Sva polja moraju biti popunjena.");
				} else {
					data = Arrays.copyOf(data, data.length + 4);
					data[0] = txt1.getText();
					data[1] = txt2.getText();
					data[2] = txt3.getText();
					data[3] = txt4.getText();
					String[] softveri = {};
					for (Component comp : pnl.getComponents()) {
						if (comp instanceof JCheckBox) {
							JCheckBox check = ((JCheckBox) comp);
							if (check.isSelected()) {
								softveri = Arrays.copyOf(softveri, softveri.length + 1);
								softveri[softveri.length - 1] = check.getText().split(" ")[0];
							}
						}
					}

					DBController dbc = new DBController();
					dbc.provjeraValjanostiHardver(txt1.getText(), txt2.getText(), txt3.getText(), txt4.getText(),
							softveri);
					frame.setVisible(false);
				}
			}

		}
		btnOK.addActionListener(new OK_click());

	}

}
