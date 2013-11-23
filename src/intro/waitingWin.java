package intro;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class waitingWin extends JDialog
{
  //constructor
  public waitingWin(JFrame mainFrame)
  {
    super(mainFrame, "Waiting", false);
    setLayout(new FlowLayout());

    JPanel textPanel;
    JLabel head;

    //base layout fields
    textPanel = new JPanel(new FlowLayout());
  
    head = new JLabel("Waiting for other players to join game...");
    head.setForeground(Color.WHITE);

    textPanel.add(head);
    textPanel.setBackground(Color.BLACK);
    add(textPanel);

    getContentPane().setBackground(Color.BLACK);
  }
}