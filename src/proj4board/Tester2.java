package proj4board;
import java.awt.Color;

import game.*;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tester2 {
	public static void main(String[] args) {
		JFrame asher = new JFrame("BULLSHIT");
		asher.setSize(600, 600);
		asher.add(new JLabel("I Rock...."));
		asher.setVisible(true);
		
		Player players[] = new Player[4];
		players[0] = new Player("Asher", 'b');
		players[1] = new Player("Kyle", 'r');
		players[2] = new Player("Troy", 'y');
		players[3] = new Player("Stephen", 'g');
		Board board = new Board();
		Player player = players[0];
		
		Frame frame = new Frame("Blokus", board, players, player, 0, null);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); 
	}
}