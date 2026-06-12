package proyecto.sokoban.juego;

import java.util.ArrayList;

public class Mapa {

    private char[][] terreno;
    private ArrayList<Caja> cajas;
    private ArrayList<Objetivo> objetivos;
    private int filaJugador;
    private int columnaJugador;

    public Mapa(String[] filasDelMapa) {
        cajas = new ArrayList<>();
        objetivos = new ArrayList<>();
        filaJugador = -1;
        columnaJugador = -1;

        int cantidadFilas = filasDelMapa.length;
        int cantidadColumnas = buscarMayorLongitud(filasDelMapa);

        terreno = new char[cantidadFilas][cantidadColumnas];

        for (int fila = 0; fila < cantidadFilas; fila++) {
            for (int columna = 0; columna < cantidadColumnas; columna++) {
                terreno[fila][columna] = ' ';
            }
        }

        leerMapa(filasDelMapa);
        validarMapa();
    }

    private int buscarMayorLongitud(String[] filasDelMapa) {
        int mayorLongitud = 0;

        for (String fila : filasDelMapa) {
            if (fila.length() > mayorLongitud) {
                mayorLongitud = fila.length();
            }
        }

        return mayorLongitud;
    }

    private void leerMapa(String[] filasDelMapa) {
        for (int fila = 0; fila < filasDelMapa.length; fila++) {
            String textoFila = filasDelMapa[fila];

            for (int columna = 0; columna < textoFila.length(); columna++) {
                char simbolo = textoFila.charAt(columna);

                switch (simbolo) {
                    case '#':
                        terreno[fila][columna] = '#';
                        break;

                    case '.':
                        terreno[fila][columna] = '.';
                        objetivos.add(new Objetivo(fila, columna));
                        break;

                    case '@':
                        terreno[fila][columna] = ' ';
                        filaJugador = fila;
                        columnaJugador = columna;
                        break;

                    case '+':
                        terreno[fila][columna] = '.';
                        filaJugador = fila;
                        columnaJugador = columna;
                        objetivos.add(new Objetivo(fila, columna));
                        break;

                    case '$':
                        terreno[fila][columna] = ' ';
                        cajas.add(new Caja(fila, columna));
                        break;

                    case '*':
                        terreno[fila][columna] = '.';
                        cajas.add(new Caja(fila, columna));
                        objetivos.add(new Objetivo(fila, columna));
                        break;

                    default:
                        terreno[fila][columna] = ' ';
                        break;
                }
            }
        }
    }

    private void validarMapa() {
        if (filaJugador == -1 || columnaJugador == -1) {
            throw new IllegalArgumentException("El nivel debe incluir un jugador.");
        }

        if (cajas.isEmpty()) {
            throw new IllegalArgumentException("El nivel debe incluir al menos una caja.");
        }

        if (cajas.size() != objetivos.size()) {
            throw new IllegalArgumentException(
                "La cantidad de cajas debe ser igual a la cantidad de objetivos."
            );
        }
    }

    public boolean moverJugador(int cambioFila, int cambioColumna) {
        int siguienteFila = filaJugador + cambioFila;
        int siguienteColumna = columnaJugador + cambioColumna;

        if (hayPared(siguienteFila, siguienteColumna)) {
            return false;
        }

        Caja cajaEncontrada = buscarCaja(siguienteFila, siguienteColumna);

        if (cajaEncontrada != null) {
            int filaDespuesDeCaja = siguienteFila + cambioFila;
            int columnaDespuesDeCaja = siguienteColumna + cambioColumna;

            boolean noSePuedeEmpujar =
                hayPared(filaDespuesDeCaja, columnaDespuesDeCaja)
                || buscarCaja(filaDespuesDeCaja, columnaDespuesDeCaja) != null;

            if (noSePuedeEmpujar) {
                return false;
            }

            cajaEncontrada.mover(cambioFila, cambioColumna);
        }

        filaJugador = siguienteFila;
        columnaJugador = siguienteColumna;
        return true;
    }

    private boolean hayPared(int fila, int columna) {
        boolean fueraDelMapa =
            fila < 0
            || fila >= terreno.length
            || columna < 0
            || columna >= terreno[0].length;

        if (fueraDelMapa) {
            return true;
        }

        return terreno[fila][columna] == '#';
    }

    private Caja buscarCaja(int fila, int columna) {
        for (Caja caja : cajas) {
            boolean mismaPosicion =
                caja.getFila() == fila && caja.getColumna() == columna;

            if (mismaPosicion) {
                return caja;
            }
        }

        return null;
    }

    private boolean hayObjetivo(int fila, int columna) {
        for (Objetivo objetivo : objetivos) {
            boolean mismaPosicion =
                objetivo.getFila() == fila
                && objetivo.getColumna() == columna;

            if (mismaPosicion) {
                return true;
            }
        }

        return false;
    }

    public boolean estaCompletado() {
        for (Caja caja : cajas) {
            if (!hayObjetivo(caja.getFila(), caja.getColumna())) {
                return false;
            }
        }

        return true;
    }

    public char getSimboloVisible(int fila, int columna) {
        if (terreno[fila][columna] == '#') {
            return '#';
        }

        Caja cajaEncontrada = buscarCaja(fila, columna);

        if (cajaEncontrada != null) {
            if (hayObjetivo(fila, columna)) {
                return '*';
            }

            return '$';
        }

        boolean estaElJugador =
            filaJugador == fila && columnaJugador == columna;

        if (estaElJugador) {
            if (hayObjetivo(fila, columna)) {
                return '+';
            }

            return '@';
        }

        if (hayObjetivo(fila, columna)) {
            return '.';
        }

        return ' ';
    }

    public int getCantidadFilas() {
        return terreno.length;
    }

    public int getCantidadColumnas() {
        return terreno[0].length;
    }
}