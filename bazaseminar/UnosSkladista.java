package bazaseminar;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class UnosSkladista {

	private JFrame frmSkladiste;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	public String[] data = {};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UnosSkladista window = new UnosSkladista();
					window.frmSkladiste.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UnosSkladista() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSkladiste = new JFrame();
		frmSkladiste.setTitle("Skladiste");
		frmSkladiste.setBounds(100, 100, 251, 262);
		frmSkladiste.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSkladiste.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Naziv :");
		lblNewLabel.setBounds(10, 27, 46, 14);
		frmSkladiste.getContentPane().add(lblNewLabel);
		
		JLabel lblTresa = new JLabel("Adresa :");
		lblTresa.setBounds(10, 72, 57, 14);
		frmSkladiste.getContentPane().add(lblTresa);
		
		JLabel lblKapacitet = new JLabel("Kapacitet :");
		lblKapacitet.setBounds(10, 122, 69, 14);
		frmSkladiste.getContentPane().add(lblKapacitet);
		
		textField = new JTextField("''");
		textField.setBounds(90, 24, 135, 20);
		frmSkladiste.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField("''");
		textField_1.setColumns(10);
		textField_1.setBounds(90, 69, 135, 20);
		frmSkladiste.getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(89, 119, 136, 20);
		frmSkladiste.getContentPane().add(textField_2);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBounds(59, 168, 101, 31);
		frmSkladiste.getContentPane().add(btnNewButton);
		
		class OK_click implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().equals("''") || textField_1.getText().equals("''") || textField_2.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Sva polja moraju biti popunjena.");
				} else {
					data= Arrays.copyOf(data, data.length + 3);
				    data[0] = textField.getText();
				    data[1] = textField_1.getText();
				    data[2] = textField_2.getText();
				    DBController dbc = new DBController();
				    dbc.dodajUBazu("Skladiste", data);
				    frmSkladiste.setVisible(false);
				}
			}
			
		}
		btnNewButton.addActionListener(new OK_click());
	}
}
