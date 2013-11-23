package intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
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
    JLabel imagepanel;

    //base layout fields
    textPanel = new JPanel(new BorderLayout());
  
    head = new JLabel("\nWaiting for other players to join game...");
    head.setForeground(Color.GREEN);

    textPanel.add(head, BorderLayout.NORTH);
    
    
    imagepanel = new JLabel(
    		new ImageIcon("src/images/StartScreen/waiting.gif"));
    
    textPanel.add(imagepanel, BorderLayout.SOUTH);
    textPanel.setBackground(Color.BLACK);
    add(textPanel);
    getContentPane().setBackground(Color.BLACK);
  }
}