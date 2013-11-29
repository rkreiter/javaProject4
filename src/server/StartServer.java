package server;

import static java.lang.System.out;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.*;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;

import database.BlokusDB;
import game.*;

public class StartServer{
	static JFrame terminal;
	static JTextArea textArea;
	static JScrollPane scroll;
	static int portNum = 4040;
  
	public static InetAddress initServer(){
		InetAddress IP = null;
		try{
	        IP = InetAddress.getLocalHost();
	        out.println("The server IP address is " + IP.getHostAddress() +
	        		"   Port: " + portNum);
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
		scroll = new JScrollPane (textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		terminal.setSize(600, 800);
		terminal.add(scroll);
		terminal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		terminal.setVisible(true);
		
		//Put in an override shut down on server
		ActionMap actionMap = scroll.getActionMap();
        InputMap inputMap = scroll.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), "QUIT");
        actionMap.put("QUIT", new AbstractAction() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		System.exit(0);
        	}
        });
		
		//Server Stuff
		ClientServerSocket theServer;
		InetAddress IP;
		boolean droppedConnection[];
		
		//Game Information
		Board board;
		Player players[];
		String playerMove = null;
		int numPlayers = -1;
		BlokusDB db = new BlokusDB();
		int type, x, y;
		Player player = null;
		Piece piece = null;

		//Create Server
		IP = initServer();
		textArea.append("The server IP address is " + IP.getHostAddress() + 
				"   Port: " + portNum + '\n');
	    theServer = new ClientServerSocket(IP.getHostAddress(), portNum);
	    out.println("Calling start server");
	    textArea.append("Calling start server\n");
	    
	    //Init Server lets all players join and sign in
	    players = theServer.startServer(textArea, db);
	    numPlayers = players.length;
	    out.println("Got num players: " + numPlayers);
	    textArea.append("Got num players: " + numPlayers + '\n');
	    
	    
	    //Initialize Everything
	    board = new Board();
	    droppedConnection = new boolean[numPlayers];

	    
	    //send all player info to each client for them to make their boards
	    out.println("Sending All Player's Information");
	    textArea.append("Sending All Player's Information\n");
	    for(int i = 0; i < numPlayers; ++i){
	    	try{
	    		theServer.sendAllPlayersToClient(players, i, db);
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
	    			break;
	    		}
	    		if(droppedConnection[turn]) continue;
	    		if(player.isPlayable()){
	    			if(!board.playerCanPlay(player))
	    				player.setPlayable(false);
	    		}
	    	} while(!player.isPlayable());
	    	board.printBoard(textArea);
	    }
	    System.out.println("Server is doing clean up");
	    textArea.append("Server is doing clean up\n");
	    textArea.setCaretPosition(textArea.getText().length() - 1);
	    
	    //Find winning player
	    player = players[0];
	    for(int i = 1; i < numPlayers; ++i){
			if(players[i].getScore() < player.getScore())
				player = players[i];
		}
	    String name = player.getName();
	    
	    //Send end game
	    theServer.sendEndGame(name);
	    
	    //UPDATE DATABASE STUFF
	    for(int i = 0; i < numPlayers; ++i){
	    	player = players[i];
	    	boolean win = false;
	    	if(player.getName().equals(name)){
	    		win = true;
	    	}
	    	out.println(player.getName() + " " + win + " " + player.getScore());
	    	textArea.append(player.getName() + " " + win + " " + player.getScore() + '\n');
	    	db.updateStats(player.getName(), player.getScore(), win);
	    }
	}
}