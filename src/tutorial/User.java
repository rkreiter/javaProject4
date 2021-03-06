package tutorial;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class User extends JPanel {
	Image avatar;
	JPanel stats;
	JLabel name;
	JLabel score;
	public JLabel wins, loses, avg;
	Color color;
  
	public User(String name, Image avatar, Color c) {
		setLayout(new GridLayout(1,2));
    
		this.name = new JLabel(name, JLabel.CENTER);
		this.score = new JLabel("89", JLabel.CENTER);
		this.color = c;
		this.wins = new JLabel("Wins: N/A", JLabel.CENTER);
		this.loses= new JLabel("Loses: N/A", JLabel.CENTER);
		this.avg = new JLabel("Average Score: N/A", JLabel.CENTER);
		stats = new JPanel(new GridLayout(5,1));
		
		Color col = Color.WHITE.brighter();
		if(c == Color.YELLOW|| c == Color.GREEN)
			col = Color.DARK_GRAY;
		this.name.setForeground(col);
		this.score.setForeground(col);
		this.wins.setForeground(col);
		this.loses.setForeground(col);
		this.avg.setForeground(col);
			
		stats.add(this.name);
		stats.add(this.score);
		stats.add(this.wins);
		stats.add(this.loses);
		stats.add(this.avg);
		stats.setBackground(c);
    
		this.avatar = avatar.getScaledInstance(tutFrame.PLAYERWIDTH/2, 
				tutFrame.PLAYERWIDTH/2, BufferedImage.SCALE_DEFAULT);

		new JPanel(new FlowLayout());
		add(new JLabel( new ImageIcon(this.avatar)));
		setBorder((BorderFactory.createLineBorder(Color.DARK_GRAY, 3)));
		add(stats);
	}
}
