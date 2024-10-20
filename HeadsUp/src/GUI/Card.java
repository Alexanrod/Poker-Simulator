package GUI;

public class Card {
	String color = "";
	String card="";
	public Card(String card, String color) {
		this.card = card;
		this.color = color;
		
	}
	public String toString() {
		return card+ color;
	}

}
