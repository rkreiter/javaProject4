package proj4board;

import game.*;
import proj4board.PiecePanel;
import server.Blokus;
import server.ClientServerSocket;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Frame extends JFrame { 
	final static int GRIDSIZE =  680;
	final static int PLAYERWIDTH = 300;
	final static int N = 20;
	final static int SPACESIZE = GRIDSIZE/N;
	JPanel playersPanel;
	JLayeredPane boardPanel;
	PiecePanel pieces;
	Board board;
	Player[] players;
	User[] users;
	int playerNum;
	ClientServerSocket theClient;

	public Frame(String title, Player[] playersArray, int playerNum, ClientServerSocket theClient) {
		//Initialize
		super(title);
		setLayout(new FlowLayout());
		board = new Board();
		this.players = playersArray;
		this.playerNum = playerNum;
		this.theClient = theClient;
		
		
		//Create Player Panel
		playersPanel = new JPanel(new GridLayout(4,1));
		playersPanel.setPreferredSize(new Dimension(PLAYERWIDTH,GRIDSIZE));
		BufferedImage p[] = new BufferedImage[4];
		Color[] colors = {Color.BLUE,Color.RED,Color.YELLOW, Color.GREEN};
		users = new User[4];
		try {
			p[0] = ImageIO.read(new File("src/images/Board/Avatars/Stephen.png"));
			p[1] = ImageIO.read(new File("src/images/Board/Avatars/Kyle.png"));
			p[2] = ImageIO.read(new File("src/images/Board/Avatars/Troy.png"));
			p[3] = ImageIO.read(new File("src/images/Board/Avatars/Asher.png"));
		}
		catch (IOException e){ System.exit(10);}
		for(int i = 0; i < players.length; ++i){
	        users[i] = new User(players[i].getName(), p[i], colors[i]);
	        playersPanel.add(users[i]);
	    }
		
		
		
		//Create Board Panel
		boardPanel = new JLayeredPane();
		boardPanel.setPreferredSize(new Dimension(GRIDSIZE, GRIDSIZE));
		ImageIcon grid = new ImageIcon("src/images/Board/Grid.png");
	    JLabel gridholder = new JLabel(grid);
	    gridholder.setSize(GRIDSIZE, GRIDSIZE);
	    boardPanel.add(gridholder, JLayeredPane.DEFAULT_LAYER);
	    
	    
	    
		//Create Pieces Panel
		pieces = new PiecePanel(this);
    
		
		
		//Merge panels together
		JPanel mainPanel = new JPanel(new FlowLayout());        
		mainPanel.add(playersPanel);
		mainPanel.add(boardPanel);
		mainPanel.add(pieces);
		mainPanel.setBackground(Color.DARK_GRAY);
		add(mainPanel);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	public void placePieceOnBoard(String moveArray[]){
		char color = moveArray[0].charAt(0);
		int type = Integer.parseInt(moveArray[1]);
		Piece curPiece = new Piece(type, color);
		int X = Integer.parseInt(moveArray[2]);
		int Y = Integer.parseInt(moveArray[3]);
		curPiece.setState(moveArray[4]);

		ImageDrag currentPiece = new ImageDrag(curPiece, SPACESIZE, board, null, null);
	    currentPiece.setSize(GRIDSIZE, GRIDSIZE);
	    boardPanel.add(currentPiece, JLayeredPane.DRAG_LAYER);
	    currentPiece.setLocation(Y * SPACESIZE, X * SPACESIZE);
	    currentPiece.finalize();
		
		board.placePiece(X, Y, curPiece);
		board.printBoard();
	}

	public void setPlayerTurn(boolean bool){
		pieces.submitButton.setEnabled(false);
		for(int i = 0; i < 21; ++i){
			pieces.clickables[i].setEnabled(bool);
		}
		pieces.currentPiece = null;
	}
}
