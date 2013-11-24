package proj4board;

import game.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class PiecePanel extends JPanel {
	final int GRIDSIZE = Frame.GRIDSIZE;
	final int PLAYERWIDTH = Frame.PLAYERWIDTH;
	final int N = Frame.N;
	final int SPACESIZE = GRIDSIZE/N;
	String color;
	Piece[] pieces;
	JRadioButton[] clickables;
	ImageDrag currentPiece;
	int currentPieceNum;
	JLayeredPane boardPanel;
	JPanel piecesPanel;
	JButton submitButton;
	Board board;
	Player player;
	Piece piece;
	Frame frame;
	
	public PiecePanel(Frame frame) {
		//Set the layout, size, background of the panel
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(PLAYERWIDTH, GRIDSIZE));
		this.setBackground(Color.DARK_GRAY.darker());

		//Initialize variables
		boardPanel = frame.boardPanel;
		this.board = frame.board;
		this.player = frame.players[frame.playerNum];
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
	  
		//Set the piececolor string to grab the images
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
		
		//Grab all the images for that player based on his/her color
		Image im[] = new Image[21];
		for (int i=0; i<21; i++) {
			try {
				im[i] = ImageIO.read(new File("src/images/" + color 
						+ "/" + String.valueOf(i) + ".png"));
			}
			catch (IOException e){ System.exit(10); }
		}
		
		//Add all the images to the panel
		piecesPanel = new JPanel(new FlowLayout());
		piecesPanel.setBackground(Color.DARK_GRAY.darker());
		pieces = new Piece[21];
		clickables = new JRadioButton[21];
		int w, h;
		ImageIcon icon = null;
		ButtonListener clicked = new ButtonListener();
		
		for (int i=0; i<21; i++) {		  
			pieces[i] = player.getPiece(i);
			w = (int) (0.75 * (pieces[i].getWidth() * SPACESIZE));
			h = (int) (0.75 * (pieces[i].getHeight() * SPACESIZE + 1));
			im[i] = im[i].getScaledInstance(w, h, BufferedImage.SCALE_DEFAULT);
			icon = new ImageIcon(im[i]);
			clickables[i] = new JRadioButton(icon);
			clickables[i].setOpaque(false);
			clickables[i].addActionListener(clicked);
			piecesPanel.add(clickables[i]);
		}
		this.add(piecesPanel);
		
		submitButton = new JButton("Submit Move!");
		submitButton.addActionListener(new SubmitListener());
		submitButton.setEnabled(false);
		this.add(submitButton, BorderLayout.SOUTH);

		//Testing ability to send updates to the board
		//frame.placePieceOnBoard('b', 0, 0, 1);
		//frame.placePieceOnBoard('r', 0, 5, 0);
	}
	
	public class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			for(int i = 0; i < clickables.length; ++i){
				if(e.getSource() == clickables[i]){
					System.out.println("Piece Num Clicked: " + i);
					if(currentPiece != null){
						boardPanel.remove(currentPiece);
						clickables[currentPieceNum].setVisible(true);
					}
					piece = pieces[i];
				    currentPiece = new ImageDrag(piece, SPACESIZE, board, player, submitButton);
				    currentPiece.setSize(GRIDSIZE, GRIDSIZE);
				    boardPanel.add(currentPiece, JLayeredPane.DRAG_LAYER);
				    currentPieceNum = i;
				    clickables[i].setVisible(false);
				    break;
				}
			}
		}
	}
	
  	public class SubmitListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(currentPiece != null){
				submitButton.setEnabled(false);
				int X = currentPiece.xVal;
				int Y = currentPiece.yVal;
				currentPiece.finalize();
				board.placePiece(X, Y, piece);
				piece.setPlaced();
				player.updateScore(piece.getValue());
				board.printBoard();
				currentPiece = null;
				if(frame.theClient != null){
					frame.setPlayerTurn(false);
					frame.theClient.sendMove(piece.toString(X, Y));
				}
			}
		}
	}
}
