package server;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.event.*;
import javax.swing.*;

public class ImageEx extends JFrame
{

    private ImageIcon image1;
    private JLabel label1;
    private JButton rotateClock;
    private JButton rotateCounter;
    private JButton flipVert;
    private JButton flipHor;
    private ButtonListener rotateAction;

    ImageEx(int imageNum){
        setLayout(new FlowLayout());

        image1 = new ImageIcon("src/images/Blue/" + imageNum + ".png");
        label1 = new JLabel(image1);
        add(label1);

        rotateClock = new JButton("Rotate Clockwise");
        rotateCounter = new JButton("Rotate Counter");
        flipVert = new JButton("Flip Vertical Axis");
        flipHor = new JButton("Flip Horizontal Axis");
        rotateAction = new ButtonListener();

        rotateClock.addActionListener(rotateAction);
        rotateCounter.addActionListener(rotateAction);
        flipVert.addActionListener(rotateAction);
        flipHor.addActionListener(rotateAction);

        JPanel buttons = new JPanel(new GridLayout(2,2));
        buttons.add(rotateClock);
        buttons.add(rotateCounter);
        buttons.add(flipVert);
        buttons.add(flipHor);
        add(buttons);
    }

    //Listener for click on input value forms
    public class ButtonListener implements ActionListener{
    	
        public void actionPerformed(ActionEvent e){
            Component component = (Component) e.getSource();
            JFrame curFrame = (JFrame) SwingUtilities.getRoot(component); 
            Component [] components = curFrame.getContentPane().getComponents();
            JLabel frameLabel = (JLabel)components[0];
            String buttonTitle = ((JButton)component).getText();

            Image img;

            if(buttonTitle == "Rotate Clockwise") img = rotate(((ImageIcon)frameLabel.getIcon()).getImage(), 90);
            else if(buttonTitle == "Rotate Counter") img = rotate(((ImageIcon)frameLabel.getIcon()).getImage(), -90);
            else if(buttonTitle == "Flip Vertical") img = flip(((ImageIcon)frameLabel.getIcon()).getImage(), false);
            else img = flip(((ImageIcon)frameLabel.getIcon()).getImage(), true);

            ImageIcon newImg = new ImageIcon(img);
            frameLabel.setIcon(newImg);
            curFrame.getContentPane().repaint();
        }

        /**
         * Rotates new copy of the image.
         * 
         * @param img The image to be rotated
         * @param angle The angle in degrees
         * @return The rotated image
         */
        public Image rotate(Image img, double angle){
            double sin = Math.abs(Math.sin(Math.toRadians(angle))), cos = Math.abs(Math.cos(Math.toRadians(angle)));
            int w = img.getWidth(null), h = img.getHeight(null);
            int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h
                    * cos + w * sin);
            BufferedImage bimg = new BufferedImage(neww, newh, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = bimg.createGraphics();
            g.translate((neww - w) / 2, (newh - h) / 2);
            g.rotate(Math.toRadians(angle), w / 2, h / 2);
            g.drawRenderedImage(toBufferedImage(img), null);
            g.dispose();
            return (Image)bimg;
        }
        
        /**
         * Flips a new copy of the image.
         * 
         * @param img The image to be flipped
         * @param vert The direction to flip
         * @return The flipped image
         */
        public Image flip(Image img, boolean vert){

            BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = bimg.createGraphics();
            if(vert){
                AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
                tx.translate(0, -img.getHeight(null));
                g.drawRenderedImage(toBufferedImage(img), tx);
            }
            else{
                AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-img.getWidth(null), 0);
                g.drawRenderedImage(toBufferedImage(img), tx);
            }
            g.dispose();

            return (Image)bimg;
        }

        /**
         * Converts a given Image into a BufferedImage
         *
         * @param img The Image to be converted
         * @return The converted BufferedImage
         */
        public BufferedImage toBufferedImage(Image img){
        	
            if (img instanceof BufferedImage) {
                return (BufferedImage) img;
            }
            
            BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(img, 0, 0, null);
            bGr.dispose();
            return bimage;
        }
    }
    

    public static void main(String args[]){
    	
        ImageEx gui = new ImageEx(17);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
        gui.pack();
        gui.setTitle("Image Rotation Example");
    }
}
