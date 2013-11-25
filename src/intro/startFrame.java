package intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class startFrame extends JFrame
{
  JPanel mainScreen = new JPanel(new BorderLayout());

  JButton play;
  JButton instr;
  JButton tutor;
  JButton about;
  JFrame main = this;

  JLabel title;
  JPanel buttons = new JPanel(new GridLayout(5,1));
  JPanel buttons2 = new JPanel(new FlowLayout());
  boolean playGame;

  final int INTRO_WIDTH = 600, INTRO_HEIGHT = 600;
  
 
  public startFrame(String init)
  {
    //set title
    super(init);
    setLayout(new BorderLayout());
    ImageIcon icon = new ImageIcon("src/images/StartScreen/introbackground.png"); 
    Image img = icon.getImage();
    icon = new ImageIcon(img);
    setContentPane(new JLabel(icon));
    setLayout(new BorderLayout());
    getContentPane().setBackground(Color.BLACK);

    title = new JLabel(new ImageIcon("src/images/StartScreen/title.png"));
    

    //--------------------PLAY BUTTON---------------------//

    play = new JButton("Play Da Game");
    play.addActionListener(new ActionListener()
                {
                  public void actionPerformed(ActionEvent e)
                  {
                	playGame = true;
                	//System.out.println("PLAY = " + getPlay());
                    setVisible(false);
                  }
                });

    //-----------------How to play button------------------//

    instr = new JButton("Dem Rulz");
    instr.addActionListener(new ActionListener()
                {
                  public void actionPerformed(ActionEvent e)
                  {
                    howtoWin how = new howtoWin(main);
                    
                    //general formatting for Jdialog window
                    how.pack();
                    how.setSize(how.getWidth()+10, how.getHeight()-220);
                    how.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    how.getContentPane().setBackground(Color.BLACK);
                    how.setVisible(true);
                    System.out.println("HOW TO PLAY");
                  }});

    //-----------------tutorial button------------------//

    tutor = new JButton("Tutorial");
    tutor.addActionListener(new ActionListener()
                {
                  public void actionPerformed(ActionEvent e)
                  {
                    System.out.println("TUTORIAL");
                  }});


    //-----------------ABOUT BUTTON------------------//
    about = new JButton("About");
    about.addActionListener(new ActionListener()
                {
                  public void actionPerformed(ActionEvent e)
                  {
                    aboutWin ab;
                    ab = new aboutWin(main);
                                       
                    //general formatting for Jdialog window
                    
                    ab.pack();
                    ab.setSize(ab.getWidth()+100, ab.getHeight()+10);
                    ab.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    ab.getContentPane().setBackground(Color.BLACK);
                    ab.setVisible(true);
                  }});


    JLabel fill = new JLabel("    ");
    fill.setBackground(Color.BLACK);

    buttons.add(play);
    buttons.add(instr);
    buttons.add(tutor);
    buttons.add(about);
    buttons.setOpaque(false);
    buttons2.add(buttons);
    buttons2.setOpaque(false);
    
    add(title, BorderLayout.NORTH);
    add(fill, BorderLayout.CENTER);
    add(buttons2, BorderLayout.SOUTH);
  }
  
  public boolean getPlay(){
	  return playGame;
  }
}