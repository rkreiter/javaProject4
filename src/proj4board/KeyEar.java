package proj4board;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyEar implements KeyListener{
   	public void keyTyped(KeyEvent e) {
   		System.out.println(e + "   " + e.getSource());
   	}

   	public void keyPressed(KeyEvent e) {
   		System.out.println(1);
   	}

   	public void keyReleased(KeyEvent e) {
   		System.out.println(2);
   	}
}
