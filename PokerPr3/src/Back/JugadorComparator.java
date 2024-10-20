package Back;

import java.util.Comparator;

public class JugadorComparator implements Comparator<Jugador> {
	@Override
	public int compare(Jugador o1, Jugador o2) {
		
		if(o1.puntosMano < o2.puntosMano) {
			return 1;
		}else if(o1.puntosMano > o2.puntosMano) {
			return -1;
		}else {
		   return empate(o1,o2);
		}
		
	}

	private int empate(Jugador j1, Jugador j2) {
		int i =0;
		boolean win = false;
		while(i<5 && !win) {
			if(j1.getCartas().get(i).numero < j2.getCartas().get(i).numero ) {
				return 1;
			}
			else if(j1.getCartas().get(i).numero > j2.getCartas().get(i).numero ) {
				return -1;
			}
			i++;
		}
		
		return 0;
	}

	

}
