package intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class aboutWin extends JDialog
{
  //constructor
  public aboutWin(JFrame mainFrame)
  {
    super(mainFrame, "About", true);
    setLayout(new FlowLayout());

    JPanel textPanel;
    JButton close;
    JLabel head;
    JPanel names;
    JLabel name;

    //base layout fields
    textPanel = new JPanel(new BorderLayout());
    names = new JPanel(new GridLayout(6,1));
  
    head = new JLabel("This version of BLOKUS was created by:");
    head.setForeground(Color.WHITE);
    name = new JLabel("Kyle Hildebrandt", SwingConstants.CENTER);
    name.setForeground(Color.BLUE);
    names.add(name);
    name = new JLabel("Ryan Kreiter", SwingConstants.CENTER);
    name.setForeground(Color.RED);
    names.add(name);
    name = new JLabel("Troy Nemeth", SwingConstants.CENTER);
    name.setForeground(Color.GREEN);
    names.add(name);
    name = new JLabel("Stephen Osentoski", SwingConstants.CENTER);
    name.setForeground(Color.YELLOW);
    names.add(name);
    name = new JLabel("Asher Perlmutter", SwingConstants.CENTER);
    name.setForeground(Color.MAGENTA);
    names.add(name);
    name = new JLabel("");
    names.add(name);
    names.setBackground(Color.BLACK);

    textPanel.add(head, BorderLayout.NORTH);
    textPanel.add(names, BorderLayout.CENTER);
    textPanel.setBackground(Color.BLACK);
    add(textPanel);

    close = new JButton("Close");
    close.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  setVisible(false);
                }});
    textPanel.add(close, BorderLayout.SOUTH);
    getContentPane().setBackground(Color.BLACK);
  }
}