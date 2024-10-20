package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Back.Carta;

public class BoardPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private final static String IMAGE_FOLDER = "images" + File.separator;
	private final static Dimension CARDDIM = new Dimension(67, 97);
	
	private JLabel card1;
	private JLabel card2;
	private JLabel card3;
	private JLabel card4;
	private JLabel card5;
	private JTextField boardText;
	
	public BoardPanel() {
		this.init();
	}
	
	private void init() {
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		this.boardText = new JTextField("PREFLOP");
		this.boardText.setEditable(false);
		this.boardText.setBorder(new EmptyBorder(0,0,0,0));
		this.boardText.setOpaque(false);
		this.boardText.setForeground(Color.WHITE);
		this.boardText.setHorizontalAlignment(JTextField.CENTER);
		this.boardText.setFont(new Font("Roboto Mono",Font.BOLD, 18));
		this.add(this.boardText, BorderLayout.NORTH);
		
		JPanel cardsPanel = new JPanel(new GridLayout(1, 5, 1, 1));
		cardsPanel.setOpaque(false);
		this.add(cardsPanel, BorderLayout.CENTER);
		
		
		this.card1 = new JLabel();
		this.card1.setSize(CARDDIM);
		cardsPanel.add(this.card1);
		this.card1.setIcon(new ImageIcon(IMAGE_FOLDER + "back.png"));
		
		this.card2 = new JLabel();
		this.card1.setSize(CARDDIM);
		cardsPanel.add(this.card2);
		this.card2.setIcon(new ImageIcon(IMAGE_FOLDER + "back.png"));
		
		this.card3 = new JLabel();
		this.card1.setSize(CARDDIM);
		cardsPanel.add(this.card3);
		this.card3.setIcon(new ImageIcon(IMAGE_FOLDER + "back.png"));
		
		this.card4 = new JLabel();
		this.card1.setSize(CARDDIM);
		cardsPanel.add(this.card4);
		this.card4.setIcon(new ImageIcon(IMAGE_FOLDER + "back.png"));
		
		this.card5 = new JLabel();
		this.card1.setSize(CARDDIM);
		cardsPanel.add(this.card5);
		this.card5.setIcon(new ImageIcon(IMAGE_FOLDER + "back.png"));
	}
	
	public void setCard1(Carta c) {
		card1.setIcon(new ImageIcon(this.getCardImg(c.toImg())));
	}
	
	public void setCard2(Carta c) {
		card2.setIcon(new ImageIcon(this.getCardImg(c.toImg())));
	}
	
	public void setCard3(Carta c) {
		card3.setIcon(new ImageIcon(this.getCardImg(c.toImg())));
	}
	
	public void setCard4(Carta c) {
		card4.setIcon(new ImageIcon(this.getCardImg(c.toImg())));
	}
	
	public void setCard5(Carta c) {
		card5.setIcon(new ImageIcon(this.getCardImg(c.toImg())));
	}
	
	public String getStage() {
		return this.boardText.getText();
	}
	
	public void next() {
		switch (this.boardText.getText()) {
			case "PREFLOP": this.boardText.setText("FLOP");
			break;
			case "FLOP": this.boardText.setText("TURN");
			break;
			case "TURN": this.boardText.setText("RIVER");
			break;
			case "RIVER": this.boardText.setText("END");
			break;
		}
	}
	
	private Image getCardImg(String name) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(IMAGE_FOLDER + name));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(card1.getWidth(), card1.getHeight(),
		        Image.SCALE_SMOOTH);
		return dimg;
	}
}
