package Back;



import java.util.ArrayList;


public class Jugador {

    private Carta carta1;
    private Carta carta2;
    private int peso;
    private double victorias;
    private int kicker;
    private ArrayList<Carta> mejorMano;
    int puntosMano;
    public String mejorManostr;
    private double equity;
    
    public Carta getCarta(int i) {
        Carta carta;

        if (i == 1) {
            carta = carta1;
        } else {
            carta = carta2;
        }

        return carta;
    }

    public Jugador(Carta carta1, Carta carta2,int puntosMano, String mejorManostr) {
        this.carta1 = carta1;
        this.carta2 = carta2;
        this.victorias = 0;
        this.mejorManostr = mejorManostr;
        this.puntosMano =puntosMano;

        if (carta1.getNumero() > carta2.getNumero()) {
            this.kicker = carta1.getNumero();
        } else {
            this.kicker = carta2.getNumero();
        }
    }
    public Jugador(Carta carta1, Carta carta2) {
        this.carta1 = carta1;
        this.carta2 = carta2;
        this.victorias = 0;
        this.mejorManostr = mejorManostr;
        this.puntosMano =puntosMano;

        if (carta1.getNumero() > carta2.getNumero()) {
            this.kicker = carta1.getNumero();
        } else {
            this.kicker = carta2.getNumero();
        }
    }


    public void setPeso(int peso) {
        this.peso = peso;

    }

    public void setCartas(ArrayList<Carta> listaCarta) {
        this.mejorMano = listaCarta;

    }

    public int getPeso() {
        return this.peso;
    }
    public int getPuntosMano() {
		return puntosMano;
}

    public ArrayList<Carta> getCartas() {
        return this.mejorMano;
    }
    public void setMejorMano(ArrayList<Carta> cartas) {
         this.mejorMano = cartas;
    }
    public double getVictorias() {
        return this.victorias;
    }

    public void setVictorias(double d) {
        this.victorias = d;
    }

    public void setKicker(int kiker) {
        this.kicker = kiker;
    }

    public int getKicker() {
        return kicker;
    }
    
    public String toString() {
    	return carta1 + "" + carta2;
    }
    
    public void setEquity(double n) {
        this.equity = n;
    }

    public double getEquity() {
        return this.equity;
    }
    
}