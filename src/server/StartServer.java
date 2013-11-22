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
		//Instant variables
		int type, x, y;
		Player player = null;
		Piece piece = null;

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
	    	playerName = theServer.getPlayerName(i);
	    	players[i] = new Player(playerName, colors[i]);
	    	theServer.sendPlayerInfoToClient(players[i], i);
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
	    		String move[] = theServer.parseMove(playerMove);
	    		try{
	    			player = players[turn];
	    			if(move[0].charAt(0) == player.getColor()){
	    				type = Integer.parseInt(move[1]);
	    				if(type >= 0 && type < 21){
	    					piece = player.getPiece(type);
	    					if(!piece.isPlaced()){
	    						x = Integer.parseInt(move[2]);
	    						y = Integer.parseInt(move[3]);
	    						if(move[4].length() == 25){
	    							piece.setState(move[4]);
	    							if(player.getScore() == 89)
	    								validMove = board.validInit(x, y, piece);
	    							else
	    								validMove = board.validPlace(x, y, piece, false);
	    							if(validMove){
	    								board.placePiece(x, y, piece);
	    								piece.setPlaced();
	    								player.updateScore(piece.getValue());
	    							}
	    						}
	    					}
	    				}
	    			}	    				
	    		}
	    		catch(Exception e){}
	    	}while(!validMove);
	    	theServer.sendAcknowledgement(turn);
	    	
	    	//Send Updates
	    	theServer.sendUpdate(playerMove, turn);
	    	
	    	//Check if Player has finished
	    	if(player.getScore() == 0){
	    		theServer.sendEndGame(player.getName());
	    		System.exit(0);
	    	}
	    	
	    	//Find next player
	    	int count = 0;
	    	do{
	    		count++;
	    		turn = (turn + 1) % numPlayers;
	    		if(count > numPlayers){
	    			for(int i = 0; i < numPlayers; ++i){
	    				if(players[i].getScore() < player.getScore())
	    					player = players[i];
	    			}
	    			theServer.sendEndGame(player.getName());
	    			System.exit(0);
	    		}
	    		if(player.isPlayable()){
	    			if(!board.playerCanPlay(player))
	    				player.setPlayable(false);
	    		}
	    	} while(!player.isPlayable());
	    	
	    	//Debugging stuff
	    	board.printBoard();
	    }
	}
}
