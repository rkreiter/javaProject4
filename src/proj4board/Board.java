package proj4board;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class Board
{
  private JLayeredPane layered = new JLayeredPane();
  
  public static void main(String[] args) 
  {
    Frame frame = new Frame("Blokus");
    
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    frame.setVisible(true);
  }
}