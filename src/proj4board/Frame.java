package proj4board;

import game.*;
import proj4board.PiecePanel;
import server.ClientServerSocket;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class Frame extends JFrame { 
	//Constants
	final static int GRIDSIZE =  680;
	final static int PLAYERWIDTH = 300;
	final static int N = 20;
	final static int SPACESIZE = GRIDSIZE/N;
	//Display
	JPanel playersPanel;
	JLayeredPane boardPanel;
	PiecePanel pieces;
	User[] users;
	//Game Variables
	Board board;
	Player[] players;
	Player player;
	int playerNum;
	ClientServerSocket theClient;
	
	public Frame(String title, Board board, Player[] players, Player player, 
			int playerNum, ClientServerSocket theClient){
		//Initialize
		super(title);
		setLayout(new FlowLayout());
		this.board = board;
		this.players = players;
		this.player = player;
		this.playerNum = playerNum;
		this.theClient = theClient;
		
		
		//Create Player Panel
		playersPanel = new JPanel(new GridLayout(4,1));
		playersPanel.setPreferredSize(new Dimension(PLAYERWIDTH,GRIDSIZE));
		
    Border bord1, bord2, finalborder;
    bord1 = new CompoundBorder(
        BorderFactory.createMatteBorder(0, 5, 0, 0, Color.BLUE),
        BorderFactory.createMatteBorder(5, 0, 0, 0, Color.RED));
    bord2 = new CompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 0, 5, Color.YELLOW),
        BorderFactory.createMatteBorder(0, 0, 5, 0, Color.GREEN));
    finalborder = new CompoundBorder(bord1, bord2);
		
    playersPanel.setBorder(finalborder);
    playersPanel.setBackground(Color.DARK_GRAY.darker());
    
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
		
		int num = 0;
		switch(color){
			case 'b':
				num = 0;
				break;
			case 'r':
				num = 1;
				break;
			case 'y':
				num = 2;
				break;
			case 'g':
				num = 3;
				break;
		}
		players[num].updateScore(curPiece.getValue());
		users[num].score.setText(String.valueOf(players[num].getScore()));
	}

	public void setPlayerTurn(boolean bool){
		pieces.submitButton.setEnabled(false);
		for(int i = 0; i < 21; ++i){
			pieces.clickables[i].setEnabled(bool);
		}
		pieces.currentPiece = null;
	}
}
