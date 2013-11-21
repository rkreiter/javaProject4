package proj4board;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Layers 
{
  public Layers(Frame frame)
  {
    JLayeredPane mainLayer = new JLayeredPane();
    frame.add(mainLayer, BorderLayout.CENTER);

    JLabel label = new JLabel("LABEL", JLabel.CENTER);
    label.setBounds(100, 100, 200, 100);
    label.setOpaque(true);
    label.setBackground(Color.cyan);
    mainLayer.add(label, 1);

    JPanel subLayer = new JPanel(new BorderLayout());
    JLabel subLabel = new JLabel("SUBLABEL", JLabel.CENTER);
    subLabel.setBounds(20, 20, 200, 100);
    subLabel.setOpaque(true);
    subLabel.setBackground(Color.yellow);
    subLayer.add(subLabel, BorderLayout.SOUTH);
    mainLayer.add(subLabel, 2);

    frame.pack();
    frame.setSize(320, 240);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
