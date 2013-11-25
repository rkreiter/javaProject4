package server;

import static java.lang.System.out;
import game.Board;
import game.Player;
import intro.*;
import proj4board.*;

import java.awt.Color;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

public class Blokus{
	static startFrame init;
	static Frame frame;
	public static Board board;
	public static Player players[] = null;
	public static Player player;
	public static int playerNum = -1;
	public static int numPlayers;
	public static ClientServerSocket theClient;
	public static boolean playable = true;
	
	public static char interpretResponse(String str) throws IOException{
		switch (str.charAt(0)){
    	//OK
		case '0':
			out.println("OK");
    		break;

    	//SEND NUMBER
		case '1':
			out.println("Sending Num Players");
			String num;
			textDial numplayers;
			do{
				numplayers = new textDial(init, "Number Of Players",
						"   Enter the number of players (1-4): ");
				num = numplayers.getText();
			}while(!(num).matches("[1-4]"));
			theClient.sendString(num, 0);
	    	
			String recvdStr = theClient.getResponse();
	    	interpretResponse(recvdStr);
    		break;
    	
    	//SEND MOVE
		case '2':
			out.println("Send server move");
			frame.setPlayerTurn(true);
    		break;
    	
    	//DO UPDATE
		case '3':
			out.println("Update board");
			frame.placePieceOnBoard(str.substring(2));
    		break;
    	
    	//GIVEN PLAYER INFO
		case '4':
			out.println("Got Color!: " + str.charAt(2));
			switch (str.charAt(2)){
				case 'b':
					playerNum = 0;
					break;
				case 'r':
					playerNum = 1;
					break;
				case 'y':
					playerNum = 2;
					break;
				case 'g':
					playerNum = 3;
					break;
			}
			System.out.println(playerNum);
			break;
    	
		//INITIALIZE PLAYER ARRAY
		case '5':
			out.println("Get all Players");
			Scanner scan1 = new Scanner(str);
			scan1.next();
			numPlayers = scan1.nextInt();
			players = new Player[numPlayers];
			for(int i = 0; i < numPlayers; ++i){
				players[i] = new Player(scan1.next(), scan1.next().charAt(0));
			}
			scan1.close();
			break;
						
    	//END GAME
		case '6':
			out.println("Game over");
			Scanner scan2 = new Scanner(str);
			scan2.next();
			endWin end;
			if(scan2.next().equals(player.getName())){
				out.println("YOU WIN!!!!");
				frame.setVisible(false);
				end = new endWin(init, 'w');
				end.pack();
		    	end.setSize(end.getWidth()+50, end.getHeight());
			}
			else{
				out.println("loser....");
				frame.setVisible(false);
				end = new endWin(init, 'l');
				end.pack();
		    	end.setSize(end.getWidth()+50, end.getHeight()+10);
			}
	        end.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	    	end.setVisible(true);
			scan2.close();
			theClient.closeConnection(0);
			//System.exit(0);
			playable = false;
    		break;
		}
		return '\0';
	}
	
	public static void main(String[] args) throws IOException{
		//Game Variables
		board = new Board();
		//players array constructed above
		player = null;
	    playerNum = 0;
	    String recvdStr;
	    
	    
	    //Make Welcome Screen
	    init = new startFrame("Welcome To Blokus");
	    init.setBackground(Color.BLACK);
	    init.setSize(600,600);
	    init.setVisible(true);
	    init.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    boolean running = false;
	    while(init.getPlay() == 'n'){
	    	if(!running){
	    		System.out.print("");
	    		running = true;
	    	}
	    }
	    
	    
	    //Create Client after Start button hit
	    theClient = new ClientServerSocket("192.168.1.234", 4000);
	    //theClient = new ClientServerSocket("67.194.3.146", 4000);
	    theClient.startClient();
	    
	    
	    
    	//0: Get response from server to get name
    	recvdStr = theClient.getResponse();
    	interpretResponse(recvdStr);  
    	
    	//Send Init Player request and wait for response
    	/*if(init.getPlay() == 'l'){+-
    	textDial nameplayer;
    	String name;
		do{
	    	nameplayer = new textDial(init, "Name",
					"   Enter your name (no spaces allowed):  ");
	    	name = nameplayer.getText();
		}while(name.contains(" ") || name.isEmpty());
		*/
		if(init.getPlay() == 'o')
			theClient.sendName(init.getUsername());
		
		//Put up waiting prompt
		waitingWin waiting = new waitingWin(init);
    	waiting.pack();
    	waiting.setSize(waiting.getWidth()+50, waiting.getHeight()-30);
        waiting.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	waiting.setVisible(true);
		
    	
    	//4: get player color
    	recvdStr = theClient.getResponse();
    	interpretResponse(recvdStr);
    	
    	
       	//5: get all player names from server
    	recvdStr = theClient.getResponse();
    	interpretResponse(recvdStr);
    	waiting.setVisible(false);

    	//Initialize stuff
    	player = players[playerNum];
    	board.printBoard();
    	
    	
    	//Start game for each player
    	frame = new Frame("Blokus", board, players, player, playerNum, theClient);
    	frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        if(playerNum != 0){
        	frame.setPlayerTurn(false);
        }
        frame.setVisible(true);
        
        
        //Start actual game
      	try{
	        while(playable){
	      		recvdStr = theClient.getResponse();
	      		interpretResponse(recvdStr);
      		}
      	}
      	catch(Exception e){
      		System.out.println("Server has given client some sort of problem");
      		System.out.println(e);
      	}
	}
}
