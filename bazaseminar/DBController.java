package bazaseminar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

public class DBController {

	public String url = "jdbc:mysql://localhost:3306/Evidencija";
	public String username = "root";
	public String password = "Pass2021";

	public Object[][] populate(String query, int columnLen) {
		Object[][] buff = {};
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {

				String data = "";
				for (int i = 1; i <= columnLen; i++) {
					data += rs.getString(i) + ";";
				}

				buff = Arrays.copyOf(buff, buff.length + 1);
				buff[buff.length - 1] = data.split(";");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return buff;
	}

	public void dodajUBazu(String tableName, String[] data) {
		String queryFindIndex = "select MAX(" + tableName + "_ID) from " + tableName + ";";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryFindIndex);

			rs.next();
			int index = (rs.getInt(1) + 1);

			String queryInsert = "insert into " + tableName + " values (" + index + "," + String.join(",", data) + ")";
			Statement st2 = conn.createStatement();
			st2.executeUpdate(queryInsert);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void ukloniIzBaze(String tableName, String id) {
		
		HashMap<String, String[]> straniKljucevi = new HashMap<>();
		String[] HardverFK = {"HardverSoftver"};
		String[] SoftverFK = { "HardverSoftver", "SoftverTip" };
		String[] ProizvFK = { "ProizvodjacTip"};
		String[] TipFK = {"SoftverTip", "ProizvodjacTip", "Hardver"};
		String[] SkladisteFK = {"Hardver"};
		
		straniKljucevi.put("Hardver", HardverFK);
		straniKljucevi.put("Softver", SoftverFK);
		straniKljucevi.put("Proizvodjac", ProizvFK);
		straniKljucevi.put("Tip", TipFK);
		straniKljucevi.put("Skladiste", SkladisteFK);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			
			for (String foreign : straniKljucevi.get(tableName)) {
				String queryDelete = "delete from " + foreign + " where " + tableName + "_ID = " + id;
				Statement statement = conn.createStatement();
				statement.executeUpdate(queryDelete);
			}
			
			String queryInsert = "delete from " + tableName + " where " + tableName + "_ID = " + id;
			Statement st2 = conn.createStatement();
			st2.executeUpdate(queryInsert);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void provjeraValjanostiHardver(String hardverNaziv, String proizvNaziv, String tip_id, String skl_id,
			String[] softveri) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection conn = DriverManager.getConnection(url, username, password);

			String queryFindIndex = "select MAX(Hardver_ID) from Hardver;";
			String proizvodjacQuery = "select exists (select * from proizvodjac where proizvodjac_naziv = "
					+ proizvNaziv + ");";
			String maxProizv = "select MAX(Proizvodjac_ID) from Proizvodjac;";
			String tipQuery = "select exists (select * from tip where tip_id = " + tip_id + ");";
			String skladisteQuery = "select exists (select * from skladiste where skladiste_id = " + skl_id + ");";

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryFindIndex);
			rs.next();
			String index = String.valueOf(rs.getInt(1) + 1);

			Statement st2 = conn.createStatement();
			ResultSet rs2 = st2.executeQuery(proizvodjacQuery);
			rs2.next();
			String proizvodjacPostoji = rs2.getString(1);

			Statement st4 = conn.createStatement();
			ResultSet rs4 = st4.executeQuery(tipQuery);
			rs4.next();

			Statement st5 = conn.createStatement();
			ResultSet rs5 = st5.executeQuery(skladisteQuery);
			rs5.next();

			if (rs4.getString(1).equals("0") || rs5.getString(1).equals("0")) {
				JOptionPane.showMessageDialog(null,
						"Neispravan ID skladista ili tipa. Komponenta nije unesena u bazu.");
			} else {
				if (proizvodjacPostoji.equals("0")) {
					Statement st3 = conn.createStatement();
					ResultSet rs3 = st3.executeQuery(maxProizv);
					rs3.next();
					String indexProizv = String.valueOf(rs3.getInt(1) + 1);
					String[] listaProizv = { indexProizv, proizvNaziv };
					dodajUBazu("Proizvodjac", listaProizv);
				}
				String[] data = { hardverNaziv, proizvNaziv, rs4.getString(1), rs5.getString(1) };
				dodajUBazu("Hardver", data);
				List<String> list = Arrays.asList(softveri);
				list.forEach(n -> dodajUBazu("HardverSoftver", new String[] { index, n }));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void provjeraValjanostiSoftver(String softverNaziv, String[] tipovi, String[] hardveri) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection conn = DriverManager.getConnection(url, username, password);

			String queryFindIndex = "select MAX(Softver_ID) from Softver;";
			String softverExists = "select exists (select * from softver where Softver_Naziv = " + softverNaziv + ");";

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryFindIndex);
			rs.next();
			String index = String.valueOf(rs.getInt(1) + 1);

			Statement st2 = conn.createStatement();
			ResultSet rs2 = st2.executeQuery(softverExists);
			rs2.next();

			if (rs2.getString(1).equals("1")) {
				JOptionPane.showMessageDialog(null, "Uneseni podaci su pogresni, softver vec postoji u bazi.");
			} else {
				String[] data = { softverNaziv };
				dodajUBazu("Softver", data);

				List<String> list = Arrays.asList(tipovi);
				list.forEach(n -> dodajUBazu("SoftverTip", new String[] { index, n }));

				List<String> list2 = Arrays.asList(hardveri);
				list2.forEach(n2 -> dodajUBazu("HardverSoftver", new String[] { n2, index }));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void provjeraValjanostiProizvodjac(String proizvNaziv, String[] tipovi) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection conn = DriverManager.getConnection(url, username, password);

			String queryFindIndex = "select MAX(Proizvodjac_ID) from Proizvodjac;";
			String proizvExists = "select exists (select * from proizvodjac where Proizvodjac_Naziv = " + proizvNaziv
					+ ");";

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryFindIndex);
			rs.next();
			String index = String.valueOf(rs.getInt(1) + 1);

			Statement st2 = conn.createStatement();
			ResultSet rs2 = st2.executeQuery(proizvExists);
			rs2.next();

			if (rs2.getString(1).equals("1")) {
				JOptionPane.showMessageDialog(null, "Uneseni podaci su pogresni, proizvodjac vec postoji u bazi.");
			} else {
				String[] data = { proizvNaziv };
				dodajUBazu("Proizvodjac", data);

				List<String> list = Arrays.asList(tipovi);
				list.forEach(n -> dodajUBazu("ProizvodjacTip", new String[] { index, n }));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void provjeraValjanostiTip(String tipNaziv, String[] softveri, String[] proizvodjaci) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection conn = DriverManager.getConnection(url, username, password);

			String queryFindIndex = "select MAX(Tip_ID) from Tip;";
			String tipExists = "select exists (select * from tip where tip_Naziv = " + tipNaziv + ");";

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryFindIndex);
			rs.next();
			String index = String.valueOf(rs.getInt(1) + 1);

			Statement st2 = conn.createStatement();
			ResultSet rs2 = st2.executeQuery(tipExists);
			rs2.next();

			if (rs2.getString(1).equals("1")) {
				JOptionPane.showMessageDialog(null, "Uneseni podaci su pogresni, tip vec postoji u bazi.");
			} else {
				String[] data = { tipNaziv };
				dodajUBazu("Tip", data);

				List<String> list = Arrays.asList(softveri);
				list.forEach(n -> dodajUBazu("SoftverTip", new String[] { n, index }));

				List<String> list2 = Arrays.asList(proizvodjaci);
				list2.forEach(n2 -> dodajUBazu("ProizvodjacTip", new String[] { n2, index }));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void izmijeniBazu(String tableName, String[] values) {
		HashMap<String, String[]> atributi = new HashMap<>();
		String[] atrHardver = { "Hardver_ID", "Hardver_Naziv", "Hardver_Proizvodjac", "Tip_ID", "Skladiste_ID" };
		String[] atrSoftver = { "Softver_ID", "Softver_Naziv" };
		String[] atrProizvodjac = { "Proizvodjac_ID", "Proizvodjac_Naziv" };
		String[] atrTip = { "Tip_ID", "Tip_Naziv" };
		String[] atrSkladiste = { "Skladiste_ID", "Skladiste_Naziv", "Skladiste_Adresa", "Skladiste_Kapacitet" };
		String[] niz = {};

		atributi.put("Hardver", atrHardver);
		atributi.put("Softver", atrSoftver);
		atributi.put("Tip", atrTip);
		atributi.put("Proizvodjac", atrProizvodjac);
		atributi.put("Skladiste", atrSkladiste);

		String query = "update " + tableName + " set ";

		for (int i = 0; i < values.length; i++) {
			niz = Arrays.copyOf(niz, niz.length + 1);
			niz[niz.length - 1] = atributi.get(tableName)[i] + "=" + values[i];
		}
		query += (String.join(", ", niz));
		query += " where " + atributi.get(tableName)[0] + "=" + values[0] + ";";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement st = conn.createStatement();
			st.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object[][] sortirajBazu(String tableName, int kolona) {
		Object[][] buff = {};
		
		HashMap<String, String[]> atributi = new HashMap<>();
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

		String query = "select * from " + tableName + " order by " + atributi.get(tableName)[kolona] + ";";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {

				String data = "";
				for (int i = 1; i <= atributi.get(tableName).length; i++) {
					data += rs.getString(i) + ";";
				}

				buff = Arrays.copyOf(buff, buff.length + 1);
				buff[buff.length - 1] = data.split(";");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return buff;
	}
	
	public Object[][] filtrirajBazu(String tableName, HashMap<Integer,Object> positionValue) {
		Object[][] buff = {};
		
		HashMap<String, String[]> atributi = new HashMap<>();
		String[] atrHardver = { "Hardver_ID", "Hardver_Naziv", "Hardver_Proizvodjac", "Tip_ID", "Skladiste_ID" };
		String[] atrSoftver = { "Softver_ID", "Softver_Naziv" };
		String[] atrProizvodjac = { "Proizvodjac_ID", "Proizvodjac_Naziv" };
		String[] atrTip = { "Tip_ID", "Tip_Naziv" };
		String[] atrSkladiste = { "Skladiste_ID", "Skladiste_Naziv", "Skladiste_Adresa", "Skladiste_Kapacitet" };
		String[] popunjeniAtributi = {};
		
		atributi.put("Hardver", atrHardver);
		atributi.put("Softver", atrSoftver);
		atributi.put("Tip", atrTip);
		atributi.put("Proizvodjac", atrProizvodjac);
		atributi.put("Skladiste", atrSkladiste);

		String query = "select * from " + tableName + " where ";
		
		for (HashMap.Entry<Integer,Object> entry : positionValue.entrySet()) {
			popunjeniAtributi = Arrays.copyOf(popunjeniAtributi, popunjeniAtributi.length + 1);
			try {
				Integer i = Integer.parseInt(entry.getValue().toString());
				popunjeniAtributi[popunjeniAtributi.length-1] = atributi.get(tableName)[entry.getKey()] + "=" + entry.getValue();
			} catch (Exception e) {
				
			}
			popunjeniAtributi[popunjeniAtributi.length-1] = atributi.get(tableName)[entry.getKey()] + "= '" + entry.getValue() + "'";
		}
		
		query += String.join(" and ", popunjeniAtributi) + ";";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {

				String data = "";
				for (int i = 1; i <= atributi.get(tableName).length; i++) {
					data += rs.getString(i) + ";";
				}

				buff = Arrays.copyOf(buff, buff.length + 1);
				buff[buff.length - 1] = data.split(";");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return buff;
	}
}
