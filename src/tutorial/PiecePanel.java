package tutorial;

import game.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class PiecePanel extends JPanel {
	//Constants
	final int GRIDSIZE = tutFrame.GRIDSIZE;
	final int PLAYERWIDTH = tutFrame.PLAYERWIDTH;
	final int N = tutFrame.N;
	final int SPACESIZE = GRIDSIZE/N;
	//Variables for Frame
	String color;
	Piece[] pieces;
	JRadioButton[] clickables;
	ImageDrag currentPiece;
	int currentPieceNum;
	JLayeredPane boardPanel;
	JPanel piecesPanel;
	JButton submitButton;
	tutFrame frame;
	Image im[];
	JButton rcButton;
	JButton rccButton;
	JButton flipButton;
	JPanel buttonPanel;
	//Game stuff
	Board board;
	Player player;
	Piece piece;
	
	public PiecePanel(tutFrame frame, int x) {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(PLAYERWIDTH, GRIDSIZE));
		this.setBackground(Color.DARK_GRAY.darker());

		//Initialize variables
		boardPanel = frame.boardPanel;
		this.board = frame.board;
		this.player = frame.players[x];
		this.frame = frame;
		currentPiece = null;
	  
		//Set the border of the panel
		Border bord1, bord2, finalborder;
		bord1 = new CompoundBorder(
				BorderFactory.createMatteBorder(0, 5, 0, 0, Color.BLUE),
				BorderFactory.createMatteBorder(5, 0, 0, 0, Color.RED));
		bord2 = new CompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 5, Color.YELLOW),
				BorderFactory.createMatteBorder(0, 0, 5, 0, Color.GREEN));
		finalborder = new CompoundBorder(bord1, bord2);
		this.setBorder(finalborder);
	  
		//Set color string to grab the images
		switch (player.getColor()) {
			case 'b':
				color = "Blue";
				break;
			case 'r':
				color = "Red";
				break;
			case 'y':
				color = "Yellow";
				break;
			case 'g':
				color = "Green";
				break;		
		}
		
		//Grab all the images for that player based on color
		im = new Image[21];
		for (int i = 0; i < 21; ++i) {
			try {
				im[i] = ImageIO.read(new File(getClass().getResource("/images/" + color 
						+ "/" + String.valueOf(i) + "S.png").toURI()));
			}
			catch (Exception e){ 
			  System.out.println("Stuff");
			  System.exit(10); }
		}
		
		//Add all the images to the panel
		piecesPanel = new JPanel(new FlowLayout());
		piecesPanel.setBackground(Color.DARK_GRAY.darker());
		pieces = new Piece[21];
		clickables = new JRadioButton[21];
		int w, h;
		ImageIcon icon = null;
		ButtonListener clicked = new ButtonListener();
		
		for (int i = 0; i < 21; ++i) {		  
			pieces[i] = player.getPiece(i);
			w = (int) (0.7 * (pieces[i].getWidth() * SPACESIZE));
			h = (int) (0.7 * (pieces[i].getHeight() * SPACESIZE + 1));
			im[i] = im[i].getScaledInstance(w, h, BufferedImage.SCALE_DEFAULT);
			icon = new ImageIcon(im[i]);
			clickables[i] = new JRadioButton(icon);
			clickables[i].setOpaque(false);
			clickables[i].addActionListener(clicked);
			piecesPanel.add(clickables[i]);
		}
		this.add(piecesPanel);
		
		//Rotate Buttons!
		rcButton = new JButton("<html><center>Rotate<p>Right(&gt)</center></html>");
		rccButton = new JButton("<html><center>Rotate<p>Left(&lt)</center></html>");
		flipButton = new JButton("Flip(^)");
		buttonPanel = new JPanel(new GridLayout(1,3));
		buttonPanel.setBackground(Color.DARK_GRAY.darker());
		buttonPanel.add(rccButton, BorderLayout.SOUTH);
		buttonPanel.add(flipButton, BorderLayout.SOUTH);
		buttonPanel.add(rcButton, BorderLayout.SOUTH);
		
		rcButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(currentPiece != null){
					currentPiece.ImageDragRC();
				}
			}
		});
		
		rccButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(currentPiece != null){
					currentPiece.ImageDragRCC();
				}
			}
		});
		
		flipButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(currentPiece != null){
					currentPiece.ImageDragFlip();
				}
			}
		});
		
		
		submitButton = new JButton("Submit Move!(ENTER)");
		submitButton.addActionListener(new SubmitListener());
		submitButton.setEnabled(false);
		
		JPanel two = new JPanel(new GridLayout(2,1));
		two.setBackground(Color.DARK_GRAY.darker());
		two.add(buttonPanel);
		two.add(submitButton);
		
		this.add(two, BorderLayout.SOUTH);
	}
	
	public class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			for(int i = 0; i < clickables.length; ++i){
				if(e.getSource() == clickables[i]){
					System.out.println("Piece Num Clicked: " + i);
					if(currentPiece != null){
						boardPanel.remove(currentPiece);
						clickables[currentPieceNum].setVisible(true);
						currentPiece.reset();
						currentPiece = null;
					}
					piece = pieces[i];
				    currentPiece = new ImageDrag(piece, SPACESIZE, board, player, submitButton);
				    currentPiece.setSize(GRIDSIZE, GRIDSIZE);
				    boardPanel.add(currentPiece, JLayeredPane.DRAG_LAYER);
				    boardPanel.moveToFront(currentPiece);
				    currentPieceNum = i;
				    clickables[i].setVisible(false);
				    break;
				}
			}
		}
	}
	
  	public class SubmitListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			tutFrame.gotoNext(frame);
			
			System.out.println(tutFrame.tutcount);
			switch(tutFrame.tutcount){
    		case 1:
    			break;
    		case 3:
    			break;
    		case 4:
    			System.out.println("doing stuffs");
    			frame.board = new Board();
	    		Player p1 = new Player("Com", 'b');
	    		Player p2 = new Player("YOU", 'r');
	    		frame.players[0] = p1;
	    		frame.players[1] = p2;
	    		frame.placePieceOnBoard("b 10 0 0 0 0");
	    		//frame.setPlayerTurn(false, 0);
    			break;
    		default:
    			System.out.println("sOOPS");
    			break;
    		}
    		if(currentPiece != null){
    			submitButton.setEnabled(false);
				int X = currentPiece.xVal;
				int Y = currentPiece.yVal;
				currentPiece.finalize();
				board.placePiece(X, Y, piece);
				piece.setPlaced();
				player.updateScore(piece.getValue());
				frame.users[frame.playerNum].score.setText(String.valueOf(player.getScore()));
				board.printBoard();
				currentPiece = null;
				
				Player tempPlayer;
				tempPlayer = frame.players[frame.turn];
				//Update score
				frame.users[frame.turn].score.setText(String.valueOf(player.getScore()));
				frame.users[frame.turn].setBorder(new LineBorder(Color.DARK_GRAY, 3));
		    	
				//Find next player turn
		    	do{
		    		frame.turn = (frame.turn + 1) % frame.players.length;
		    		tempPlayer = frame.players[frame.turn];
		    		if(tempPlayer.isPlayable()){
		    			if(!board.playerCanPlay(tempPlayer))
		    				tempPlayer.setPlayable(false);
		    		}
		    	} while(!tempPlayer.isPlayable());
		    	frame.users[frame.turn].setBorder(new LineBorder(Color.WHITE, 3));
				
				//Switch panel
		    	frame.mainPanel.remove(frame.pieces);
				frame.pieces = frame.piecePanelArray[frame.turn];
				frame.mainPanel.add(frame.pieces);
				frame.outter.add(tutFrame.text, BorderLayout.NORTH);
				frame.outter.add(frame.mainPanel, BorderLayout.CENTER);
				frame.setContentPane(frame.outter);
			}
		}
	}
}
