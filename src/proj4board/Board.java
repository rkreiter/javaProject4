package proj4board;
import game.*;

import javax.swing.JFrame;

public class Board
{
  public static void main(String[] args)
  {
	Player players[] = new Player[4];
	players[0] = new Player("Stephen", 'b');
	players[1] = new Player("Kyle", 'r');
	players[2] = new Player("Troy", 'y');
	players[3] = new Player("Asher", 'g');
	
    Frame frame = new Frame("Blokus", players);
    
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setVisible(true);
    
  }
}