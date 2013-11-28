package intro;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.*;


public class TextDialog extends JDialog {
	//public variables
	JTextField textField;
	JButton okButton;

	//constructor
	public TextDialog(JFrame mainFrame, String title, String q) {
		super(mainFrame, title, true);

	    JPanel panel = new JPanel(new FlowLayout()); 
	    setLayout(new BorderLayout());
    
	    //insert provided text
	    JLabel question = new JLabel(q);
	    question.setForeground(Color.WHITE);
	    add(question, BorderLayout.NORTH);

	    //set text field size and add to panel
	    textField = new JTextField(15);
	    add(textField, BorderLayout.CENTER);    

	    //create Jbutton for OK with action listener
	    okButton = new JButton(" OK ");
	    okButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		setVisible(false);
	    	}
	    });
    
	    //add button to panel
	    panel.add(okButton);
	    panel.setBackground(Color.BLACK);

	    //add panel to JDialog
	    add(panel, BorderLayout.SOUTH);
	    getContentPane().setBackground(Color.BLACK);

	    pack();
	    setSize(getWidth()+20, getHeight());
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    setBackground(Color.BLACK);
	    setVisible(true);
	}
  
	//returns what the user entered in the text field
	public String getText() {
		return textField.getText();
	}
}