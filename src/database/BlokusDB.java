package database;

import static java.lang.System.out;
import java.sql.*;

public class BlokusDB {

	Connection conn;
	
	public BlokusDB() {
		//String connInfo = "jdbc:sqlite:src/database/GameDB";
		String connInfo = "jdbc:sqlite::resource:database/GameDB";
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(connInfo);
		}
		catch(SQLException sqlEx)
		{
			out.println("Got a SQLException 0");
			out.println(sqlEx.getMessage());
		}
		catch(ClassNotFoundException cnfEx)
		{
			out.println("Got a ClassNotFoundException! 1");
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
			out.println("Got a SQLException 2");
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
			ResultSet res = statement.executeQuery("SELECT Password "
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
			out.println("Got a SQLException 3");
			out.println(sqlEx.getMessage());
		}
		return loggedIn;
	}
	
	public void updateStats(String userName, int score, boolean winner)
	{
		
		try
		{
			Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);
			ResultSet res = statement.executeQuery("SELECT UserId, Wins, Losses, AverageScore "
									+ "FROM PlayerInfo "
									+ "WHERE UserName = '" + userName + "';");
		
			if(res.next())
			{	
				int wins = res.getInt("Wins"), losses = res.getInt("Losses");
				double oldAvg = res.getInt("AverageScore"), totalScore = (wins+losses)*oldAvg + score;
				double newAvg = totalScore/(wins+losses+1);
				
				if(winner)
					wins++;
				else
					losses++;
				
				String update = "UPDATE PlayerInfo SET Wins=" + wins +", Losses=" + losses + 
								", AverageScore="+ newAvg +" WHERE UserId = "+ res.getInt("UserId") + ";";
				PreparedStatement ps = conn.prepareStatement(update);
				boolean good = ps.execute();
				//RYAN ps.execute() ALWAYS RETURNS FALSE THIS IS BAD... 
				//BUT IT WORKS SO I DONT UNDERSTAND
				if(!good){ 
					//out.println("ERROR: Couldn't update player stats.");
				}
			}
			else
				out.println("ERROR: No such user " + userName);
		}
		catch(SQLException sqlEx)
		{
			out.println("Got a SQLException 4");
			out.println(sqlEx.getMessage());
		}
	}
	
	/*REQUIRES: stat should be one of the statistics from the db
				stat can be: Wins, Losses, or AverageScore
	   RETURNS: value of stat on success
	  		    -1 on failure
	*/
	public double getStat(String userName, String stat)
	{
		
		try
		{
			Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);
			ResultSet res = statement.executeQuery("SELECT " + stat
									+ " FROM PlayerInfo "
									+ "WHERE UserName = '" + userName + "';");
		
			if(res.next())
				return res.getDouble(stat);
			else
				out.println("ERROR: No such user " + userName);
		}
		catch(SQLException sqlEx)
		{
			out.println("Got a SQLException 5");
			out.println(sqlEx.getMessage());
		}
		
		return -1;
	}
}