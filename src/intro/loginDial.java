package intro;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.*;


public class loginDial extends JDialog
{
  //public variables
  JTextField username;
  JTextField password;
  JButton okButton;
  JButton createButt;

  //constructor
  public loginDial(final startFrame mainFrame, String title, String q)
  {
    super(mainFrame, title, true);

    JPanel mainPan = new JPanel(new GridLayout(2,2)); 
    JPanel buttons = new JPanel(new GridLayout(1,2)); 
    setLayout(new BorderLayout());
    
    JLabel question = new JLabel(q, SwingConstants.RIGHT);
    question.setForeground(Color.WHITE);
    
    //set question in labelpanel
    mainPan.add(question);

    //set text field size and add to panel
    username = new JTextField(15);
    mainPan.add(username, BorderLayout.CENTER);   
    
    
    JLabel pass = new JLabel("   Password: ", SwingConstants.RIGHT);
    pass.setForeground(Color.WHITE);
    
    //set question in labelpanel
    mainPan.add(pass, BorderLayout.NORTH);

    //set text field size and add to panel
    password = new JTextField(15);
    mainPan.add(password, BorderLayout.CENTER);
    
    mainPan.setBackground(Color.BLACK);
    

    //create Jbutton for OK with action listener
    okButton = new JButton(" Login ");
    okButton.addActionListener(new ActionListener()
                                  {
                                    public void actionPerformed(ActionEvent e)
                                    {
                                      //hide window after action
                                      mainFrame.login.removeActionListener(mainFrame.lac);
                                      mainFrame.login.setText("     Play Online    ");
                                   
                                      mainFrame.login.addActionListener(new ActionListener()
                                      {
                                        public void actionPerformed(ActionEvent e)
                                        {
                                          //hide window after action
                                          mainFrame.playGame = 'o';
                                          setVisible(false);
                                        }});
                                      setVisible(false);
                                    }});
    
    createButt = new JButton(" Create New User ");
    createButt.addActionListener(new ActionListener()
                                  {
                                    public void actionPerformed(ActionEvent e)
                                    {
                                    	mainFrame.login.removeActionListener(mainFrame.lac);
                                        mainFrame.login.setText("     Play Online    ");
                                     
                                        mainFrame.login.addActionListener(new ActionListener()
                                        {
                                          public void actionPerformed(ActionEvent e)
                                          {
                                            //hide window after action
                                        	  mainFrame.playGame = 'o';
                                              setVisible(false);
                                          }});
                                        setVisible(false);
                                    }});
    //add button to panel
    buttons.add(okButton);
    buttons.add(createButt);
    buttons.setBackground(Color.BLACK);

    //add panel to JDialog
    add(mainPan, BorderLayout.NORTH);
    add(buttons, BorderLayout.SOUTH);
    getContentPane().setBackground(Color.BLACK);

    pack();
    setVisible(true);
  }
  
  //returns what the user entered in the text field
  public String getText()
  {
    return (username.getText());
  }
}