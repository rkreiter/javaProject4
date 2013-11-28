package intro;

import game.Board;
import game.Player;
import tutorial.*;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class startTutWin extends JDialog {
	//constructor
	public startTutWin(JFrame mainFrame) {
		super(mainFrame, "Tutorial", true);
		setLayout(new FlowLayout());

	    JPanel main;
	    JTextArea rulzArea;
	    JButton close;

	    //base layout fields
	    main = new JPanel(new GridLayout(2,1));

	    rulzArea = new JTextArea(
	    	"Tutorial:\n  This tutorial will go over the basics of how to run " +
	    	"this version of Blokus. It will go over the basics of how to place " +
	    	"your pieces on the board and orient them in valid ways. Click the " +
	    	"button below to begin!"
	    );

    
	    rulzArea.setLineWrap(true);
	    rulzArea.setWrapStyleWord(true);
	    rulzArea.setEditable(false);
	    rulzArea.setOpaque(false);
	    rulzArea.setForeground(Color.WHITE);
	    rulzArea.setColumns(50);
    
	    //colorful borders start
	    JPanel blue = new JPanel(new FlowLayout());
	    blue.add(rulzArea);
	    blue.setBorder(BorderFactory.createLineBorder(Color.blue));
	    blue.setOpaque(false);
	    JPanel yellow = new JPanel(new FlowLayout());
	    yellow.add(blue);
	    yellow.setBorder(BorderFactory.createLineBorder(Color.yellow));
	    yellow.setOpaque(false);
	    JPanel red = new JPanel(new FlowLayout());
	    red.add(yellow);
	    red.setBorder(BorderFactory.createLineBorder(Color.red));
	    red.setOpaque(false);
	    JPanel green = new JPanel(new FlowLayout());
	    green.add(red);
	    green.setBorder(BorderFactory.createLineBorder(Color.green));
	    green.setOpaque(false);
	    //colorful borders end
    
	    main.add(green);
	    main.setBackground(Color.BLACK);
    
	    JPanel buttonPan = new JPanel(new FlowLayout());
	    close = new JButton("Begin!");
	    close.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		Board board = new Board();
	    		Player p1 = new Player("Com", 'b');
	    		Player p2 = new Player("YOU", 'r');
	    		Player players[] = {p1, p2};
	    		tutFrame tut = new tutFrame("Tutorial", board, players, p1, 
	    				0, null);
	    		tut.getContentPane().setBackground(Color.DARK_GRAY);
		        tut.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        tut.setExtendedState(JFrame.MAXIMIZED_BOTH);
		        tut.pack();
		        setVisible(false);
		        tut.setVisible(true);
	    	}
	    });
	    buttonPan.add(close);
	    buttonPan.setBackground(Color.BLACK);
	    main.add(buttonPan);
	    getContentPane().setBackground(Color.BLACK);
	    add(main);
	    this.pack();
	    this.setSize(this.getWidth()+10, this.getHeight());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);
        this.setVisible(true);
	}
}