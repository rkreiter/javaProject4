package proj4board;
import game.*;

import javax.swing.JFrame;

public class Board
{
  public static void main(String[] args)
  {
	Player players[] = new Player[4];
	
    Frame frame = new Frame("Blokus", players);
    
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    frame.setVisible(true);
  }
}