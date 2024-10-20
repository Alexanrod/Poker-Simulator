package Back;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Back.Jugador;
import Back.JugadorComparator;
import GUI.MainWindow;

public class EquityCalculator {
	// private Jugador jugadores[] = new Jugador[6];
	private static final int NUMVECES = 2000000;
	ArrayList<Jugador> jugadores = new ArrayList<Jugador>(6);
	private ArrayList<Carta> board = new ArrayList<Carta>();
	private ArrayList<Carta> baraja = new ArrayList<Carta>();

	Organizer organizer = new Organizer();

	public EquityCalculator() {
		crearBaraja();
		// generarJugadores();
		// manejadorPorcentajes();
	}

	// Lo tenemos mejor hecho
	public void crearBaraja() {
		baraja.clear();
		for (int i = 0; i < 4; i++) {
			for (int j = 2; j <= 14; j++) {
				if (i == 0) {
					baraja.add(new Carta(j, 'h'));
				} else if (i == 1) {
					baraja.add(new Carta(j, 'd'));
				} else if (i == 2) {
					baraja.add(new Carta(j, 'c'));
				} else {
					baraja.add(new Carta(j, 's'));
				}
			}
		}
	}

	public void generarBoardRandom() {
		ArrayList<Carta> tempbaraja = (ArrayList<Carta>) this.baraja.clone();
		for (int i = 0; i < 5; i++) {
			board.add(getCartaRandomBorrando(tempbaraja));
		}
	}

	public void generarJugadoresRandom() {
		for (int i = 0; i < 2; i++) {
			jugadores.add(new Jugador(getCartaRandomBorrando(baraja), getCartaRandomBorrando(baraja)));
		}
	}

	private Carta getCartaRandomBorrando(ArrayList<Carta> tmp) {
		Carta c = getCartaRandom(tmp);
		tmp.remove(c);
		return c;
	}

	private void removeCartaBaraja(Carta c) {
		for (int i = 0; i < baraja.size(); i++) {
			if (baraja.get(i).numero == c.numero && baraja.get(i).color == c.color) {
				baraja.remove(i);
				return;
			}
		}
	}

	public void setCartasJugadorBorrando(String mano) {
		String[] cartas = mano.split(",");
		ArrayList<Carta> cs = new ArrayList<Carta>(2);
		for (int i = 0; i < cartas.length; i++) {
			Carta c = new Carta(cartas[i].charAt(0), cartas[i].charAt(1));
			removeCartaBaraja(c);
			cs.add(c);
		}
		jugadores.add(new Jugador(cs.get(0), cs.get(1)));
	}

	public void setCartasBoardBorrando(String board) {
		String[] cartas = board.split(",");
		for (int i = 0; i < cartas.length; i++) {
			Carta c = new Carta(cartas[i].charAt(0), cartas[i].charAt(1));
			this.board.add(c);
		}
	}
	
	public void borrarBoard(int i) {
		removeCartaBaraja(this.board.get(i));
	}
	
	public void createBoard(String board){
		String[] cartas = board.split(",");
		for (int i = 0; i < cartas.length; i++) {
			Carta c = new Carta(cartas[i].charAt(0), cartas[i].charAt(1));
			this.board.add(c);
		}
	}

	private Carta getCartaRandom(ArrayList<Carta> baraja) {
		Random rnd = new Random();
		int i = (int) (rnd.nextDouble() * baraja.size());
		Carta c = baraja.get(i);
		return c;
	}

	public void manejadorPorcentajes() {
		int numVeces = 2000000;
		Carta aux;

		// PREFLOP
		for (int i = 0; i < numVeces; i++) {
			calcularVictoriasPreFlop();
		}
		porcentajesYClear(numVeces);

		// FLOP
		for (int i = 0; i < 3; i++) {
			aux = getCartaRandomBorrando(baraja);
			board.add(aux);
		}

		System.out.println("FLOP with " + board + " " + numVeces + " games: ");

		for (int i = 0; i < numVeces; i++) {
			calcularVictoriasFlop();
		}
		porcentajesYClear(numVeces);

		// TURN
		aux = getCartaRandomBorrando(baraja);
		board.add(aux);

		System.out.println("TURN with " + board + " " + numVeces + " games: ");

		for (int i = 0; i < numVeces; i++) {
			calcularVictoriasTurn();
		}
		porcentajesYClear(numVeces);

		// RIVER
		aux = getCartaRandomBorrando(baraja);
		board.add(aux);

		System.out.println("RIVER with " + board);

		calcularVictoriasRiver();
		porcentajesYClear(1);
	}

	private void clearVictorias() {
		for (Jugador j : jugadores) {
			j.setVictorias(0);
		}
	}

	public void calcularVictoriasPreFlop() {// Cartas 6 jugadores separados comas
		if(jugadores.size() > 1) {
			for (int j = 0; j < NUMVECES; j++) {
				ArrayList<Carta> baraja = (ArrayList<Carta>) this.baraja.clone();
				ArrayList<Carta> board = new ArrayList<Carta>();
				ArrayList<Carta> cartasJugYBoard;
				
		
				for (int i = 0; i < 5; i++) {
					Carta carta = getCartaRandomBorrando(baraja);
					board.add(carta);
				}
		
				for (int i = 0; i < jugadores.size(); i++) {
					cartasJugYBoard = new ArrayList<Carta>(board);
					cartasJugYBoard.add(jugadores.get(i).getCarta(0));
					cartasJugYBoard.add(jugadores.get(i).getCarta(1));
					organizer.ordenarCartas(cartasJugYBoard);
					organizer.bestHand(cartasJugYBoard, jugadores.get(i));
					puntuarManoJugadores(jugadores.get(i));
				}
		
				jugadorGanador();
			}
			porcentajesYClear(NUMVECES);
		}
		else {
			jugadores.get(0).setVictorias(1);
			porcentajesYClear(1);
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ArrayList<Jugador> setCartasJugadorBorrando(String mano, ArrayList<Jugador> players) {
		String[] cartas = mano.split(",");
		ArrayList<Carta> cs = new ArrayList<Carta>(2);
		for (int i = 0; i < cartas.length; i++) {
			Carta c = new Carta(cartas[i].charAt(0), cartas[i].charAt(1));
			removeCartaBaraja(c);
			cs.add(c);
		}
		players.add(new Jugador(cs.get(0), cs.get(1)));
		return players;
	}
	
	public void calcularVictoriasPreFlop(ArrayList<Jugador> players, ArrayList<Carta> baraja) {// Cartas 6 jugadores separados comas
		if(players.size() > 1) {
			for (int j = 0; j < NUMVECES; j++) {
				ArrayList<Carta> board = new ArrayList<Carta>();
				ArrayList<Carta> cartasJugYBoard;
				
		
				for (int i = 0; i < 5; i++) {
					Carta carta = getCartaRandomBorrando(baraja);
					board.add(carta);
				}
		
				for (int i = 0; i < players.size(); i++) {
					cartasJugYBoard = new ArrayList<Carta>(board);
					cartasJugYBoard.add(players.get(i).getCarta(0));
					cartasJugYBoard.add(players.get(i).getCarta(1));
					organizer.ordenarCartas(cartasJugYBoard);
					organizer.bestHand(cartasJugYBoard, players.get(i));
					puntuarManoJugadores(players.get(i));
				}
		
				jugadorGanador();
			}
			porcentajesYClear(NUMVECES);
		}
		else {
			players.get(0).setVictorias(1);
			porcentajesYClear(1);
		}
	}
	
	public void calcularVictoriasFlop(ArrayList<Jugador> players, ArrayList<Carta> baraja) {// 3 d la mesa+jugadore
		if(players.size() > 1) {
			this.borrarBoard(0);
			this.borrarBoard(1);
			this.borrarBoard(2);
			for (int j = 0; j < NUMVECES; j++) {
				ArrayList<Carta> board = (ArrayList<Carta>) this.board.clone();
				ArrayList<Carta> cartasJugYBoard;
				
				board.remove(board.size()-1);
				board.remove(board.size()-2);
				
				for (int i = 0; i < 2; i++) {
					Carta carta = getCartaRandomBorrando(baraja);
					board.add(carta);
				}
		
				for (int i = 0; i < players.size(); i++) {
					cartasJugYBoard = new ArrayList<Carta>(board);
					cartasJugYBoard.add(players.get(i).getCarta(0));
					cartasJugYBoard.add(players.get(i).getCarta(1));
					organizer.ordenarCartas(cartasJugYBoard);
					organizer.bestHand(cartasJugYBoard, players.get(i));
					puntuarManoJugadores(players.get(i));
				}
				jugadorGanador();
			}
			porcentajesYClear(NUMVECES);
		}
		else {
			players.get(0).setVictorias(1);
			porcentajesYClear(1);
		}
	}
	
	public void calcularVictoriasTurn(ArrayList<Jugador> players, ArrayList<Carta> baraja) {// 4 d la mesa+jugadores
		if(players.size() > 1) {
			this.borrarBoard(4);
			for (int j = 0; j < NUMVECES; j++) {
				ArrayList<Carta> board = (ArrayList<Carta>) this.board.clone();
				ArrayList<Carta> cartasJugYBoard;
		
				board.remove(board.size()-1);
				
				Carta carta = getCartaRandomBorrando(baraja);
				board.add(carta);
		
				for (int i = 0; i < players.size(); i++) {
					cartasJugYBoard = new ArrayList<Carta>(board);
					cartasJugYBoard.add(players.get(i).getCarta(0));
					cartasJugYBoard.add(players.get(i).getCarta(1));
					organizer.ordenarCartas(cartasJugYBoard);
					organizer.bestHand(cartasJugYBoard, players.get(i));
					puntuarManoJugadores(players.get(i));
				}
		
				jugadorGanador();
			}
			porcentajesYClear(NUMVECES);
		}
		else {
			players.get(0).setVictorias(1);
			porcentajesYClear(1);
		}
	}
	
	public void calcularVictoriasRiver(ArrayList<Jugador> players, ArrayList<Carta> baraja) {// 5 d la mesa+jugadores 100%/0%
		if(players.size() > 1) {
			this.borrarBoard(4);
			ArrayList<Carta> cartasJugYBoard;
	
			for (int i = 0; i < players.size(); i++) {
				cartasJugYBoard = new ArrayList<Carta>(board);
				cartasJugYBoard.add(players.get(i).getCarta(0));
				cartasJugYBoard.add(players.get(i).getCarta(1));
				organizer.ordenarCartas(cartasJugYBoard);
				organizer.bestHand(cartasJugYBoard, players.get(i));
				puntuarManoJugadores(players.get(i));
			}
	
			jugadorGanador();
			porcentajesYClear(1);
		}
		else {
			players.get(0).setVictorias(1);
			porcentajesYClear(1);
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void calcularVictoriasFlop() {// 3 d la mesa+jugadore
		if(jugadores.size() > 1) {
			this.borrarBoard(0);
			this.borrarBoard(1);
			this.borrarBoard(2);
			for (int j = 0; j < NUMVECES; j++) {
				ArrayList<Carta> baraja = (ArrayList<Carta>) this.baraja.clone();
				ArrayList<Carta> board = (ArrayList<Carta>) this.board.clone();
				ArrayList<Carta> cartasJugYBoard;
				
				board.remove(board.size()-1);
				board.remove(board.size()-2);
				
				for (int i = 0; i < 2; i++) {
					Carta carta = getCartaRandomBorrando(baraja);
					board.add(carta);
				}
		
				for (int i = 0; i < jugadores.size(); i++) {
					cartasJugYBoard = new ArrayList<Carta>(board);
					cartasJugYBoard.add(jugadores.get(i).getCarta(0));
					cartasJugYBoard.add(jugadores.get(i).getCarta(1));
					organizer.ordenarCartas(cartasJugYBoard);
					organizer.bestHand(cartasJugYBoard, jugadores.get(i));
					puntuarManoJugadores(jugadores.get(i));
				}
		
				jugadorGanador();
			}
			porcentajesYClear(NUMVECES);
		}
		else {
			jugadores.get(0).setVictorias(1);
			porcentajesYClear(1);
		}
	}

	public void calcularVictoriasTurn() {// 4 d la mesa+jugadores
		if(jugadores.size() > 1) {
			this.borrarBoard(4);
			for (int j = 0; j < NUMVECES; j++) {
				ArrayList<Carta> baraja = (ArrayList<Carta>) this.baraja.clone();
				ArrayList<Carta> board = (ArrayList<Carta>) this.board.clone();
				ArrayList<Carta> cartasJugYBoard;
		
				board.remove(board.size()-1);
				
				Carta carta = getCartaRandomBorrando(baraja);
				board.add(carta);
		
				for (int i = 0; i < jugadores.size(); i++) {
					cartasJugYBoard = new ArrayList<Carta>(board);
					cartasJugYBoard.add(jugadores.get(i).getCarta(0));
					cartasJugYBoard.add(jugadores.get(i).getCarta(1));
					organizer.ordenarCartas(cartasJugYBoard);
					organizer.bestHand(cartasJugYBoard, jugadores.get(i));
					puntuarManoJugadores(jugadores.get(i));
				}
		
				jugadorGanador();
			}
			porcentajesYClear(NUMVECES);
		}
		else {
			jugadores.get(0).setVictorias(1);
			porcentajesYClear(1);
		}
	}

	public void calcularVictoriasRiver() {// 5 d la mesa+jugadores 100%/0%
		if(jugadores.size() > 1) {
			this.borrarBoard(4);
			ArrayList<Carta> baraja = (ArrayList<Carta>) this.baraja.clone();
			ArrayList<Carta> cartasJugYBoard;
	
			for (int i = 0; i < jugadores.size(); i++) {
				cartasJugYBoard = new ArrayList<Carta>(board);
				cartasJugYBoard.add(jugadores.get(i).getCarta(0));
				cartasJugYBoard.add(jugadores.get(i).getCarta(1));
				organizer.ordenarCartas(cartasJugYBoard);
				organizer.bestHand(cartasJugYBoard, jugadores.get(i));
				puntuarManoJugadores(jugadores.get(i));
			}
	
			jugadorGanador();
			porcentajesYClear(1);
		}
		else {
			jugadores.get(0).setVictorias(1);
			porcentajesYClear(1);
		}
	}

	public void porcentajesJugador(int numVeces) {
		double porcentajeJugs[] = new double[jugadores.size()];
		double total = 0;
		for (int i = 0; i < jugadores.size(); i++) {
			double aux = jugadores.get(i).getVictorias() / numVeces * 100;
			porcentajeJugs[i] = Math.round(aux * 100.0) / 100.0;
			total += porcentajeJugs[i];
			System.out.println("J" + (i + 1) + ": " + jugadores.get(i) + " winrate = " + porcentajeJugs[i] + "%");
			jugadores.get(i).setEquity(porcentajeJugs[i]);
		}
		// System.out.println(total + "%");
		// pasar gui
		// Borrar victorias
	}

	public void porcentajesYClear(int numVeces) {
		porcentajesJugador(numVeces);
		clearVictorias();
	}

	private void jugadorGanador() {
		
		JugadorComparator jComp = new JugadorComparator();
		ArrayList<Jugador> jugadoresAux = new ArrayList<Jugador>(jugadores);
		Collections.sort(jugadoresAux, new JugadorComparator());

		int cont = 1;
		int num = jComp.compare(jugadoresAux.get(0), jugadoresAux.get(1));
		if (num == 0) {
			int i = 1;
			int contEmpates = 2;
			while (num == 0 && i < jugadoresAux.size() - 1) {
				num = jComp.compare(jugadoresAux.get(i), jugadoresAux.get(i + 1));
				i++;
				contEmpates++;
			}
			cont = contEmpates;
			// en caso de empate (num == 0) no se suman victorias
		}

		if (cont == 1) {
			for (int i = 0; i < jugadores.size() && cont > 0; i++) {
				if (jugadoresAux.get(0) == jugadores.get(i)) {
					jugadores.get(i).setVictorias(jugadores.get(i).getVictorias() + 1);
				}
			}
		} else {
			double puntos = (double) 1 / cont;
			while (cont > 0) {
				for (int i = 0; i < jugadores.size(); i++) {
					if (jugadoresAux.get(cont - 1) == jugadores.get(i)) {
						jugadores.get(i).setVictorias(jugadores.get(i).getVictorias() + puntos);
					}
				}
				cont--;
			}
		}

	}

	public static void puntuarManoJugadores(Jugador jugador) {
		if (jugador.mejorManostr == "Straight Flush") {
			jugador.puntosMano = 900;
		} else if (jugador.mejorManostr == "Poker") {
			jugador.puntosMano = 800;
		} else if (jugador.mejorManostr == "Full House") {
			jugador.puntosMano = 700;
		} else if (jugador.mejorManostr == "Flush") {
			jugador.puntosMano = 600;
		} else if (jugador.mejorManostr == "Straight") {
			jugador.puntosMano = 500;
		} else if (jugador.mejorManostr == "Set") {
			jugador.puntosMano = 400;
		} else if (jugador.mejorManostr == "Double Pair") {
			jugador.puntosMano = 300;
		} else if (jugador.mejorManostr == "Pair") {
			jugador.puntosMano = 200;
		} else if (jugador.mejorManostr == "High Card") {
			jugador.puntosMano = 100;
		}
	}

	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	public ArrayList<Carta> getBaraja() {
		return baraja;
	}

	public void setBaraja(ArrayList<Carta> baraja) {
		this.baraja = baraja;
	}
	
	public ArrayList<Carta> getBoard() {
		return board;
	}

	public void setBoard(ArrayList<Carta> board) {
		this.board = board;
	}
	
	public void removePlayer(int i) {
		this.jugadores.remove(i);
		MainWindow.removePlayer(i);
	}
	
	public int  getNJugadores() {
		return this.jugadores.size();
	}
}
