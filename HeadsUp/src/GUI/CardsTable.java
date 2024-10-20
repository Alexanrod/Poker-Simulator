package GUI;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputListener;
import javax.swing.table.AbstractTableModel;


public class CardsTable extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private JLabel[][] table = new JLabel[13][4];
	private ArrayList<String> boardCards = new ArrayList<String>();
	private final int MAXCARDS = 5;
    private int count =0;
    private String actualRange = "";
    
	public CardsTable() {
		this.init(new CardsMatrix());
	}
	
	public void init(CardsMatrix matrix) {
        for(int i = 0; i < 13; i++) {
            for(int j = 0; j < 4; j++) {
                JLabel tempLabel =  new JLabel(matrix.get(i, j).toString(), SwingConstants.CENTER);
                tempLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
                switch (j){
                	case 0:
                		tempLabel.setBackground(ColorFactory.create("LRed"));
                		break;
                	case 1:
                		tempLabel.setBackground(ColorFactory.create("LGreen"));
                		break;
                	case 2:
                		tempLabel.setBackground(ColorFactory.create("LBlue"));
                		break;
                	case 3: 
                		tempLabel.setBackground(ColorFactory.create("LBlack"));
                		break;
                }
                tempLabel.setOpaque(true);
                tempLabel.addMouseListener(new MouseInputListener() {
                    public void mouseClicked(MouseEvent me){
                    	//Blue
                    	if (tempLabel.getBackground().equals(ColorFactory.create("blue"))) {
                    		count--;
                    		boardCards.remove(tempLabel.getText());
                            tempLabel.setBackground(ColorFactory.create("LBlue"));
                    	}
                        else if(count < MAXCARDS && tempLabel.getBackground().equals(ColorFactory.create("LBlue"))){
                        	count++;
                        	boardCards.add(tempLabel.getText());
                        	tempLabel.setBackground(ColorFactory.create("blue"));
                        }
                    	//Green
                        else if (tempLabel.getBackground().equals(ColorFactory.create("green"))) {
                    		count--;
                    		boardCards.remove(tempLabel.getText());
                            tempLabel.setBackground(ColorFactory.create("LGreen"));
                    	}
                        else if(count < MAXCARDS && tempLabel.getBackground().equals(ColorFactory.create("LGreen"))){
                        	count++;
                        	boardCards.add(tempLabel.getText());
                        	tempLabel.setBackground(ColorFactory.create("green"));
                        }
                    	//Red
                        else if (tempLabel.getBackground().equals(ColorFactory.create("red"))) {
                    		count--;
                    		boardCards.remove(tempLabel.getText());
                            tempLabel.setBackground(ColorFactory.create("LRed"));
                    	}
                        else if(count < MAXCARDS && tempLabel.getBackground().equals(ColorFactory.create("LRed"))){
                        	count++;
                        	boardCards.add(tempLabel.getText());
                        	tempLabel.setBackground(ColorFactory.create("red"));
                        }
                    	//Black
                        else if (tempLabel.getBackground().equals(ColorFactory.create("black"))) {
                    		count--;
                    		boardCards.remove(tempLabel.getText());
                            tempLabel.setBackground(ColorFactory.create("LBlack"));
                    	}
                        else if(count < MAXCARDS && tempLabel.getBackground().equals(ColorFactory.create("LBlack"))){
                        	count++;
                        	boardCards.add(tempLabel.getText());
                        	tempLabel.setBackground(ColorFactory.create("black"));
                        }
                    	refreshRange();
                    }

					@Override
					public void mousePressed(MouseEvent e) {}

					@Override
					public void mouseReleased(MouseEvent e) {}

					@Override
					public void mouseEntered(MouseEvent e) {}

					@Override
					public void mouseExited(MouseEvent e) {}

					@Override
					public void mouseDragged(MouseEvent e) {}

					@Override
					public void mouseMoved(MouseEvent e) {}
                });
                this.table[i][j] = tempLabel;
                
            }
        } 
    }
	
	public void clear() {
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 4; j++) {
				 switch (j){
	             	case 0:
	             		this.table[i][j].setBackground(ColorFactory.create("LRed"));
	             		break;
	             	case 1:
	             		this.table[i][j].setBackground(ColorFactory.create("LGreen"));
	             		break;
	             	case 2:
	             		this.table[i][j].setBackground(ColorFactory.create("LBlue"));
	             		break;
	             	case 3: 
	             		this.table[i][j].setBackground(ColorFactory.create("LBlack"));
	             		break;
				 }
			}
		}
		this.actualRange = "";
		this.boardCards.clear();
		this.count = 0;
	}
	
	public int getBoardSize() {
		return this.boardCards.size();
	}
	
	public String getBoardCard(int idx) {
		return this.boardCards.get(idx);
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
	
	private void refreshRange() {
		this.actualRange = "";
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				if (this.table[i][j].getBackground().equals(ColorFactory.create("red"))
					||this.table[i][j].getBackground().equals(ColorFactory.create("blue"))
					|| this.table[i][j].getBackground().equals(ColorFactory.create("green"))
					||this.table[i][j].getBackground().equals(ColorFactory.create("black")) ) {
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
				if (this.table[i][j].getBackground().equals(ColorFactory.create("red"))) {
					if (this.actualRange.equals(""))
						this.actualRange += this.table[i][j].getText();
					else
						this.actualRange += "," + this.table[i][j].getText();
				}

			}
		}
	}
	
	public String getRange() {
		for(int i =0; i < actualRange.length();i++) {
			if(actualRange.charAt(i) =='\u2665' ) {
				char[] myNameChars = actualRange.toCharArray();
				myNameChars[i] = 'h';
				actualRange = String.valueOf(myNameChars);
				//actualRange = 'h';
			}
			else if(actualRange.charAt(i) =='\u2663' ) {
				char[] myNameChars = actualRange.toCharArray();
				myNameChars[i] = 'c';
				actualRange = String.valueOf(myNameChars);
				//actualRange = 'c';
			}
			else if(actualRange.charAt(i) =='\u2666' ) {
				char[] myNameChars = actualRange.toCharArray();
				myNameChars[i] = 'd';
				actualRange = String.valueOf(myNameChars);
				//actualRange = 'd';
			}
			else if(actualRange.charAt(i) =='\u2660' ) {
				char[] myNameChars = actualRange.toCharArray();
				myNameChars[i] = 's';
				actualRange = String.valueOf(myNameChars);
				//actualRange = 's';
			}
		}
		return this.actualRange;
	}
	
}