package intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

public class AboutWindow extends JDialog {
	//constructor
	public AboutWindow(JFrame mainFrame) {
		super(mainFrame, "About", true);
		setLayout(new FlowLayout());

	    JPanel textPanel;
	    final JButton close;
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
	    close.setFocusPainted(false);
	    close.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		setVisible(false);
	    	}
	    });
	    
	    //Adding Key Stroke Listeners
  		ActionMap actionMap = textPanel.getActionMap();
        InputMap inputMap = textPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        //Close
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "CLOSE");
        actionMap.put("CLOSE", new AbstractAction() {
      	  public void actionPerformed(ActionEvent arg0) {
      		  close.doClick();
      	  }
        });
	          
	    textPanel.add(close, BorderLayout.SOUTH);
	    getContentPane().setBackground(Color.BLACK);
	    this.pack();
        this.getContentPane().setBackground(Color.BLACK);
		this.setSize(this.getWidth()+100, this.getHeight()+10);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}