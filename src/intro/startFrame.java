package intro;

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


public class startFrame extends JFrame
{
  JPanel mainScreen = new JPanel(new BorderLayout());

  JButton create;
  JButton join;
  JButton instr;
  JButton tutor;
  JButton about;
  JFrame main = this;

  JLabel title;
  JPanel buttons = new JPanel(new GridLayout(5,1));
  
 
  public startFrame(String intit)
  {
    //set title
    super(intit);
    setLayout(new BorderLayout());
    setContentPane(new JLabel(new ImageIcon("images/introbackground.png")));
    setLayout(new BorderLayout());

    title = new JLabel("BLOKUS", SwingConstants.CENTER);
    create = new JButton("Create A Game");
    join = new JButton("Join A Game");
    instr = new JButton("How To Play");
    tutor = new JButton("Tutorial");
    about = new JButton("About");

    JLabel fill = new JLabel("    ");
    fill.setBackground(Color.BLACK);
    title.setForeground(Color.YELLOW);

    buttons.add(create);
    buttons.add(join);
    buttons.add(instr);
    buttons.add(tutor);
    buttons.add(about);

    
    add(title, BorderLayout.NORTH);
    add(fill, BorderLayout.CENTER);
    add(buttons, BorderLayout.SOUTH);

    pack();
  }
}