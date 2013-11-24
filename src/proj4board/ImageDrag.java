package proj4board;
import game.*;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.*;

public class ImageDrag extends JComponent implements MouseMotionListener, MouseListener {
	final int GRIDSIZE = Frame.GRIDSIZE;
	final int N = Frame.N;
	final int SPACESIZE = GRIDSIZE/N; 
	BufferedImage image;
	BufferedImage defaultImage;
	boolean clicked;
	int x=0, y=0, width, height, size;
	Board board;
	Player player;
	Piece piece;
	Color color;
  
	public ImageDrag(Piece piece, int size, Board board, Player player) {
		this.board = board;
		this.player = player;
		this.piece = piece;
		initComponents(size);
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	public void initComponents(int size) {  
		this.width = (piece.getWidth() * size);
		this.height = (piece.getHeight() * size) + 1;
		this.size = size;
		this.clicked = false;
		try { 
			switch(piece.getColor()) {
				case 'b':
					image = ImageIO.read(new File("src/images/Blue/"
							+ piece.getType() + ".png"));
					color = Color.BLUE;
					break;
				case 'r':
					image = ImageIO.read(new File("src/images/Red/"
	  			  			+ piece.getType() + ".png"));
					color = Color.RED;
					break;
				case 'y':
					image = ImageIO.read(new File("src/images/Yellow/"
	  			  			+ piece.getType() + ".png"));
					color = Color.YELLOW;
					break;
				case 'g':
					image = ImageIO.read(new File("src/images/Green/"
	  			  			+ piece.getType() + ".png"));
					color = Color.GREEN;
					break;
			}
			defaultImage = image;
			setAlpha((byte) 50);
		}
		catch(IOException ioe) { ioe.printStackTrace(); }
		Image img = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		image = new BufferedImage(width, height, Image.SCALE_REPLICATE);
		image.getGraphics().drawImage(img, 0, 0, null);
		
	}
	
	public void paint(Graphics g) {
		g.drawImage(image, x, y+1, this);
	}
  
	public boolean inBounds(MouseEvent e) {
		return (e.getX() < (680 - (width-size)) && e.getY() < (680 - (height-size)));
	}
  
	public void mouseMoved(MouseEvent e) {
		if (!clicked) {
			x = e.getX();
			y = e.getY();
			repaint();
		}
	}
  
	public void mouseClicked(MouseEvent e) {
		if(inBounds(e)){
			if(!clicked){
				int x = Xloc(e);
				int y = Yloc(e);
				System.out.println("Location: " + x + "," + y);
				boolean validSpot = false;
				if(player.isInit()){
					validSpot = board.validInit(x, y, piece);
				}
				else
					validSpot = board.validPlace(x, y, piece, false);
				if(validSpot){
					setAlpha((byte) 255);
				}
			}
			else{
				setAlpha((byte) 50);
			}
			clicked = !clicked;
			setLocation(Xsnap(e), Ysnap(e));
		}
	}
  
	public void mouseDragged(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void setLocation(int xloc, int yloc) {
		x = xloc;
		y = yloc;
		repaint(); 
	}
	
	public int Xloc(MouseEvent e) { 
		return e.getX() / SPACESIZE;
	}
	public int Yloc(MouseEvent e) { 
		return e.getY() / SPACESIZE;
	}
	public int Xsnap(MouseEvent e) { 
		return Xloc(e) * SPACESIZE; 
	}
	public int Ysnap(MouseEvent e) { 
		return Yloc(e) * SPACESIZE; 
	}
	
	public void finalize(){
		removeMouseListener(this);
		removeMouseMotionListener(this);
	}

	public void setAlpha(byte alpha) {       
	    alpha %= 0xff; 
		for (int cx=0;cx<image.getWidth();cx++) {          
	        for (int cy=0;cy<image.getHeight();cy++) {
	            int color = image.getRGB(cx, cy);

	            int mc = (alpha << 24) | 0x00ffffff;
	            int newcolor = color & mc;
	            image.setRGB(cx, cy, newcolor);            

	        }

	    }
	}
}