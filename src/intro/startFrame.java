package intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class startFrame extends JFrame{
	
  JPanel mainScreen = new JPanel(new BorderLayout());

  JButton create;
  JButton join;
  JButton instr;
  JButton tutor;
  JButton about;
  JFrame main = this;

  JLabel title;
  JPanel buttons = new JPanel(new GridLayout(5,1));
  
  final static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
  final static int INTRO_WIDTH = (int) SCREEN_SIZE.getWidth();
  final static int INTRO_HEIGHT = (int) SCREEN_SIZE.getHeight();
  
  public startFrame(String intit){
	  
    //set title
    super(intit);
    setLayout(new BorderLayout());
    ImageIcon icon = new ImageIcon("src/images/introbackground.png"); 
    Image img = icon.getImage();  
    Image newimg = img.getScaledInstance(INTRO_WIDTH, INTRO_HEIGHT,
    										java.awt.Image.SCALE_SMOOTH);  
    icon = new ImageIcon(newimg);
    setContentPane(new JLabel(icon));
    setLayout(new BorderLayout());

    title = new JLabel("BLOKUS", SwingConstants.CENTER);
    create = new JButton("Create A Game");
    join = new JButton("Join A Game");
    instr = new JButton("How To Play");
    tutor = new JButton("Tutorial");
    about = new JButton("About");

    JLabel fill = new JLabel("    ");
    fill.setBackground(Color.BLACK);
    title.setForeground(Color.YELLOW);

    buttons.add(create);
    buttons.add(join);
    buttons.add(instr);
    buttons.add(tutor);
    buttons.add(about);

    
    add(title, BorderLayout.NORTH);
    add(fill, BorderLayout.CENTER);
    add(buttons, BorderLayout.EAST);

    pack();  
  }
}