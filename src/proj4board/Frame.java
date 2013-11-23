package proj4board;

import game.Piece;
import game.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Frame extends JFrame 
{ 
  public ImageDrag draggable;
  
  final int GRIDSIZE =  680;
  final int PLAYERWIDTH = 300;
  final int N = 20;
  
  final int SPACESIZE = GRIDSIZE/N;
  private BoardListener mouseListener;
  private PanelListener panlist;

  public Frame(String title, Player players[])
  {
	//Set up the title and general layout
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
    
    JPanel Players = new JPanel(new GridLayout(4,1));
    Players.setPreferredSize(new Dimension(PLAYERWIDTH,GRIDSIZE));
    
    JLayeredPane Board = new JLayeredPane();
    Board.setPreferredSize(new Dimension(GRIDSIZE, GRIDSIZE));
    
    JPanel Pieces = new PiecePanel('b', PLAYERWIDTH, GRIDSIZE);
    panlist = new PanelListener();
    Pieces.addMouseListener(panlist);
    
    JPanel All = new JPanel(new FlowLayout());
    
    for(int i = 0; i < players.length; ++i){
        User u = new User(players[i].getName(), p[i], colors[i]);
        Players.add(u);
    }
    
    ImageIcon grid = new ImageIcon("src/images/Board/Grid.png");
    JLabel gridholder = new JLabel(grid);
    gridholder.setSize(GRIDSIZE, GRIDSIZE);
    
    Piece piece = new Piece(18, 'b');
    draggable = new ImageDrag(piece, SPACESIZE);
    
    ImageIcon drag = new ImageIcon(draggable.image);
    JLabel dragholder = new JLabel(drag);
    dragholder.setSize(GRIDSIZE,GRIDSIZE);
    
    draggable.setSize(GRIDSIZE, GRIDSIZE);
    
    Board.add(gridholder, JLayeredPane.DEFAULT_LAYER);
    Board.add(draggable, JLayeredPane.DRAG_LAYER);
    
    All.add(Players);
    All.add(Board);
    All.add(Pieces);

    All.setBackground(Color.DARK_GRAY);
    
    add(All);
    
    mouseListener = new BoardListener();
    draggable.addMouseListener(mouseListener);
    
    
    setExtendedState(JFrame.MAXIMIZED_BOTH);
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

      public void mouseReleased(MouseEvent e)
      {
        if (!draggable.clicked)
          draggable.setLocation(Xsnap(e), Ysnap(e));
      }
    }
    public class PanelListener extends MouseAdapter
    {
      public void mouseClicked(MouseEvent me) 
      { 
        System.out.println("You clicked me!");
      }
      public void mouseEntered(MouseEvent me)
      {
        System.out.println("Entered something");
      }
    }
}
