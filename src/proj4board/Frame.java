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
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
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
										Math.min(SCREEN_HEIGHT, 500));
	final static int PLAYERWIDTH = GRIDSIZE/2;
	final static int N = 20;
	final static int SPACESIZE = GRIDSIZE/N;
	//Display
	JPanel playersPanel;
	JLayeredPane boardPanel;
	JPanel mainPanel;
	PiecePanel pieces;
	PiecePanel piecePanelArray[];	
	public User[] users;
	//Game Variables
	Board board;
	Player[] players;
	Player player;
	int playerNum;
	ClientServerSocket theClient;
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
		for(int i = 0; i < 4; ++i){
			try {
			p[i] = ImageIO.read(new File(getClass().getResource(
					"/images/Board/Avatars/" + 
					players[i].getName() + ".png").toURI()));
			}
			catch (Exception e) { 
				try{
					p[i] = ImageIO.read(new File(getClass().getResource(
						"/images/Board/Avatars/blank.png").toURI()));
				}
				catch (Exception ee) {
					System.out.println("Problem finding avatar");
					p[i] = null;
				}
			}
		}
		
		for(int i = 0; i < players.length; ++i){
	        users[i] = new User(players[i].getName(), p[i], colors[i]);
	        playersPanel.add(users[i]);
	    }
		//********THIS LINE SHOULDN'T BE HERE
		//users[turn].setBorder(new LineBorder(Color.WHITE, 3));
		
		
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
	    if(theClient == null){
	    	piecePanelArray = new PiecePanel[players.length];
	    	for(int i = 0; i < players.length; ++i){
	    		piecePanelArray[i] = new PiecePanel(this, i);
	    	}
	    	pieces = piecePanelArray[0];
	    	users[turn].setBorder(new LineBorder(Color.WHITE, 3));
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
		this.setContentPane(mainPanel);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

		
		//Adding Key Stroke Listeners
		ActionMap actionMap = mainPanel.getActionMap();
        InputMap inputMap = mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        //Rotate Counter
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LEFT");
        actionMap.put("LEFT", new AbstractAction() {
        	public void actionPerformed(ActionEvent arg0) {
        		pieces.rccButton.doClick();
        	}
        });
        //Rotate Clockwise
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RIGHT");
        actionMap.put("RIGHT", new AbstractAction() {
        	public void actionPerformed(ActionEvent arg0) {
        		pieces.rcButton.doClick();
        	}
        });
        //Flip
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UP");
        actionMap.put("UP", new AbstractAction() {
        	public void actionPerformed(ActionEvent arg0) {
        		pieces.flipButton.doClick();
        	}
        });
        //ENTER
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER");
        actionMap.put("ENTER", new AbstractAction() {
        	public void actionPerformed(ActionEvent arg0) {
        		if(pieces.submitButton.isEnabled()){
        			pieces.submitButton.doClick();
        		}
        	}
        });
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
