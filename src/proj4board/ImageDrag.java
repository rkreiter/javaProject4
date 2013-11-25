package proj4board;
import game.*;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageDrag extends JComponent implements MouseMotionListener, MouseListener {
	final int GRIDSIZE = Frame.GRIDSIZE;
	final int N = Frame.N;
	final int SPACESIZE = GRIDSIZE/N; 
	Image image;
	Image darkImage;
	Image lightImage;
	Image initDark;
	Image initLight;
	boolean clicked;
	int x=0, y=0, width, height, size;
	int xVal = 0, yVal = 0;
	Board board;
	Player player;
	Piece piece;
	Color color;
	JButton submitButton;
	
	public void reset(){
		piece.setOriginalState();
		lightImage = initLight;
		darkImage = initDark;
		image = lightImage;
		this.width = (piece.getWidth() * size);
		this.height = (piece.getHeight() * size) + 1;
		clicked = false;
	}
	
	//Sets the display image to the correct thing THAT IS IT
	public void setToState(int state){
		switch(state){
			case 0:
				lightImage = initLight;
				darkImage = initDark;
				break;
			case 1:
				lightImage = rotateImage(initLight, 90);
				darkImage = rotateImage(initDark, 90);
				break;
			case 2:
				lightImage = rotateImage(initLight, 180);
				darkImage = rotateImage(initDark, 180);
				break;
			case 3:
				lightImage = rotateImage(initLight, -90);
				darkImage = rotateImage(initDark, -90);
				break;
			case 4:
				lightImage = flipImage(initLight);
				darkImage = flipImage(initDark);
				lightImage = rotateImage(lightImage, 180);
				darkImage = rotateImage(darkImage, 180);
				break;
			case 5:
				lightImage = flipImage(initLight);
				darkImage = flipImage(initDark);
				lightImage = rotateImage(lightImage, -90);
				darkImage = rotateImage(darkImage, -90);
				break;
			case 6:
				lightImage = flipImage(initLight);
				darkImage = flipImage(initDark);
				break;
			case 7:
				lightImage = flipImage(initLight);
				darkImage = flipImage(initDark);
				lightImage = rotateImage(lightImage, 90);
				darkImage = rotateImage(darkImage, 90);
				break;
		}
		lightImage = lightImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		darkImage = darkImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		if(player != null){
			translatePieceHelper();
		}
	}
  
	public ImageDrag(Piece piece, int size, Board board, Player player, JButton submit) {
		this.board = board;
		this.player = player;
		this.piece = piece;
		this.submitButton = submit;
		initComponents(size);
		addMouseMotionListener(this);
		addMouseListener(this);
		piece.printShape();
	}
	
	public void initComponents(int size) {  
		this.width = (piece.getWidth() * size);
		this.height = (piece.getHeight() * size) + 1;
		this.size = size;
		this.clicked = false;
		try { 
			switch(piece.getColor()) {
				case 'b':
					darkImage = ImageIO.read(new File("src/images/Blue/"
							+ piece.getType() + ".png"));
					lightImage = ImageIO.read(new File("src/images/Blue/"
							+ piece.getType() + "T.png"));
					color = Color.BLUE;
					break;
				case 'r':
					darkImage = ImageIO.read(new File("src/images/Red/"
	  			  			+ piece.getType() + ".png"));
					lightImage = ImageIO.read(new File("src/images/Red/"
	  			  			+ piece.getType() + "T.png"));
					color = Color.RED;
					break;
				case 'y':
					darkImage = ImageIO.read(new File("src/images/Yellow/"
	  			  			+ piece.getType() + ".png"));
					lightImage = ImageIO.read(new File("src/images/Yellow/"
	  			  			+ piece.getType() + "T.png"));
					color = Color.YELLOW;
					break;
				case 'g':
					darkImage = ImageIO.read(new File("src/images/Green/"
	  			  			+ piece.getType() + ".png"));
					lightImage = ImageIO.read(new File("src/images/Green/"
	  			  			+ piece.getType() + "T.png"));
					color = Color.GREEN;
					break;
			}
			if(submitButton != null){
				submitButton.setEnabled(false);
			}
		}
		catch(IOException ioe) { ioe.printStackTrace(); }
		initDark = darkImage;
		initLight = lightImage;
		lightImage = lightImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		darkImage = darkImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		image = lightImage;
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
				xVal = Yloc(e);
				yVal = Xloc(e);
				System.out.println("Location: " + xVal + "," + yVal + "   State: " + piece.getState());
				piece.printShape();
				boolean validSpot = false;
				if(player.isInit()){
					validSpot = board.validInit(xVal, yVal, piece);
				}
				else
					validSpot = board.validPlace(xVal, yVal, piece, false);
				if(validSpot){
					image = darkImage;
					if(submitButton != null)
						submitButton.setEnabled(true);
				}
			}
			else{
				image = lightImage;
				if(submitButton != null)
					submitButton.setEnabled(false);
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
	
	
	
	//Only called When it is the current players move! NOT UPDATE
	public void translatePieceHelper(){
		System.out.println("Location: " + xVal + "," + yVal + "   State: " + piece.getState());
		piece.printShape();
		image = lightImage;
		submitButton.setEnabled(false);
		boolean validSpot = false;
		if(player.isInit()){
			validSpot = board.validInit(xVal, yVal, piece);
		}
		else
			validSpot = board.validPlace(xVal, yVal, piece, false);
		if(validSpot){
			image = darkImage;
			if(submitButton != null)
				submitButton.setEnabled(true);
		}
		repaint();
	}

	public void updatePieceHelper(){
		System.out.println("Location: " + xVal + "," + yVal + "   State: " + piece.getState());
		image = lightImage;
		repaint();
	}
	
	
	
	//Functions called by PiecePanel
	public void ImageDragRC(){
		rotateClockwise();
		setToState(piece.getState());
	}
	
	public void ImageDragRCC(){
		rotateCounterClockwise();
		setToState(piece.getState());
	}
	
	public void ImageDragFlip(){
		flip();
		setToState(piece.getState());
	}
	
	
	
	
	//Internal Functions to make ONLY THE PIECE CHANGE
	public void rotateClockwise(){
		piece.rotateClockwise();
		this.width = (piece.getWidth() * size);
		this.height = (piece.getHeight() * size) + 1;
	}
	
	public void rotateCounterClockwise(){
		piece.rotateCounterClockwise();
		this.width = (piece.getWidth() * size);
		this.height = (piece.getHeight() * size) + 1;
	}
	
	public void flip(){
		piece.flipHorizontalAxis();
	}
	
	
	
	//Functions to Change THE IMAGE ONLY!!
    public Image rotateImage(Image img, double angle){
        double sin = Math.abs(Math.sin(Math.toRadians(angle))), cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = img.getWidth(null), h = img.getHeight(null);
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h
                * cos + w * sin);
        BufferedImage bimg = new BufferedImage(neww, newh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bimg.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(Math.toRadians(angle), w / 2, h / 2);
        g.drawRenderedImage(toBufferedImage(img), null);
        g.dispose();
        return (Image)bimg;
    }
    
    public Image flipImage(Image img){
        BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bimg.createGraphics();
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -img.getHeight(null));
        g.drawRenderedImage(toBufferedImage(img), tx);
        g.dispose();
        return (Image)bimg;
    }

    public BufferedImage toBufferedImage(Image img){
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }
}