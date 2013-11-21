package server;

import static java.lang.System.out;
import java.net.*;
import game.*;

public class StartServer{
	
  
	public static void main(String[] args){
		//Server Stuff
		ClientServerSocket theServer;
		InetAddress IP;
		//Game Information
		Board b;
		Player players[];
		String playerNum, initPlayer, playerMove;
		int numPlayers = -1;
		char colors[] = new char[] {'b', 'y', 'g', 'r'};

		
		
		//Create Server
	    try{
	        IP = InetAddress.getLocalHost();
	        out.println("The server IP address is " + IP.getHostAddress());
	    }
	    catch(SecurityException s){
	        out.println("Security Exception Thrown");
	        return;
	    }
	    catch(UnknownHostException u){
	        out.println("Unknown Host");
	        return;
	    }
	    theServer = new ClientServerSocket(IP.getHostAddress(), 2323);
	    theServer.startServer();
	    
	    
	    
	    //Figure out how many players to play
	    do{
	    	playerNum = theServer.recvString();
	    	try{
	    		numPlayers = Integer.parseInt(playerNum);
	    	}
	    	catch(NumberFormatException e){
	    	
	    	}
	    } while(numPlayers == -1);
	    
	    
	    //Initialize Everything
	    b = new Board();
	    players = new Player[numPlayers];
	    
	    
	    //Wait for players to join
	    for(int i = 0; i < numPlayers; ++i){
	    	initPlayer = theServer.recvString();
	    	players[i] = new Player(initPlayer, colors[i]);
	    }
	    
	    
	    //Actual game logic will loop until winner
	    playerMove = "";
	    b.printBoard();
	    out.print(playerMove);
	}
}
