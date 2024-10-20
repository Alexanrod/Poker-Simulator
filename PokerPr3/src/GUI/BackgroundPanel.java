package GUI;


import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

	 private Image backgroundImage;

	  public BackgroundPanel(String fileName) {
	    try {
	    	backgroundImage = ImageIO.read(new File(fileName));
        } 
	    catch (IOException e) {
	    	System.out.println("Error al cargar la imagen");
        }
	  }

	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    // Draw the background image.
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(),null);
	  }
  
}