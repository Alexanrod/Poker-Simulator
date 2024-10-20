package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import Back.Carta;
import Back.EquityCalculator;

public class MainWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private final static String IMAGE_FOLDER = "images" + File.separator;
	private final static int NUMBER_PLAYERS = 2;
	
	private EquityCalculator equityCalculator;
	private BackgroundPanel tablePanel;
	private W2CardsPanel cardsTableWindow;
	private static PlayerPanel playersPanels[];
	private BoardPanel boardPanel;
	private boolean activePlayers[];
	private ArrayList<Carta> boardCards;
	private int nPlayers = 0;
	
	
	public MainWindow() {}
	
	public MainWindow(EquityCalculator ec){
		this.equityCalculator = ec;
		init();
	}
	
	private void init() {
		this.setTitle("Final Table - Main Event");
		this.setSize(1200,800);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
		
		this.activePlayers = new boolean[NUMBER_PLAYERS];
		for(int i = 0; i < NUMBER_PLAYERS; i++) 
			this.activePlayers[i] = false;
		this.playersPanels = new PlayerPanel[NUMBER_PLAYERS];
		this.boardPanel = new BoardPanel();
		this.boardCards = this.equityCalculator.getBoard();
		this.tablePanel = new BackgroundPanel(IMAGE_FOLDER + "PokerTable.png");
		this.tablePanel.setSize(this.getWidth(), this.getHeight());
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
	        	tablePanel.setSize(e.getComponent().getWidth(), e.getComponent().getHeight());
	        }
		});
		this.tablePanel.setLayout(null);
		this.add(this.tablePanel);
		
		for(int i = 0; i < NUMBER_PLAYERS; i++) {
			this.playersPanels[i] = new PlayerPanel(i ,
					equityCalculator.getJugadores().get(i).getCarta(1),
					equityCalculator.getJugadores().get(i).getCarta(2),
					this.equityCalculator);
			this.playersPanels[i].setSize(140,140);
			this.tablePanel.add(this.playersPanels[i]);
		}
		
		//Locations minimizado
		this.playersPanels[0].setLocation(528, 570);
		this.playersPanels[1].setLocation(115, 470);
		
		/*this.playersPanels[2].setLocation(115, 150);
		this.playersPanels[3].setLocation(528, 40);
		this.playersPanels[4].setLocation(940, 150);
		this.playersPanels[5].setLocation(940, 470);*/
		
		this.tablePanel.add(this.boardPanel);
		this.boardPanel.setSize(350, 130);
		this.boardPanel.setLocation(420, 320);
		
		JButton calculateButton = new JButton(" Calculate ");
		calculateButton.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				int j = 0;
				switch(boardPanel.getStage()) {
					case "PREFLOP": equityCalculator.calcularVictoriasPreFlop();
					break;
					case "FLOP": equityCalculator.calcularVictoriasFlop();
					break;
					case "TURN": equityCalculator.calcularVictoriasTurn();
					break;
					case "RIVER": equityCalculator.calcularVictoriasRiver();
				}
				
				for(int i = 0; i< NUMBER_PLAYERS; i++) {
					if(playersPanels[i].getOnGame()) {
						playersPanels[i].setEquity(equityCalculator.getJugadores().get(j).getEquity());
						j++;
					}
				}		
			}
		});
		
		JButton nextButton = new JButton(" Next ");
		nextButton.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				boardPanel.next();
				
				switch(boardPanel.getStage()) {
					case "FLOP":
						boardPanel.setCard1(boardCards.get(0));
						boardPanel.setCard2(boardCards.get(1));
						boardPanel.setCard3(boardCards.get(2));
					break;
					case "TURN":
						boardPanel.setCard4(boardCards.get(3));
					break;
					case "RIVER":
						boardPanel.setCard5(boardCards.get(4));
					break;
					default:
						boardPanel.setCard1(boardCards.get(0));
						boardPanel.setCard2(boardCards.get(1));
						boardPanel.setCard3(boardCards.get(2));
						boardPanel.setCard4(boardCards.get(3));
						boardPanel.setCard5(boardCards.get(4));
				}
				if(!boardPanel.getStage().equals("END")){
					for(int i = 0; i< NUMBER_PLAYERS; i++)
							playersPanels[i].setEquity(0);
				}
			}
		});
		
		JPanel controlPanel = new JPanel();
		this.tablePanel.add(controlPanel);
		controlPanel.setLocation(965,675);
		controlPanel.setSize(200, 100);
		controlPanel.setOpaque(false);
		controlPanel.add(calculateButton);
		controlPanel.add(nextButton);
	}
	
	public static void removePlayer(int id) {
		for(int i = id+1; i <= 1; i++)
			playersPanels[i].decPlayerID();
	}
}
