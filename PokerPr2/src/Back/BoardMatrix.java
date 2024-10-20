package Back;

public class BoardMatrix {
	private String[] cards = { "A", "K","Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2" };
	private String[] colors = { "\u2665","\u2663","\u2666","\u2660" };
	private Card[][] matrix = new Card[13][4];
	
	public BoardMatrix() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				matrix[i][j] = new Card(cards[i],colors[j]);
			}
		}
	}
	public void imprimir() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("");
		}
	}
	public Card get(int i, int j) {
		return matrix[i][j];
	}
	}
