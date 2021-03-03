package bazaseminar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class VariousTests {
	
public static void main(String[] args) throws SQLException {
		
		
		String url = "jdbc:mysql://localhost:3306/Evidencija";
		String username = "root";
		String password = "Pass2021";
		String query = "select * from skladiste";
		String[] lista = {"6","'Spajz'","'Dom rata'","20"};
		List<Object> lista2 = new ArrayList<>();
		lista2.add(1);
		lista2.add("'Spajz'");
		lista2.add("'Dom rata 2'");
		lista2.add(20);
		//String query2 = "insert into skladiste values (" + String.join(",", lista) + ")";
		String query2 = "select exists(select * from hardver where skladiste_id = 1)";
		Object [][] buff = {};
		//String query = "insert into Skladiste values (5, 'Spajz', 'Domovinskog rata 2', 50)";
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement st = conn.createStatement();
			ResultSet rss = st.executeQuery(query2);
			
			rss.next();
			System.out.println("RSS JEEEEE :  " + rss.getString(1));;
			Statement st3 = conn.createStatement();
			ResultSet rs3 = st.executeQuery("select MAX(Proizvodjac_ID) from Proizvodjac;");
			rs3.next();
			String indexProizv = String.valueOf(rs3.getInt(1)+1);
			
			
			System.out.println(indexProizv +" ken rosenBURP");
			//int affected = st.executeUpdate(query);
			//System.out.println(affected + " rows affected");
			
			Integer[] values = {1,2,3,4,5};
			List<Integer> list = Arrays.asList(values);
			list.forEach(n -> System.out.println(n));
			//st.close();
			//conn.close();
			
			String[] arr = {"Tommy", "Lance", "Ken"};
			
			HashMap<String,String[]> mapa = new HashMap<>();
			String[] hardverCombo = {"Hardver_Naziv", "Hardver_Proizvodjac", "Tip_ID", "Skladiste_ID"};
			String[] softverCombo = {"Softver_Naziv"};
			String[] proizvCombo = {"Proizvodjac_Naziv"};
			String[] tipCombo = {"Tip_Naziv"};
			String[] skladisteCombo = {"Skladiste_Naziv", "Skladiste_Adresa", "Skladiste_Kapacitet"};
			mapa.put("Hardver", hardverCombo);
			System.out.println("ovo je zadnje " + mapa.get("Hardver")[2]);
			
			System.out.println(String.join(" and ", arr));
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
}
