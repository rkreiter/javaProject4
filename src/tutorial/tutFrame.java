package tutorial;

import game.Board;
import game.Piece;
import game.Player;
import server.ClientServerSocket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;


public class tutFrame extends JFrame { 
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
	JPanel outter;
	static JButton next; 
	static JTextArea text;
	PiecePanel pieces;
	PiecePanel piecePanelArray[];	
	public User[] users;
	//Game Variables
	public Board board;
	Player[] players;
	Player player;
	int playerNum;
	ClientServerSocket theClient;
	public int turn;
	public static int tutcount = -1;
	tutFrame main = this;
	
	public tutFrame(String title, Board board, Player[] players, Player player, 
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
		ImageIcon icon;
		Image img;
		for(int i = 0; i < 4; ++i){
			try {
			icon = new ImageIcon(getClass().getResource(
						"/images/Board/Avatars/" + players[i].getName() + ".png"));
			img = icon.getImage();
			p[i] = (BufferedImage) img;
			}
			catch (Exception e) { 
				try{
					icon = new ImageIcon(getClass().getResource(
							"/images/Board/Avatars/blank.png"));
					img = icon.getImage();
					p[i] = (BufferedImage) img;
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
	    text = new JTextArea("start");
	    setPlayerTurn(false, 0);
	    text.setLineWrap(true);
	    text.setWrapStyleWord(true);
	    text.setEditable(false);
	    text.setOpaque(false);
	    text.setForeground(Color.WHITE);
	    text.setColumns(50);
	    text.setBackground(Color.DARK_GRAY);
	    next = new JButton("Next");
	    gotoNext(main);
	    next.addActionListener(new nextList());
	    JPanel butt = new JPanel(new FlowLayout());
	    butt.add(next);
	    butt.setSize(40, 10);
	    butt.setOpaque(false);
	    outter = new JPanel(new BorderLayout());
		mainPanel = new JPanel(new FlowLayout());
		mainPanel.add(playersPanel);
		mainPanel.add(boardPanel);
		mainPanel.add(pieces);
		mainPanel.setBackground(Color.DARK_GRAY);
		outter.add(text, BorderLayout.NORTH);
		outter.add(mainPanel, BorderLayout.CENTER);
		outter.add(butt, BorderLayout.SOUTH);
		outter.setBackground(Color.DARK_GRAY);
		this.setContentPane(outter);
		this.pack();
		this.setSize(this.getWidth()-100, this.getHeight()-200);

		
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
	
	public class nextList implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			switch(tutcount){
    		case 0:
    			placePieceOnBoard("b 10 0 0 0 0");
    			setPlayerTurn(false, 0);
    			break;
    		case 2:
    			placePieceOnBoard("b 9 0 4 0 0");
    			setPlayerTurn(false, 0);
    			break;
    		default:
    			System.out.println("OOPS");
    			break;
    		}
			Player tempPlayer;
			tempPlayer = main.players[main.turn];
			main.users[main.turn].score.setText(String.valueOf(player.getScore()));
			main.users[main.turn].setBorder(new LineBorder(Color.DARK_GRAY, 3));
    		//Find next player turn
	    	do{
	    		main.turn = (main.turn + 1) % main.players.length;
	    		tempPlayer = main.players[main.turn];
	    		if(tempPlayer.isPlayable()){
	    			if(!board.playerCanPlay(tempPlayer))
	    				tempPlayer.setPlayable(false);
	    		}
	    	} while(!tempPlayer.isPlayable());
	    	main.users[main.turn].setBorder(new LineBorder(Color.WHITE, 3));
			
			//Switch panel
	    	tutFrame.gotoNext(main);
	    	main.mainPanel.remove(main.pieces);
			main.pieces = main.piecePanelArray[main.turn];
			main.mainPanel.add(main.pieces);
			main.outter.add(tutFrame.text, BorderLayout.NORTH);
			main.outter.add(main.mainPanel, BorderLayout.CENTER);
			main.setContentPane(main.outter);
		}
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
	public static void gotoNext(tutFrame frame){
		tutcount++;
		switch(tutcount){
		case 0:
			System.out.println("case 0");
			text.setText("The blue player always goes first, the first move\n" +
						"for each player must start in that players corner\n" + 
						"(marked by a translucent square of their color)\n" +
						"Press the 'Next' button to continue");
			next.setEnabled(true);
			break;
		case 1:
			System.out.println("case 1");
			text.setText("Now it is your turn (notice the white border around\n"+
						" your panel on the left), click any piece from the\n" +
						"panel on the right and place it one the board, when\n" +
						"your move is valid the piece will turn from \n" +
						"translucent to opaque, Press the Enter button or the\n"+
						" 'Enter' key to sumbit your move\n" +
						"(Notice how blue's score was decremented by 5 since\n"+
						"the piece played had 5 units)");
			next.setEnabled(false);
			break;
		case 2:
			System.out.println("case 2");
			text.setText("Turns will always go blue, red, yellow, then green,\n"+
						" since there is only two players right now the order\n"+
						" goes blue, red, blue, red...\n" +
						"Each piece a player plays must touch one of their\n" +
						"existing piece's corners but not an existing piece\n"+
						"of the same color's edge\n" +
						"Press the 'Next' button to continue");
			next.setEnabled(true);
			break;
		case 3:
			System.out.println("case 3");
			text.setText("Now it's your turn again! Try to place another one\n"+
						"of your pieces on the board, the piece will become\n" +
						"opaque when it is in a valid spot");
			next.setEnabled(false);
			break;
		case 4:
			System.out.println("case 4");
			frame.setPlayerTurn(false, 0);
			frame.setPlayerTurn(true, 1);
			text.setText("In this case you can see how your pieces can\n" + 
						"interact with the opponents pieces, where your\n" +
						"pieces can only touch by the corners to your \n" +
						"other pieces, your pieces can touch opponent \n" +
						"pieces on any side/edge\nTry to place " +
						"another piece given this board");
			next.setEnabled(false);
			break;
		case 5:
			System.out.println("case 5");
			text.setText("When a player is out of moves the game will \n" + 
						"automatically skip you until all other players\n" +
						"also run of of moves");
			next.setEnabled(true);
			break;
		default:
			System.out.println("case D");
			text.setText("This is awkward....");
			next.setEnabled(true);
			break;
		}
		text.setBorder(new LineBorder(Color.WHITE, 3));
	}
}
