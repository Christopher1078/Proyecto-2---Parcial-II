package proyecto.sokoban.juego;

public class Caja {

    private int fila;
    private int columna;

    public Caja(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public void mover(int cambioFila, int cambioColumna) {
        fila += cambioFila;
        columna += cambioColumna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}