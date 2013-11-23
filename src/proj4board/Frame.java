package proj4board;

import game.Piece;
import game.Player;
import proj4board.PiecePanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	public ImageDrag draggable;
	final int GRIDSIZE =  680;
	final int PLAYERWIDTH = 300;
	final int N = 20;
	final int SPACESIZE = GRIDSIZE/N;
	private BoardListener mouseListener;

	public Frame(String title, Player players[]) {
		//Set up the title and general layout
		super(title);
		setLayout(new FlowLayout());	
		
		//Create Player Panel
		BufferedImage p[] = new BufferedImage[4];
		Color[] colors = {Color.BLUE,Color.RED,Color.YELLOW, Color.GREEN};
		
		//Get player images
		try {
			p[0] = ImageIO.read(new File("src/images/Board/Avatars/Stephen.png"));
			p[1] = ImageIO.read(new File("src/images/Board/Avatars/Kyle.png"));
			p[2] = ImageIO.read(new File("src/images/Board/Avatars/Troy.png"));
			p[3] = ImageIO.read(new File("src/images/Board/Avatars/Asher.png"));
		}
		catch (IOException e){ System.exit(10);}
		//Create panel
		JPanel Players = new JPanel(new GridLayout(4,1));
		Players.setPreferredSize(new Dimension(PLAYERWIDTH,GRIDSIZE));
		//load each user into panel
		for(int i = 0; i < players.length; ++i){
	        User u = new User(players[i].getName(), p[i], colors[i]);
	        Players.add(u);
	    }
		
		
		
		//Create Board Panel
		JLayeredPane Board = new JLayeredPane();
		Board.setPreferredSize(new Dimension(GRIDSIZE, GRIDSIZE));
		
		//Add board image
		ImageIcon grid = new ImageIcon("src/images/Board/Grid.png");
    JLabel gridholder = new JLabel(grid);
    gridholder.setSize(GRIDSIZE, GRIDSIZE);
    Board.add(gridholder, JLayeredPane.DEFAULT_LAYER);
    
    //Create actual draggable piece
    Piece piece = new Piece(16, 'b');
    draggable = new ImageDrag(piece, SPACESIZE);
    draggable.setSize(GRIDSIZE, GRIDSIZE);
    Board.add(draggable, JLayeredPane.DRAG_LAYER);
    mouseListener = new BoardListener();
    draggable.addMouseListener(mouseListener);

		
	    
		//Create Pieces Panel
		PiecePanel Pieces = new PiecePanel('b', PLAYERWIDTH, GRIDSIZE);
    
		
		
		//Merge panels together
		JPanel All = new JPanel(new FlowLayout());        
		All.add(Players);
		All.add(Board);
		All.add(Pieces);
		All.setBackground(Color.DARK_GRAY);
		add(All);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
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
    		if (!draggable.clicked && draggable.inBounds(e)) {
    			draggable.setLocation(Xsnap(e), Ysnap(e));
    		}
    	}
    }
}
