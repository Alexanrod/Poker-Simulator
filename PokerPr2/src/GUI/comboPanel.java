package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Back.BoardMatrix;
import Back.Combos;
import Back.Matrix;

public class comboPanel extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JFrame window;
	private JPanel centralPanel;
	private JPanel infoPanel;
	private JPanel tablaPanel;
    private JPanel boardPanel;
    private JPanel boardMatrixPanel;
    private StatisticsList staticsList;
    private TablaMatriz table;
    private BoardMatriz boardTable;
    private JLabel boardLabel;
    private JLabel[] board;
    private JLabel[] comboList;
    private JLabel comboLabel;
    private JLabel totalCombo;
    private Combos combos;
    
	public comboPanel() {
    	initGUI();
    }
	
	private void initGUI() {
		this.window = new JFrame("Tabla Combos");
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.window.setBounds(0,0,1000,600);
	    this.window.setLayout(new BorderLayout());
	    
	    
	    this.centralPanel = new JPanel(new GridLayout(1,2,10,5));
	    this.infoPanel = new JPanel(new GridLayout(1,2));
	    this.tablaPanel = new JPanel(new GridLayout(13, 13, 2, 2));
	    this.boardMatrixPanel = new JPanel(new GridLayout(13, 4, 1, 1));
	    this.boardPanel = new JPanel(new BorderLayout());
	    
	    this.staticsList = new StatisticsList();
	    this.table = new TablaMatriz(new Matrix());
	    this.boardTable = new BoardMatriz(new BoardMatrix());
	    this.boardLabel = new JLabel("PREFLOP");
	    this.comboLabel = new JLabel("COMBOS TOTALES : ");
	    this.totalCombo = new JLabel();
	    this.totalCombo.setOpaque(false);
	    this.board = new JLabel[5];
	    this.comboList = new JLabel[this.staticsList.length()];
	    
	    JPanel centerPanel = new JPanel(new BorderLayout());
	    this.window.add(centerPanel, BorderLayout.CENTER);
	    this.tablaPanel.setBackground(Color.WHITE);
	    this.boardMatrixPanel.setBackground(Color.WHITE);
	    this.centralPanel.setBackground(Color.WHITE);
	    this.centralPanel.setPreferredSize(new Dimension(400,600));
	    this.infoPanel.setBackground(Color.WHITE);
	    centerPanel.add(this.tablaPanel, BorderLayout.CENTER);
	    this.centralPanel.add(this.boardPanel);
	    this.centralPanel.add(this.infoPanel);
	    this.boardPanel.add(this.boardMatrixPanel, BorderLayout.CENTER);
	    centerPanel.add(this.centralPanel, BorderLayout.EAST);
	    
	    
	    for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 13; j++) {
				this.tablaPanel.add((Component) this.table.getValueAt(i,j));
			}
	    }
	    
	    for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 4; j++) {
				this.boardMatrixPanel.add((Component) this.boardTable.getValueAt(i,j));
			}
	    }
	    
	    JPanel staticsListPanel = new JPanel(new GridLayout(this.staticsList.length(),1));
	    staticsListPanel.setBackground(Color.WHITE);
	    this.infoPanel.add(staticsListPanel);
	    for(int i = 0; i < this.staticsList.length(); i++) {
			staticsListPanel.add((Component) this.staticsList.getValueAt(i));
	    }
	    
	    JPanel comboListPanel = new JPanel(new GridLayout(this.comboList.length,1));
	    comboListPanel.setBackground(Color.WHITE);
	    this.infoPanel.add(comboListPanel);
	    for(int i = 0; i < this.comboList.length; i++) {
	    	this.comboList[i] = new JLabel();
	    	this.comboList[i].setBackground(Color.WHITE);
	    	comboListPanel.add(this.comboList[i]);
	    }
	    
	 
        
        JButton buttonApplyBoard = new JButton("Apply");
        buttonApplyBoard.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				String tableRange = table.getRange();
				String boardRange = boardTable.getRange();
				if(!tableRange.equals("") && !boardRange.equals("")) {
				    combos = new Combos();
					combos.doCombos(tableRange,boardRange);
					int[] tempCombo = combos.getCombos();
					for(int i = 0; i < comboList.length; i++) {
						float n =(float) tempCombo[i] / combos.getTotalCombos();
						n *= 100;
						String percentage = String.valueOf(n);
				    	comboList[i].setText(String.valueOf(tempCombo[i] + "  " + String.format("%.0f", n) + "%"));
				    }
					totalCombo.setText(String.valueOf(combos.getTotalCombos()));
					comboLabel.setOpaque(true);
					totalCombo.setOpaque(true);
				}
				switch (boardTable.getBoardSize()) {
					case 3: boardLabel.setText("FLOP");
						break;
					case 4: boardLabel.setText("TURN");
						break;
					case 5: boardLabel.setText("RIVER");
						break;
					default: boardLabel.setText("PREFLOP");
				}
				for(int i = 0; i < boardTable.getBoardSize(); i++) {
					board[i].setText(boardTable.getBoardCard(i));
					board[i].setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
					board[i].setHorizontalAlignment(SwingConstants.CENTER);
					switch (board[i].getText().substring(1)){
		             	case "\u2665":
		             		board[i].setBackground(ColorFactory.create("LRed"));
		             		break;
		             	case "\u2663":
		             		board[i].setBackground(ColorFactory.create("LGreen"));
		             		break;
		             	case "\u2666":
		             		board[i].setBackground(ColorFactory.create("LBlue"));
		             		break;
		             	case "\u2660": 
		             		board[i].setBackground(ColorFactory.create("LBlack"));
		             		break;
					}
					board[i].setOpaque(true);
				}
			}
        });
        
        JButton buttonClearBoard = new JButton("Clear");
        buttonClearBoard.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < boardTable.getBoardSize(); i++) {
					if(board[i] != null) board[i].setOpaque(false);
				}
				for(int i = 0; i < comboList.length; i++) {
			    	comboList[i].setText(String.valueOf(""));
			    }
				for(int i = 0; i < boardTable.getBoardSize(); i++) {
					board[i].setText("");
					board[i].setBackground(Color.WHITE);
				}
				totalCombo.setText("");
				boardTable.clear();
				table.clear();
				boardLabel.setText("PREFLOP");
			}
        });

        JPanel boardbuttonPanel = new JPanel(new GridLayout(1,2));
        JPanel boardCardsPanel = new JPanel(new GridLayout(1,14));
        boardCardsPanel.setBackground(Color.WHITE);
        boardbuttonPanel.setBackground(Color.WHITE);
        boardCardsPanel.add(new JLabel(""));
        boardCardsPanel.add(this.boardLabel);
        for(int i = 0; i < board.length; i++) {
	    	board[i] = new JLabel("");
	    	board[i].setBackground(Color.WHITE);
	    	boardCardsPanel.add(board[i]);
	    }
        boardCardsPanel.add(new JLabel(""));
        
        this.window.add(boardCardsPanel, BorderLayout.NORTH);
        this.boardPanel.add(boardbuttonPanel, BorderLayout.SOUTH);
        boardbuttonPanel.add(buttonApplyBoard);
        boardbuttonPanel.add(buttonClearBoard);
        /*
        this.infoPanel.add(buttonOkRange);
        this.infoPanel.add(combTotales);*/
        this.comboLabel.setBackground(Color.WHITE);
        this.totalCombo.setBackground(Color.WHITE);
        JPanel comboPanel = new JPanel(new FlowLayout());
        comboPanel.setBackground(Color.WHITE);
        comboPanel.add(this.comboLabel);
        comboPanel.add(this.totalCombo);
        this.window.add(comboPanel, BorderLayout.SOUTH);
	    //window.add(centralPanel);
        this.window.setVisible(true);
	}
}
