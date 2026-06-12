package proyecto.sokoban.juego;

public abstract class Juego {

    protected int nivelActual;
    protected boolean juegoTerminado;

    public Juego() {
        nivelActual = 0;
        juegoTerminado = false;
    }

    public abstract void cargarNivel(int numeroNivel);

    public abstract boolean moverJugador(int cambioFila, int cambioColumna);

    public abstract void reiniciarNivel();

    public abstract boolean avanzarNivel();

    public int getNivelActual() {
        return nivelActual;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }
}