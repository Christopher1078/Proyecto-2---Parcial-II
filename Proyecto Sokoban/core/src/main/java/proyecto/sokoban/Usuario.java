package proyecto.sokoban;
import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDate;
public class Usuario implements Serializable {
    private String username, password, nombreCompleto, avatar;
    private Idioma idioma;
    private LocalDate fechaRegistro, ultimaSesion;
    private int nivelesCompletados, nivelActual, partidasJugadas;
    private boolean[] nivelesDesbloqueados;
    private long tiempoJugado;
    private double tiempoPromedio;
    private ArrayList<Usuario> amigos;   
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
        
        this.avatar="Avatar";
        idioma=Idioma.ESPANOL;
        
        amigos=new ArrayList<>();
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
    
    
    
    @Override
    public String toString(){
        return "Username: "+username
                +"\nNombre Completo: "+nombreCompleto
                +"\nFecha de registro: "+fechaRegistro.toString()
                +"\nNivel actual: "+nivelActual
                +"\nPartidas jugadas: "+partidasJugadas;
    }
    
}
