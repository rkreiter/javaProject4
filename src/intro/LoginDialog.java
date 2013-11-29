package intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import server.ClientServerSocket;

public class LoginDialog extends JDialog {
	//Global Variables
	JTextField username;
	JPasswordField password;
	JButton loginButton;
	JButton createButton;

	//constructor
	public LoginDialog(final startFrame mainFrame, String title, String q, 
			final ClientServerSocket theClient) {
		super(mainFrame, title, true);
		//Local Variables
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
    
	    //PASSWORD
	    JLabel pass = new JLabel("   Password: ", SwingConstants.RIGHT);
	    pass.setForeground(Color.WHITE);
	    mainPan.add(pass, BorderLayout.NORTH);

	    //set text field size and add to panel
	    password = new JPasswordField(15);
	    mainPan.add(password, BorderLayout.CENTER);
	    mainPan.setBackground(Color.BLACK);
    

	    loginButton = new JButton(" Login ");
	    loginButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(username.getText() != "" && password.getPassword().length != 0){
		    		String str = "Login " + username.getText() 
		    				+ " " + new String(password.getPassword());
					try{
						theClient.sendString(str, 0);
					}
					catch (Exception ee){
						System.out.println("Server quit on us... in login");
						System.exit(20);
					}
		    		setVisible(false);
		    		username.setText("");
	            	password.setText("");
	    		}
            }
	    });
	    
	  //Adding Key Stroke Listeners
  		ActionMap actionMap = buttons.getActionMap();
        InputMap inputMap = buttons.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        //Login
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "LOGIN");
        actionMap.put("LOGIN", new AbstractAction() {
      	  public void actionPerformed(ActionEvent arg0) {
      		  loginButton.doClick();
      	  }
        });
    
	    createButton = new JButton(" Create New User ");
	    createButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(username.getText() != "" && password.getPassword().length != 0){
		    		String str = "Create " + username.getText() + 
		    				" " + new String(password.getPassword());
					try{
						theClient.sendString(str, 0);
					}
					catch (Exception ee){
						System.out.println("Server quit on us... in login");
						System.exit(20);
					}
		    		setVisible(false);
		    		username.setText("");
	            	password.setText("");
	    		}
	    	}
	    });

	    //add button to panel
	    buttons.add(loginButton);
	    buttons.add(createButton);
	    buttons.setBackground(Color.BLACK);

	    //add panel to JDialog
	    add(mainPan, BorderLayout.NORTH);
	    add(buttons, BorderLayout.SOUTH);

	    pack();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setBackground(Color.BLACK);
		this.setVisible(false);
	}
  
	//returns the username
	public String getName() {
		return username.getText();
	}
	
	public String getPssword() {
		return new String(password.getPassword());
	}
}