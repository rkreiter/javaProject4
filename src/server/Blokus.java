package server;

import static java.lang.System.out;
import game.Board;
import game.Piece;
import game.Player;
import intro.startFrame;

import java.awt.Color;
import java.util.Scanner;

import javax.swing.JFrame;

public class Blokus{
	static Scanner in;
	
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
			
    	    c.sendString("2", 0);  //This will be replaced by a gui
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
    	
    	//END GAME
		case '5':
			out.println("Game over");
			Scanner scan = new Scanner(str);
			scan.next();
			if(scan.next().equals(p.getName()))
				out.println("YOU WIN!!!!");
			else
				out.println("loser....");
			scan.close();
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
	    
	    startFrame init = new startFrame("Welcome To Blokus");
	    init.setBackground(Color.BLACK);
	    init.setSize(600,600);
	    init.setVisible(true);
	    init.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Create Client
	    theClient = new ClientServerSocket("192.168.1.243", 4000);
	    theClient.startClient();
	    
	    //Get response from server
	    recvdStr = theClient.getResponse();
	    interpretResponse(recvdStr, theClient, board, player);  
	    
	    //Send Init Player request and wait for response
	    theClient.sendName("Asher");
	    recvdStr = theClient.getResponse();
	    interpretResponse(recvdStr, theClient, board, player);
	    out.println(recvdStr.charAt(2));
	    
	    //Initialize stuff
	    player = new Player("Asher", recvdStr.charAt(2));
	    board.printBoard();
	    
	    //Start actual game
	    while(true){
	    	recvdStr = theClient.getResponse();
	    	interpretResponse(recvdStr, theClient, board, player);
	    }
	}
}
