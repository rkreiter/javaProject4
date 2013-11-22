package src.intro;

import static java.lang.System.out;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.applet.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.net.*;

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
      "1.  Each player chooses a color and places that set of 21 pieces in " + 
      "front of his/her side of the board.  The order of play is as follows: "+
      "blue, yellow, red, and then green.\n\n 2.  The first player (blue) " +
      "any of his/her pieces in a corner square.  Play proceeds clockwise " +
      "around the board (yellow, red, and green), each player putting their " +
      "first piece down in one of the corner squares.\n\n3.  Play continues as "+
      "each player lays down one piece during a turn.\n\t- Each new piece must"+
      " touch at least one other piece of the same color, but only at the " +
      "corners.\n\t- No flat edges of same color pieces can touch.\n\nThere " +
      "are no restrictions on how pieces of different colors can touch one " + 
      "another.\n\n4.  Whenever a player is unable to place one of his/her " + 
      "remaining pieces on the board, that player must pass his/her turn.\n\n" +
      "\nEnd of Game\nThe game ends when all players are blocked from laying " +
      "down any more of their pieces.  This also includes any players who may" +
      " have placed all of their pieces on the board.  Scores are tallied, " + 
      "and the player with the highest score is the winner.\n\n\nScoring\n" +
      "Each player counts the number of unit squares in his/her remaining " +
      "pieces (1 unit square = -1 point).\n\nA player earns +15 points if " +
      "all his/her pieces have been placed on the board plus 5 additional " +
      "bonus points if the last piece placed on the board was the smallest " +
      "piece (one square).\n\nCitation: " +
      "www.educationallearninggames.com/how-to-play-blokus-game-rules.asp"
    );

    rulzArea.setColumns(80);
    rulzArea.setLineWrap(true);
    rulzArea.setWrapStyleWord(true);
    rulzArea.setEditable(false);
    rulzArea.setOpaque(false);
    rulzArea.setForeground(Color.WHITE);

  
    JLabel fill = new JLabel("    ");
    fill.setBackground(Color.BLACK);

    main.add(rulzArea);
    //main.add(fill);

    JPanel buttonPan = new JPanel(new FlowLayout());
    close = new JButton("Close");
    close.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  setVisible(false);
                }});
    buttonPan.add(close);
    main.add(buttonPan);

    add(main);
    setBackground(Color.BLACK);
    pack();
  }
}