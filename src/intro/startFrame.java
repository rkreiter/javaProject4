package intro;

import game.Board;
import game.Player;

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

import proj4board.Frame;


public class startFrame extends JFrame
{
  JPanel mainScreen = new JPanel(new BorderLayout());

  JButton login;
  JButton loc;
  JButton instr;
  JButton tutor;
  JButton about;
  startFrame main = this;
  loginActionListener lac = new loginActionListener();
  loginDial log;

  JLabel title;
  JPanel buttons = new JPanel(new GridLayout(5,1));
  JPanel buttons2 = new JPanel(new FlowLayout());
  char playGame = 'n';

  final int INTRO_WIDTH = 600, INTRO_HEIGHT = 600;
  
 
  public startFrame(String init)
  {
    //set title
    super(init);
    setLayout(new BorderLayout());
    ImageIcon icon = new ImageIcon(getClass().getResource(
    					"/images/StartScreen/introbackground.png")); 
    Image img = icon.getImage();
    icon = new ImageIcon(img);
    setContentPane(new JLabel(icon));
    setLayout(new BorderLayout());
    getContentPane().setBackground(Color.BLACK);

    title = new JLabel(new ImageIcon(getClass().getResource(
    								"/images/StartScreen/title.png")));
    
    
    //-----------------LOGIN BUTTON/PLAY ONLINE------------------//
    login = new JButton("Login For Online");
    login.addActionListener(lac);
    

    //--------------------LOCAL BUTTON---------------------//

    loc = new JButton("Play Locally");
    loc.addActionListener(new ActionListener()
                {
                  public void actionPerformed(ActionEvent e)
                  {
                	playGame = 'l';
                	//System.out.println("PLAY = " + getPlay());
                	
                	//Figure out number of players for local play
                	String number;
        			textDial numplayers;
        			do{
        				numplayers = new textDial(main, "Number Of Players",
        						"   Enter the number of players (1-4): ");
        				number = numplayers.getText();
        			}while(!(number).matches("[1-4]"));
        			int num = Integer.parseInt(number);
                    numplayers.setVisible(false);


                    //Get Names of Players
                    Player players[] = new Player[num];
                    char colors[] = new char[] {'b', 'r', 'y', 'g'};
                    for(int i = 0; i < num; ++i){
                    	textDial nameplayer;
                    	String name;
                		do{
                	    	nameplayer = new textDial(main, "Name",
                					"   Player " + (i+1) + 
                					", enter your name (no spaces allowed):  ");
                	    	name = nameplayer.getText();
                		}while(name.contains(" ") || name.isEmpty());
                		nameplayer.setVisible(false);
                		players[i] = new Player(name, colors[i]);
                    }
                    
                    //Create info
            		Board board = new Board();
            		Player player = players[0];
            	
            		Frame frame = new Frame("Blokus", board, players, player, 0, null);
            		frame.getContentPane().setBackground(Color.DARK_GRAY);
            		frame.pack();
            		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            		frame.setVisible(true);   
            		setVisible(false);
                  }
                });

    //-----------------How to play button------------------//

    instr = new JButton("Rules");
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
    
    
    
    
    //---------------FILL MAINFRAME------------------//
    JLabel fill = new JLabel("    ");
    fill.setBackground(Color.BLACK);
    buttons.add(login);
    buttons.add(loc);
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
  
  class loginActionListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      //open login win
      log = new loginDial(main,"Login", "   Enter username: ");
      log.pack();
      log.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      log.getContentPane().setBackground(Color.BLACK);
      log.setVisible(true);
      System.out.println("LOGIN");
    }
  }
  
  public char getPlay(){
	  return playGame;
  }
  public String getUsername(){
	  return log.getName();
  }
}