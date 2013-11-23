package proj4board;
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
  
  public ImageDrag(int numx, int numy, int size)
  { 
    initComponents(numx, numy, size);
    addMouseMotionListener(this);
    addMouseListener(this);
  }
  public void initComponents(int numx, int numy, int size)
  {  
    try
    { image = ImageIO.read(new File("src/proj4board/I.png"));}
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