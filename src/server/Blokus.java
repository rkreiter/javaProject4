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
import javax.swing.border.LineBorder;

public class Blokus{
	static startFrame init;
	static Frame frame;
	static WaitingWindow waiting;
	static LoginDialog login;
	public static Board board;
	public static Player players[] = null;
	public static Player player;
	public static int playerNum = -1;
	public static int numPlayers;
	public static ClientServerSocket theClient;
	public static boolean playable = true;

	public static void main(String[] args) throws IOException{
		//Game Variables
		board = new Board();
		player = null;
	    playerNum = 0;
	    String recvdStr;
	    
	    //Make Welcome Screen
	    init = new startFrame("Welcome To Blokus");
	    init.setVisible(true);
	    
	    //wait for either game type to be ran
	    boolean running = false;
	    while(init.getPlay() == 'n'){
	    	if(!running){
	    		System.out.print("");
	    		running = true;
	    	}
	    }
	    //If local play can stop here
	    if(init.getPlay() == 'l'){
	    	return;
	    }
	    
	    //Create Client after Start button hit
	    //*********MAY ALSO HAVE USER GIVE PORT NUM AT SOME POINT**************
	    theClient = new ClientServerSocket(init.getIP(), 4000);
	    theClient.startClient();
	    //Create login but don't use yet
	    login = new LoginDialog(init, "Login", "   Enter username: ", theClient);
	    
	    waiting = new WaitingWindow(init);
    	waiting.pack();
    	waiting.setSize(waiting.getWidth()+50, waiting.getHeight()-30);
        waiting.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	waiting.setVisible(true);
	    
        //Start actual game
    	int count = 0;
      	try{
	        while(playable){
	        	count++;
	      		recvdStr = theClient.getResponse();
	      		interpretResponse(recvdStr);
      		}
      	}
      	catch(Exception e){
      		System.out.println("Server has given client some sort of problem");
      		System.out.println(e);
      		System.out.println("Request number: " + count);
      		new ErrorWindow(frame, "Server has broken");
      		System.exit(0);
      	}
	}
	
	//Helper Function
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
				TextDialog numPlayersDialog;
				do{
					numPlayersDialog = new TextDialog(init, "Number Of Players",
							"   Enter the number of players (1-4): ");
					num = numPlayersDialog.getText();
				}while(!(num).matches("[1-4]"));
				theClient.sendString(num, 0);
	    		break;
	    	
	    	//SEND MOVE
			case '2':
				out.println("Send server move");
				frame.setPlayerTurn(true, playerNum);
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
				out.println(str);
				Scanner scan1 = new Scanner(str);
				scan1.next();
				numPlayers = scan1.nextInt();
				players = new Player[numPlayers];
				int winArray[] = new int[numPlayers];
				int loseArray[] = new int[numPlayers];
				double avgArray[] = new double[numPlayers];
				for(int i = 0; i < numPlayers; ++i){
					players[i] = new Player(scan1.next(), scan1.next().charAt(0));
					winArray[i] = (int) scan1.nextDouble();
					loseArray[i] = (int) scan1.nextDouble();
					avgArray[i] = scan1.nextDouble();
				}
				scan1.close();
				player = players[playerNum];
		    	board.printBoard();
		    	
		    	//Start game for each player
		    	frame = new Frame("Blokus", board, players, player, playerNum, theClient);
		    	frame.getContentPane().setBackground(Color.DARK_GRAY);
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		        frame.pack();

		        //Display all players stats
		        for(int i = 0; i < numPlayers; ++i){
		        	frame.users[i].wins.setText("Wins: " + winArray[i]);
					frame.users[i].loses.setText("Loses: " + loseArray[i]);
					frame.users[i].avg.setText("Average Score: " + avgArray[i]);	
		        }
		        
		        if(playerNum != 0){
		        	frame.setPlayerTurn(false, playerNum);
		        }
		        waiting.setVisible(false);
		        frame.setVisible(true);
				break;
							
	    	//END GAME
			case '6':
				out.println("Game over");
				Scanner scan2 = new Scanner(str);
				scan2.next();
				EndWindow end;
				if(scan2.next().equals(player.getName())){
					out.println("YOU WIN!!!!");
					end = new EndWindow(frame, 'w');
					end.pack();
			    	end.setSize(end.getWidth()+50, end.getHeight());
				}
				else{
					out.println("loser....");
					end = new EndWindow(frame, 'l');
					end.pack();
			    	end.setSize(end.getWidth()+50, end.getHeight()+10);
				}
		    	end.setVisible(true);
		    	frame.setVisible(false);
				scan2.close();
				theClient.closeConnection(0);
				playable = false;
	    		System.exit(0);
	    	
	    	//GIVEN WHICH PLAYERS TURN IT IS
			case '7':
				int temp = Integer.parseInt(str.substring(2));
				out.println("Player Turn number: " + temp);
				frame.users[frame.turn].setBorder(new LineBorder(Color.DARK_GRAY, 3));
				frame.turn = temp;
				frame.users[frame.turn].setBorder(new LineBorder(Color.WHITE, 3));
				break;
				
			//ASKED TO SEND A VALID LOGIN
			case '8':
				System.out.println(str);
				switch(str.charAt(2)){
					case '0':
						break;
					case '1':
						new ErrorWindow(init, "Invalid Username/Password!");
						break;
					case '2':
						new ErrorWindow(init, "Username Taken");
						break;
				}
				out.println("Login Prompt");
				login.setVisible(true);
				break;
		}
		return '\0';
	}
}
