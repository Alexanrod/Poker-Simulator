package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class W2CardsPanel extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private CardsTable cardsTable;
	private JPanel cardsTablePanel;
	private JPanel buttonPanel;
	
	public W2CardsPanel() {
		this.init();
	}
	
	private void init() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.setSize(400, 600);
		this.cardsTablePanel = new JPanel(new GridLayout(13, 4, 1, 1));
		this.cardsTablePanel.setBackground(Color.WHITE);
		this.buttonPanel = new JPanel(new GridLayout());
		this.buttonPanel.setBackground(Color.WHITE);
		/*
		int gap = 200;
        this.setBorder(BorderFactory.createEmptyBorder(300, 600, gap, gap));*/
        
        JButton selectButton = new JButton("Select");
        selectButton.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				close();
			}
		});
        this.buttonPanel.add(selectButton);
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				close();
			}
		});
        this.buttonPanel.add(closeButton);
        this.add(this.buttonPanel, BorderLayout.SOUTH);
        
        /*
        JDialog dialog = new JDialog(window, "Settings", ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.add(this);
        dialog.pack();
        dialog.setLocationRelativeTo(window);*/
		
		this.cardsTable = new CardsTable();
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 4; j++) {
				this.cardsTablePanel.add((Component) this.cardsTable.getValueAt(i,j));
			}
	    }
		this.add(this.cardsTablePanel, BorderLayout.CENTER);
	}

	private void setLayout(BorderLayout borderLayout) {
		// TODO Auto-generated method stub
		
	}

	public void display() {
		this.setVisible(true);
	}

	public void close() {
		this.setVisible(false);
	}
	
}

