package intro;

import javax.swing.JFrame;

public class Blokus{
  
  public static void main(String [] args){
    
    startFrame init = new startFrame("Welcome To Blokus");
    init.pack();
    init.setVisible(true);
    init.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    return;
  }
}