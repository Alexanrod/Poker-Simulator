package Back;

public class Hand {
	String color = "";
	int card1;
	int card2;
	String sc1;
	String sc2;

	public Hand(String card1, String card2) {
		this.card1 = translateCard(card1);
		this.card2 = translateCard(card2);
		this.sc1 = card1;
		this.sc2 = card2;
	}
	
	public Hand(String card1, String card2, String color) {
		this.card1 = translateCard(card1);
		this.card2 = translateCard(card2);
		this.sc1 = card1;
		this.sc2 = card2;
		this.color = color;
	}
	
	public Hand(char card1, char card2, char color) {
		this.card1 = translateCard(String.valueOf(card1));
		this.card2 = translateCard(String.valueOf(card2));
		this.sc1 = String.valueOf(card1);
		this.sc2 = String.valueOf(card2);
		this.color = String.valueOf(color);
	}
	
	public Hand(char card1, char card2) {
		this.card1 = translateCard(String.valueOf(card1));
		this.card2 = translateCard(String.valueOf(card2));
		this.sc1 = String.valueOf(card1);
		this.sc2 = String.valueOf(card2);
	}

	public static int translateCard(String c) {
		int num = 0;
		if (c.equals("A")) {
			num = 14;
		} else if (c.equals("K")) {
			num = 13;
		} else if (c.equals("Q")) {
			num = 12;
		} else if (c.equals("J")) {
			num = 11;
		} else if (c.equals("T")) {
			num = 10;
		} else {
			num = Integer.parseInt(c);
		}
		return num;
	}
	
	static String translateCard(int num) {
		if(num == 14) {
			return "A";
		}
		else if(num == 13) {
			return "K";
		}
		else if(num == 12) {
			return "Q";
		}
		else if(num == 11) {
			return "J";
		}
		else if(num == 10) {
			return "T";
		}
		else {
			return String.valueOf(num);
		}
	}
	
	public String toString() {
		return translateCard(card1) + translateCard(card2) + color;
	}

}
