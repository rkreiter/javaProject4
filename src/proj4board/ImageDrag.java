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
  BufferedImage image;
  int x=0, y=0;
  int mouseX, mouseY;
  
  public ImageDrag()
  {
    initComponents();
    addMouseMotionListener(this);
    addMouseListener(this);
  }
  public void initComponents()
  {
    try
    { image = ImageIO.read(new File("src/proj4board/Leven.png"));}
    catch(IOException ioe)
    { ioe.printStackTrace(); }
  }
  public void paint(Graphics g)
  {
    g.drawImage(image, x, y, this);
  }
  public void mouseDragged(MouseEvent me)
  {
    if (!(mouseX > x && mouseX < x + image.getWidth() &&
        mouseY > y && mouseY < y + image.getHeight()))
    {
      x = me.getX();
      y = me.getY();
      repaint();
    }
  }
  public void mouseMoved(MouseEvent arg0){}
  public void mouseClicked(MouseEvent e) 
  {
    mouseX = e.getX();
    mouseY = e.getY();
  }
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