package GUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class StatisticsList {
	private String manos[] = { "Str.flush", "Quads","Full-House","Flush","Straight","Set","Two-pair",
											"OverPair","TopPair","PoketPair","MiddlePair","WeakPair","AcePair","Ace-High", "No-Made-Hand" };
	private  JLabel[] table = new JLabel[this.manos.length];
	
	public StatisticsList() {
		this.init(this.manos);
	}
	
	public void init(String manos[]) {
		 for(int i = 0; i < this.manos.length; i++) {
			this.table[i] = new JLabel(manos[i]);
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
