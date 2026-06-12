package proyecto.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import proyecto.sokoban.juego.Mapa;
import proyecto.sokoban.juego.Sokoban;

public class MapaScreen extends ScreenAdapter {

    private ShapeRenderer figuras;
    private SpriteBatch batch;
    private BitmapFont fuente;
    private Sokoban sokoban;

    public MapaScreen() {
        figuras = new ShapeRenderer();
        batch = new SpriteBatch();
        fuente = new BitmapFont();
        fuente.getData().setScale(1.05f);
        sokoban = new Sokoban();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.10f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        leerTeclado();
        dibujarMapa();
        dibujarInformacion();
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
            sokoban.moverJugador(-1, 0);
        }

        if (
            Gdx.input.isKeyJustPressed(Input.Keys.S)
            || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)
        ) {
            sokoban.moverJugador(1, 0);
        }

        if (
            Gdx.input.isKeyJustPressed(Input.Keys.A)
            || Gdx.input.isKeyJustPressed(Input.Keys.LEFT)
        ) {
            sokoban.moverJugador(0, -1);
        }

        if (
            Gdx.input.isKeyJustPressed(Input.Keys.D)
            || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)
        ) {
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

        figuras.begin(ShapeRenderer.ShapeType.Filled);

        for (int fila = 0; fila < mapa.getCantidadFilas(); fila++) {
            for (int columna = 0; columna < mapa.getCantidadColumnas(); columna++) {
                float x = inicioX + columna * tamanoCelda;
                float y = inicioY
                    + (mapa.getCantidadFilas() - fila - 1) * tamanoCelda;

                char simbolo = mapa.getSimboloVisible(fila, columna);
                dibujarCelda(simbolo, x, y, tamanoCelda);
            }
        }

        figuras.end();
    }

    private void dibujarCelda(char simbolo, float x, float y, float tamano) {
        figuras.setColor(0.30f, 0.38f, 0.30f, 1);
        figuras.rect(x + 1, y + 1, tamano - 2, tamano - 2);

        if (simbolo == '#') {
            figuras.setColor(0.24f, 0.25f, 0.28f, 1);
            figuras.rect(x + 2, y + 2, tamano - 4, tamano - 4);
            return;
        }

        if (simbolo == '.' || simbolo == '*' || simbolo == '+') {
            figuras.setColor(0.95f, 0.74f, 0.18f, 1);
            figuras.circle(
                x + tamano / 2f,
                y + tamano / 2f,
                tamano * 0.20f
            );
        }

        if (simbolo == '$' || simbolo == '*') {
            if (simbolo == '*') {
                figuras.setColor(0.20f, 0.68f, 0.32f, 1);
            } else {
                figuras.setColor(0.67f, 0.39f, 0.18f, 1);
            }

            figuras.rect(
                x + tamano * 0.16f,
                y + tamano * 0.16f,
                tamano * 0.68f,
                tamano * 0.68f
            );
        }

        if (simbolo == '@' || simbolo == '+') {
            figuras.setColor(0.22f, 0.72f, 0.92f, 1);
            figuras.circle(
                x + tamano / 2f,
                y + tamano / 2f,
                tamano * 0.28f
            );
        }
    }

    private void dibujarInformacion() {
        batch.begin();

        fuente.setColor(1, 1, 1, 1);

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

        batch.end();
    }

    @Override
    public void hide() {
        sokoban.detener();
    }

    @Override
    public void dispose() {
        sokoban.detener();
        figuras.dispose();
        batch.dispose();
        fuente.dispose();
    }
}