package GUI;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputListener;
import javax.swing.table.AbstractTableModel;

import Back.Hand;
import Back.Matrix;

public class TablaMatriz extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private JLabel[][] table = new JLabel[13][13];
	private ArrayList<String> paintRange = new ArrayList<String>();
	private String actualRange = "";

	public TablaMatriz(Matrix matrix) {
		this.init(matrix);
	}

	public void init(Matrix matrix) {
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.size(); j++) {
				JLabel tempLabel = new JLabel(matrix.get(i, j).toString(), SwingConstants.CENTER);
				tempLabel.setBackground(Color.GRAY);
				tempLabel.setOpaque(true);
				tempLabel.addMouseListener(new MouseInputListener() {
					public void mouseClicked(MouseEvent me) {
						if (tempLabel.getBackground() == Color.YELLOW)
							tempLabel.setBackground(Color.GRAY);
						else
							tempLabel.setBackground(Color.YELLOW);
						
						refreshRange();
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
					}

					@Override
					public void mouseReleased(MouseEvent e) {
					}

					@Override
					public void mouseEntered(MouseEvent e) {
					}

					@Override
					public void mouseExited(MouseEvent e) {
					}

					@Override
					public void mouseDragged(MouseEvent e) {
					}

					@Override
					public void mouseMoved(MouseEvent e) {
					}
				});
				this.table[i][j] = tempLabel;

			}
		}
	}

	// añadir actual range a todas las tablas y action listeners y botones que
	// modifiquen las celdas a encendidas
	private void refreshRange() {
		this.actualRange = "";
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				if (this.table[i][j].getBackground().equals(Color.YELLOW)) {
					if (this.actualRange.equals(""))
						this.actualRange += this.table[i][j].getText();
					else
						this.actualRange += "," + this.table[i][j].getText();
				}
			}
		}
	}

	public void actualRangee() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				if (this.table[i][j].getBackground().equals(Color.YELLOW)) {
					if (this.actualRange.equals(""))
						this.actualRange += this.table[i][j].getText();
					else
						this.actualRange += "," + this.table[i][j].getText();
				}

			}
		}
	}

	public void pintar(String range) {
		if (!range.equals("")) {
			this.clear();
			String[] parts = range.split(",");
			for (int i = 0; i < parts.length; i++) {
				String hand = parts[i];
				this.paintRange.add(hand);
				int col = Math.abs(Hand.translateCard(String.valueOf(hand.charAt(0))) - 14);
				int row = Math.abs(Hand.translateCard(String.valueOf(hand.charAt(1))) - 14);
				if (hand.length() == 3 && hand.charAt(2) == 'o')
					this.table[row][col].setBackground(Color.YELLOW);
				else
					this.table[col][row].setBackground(Color.YELLOW);
			}
			this.refreshRange();
		}
	}

	public void all() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				this.table[i][j].setBackground(Color.YELLOW);
				this.paintRange.add(this.table[i][j].getText());
			}
		}
		this.refreshRange();
	}

	public void suiteds() {
		for (int i = 0; i < 13; i++) {
			for (int j = i + 1; j < 13; j++) {
				this.table[i][j].setBackground(Color.YELLOW);
				this.paintRange.add(this.table[i][j].getText());
			}
		}
		this.refreshRange();
	}

	public void broadWays() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				this.table[i][j].setBackground(Color.YELLOW);
				this.paintRange.add(this.table[i][j].getText());
			}
		}
		this.refreshRange();
	}

	public void anyPair() {
		for (int i = 0; i < 13; i++) {
			this.table[i][i].setBackground(Color.YELLOW);
			this.paintRange.add(this.table[i][i].getText());
		}
		this.refreshRange();
	}

	/*
	 * public void clear() { for(int i = 0; i < paintRange.size(); i++) { int col =
	 * Math.abs(Hand.translateCard(String.valueOf(this.paintRange.get(i).charAt(0)))
	 * -14); int row =
	 * Math.abs(Hand.translateCard(String.valueOf(this.paintRange.get(i).charAt(1)))
	 * -14); if(this.paintRange.get(i).length() == 3 &&
	 * this.paintRange.get(i).charAt(2)=='o')
	 * this.table[row][col].setBackground(Color.GRAY); else
	 * this.table[col][row].setBackground(Color.GRAY); } }
	 */

	// este siempre recorre entero pero es menos en ciertas situaciones
	public void clear() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				this.table[i][j].setBackground(Color.GRAY);
			}
		}
		this.actualRange = "";
	}

	public void updateSlider(String[] rangoSlavosky, int result) {
		for (int i = 0; i < rangoSlavosky.length; i++) {
			int col = Math.abs(Hand.translateCard(String.valueOf(rangoSlavosky[i].charAt(0))) - 14);
			int row = Math.abs(Hand.translateCard(String.valueOf(rangoSlavosky[i].charAt(1))) - 14);
			if (i < result) {
				if (rangoSlavosky[i].length() == 3 && rangoSlavosky[i].charAt(2) == 's')
					table[col][row].setBackground(Color.YELLOW);
				else
					table[row][col].setBackground(Color.YELLOW);
			} else {
				table[row][col].setBackground(Color.GRAY);
				table[col][row].setBackground(Color.GRAY);
			}
		}
		this.refreshRange();
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

	public String getRange() {
		return this.actualRange;
	}
}
