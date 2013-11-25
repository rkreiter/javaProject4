package database;

import static java.lang.System.out;
import java.sql.*;

public class Demo {

	public static void main(String[] args) {
		String connInfo = "jdbc:sqlite:src/database/DemoDB";
		try
		{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connInfo);
			Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);
			ResultSet res = statement.executeQuery("SELECT * FROM PlayerInfo "
									+ "WHERE UserId = 1;");
			out.println(res.getString("UserName"));
		}
		catch(SQLException sqlEx)
		{
			out.println("Got a SQLException");
			out.println(sqlEx.getMessage());
		}
		catch(ClassNotFoundException cnfEx)
		{
			out.println("Got a ClassNotFoundException!");
		}
	}
}
