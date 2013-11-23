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
	static Scanner in;
	static startFrame init;
	static Player players[];
	static char colors[];
	
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
			int piece, x, y;
			boolean loop = false;
			do{
				piece = in.nextInt();
				x = in.nextInt();
				y = in.nextInt();
				if(p.getScore() == 89)
					loop = b.validInit(x, y, p.getPiece(piece));
				else
					loop = b.validPlace(x, y, p.getPiece(piece), false);
			}while(!loop);
			b.placePiece(x, y, p.getPiece(piece));
			p.getPiece(piece).setPlaced();
			p.updateScore(p.getPiece(piece).getValue());
			c.sendMove(p.getPiece(piece).toString(x, y));
			b.printBoard();
    		break;
    	
    	//DO UPDATE
		case '3':
			out.println("Update board");
			String s[] = c.parseMove(str.substring(2));
			char color = s[0].charAt(0);
			int type = Integer.parseInt(s[1]);
			Piece curPiece = new Piece(type, color);
			int X = Integer.parseInt(s[2]);
			int Y = Integer.parseInt(s[3]);
			curPiece.setState(s[4]);
			b.placePiece(X, Y, curPiece);
			b.printBoard();
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
		in = new Scanner(System.in);
	    //Client Stuff
		ClientServerSocket theClient;
		//Game Variables
		Board board = new Board();
		Player player = null;
	    String recvdStr;
	    
	    colors = new char[] {'b', 'r', 'y', 'g'};
	    
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
	    //Create Client
	    theClient = new ClientServerSocket("192.168.1.213", 4000);
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
    	waiting.setSize(waiting.getWidth()+50, waiting.getHeight()+10);
        waiting.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	waiting.setVisible(true);
		
    	//4: get player color
    	recvdStr = theClient.getResponse();
    	interpretResponse(recvdStr, theClient, board, player);
    	out.println(recvdStr.charAt(2));
    	
    	System.out.println("before");
    
    	//Initialize stuff
    	player = new Player(nameplayer.getText(), recvdStr.charAt(2));
    	board.printBoard();
    	
    	System.out.println("before");
    	
    	
    	System.out.println("before");
    	//5: get all player names from server
    	recvdStr = theClient.getResponse();
    	interpretResponse(recvdStr, theClient, board, player);

    	waiting.setVisible(false);
    	
    	//Start game for each player
    	Frame frame = new Frame("Blokus", players);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    	
    	//Start actual game
    	while(true){
    		recvdStr = theClient.getResponse();
    		interpretResponse(recvdStr, theClient, board, player);
    	}
	 
	}
}
