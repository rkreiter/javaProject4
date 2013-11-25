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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import database.BlokusDB;


public class loginDial extends JDialog
{
  //public variables
  JTextField username;
  JPasswordField password;
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
    password = new JPasswordField(15);
    mainPan.add(password, BorderLayout.CENTER);
    
    mainPan.setBackground(Color.BLACK);
    

    //create Jbutton for OK with action listener
    okButton = new JButton(" Login ");
    okButton.addActionListener(new ActionListener()
                                  {
                                    public void actionPerformed(ActionEvent e)
                                    {
                                      BlokusDB db = new BlokusDB();
                                      String inputPass = new String(password.getPassword());
                                      if(!db.userLogin(username.getText(), inputPass))
                                      {
                                    	  username.setText("");
                                    	  password.setText("");
                                    	  errorWin err = new errorWin(mainFrame, "Invalid Username/Password!");
                                    	  err.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                    	  return;
                                      }
                                    	
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
                                          mainFrame.setVisible(false);
                                        }});
                                      setVisible(false);
                                    }});
    
    createButt = new JButton(" Create New User ");
    createButt.addActionListener(new ActionListener()
                                  {
                                    public void actionPerformed(ActionEvent e)
                                    {
                                    	BlokusDB db = new BlokusDB();
                                        String inputPass = new String(password.getPassword());
                                        if(!db.createUser(username.getText(), inputPass))
                                        {
                                      	  username.setText("");
                                      	  password.setText("");
                                      	  errorWin err = new errorWin(mainFrame, "Username Taken");
                                      	  err.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                      	  return;
                                        }
                                    	
                                    	mainFrame.login.removeActionListener(mainFrame.lac);
                                        mainFrame.login.setText("     Play Online    ");
                                     
                                        mainFrame.login.addActionListener(new ActionListener()
                                        {
                                          public void actionPerformed(ActionEvent e)
                                          {
                                            //hide window after action
                                        	  mainFrame.playGame = 'o';
                                              setVisible(false);
                                              mainFrame.setVisible(false);
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
  }
  
  //returns what the user entered in the text field
  public String getName()
  {
    return (username.getText());
  }
}