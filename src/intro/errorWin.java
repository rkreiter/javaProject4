package intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class errorWin extends JDialog
{
  //constructor
  public errorWin(JFrame mainFrame, String message)
  {
    super(mainFrame, "ERROR", true);
    setLayout(new GridLayout(2,1));

    JPanel textPanel;
    JLabel head;
    JButton close;

    //base layout fields
    textPanel = new JPanel(new BorderLayout());
  
    head = new JLabel(message);
    head.setForeground(Color.WHITE);

    textPanel.add(head, BorderLayout.NORTH);
    textPanel.setBackground(Color.BLACK);
    
    close = new JButton("CLOSE");
    close.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        setVisible(false);
      }});
    
    add(textPanel);
    add(close);
    getContentPane().setBackground(Color.BLACK);
    
    pack();
    setVisible(true);
  }
}