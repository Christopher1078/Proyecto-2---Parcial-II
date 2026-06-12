package proyecto.sokoban.juego;

public class Objetivo {

    private int fila;
    private int columna;

    public Objetivo(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}