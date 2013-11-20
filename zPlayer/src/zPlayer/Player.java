package zPlayer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.util.*;

public class Player 
{
  private Border blackline = BorderFactory.createLineBorder(Color.black);
  private String name;
  private int score;
  private int playernum;
  private JPanel PlayPanel;
  private TitledBorder BlackBorder;
  private ImageIcon image;
  
  private Color ColorGenerator(int playernum)
  {
    switch (playernum)
    {
      case 1:
        return Color.BLUE;
      case 2:
        return Color.RED;
      case 3:
        return Color.YELLOW;
      case 4:
        return Color.GREEN;     
      default:
        return Color.BLACK;
    }
  }
  
  private JPanel makePanel()
  {
    JLabel NameLabel = new JLabel(name);
    JLabel ScoreLabel = new JLabel("Score: " + String.valueOf(this.score));
    JLabel imageIcon = new JLabel(image);
      
    JPanel ALL = new JPanel(new BorderLayout());
    
    JPanel allinfo = new JPanel(new GridLayout(1,2));
    JPanel avatar = new JPanel(new FlowLayout());
    JPanel playerpan = new JPanel(new GridLayout(2,1));
    JPanel[] Pieces = new JPanel[5];
    
    for(int i=0; i<5; i++)
      Pieces[i] = new JPanel(new FlowLayout());
      
    avatar.add(imageIcon);
    playerpan.add(NameLabel);
    playerpan.add(ScoreLabel);
    
    allinfo.add(imageIcon);
    allinfo.add(playerpan);  
    allinfo.setBorder(BlackBorder);
    
    ALL.add(allinfo, BorderLayout.NORTH);
    ALL.setBackground(ColorGenerator(playernum));
    
    return ALL;
  }
  
  Player(String str, int num, ImageIcon icon)
  {
    name = str;
    playernum = num;
    score = 81;
    image = icon;
    BlackBorder = BorderFactory.createTitledBorder(blackline, String.valueOf(playernum));
    PlayPanel = makePanel();
  }
}
