package proyecto.sokoban.datos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String nombreCompleto;

    private Idioma idioma;
    private GeneroAvatar genero;

    private LocalDate fechaRegistro;
    private LocalDate ultimaSesion;

    private int nivelesCompletados;
    private int nivelActual;
    private int partidasJugadas;

    private boolean[] nivelesDesbloqueados;

    private long tiempoJugado;
    private double tiempoPromedio;

    private boolean cuentaDeshabilitada;

    private ArrayList<String> amigos;
    private ArrayList<String> solicitudesRecibidas;
    private ArrayList<String> solicitudesEnviadas;
    private ArrayList<HistorialPartida> historial;
    private ArrayList<SolicitudDuelo> duelos;

    public Usuario(String username, String password, String nombreCompleto) {
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombreCompleto;

        fechaRegistro = LocalDate.now();
        ultimaSesion = LocalDate.now();

        idioma = Idioma.ESPANOL;
        genero = GeneroAvatar.MASCULINO;

        nivelesCompletados = 0;
        nivelActual = 1;
        partidasJugadas = 0;

        nivelesDesbloqueados = new boolean[5];
        nivelesDesbloqueados[0] = true;

        tiempoJugado = 0;
        tiempoPromedio = 0;

        cuentaDeshabilitada = false;

        amigos = new ArrayList<>();
        solicitudesRecibidas = new ArrayList<>();
        solicitudesEnviadas = new ArrayList<>();
        historial = new ArrayList<>();
        duelos = new ArrayList<>();
    }

    private void revisarDatos() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDate.now();
        }

        if (ultimaSesion == null) {
            ultimaSesion = LocalDate.now();
        }

        if (idioma == null) {
            idioma = Idioma.ESPANOL;
        }

        if (genero == null) {
            genero = GeneroAvatar.MASCULINO;
        }

        if (nivelesDesbloqueados == null || nivelesDesbloqueados.length < 5) {
            boolean[] nuevosNiveles = new boolean[5];
            nuevosNiveles[0] = true;

            if (nivelesDesbloqueados != null) {
                for (int i = 0; i < nivelesDesbloqueados.length && i < nuevosNiveles.length; i++) {
                    nuevosNiveles[i] = nivelesDesbloqueados[i];
                }
            }

            nivelesDesbloqueados = nuevosNiveles;
        }

        if (nivelActual < 1) {
            nivelActual = 1;
        }

        if (amigos == null) {
            amigos = new ArrayList<>();
        }

        if (solicitudesRecibidas == null) {
            solicitudesRecibidas = new ArrayList<>();
        }

        if (solicitudesEnviadas == null) {
            solicitudesEnviadas = new ArrayList<>();
        }

        if (historial == null) {
            historial = new ArrayList<>();
        }

        if (duelos == null) {
            duelos = new ArrayList<>();
        }
    }

    public void iniciarSesion() {
        revisarDatos();
        ultimaSesion = LocalDate.now();
    }

    public boolean nivelDesbloqueado(int nivel) {
        revisarDatos();

        if (nivel < 1 || nivel > nivelesDesbloqueados.length) {
            return false;
        }

        return nivelesDesbloqueados[nivel - 1];
    }

    public void completarNivel(int nivel) {
        revisarDatos();

        if (nivel > nivelesCompletados) {
            nivelesCompletados = nivel;
        }

        int siguienteNivel = nivel + 1;

        if (siguienteNivel <= nivelesDesbloqueados.length) {
            nivelesDesbloqueados[siguienteNivel - 1] = true;
            nivelActual = siguienteNivel;
        } else {
            nivelActual = nivel;
        }
    }

    public void registrarPartida(int nivel, int movimientos, long tiempo, boolean victoria) {
        revisarDatos();

        partidasJugadas++;
        tiempoJugado += tiempo;

        if (partidasJugadas > 0) {
            tiempoPromedio = (double) tiempoJugado / partidasJugadas;
        }

        historial.add(new HistorialPartida(nivel, movimientos, tiempo, victoria));

        if (victoria) {
            completarNivel(nivel);
        }
    }

    public boolean isCuentaDeshabilitada() {
        revisarDatos();
        return cuentaDeshabilitada;
    }

    public void deshabilitar() {
        cuentaDeshabilitada = true;
    }

    public void habilitar() {
        cuentaDeshabilitada = false;
    }

    public void agregarDuelo(SolicitudDuelo duelo) {
        revisarDatos();

        if (duelo == null) {
            return;
        }

        SolicitudDuelo existente = buscarDuelo(
            duelo.getRetador(),
            duelo.getRetado(),
            duelo.getNivel()
        );

        if (existente == null) {
            duelos.add(duelo);
        }
    }

    public SolicitudDuelo buscarDuelo(String retador, String retado, int nivel) {
        revisarDatos();

        for (SolicitudDuelo duelo : duelos) {
            if (duelo.getRetador().equals(retador)
                && duelo.getRetado().equals(retado)
                && duelo.getNivel() == nivel) {

                return duelo;
            }
        }

        return null;
    }

    public ArrayList<SolicitudDuelo> getDuelosPendientesAceptar(String miNombre) {
        revisarDatos();

        ArrayList<SolicitudDuelo> resultado = new ArrayList<>();

        for (SolicitudDuelo duelo : duelos) {
            if (duelo.getRetado().equals(miNombre)) {
                resultado.add(duelo);
            }
        }

        return resultado;
    }

    public ArrayList<SolicitudDuelo> getDuelosPorJugar(String miNombre) {
        revisarDatos();

        ArrayList<SolicitudDuelo> resultado = new ArrayList<>();

        for (SolicitudDuelo duelo : duelos) {
            if (duelo.getRetador().equals(miNombre)
                || duelo.getRetado().equals(miNombre)) {

                resultado.add(duelo);
            }
        }

        return resultado;
    }

    public ArrayList<SolicitudDuelo> getDuelosCompetados(String miNombre) {
        revisarDatos();

        ArrayList<SolicitudDuelo> resultado = new ArrayList<>();

        for (SolicitudDuelo duelo : duelos) {
            if (duelo.getRetador().equals(miNombre)
                || duelo.getRetado().equals(miNombre)) {

                resultado.add(duelo);
            }
        }

        return resultado;
    }

    public ArrayList<SolicitudDuelo> getDuelosCompletados(String miNombre) {
        return getDuelosCompetados(miNombre);
    }

    public String getUsername() {
        revisarDatos();
        return username;
    }

    public String getPassword() {
        revisarDatos();
        return password;
    }

    public String getNombreCompleto() {
        revisarDatos();
        return nombreCompleto;
    }

    public Idioma getIdioma() {
        revisarDatos();
        return idioma;
    }

    public GeneroAvatar getGenero() {
        revisarDatos();
        return genero;
    }

    public LocalDate getFechaRegistro() {
        revisarDatos();
        return fechaRegistro;
    }

    public LocalDate getUltimaSesion() {
        revisarDatos();
        return ultimaSesion;
    }

    public int getNivelesCompletados() {
        revisarDatos();
        return nivelesCompletados;
    }

    public int getNivelActual() {
        revisarDatos();
        return nivelActual;
    }

    public int getPartidasJugadas() {
        revisarDatos();
        return partidasJugadas;
    }

    public long getTiempoJugado() {
        revisarDatos();
        return tiempoJugado;
    }

    public double getTiempoPromedio() {
        revisarDatos();
        return tiempoPromedio;
    }

    public ArrayList<String> getAmigos() {
        revisarDatos();
        return amigos;
    }

    public ArrayList<String> getSolicitudesRecibidas() {
        revisarDatos();
        return solicitudesRecibidas;
    }

    public ArrayList<String> getSolicitudesEnviadas() {
        revisarDatos();
        return solicitudesEnviadas;
    }

    public ArrayList<HistorialPartida> getHistorial() {
        revisarDatos();
        return historial;
    }

    public ArrayList<SolicitudDuelo> getDuelos() {
        revisarDatos();
        return duelos;
    }

    public String getMejorTiempo() {
        revisarDatos();
 
        long mejor = Long.MAX_VALUE;
 
        for (HistorialPartida partida : historial) {
            if (partida.isVictoria()&& partida.getNivel() == nivelesCompletados&& partida.getTiempo() < mejor) {
                mejor = partida.getTiempo();
            }
        }
 
        if (mejor == Long.MAX_VALUE) {
            return "N/A";
        }
 
        long minutos = mejor / 60;
        long segundos = mejor % 60;
 
        return minutos + "m " + segundos + "s";
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

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public void setGenero(GeneroAvatar genero) {
        this.genero = genero;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }

    public void setNivelesCompletados(int nivelesCompletados) {
        this.nivelesCompletados = nivelesCompletados;
    }

    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    public void setTiempoJugado(long tiempoJugado) {
        this.tiempoJugado = tiempoJugado;
    }

    public void setCuentaDeshabilitada(boolean cuentaDeshabilitada) {
        this.cuentaDeshabilitada = cuentaDeshabilitada;
    }

    public boolean isAmigo(String username) {
        revisarDatos();
        return amigos.contains(username);
    }

    public boolean tieneSolicitudesRecibidasDe(String username) {
        revisarDatos();
        return solicitudesRecibidas.contains(username);
    }

    public boolean tieneSolicitudesEnviadasA(String username) {
        revisarDatos();
        return solicitudesEnviadas.contains(username);
    }

    public void agregarAmigo(String username) {
        revisarDatos();

        if (!amigos.contains(username)) {
            amigos.add(username);
        }
    }

    public void eliminarAmigo(String username) {
        revisarDatos();
        amigos.remove(username);
    }

    public void agregarSolicitudRecibida(String username) {
        revisarDatos();

        if (!solicitudesRecibidas.contains(username)) {
            solicitudesRecibidas.add(username);
        }
    }

    public void eliminarSolicitudRecibida(String username) {
        revisarDatos();
        solicitudesRecibidas.remove(username);
    }

    public void agregarSolicitudEnviada(String username) {
        revisarDatos();

        if (!solicitudesEnviadas.contains(username)) {
            solicitudesEnviadas.add(username);
        }
    }

    public void eliminarSolicitudEnviada(String username) {
        revisarDatos();
        solicitudesEnviadas.remove(username);
    }
    
    public String getTiempoPromedioPorNivel(int nivel){
        revisarDatos();
        
        long suma=0;
        int count=0;
        
        for(HistorialPartida p: historial){
            if(p.isVictoria() && p.getNivel()==nivel){
                suma += p.getTiempo();
                count++;
            }
        }
        
        if(count==0)
            return "N/A";
        
        long promedio=suma/count;
        long minutos=promedio/60;
        long segundos=promedio%60;
        
        return String.format("%02d:%02d", minutos, segundos);
    }
    
    public String getTiempoPromedioGlobal(){
        revisarDatos();
        
        long suma=0;
        int count=0;
        
        for(HistorialPartida p: historial){
            if(p.isVictoria()){
                suma+=p.getTiempo();
                count++;
            }
        }
        
        if(count==0)
            return "N/A";
        
        long promedio=suma/count;
        long minutos=promedio/60;
        long segundos=promedio%60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    @Override
    public String toString() {
        revisarDatos();

        return "Username: " + username
            + "\nNombre Completo: " + nombreCompleto
            + "\nFecha de registro: " + fechaRegistro
            + "\nUltima sesion: " + ultimaSesion
            + "\nNivel actual: " + nivelActual
            + "\nNiveles completados: " + nivelesCompletados
            + "\nPartidas jugadas: " + partidasJugadas;
    }
}
