package Back;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class StatisticsList {
	private String jugadores[] = { "Jug1", "Jug2","Jug3","Jug4","Jug5","Jug6" };
	private  JLabel[] table = new JLabel[this.jugadores.length];
	
	public StatisticsList() {
		this.init(this.jugadores);
	}
	
	public void init(String jugadores[]) {
		 for(int i = 0; i < this.jugadores.length; i++) {
			this.table[i] = new JLabel(jugadores[i]);
			this.table[i].setBackground(Color.WHITE);
			this.table[i].setOpaque(true);
			this.table[i].setFont(new Font("Lexend",Font.BOLD,13));
	    } 
	}
	
	public int length() {
		return this.table.length;
	}
	
	public Object getValueAt(int idx) {
		return this.table[idx];
	}
}

