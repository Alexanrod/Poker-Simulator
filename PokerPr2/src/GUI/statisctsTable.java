package GUI;

import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

public class statisctsTable extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	
	private String manos[] = { "str.flush", "quads","full-house","flush","straight","set","two-pair",
											"overpair","toppair","poketpair","middlepair","weakpair","acepair","high-card" };
	private  JTextField[][] table = new JTextField[14][2];
	
	public statisctsTable() {
		this.init(manos);
	}
	
	public void init(String manos[]) {
		 for(int i = 0; i < 14; i++) {
				for(int j = 0; j < 2; j++) {
					table[i][j] = new JTextField();
					table[i][j].setEditable(false);
					if(j ==0) {
						table[i][j].setText(manos[i]);
					}
				}
		    }
		 
	}
	
	@Override
	public int getColumnCount() {
		return this.table[0].length;
	}

	@Override
	public int getRowCount() {
		return this.table.length;
	}
	
	@Override
	public Object getValueAt(int fila, int col) {
		return this.table[fila][col];
	}
}
