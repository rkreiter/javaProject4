package intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class endWin extends JDialog
{
  //constructor
  public endWin(JFrame mainFrame, char lw)
  {
    super(mainFrame, "GAME OVER", false);
    setLayout(new FlowLayout());

    JPanel textPanel;
    JLabel head;
    JLabel imagepanel;

    //base layout fields
    textPanel = new JPanel(new BorderLayout());
  
    if(lw == 'l'){
    	head = new JLabel("YOU LOOSE...");
    	head.setForeground(Color.RED);
    }
    else{
    	head = new JLabel("YOU WIN!!!");
    	head.setForeground(Color.GREEN);
    }

    textPanel.add(head, BorderLayout.NORTH);
    
    if(lw == 'l')
    	imagepanel = new JLabel(
    			new ImageIcon("src/images/StartScreen/waiting.gif"));
    else
    	imagepanel = new JLabel(
    			new ImageIcon("src/images/StartScreen/waiting.gif"));
    
    textPanel.add(imagepanel, BorderLayout.SOUTH);
    textPanel.setBackground(Color.BLACK);
    add(textPanel);
    getContentPane().setBackground(Color.BLACK);
  }
}