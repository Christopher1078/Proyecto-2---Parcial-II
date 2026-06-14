package proyecto.sokoban;
import java.io.Serializable;
import java.time.LocalDate;
public class HistorialPartida implements Serializable {
    private LocalDate fecha;
    private int nivel, movimientos;
    private long tiempo;
    private boolean victoria;
    private static final long serialVersionUID=1L;

    public HistorialPartida(int nivel, int movimientos, long tiempo, boolean victoria) {
        this.nivel = nivel;
        this.movimientos = movimientos;
        this.tiempo = tiempo;
        this.victoria = victoria;
        this.fecha=LocalDate.now();
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
    
    
    
}
