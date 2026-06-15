package proyecto.sokoban;
import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDate;
public class Usuario implements Serializable {
    private String username, password, nombreCompleto;
    private Idioma idioma;
    private GeneroAvatar genero;
    private LocalDate fechaRegistro, ultimaSesion;
    private int nivelesCompletados, nivelActual, partidasJugadas;
    private boolean[] nivelesDesbloqueados;
    private long tiempoJugado;
    private double tiempoPromedio;
    private ArrayList<String> amigos;
    private ArrayList<String> solicitudesRecibidas;
    private ArrayList<String> solicitudesEnviadas;
    private ArrayList<HistorialPartida> historial;
    private static final long serialVersionUID=1L; 

    public Usuario(String username, String password, String nombreCompleto) {
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        
        fechaRegistro=LocalDate.now();
        ultimaSesion=LocalDate.now();
        
        nivelActual=1;
        nivelesCompletados=0;
        
        nivelesDesbloqueados=new boolean[5];
        nivelesDesbloqueados[0]=true;
        for(int i=1;i<5;i++){
            nivelesDesbloqueados[i]=false;
        }
        
        partidasJugadas=0;
        tiempoJugado=0;
        this.genero=GeneroAvatar.MASCULINO;
        idioma=Idioma.ESPANOL;
        
        amigos=new ArrayList<>();
        solicitudesRecibidas=new ArrayList<>();
        solicitudesEnviadas=new ArrayList<>();
        historial=new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }

    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    public GeneroAvatar getGenero() {
        return genero;
    }

    public void setGenero(GeneroAvatar genero) {
        this.genero = genero;
    }

    public long getTiempoJugado() {
        return tiempoJugado;
    }
    
    public void setTiempoJugado(long tiempoJugado) {
        this.tiempoJugado=tiempoJugado; 
    }

    public int getNivelesCompletados() {
        return nivelesCompletados;
    }
    
    public String getMejorTiempo(){
        long mejor=Long.MAX_VALUE;
        for(HistorialPartida h: historial){
            if(h.isVictoria() && h.getTiempo()<mejor)
                mejor=h.getTiempo();
        }
        if(mejor==Long.MAX_VALUE) return "N/A";
        long m=mejor/60;
        long s=mejor%60;
        return m+"m "+s+"s";
    }

    public ArrayList<String> getAmigos() {
        return amigos;
    }

    public ArrayList<String> getSolicitudesRecibidas() {
        return solicitudesRecibidas;
    }

    public ArrayList<String> getSolicitudesEnviadas() {
        return solicitudesEnviadas;
    }
    
    public boolean isAmigo(String username){
        return amigos.contains(username);
    }
    
    public boolean tieneSolicitudesRecibidasDe(String username){
        return solicitudesRecibidas.contains(username);
    }
    
    public boolean tieneSolicitudesEnviadasA(String username){
        return solicitudesEnviadas.contains(username);
    }
    
    public void agregarAmigo(String username){
        if(!amigos.contains(username))
            amigos.add(username);
    }
    
    public void eliminarAmigo(String username){
        amigos.remove(username);
    }
    
    public void agregarSolicitudRecibida(String username){
        if(!solicitudesRecibidas.contains(username))
            solicitudesRecibidas.add(username);
    }
    
    public void eliminarSolicitudRecibida(String username){
        solicitudesRecibidas.remove(username);
    }
    
    public void agregarSolicitudEnviada(String username){
        if(!solicitudesEnviadas.contains(username))
            solicitudesEnviadas.add(username);
    }
    
    public void eliminarSolicitudEnviada(String username){
        solicitudesEnviadas.remove(username);
    }
    
    @Override
    public String toString(){
        return "Username: "+username
                +"\nNombre Completo: "+nombreCompleto
                +"\nFecha de registro: "+fechaRegistro.toString()
                +"\nNivel actual: "+nivelActual
                +"\nPartidas jugadas: "+partidasJugadas;
    }
    
}
