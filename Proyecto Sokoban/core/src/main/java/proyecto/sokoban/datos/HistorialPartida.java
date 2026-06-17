package proyecto.sokoban.datos;

import java.io.Serializable;
import java.time.LocalDate;

public class HistorialPartida implements Serializable {

    private LocalDate fecha;
    private int nivel;
    private int movimientos;
    private long tiempo;
    private boolean victoria;

    private static final long serialVersionUID = 1L;

    public HistorialPartida(int nivel, int movimientos, long tiempo, boolean victoria) {
        this.fecha = LocalDate.now();
        this.nivel = nivel;
        this.movimientos = movimientos;
        this.tiempo = tiempo;
        this.victoria = victoria;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getNivel() {
        return nivel;
    }

    public int getMovimientos() {
        return movimientos;
    }

    public long getTiempo() {
        return tiempo;
    }

    public boolean isVictoria() {
        return victoria;
    }

    public String getTiempoFormateado() {
        long minutos = tiempo / 60;
        long segundos = tiempo % 60;

        return String.format("%02d:%02d", minutos, segundos);
    }

    @Override
    public String toString() {
        String resultado = "Intento";

        if (victoria) {
            resultado = "Victoria";
        }

        return fecha
            + " | Nivel " + nivel
            + " | " + resultado
            + " | Movimientos: " + movimientos
            + " | Tiempo: " + getTiempoFormateado();
    }
}
