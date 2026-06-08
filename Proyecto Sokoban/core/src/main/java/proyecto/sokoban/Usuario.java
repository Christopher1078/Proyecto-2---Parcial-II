package proyecto.sokoban;
import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDate;
public class Usuario implements Serializable {
    private String username, password, nombreCompleto, avatar, arriba, abajo, izquierda, derecha;
    private Idioma idioma;
    private LocalDate fechaRegistro, ultimaSesion;
    private int nivelesCompletados, nivelActual, cantPartidas, partidasGanadas, movimientosTotales, ranking, puntos, volumen;
    private boolean[] nivelesDesbloqueados;
    private long tiempoJugado;
    private double tiempoPromedio;
    private ArrayList<String> amigos;   
    private ArrayList<HistorialPartida> historial;

    public Usuario(String username, String password, String nombreCompleto, String avatar) {
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
        
        cantPartidas=0;
        partidasGanadas=0;
        movimientosTotales=0;
        tiempoJugado=0;
        puntos=0;
        ranking=0;
        
        this.avatar=avatar;
        idioma=Idioma.ESPANOL;
        
        volumen=100;
        arriba="W";
        abajo="S";
        izquierda="A";
        derecha="D";
        
        amigos=new ArrayList<>();
        historial=new ArrayList<>();
    }
    
    
}
