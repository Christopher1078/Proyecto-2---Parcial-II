package proyecto.sokoban;
import java.io.Serializable;
import java.time.LocalDate;
public class SolicitudDuelo implements Serializable {

    public enum Estado {
        PENDIENTE,   
        ACEPTADO, 
        COMPLETADO  
    }
 
    private static final long serialVersionUID = 1L;
 
    private final String retador;       
    private final String retado;        
    private final int nivel;            
    private final LocalDate fecha;
    private long tiempoRetador;
    private long tiempoRetado;
    private Estado estado;
 
    public SolicitudDuelo(String retador, String retado, int nivel) {
        this.retador     = retador;
        this.retado      = retado;
        this.nivel       = nivel;
        this.fecha       = LocalDate.now();
        this.tiempoRetador = -1;
        this.tiempoRetado  = -1;
        this.estado      = Estado.PENDIENTE;
    }
 
    public String getRetador(){ 
        return retador; 
    }
    
    public String getRetado(){ 
        return retado;  
    }
    
    public int getNivel(){
        return nivel;  
    }
    
    public LocalDate getFecha(){
        return fecha;  
    }
    
    public long getTiempoRetador(){
        return tiempoRetador; 
    }
    
    public long getTiempoRetado(){
        return tiempoRetado;  
    }
    
    public Estado getEstado(){
        return estado;  
    }
  
    public void registrarTiempoRetador(long segundos) {
        this.tiempoRetador = segundos;
        actualizarEstado();
    }
 
    public void registrarTiempoRetado(long segundos) {
        this.tiempoRetado  = segundos;
        this.estado        = Estado.ACEPTADO;
        actualizarEstado();
    }
 
    private void actualizarEstado() {
        if (tiempoRetador >= 0 && tiempoRetado >= 0) {
            estado = Estado.COMPLETADO;
        }
    }
 
    public String getGanador() {
        if (estado != Estado.COMPLETADO) return null;
        return tiempoRetador <= tiempoRetado ? retador : retado;
    }
 
    public static String formatearTiempo(long segundos) {
        if (segundos < 0) return "Pendiente";
        long m = segundos / 60;
        long s = segundos % 60;
        return String.format("%02d:%02d", m, s);
    }
 
    public String getRival(String yo) {
        return yo.equals(retador) ? retado : retador;
    }

    public boolean yaJugo(String yo) {
        if (yo.equals(retador)) return tiempoRetador >= 0;
        return tiempoRetado >= 0;
    }
 
    public boolean eresElRetado(String yo) {
        return yo.equals(retado);
    }    
}
