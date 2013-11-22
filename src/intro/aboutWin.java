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

public class aboutWin extends JDialog
{
  //constructor
  public aboutWin(JFrame mainFrame)
  {
    super(mainFrame, "About", true);
    setLayout(new FlowLayout());

    JPanel textPanel;
    JButton close;
    JLabel head;
    JPanel names;
    JLabel name;

    //base layout fields
    textPanel = new JPanel(new BorderLayout());
    names = new JPanel(new GridLayout(6,1));
  
    head = new JLabel("This version of BLOKUS was created by:");
    head.setForeground(Color.WHITE);
    name = new JLabel("Kyle Hildebrandt", SwingConstants.CENTER);
    name.setForeground(((((Color.BLUE).brighter()).brighter()).brighter()));
    names.add(name);
    name = new JLabel("Ryan Kreiter", SwingConstants.CENTER);
    name.setForeground(Color.RED);
    names.add(name);
    name = new JLabel("Troy Nemeth", SwingConstants.CENTER);
    name.setForeground(Color.GREEN);
    names.add(name);
    name = new JLabel("Stephen Osentoski", SwingConstants.CENTER);
    name.setForeground(Color.YELLOW);
    names.add(name);
    name = new JLabel("Asher Perlmutter", SwingConstants.CENTER);
    name.setForeground(Color.MAGENTA);
    names.add(name);
    name = new JLabel("_______________");
    names.add(name);

    textPanel.add(head, BorderLayout.NORTH);
    textPanel.add(names, BorderLayout.CENTER);
    add(textPanel);

    close = new JButton("Close");
    close.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  setVisible(false);
                }});
    textPanel.add(close, BorderLayout.SOUTH);
  }
}