package Back;

public class Rangos {

	static String intervalo = "";

	public static String calculaRangos(String rango) {
		intervalo = "";
		String[] splitted = rango.split(",");
		for (int i = 0; i < splitted.length; i++) {
			String[] hands = new String[2];
			if (splitted[i].contains("-")) {
				String color = "";
				hands = splitted[i].split("-");

				if (hands[0].length() > 2) {
					color = String.valueOf(hands[0].charAt(2));
				} else if (hands[1].length() > 2) {
					color = String.valueOf(hands[1].charAt(2));
				}

				Hand hand1 = new Hand(String.valueOf(hands[0].charAt(0)), String.valueOf(hands[0].charAt(1)));
				Hand hand2 = new Hand(String.valueOf(hands[1].charAt(0)), String.valueOf(hands[1].charAt(1)));

				if (hand1.card1 == hand1.card2 && hand2.card1 == hand2.card2) {
					diagonal(hand1, hand2);
				} else if (hand1.card1 == hand2.card1) {
					horizontal(hand1, hand2, color);
				} else if (hand1.card2 == hand2.card2) {
					vertical(hand1, hand2, color);
				} else if (hand1.card2 == hand2.card1) {
					Hand pareja = new Hand(hand1.sc2, hand2.sc1);
					vertical(hand1, pareja, color);
					Hand anterior = new Hand(Hand.translateCard(pareja.card1), Hand.translateCard(pareja.card1 - 1));
					horizontal(anterior, hand2, color);
				} else if (hand1.card1 == hand2.card2) {
					Hand pareja = new Hand(hand1.sc1, hand2.sc2);
					horizontal(pareja, hand1, color);
					Hand siguiente = new Hand(Hand.translateCard(pareja.card1 + 1), Hand.translateCard(pareja.card1));
					vertical(hand2, siguiente, color);
				}
			} else if (splitted[i].contains("+")) {
				hands[0] = splitted[i];

				Hand hand = new Hand(String.valueOf(hands[0].charAt(0)), String.valueOf(hands[0].charAt(1)));

				if (hand.card1 == hand.card2) {
					for (int j = hand.card1; j <= 14; j++) {
						intervalo += Hand.translateCard(j) + Hand.translateCard(j) + ",";
					}
				} else {
					for (int j = hand.card2; j < hand.card1; j++) {
						intervalo += Hand.translateCard(hand.card1) + Hand.translateCard(j);
						if (hands[0].charAt(2) == 's') {
							intervalo += "s";
						} else if (hands[0].charAt(2) == 'o') {
							intervalo += "o";
						}
						intervalo += ",";
					}
				}
			}
		}

		String singleCards = "";
		for (int i = 0; i < splitted.length; i++) {
			if (!splitted[i].contains("-") && !splitted[i].contains("+")) {
				singleCards += splitted[i] += ",";
			}
		}

		intervalo += singleCards;
		intervalo = intervalo.substring(0, intervalo.length() - 1);

		return intervalo;
	}

	public static String calculaIntervalos(String rango) {
		String[] splitted = rango.split(",");

		if (splitted.length <= 1)
			return splitted[0];

		Hand[][] matriz = new Hand[13][13];

		for (int i = 0; i < matriz.length; i++)
			for (int j = 0; j < matriz[i].length; j++)
				matriz[i][j] = new Hand("-1", "-1");

		for (int i = 0; i < splitted.length; i++) {
			Hand h;
			if (splitted[i].length() == 2) {
				h = new Hand(String.valueOf(splitted[i].charAt(0)), String.valueOf(splitted[i].charAt(1)));
				matriz[14 - h.card1][14 - h.card2] = h;
			} else {
				h = new Hand(String.valueOf(splitted[i].charAt(0)), String.valueOf(splitted[i].charAt(1)),
						String.valueOf(splitted[i].charAt(2)));
				if (h.color.equals("s"))
					matriz[14 - h.card1][14 - h.card2] = h;
				else
					matriz[14 - h.card2][14 - h.card1] = h;
			}
		}

		// FILAS
		String aux = "";
		for (int i = 0; i < matriz.length; i++) {
			int cont = 1;
			String ini = "", fin = "";
			for (int j = 0; j < matriz[i].length - 1; j++) {
				if (matriz[i][j].card1 != -1 && matriz[i][j + 1].card1 != -1 && i != j && i != j + 1) {
					if (cont == 1) {
						ini = matriz[i][j].toString();
					}
					fin = matriz[i][j + 1].toString();
					cont++;
					if (j == matriz[i].length - 2) {
						aux += ini + "-" + fin + ",";
					}
				} else if (cont > 1) {
					aux += ini + "-" + fin + ",";
					cont = 1;
				} else {
					cont = 1;
				}
			}
		}
		
		if(!aux.equals(""))
			matriz = reCalculaMatriz(aux, matriz);
			

		// COLUMNAS
		for (int i = 0; i < matriz.length; i++) {
			int cont = 1;
			String ini = "", fin = "";
			for (int j = 0; j < matriz.length - 1; j++) {
				if (matriz[j][i].card1 != -1 && matriz[j + 1][i].card1 != -1 && i != j && i != j + 1) {
					if (cont == 1) {
						ini = matriz[j][i].toString();
					}
					fin = matriz[j + 1][i].toString();
					cont++;
					if (j == matriz[i].length - 2) {
						aux += ini + "-" + fin + ",";
					}
				} else if (cont > 1) {
					aux += ini + "-" + fin + ",";
					cont = 1;
				} else {
					cont = 1;
				}
			}
		}
		
		if(!aux.equals(""))
			matriz = reCalculaMatriz(aux, matriz);

		// DIAGONAL
		String ini = "", fin = "";
		int cont = 1;
		for (int i = 0; i < matriz.length - 1; i++) {
			if (matriz[i][i].card1 != -1 && matriz[i + 1][i + 1].card1 != -1) {
				if (cont == 1) {
					ini = matriz[i][i].toString();
				}
				fin = matriz[i + 1][i + 1].toString();
				cont++;
				if (i == matriz.length - 2) {
					aux += ini + "-" + fin + ",";
				}
			} else if (cont > 1) {
				aux += ini + "-" + fin + ",";
				cont = 1;
			} else {
				cont = 1;
			}
		}
		
		if(!aux.equals(""))
			matriz = reCalculaMatriz(aux, matriz);

		// MANOS SUELTAS
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				if (matriz[i][j].card1 != -1) {
					aux += matriz[i][j].toString() + ",";
				}
			}
		}
		
		//FUNCIÓN PARA TRANSFORMAR INTERVALOS A MANOS CON "+"
		aux = encuentraMases(aux);
		
		if (aux.charAt(aux.length() - 1) == ',')
			return aux.substring(0, aux.length() - 1);

		return aux;
	}

	private static Hand[][] reCalculaMatriz(String aux, Hand[][] matriz) {
		String calc = Rangos.calculaRangos(aux);

		String[] calcSplitted = calc.split(",");

		for (String a : calcSplitted) {
			Hand h = new Hand(a.charAt(0), a.charAt(1));
			if (a.length() == 3) {
				if (a.charAt(2) == 's')
					matriz[14 - h.card1][14 - h.card2] = new Hand("-1", "-1");
				else
					matriz[14 - h.card2][14 - h.card1] = new Hand("-1", "-1");
			} else {
				matriz[14 - h.card1][14 - h.card2] = new Hand("-1", "-1");
			}
		}
		return matriz;
	}

	private static String encuentraMases(String aux) {
		String[] splitted = aux.split(",");

		for (int i = 0; i < splitted.length; i++) {
			if (splitted[i].contains("-")) {
				String[] sp = splitted[i].split("-");
				if (sp[0].length() == 2) {
					Hand h1 = new Hand(String.valueOf(sp[0].charAt(0)), String.valueOf(sp[0].charAt(1)));
					Hand h2 = new Hand(String.valueOf(sp[1].charAt(0)), String.valueOf(sp[1].charAt(1)));
					if (h1.card1 == 14 && h1.card2 == 14)
						splitted[i] = h2.toString() + "+";
				} else {
					Hand h1 = new Hand(String.valueOf(sp[0].charAt(0)), String.valueOf(sp[0].charAt(1)),
							String.valueOf(splitted[i].charAt(2)));
					Hand h2 = new Hand(String.valueOf(sp[1].charAt(0)), String.valueOf(sp[1].charAt(1)),
							String.valueOf(splitted[i].charAt(2)));
					if (h1.card1 - 1 == h1.card2)
						splitted[i] = h2.toString() + "+";
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		for (String s : splitted) {
			sb.append(s).append(",");
		}
		String result = sb.deleteCharAt(sb.length() - 1).toString();

		return result;
	}

	private static void diagonal(Hand hand1, Hand hand2) {
		if (hand1.card1 < hand2.card1) {
			Hand aux = hand2;
			hand2 = hand1;
			hand1 = aux;
		}

		for (int j = 0; j <= hand1.card1 - hand2.card1; j++) {
			String m = Hand.translateCard(j + hand2.card1) + Hand.translateCard(j + hand2.card2);
			intervalo += m += ",";
		}
	}

	private static void horizontal(Hand hand1, Hand hand2, String color) {
		if (hand1.card2 < hand2.card2) {
			Hand aux = hand2;
			hand2 = hand1;
			hand1 = aux;
		}

		for (int j = 0; j <= hand1.card2 - hand2.card2; j++) {
			String m = hand1.sc1;
			m += Hand.translateCard(j + hand2.card2);

			if (!hand1.sc1.equals(Hand.translateCard(j + hand2.card2))) {
				m += color;
			}

			intervalo += m += ",";
		}
	}

	private static void vertical(Hand hand1, Hand hand2, String color) {
		if (hand1.card1 < hand2.card1) {
			Hand aux = hand2;
			hand2 = hand1;
			hand1 = aux;
		}

		for (int j = 0; j <= hand1.card1 - hand2.card1; j++) {
			String m = Hand.translateCard(j + hand2.card1);
			m += hand1.sc2;

			if (!hand2.sc1.equals(Hand.translateCard(j + hand2.card2))) {
				m += color;
			}

			intervalo += m += ",";
		}
	}

}
