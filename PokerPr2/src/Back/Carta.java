package Back;

import java.util.Comparator;

public class Carta {

	public int numero;
	public char color;
	public char from;

	public Carta(int numero, char color) {
		this.numero = numero;
		this.color = color;
		this.from = 'b';
	}
	
	public Carta(int numero, char color, char from) {
		this.numero = numero;
		this.color = color;
		this.from = from;
	}

	static class CardComparator implements Comparator<Carta> {
		@Override
		public int compare(Carta o1, Carta o2) {
			if (o1.numero < o2.numero) {
				return -1;
			} else if (o1.numero > o2.numero) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	public int getNumero() {
		return this.numero;
	}

	public static char translateCard(int num) {
		char c;
		if (num == 14) {
			c = 'A';
		} else if (num == 13) {
			c = 'K';
		} else if (num == 12) {
			c = 'Q';
		} else if (num == 11) {
			c = 'J';
		} else if (num == 10) {
			c = 'T';
		} else {
			c = Character.forDigit(num, 10);
		}
		return c;
	}

	public String toString() {
		return String.valueOf(translateCard(this.numero)) + this.color;
	}
}
