package intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

public class ErrorWindow extends JDialog {
	//constructor
	public ErrorWindow(JFrame mainFrame, String message) {
		super(mainFrame, "ERROR", true);
		setLayout(new BorderLayout());

		JPanel textPanel;
		JLabel head;
		final JButton close;

		//base layout fields
		textPanel = new JPanel(new BorderLayout());
  
		head = new JLabel(message, SwingConstants.CENTER);
		head.setForeground(Color.WHITE);

		textPanel.add(head, BorderLayout.NORTH);
		textPanel.setBackground(Color.BLACK);
    
		close = new JButton("CLOSE");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		JPanel buttonPan = new JPanel(new FlowLayout());
		
		//Adding Key Stroke Listeners
  		ActionMap actionMap = buttonPan.getActionMap();
        InputMap inputMap = buttonPan.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        //Close
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "CLOSE");
        actionMap.put("CLOSE", new AbstractAction() {
      	  public void actionPerformed(ActionEvent arg0) {
      		  close.doClick();
      	  }
        });
        
        buttonPan.add(close);
        buttonPan.setOpaque(false);
    
		JPanel red = new JPanel(new FlowLayout());
		red.add(textPanel);
		red.setBorder(BorderFactory.createLineBorder(Color.red, 3));
		red.setOpaque(false);
    
		JPanel red2 = new JPanel(new FlowLayout());
		red2.add(red);
		//red2.setBorder(BorderFactory.createLineBorder(Color.red));
		red2.setOpaque(false);
    
		add(red2, BorderLayout.NORTH);
		add(buttonPan, BorderLayout.SOUTH);
		getContentPane().setBackground(Color.BLACK);
    
		pack();
		setBackground(Color.BLACK);
        setSize(getWidth()+100, getHeight()+10);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}