package proj4board;

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
	Color color;
  
	public User(String name, Image avatar, Color c) {
		setLayout(new GridLayout(1,2));
    
		this.name = new JLabel(name, JLabel.CENTER);
		this.score = new JLabel("89", JLabel.CENTER);
		this.color = c;
		stats = new JPanel(new GridLayout(2,1));
		if(c == Color.BLUE) {
			this.name.setForeground(Color.WHITE);
			this.score.setForeground(Color.WHITE);
		}
		stats.add(this.name);
		stats.add(this.score);
		stats.setBackground(c);
    
		this.avatar = avatar.getScaledInstance(Frame.PLAYERWIDTH/2, 
				Frame.PLAYERWIDTH/2, BufferedImage.SCALE_DEFAULT);

		new JPanel(new FlowLayout());
		add(new JLabel( new ImageIcon(this.avatar)));
		setBorder((BorderFactory.createLineBorder(Color.DARK_GRAY, 3)));
		add(stats);
	}
}
