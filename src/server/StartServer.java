package server;

import static java.lang.System.out;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import game.*;

public class StartServer{
	static JFrame terminal;
	static JTextArea textArea;
	static JScrollPane scroll;
  
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
		//Terminal Screen Simulation
		terminal = new JFrame("Terminal Output");
		textArea = new JTextArea (600, 800);
		textArea.setEditable(false);
		scroll = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		terminal.setSize(600, 800);
		terminal.add(scroll);
		terminal.setVisible(true);
		//Server Stuff
		ClientServerSocket theServer;
		InetAddress IP;
		boolean droppedConnection[];
		
		//Game Information
		Board board;
		Player players[];
		String playerName, playerMove = null;
		int numPlayers = -1;
		char colors[] = new char[] {'b', 'r', 'y', 'g'};
		
		//Instant variables
		int type, x, y;
		Player player = null;
		Piece piece = null;

		
		//Create Server
		IP = initServer();
		textArea.append("The server IP address is " + IP.getHostAddress() + '\n');
	    theServer = new ClientServerSocket(IP.getHostAddress(), 4040);
	    out.println("Calling start server");
	    textArea.append("Calling start server\n");
	    numPlayers = theServer.startServer(textArea);
	    out.println("Got num players: " + numPlayers);
	    textArea.append("Got num players: " + numPlayers + '\n');
	    
	    
	    //Initialize Everything
	    board = new Board();
	    players = new Player[numPlayers];
	    droppedConnection = new boolean[numPlayers];

	    
	    //Wait for players to join
	    for(int i = 0; i < numPlayers; ++i){
	    	try{
	    		playerName = theServer.getPlayerName(i);
	    		players[i] = new Player(playerName, colors[i]);
	    		theServer.sendPlayerInfoToClient(players[i], i);
	    	}
	    	catch(Exception e){
	    		//Should debug
	    		System.exit(2);
	    	}
	    }
	    
	    
	    //send all player info to each client for them to make their boards
	    out.println("Sending All Player's Information");
	    textArea.append("Sending All Player's Information\n");
	    for(int i = 0; i < numPlayers; ++i){
	    	try{
	    		theServer.sendAllPlayersToClient(players, i);
	    	}
	    	catch(Exception e){
	    		System.out.println("Player " + i + " left");
	    		textArea.append("Player " + i + " left\n");
	    		System.exit(2);
	    	}
	    }

	    //Actual game logic will loop until winner
	    board.printBoard(textArea);
	    int turn = 0;
	    while(true){
	    	System.out.println(players[turn].getName() + "'s Move");
	    	textArea.append(players[turn].getName() + "'s Move\n");
	    	textArea.setCaretPosition(textArea.getText().length() - 1);

	    	try{
	    		//Tell everyone who's move it is
	    		theServer.announceTurn(turn);
	    		
		    	//Get a valid move from current Player
	    		theServer.askForMove(turn);
	    		playerMove = theServer.getMove(turn);
	    		
	    		//Interpret move
	    		String move[] = theServer.parseMove(playerMove);
	    		player = players[turn];
	    		type = Integer.parseInt(move[1]);
	    		piece = player.getPiece(type);
	    		x = Integer.parseInt(move[2]);
				y = Integer.parseInt(move[3]);
				piece.setState(move[4]);
				
				//Execute move
	    		board.placePiece(x, y, piece);
				piece.setPlaced();
				player.updateScore(piece.getValue());
				
		    	//Send Updates
				theServer.sendAcknowledgement(turn);
		    	System.out.println("Update:   " + playerMove);
		    	textArea.append("Update:   " + playerMove + '\n');
		    	textArea.setCaretPosition(textArea.getText().length() - 1);
		    	theServer.sendUpdate(playerMove, turn, textArea);
		    	
		    	//Check if Player has finished
		    	if(player.getScore() == 0){
		    		break;
		    	}
	    	}
	    	catch(Exception e){
		    	System.out.println("Player " + turn + "has quit");
		    	textArea.append("Player " + turn + "has quit\n");
		    	textArea.setCaretPosition(textArea.getText().length() - 1);
		    	droppedConnection[turn] = true;
		    }
	    	

	    	
	    	boolean dropped = true;
	    	for(int i = 0; i < numPlayers; ++i){
	    		if(!droppedConnection[i]){
	    			dropped = false;
	    		}
	    	}
	    	//Every connection has been lost so end server
	    	if(dropped){
	    		break;
	    	}
	    	
	    	
	    	
	    	//Find next player
	    	int count = 0;
	    	do{
	    		count++;
	    		turn = (turn + 1) % numPlayers;
	    		player = players[turn];
	    		if(count > numPlayers){
	    			//Its an end game so close the server
	    			break;
	    		}
	    		if(droppedConnection[turn]) continue;
	    		if(player.isPlayable()){
	    			if(!board.playerCanPlay(player))
	    				player.setPlayable(false);
	    		}
	    	} while(!player.isPlayable());
	    	
	    	//Debugging stuff
	    	board.printBoard(textArea);
	    }
	    System.out.println("Server is doing clean up");
	    textArea.append("Server is doing clean up\n");
	    textArea.setCaretPosition(textArea.getText().length() - 1);
	    
	    //Find winning player
	    for(int i = 0; i < numPlayers; ++i){
			if(players[i].getScore() < player.getScore())
				player = players[i];
		}
	    String name = player.getName();
	    
	    
	    //Send end game
	    theServer.sendEndGame(name);
	    
	    //UPDATE DATABASE STUFF
	    //RYAN WE NEED TO DO YOUR STUFF HERE!!!!!!
	}
}
