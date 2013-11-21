package proj4board;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Frame extends JFrame 
{
  public Grids grid;
  
  final int GRIDSIZE =  680;
  final int PLAYERWIDTH = 300;
  final int N = 20;
  
  final int SPACESIZE = GRIDSIZE/N;
  
  private BoardListener mouseListener;
  
  public Frame(String title)
  {
    super(title);
    setLayout(new FlowLayout());
    
    ImageDrag draggable = new ImageDrag();
    
    JPanel LeftPlayers = new JPanel(new GridLayout(2,1));
    LeftPlayers.setPreferredSize(new Dimension(PLAYERWIDTH,GRIDSIZE));
    
    JPanel RightPlayers = new JPanel(new GridLayout(2,1));
    RightPlayers.setPreferredSize(new Dimension(PLAYERWIDTH,GRIDSIZE));
    
    JPanel Board = new JPanel(new FlowLayout());
    
    JPanel All = new JPanel(new FlowLayout());
    
    JButton Player1 = new JButton("Player1");
    JButton Player2 = new JButton("Player2");
    JButton Player3 = new JButton("Player3");
    JButton Player4 = new JButton("Player4");
    
    LeftPlayers.add(Player1);
    LeftPlayers.add(Player2);
    RightPlayers.add(Player3);
    RightPlayers.add(Player4);
    
    grid = new Grids(GRIDSIZE, GRIDSIZE, N, N);    
    Board.add(grid);
    
    All.add(LeftPlayers);
    All.add(Board);
    All.add(RightPlayers);

    add(All);
    
    mouseListener = new BoardListener();
    grid.addMouseListener(mouseListener);
  }
    
    public class BoardListener extends MouseAdapter
    {
      public void mouseClicked(MouseEvent e)
      {
        System.out.println("Board clicked");
        System.out.println(e.getX()/SPACESIZE);
        System.out.println(e.getY()/SPACESIZE);
      }
    }
}
