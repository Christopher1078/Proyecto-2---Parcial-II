package proyecto.sokoban.juego;

import java.util.ArrayList;

public class Sokoban extends Juego {

    private ArrayList<Nivel> niveles;
    private Mapa mapaActual;
    private HiloTiempo hiloTiempo;
    private int movimientosNivel;
    private int movimientosTotales;
    private int fallosNivel;
    private int fallosTotales;
    private int reinicios;
    private boolean nivelCompletado;
    private String mensaje;

    public Sokoban() {
        this(1);
    }

    public Sokoban(int nivelInicial) {
        niveles = Nivel.crearNiveles();
        hiloTiempo = new HiloTiempo();
        hiloTiempo.start();
        cargarNivel(nivelInicial - 1);
    }

    @Override
    public void cargarNivel(int numeroNivel) {
        if (numeroNivel < 0 || numeroNivel >= niveles.size()) {
            throw new IllegalArgumentException("El nivel solicitado no existe.");
        }

        nivelActual = numeroNivel;
        mapaActual = new Mapa(niveles.get(numeroNivel).getDiseno());
        movimientosNivel = 0;
        fallosNivel = 0;
        nivelCompletado = false;
        juegoTerminado = false;
        mensaje = "Ubica todas las cajas en los objetivos.";

        hiloTiempo.reiniciarTiempoDelNivel();
    }

    @Override
    public boolean moverJugador(int cambioFila, int cambioColumna) {
        if (juegoTerminado || nivelCompletado) {
            return false;
        }

        boolean seMovio = mapaActual.moverJugador(cambioFila, cambioColumna);

        if (seMovio) {
            movimientosNivel++;
            movimientosTotales++;
            mensaje = "Movimiento realizado.";

            if (mapaActual.estaCompletado()) {
                nivelCompletado = true;
                hiloTiempo.pausarNivel();
                mensaje = "Nivel completado. Presiona ENTER para continuar.";
            }
        } else {
            registrarFallo();
        }

        return seMovio;
    }

    private void registrarFallo() {
        fallosNivel++;
        fallosTotales++;
        mensaje = "Movimiento bloqueado.";
    }

    @Override
    public void reiniciarNivel() {
        reinicios++;
        cargarNivel(nivelActual);
        mensaje = "Nivel reiniciado.";
    }

    @Override
    public boolean avanzarNivel() {
        if (!nivelCompletado) {
            return false;
        }

        boolean eraElUltimoNivel = nivelActual == niveles.size() - 1;

        if (eraElUltimoNivel) {
            juegoTerminado = true;
            hiloTiempo.pausarNivel();
            mensaje = "Felicidades. Completaste todos los niveles.";
            return false;
        }

        cargarNivel(nivelActual + 1);
        return true;
    }

    public void detener() {
        if (hiloTiempo != null) {
            hiloTiempo.detener();
        }
    }

    public Mapa getMapaActual() {
        return mapaActual;
    }

    public int getMovimientosNivel() {
        return movimientosNivel;
    }

    public int getMovimientosTotales() {
        return movimientosTotales;
    }

    public int getFallosNivel() {
        return fallosNivel;
    }

    public int getFallosTotales() {
        return fallosTotales;
    }

    public int getVidas() {
        return 0;
    }

    public int getReinicios() {
        return reinicios;
    }

    public int getCantidadNiveles() {
        return niveles.size();
    }

    public boolean isNivelCompletado() {
        return nivelCompletado;
    }

    public String getNombreNivelActual() {
        return niveles.get(nivelActual).getNombre();
    }

    public int getSegundosNivel() {
        return hiloTiempo.getSegundosNivel();
    }

    public String getTiempoNivelFormateado() {
        return hiloTiempo.getTiempoNivelFormateado();
    }

    public String getTiempoPartidaFormateado() {
        return hiloTiempo.getTiempoPartidaFormateado();
    }

    public String getMensaje() {
        return mensaje;
    }
}