package zPlayer;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.io.FileFilter;
import java.io.File;
import java.net.URISyntaxException;

import java.util.Random;

import static java.lang.System.out;
import java.util.*;

public class MainFrame extends JFrame
{ 
  public Player[] playerarr;
  
  public MainFrame(String title)
  {
    super(title);
    
    JPanel LeftPlayers = new JPanel(new GridLayout(1,2));
    JPanel RightPlayers = new JPanel(new GridLayout(1,2));
    
    ImageIcon image = new ImageIcon("Single.png");  
    
    playerarr = new Player[4];
    
    for (int i=0; i<4; i++)
    {
      playerarr[i] = new Player("Player " + String.valueOf(i+1), i+1, image);
      if (i < 2)
        LeftPlayers.add(playerarr[i])
    }
    
  }
}
