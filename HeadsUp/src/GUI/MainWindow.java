package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Back.Carta;
import Back.EquityCalculator;

public class MainWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private final static String IMAGE_FOLDER = "images" + File.separator;
	private final static int NUMBER_PLAYERS = 2;
	
	private EquityCalculator equityCalculator;
	private BackgroundPanel tablePanel;
	private W2CardsPanel cardsTableWindow;
	private MenuPanel menuPanel;
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
		
		//Locations
		this.playersPanels[0].setLocation(528, 570);
		this.playersPanels[1].setLocation(115, 470);
		
		this.tablePanel.add(this.boardPanel);
		this.boardPanel.setSize(350, 160);
		this.boardPanel.setLocation(420, 320);
		
		
		this.menuPanel = new MenuPanel(this.playersPanels[0], this.boardPanel);
		this.menuPanel.setSize(350, 160);
		this.menuPanel.setLocation(450, 50);
		this.tablePanel.add(menuPanel);
		
		
		
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
				if(playersPanels[0].getOnGame()&& playersPanels[1].getOnGame()) {
					boardPanel.next();
					boardPanel.nextRound();
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
							calculateEquity();
						break;
						case "END":
							endHand();
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
				else endHand();
			}
		});
		
		JButton IAButton = new JButton("IA Turn");
		IAButton.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				if(playersPanels[1].getOnGame() && playersPanels[0].getOnGame()) {
					if(!boardPanel.getStage().equals("PREFLOP")){
						double minEquity = ((double) boardPanel.getRoundBet() / (boardPanel.getPot() + boardPanel.getRoundBet())) * 100;
						double IAEquity = calulateIAEquity();
						int IAbet = 0;
						System.out.println(IAEquity);
						if(IAEquity >= minEquity) {
							if(playersPanels[1].getChips() >= boardPanel.getRoundBet()) {
								IAbet = boardPanel.getRoundBet();
								playersPanels[1].setChips(playersPanels[1].getChips() - IAbet);
							}
							else {
								int diference = boardPanel.getRoundBet() - playersPanels[1].getChips();
								IAbet = playersPanels[1].getChips();
								playersPanels[0].setChips(playersPanels[0].getChips() + diference);
								boardPanel.addRoundBet(-diference);
								playersPanels[1].setChips(0);
							}
							boardPanel.addPot(IAbet);
						}
						else {
							playersPanels[1].fold();
							calculateEquity();
							endHand();
						}
					}
					else { // not preflop
							double IAEquity = calulateIAEquity();
							int IAbet = 0;
							System.out.println(IAEquity);
							if(IAEquity >= 25) {
								if(playersPanels[1].getChips() >= boardPanel.getRoundBet()) {
									IAbet = boardPanel.getRoundBet();
									playersPanels[1].setChips(playersPanels[1].getChips() - IAbet);
								}
								else {
									int diference = boardPanel.getRoundBet() - playersPanels[1].getChips();
									IAbet = playersPanels[1].getChips();
									playersPanels[0].setChips(playersPanels[0].getChips() + diference);
									boardPanel.addRoundBet(-diference);
									playersPanels[1].setChips(0);
								}
								boardPanel.addPot(IAbet);
							}
							else {
								playersPanels[1].fold();
								calculateEquity();
								endHand();
							}
					}
				}
			}
		});
		
		JPanel controlPanel = new JPanel();
		this.tablePanel.add(controlPanel);
		controlPanel.setLocation(965,675);
		controlPanel.setSize(200, 100);
		controlPanel.setOpaque(false);
		controlPanel.add(IAButton);
		controlPanel.add(calculateButton);
		controlPanel.add(nextButton);
	}

	private double calulateIAEquity() {
		calculateEquity();
		return playersPanels[1].getEquity();
	}
	
	private void endHand() {
		if(!playersPanels[0].getOnGame()) playersPanels[1].setChips(playersPanels[1].getChips() + boardPanel.getPot());
		else if(!playersPanels[1].getOnGame()) playersPanels[0].setChips(playersPanels[0].getChips() + boardPanel.getPot());
		else if(playersPanels[0].getEquity() == 100)
			playersPanels[0].setChips(playersPanels[0].getChips() + boardPanel.getPot());
		else playersPanels[1].setChips(playersPanels[1].getChips() + boardPanel.getPot());
		boardPanel.addPot(-boardPanel.getPot());
		if(playersPanels[0].getChips() > 0 && playersPanels[1].getChips() > 0) {
			newHand();
		}
		else boardPanel.endGame();
	}
	
	private void newHand() {
		this.equityCalculator = new EquityCalculator();
		this.equityCalculator.generarJugadoresRandom();
    	this.equityCalculator.generarBoardRandom();
    	this.boardCards = this.equityCalculator.getBoard();
    	this.playersPanels[0].newHand(equityCalculator.getJugadores().get(0).getCarta(1),
										equityCalculator.getJugadores().get(0).getCarta(2),
											this.equityCalculator, 0);
    	this.playersPanels[1].newHand(equityCalculator.getJugadores().get(1).getCarta(1),
										equityCalculator.getJugadores().get(1).getCarta(2),
											this.equityCalculator, 1);
    	this.boardPanel.newHand();
	}

	private void calculateEquity() {
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
	
	public static void removePlayer(int id) {
		for(int i = id+1; i <= 1; i++)
			playersPanels[i].decPlayerID();
	}
}
