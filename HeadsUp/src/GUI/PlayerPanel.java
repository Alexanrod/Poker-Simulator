package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Back.Carta;
import Back.EquityCalculator;

public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final static String IMAGE_FOLDER = "images" + File.separator;
	private final static Dimension CARDDIM = new Dimension(67, 97);
	private JTextField playerText;
	private JLabel card1;
	private JLabel card2;
	private JButton foldButton;
	private JButton handButton;
	private JTextField equityText;
	private double equity;
	private int playerID;
	private int nChips;
	private JTextField chipsText;
	private W2CardsPanel cardsTableWindow;
	private EquityCalculator equityCalculator;
	private boolean onGame;
	
	public PlayerPanel(int player, Carta c1, Carta c2, EquityCalculator ec){
		
		this.init(player, c1, c2,ec);
	}
	
	private void init(int player, Carta c1, Carta c2,EquityCalculator ec) {
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		this.playerID = player;
		this.equityCalculator = ec;
		this.onGame = true;
		
		
		this.playerText = new JTextField("J" + (player + 1));
		this.playerText.setEditable(false);
		this.playerText.setBorder(new EmptyBorder(0,0,0,0));
		this.playerText.setOpaque(false);
		this.playerText.setForeground(Color.WHITE);
		this.playerText.setHorizontalAlignment(JTextField.CENTER);
		this.playerText.setFont(new Font("Roboto Mono",Font.BOLD, 14));
		
		this.equityText = new JTextField();
		this.setEquity(0.0);
		this.equityText.setEditable(false);
		this.equityText.setBorder(new EmptyBorder(0,0,0,0));
		this.equityText.setOpaque(false);
		this.equityText.setForeground(Color.WHITE);
		this.equityText.setHorizontalAlignment(JTextField.CENTER);
		this.equityText.setFont(new Font("Roboto Mono",Font.BOLD, 14));
		
		this.chipsText = new JTextField();
		this.setChips(1000);
		this.chipsText.setEditable(false);
		this.chipsText.setBorder(new EmptyBorder(0,0,0,0));
		this.chipsText.setBackground(ColorFactory.create("Gold"));
		this.chipsText.setForeground(Color.WHITE);
		this.chipsText.setHorizontalAlignment(JTextField.CENTER);
		this.chipsText.setFont(new Font("Roboto Mono",Font.BOLD, 14));
		
		this.card1 = new JLabel();
		this.card1.setSize(CARDDIM);
		this.card1.setIcon(new ImageIcon(this.getCardImg(c1.toImg())));
		this.card2 = new JLabel();
		this.card1.setSize(CARDDIM);
		this.card2.setIcon(new ImageIcon(this.getCardImg(c2.toImg())));
		
		this.cardsTableWindow = new W2CardsPanel();
		this.handButton = this.selectButton();
		this.handButton.setBorder(new EmptyBorder(1,0,1,1));
		this.foldButton = this.foldButton();
		this.foldButton.setBorder(new EmptyBorder(1,0,1,1));
		
		JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.setOpaque(false);
		northPanel.add(this.playerText, BorderLayout.WEST);
		JPanel infoPanel = new JPanel(new GridLayout());
		infoPanel.setOpaque(false);
		infoPanel.add(this.chipsText);
		infoPanel.add(this.equityText);
		northPanel.add(infoPanel, BorderLayout.CENTER);
		
		JPanel centerPanel = new JPanel(new GridLayout(1, 2, 1, 1));
		centerPanel.setOpaque(false);
		centerPanel.add(this.card1);
		centerPanel.add(this.card2);
		
		JPanel southPanel = new JPanel(new GridLayout(1, 2, 1, 1));
		northPanel.setOpaque(false);
		southPanel.add(this.handButton);
		southPanel.add(this.foldButton);
		
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		
		
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
	
	public void setCard1(Carta c) {
		this.card1.setIcon(new ImageIcon(this.getCardImg(c.toImg())));
	}
	
	public void setCard1(String img) {
		this.card1.setIcon(new ImageIcon(IMAGE_FOLDER + img + ".png"));
	}
	
	public void setCard2(Carta c) {
		this.card2.setIcon(new ImageIcon(this.getCardImg(c.toImg())));
	}
	
	public void setCard2(String img) {
		this.card2.setIcon(new ImageIcon(IMAGE_FOLDER + img + ".png"));
	}
	
	public void setEquity(double n) {
		this.equity = n;
		this.equityText.setText(this.equity + " %");
	}
	public double getEquity() {
		return this.equity;
	}
	
	public void setChips(int n) {
		this.nChips = n;
		this.chipsText.setText(this.nChips + " pts");
	}
	
	public int getChips() {
		return nChips;
	}
	
	public boolean getOnGame() {
		return this.onGame;
	}
	
	public void setOffGame() {
		this.onGame = false;
	}
	
	private JButton foldButton() {
		JButton button = new JButton("Fold");
		button.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				fold();
			}
		});
		return button;
	}
	
	public void fold() {
		card1.setIcon(new ImageIcon(getCardImg("back.png")));
		card2.setIcon(new ImageIcon(getCardImg("back.png")));
		equityCalculator.removePlayer(playerID);
		setOffGame();
		setEquity(0);
	}

	private JButton selectButton() {
		JButton button = new JButton("Select");
		button.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				cardsTableWindow.display();
			}
		});
		return button;
	}
	
	public int getPlayerID() {
		return this.playerID;
	}
	public void decPlayerID() {
		this.playerID--;
	}

	public void newHand(Carta carta1, Carta carta2, EquityCalculator equityCalculator2, int playerid) {
		// TODO Auto-generated method stub
		this.onGame = true;
		this.playerID = playerid;
		this.setEquity(0.0);
		this.setCard1(carta1);
		this.setCard2(carta2);
	}
}
