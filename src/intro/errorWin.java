package intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class errorWin extends JDialog
{
  //constructor
  public errorWin(JFrame mainFrame, String message)
  {
    super(mainFrame, "ERROR", true);
    setLayout(new BorderLayout());

    JPanel textPanel;
    JLabel head;
    JButton close;

    //base layout fields
    textPanel = new JPanel(new BorderLayout());
  
    head = new JLabel(message, SwingConstants.CENTER);
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
    
    JPanel red = new JPanel(new FlowLayout());
    red.add(textPanel);
    red.setBorder(BorderFactory.createLineBorder(Color.red, 3));
    red.setOpaque(false);
    
    JPanel red2 = new JPanel(new FlowLayout());
    red2.add(red);
    //red2.setBorder(BorderFactory.createLineBorder(Color.red));
    red2.setOpaque(false);
    
    add(red2, BorderLayout.NORTH);
    add(new JPanel(new FlowLayout()).add(close), BorderLayout.SOUTH);
    getContentPane().setBackground(Color.BLACK);
    
    pack();
    setSize(getWidth()+20, getHeight()+10);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBackground(Color.BLACK);
    setVisible(true);
  }
}