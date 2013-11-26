package intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
    	head = new JLabel("YOU LOSE...", SwingConstants.CENTER);
    	head.setForeground(Color.RED);
    }
    else{
    	head = new JLabel("YOU WIN!!!", SwingConstants.CENTER);
    	head.setForeground(Color.GREEN);
    }

    textPanel.add(head, BorderLayout.NORTH);
    
    if(lw == 'l')
    	imagepanel = new JLabel(
    			new ImageIcon(getClass().getResource("/images/StartScreen/lose.gif")));
    else
    	imagepanel = new JLabel(
    			new ImageIcon(getClass().getResource("/images/StartScreen/win.gif")));
    
    textPanel.add(imagepanel, BorderLayout.SOUTH);
    textPanel.setBackground(Color.BLACK);
    add(textPanel);
    getContentPane().setBackground(Color.BLACK);
  }
  public endWin(JFrame mainFrame, char lw, String name)
  {
    super(mainFrame, "GAME OVER", false);
    setLayout(new FlowLayout());

    JPanel textPanel;
    JLabel head;
    JLabel imagepanel;

    //base layout fields
    textPanel = new JPanel(new BorderLayout());
  
	head = new JLabel(name + " WINS!!!", SwingConstants.CENTER);
	head.setForeground(Color.GREEN);

    textPanel.add(head, BorderLayout.NORTH);
    
    imagepanel = new JLabel(
    		new ImageIcon(getClass().getResource("/images/StartScreen/win.gif")));
    
    textPanel.add(imagepanel, BorderLayout.SOUTH);
    textPanel.setBackground(Color.BLACK);
    add(textPanel);
    getContentPane().setBackground(Color.BLACK);
  }
  
}