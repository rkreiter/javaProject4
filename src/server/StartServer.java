package server;

import static java.lang.System.out;
import java.net.*;

import game.*;

public class StartServer{
  
	public static InetAddress initServer(){
		InetAddress IP = null;
		try{
	        IP = InetAddress.getLocalHost();
	        out.println("The server IP address is " + IP.getHostAddress());
	    }
	    catch(SecurityException s){
	        out.println("Security Exception Thrown");
	        System.exit(1);
	    }
	    catch(UnknownHostException u){
	        out.println("Unknown Host");
	        System.exit(1);
	    }
	    return IP;
	}
	
	public static void main(String[] args){
		//Server Stuff
		ClientServerSocket theServer;
		InetAddress IP;
		//Game Information
		Board board;
		Player players[];
		String playerName, playerMove;
		int numPlayers = -1;
		char colors[] = new char[] {'b', 'y', 'g', 'r'};

		//Create Server
		IP = initServer();
	    theServer = new ClientServerSocket(IP.getHostAddress(), 4000);
	    out.println("calling start server");
	    numPlayers = theServer.startServer();
	    out.println("Got num players: " + numPlayers);
	    
	    //Initialize Everything
	    board = new Board();
	    players = new Player[numPlayers];

	    //Wait for players to join
	    for(int i = 0; i < numPlayers; ++i){
	    	playerName = theServer.getPlayer(i);
	    	players[i] = new Player(playerName, colors[i]);
	    	theServer.sendAcknowledgement(i);
	    }

	    //Actual game logic will loop until winner
	    playerMove = "";
	    board.printBoard();
	    out.print(playerMove);
	    
	    while(true);
	}
}
