package server;

import static java.lang.System.out;
import game.Board;
import game.Piece;
import game.Player;
import intro.*;
import proj4board.*;

import java.awt.Color;
import java.util.Scanner;

import javax.swing.JFrame;

public class Blokus{
	static startFrame init;
	static Player players[];
	static char colors[];
	static Frame frame;
	static Board board;
	static Player player;
	static int playerNum;
	static ClientServerSocket theClient;
	
	public static char interpretResponse(String str, ClientServerSocket c,
			Board b, Player p){
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
			
			c.sendString(num, 0);
			String recvdStr = c.getResponse();
    	    interpretResponse(recvdStr, c, b, p);
    		break;
    	
    	//SEND MOVE
		case '2':
			out.println("Send server move");
			frame.setPlayerTurn(true);
    		break;
    	
    	//DO UPDATE
		case '3':
			out.println("Update board");
			System.out.println(str);
			String s[] = c.parseMove(str.substring(2));
			frame.placePieceOnBoard(s);
    		break;
    	
    	//GIVEN PLAYER INFO
		case '4':
			out.println("Got Color!");
			break;
    	
		//INITIALIZE PLAYER ARRAY
		case '5':
			out.println("Get all Players");
			Scanner scan1 = new Scanner(str);
			scan1.next();
			int numP = scan1.nextInt();
			players = new Player[numP];
			for(int i = 0; i < numP; ++i){
				players[i] = new Player(scan1.next(), scan1.next().charAt(0));
			}
			break;
						
    	//END GAME
		case '6':
			out.println("Game over");
			Scanner scan2 = new Scanner(str);
			scan2.next();
			if(scan2.next().equals(p.getName()))
				out.println("YOU WIN!!!!");
			else
				out.println("loser....");
			scan2.close();
			//Kill socket stuff??
			System.exit(0);
    		break;
		}
		return '\0';
	}
	
	public static void main(String[] args){
		//Game Variables
		board = new Board();
		player = null;
	    playerNum = 0;
	    String recvdStr;
	    colors = new char[] {'b', 'r', 'y', 'g'};
	    
	    
	    //Make Welcome Screen
	    init = new startFrame("Welcome To Blokus");
	    init.setBackground(Color.BLACK);
	    init.setSize(600,600);
	    init.setVisible(true);
	    init.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    boolean running = false;
	    while(!init.getPlay()){
	    	if(!running){
	    		System.out.print("");
	    		running = true;
	    	}
	    }
	    
	    
	    //Create Client after Start button hit
	    theClient = new ClientServerSocket("192.168.56.1", 4000);
	    theClient.startClient();
	    
	    
	    
    	//0: Get response from server to get name
    	recvdStr = theClient.getResponse();
    	interpretResponse(recvdStr, theClient, board, player);  
    	//Send Init Player request and wait for response
    	textDial nameplayer;
    	String name;
		do{
	    	nameplayer = new textDial(init, "Name",
					"   Enter your name (no spaces allowed):  ");
	    	name = nameplayer.getText();
		}while(name.contains(" ") || name.isEmpty());
		theClient.sendName(name);
		waitingWin waiting = new waitingWin(init);
    	waiting.pack();
    	waiting.setSize(waiting.getWidth()+50, waiting.getHeight()-30);
        waiting.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	waiting.setVisible(true);
		
    	
    	//4: get player color
    	recvdStr = theClient.getResponse();
    	interpretResponse(recvdStr, theClient, board, player);
    	out.println(recvdStr.charAt(2));
    	switch (recvdStr.charAt(2)){
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

    	
    	//Initialize stuff
    	player = new Player(nameplayer.getText(), recvdStr.charAt(2));
    	board.printBoard();

    	
       	//5: get all player names from server
    	recvdStr = theClient.getResponse();
    	interpretResponse(recvdStr, theClient, board, player);
    	waiting.setVisible(false);
    	
    	
    	//Start game for each player
    	frame = new Frame("Blokus", players, playerNum, theClient);
    	frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        if(playerNum != 0){
        	frame.setPlayerTurn(false);
        }
        frame.setVisible(true);
        
        
        //Start actual game
      	while(true){
      		recvdStr = theClient.getResponse();
      		Blokus.interpretResponse(recvdStr, theClient, board, players[playerNum]);
      	}
	}
}
