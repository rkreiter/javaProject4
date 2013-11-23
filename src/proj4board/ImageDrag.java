package proj4board;
import game.Piece;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.*;

public class ImageDrag extends JComponent implements MouseMotionListener, MouseListener
{
  Image image;
  boolean clicked;
  int x=0, y=0;
  int mouseX, mouseY;
  
  public ImageDrag(Piece p, int size)
  { 
    initComponents(p, size);
    addMouseMotionListener(this);
    addMouseListener(this);
  }
  public void initComponents(Piece p, int size)
  {  
	int numx = p.getWidth();
	int numy = p.getHeight();
	
	try
    { 
	  switch(p.getColor()){
	  	case 'b':
	  	  image = ImageIO.read(new File("src/images/Blue/"
	  			  			+ p.getType() + ".png"));
	  	  break;
	  	  
	  	case 'r':
	  	  image = ImageIO.read(new File("src/images/Red/"
	  			  			+ p.getType() + ".png"));
		  break;
		  	  
	  	case 'y':
	  	  image = ImageIO.read(new File("src/images/Yellow/"
	  			  			+ p.getType() + ".png"));
		  break;
		  	  
	  	case 'g':
	  	  image = ImageIO.read(new File("src/images/Green/"
	  			  			+ p.getType() + ".png"));
		  break;
	  }
	}
    catch(IOException ioe)
    { ioe.printStackTrace(); }
    
    image = image.getScaledInstance((numx*size)+1, (numy*size)+1, BufferedImage.SCALE_DEFAULT);
    this.clicked = false;
  }
  public void paint(Graphics g)
  {
    g.drawImage(image, x, y+1, this);
  }
  public void mouseMoved(MouseEvent me)
  {
    if (!clicked)
    {
      x = me.getX();
      y = me.getY();
      repaint();
    }
  }
  public void mouseClicked(MouseEvent me) 
  {
    clicked = true;
  }
  public void mouseDragged(MouseEvent arg0){}
  public void mouseEntered(MouseEvent arg0) {}
  public void mouseExited(MouseEvent arg0) {}
  public void mousePressed(MouseEvent arg0) {}
  public void mouseReleased(MouseEvent arg0) {}
  public void setLocation(int xloc, int yloc)
  {
    x = xloc;
    y = yloc;
    repaint(); 
  }
}