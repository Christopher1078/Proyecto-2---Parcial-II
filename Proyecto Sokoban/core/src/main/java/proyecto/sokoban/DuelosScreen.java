package proyecto.sokoban;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.ArrayList;
public class DuelosScreen implements Screen {

    private final Game game;
    private final GestorUsuarios gestor;
    private Stage stage;
    private Skin skin;
 
    public DuelosScreen(Game game, GestorUsuarios gestor) {
        this.game   = game;
        this.gestor = gestor;
    }
 
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin  = new Skin(Gdx.files.internal("uiskin.json"));
 
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        stage.addActor(table);
 
        Label titulo = new Label("Duelos", skin);
        table.add(titulo).padTop(15).padBottom(20);
        table.row();
 
        Usuario yo = gestor.getLoggedIn();
        String miNombre = yo.getUsername();
 
        table.add(new Label("Retos recibidos", skin)).padBottom(8);
        table.row();
 
        Table tablaPendientes = new Table();
        ArrayList<SolicitudDuelo> pendientes = yo.getDuelosPendientesAceptar(miNombre);
 
        if (pendientes.isEmpty()) {
            tablaPendientes.add(new Label("No tienes retos pendientes.", skin)).pad(5);
        } else {
            for (SolicitudDuelo d : pendientes) {
                Label lbl = new Label(d.getRetador() + "  |  Nivel " + d.getNivel(), skin);
                TextButton btnJugar = new TextButton("Aceptar y jugar", skin);
 
                btnJugar.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(new DueloScreen(game, gestor, d, true));
                    }
                });
 
                tablaPendientes.add(lbl).left().padRight(20).pad(5);
                tablaPendientes.add(btnJugar).width(160).height(35).pad(5);
                tablaPendientes.row();
            }
        }
 
        ScrollPane scrollPendientes = new ScrollPane(tablaPendientes, skin);
        scrollPendientes.setFadeScrollBars(false);
        table.add(scrollPendientes).width(500).height(110).padBottom(20);
        table.row();
 
        table.add(new Label("Tu turno de jugar", skin)).padBottom(8);
        table.row();
 
        Table tablaTuTurno = new Table();
        ArrayList<SolicitudDuelo> porJugar = yo.getDuelosPorJugar(miNombre);
 
        if (porJugar.isEmpty()) {
            tablaTuTurno.add(new Label("No hay duelos esperando tu turno.", skin)).pad(5);
        } else {
            for (SolicitudDuelo d : porJugar) {
                String rival = d.getRival(miNombre);
                String tiempoRival = SolicitudDuelo.formatearTiempo(d.getTiempoRetado());
                Label lbl = new Label(rival + "  |  Nivel " + d.getNivel()
                        + "  |  Tiempo rival: " + tiempoRival, skin);
                TextButton btnJugar = new TextButton("Jugar", skin);
 
                btnJugar.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(new DueloScreen(game, gestor, d, false));
                    }
                });
 
                tablaTuTurno.add(lbl).left().padRight(20).pad(5);
                tablaTuTurno.add(btnJugar).width(100).height(35).pad(5);
                tablaTuTurno.row();
            }
        }
 
        ScrollPane scrollTuTurno = new ScrollPane(tablaTuTurno, skin);
        scrollTuTurno.setFadeScrollBars(false);
        table.add(scrollTuTurno).width(500).height(110).padBottom(20);
        table.row();
 
        table.add(new Label("Resultados", skin)).padBottom(8);
        table.row();
 
        Table tablaCompletados = new Table();
        ArrayList<SolicitudDuelo> completados = yo.getDuelosCompetados(miNombre);
 
        if (completados.isEmpty()) {
            tablaCompletados.add(new Label("Aun no has completado ningun duelo.", skin)).pad(5);
        } else {
            for (int i = completados.size() - 1; i >= 0; i--) {
                SolicitudDuelo d = completados.get(i);
                String rival   = d.getRival(miNombre);
                String ganador = d.getGanador();
                boolean gane   = miNombre.equals(ganador);
 
                String miTiempo    = SolicitudDuelo.formatearTiempo(
                        miNombre.equals(d.getRetador()) ? d.getTiempoRetador() : d.getTiempoRetado());
                String tiempoRival = SolicitudDuelo.formatearTiempo(
                        miNombre.equals(d.getRetador()) ? d.getTiempoRetado() : d.getTiempoRetador());
 
                Label lblInfo = new Label(
                        "Nivel " + d.getNivel() + "  vs " + rival
                        + "  |  Tu: " + miTiempo + "  |  " + rival + ": " + tiempoRival,
                        skin);
 
                Label lblResultado = new Label(gane ? "Ganaste" : "Perdiste", skin);
                lblResultado.setColor(gane ? Color.GREEN : Color.RED);
 
                tablaCompletados.add(lblInfo).left().padRight(20).pad(5);
                tablaCompletados.add(lblResultado).pad(5);
                tablaCompletados.row();
            }
        }
 
        ScrollPane scrollCompletados = new ScrollPane(tablaCompletados, skin);
        scrollCompletados.setFadeScrollBars(false);
        table.add(scrollCompletados).width(500).height(130).padBottom(20);
        table.row();
 
        TextButton btnVolver = new TextButton("Volver", skin);
        btnVolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MiPerfilScreen(game, gestor));
            }
        });
 
        table.add(btnVolver).width(200).height(40).padBottom(20);
 
        Gdx.input.setInputProcessor(stage);
    }
 
    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }
 
    @Override
    public void resize(int w, int h){ 
        stage.getViewport().update(w, h, true); 
    }
 
    @Override public void pause(){
    }
    
    @Override public void resume(){
    }
 
    @Override
    public void hide(){ 
        Gdx.input.setInputProcessor(null); 
    }
 
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }    
}
