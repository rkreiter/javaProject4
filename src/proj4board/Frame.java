package proj4board;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Frame extends JFrame 
{ 
  public ImageDrag draggable;
  
  final int GRIDSIZE =  680;
  final int PLAYERWIDTH = 300;
  final int N = 20;
  
  public final int SPACESIZE = GRIDSIZE/N;
  
  private BoardListener mouseListener;
  
  public Frame(String title)
  {
    super(title);
    setLayout(new FlowLayout());
    
    draggable = new ImageDrag(3, 3, SPACESIZE);
    
    JPanel LeftPlayers = new JPanel(new GridLayout(2,1));
    LeftPlayers.setPreferredSize(new Dimension(PLAYERWIDTH,GRIDSIZE));
    
    JPanel RightPlayers = new JPanel(new GridLayout(2,1));
    RightPlayers.setPreferredSize(new Dimension(PLAYERWIDTH,GRIDSIZE));
    
    JLayeredPane Board = new JLayeredPane();
    Board.setPreferredSize(new Dimension(GRIDSIZE, GRIDSIZE));
    
    JPanel All = new JPanel(new FlowLayout());
    
    JButton Player1 = new JButton("Player1");
    JButton Player2 = new JButton("Player2");
    JButton Player3 = new JButton("Player3");
    JButton Player4 = new JButton("Player4");
    
    LeftPlayers.add(Player1);
    LeftPlayers.add(Player2);
    RightPlayers.add(Player3);
    RightPlayers.add(Player4);
    
    ImageIcon grid = new ImageIcon("src/proj4board/Grid.png");
    JLabel gridholder = new JLabel(grid);
    gridholder.setSize(GRIDSIZE, GRIDSIZE);
    
    ImageIcon drag = new ImageIcon(draggable.image);
    JLabel dragholder = new JLabel(drag);
    dragholder.setSize(GRIDSIZE,GRIDSIZE);
    
    draggable.setSize(GRIDSIZE, GRIDSIZE);
    
    Board.add(gridholder, JLayeredPane.DEFAULT_LAYER);
    Board.add(draggable, JLayeredPane.DRAG_LAYER);
    
    
    All.add(LeftPlayers);
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
        draggable.setLocation(Xsnap(e), Ysnap(e));
      }
    }
}
