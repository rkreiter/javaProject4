package proj4board;

import game.Board;
import game.Piece;
import game.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import server.ClientServerSocket;

public class Frame extends JFrame { 
	//Constants
	final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final static int SCREEN_WIDTH = (int) screenSize.getWidth();
	final static int SCREEN_HEIGHT = (int) screenSize.getHeight();
	final static int GRIDSIZE =  (int) Math.min(SCREEN_WIDTH/2, 
										Math.min(SCREEN_HEIGHT, 600));
	final static int PLAYERWIDTH = GRIDSIZE/2;
	final static int N = 20;
	final static int SPACESIZE = GRIDSIZE/N;
	//Display
	JPanel playersPanel;
	JLayeredPane boardPanel;
	JPanel mainPanel;
	PiecePanel pieces;
	PiecePanel piecePanelArray[];
//MAY CHANGE THIS TO GETTER AND SETTER	
	public User[] users;
	//Game Variables
	Board board;
	Player[] players;
	Player player;
	int playerNum;
	ClientServerSocket theClient;
	boolean local;
//MAY CHANGE THIS TO GETTER AND SETTER
	public int turn;
	
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
		turn = 0;
		if(theClient == null)
			local = true;
		else
			local = false;
		
		
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
			p[0] = ImageIO.read(new File(getClass().getResource(
							"/images/Board/Avatars/Stephen.png").toURI()));
			p[1] = ImageIO.read(new File(getClass().getResource(
							"/images/Board/Avatars/Kyle.png").toURI()));
			p[2] = ImageIO.read(new File(getClass().getResource(
							"/images/Board/Avatars/Troy.png").toURI()));
			p[3] = ImageIO.read(new File(getClass().getResource(
							"/images/Board/Avatars/Asher.png").toURI()));
		}
		catch (Exception e){ System.exit(10);}
		for(int i = 0; i < players.length; ++i){
	        users[i] = new User(players[i].getName(), p[i], colors[i]);
	        if(i == 0){
	        }
	        playersPanel.add(users[i]);
	    }
		users[turn].setBorder(new LineBorder(Color.WHITE, 3));
		
		
		//Create Board Panel
		boardPanel = new JLayeredPane();
		boardPanel.setPreferredSize(new Dimension(GRIDSIZE, GRIDSIZE));
		Image grid = null;
		try {
			grid = ImageIO.read(new File(getClass().getResource(
					"/images/Board/Grid.png").toURI()));
		}
		catch (Exception e){ System.exit(10);}
		grid = grid.getScaledInstance(GRIDSIZE, GRIDSIZE, Image.SCALE_SMOOTH);
	    JLabel gridholder = new JLabel(new ImageIcon(grid));
	    gridholder.setSize(GRIDSIZE, GRIDSIZE);
	    boardPanel.add(gridholder, JLayeredPane.DEFAULT_LAYER);
	    
	    
	    
		//Create Pieces Panel
	    if(local){
	    	piecePanelArray = new PiecePanel[players.length];
	    	for(int i = 0; i < players.length; ++i){
	    		piecePanelArray[i] = new PiecePanel(this, i);
	    	}
	    	pieces = piecePanelArray[0];
	    }
	    else{
	    	pieces = new PiecePanel(this, playerNum);
	    }
		
		
		
		//Merge panels together
		mainPanel = new JPanel(new FlowLayout());        
		mainPanel.add(playersPanel);
		mainPanel.add(boardPanel);
		mainPanel.add(pieces);
		mainPanel.setBackground(Color.DARK_GRAY);
		add(mainPanel);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
	}
	
	public void placePieceOnBoard(String move){
		String moveArray[] = new String[6];
		Scanner scan = new Scanner(move);
		for(int i = 0; i < 6; ++i){
			moveArray[i] = scan.next();
		}
		scan.close();
		
		char color = moveArray[0].charAt(0);
		int type = Integer.parseInt(moveArray[1]);
		Piece curPiece = new Piece(type, color);
		int X = Integer.parseInt(moveArray[2]);
		int Y = Integer.parseInt(moveArray[3]);
		//curPiece.setState(moveArray[4]);
		int state = Integer.parseInt(moveArray[5]);
		
		//Local Player
		Player updatePlayer;
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
		updatePlayer = players[num];
		
		
		ImageDrag currentPiece = new ImageDrag(curPiece, SPACESIZE, board, null, null);
	    currentPiece.setSize(GRIDSIZE, GRIDSIZE);
	    boardPanel.add(currentPiece, JLayeredPane.DRAG_LAYER);
	    currentPiece.setLocation(Y * SPACESIZE, X * SPACESIZE);
	    switch (state){
	    case 0:
			break;
		case 1:
			currentPiece.rotateClockwise();
			break;
		case 2:
			currentPiece.rotateClockwise();
			currentPiece.rotateClockwise();
			break;
		case 3:
			currentPiece.rotateCounterClockwise();
			break;
		case 4:
			currentPiece.flip();
			currentPiece.rotateClockwise();
			currentPiece.rotateClockwise();
			break;
		case 5:
			currentPiece.flip();
			currentPiece.rotateCounterClockwise();
			break;
		case 6:
			currentPiece.flip();
			break;
		case 7:
			currentPiece.flip();
			currentPiece.rotateClockwise();
			break;
	    }
	    currentPiece.setToState(currentPiece.piece.getState());
	    currentPiece.updatePieceHelper();
	    currentPiece.finalize();
		
		board.placePiece(X, Y, curPiece);
		board.printBoard();
		
		updatePlayer.updateScore(curPiece.getValue());
		users[num].score.setText(String.valueOf(updatePlayer.getScore()));
	}

	public void setPlayerTurn(boolean bool, int playernum){
	  pieces.submitButton.setEnabled(false);
		for(int i = 0; i < 21; ++i){
			pieces.clickables[i].setEnabled(bool);
		}
		pieces.currentPiece = null;
	}
}
