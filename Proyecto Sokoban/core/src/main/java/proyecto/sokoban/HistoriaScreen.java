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
import java.io.IOException;
import java.util.ArrayList;
public class HistoriaScreen implements Screen {

    private final Game game;
    private final GestorUsuarios gestor;
    private Stage stage;
    private Skin skin;
 
    public HistoriaScreen(Game game, GestorUsuarios gestor) {
        this.game = game;
        this.gestor = gestor;
    }    
    
    @Override
    public void show() {
     stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
 
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        stage.addActor(table);
 
        Label titulo = new Label("Historial de Partidas", skin);
        table.add(titulo).padTop(15).padBottom(20);
        table.row();
 
        Table tablaHistorial = new Table();
        tablaHistorial.top();
 
        tablaHistorial.add(new Label("Fecha", skin)).left().padRight(20).padBottom(8);
        tablaHistorial.add(new Label("Nivel", skin)).padRight(20).padBottom(8);
        tablaHistorial.add(new Label("Resultado", skin)).padRight(20).padBottom(8);
        tablaHistorial.add(new Label("Movimientos", skin)).padRight(20).padBottom(8);
        tablaHistorial.add(new Label("Tiempo", skin)).padBottom(8);
        tablaHistorial.row();
 
        tablaHistorial.add(
            new Label("------------------------------------------------", skin)
        ).colspan(5).padBottom(8);
        tablaHistorial.row();
 
        ArrayList<HistorialPartida> historial = new ArrayList<>();
        try {
            historial = gestor.cargarHistorial(gestor.getLoggedIn().getUsername());
        } catch (IOException | ClassNotFoundException e) {
            historial = gestor.getLoggedIn().getHistorial();
        }
 
        if (historial.isEmpty()) {
            tablaHistorial.add(
                new Label("Aun no has jugado ninguna partida.", skin)
            ).colspan(5).padTop(10);
        } else {
            for (int i = historial.size() - 1; i >= 0; i--) {
                HistorialPartida partida = historial.get(i);
 
                Label lblFecha       = new Label(partida.getFecha().toString(), skin);
                Label lblNivel       = new Label("Nivel " + partida.getNivel(), skin);
                Label lblResultado   = new Label(partida.isVictoria() ? "Victoria" : "Intento", skin);
                Label lblMovimientos = new Label(String.valueOf(partida.getMovimientos()), skin);
                Label lblTiempo      = new Label(partida.getTiempoFormateado(), skin);
 
                if (partida.isVictoria()) {
                    lblResultado.setColor(Color.GREEN);
                } else {
                    lblResultado.setColor(Color.RED);
                }
 
                tablaHistorial.add(lblFecha).left().padRight(20).padTop(5);
                tablaHistorial.add(lblNivel).padRight(20).padTop(5);
                tablaHistorial.add(lblResultado).padRight(20).padTop(5);
                tablaHistorial.add(lblMovimientos).padRight(20).padTop(5);
                tablaHistorial.add(lblTiempo).padTop(5);
                tablaHistorial.row();
            }
        }
 
        ScrollPane scroll = new ScrollPane(tablaHistorial, skin);
        scroll.setFadeScrollBars(false);
        scroll.setScrollingDisabled(true, false);
 
        table.add(scroll).width(600).expandY().fillY().padBottom(15);
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
    public void render(float f) {
        ScreenUtils.clear(0,0,0,1);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
