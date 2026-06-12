package proyecto.sokoban.juego;

public class HiloTiempo extends Thread {

    private int segundosNivel;
    private int segundosPartida;
    private boolean activo;
    private boolean nivelPausado;

    public HiloTiempo() {
        segundosNivel = 0;
        segundosPartida = 0;
        activo = true;
        nivelPausado = false;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (estaActivo()) {
            try {
                Thread.sleep(1000);
                aumentarTiempo();
            } catch (InterruptedException error) {
                if (!estaActivo()) {
                    return;
                }
            }
        }
    }

    private synchronized void aumentarTiempo() {
        if (!activo) {
            return;
        }

        segundosPartida++;

        if (!nivelPausado) {
            segundosNivel++;
        }
    }

    public synchronized void reiniciarTiempoDelNivel() {
        segundosNivel = 0;
        nivelPausado = false;
    }

    public synchronized void pausarNivel() {
        nivelPausado = true;
    }

    public synchronized void detener() {
        activo = false;
        interrupt();
    }

    private synchronized boolean estaActivo() {
        return activo;
    }

    public synchronized int getSegundosNivel() {
        return segundosNivel;
    }

    public synchronized int getSegundosPartida() {
        return segundosPartida;
    }

    public String getTiempoNivelFormateado() {
        return formatearTiempo(getSegundosNivel());
    }

    public String getTiempoPartidaFormateado() {
        return formatearTiempo(getSegundosPartida());
    }

    private String formatearTiempo(int segundos) {
        int minutos = segundos / 60;
        int segundosRestantes = segundos % 60;

        return String.format("%02d:%02d", minutos, segundosRestantes);
    }
}