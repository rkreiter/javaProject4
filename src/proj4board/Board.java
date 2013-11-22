package proj4board;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Board
{
  private static JLayeredPane layered = new JLayeredPane();
  
  public static void main(String[] args) 
  {
    Frame frame = new Frame("Board");
    Layers main = new Layers(frame);
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    frame.setVisible(true);
  }
}