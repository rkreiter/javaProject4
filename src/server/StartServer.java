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
	    theServer = new ClientServerSocket(IP.getHostAddress(), 4343);
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
	    board.printBoard();
	    int turn = 0;
	    
	    while(true){
	    	//Get a valid move from current Player
	    	boolean validMove = false;
	    	do{
	    		theServer.askForMove(turn);
	    		playerMove = theServer.getMove(turn);
	    		validMove = true;
	    		//**Check if this move is a valid move
	    	} while(!validMove);
	    	theServer.sendAcknowledgement(turn);
	    	
	    	//Send Updates
	    	theServer.sendUpdate(playerMove, turn);
	    	
	    	//Find next player
	    	int count = 0;
	    	do{
	    		count++;
	    		turn = (turn + 1) % numPlayers;
	    		if(count > numPlayers){
	    			//End game scenario say who wins
	    			theServer.sendEndGame(players[0].getName());
	    		}
	    	} while(players[turn].getScore() != 89 && !board.playerCanPlay(players[turn]));

	    	//Debugging stuff
	    	board.printBoard();
	    }
	}
}
