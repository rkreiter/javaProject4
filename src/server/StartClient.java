package server;

import static java.lang.System.out;

import java.util.Scanner;

import game.*;

public class StartClient{
	
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
    	    c.sendString("1", 0);  //This will be replaced by a gui
    	    String recvdStr = c.getResponse();
    	    interpretResponse(recvdStr, c, b, p);
    		break;
    	
    	//SEND MOVE
		case '2':
			Scanner in = new Scanner(System.in);
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
			in.close();
			b.placePiece(x, y, p.getPiece(piece));
			p.getPiece(piece).setPlaced();
			p.updateScore(p.getPiece(piece).getValue());
			c.sendMove(p.getPiece(piece).toString(x, y));
			out.println("Send server move");
    		break;
    	
    	//DO UPDATE
		case '3':
			String s[] = c.parseMove(str);
			char color = s[0].charAt(0);
			int type = Integer.parseInt(s[1]);
			Piece curPiece = new Piece(type, color);
			int X = Integer.parseInt(s[2]);
			int Y = Integer.parseInt(s[3]);
			curPiece.setState(s[4]);
			b.placePiece(X, Y, curPiece);
			out.println("Update board");
    		break;
    	
    	//GIVEN PLAYER INFO
		case '4':
			out.println("Got Color!");
			break;
    	
    	//END GAME
		case '5':
			Scanner scan = new Scanner(str);
			scan.next();
			if(scan.next().equals(p.getName()))
				out.println("YOU WIN!!!!");
			else
				out.println("loser....");
			out.println("Game over");
			scan.close();
			//Kill socket stuff??
			System.exit(0);
    		break;
		}
		return '\0';
	}
	
	public static void main(String[] args){
	    //Client Stuff
		ClientServerSocket theClient;
		//Game Variables
		Board board = new Board();
		Player player = null;
	    String recvdStr;
	    
	    //Create Client
	    theClient = new ClientServerSocket("67.194.56.42", 4000);
	    theClient.startClient();
	    
	    //Get response from server
	    recvdStr = theClient.getResponse();
	    interpretResponse(recvdStr, theClient, board, player);  
	    
	    //Send Init Player request and wait for response
	    theClient.sendName("ASHER");
	    recvdStr = theClient.getResponse();
	    interpretResponse(recvdStr, theClient, board, player);
	    
	    //Initialize stuff
	    player = new Player("Asher", recvdStr.charAt(2));
	    board.printBoard();
	    out.print(player);
	    
	    //Start actual game
	    while(true){
	    	recvdStr = theClient.getResponse();
	    	interpretResponse(recvdStr, theClient, board, player);
	    }
	}
}
