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
public class DueloScreen extends ScreenAdapter{

    private final Game game;
    private final GestorUsuarios gestor;
    private final SolicitudDuelo duelo;
    private final boolean esRetado;
    private SpriteBatch batch;
    private BitmapFont fuente;
    private Sokoban sokoban;
    private Texture piso, pared, caja, objetivo, cajaObjetivo;
    private Texture personajeAbajo, personajeDerecha, personajeArriba, personajeIzquierda;
    private Texture personajeActual;
 
    private boolean resultadoGuardado = false;
 
    public DueloScreen(Game game, GestorUsuarios gestor, SolicitudDuelo duelo, boolean esRetado) {
        this.game=game;
        this.gestor= gestor;
        this.duelo= duelo;
        this.esRetado= esRetado;
 
        batch=new SpriteBatch();
        fuente=new BitmapFont();
        fuente.getData().setScale(1.05f);
 
        piso=cargarTextura("Imagenes/piso.png","imagenes/piso.png");
        pared=cargarTextura("Imagenes/pared.png","imagenes/pared.png");
        caja=cargarTextura("Imagenes/caja.png","imagenes/caja.png");
        objetivo=cargarTextura("Imagenes/objetivo.png","imagenes/objetivo.png");
        cajaObjetivo=cargarTextura("Imagenes/caja_objetivo.png","imagenes/caja_objetivo.png");
 
        GeneroAvatar genero = GeneroAvatar.MASCULINO;
        if (gestor != null && gestor.getLoggedIn() != null) {
            genero=gestor.getLoggedIn().getGenero();
        }
 
        personajeAbajo=cargarAvatar(genero, 1);
        personajeDerecha=cargarAvatar(genero, 2);
        personajeArriba= cargarAvatar(genero, 3);
        personajeIzquierda=cargarAvatar(genero, 4);
        personajeActual=personajeAbajo;
 
        sokoban=new Sokoban(duelo.getNivel());
    }
 
    private Texture cargarTextura(String principal, String secundaria) {
        if (Gdx.files.internal(principal).exists()) 
            return new Texture(principal);
        return new Texture(secundaria);
    }
 
    private Texture cargarAvatar(GeneroAvatar genero, int pos) {
        String ruta = genero.getRuta() + pos + ".PNG";
        if (Gdx.files.internal(ruta).exists()) 
            return new Texture(ruta);
        return new Texture("Avatares/Avatar1_pos" + pos + ".PNG");
    }
 
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.10f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
 
        leerTeclado();
 
        batch.begin();
        dibujarMapa();
        dibujarHud();
        batch.end();
    }
 
    private void leerTeclado() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            sokoban.detener();
            game.setScreen(new DuelosScreen(game, gestor));
            return;
        }
 
        if (sokoban.isJuegoTerminado()) 
            return;
 
        if (sokoban.isNivelCompletado()) {
            guardarResultado();
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                sokoban.detener();
                game.setScreen(new DuelosScreen(game, gestor));
            }
            return;
        }
 
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            sokoban.reiniciarNivel();
            return;
        }
 
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            personajeActual = personajeArriba;
            sokoban.moverJugador(-1, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            personajeActual = personajeAbajo;
            sokoban.moverJugador(1, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            personajeActual = personajeIzquierda;
            sokoban.moverJugador(0, -1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            personajeActual = personajeDerecha;
            sokoban.moverJugador(0, 1);
        }
    }
 
    private void guardarResultado() {
        if (resultadoGuardado) 
            return;
        resultadoGuardado = true;
 
        long tiempo = sokoban.getSegundosNivel();
 
        try {
            if (esRetado) {
                gestor.aceptarDuelo(duelo, tiempo);
            } else {
                gestor.completarDuelo(duelo, tiempo);
            }
        } catch (Exception e) {
            System.out.println("Error al guardar resultado del duelo: " + e.getMessage());
        }
    }
 
    private void dibujarMapa() {
        Mapa mapa = sokoban.getMapaActual();
 
        float anchoDisp=Gdx.graphics.getWidth()-50f;
        float altoDisp=Gdx.graphics.getHeight()-190f;
        float anchoCelda=anchoDisp/mapa.getCantidadColumnas();
        float altoCelda=altoDisp/mapa.getCantidadFilas();
        float tam=Math.min(anchoCelda,altoCelda);
 
        float anchoMapa=tam*mapa.getCantidadColumnas();
        float inicioX=(Gdx.graphics.getWidth()-anchoMapa)/2f;
        float inicioY=30f;
 
        for (int fila = 0; fila<mapa.getCantidadFilas(); fila++) {
            for (int col=0; col<mapa.getCantidadColumnas(); col++) {
                float x=inicioX+col*tam;
                float y=inicioY+(mapa.getCantidadFilas()-fila-1)*tam;
                dibujarCelda(mapa.getSimboloVisible(fila, col), x, y, tam);
            }
        }
    }
 
    private void dibujarCelda(char simbolo, float x, float y, float tam) {
        batch.draw(piso, x, y, tam, tam);
        switch (simbolo) {
            case '#': 
                batch.draw(pared, x, y, tam, tam); 
                break;
            case '$': 
                batch.draw(caja, x, y, tam, tam); 
                break;
            case '.': 
                batch.draw(objetivo, x, y, tam, tam); 
                break;
            case '*': 
                batch.draw(cajaObjetivo, x, y, tam, tam); 
                break;
            case '@': 
                dibujarPersonaje(x, y, tam); 
                break;
            case '+':
                batch.draw(objetivo, x, y, tam, tam);
                dibujarPersonaje(x, y, tam);
                break;
        }
    }
 
    private void dibujarPersonaje(float x, float y, float tam) {
        float escala=1.15f;
        float ancho=tam*escala;
        float alto=tam*escala;
        float xd=x+(tam-ancho)/2f;
        float yd=y+(tam-alto)/2f;
        batch.draw(personajeActual, xd, yd, ancho, alto);
    }
 
    private void dibujarHud() {
        int h = Gdx.graphics.getHeight();
        String rival = duelo.getRival(gestor.getLoggedIn().getUsername());
        String rolTexto = esRetado ? "Retado por: " + duelo.getRetador(): "Retaste a: " + rival;
 
        fuente.draw(batch, "DUELO | Nivel " + duelo.getNivel() + " | " + rolTexto, 20, h - 22);
        fuente.draw(batch, "Movimientos: " + sokoban.getMovimientosNivel()+ " | Tiempo: " + sokoban.getTiempoNivelFormateado(), 20, h - 48);
 
        long tiempoRival = esRetado ? -1 : duelo.getTiempoRetado();
        if (tiempoRival >= 0) {
            fuente.draw(batch, "Tiempo de " + rival + ": "
                    + SolicitudDuelo.formatearTiempo(tiempoRival)
                    + "  (supéralo!)", 20, h - 74);
        } else {
            fuente.draw(batch, rival + " aun no ha jugado.", 20, h - 74);
        }
 
        fuente.draw(batch, "Controles: WASD o flechas | R reiniciar | ESC salir", 20, h - 100);
        fuente.draw(batch, sokoban.getMensaje()/*+ (sokoban.isNivelCompletado() ? "  Presiona ENTER para continuar." : "")*/, 20, h - 126);
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
        piso.dispose(); pared.dispose(); caja.dispose();
        objetivo.dispose(); cajaObjetivo.dispose();
        personajeAbajo.dispose(); personajeDerecha.dispose();
        personajeArriba.dispose(); personajeIzquierda.dispose();
    }    
}
