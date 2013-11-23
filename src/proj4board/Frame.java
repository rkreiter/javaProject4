package proj4board;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Frame extends JFrame 
{ 
  public ImageDrag draggable;
  
  final int GRIDSIZE =  680;
  final int PLAYERWIDTH = 300;
  final int N = 20;
  
  final int SPACESIZE = GRIDSIZE/N;
  private BoardListener mouseListener;

  public Frame(String title) throws IOException
  {
    super(title);
    setLayout(new FlowLayout());
    
    BufferedImage stephen = ImageIO.read(new File("src/proj4board/Stephen.png"));
    BufferedImage kyle = ImageIO.read(new File("src/proj4board/Kyle.png"));
    BufferedImage troy = ImageIO.read(new File("src/proj4board/Troy.png"));
    BufferedImage asher = ImageIO.read(new File("src/proj4board/Asher.png"));
    
    
    draggable = new ImageDrag(1, 5, SPACESIZE);
    
    JPanel Players = new JPanel(new GridLayout(4,1));
    Players.setPreferredSize(new Dimension(PLAYERWIDTH,GRIDSIZE));
    
    JPanel RightPlayers = new JPanel(new GridLayout(2,1));
    RightPlayers.setPreferredSize(new Dimension(PLAYERWIDTH,GRIDSIZE));
    
    JLayeredPane Board = new JLayeredPane();
    Board.setPreferredSize(new Dimension(GRIDSIZE, GRIDSIZE));
    
    JPanel All = new JPanel(new FlowLayout());
    
    Player One = new Player("Stephen", stephen, Color.blue);
    Player Two = new Player("Kyle", kyle, Color.red);
    Player Three = new Player("Troy", troy, Color.yellow);
    Player Four = new Player("Asher", asher, Color.green);
    
    Players.add(One);
    Players.add(Two);
    Players.add(Three);
    Players.add(Four);
    
    ImageIcon grid = new ImageIcon("src/proj4board/Grid.png");
    JLabel gridholder = new JLabel(grid);
    gridholder.setSize(GRIDSIZE, GRIDSIZE);
    
    ImageIcon drag = new ImageIcon(draggable.image);
    JLabel dragholder = new JLabel(drag);
    dragholder.setSize(GRIDSIZE,GRIDSIZE);
    
    draggable.setSize(GRIDSIZE, GRIDSIZE);
    
    Board.add(gridholder, JLayeredPane.DEFAULT_LAYER);
    Board.add(draggable, JLayeredPane.DRAG_LAYER);
    
    
    All.add(Players);
    All.add(Board);
    All.add(RightPlayers);

    add(All);
    
    mouseListener = new BoardListener();
    draggable.addMouseListener(mouseListener);
  }
    
    public class BoardListener extends MouseAdapter
    {
      
      public int Xloc(MouseEvent e)
      { return e.getX()/SPACESIZE;}
      public int Yloc(MouseEvent e)
      { return e.getY()/SPACESIZE;}
      public int Xsnap(MouseEvent e)
      { return Xloc(e)*SPACESIZE; }
      public int Ysnap(MouseEvent e)
      { return Yloc(e)*SPACESIZE; }
     
      public void mouseClicked(MouseEvent e)
      {
      }
      public void mouseReleased(MouseEvent e)
      {
        if (!draggable.clicked)
          draggable.setLocation(Xsnap(e), Ysnap(e));
      }
    }
}
