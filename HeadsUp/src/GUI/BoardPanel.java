package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	private JTextField potText;
	private int potChips;
	private JTextField boardText;

	private JTextField betText;
	private int roundBet;
	
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
		
		
		JPanel chipsPanel = new JPanel(new FlowLayout());
		chipsPanel.setOpaque(false);
		this.add(chipsPanel, BorderLayout.SOUTH);
		this.potText = new JTextField();
		this.addPot(0);
		this.potText.setEditable(false);
		this.potText.setBorder(new EmptyBorder(0,0,0,0));
		this.potText.setOpaque(false);
		this.potText.setForeground(Color.WHITE);
		this.potText.setHorizontalAlignment(JTextField.CENTER);
		this.potText.setFont(new Font("Roboto Mono",Font.BOLD, 15));
		chipsPanel.add(this.potText);
		
		//Guarreria para a�adir separacion
		JTextField tmp = new JTextField(2);
		tmp.setOpaque(false);
		tmp.setBorder(new EmptyBorder(0,0,0,0));
		chipsPanel.add(tmp);
		//this.add(this.potText, BorderLayout.SOUTH);
		
		this.betText = new JTextField();
		this.addRoundBet(0);
		this.betText.setEditable(false);
		this.betText.setBorder(new EmptyBorder(0,0,0,0));
		this.betText.setOpaque(false);
		this.betText.setForeground(Color.WHITE);
		this.betText.setHorizontalAlignment(JTextField.CENTER);
		this.betText.setFont(new Font("Roboto Mono",Font.BOLD, 15));
		chipsPanel.add(this.betText);
		//this.add(this.betText, BorderLayout.SOUTH);
		
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
	
	public void addPot(int n) {
		this.potChips += n;
		this.potText.setText("POT: " + this.potChips + " pts");
	}
	
	public int getPot() {
		return this.potChips;
	}
	
	public void addRoundBet(int n) {
		this.roundBet += n;
		this.betText.setText("Round Bet: " + this.roundBet + " pts");
	}
	
	public int getRoundBet() {
		return this.roundBet;
	}
	
	public void resetRound() {
		this.roundBet = 0;
		this.betText.setText("Round Bet: " + this.roundBet + " pts");
	}
	
	public void nextRound() {
		this.resetRound();
	}
	
	public void endGame(){
		this.boardText.setText("END");
	}
	
	public void newHand() {
		this.addPot(-this.potChips);
		this.resetRound();;
		this.card1.setIcon(new ImageIcon(IMAGE_FOLDER + "back.png"));
		this.card2.setIcon(new ImageIcon(IMAGE_FOLDER + "back.png"));
		this.card3.setIcon(new ImageIcon(IMAGE_FOLDER + "back.png"));
		this.card4.setIcon(new ImageIcon(IMAGE_FOLDER + "back.png"));
		this.card5.setIcon(new ImageIcon(IMAGE_FOLDER + "back.png"));
		this.boardText.setText("PREFLOP");
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
