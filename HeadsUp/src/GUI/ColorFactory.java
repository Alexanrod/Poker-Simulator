package GUI;

import java.awt.Color;

public class ColorFactory {
	
	public static Color create(String color) {
		switch(color) {
			case "blue" : return new Color(51,153,255);
			case "LBlue" : return new Color(210,235,255);
			case "green" : return new Color(60,255,60);
			case "LGreen" : return new Color(230,255,230);
			case "red" : return new Color(255,51,51);
			case "LRed" : return new Color(255,215,215);
			case "black" : return new Color(100,100,100);
			case "LBlack" : return new Color(220,220,220);
			case "Gold" : return new Color(239,184,16);
			default : return new Color(0,0,0);
		}
		
	}
}
