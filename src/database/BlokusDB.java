package database;

import static java.lang.System.out;
import java.sql.*;

public class BlokusDB {

	Connection conn;
	
	public BlokusDB() {
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
									+ "WHERE UserName = '" + userName + "';");
			
			if(!res.next())
			{
				valid = true;
				statement.executeUpdate("INSERT INTO PlayerInfo "
									+ "(UserName, Password, Wins, Losses) "
									+ "VALUES ('" + userName + "', '" + password + "', 0, 0);");
				out.println("Entered " + userName + " into the table.");
			}
			else out.println("Create user invalid");
			res.close();
			statement.close();
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
									+ "WHERE UserName = '" + userName + "';");
		
			//if there is a user with this username
			if(res.next()){
				if(res.getString("Password").equals(password)) loggedIn = true;
				else out.println("Wrong Password. Password Should Be: " + res.getString("Password"));
			}
			else out.println("Invalid Username");
			res.close();
			statement.close();
		}
		catch(SQLException sqlEx)
		{
			out.println("Got a SQLException");
			out.println(sqlEx.getMessage());
		}
		return loggedIn;
	}
}
