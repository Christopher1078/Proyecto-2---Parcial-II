package proyecto.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import proyecto.sokoban.juego.Mapa;
import proyecto.sokoban.juego.Sokoban;

public class MapaScreen extends ScreenAdapter {

    private SpriteBatch batch;
    private BitmapFont fuente;
    private Sokoban sokoban;

    private Texture piso;
    private Texture pared;
    private Texture caja;
    private Texture objetivo;
    private Texture cajaObjetivo;

    private Texture personajeAbajo;
    private Texture personajeDerecha;
    private Texture personajeArriba;
    private Texture personajeIzquierda;

    private Texture personajeActual;

    public MapaScreen() {
        batch = new SpriteBatch();
        fuente = new BitmapFont();
        fuente.getData().setScale(1.05f);

        piso = new Texture("imagenes/piso.png");
        pared = new Texture("imagenes/pared.png");
        caja = new Texture("imagenes/caja.png");
        objetivo = new Texture("imagenes/objetivo.png");
        cajaObjetivo = new Texture("imagenes/caja_objetivo.png");

        personajeAbajo = new Texture("Avatar1_pos1.PNG");
        personajeDerecha = new Texture("Avatar1_pos2.PNG");
        personajeArriba = new Texture("Avatar1_pos3.PNG");
        personajeIzquierda = new Texture("Avatar1_pos4.PNG");

        personajeActual = personajeAbajo;

        sokoban = new Sokoban();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.10f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        leerTeclado();

        batch.begin();
        dibujarMapa();
        dibujarInformacion();
        batch.end();
    }

    private void leerTeclado() {
        if (sokoban.isJuegoTerminado()) {
            return;
        }

        if (sokoban.isNivelCompletado()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                sokoban.avanzarNivel();
            }

            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            sokoban.reiniciarNivel();
            return;
        }

        if (
            Gdx.input.isKeyJustPressed(Input.Keys.W)
            || Gdx.input.isKeyJustPressed(Input.Keys.UP)
        ) {
            personajeActual = personajeArriba;
            sokoban.moverJugador(-1, 0);
        }

        if (
            Gdx.input.isKeyJustPressed(Input.Keys.S)
            || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)
        ) {
            personajeActual = personajeAbajo;
            sokoban.moverJugador(1, 0);
        }

        if (
            Gdx.input.isKeyJustPressed(Input.Keys.A)
            || Gdx.input.isKeyJustPressed(Input.Keys.LEFT)
        ) {
            personajeActual = personajeIzquierda;
            sokoban.moverJugador(0, -1);
        }

        if (
            Gdx.input.isKeyJustPressed(Input.Keys.D)
            || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)
        ) {
            personajeActual = personajeDerecha;
            sokoban.moverJugador(0, 1);
        }
    }

    private void dibujarMapa() {
        Mapa mapa = sokoban.getMapaActual();

        float anchoDisponible = Gdx.graphics.getWidth() - 50f;
        float altoDisponible = Gdx.graphics.getHeight() - 190f;

        float anchoCelda = anchoDisponible / mapa.getCantidadColumnas();
        float altoCelda = altoDisponible / mapa.getCantidadFilas();

        float tamanoCelda = Math.min(anchoCelda, altoCelda);

        float anchoMapa = tamanoCelda * mapa.getCantidadColumnas();
        float inicioX = (Gdx.graphics.getWidth() - anchoMapa) / 2f;
        float inicioY = 30f;

        for (int fila = 0; fila < mapa.getCantidadFilas(); fila++) {
            for (int columna = 0; columna < mapa.getCantidadColumnas(); columna++) {
                float x = inicioX + columna * tamanoCelda;

                float y =
                    inicioY
                    + (mapa.getCantidadFilas() - fila - 1)
                    * tamanoCelda;

                char simbolo = mapa.getSimboloVisible(fila, columna);

                dibujarCelda(simbolo, x, y, tamanoCelda);
            }
        }
    }

    private void dibujarCelda(char simbolo, float x, float y, float tamanoCelda) {
        batch.draw(piso, x, y, tamanoCelda, tamanoCelda);

        switch (simbolo) {
            case '#':
                batch.draw(pared, x, y, tamanoCelda, tamanoCelda);
                break;

            case '$':
                batch.draw(caja, x, y, tamanoCelda, tamanoCelda);
                break;

            case '.':
                batch.draw(objetivo, x, y, tamanoCelda, tamanoCelda);
                break;

            case '*':
                batch.draw(cajaObjetivo, x, y, tamanoCelda, tamanoCelda);
                break;

            case '@':
                batch.draw(personajeActual, x, y, tamanoCelda, tamanoCelda);
                break;

            case '+':
                batch.draw(objetivo, x, y, tamanoCelda, tamanoCelda);
                batch.draw(personajeActual, x, y, tamanoCelda, tamanoCelda);
                break;

            default:
                break;
        }
    }

    private void dibujarInformacion() {
        fuente.draw(
            batch,
            "SOKOBAN | Nivel "
                + (sokoban.getNivelActual() + 1)
                + " de "
                + sokoban.getCantidadNiveles()
                + " | "
                + sokoban.getNombreNivelActual(),
            20,
            Gdx.graphics.getHeight() - 22
        );

        fuente.draw(
            batch,
            "Movimientos: "
                + sokoban.getMovimientosNivel()
                + " | Fallos: "
                + sokoban.getFallosNivel()
                + " | Vidas: "
                + sokoban.getVidas()
                + " | Reinicios: "
                + sokoban.getReinicios(),
            20,
            Gdx.graphics.getHeight() - 48
        );

        fuente.draw(
            batch,
            "Tiempo del nivel: "
                + sokoban.getTiempoNivelFormateado()
                + " | Tiempo total: "
                + sokoban.getTiempoPartidaFormateado(),
            20,
            Gdx.graphics.getHeight() - 74
        );

        fuente.draw(
            batch,
            "Controles: WASD o flechas para moverse | R para reiniciar",
            20,
            Gdx.graphics.getHeight() - 100
        );

        fuente.draw(
            batch,
            sokoban.getMensaje(),
            20,
            Gdx.graphics.getHeight() - 126
        );
    }

    @Override
    public void hide() {
        sokoban.detener();
    }

    @Override
    public void dispose() {
        sokoban.detener();

        batch.dispose();
        fuente.dispose();

        piso.dispose();
        pared.dispose();
        caja.dispose();
        objetivo.dispose();
        cajaObjetivo.dispose();

        personajeAbajo.dispose();
        personajeDerecha.dispose();
        personajeArriba.dispose();
        personajeIzquierda.dispose();
    }
}