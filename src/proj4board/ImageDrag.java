package proj4board;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.*;

public class ImageDrag extends JComponent implements MouseMotionListener
{
  BufferedImage image;
  int x=0, y=0;
  
  public ImageDrag()
  {
    initComponents();
    addMouseMotionListener(this);
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
  public void mouseMoved(MouseEvent me){}
  public void mouseDragged(MouseEvent me)
  {
    x = me.getX();
    y = me.getY();
    repaint();
  }
  public void setLocation(int xloc, int yloc)
  {
    x = xloc;
    y = yloc;
    repaint();
  }
}