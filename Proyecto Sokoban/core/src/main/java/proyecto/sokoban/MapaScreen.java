package proyecto.sokoban;

import com.badlogic.gdx.Game;
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

    private Game game;
    private GestorUsuarios gestor;
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
    private boolean partidaGuardada;
    private boolean pantallaVictoriaAbierta;

    public MapaScreen() {
        this(null, null, 1);
    }

    public MapaScreen(Game game, GestorUsuarios gestor, int nivelInicial) {
        this.game = game;
        this.gestor = gestor;
        this.partidaGuardada = false;
        this.pantallaVictoriaAbierta = false;

        batch = new SpriteBatch();
        fuente = new BitmapFont();
        fuente.getData().setScale(1.05f);

        piso = cargarTextura("Imagenes/piso.png", "imagenes/piso.png");
        pared = cargarTextura("Imagenes/pared.png", "imagenes/pared.png");
        caja = cargarTextura("Imagenes/caja.png", "imagenes/caja.png");
        objetivo = cargarTextura("Imagenes/objetivo.png", "imagenes/objetivo.png");
        cajaObjetivo = cargarTextura("Imagenes/caja_objetivo.png", "imagenes/caja_objetivo.png");

        GeneroAvatar genero = GeneroAvatar.MASCULINO;

        if (gestor != null && gestor.getLoggedIn() != null) {
            genero = gestor.getLoggedIn().getGenero();
        }

        personajeAbajo = cargarAvatar(genero, 1);
        personajeDerecha = cargarAvatar(genero, 2);
        personajeArriba = cargarAvatar(genero, 3);
        personajeIzquierda = cargarAvatar(genero, 4);

        personajeActual = personajeAbajo;

        sokoban = new Sokoban(nivelInicial);
    }

    private Texture cargarTextura(String rutaPrincipal, String rutaSecundaria) {
        if (Gdx.files.internal(rutaPrincipal).exists()) {
            return new Texture(rutaPrincipal);
        }

        return new Texture(rutaSecundaria);
    }

    private Texture cargarAvatar(GeneroAvatar genero, int posicion) {
        String ruta = genero.getRuta() + posicion + ".PNG";

        if (Gdx.files.internal(ruta).exists()) {
            return new Texture(ruta);
        }

        return new Texture("Avatares/Avatar1_pos" + posicion + ".PNG");
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            guardarPartida(false);

            if (game != null && gestor != null) {
                game.setScreen(new SeleccionNivelScreen(game, gestor));
            }

            return;
        }

        if (sokoban.isJuegoTerminado()) {
            return;
        }

        if (sokoban.isNivelCompletado()) {
            abrirPantallaVictoria();
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            sokoban.reiniciarNivel();
            partidaGuardada = false;
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)
            || Gdx.input.isKeyJustPressed(Input.Keys.UP)) {

            personajeActual = personajeArriba;
            sokoban.moverJugador(-1, 0);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)
            || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {

            personajeActual = personajeAbajo;
            sokoban.moverJugador(1, 0);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)
            || Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {

            personajeActual = personajeIzquierda;
            sokoban.moverJugador(0, -1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)
            || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {

            personajeActual = personajeDerecha;
            sokoban.moverJugador(0, 1);
        }
    }

    private void abrirPantallaVictoria() {
        if (pantallaVictoriaAbierta) {
            return;
        }

        guardarPartida(true);
        pantallaVictoriaAbierta = true;

        if (game != null && gestor != null) {
            game.setScreen(
                new VictoriaScreen(
                    game,
                    gestor,
                    sokoban.getNivelActual() + 1,
                    sokoban.getCantidadNiveles(),
                    sokoban.getMovimientosNivel(),
                    sokoban.getSegundosNivel(),
                    sokoban.getReinicios()
                )
            );
        }
    }

    private void guardarPartida(boolean victoria) {
        if (partidaGuardada) {
            return;
        }

        if (gestor == null || gestor.getLoggedIn() == null) {
            return;
        }

        Usuario usuario = gestor.getLoggedIn();
        int nivel = sokoban.getNivelActual() + 1;

        usuario.registrarPartida(
            nivel,
            sokoban.getMovimientosNivel(),
            sokoban.getSegundosNivel(),
            victoria
        );

        gestor.guardarUsuarioActual();
        partidaGuardada = true;
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
                dibujarPersonaje(x, y, tamanoCelda);
                break;

            case '+':
                batch.draw(objetivo, x, y, tamanoCelda, tamanoCelda);
                dibujarPersonaje(x, y, tamanoCelda);
                break;

            default:
                break;
        }
    }

    private void dibujarPersonaje(float x, float y, float tamanoCelda) {
        float ancho = tamanoCelda * 0.90f;
        float alto = tamanoCelda * 1.40f;

        float xDibujo = x + (tamanoCelda - ancho) / 2f;
        float yDibujo = y + (tamanoCelda - alto) / 2f;

        batch.draw(personajeActual, xDibujo, yDibujo, ancho, alto);
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
            "Controles: WASD o flechas | R reiniciar | ESC menu de niveles",
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
        if (!pantallaVictoriaAbierta) {
            guardarPartida(false);
        }

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
