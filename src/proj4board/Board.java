package proj4board;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class Board
{
  public static void main(String[] args) throws IOException 
  {
    Frame frame = new Frame("Blokus");
    
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    frame.setVisible(true);
  }
}