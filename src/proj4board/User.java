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

public class User extends JPanel
{
  Image avatar;
  JPanel stats;
  JLabel name;
  JLabel score;
  
  public User(String name, Image avatar, Color c)
  {
    setLayout(new GridLayout(1,2));
    
    this.name = new JLabel(name, JLabel.CENTER);
    this.score = new JLabel("89", JLabel.CENTER);
    stats = new JPanel(new GridLayout(2,1));
    stats.add(this.name);
    stats.add(this.score);
    stats.setBorder((BorderFactory.createLineBorder(Color.black)));
    stats.setBackground(c);
    
    this.avatar = avatar.getScaledInstance(680/4, 
                                           680/4,
                                           BufferedImage.SCALE_DEFAULT);

    new JPanel(new FlowLayout());
    add(new JLabel( new ImageIcon(this.avatar)));
    add(stats);
  }
}