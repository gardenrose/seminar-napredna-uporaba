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

public class UnosSoftvera {

	private JFrame frmTip;
	public JTextField textField;
	public DBController dbc = new DBController();
	public String[] data = {};
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UnosSoftvera window = new UnosSoftvera();
					window.frmTip.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UnosSoftvera() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTip = new JFrame();
		frmTip.setTitle("Softver");
		frmTip.setBounds(100, 100, 277, 410);
		frmTip.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frmTip.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Naziv :");
		lblNewLabel.setBounds(10, 32, 46, 14);
		frmTip.getContentPane().add(lblNewLabel);
		
		textField = new JTextField("''");
		textField.setBounds(86, 29, 157, 20);
		frmTip.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		JLabel lblSoftver = new JLabel("Tip :");
		lblSoftver.setBounds(10, 85, 66, 14);
		frmTip.getContentPane().add(lblSoftver);
		
		JButton btnOK = new JButton("OK");
		btnOK.setBounds(86, 330, 100, 30);
		frmTip.getContentPane().add(btnOK);
		
		JPanel pnl = new JPanel();
		pnl.setLayout(new javax.swing.BoxLayout(pnl, javax.swing.BoxLayout.Y_AXIS));
		Object[][] softverr = dbc.populate("select tip_id,tip_naziv from tip;", 2);
		
		for (Object[] sftvr : softverr) {
			JCheckBox ch = new JCheckBox(sftvr[0].toString() + " " + sftvr[1].toString());
			pnl.add(ch);
		}
		JScrollPane pain = new JScrollPane(pnl);
		pain.setBounds(86, 85, 158, 113);
		frmTip.getContentPane().add(pain);
		
		
		
		
		JPanel pnl1 = new JPanel();
		pnl1.setLayout(new javax.swing.BoxLayout(pnl1, javax.swing.BoxLayout.Y_AXIS));
		Object[][] proizvodjacc = dbc.populate("select hardver_id,hardver_naziv from hardver;", 2);
		
		for (Object[] proizv : proizvodjacc) {
			JCheckBox box = new JCheckBox(proizv[0].toString() + " " + proizv[1].toString());
			pnl1.add(box);
		}
		
		JScrollPane pain_1 = new JScrollPane(pnl1);
		pain_1.setBounds(85, 209, 158, 113);
		frmTip.getContentPane().add(pain_1);
		
		JLabel lblProizvodjaci = new JLabel("Hardver :");
		lblProizvodjaci.setBounds(10, 209, 83, 14);
		frmTip.getContentPane().add(lblProizvodjaci);
		
		
		class OK_click implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().equals("''")) {
					JOptionPane.showMessageDialog(null, "Sva polja moraju biti popunjena.");
				} else {
					data= Arrays.copyOf(data, data.length + 1);
				    data[0] = textField.getText();
				    
				    String[] tipovi  = {};
				    String[] hardveri = {};
				    for( Component comp : pnl.getComponents() ) {
				       if( comp instanceof JCheckBox) {
				    	   JCheckBox check = ( (JCheckBox)comp );
				    	   if(check.isSelected()) {
				    		   tipovi = Arrays.copyOf(tipovi, tipovi.length + 1);
				    		   tipovi[tipovi.length - 1] = check.getText().split(" ")[0];
				    	   }
				       }
				    }
				    for( Component comp : pnl1.getComponents() ) {
					       if( comp instanceof JCheckBox) {
					    	   JCheckBox check = ( (JCheckBox)comp );
					    	   if(check.isSelected()) {
					    		   hardveri = Arrays.copyOf(hardveri, hardveri.length + 1);
					    		   hardveri[hardveri.length - 1] = check.getText().split(" ")[0];
					    	   }
					       }
					    }
				    
				    DBController dbc = new DBController();
				    dbc.provjeraValjanostiSoftver(data[0],tipovi, hardveri);
				    frmTip.setVisible(false);
				}
			}
			
		}
		btnOK.addActionListener(new OK_click());
	}
}
