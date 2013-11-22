package src.intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
 
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Blokus{
  
  public static void main(String [] args){
    
    startFrame init = new startFrame("Welcome To Blokus");
    init.setBackground(Color.BLACK);
    init.setSize(600,600);
    init.setVisible(true);
    init.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    return;
  }
}