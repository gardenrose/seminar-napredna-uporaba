package bazaseminar;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class JDBC {
	

	public static void main(String[] args) throws SQLException {
		
		
		String url = "jdbc:mysql://localhost:3306/Evidencija";
		String username = "root";
		String password = "Pass2021";
		String query = "select * from Hardver";
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
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				
				String data = "";
				for (int i = 1; i <= 5 ; i++) {
					data += rs.getString(i) + ";";
			}
				buff= Arrays.copyOf(buff, buff.length + 1);
			    buff[buff.length - 1] = data.split(";");
			}
				
			
			//int affected = st.executeUpdate(query);
			//System.out.println(affected + " rows affected");
			
			
			//st.close();
			//conn.close();
			String[] columns = {"ID","Naziv","Proizvodjac","Tip_ID","Skladiste_ID"};
			GUI gui = new GUI(buff, columns);
			gui.tableName = "Hardver";
			gui.load(buff, columns);
			System.out.println("Ozzie kangaroo, uspjesno pokrenuto");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}