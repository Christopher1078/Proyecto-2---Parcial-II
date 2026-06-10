package proyecto.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
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
    private boolean esperandoSiguienteNivel;
    private boolean juegoFinalizado;

    public MapaScreen() {
        figuras = new ShapeRenderer();
        batch = new SpriteBatch();
        fuente = new BitmapFont();
        sokoban = new Sokoban();

        esperandoSiguienteNivel = false;
        juegoFinalizado = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        leerTeclado();
        dibujarMapa();
        dibujarInformacion();
    }

    private void leerTeclado() {
        if (juegoFinalizado) {
            return;
        }

        if (esperandoSiguienteNivel) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                boolean hayOtroNivel = sokoban.avanzarNivel();
                esperandoSiguienteNivel = false;

                if (!hayOtroNivel) {
                    juegoFinalizado = true;
                }
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

        if (sokoban.nivelCompletado()) {
            esperandoSiguienteNivel = true;
        }
    }

    private void dibujarMapa() {
        Mapa mapa = sokoban.getMapaActual();

        float espacioHorizontal = Gdx.graphics.getWidth() - 40f;
        float espacioVertical = Gdx.graphics.getHeight() - 150f;

        float anchoCelda =
            espacioHorizontal / mapa.getCantidadColumnas();

        float altoCelda =
            espacioVertical / mapa.getCantidadFilas();

        float tamanoCelda = Math.min(anchoCelda, altoCelda);

        float anchoMapa =
            tamanoCelda * mapa.getCantidadColumnas();

        float inicioX =
            (Gdx.graphics.getWidth() - anchoMapa) / 2f;

        float inicioY = 25f;

        figuras.begin(ShapeRenderer.ShapeType.Filled);

        for (int fila = 0; fila < mapa.getCantidadFilas(); fila++) {
            for (
                int columna = 0;
                columna < mapa.getCantidadColumnas();
                columna++
            ) {
                char simbolo =
                    mapa.getSimboloVisible(fila, columna);

                figuras.setColor(obtenerColor(simbolo));

                float x = inicioX + columna * tamanoCelda;

                float y =
                    inicioY
                    + (mapa.getCantidadFilas() - fila - 1)
                    * tamanoCelda;

                figuras.rect(
                    x + 1,
                    y + 1,
                    tamanoCelda - 2,
                    tamanoCelda - 2
                );
            }
        }

        figuras.end();
    }

    private Color obtenerColor(char simbolo) {
        switch (simbolo) {
            case '#':
                return Color.DARK_GRAY;

            case '$':
                return Color.BROWN;

            case '.':
                return Color.GOLD;

            case '*':
                return Color.GREEN;

            case '@':
                return Color.CYAN;

            case '+':
                return Color.BLUE;

            default:
                return Color.GRAY;
        }
    }

    private void dibujarInformacion() {
        batch.begin();

        fuente.draw(
            batch,
            "Nivel "
                + (sokoban.getNivelActual() + 1)
                + " de "
                + sokoban.getCantidadNiveles()
                + " | "
                + sokoban.getNombreNivelActual(),
            20,
            Gdx.graphics.getHeight() - 20
        );

        fuente.draw(
            batch,
            "Movimientos: "
                + sokoban.getMovimientos()
                + " | Fallos: "
                + sokoban.getFallos()
                + " | Tiempo: "
                + sokoban.getTiempoFormateado(),
            20,
            Gdx.graphics.getHeight() - 45
        );

        fuente.draw(
            batch,
            "Controles: WASD o flechas | R para reiniciar",
            20,
            Gdx.graphics.getHeight() - 70
        );

        if (esperandoSiguienteNivel) {
            fuente.draw(
                batch,
                "Nivel completado. Presiona ENTER para continuar.",
                20,
                Gdx.graphics.getHeight() - 95
            );
        }

        if (juegoFinalizado) {
            fuente.draw(
                batch,
                "Felicidades. Completaste todos los niveles.",
                20,
                Gdx.graphics.getHeight() - 95
            );
        }

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