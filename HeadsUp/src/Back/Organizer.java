package Back;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import Back.Carta.CardComparator;

public class Organizer {
	ArrayList<Carta> manoGanadora = new ArrayList<Carta>();

	public ArrayList<String> read() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		archivo = new File("src/lecturas/entrada1.txt");
		try {
			fr = new FileReader(archivo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		br = new BufferedReader(fr);

		// Lectura del fichero
		ArrayList<String> lines = new ArrayList<String>();
		try {

			String aux = br.readLine();
			while (aux != null && !aux.equals("")) {
				lines.add(aux);
				aux = br.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}

	public ArrayList<Carta> createTable(String lines) {
		String aux;
		ArrayList<Carta> cartas = new ArrayList<Carta>();
		for (int i = 0; i < lines.length(); i += 2) {
			aux = lines.substring(i, i + 2);
			try {
				cartas.add(new Carta(translateCard(aux.charAt(0)), aux.charAt(1)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Collections.sort(cartas, new CardComparator());
		return cartas;
	}

	public void ordenarCartas(ArrayList<Carta> cartas) {
		Collections.sort(cartas, new CardComparator());
	}

	public String bestHand(ArrayList<Carta> cartas, Jugador jug) {
		String mejorMano = "High Card";

		if (isStraightFlush(cartas)) {
			mejorMano = "Straight Flush";
		} else if (isPoker(cartas)) {
			mejorMano = "Poker";
		} else if (isFullHouse(cartas)) {
			mejorMano = "Full House";
		} else if (isFlush(cartas)) {
			mejorMano = "Flush";
		} else if (isStraight(cartas)) {
			mejorMano = "Straight";
		} else if (isSet(cartas)) {
			mejorMano = "Set";
		} else if (isDoublePair(cartas)) {
			mejorMano = "Double Pair";
		} else if (isPair(cartas)) {
			mejorMano = "Pair";
		} else {
			highCard(cartas);
		}
		fillBestHand(cartas);
		ArrayList<Carta> manoGanadoraAux = new ArrayList<Carta>(manoGanadora);
		jug.setMejorMano(manoGanadoraAux);
		jug.mejorManostr = mejorMano;
		return mejorMano;
	}

	public void fillBestHand(ArrayList<Carta> cartas) {
		if (manoGanadora.size() != 5) {
			int i = cartas.size() - 1;
			while (i >= 0) {
				if (!manoGanadora.contains(cartas.get(i))) {
					manoGanadora.add(cartas.get(i));
				}
				if (manoGanadora.size() == 5) {
					i = 0;
				}
				i--;
			}
		}
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

	/*
	 * private boolean isFullHouse(ArrayList<Carta> cartas) { HashMap<Integer,
	 * Integer> map = new HashMap<Integer, Integer>();
	 * 
	 * for (int i = 0; i < cartas.size(); i++) { if
	 * (map.containsKey(cartas.get(i).numero)) { int cantidad =
	 * map.get(cartas.get(i).numero); map.put(cartas.get(i).numero, ++cantidad); }
	 * else map.put(cartas.get(i).numero, 1); }
	 * 
	 * boolean pareja = false, trio = false;
	 * 
	 * for (Map.Entry<Integer, Integer> i : map.entrySet()) { if (i.getValue() == 2)
	 * pareja = true; else if (i.getValue() == 3) trio = true; }
	 * 
	 * return pareja && trio; }
	 */

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
			if (contador == 3)
				return true;
			i--;
		}

		manoGanadora.clear();
		return false;

	}

	public boolean isDoublePair(ArrayList<Carta> cartas) {
		boolean pair1 = false;
		int i = cartas.size() - 1;
		while (i > 0) {
			if (cartas.get(i - 1).numero == cartas.get(i).numero) {
				manoGanadora.add(cartas.get(i));
				manoGanadora.add(cartas.get(i - 1));
				i--;
				if (!pair1)
					pair1 = true;
				else
					return true;
			}
			i--;
		}
		manoGanadora.clear();
		return false;
	}

	private boolean isPair(ArrayList<Carta> cartas) {
		int i = cartas.size() - 1;

		while (i > 0) {
			if (cartas.get(i - 1).numero == cartas.get(i).numero) {
				manoGanadora.add(cartas.get(i - 1));
				manoGanadora.add(cartas.get(i));
				return true;
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

	/*
	 * private boolean isPoker(List<Carta> cartas) { int contador = 1; boolean poker
	 * = false; int i = cartas.size() - 1; manoGanadora.add(cartas.get(i));
	 * 
	 * while (i > 0 && !poker) { if (cartas.get(i - 1).numero ==
	 * cartas.get(i).numero) { contador++; manoGanadora.add(cartas.get(i - 1)); //
	 * creo que no es necesario /* if (i == 1) manoGanadora.add(cartas.get(i - 1));
	 * 
	 * } else { contador = 1; if (!poker) { manoGanadora.clear();
	 * manoGanadora.add(cartas.get(i - 1)); } } if (contador == 4) poker = true;
	 * i--; } if (!poker) manoGanadora.clear();
	 * 
	 * return poker; }
	 */

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

	private boolean isPoker(List<Carta> cartas) {
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
}
