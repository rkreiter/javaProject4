package intro;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.*;


public class textDial extends JDialog
{
  //public variables
  JTextField textField;
  JButton okButton;

  //constructor
  public textDial(JFrame mainFrame, String title, String q)
  {
    super(mainFrame, title, true);

    JPanel southPan = new JPanel(new FlowLayout()); 
    setLayout(new BorderLayout());
    
    JLabel question = new JLabel(q);
    question.setForeground(Color.WHITE);
    
    //set question in labelpanel
    add(question, BorderLayout.NORTH);

    //set text field size and add to panel
    textField = new JTextField(15);
    add(textField, BorderLayout.CENTER);    

    //create Jbutton for OK with action listener
    okButton = new JButton(" OK ");
    okButton.addActionListener(new ActionListener()
                                  {
                                    public void actionPerformed(ActionEvent e)
                                    {
                                      //hide window after action
                                      setVisible(false);
                                    }});
    //add button to panel
    southPan.add(okButton);
    southPan.setBackground(Color.BLACK);

    //add panel to JDialog
    add(southPan, BorderLayout.SOUTH);
    getContentPane().setBackground(Color.BLACK);

    pack();
    setVisible(true);
  }
  
  //returns what the user entered in the text field
  public String getText()
  {
    return (textField.getText());
  }
}