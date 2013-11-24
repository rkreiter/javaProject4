package proj4board;

import game.*;
import proj4board.PiecePanel;
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

	public Frame(String title, Player players[]) {
		//Initialize
		super(title);
		setLayout(new FlowLayout());
		Board board = new Board();
		
		
		
		//Create Player Panel
		JPanel playersPanel = new JPanel(new GridLayout(4,1));
		playersPanel.setPreferredSize(new Dimension(PLAYERWIDTH,GRIDSIZE));
		BufferedImage p[] = new BufferedImage[4];
		Color[] colors = {Color.BLUE,Color.RED,Color.YELLOW, Color.GREEN};
		try {
			p[0] = ImageIO.read(new File("src/images/Board/Avatars/Stephen.png"));
			p[1] = ImageIO.read(new File("src/images/Board/Avatars/Kyle.png"));
			p[2] = ImageIO.read(new File("src/images/Board/Avatars/Troy.png"));
			p[3] = ImageIO.read(new File("src/images/Board/Avatars/Asher.png"));
		}
		catch (IOException e){ System.exit(10);}
		for(int i = 0; i < players.length; ++i){
	        User u = new User(players[i].getName(), p[i], colors[i]);
	        playersPanel.add(u);
	    }
		
		
		
		//Create Board Panel
		JLayeredPane boardPanel = new JLayeredPane();
		boardPanel.setPreferredSize(new Dimension(GRIDSIZE, GRIDSIZE));
		ImageIcon grid = new ImageIcon("src/images/Board/Grid.png");
	    JLabel gridholder = new JLabel(grid);
	    gridholder.setSize(GRIDSIZE, GRIDSIZE);
	    boardPanel.add(gridholder, JLayeredPane.DEFAULT_LAYER);
	    
	    
	    
		//Create Pieces Panel
		PiecePanel Pieces = new PiecePanel(players[1].getColor(), PLAYERWIDTH, 
				GRIDSIZE, boardPanel, board, players[1]);
    
		
		
		//Merge panels together
		JPanel mainPanel = new JPanel(new FlowLayout());        
		mainPanel.add(playersPanel);
		mainPanel.add(boardPanel);
		mainPanel.add(Pieces);
		mainPanel.setBackground(Color.DARK_GRAY);
		add(mainPanel);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}
