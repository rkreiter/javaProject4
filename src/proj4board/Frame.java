package proj4board;

import game.Player;

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

  public Frame(String title, Player players[])
  {
    super(title);
    setLayout(new FlowLayout());
    
    BufferedImage p[] = new BufferedImage[4];
    Color[] colors = {Color.BLUE,Color.RED,Color.YELLOW, Color.GREEN};
    
    try
    {
      p[0] = ImageIO.read(new File("src/images/Board/Avatars/Stephen.png"));
      p[1] = ImageIO.read(new File("src/images/Board/Avatars/Kyle.png"));
      p[2] = ImageIO.read(new File("src/images/Board/Avatars/Troy.png"));
      p[3] = ImageIO.read(new File("src/images/Board/Avatars/Asher.png"));
    }
    catch (IOException e){ System.exit(10);}
    
    draggable = new ImageDrag(1, 5, SPACESIZE);
    
    JPanel Players = new JPanel(new GridLayout(4,1));
    Players.setPreferredSize(new Dimension(PLAYERWIDTH,GRIDSIZE));
    
    JPanel RightPlayers = new JPanel(new GridLayout(2,1));
    RightPlayers.setPreferredSize(new Dimension(PLAYERWIDTH,GRIDSIZE));
    
    JLayeredPane Board = new JLayeredPane();
    Board.setPreferredSize(new Dimension(GRIDSIZE, GRIDSIZE));
    
    JPanel All = new JPanel(new FlowLayout());
    
    for(int i = 0; i < players.length; ++i){
        User u = new User(players[i].getName(), p[i], colors[i]);
        Players.add(u);
    }
    
    ImageIcon grid = new ImageIcon("src/images/Board/Grid.png");
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
