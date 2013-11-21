package server;

import static java.lang.System.out;
import game.*;

public class StartClient{
	
	public static void main(String[] args){
	    //Client Stuff
		ClientServerSocket theClient;
		//Game Variables
		Board b;
		Player player;
	    String recvdStr;
	    //Piece piece;
	    
	    
	    
	    //Create Client
	    theClient = new ClientServerSocket("192.168.1.213", 2333);
	    theClient.startClient();
	    
	    
	    
	    
	    //Send number of players to server
	    theClient.sendString("2");
	    
	    
	    
	    
	    //Send Init request and wait for response
	    theClient.sendString("Kyle");
	    recvdStr = theClient.recvString();
	    
	    
	    //Initialize stuff
	    b = new Board();
	    player = new Player("Kyle", recvdStr.charAt(0));
	    b.printBoard();
	    out.print(player);
	}
}