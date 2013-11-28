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

public class startFrame extends JFrame {
	JPanel mainScreen = new JPanel(new BorderLayout());
	JButton login;
	JButton loc;
	JButton instr;
	JButton tutor;
	JButton about;
	startFrame main = this;
	loginActionListener lac = new loginActionListener();
	LoginDialog log;
	JLabel title;
	JPanel buttons = new JPanel(new GridLayout(5,1));
	JPanel buttons2 = new JPanel(new FlowLayout());
	char playGame = 'n';
	String ipString;
	final int INTRO_WIDTH = 600, INTRO_HEIGHT = 600;
  
 
	public startFrame(String init) {
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
		loc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playGame = 'l';
				//System.out.println("PLAY = " + getPlay());
				
                //Figure out number of players for local play
                String number;
        		TextDialog numplayers;
        		do{
        			numplayers = new TextDialog(main, "Number Of Players",
        					"   Enter the number of players (1-4): ");
        			number = numplayers.getText();
        		}while(!(number).matches("[1-4]"));
        		int num = Integer.parseInt(number);
        		numplayers.setVisible(false);

        		//Get Names of Players
        		Player players[] = new Player[num];
                char colors[] = new char[] {'b', 'r', 'y', 'g'};
                for(int i = 0; i < num; ++i) {
                	TextDialog nameplayer;
                	String name;
                	do{
                		nameplayer = new TextDialog(main, "Name",
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
		instr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new howtoWin(main);
                System.out.println("HOW TO PLAY");
			}
		});

		//-----------------tutorial button------------------//
		tutor = new JButton("Tutorial");
		tutor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("TUTORIAL");
			}
		});


		//-----------------ABOUT BUTTON------------------//
		about = new JButton("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				new aboutWin(main);
				System.out.println("ABOUT");
			}
		});
    
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
	    this.setBackground(Color.BLACK);
	    this.setSize(600,600);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
  
	class loginActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//Figure out what IP address the user wants to connect to
			TextDialog ipDialog = new TextDialog(main, "IP Address",
					"   What IP would you like to connect to: ");
			ipString = ipDialog.getText();
			playGame = 'o';
            setVisible(false);
			System.out.println("LOGIN");
		}
	}
  
	public char getPlay(){
		return playGame;
	}
  
	public String getUsername(){
		return log.getName();
	}
	
	public String getIP(){
		return ipString;
	}
}