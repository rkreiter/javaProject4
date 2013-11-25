package proj4board;
import java.awt.Color;

import game.*;

import javax.swing.JFrame;

public class Tester2 {
	public static void main(String[] args) {
		Player players[] = new Player[4];
		players[0] = new Player("Stephen", 'b');
		players[1] = new Player("Kyle", 'r');
		players[2] = new Player("Troy", 'y');
		players[3] = new Player("Asher", 'g');
		Board board = new Board();
		int num = 1;
		Player player = players[num];
	
		Frame frame = new Frame("Blokus", board, players, player, num, null);
    
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);   
	}
}