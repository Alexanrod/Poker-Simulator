package Back;

public class Matrix {
	private static final int ROWCOL = 13;
	private String[] cards = { "A", "K","Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2" };
	private Hand[][] matrix = new Hand[ROWCOL][ROWCOL];

	public Matrix() {
		for (int i = 0; i < this.cards.length; i++) {
			for (int j = 0; j < this.cards.length; j++) {
				if (i < j) {
					this.matrix[i][j] = new Hand(this.cards[i], this.cards[j], "s");
				}
				else if(i > j){
					this.matrix[i][j] = new Hand(this.cards[j], this.cards[i], "o");
				}
				else {
					this.matrix[i][j] = new Hand(this.cards[j], this.cards[i]);
				}
			}
		}
	}

	public int size() {
		return ROWCOL;
	}
	
	public void imprimir() {
		for (int i = 0; i < this.cards.length; i++) {
			for (int j = 0; j < this.cards.length; j++) {
				System.out.print(this.matrix[i][j] + " ");
			}
			System.out.println("");
		}
	}
	public Hand get(int i, int j) {
		return this.matrix[i][j];
	}

}
