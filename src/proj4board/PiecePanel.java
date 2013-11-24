package proj4board;

import game.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class PiecePanel extends JPanel {
	final int GRIDSIZE =  680;
	final int PLAYERWIDTH = 300;
	final int N = 20;
	final int SPACESIZE = GRIDSIZE/N;
	String color;
	Piece[] pieces;
	ImageDrag[] icons;
	JLabel[] clickables;
	IconListener list;
	ImageDrag currentPiece;
	int currentPieceNum;
	JLayeredPane board;
	private BoardListener mouseListener;
	private game.Board gameBoard;
	boolean init = true;
	
	public PiecePanel(char piececolor, int width, int height, JLayeredPane b, game.Board gB) {
		//Set the layout, size, background of the panel
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.DARK_GRAY.darker());

		//ASHER's STUFFF!!!!!!
		board = b;
		gameBoard = gB;
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
		switch (piececolor) {
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
		pieces = new Piece[21];
		icons = new ImageDrag[21];
		clickables = new JLabel[21];
		int w, h;
		ImageIcon drag;
		JLabel dragholder;
		list = new IconListener();
		
		for (int i=0; i<21; i++) {
			pieces[i] = new Piece(i, piececolor);
			icons[i] = new ImageDrag(pieces[i], SPACESIZE);
		  
			w = icons[i].width;
			h = icons[i].height;
		  
			icons[i].image = icons[i].image.getScaledInstance(3*w/4, 
					3*h/4, BufferedImage.SCALE_DEFAULT);
		  
			drag = new ImageIcon(icons[i].image);
			dragholder = new JLabel(drag);
			clickables[i] = dragholder;
			dragholder.addMouseListener(list);
		  
			this.add(dragholder);
		}
	}
  
	public class IconListener extends MouseAdapter {
		public void mouseClicked(MouseEvent me) {
			for(int i = 0; i < clickables.length; ++i){
				if(me.getSource() == clickables[i]){
					System.out.println("Piece Num Clicked: " + i);
					if(currentPiece != null){
						board.remove(currentPiece);
						clickables[currentPieceNum].setVisible(true);
					}
					Piece piece = pieces[i];
				    currentPiece = new ImageDrag(piece, SPACESIZE);
				    currentPiece.setSize(GRIDSIZE, GRIDSIZE);
				    board.add(currentPiece, JLayeredPane.DRAG_LAYER);
				    mouseListener = new BoardListener();
				    currentPiece.addMouseListener(mouseListener);
				    currentPieceNum = i;
				    clickables[i].setVisible(false);
				    break;
				}
			}
		}
	}
	public class BoardListener extends MouseAdapter {
	  
	    public int Xloc(MouseEvent e) { 
    		return e.getX()/SPACESIZE;
    	}
    	public int Yloc(MouseEvent e) { 
    		return e.getY()/SPACESIZE;
    	}
    	public int Xsnap(MouseEvent e) { 
    		return Xloc(e)*SPACESIZE; 
    	}
    	public int Ysnap(MouseEvent e) { 
    		return Yloc(e)*SPACESIZE; 
    	}
    	
    	public void mouseReleased(MouseEvent e) {
    		if (currentPiece != null && !currentPiece.clicked && currentPiece.inBounds(e)){
    			System.out.println("Location: " + Xloc(e) + "," + Yloc(e));
    			currentPiece.setLocation(Xsnap(e), Ysnap(e));
    			currentPiece = null;
    		}
    	}
    }
}
