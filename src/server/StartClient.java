package server;

import static java.lang.System.out;
import game.*;

public class StartClient{
	
	public static void interpretResponse(String str, ClientServerSocket c,
			Board b, Player p){
		switch (str.charAt(0)){
    	//OK
		case '0':
			out.println("OK");
    		break;

    	//SEND NUMBER
   		//This will make a pop up for player in gui
   	    //Send number of players to server
		case '1':
			out.println("Sending Num Players");
    	    c.sendString("1", 0);
    	    String recvdStr = c.getResponse();
    	    interpretResponse(recvdStr, c, b, p);
    		break;
    	
    	//SEND MOVE
		case '2':
			out.println("Send server move");
    		break;
    	
    	//DO UPDATE
		case '3':
			out.println("Update board");
			//Piece p;
    		break;
    	
    	//END GAME
		case '4':
			out.println("Game over");
			System.exit(0);
    		break;
		}
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
	    player = new Player("Asher", recvdStr.charAt(0));
	    board.printBoard();
	    out.print(player);
	    
	    //Start actual game
	    while(true){
	    	recvdStr = theClient.getResponse();
	    	interpretResponse(recvdStr, theClient, board, player);
	    }
	}
}
