package database;

import static java.lang.System.out;
import java.sql.*;

public class BlokusDB {

	static Connection conn;
	
	public static void main(String[] args) {
		String connInfo = "jdbc:sqlite:src/database/GameDB";
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(connInfo);
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
	
	public boolean createUser(String userName, String password)
	{
		boolean valid = false;
		try
		{
			Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);
			ResultSet res = statement.executeQuery("SELECT UserId FROM PlayerInfo "
									+ "WHERE UserName = " + userName + ";");
			valid = !res.first();
			if(valid)
			{
				statement.executeUpdate("INSERT INTO PlayerInfo "
									+ "(UserName, Password, Wins, Losses) "
									+ "VALUES (" + userName + ", " + password + ", 0, 0);");
				out.println("Entered " + userName + " into the table.");
			}
		}
		catch(SQLException sqlEx)
		{
			out.println("Got a SQLException");
			out.println(sqlEx.getMessage());
		}
		
		return valid;
	}
	
	public boolean userLogin(String userName, String password)
	{
		boolean loggedIn = false;
		try
		{
			Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);
			ResultSet res = statement.executeQuery("SELECT UserId, Password "
									+ "FROM PlayerInfo "
									+ "WHERE UserName = " + userName + ";");
		
			//if there is a user with this username
			if(res.first())
				if(res.getString("Password") == password) loggedIn = true;
		}
		catch(SQLException sqlEx)
		{
			out.println("Got a SQLException");
			out.println(sqlEx.getMessage());
		}
		return loggedIn;
	}
}
