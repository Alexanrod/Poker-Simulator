package Back;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import Back.Carta.CardComparator;

public class Combos {

	ArrayList<Carta> manoGanadora = new ArrayList<Carta>();
	private int ncStrFlush = 0, ncQuads = 0, ncFullHouse = 0, ncFlush = 0, ncStraight = 0, ncSet = 0, ncTwoPair = 0,
			ncOverPair = 0, ncTopPair = 0, ncPocketPair = 0, ncMiddlePair = 0, ncWeakPair = 0, ncAcePair = 0,
			ncAceHigh = 0, ncNoMadeHand = 0;
	private String[] colors = { "h", "c", "d", "s" };
	
	public void doCombos(String matrixHands, String boardCards) {
		String[] splittedMC = matrixHands.split(",");
		for (int i = 0; i < splittedMC.length; i++) {
			calcularCombinaciones(splittedMC[i], boardCards);
		}
	}

	private void calcularCombinaciones(String hand, String table) {
		/*
		 * HAND = AKo || TABLE =AhQhJc //HAND o =12 (AKo) pareja = 6 (AA) s = 4 (AKs)
		 */
		// JUNTAR hand+table == HAND = AKs || TABLE =AdQhJh10h
		String[] combos;
		if (hand.length() == 3 && hand.charAt(2) == 's') {
			// Sacar las combinaciones AhKh AsKs AdKd AcKc
			combos = generarCombosSuited(hand, table);
			// AhKh+ AdQhJh10h
			// AcKc+AdQhJh10h...
		} else if (hand.length() == 3 && hand.charAt(2) == 'o') {
			combos = generarCombosOffsuited(hand, table);
		} else {
			combos = generarCombosParejas(hand, table);
		}
		

		int i = 0;
		while (i < combos.length && combos[i] != null) {
			String handAndTable = combos[i] + "," + table;
			String[] splitted = handAndTable.split(",");
			ArrayList<Carta> cartas = new ArrayList<Carta>();
			for (String carta : splitted) {
				Carta c;
				if(carta.length() > 2) {
					c = new Carta(Hand.translateCard(String.valueOf(carta.charAt(0))), carta.charAt(1), carta.charAt(2));
				}else {
					c = new Carta(Hand.translateCard(String.valueOf(carta.charAt(0))), carta.charAt(1));
				}
				cartas.add(c);
			}
			Collections.sort(cartas, new CardComparator());
			bestHand(cartas);
			i++;
		}

	}

	String[] generarCombosSuited(String hand, String table) {
		String[] combos = new String[4];
		int cont = 0;
		for (int i = 0; i < 4; i++) {
			String carta1 = hand.charAt(0) + colors[i];
			String carta2 = hand.charAt(1) + colors[i];
			if (!table.contains(carta1) && !table.contains(carta2)) {
				combos[cont] = carta1 + "h," + carta2 + "h";
				cont++;
			}
		}
		return combos;// AhKh,AcKc,Kd,AsKs
	}

	String[] generarCombosOffsuited(String hand, String table) {
		String[] combos = new String[12];
		int cont = 0;
		for (int i = 0; i < 4 && cont < 12; i++) {
			String carta1 = hand.charAt(0) + colors[i];
			for (int j = 0; j < 4 && cont < 12; j++) {
				String carta2 = hand.charAt(1) + colors[j];
				if (i != j) {
					if (!table.contains(carta1) && !table.contains(carta2)) {
						combos[cont] = carta1 + "h," + carta2 + "h";
						cont++;
					}
				}
			}
		}
		return combos;// AhKc,AhKs,AhKd,AhKs
	}

	public String[] generarCombosParejas(String hand, String table) {
		String[] combos = new String[6];
		int cont = 0;
		for (int i = 0; i < 4 && cont < 6; i++) {
			String carta1 = hand.charAt(0) + colors[i] + "h";
			for (int j = 0; j < 4 && cont < 6; j++) {
				String carta2 = hand.charAt(1) + colors[j] + "h";
				if (i != j) {
					if (!table.contains(carta1.substring(0, carta1.length()-1)) && !table.contains(carta2.substring(0, carta1.length()-1)) && !isContained(carta1, carta2, combos)) {
						combos[cont] = carta1 + "," + carta2;
						cont++;
					}
				}
			}
		}
		return combos;// AhKc,AhKs,AhKd,AhKs
	}

	boolean isContained(String carta1, String carta2, String[] combos) {
		for (int i = 0; i < combos.length; i++) {
			if (combos[i] != null && combos[i].contains(carta2 + "," + carta1))
				return true;
		}
		return false;
	}

	public void bestHand(ArrayList<Carta> cartas) {
		
		if (isStraightFlush(cartas) && manoGanadoraHasHand()) {
			this.ncStrFlush++;
			// cont++;
			// llamar a la GUI
		} else if (isPoker(cartas) && manoGanadoraHasHand()) {
			this.ncQuads++;
		} else if (isFullHouse(cartas) && manoGanadoraHasHand()) {
			this.ncFullHouse++;
		} else if (isFlush(cartas) && manoGanadoraHasHand()) {
			this.ncFlush++;
		} else if (isStraight(cartas) && manoGanadoraHasHand()) {
			this.ncStraight++;
		} else if (isSet(cartas) && manoGanadoraHasHand()) {
			this.ncSet++;
		} else if (isDoublePair(cartas) && manoGanadoraHasHand()) {
			this.ncTwoPair++;
		} 
		else if (isPair(cartas)) {
			if(isOverPair(cartas))
				this.ncOverPair++;
			else if(isTopPair(cartas))
				this.ncTopPair++;
			else if(isPocketPair(cartas))
				this.ncPocketPair++;
			else if(isMiddlePair(cartas))
				this.ncMiddlePair++;
			else
				this.ncWeakPair++;		
		} else {
			highCard(cartas);
			if(manoGanadoraHasAce())
				this.ncAceHigh++;
			else 
				this.ncNoMadeHand++;	
			manoGanadora.clear();
		}

	}
	
	private boolean isMiddlePair(ArrayList<Carta> cartas) {
		Carta maxCard = null;
		boolean found = false;
		int cont = 0;
		
		for(int i = cartas.size() - 1; i >= 0 && !found; i--) {
			if (cartas.get(i).from != 'h' && cont == 0) {
				cont++;
			}else if(cartas.get(i).from != 'h' && cont == 1) {
				maxCard = cartas.get(i);
				found = true;
			}
		}
		
		if(maxCard == null)
			return false;
		
		return manoGanadora.contains(maxCard);
	}

	private boolean isPocketPair(ArrayList<Carta> cartas) {
		for(Carta c: manoGanadora) {
			if(c.from != 'h')
				return false;
		}
		
		Carta maxCard = null;
		boolean found = false;
		
		for(int i = cartas.size() - 1; i >= 0 && !found; i--) {
			if (cartas.get(i).from != 'h') {
				maxCard = cartas.get(i);
				found = true;
			}
		}
		
		if(maxCard == null)
			return false;
		
		Carta maxCard2 = null;
		int cont = 0;
		found = false;
		for(int i = cartas.size() - 1; i >= 0 && !found; i--) {
			if (cartas.get(i).from != 'h' && cont == 0) {
				cont++;
			}else if(cartas.get(i).from != 'h' && cont == 1) {
				maxCard2 = cartas.get(i);
				found = true;
			}
		}
		
		if(maxCard2 == null)
			return false;
		
		for(Carta c: manoGanadora) {
			if(c.numero < maxCard.numero && c.numero > maxCard2.numero)
				return true;
		}
		
		return false;
	}

	private boolean isTopPair(ArrayList<Carta> cartas) {
		Carta maxCard = null;
		boolean found = false;
		
		for(int i = cartas.size() - 1; i >= 0 && !found; i--) {
			if (cartas.get(i).from != 'h') {
				maxCard = cartas.get(i);
				found = true;
			}
		}
		
		if(maxCard == null)
			return false;
		
		return manoGanadora.contains(maxCard);
	}

	private boolean isOverPair(ArrayList<Carta> cartas) {
		for(Carta c: manoGanadora) {
			if(c.from != 'h')
				return false;
			for(Carta c2: cartas) {
				if(!manoGanadora.contains(c2) && c.numero <= c2.numero) 
					return false;
			}
		}
		return true;
	}

	private boolean manoGanadoraHasHand() {
		for(Carta c: manoGanadora) {
			if(c.from == 'h')
				return true;
		}
		manoGanadora.clear();
		return false;
	}
	
	private boolean manoGanadoraHasAce() {
		for(Carta c: manoGanadora) {
			if(c.numero == 14 && c.from == 'h')
				return true;
		}
		return false;
	}

	private boolean isStraightFlush(ArrayList<Carta> cartas) {
		ArrayList<Carta> h = new ArrayList<Carta>();
		ArrayList<Carta> d = new ArrayList<Carta>();
		ArrayList<Carta> c = new ArrayList<Carta>();
		ArrayList<Carta> s = new ArrayList<Carta>();

		int i = cartas.size() - 1;

		while (i >= 0) {
			if (cartas.get(i).color == 'h') {
				h.add(cartas.get(i));
				if (h.size() >= 5) {
					Collections.sort(h, new CardComparator());
					if (isStraight(h)) {
						return true;
					}
				}
			} else if (cartas.get(i).color == 'd') {
				d.add(cartas.get(i));
				if (d.size() >= 5) {
					Collections.sort(d, new CardComparator());
					if (isStraight(d)) {
						return true;
					}
				}
			} else if (cartas.get(i).color == 'c') {
				c.add(cartas.get(i));
				if (c.size() >= 5) {
					Collections.sort(c, new CardComparator());
					if (isStraight(c)) {
						return true;
					}
				}
			} else {
				s.add(cartas.get(i));
				if (s.size() >= 5) {
					Collections.sort(s, new CardComparator());
					if (isStraight(s)) {
						return true;
					}
				}
			}
			i--;
		}

		manoGanadora.clear();
		return false;
	}

	private void highCard(ArrayList<Carta> cartas) {
		int i = cartas.size() - 1;
		int contador = 0;
		while (contador < 5 && i >= 0) {
			manoGanadora.add(cartas.get(i));
			contador++;
			i--;
		}
	}

	private boolean isFullHouse(ArrayList<Carta> cartas) {
		int i = cartas.size() - 1;
		int count = 1;
		boolean set = false, pair = false;

		while (i > 0) {
			if (cartas.get(i).numero - cartas.get(i - 1).numero == 0) {
				count++;
			} else {
				if (count == 3 && !set) {
					set = true;
					manoGanadora.add(0, cartas.get(i + 2));
					manoGanadora.add(0, cartas.get(i + 1));
					manoGanadora.add(0, cartas.get(i));
				} else if (count >= 2 && !pair) {
					pair = true;
					manoGanadora.add(cartas.get(i + 1));
					manoGanadora.add(cartas.get(i));
				}
				count = 1;
			}

			if (i - 1 == 0) {
				if (count == 3 && !set) {
					set = true;
					manoGanadora.add(0, cartas.get(i + 1));
					manoGanadora.add(0, cartas.get(i));
					manoGanadora.add(0, cartas.get(i - 1));
				} else if (count >= 2 && !pair) {
					pair = true;
					manoGanadora.add(cartas.get(i));
					manoGanadora.add(cartas.get(i - 1));
				}
			}

			if (pair && set)
				i = 0;
			i--;
		}

		if (!pair || !set)
			manoGanadora.clear();

		return pair && set;
	}

	private boolean isSet(ArrayList<Carta> cartas) {
		int contador = 1;
		int i = cartas.size() - 1;

		while (i > 0) {
			if (cartas.get(i - 1).numero == cartas.get(i).numero) {
				if (contador == 1) {
					manoGanadora.add(cartas.get(i));
				}
				contador++;
				manoGanadora.add(cartas.get(i - 1));
			} else {
				contador = 1;
				manoGanadora.clear();
			}
			if (contador >= 3 && manoGanadoraHasHand())
				return true;
			i--;
		}

		manoGanadora.clear();
		return false;
	}
	
	

	private boolean isDoublePair(ArrayList<Carta> cartas) {
		int cont = 1;
		boolean pair1 = false, pair2 = false, pairtotal = false;
		manoGanadora.add(cartas.get(cartas.size() - 1));
		int i = cartas.size() - 1;
		while (i > 0 && pairtotal == false) {
			if (cartas.get(i - 1).numero == cartas.get(i).numero) {
				cont++;
				manoGanadora.add(cartas.get(i - 1));
			} else {
				cont = 1;
				if (pair1 == false) {
					manoGanadora.clear();
					manoGanadora.add(cartas.get(i - 1));
				} else if (pair2 == false) {
					manoGanadora.remove(2);
					manoGanadora.add(cartas.get(i - 1));
				}
			}
			if (cont == 2) {
				if (pair1 == true) {
					pair2 = true;
				} else {
					pair1 = true;
					manoGanadora.add(cartas.get(i));
					manoGanadora.add(cartas.get(i - 1));
				}

				cont = 1;
			}
			if (pair1 == true && pair2 == true)
				pairtotal = true;

			i--;
		}
		if (pairtotal == false)
			manoGanadora.clear();

		return pairtotal;
	}
	/*
	 * private boolean isPair(ArrayList<Carta> cartas) { int cont = 1; boolean pair
	 * = false; manoGanadora.add(cartas.get(cartas.size()-1)); int i =
	 * cartas.size()-1; while(i >0 && pair == false) { if (cartas.get(i -1 ).numero
	 * == cartas.get(i).numero) { cont++; manoGanadora.add(cartas.get(i-1)); } else
	 * { cont = 1; if (pair == false) { manoGanadora.clear();
	 * manoGanadora.add(cartas.get(i - 1)); } } if (cont == 2) pair = true; i--; }
	 * if (pair == false) manoGanadora.clear(); return pair; }
	 */

	private boolean isPair(ArrayList<Carta> cartas) {
		int i = cartas.size() - 1;

		while (i > 0) {
			if (cartas.get(i - 1).numero == cartas.get(i).numero) {
				manoGanadora.add(cartas.get(i - 1));
				manoGanadora.add(cartas.get(i));
				if(manoGanadoraHasHand())
					return true;
				else manoGanadora.clear();
			}
			i--;
		}

		return false;
	}

	private boolean isFlush(ArrayList<Carta> cartas) {
		ArrayList<Carta> h = new ArrayList<Carta>();
		ArrayList<Carta> d = new ArrayList<Carta>();
		ArrayList<Carta> c = new ArrayList<Carta>();
		ArrayList<Carta> s = new ArrayList<Carta>();

		int i = cartas.size() - 1;

		while (i >= 0) {
			if (cartas.get(i).color == 'h') {
				h.add(cartas.get(i));
				if (h.size() == 5) {
					manoGanadora = h;
					return true;
				}
			} else if (cartas.get(i).color == 'd') {
				d.add(cartas.get(i));
				if (d.size() == 5) {
					manoGanadora = d;
					return true;
				}
			} else if (cartas.get(i).color == 'c') {
				c.add(cartas.get(i));
				if (c.size() == 5) {
					manoGanadora = c;
					return true;
				}
			} else {
				s.add(cartas.get(i));
				if (s.size() == 5) {
					manoGanadora = s;
					return true;
				}
			}
			i--;
		}

		return false;
	}

	private boolean isStraight(ArrayList<Carta> cartas) {
		ArrayList<Carta> h = new ArrayList<Carta>();
		int contador = 1;
		int i = cartas.size() - 1;

		while (i > 0) {
			if (cartas.get(i).numero - cartas.get(i - 1).numero == 1) {
				if (contador == 1) {
					h.add(cartas.get(i));
				}
				h.add(cartas.get(i - 1));
				contador++;
			} else if (cartas.get(i).numero - cartas.get(i - 1).numero != 0) {
				h.clear();
				contador = 1;
			}

			if (contador == 5) {
				manoGanadora = h;
				return true;
			}
			i--;
		}

		if (contador == 4 && cartas.get(0).numero == 2 && cartas.get(cartas.size() - 1).numero == 14) {
			contador++;
			h.add(cartas.get(cartas.size() - 1));
			manoGanadora = h;
		}

		return contador == 5;
	}

	/*
	 * private boolean isStraight(ArrayList<Carta> cartas) { int count = 1; boolean
	 * straight = false; int i = cartas.size() - 1; manoGanadora.add(cartas.get(i));
	 * 
	 * if (cartas.get(i - 1).numero == 2 && cartas.get(cartas.size() - 1).numero ==
	 * 14) { count++; manoGanadora.add(cartas.get(cartas.size() - 1)); }
	 * 
	 * while (i > 0 && !straight) { if (cartas.get(i).numero - cartas.get(i -
	 * 1).numero == 1) { if (count == 1) { manoGanadora.add(cartas.get(i)); }
	 * count++; manoGanadora.add(cartas.get(i - 1));
	 * 
	 * } else if (cartas.get(i).numero - cartas.get(i - 1).numero != 0 && !straight)
	 * { count = 1; manoGanadora.clear(); }
	 * 
	 * if (count == 5) straight = true; i--; }
	 * 
	 * return straight; }
	 */

//	  private boolean isPoker(List<Carta> cartas) { int contador = 1; boolean poker
//	  = false; int i = cartas.size() - 1; manoGanadora.add(cartas.get(i));
//	  
//	  while (i > 0 && !poker) { if (cartas.get(i - 1).numero ==
//	  cartas.get(i).numero) { contador++; manoGanadora.add(cartas.get(i - 1));
//	  creo que no es necesario if (i == 1) manoGanadora.add(cartas.get(i - 1));
//	  
//	  } else { contador = 1; if (!poker) { manoGanadora.clear();
//	  manoGanadora.add(cartas.get(i - 1)); } } if (contador == 4) poker = true;
//	  i--; } if (!poker) manoGanadora.clear();
//	  
//	  return poker; }

	/*
	 * private boolean isPoker(List<Carta> cartas) { int contador = 1; boolean poker
	 * = false; manoGanadora.add(cartas.get(0)); for (int i = 0; i < cartas.size() -
	 * 1; i++) { if (cartas.get(i + 1).numero == cartas.get(i).numero) { contador++;
	 * manoGanadora.add(cartas.get(i + 1)); if (i == cartas.size() - 2)
	 * manoGanadora.add(cartas.get(i + 1)); } else { contador = 1; if (poker ==
	 * false) { manoGanadora.clear(); manoGanadora.add(cartas.get(i + 1)); } }
	 * 
	 * if (contador == 4) poker = true; } if (!poker) manoGanadora.clear(); return
	 * poker; }
	 */

	private boolean isPoker(ArrayList<Carta> cartas) {
		int contador = 1;
		int i = cartas.size() - 1;

		while (i > 0) {
			if (cartas.get(i - 1).numero == cartas.get(i).numero) {
				if (contador == 1) {
					manoGanadora.add(cartas.get(i));
				}
				contador++;
				manoGanadora.add(cartas.get(i - 1));
			} else {
				contador = 1;
				manoGanadora.clear();
			}
			if (contador == 4)
				return true;
			i--;
		}

		manoGanadora.clear();
		return false;
	}

	/*
	 * private boolean isStraightFlush(ArrayList<Carta> cartas) { ArrayList<Carta> h
	 * = new ArrayList<Carta>(); int contador = 1; int i = cartas.size() - 1;
	 * 
	 * while(i > 0) { if (cartas.get(i).numero - cartas.get(i - 1).numero == 1) {
	 * if(contador == 1) { h.add(cartas.get(i)); } h.add(cartas.get(i - 1));
	 * contador++; } else if (cartas.get(i).numero - cartas.get(i - 1).numero != 0)
	 * { h.clear(); contador = 1; }
	 * 
	 * if (contador == 5) { manoGanadora = h; return true; } i--; }
	 * 
	 * if (contador == 4 && cartas.get(0).numero == 2 && cartas.get(cartas.size() -
	 * 1).numero == 14) { contador++; h.add(cartas.get(cartas.size()-1));
	 * manoGanadora = h; }
	 * 
	 * return contador == 5; }
	 */

	public void draws(ArrayList<Carta> cartas) {
		if (!isFlush(cartas) && isFlushDraw(cartas, 4)) {
			System.out.println("Draw: Flush");
		}
		if (!isStraight(cartas) && openEnded(cartas, 4)) {
			System.out.println("Draw: Straight Open-ended");
		}
		if (!isStraight(cartas) && isGutShot(cartas)) {// !isStraight2(cartas,4)
			System.out.println("Draw: Straight GutShot");
		}
	}

	private boolean isGutShot(ArrayList<Carta> cartas) {
		int contador = 1;
		boolean straight = false, turno = false;
		int i = 0;
		if (cartas.get(0).numero == 2 && cartas.get(cartas.size() - 1).numero == 14) {
			contador++;
		} else if (cartas.get(0).numero == 3 && cartas.get(cartas.size() - 1).numero == 14) {
			contador++;
			turno = true;
		}
		while (i < cartas.size() - 1) {

			if (cartas.get(i + 1).numero - cartas.get(i).numero == 1)
				contador++;
			else if (cartas.get(i + 1).numero - cartas.get(i).numero == 2 && turno == false) {
				contador++;
				// i++;
				turno = true;
			} else if (cartas.get(i + 1).numero - cartas.get(i).numero != 0) {
				turno = false;
				contador = 1;
			}
			if (contador == 4 && turno == true)
				straight = true;
			i++;
		}

		return straight;
	}

	private boolean openEnded(ArrayList<Carta> cartas, int n) {
		int contador = 1;
		boolean straight = false;
		if (cartas.get(0).numero == 2 && cartas.get(cartas.size() - 1).numero == 14) {
			contador++;
		}
		for (int i = 0; i < cartas.size() - 1; i++) {
			if (cartas.get(i + 1).numero - cartas.get(i).numero == 1)
				contador++;
			else if (cartas.get(i + 1).numero - cartas.get(i).numero != 0) {
				contador = 1;
			}

			if (contador == n)
				straight = true;
		}
		return straight;
	}

	private boolean isFlushDraw(ArrayList<Carta> cartas, int n) {
		int flush[] = new int[4];
		for (int i = 0; i < cartas.size(); i++) {
			if (cartas.get(i).color == 'h') {
				flush[0]++;
			} else if (cartas.get(i).color == 'd') {
				flush[1]++;
			} else if (cartas.get(i).color == 'c') {
				flush[2]++;
			} else {
				flush[3]++;
			}
		}
		Arrays.sort(flush);
		return flush[3] == n;
	}

	public static int translateCard(char c) {
		int num = 0;
		if (c == 'A') {
			num = 14;
		} else if (c == 'K') {
			num = 13;
		} else if (c == 'Q') {
			num = 12;
		} else if (c == 'J') {
			num = 11;
		} else if (c == 'T') {
			num = 10;
		} else {
			num = Character.getNumericValue(c);
		}
		return num;
	}

	public int[] getCombos() {
		int[] comboList  = {ncStrFlush, ncQuads, ncFullHouse, ncFlush, ncStraight, ncSet, ncTwoPair, ncOverPair,
				ncTopPair , ncPocketPair, ncMiddlePair, ncWeakPair, ncAcePair, ncAceHigh, ncNoMadeHand};
		return comboList;
	}
	
	public int getTotalCombos() {
		int n = ncStrFlush + ncQuads + ncFullHouse + ncFlush + ncStraight + ncSet + ncTwoPair + ncOverPair +
				ncTopPair + ncPocketPair + ncMiddlePair + ncWeakPair + ncAcePair + ncAceHigh + ncNoMadeHand;
		return n;
	}
}
