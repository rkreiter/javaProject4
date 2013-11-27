package intro;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class howtoWin extends JDialog
{
  //constructor
  public howtoWin(JFrame mainFrame)
  {
    super(mainFrame, "Rules", true);
    setLayout(new FlowLayout());

    JPanel main;
    JTextArea rulzArea;
    JButton close;

    //base layout fields
    main = new JPanel(new GridLayout(2,1));

    rulzArea = new JTextArea(
      "Rules:\n" +
      "1.  Each player is given a color and places that set of 21 pieces " +
      "on the right side his/her board.  The order of play is as follows: " +
      "blue, red, yellow, and then green.\n\n 2.  The first player (blue) " +
      "the places any of his/her pieces in their designated corner square. " +
      "Play proceeds clockwise around the board (red, yellow, and green), " +
      "each player putting their first piece down in their corner square. " +
      "\n\n3.  Play continues as each player lays down one piece during a " +
      "turn.\n  - Each new piece must touch at least one other piece of " +
      "the same color, but only at the corners.\n  - No flat edges of same " +
      "color pieces can touch.\n\nThere are no restrictions on how pieces " +
      "of different colors can touch one another.\n\n4.  Whenever a player " +
      "is unable to place one of his/her remaining pieces on the board, " +
      "that player's turn will be skipped until the end of the game.\n\n\n" +
      "End of Game\nThe game ends when all players are blocked from laying " +
      "down any more of their pieces.  This also includes any players who " +
      "may have placed all of their pieces on the board.  Scores are " +
      "tallied, and the player with the lowest score is the winner.\n\n\n" +
      "Scoring\nEach player's score is the count of the number of unit " +
      "squares in his/her remaining pieces (1 unit square = 1 point).\n"
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
    close = new JButton("Close");
    close.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  setVisible(false);
                }});
    buttonPan.add(close);
    buttonPan.setBackground(Color.BLACK);
    main.add(buttonPan);
    getContentPane().setBackground(Color.BLACK);
    
    add(main);
  }
}