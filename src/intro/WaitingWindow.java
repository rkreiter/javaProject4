package intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WaitingWindow extends JDialog {
	public WaitingWindow(JFrame mainFrame) {    
		super(mainFrame, "Waiting", false);
		setLayout(new FlowLayout());
		
		//Local Variables
		JPanel textPanel;
	    JLabel head;
	    JLabel imagepanel;

	    //base layout fields
	    textPanel = new JPanel(new BorderLayout());
  
	    head = new JLabel("Waiting for other players to join game...", 
	    		SwingConstants.CENTER);
	    head.setForeground(Color.GREEN);

	    textPanel.add(head, BorderLayout.NORTH);
    
	    imagepanel = new JLabel(new ImageIcon(getClass().getResource(
	    		"/images/StartScreen/waiting.gif")));
    
	    textPanel.add(imagepanel, BorderLayout.SOUTH);
	    textPanel.setBackground(Color.BLACK);
	    add(textPanel);
	    getContentPane().setBackground(Color.BLACK);
	}
}