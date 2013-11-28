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
  JButton loginButt;
  JButton createButt;
  startFrame main;

  //constructor
  public loginDial(final startFrame mainFrame, String title, String q)
  {
    super(mainFrame, title, true);
    
    main = mainFrame;
    JPanel mainPan = new JPanel(new GridLayout(2,2)); 
    JPanel buttons = new JPanel(new GridLayout(1,2)); 
    setLayout(new BorderLayout());
    
    //username
    JLabel question = new JLabel(q, SwingConstants.RIGHT);
    question.setForeground(Color.WHITE);
    
    mainPan.add(question);

    //set text field size and add to panel
    username = new JTextField(15);
    mainPan.add(username, BorderLayout.CENTER);   
    
    
    JLabel pass = new JLabel("   Password: ", SwingConstants.RIGHT);
    pass.setForeground(Color.WHITE);
    
    mainPan.add(pass, BorderLayout.NORTH);

    //set text field size and add to panel
    password = new JPasswordField(15);
    mainPan.add(password, BorderLayout.CENTER);
    
    mainPan.setBackground(Color.BLACK);
    

    loginButt = new JButton(" Login ");
    loginButt.addActionListener(new ActionListener()
                                  {
                                    public void actionPerformed(ActionEvent e)
                                    {
                                      BlokusDB db = new BlokusDB();
                                      String inputPass = new String(password.getPassword());
                                      onlineListener ol = new onlineListener();
                                      
                                      //Check username and password
                                      if(!db.userLogin(username.getText(), inputPass))
                                      {
                                    	  username.setText("");
                                    	  password.setText("");
                                    	  errorWin err = new errorWin(main, "Invalid Username/Password!");
                                    	  err.pack();
                                          err.setSize(err.getWidth()+100, err.getHeight()+10);
                                    	  err.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                    	  setVisible(true);
                                    	  return;
                                      }
                                      
                                      //changes login button to play online
                                      main.login.removeActionListener(main.lac);
                                      main.login.setText("     Play Online    ");
                                      
                                      //play online actlist
                                      main.login.addActionListener(ol);
                                      //closes login dial
                                      setVisible(false);
                                    }
                                  }
    						   );
    
    createButt = new JButton(" Create New User ");
    createButt.addActionListener(new ActionListener()
                                  {
                                    public void actionPerformed(ActionEvent e)
                                    {
                                    	BlokusDB db = new BlokusDB();
                                        String inputPass = new String(password.getPassword());
                                        onlineListener ol = new onlineListener();
                                        
                                        //checks if username exists
                                        if(!db.createUser(username.getText(), inputPass))
                                        {
                                      	  username.setText("");
                                      	  password.setText("");
                                      	  errorWin err = new errorWin(main, "Username Taken");
                                      	  err.pack();
                                          err.setSize(err.getWidth()+100, err.getHeight()+10);
                                      	  err.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                      	  err.setVisible(true);
                                      	  return;
                                        }
                                    	
                                        //changes login button to play online
                                        main.login.removeActionListener(main.lac);
                                        main.login.setText("     Play Online    ");
                                        
                                        //play online actlist
                                        main.login.addActionListener(ol);
                                        //closes login dial
                                        setVisible(false);
                                    }
                                  }
    							);
    //add button to panel
    buttons.add(loginButt);
    buttons.add(createButt);
    buttons.setBackground(Color.BLACK);

    //add panel to JDialog
    add(mainPan, BorderLayout.NORTH);
    add(buttons, BorderLayout.SOUTH);
    getContentPane().setBackground(Color.BLACK);

    pack();
  }
  
  public class onlineListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      //ask for server IP address
      textDial ipDial = new textDial(main, "Server IP Address",
				"   Enter the IP Address of the running server: ");
	  main.serverIP = ipDial.getText();
    	
      //closes intro screen
      main.playGame = 'o';
      setVisible(false);
      main.setVisible(false);
    }
  }
  
  //returns the username
  public String getName()
  {
    return (username.getText());
  }
}