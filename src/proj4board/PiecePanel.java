package proj4board;

import game.Piece;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class PiecePanel extends JPanel
{
  final int SPACESIZE = 680/20;
  
	String color;
	Piece[] pieces;
	ImageDrag[] icons;
	
	public PiecePanel(char piececolor, int width, int height)
	{
	  
	  //Set the layout, size, background of the panel
	  this.setLayout(new FlowLayout());
	  this.setPreferredSize(new Dimension(width, height));
	  this.setBackground(Color.DARK_GRAY.darker());
	  
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
		switch (piececolor)
		{
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
		for (int i=0; i<21; i++)
		{
		  try
		  {
		    im[i] = ImageIO.read(new File("src/images/" + color + "/" + String.valueOf(i) + ".png"));
		  }
		  catch (IOException e){ System.exit(10); }
		}
		
		//Add all the images to the panel
		pieces = new Piece[21];
		icons = new ImageDrag[21];
		int w, h;
		ImageIcon drag;
		JLabel dragholder;
		
		for (int i=0; i<21; i++)
		{
		  pieces[i] = new Piece(i, piececolor);
		  icons[i] = new ImageDrag(pieces[i], SPACESIZE);
		  
		  w = icons[i].width;
		  h = icons[i].height;
		  
		  icons[i].image = icons[i].image.getScaledInstance(3*w/4, 3*h/4, BufferedImage.SCALE_DEFAULT);
		  
		  drag = new ImageIcon(icons[i].image);
		  dragholder = new JLabel(drag);
		  
		  this.add(dragholder);
		}
	}
  public void changeDraggable(ImageDrag g)
  {
    g = icons[4];
  }
}
