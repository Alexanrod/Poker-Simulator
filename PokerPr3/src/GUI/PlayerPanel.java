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
	private JTextField equity;
	private int playerID;
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
		this.playerText.setFont(new Font("Roboto Mono",Font.BOLD, 18));
		
		this.equity = new JTextField("0,0 %");
		this.equity.setEditable(false);
		this.equity.setBorder(new EmptyBorder(0,0,0,0));
		this.equity.setOpaque(false);
		this.equity.setForeground(Color.WHITE);
		this.equity.setHorizontalAlignment(JTextField.CENTER);
		this.equity.setFont(new Font("Roboto Mono",Font.BOLD, 18));
		
		
		
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
		northPanel.add(this.equity, BorderLayout.CENTER);
		
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
	
	public void setCard1(ImageIcon img) {
		this.card1.setIcon(img);
	}
	
	public void setCard1(String img) {
		this.card1.setIcon(new ImageIcon(IMAGE_FOLDER + img + ".png"));
	}
	
	public void setCard2(ImageIcon img) {
		this.card2.setIcon(img);
	}
	
	public void setCard2(String img) {
		this.card2.setIcon(new ImageIcon(IMAGE_FOLDER + img + ".png"));
	}
	
	public void setEquity(double n) {
		this.equity.setText(n + " %");
	}
	
	public boolean getOnGame() {
		return this.onGame;
	}
	
	public void setOnGame() {
		this.onGame = false;
	}
	
	private JButton foldButton() {
		JButton button = new JButton("Fold");
		button.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				card1.setIcon(new ImageIcon(getCardImg("back.png")));
				card2.setIcon(new ImageIcon(getCardImg("back.png")));
				equityCalculator.removePlayer(playerID);
				setOnGame();
				setEquity(0);
			}
		});
		return button;
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
}
