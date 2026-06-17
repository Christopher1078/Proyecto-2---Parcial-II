package proyecto.sokoban.screens;
 
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
import proyecto.sokoban.datos.GestorUsuarios;
import proyecto.sokoban.datos.HistorialPartida;
import proyecto.sokoban.utilidad.Textos;
 
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
 
        Table root = new Table();
        root.setFillParent(true);
        root.top();
        stage.addActor(root);
 
        Label titulo = new Label(Textos.get("historial.titulo"), skin);
        root.add(titulo).padTop(15).padBottom(20);
        root.row();
 
        Table tablaHistorial = new Table();
        tablaHistorial.top();
 
        tablaHistorial.add(new Label(Textos.get("historial.fecha"), skin)).left().padRight(20).padBottom(8);
        tablaHistorial.add(new Label(Textos.get("historial.nivel"), skin)).padRight(20).padBottom(8);
        tablaHistorial.add(new Label(Textos.get("historial.resultado"), skin)).padRight(20).padBottom(8);
        tablaHistorial.add(new Label(Textos.get("historial.movimientos"), skin)).padRight(20).padBottom(8);
        tablaHistorial.add(new Label(Textos.get("historial.tiempo"), skin)).padBottom(8);
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
                new Label(Textos.get("historial.vacio"), skin)
            ).colspan(5).padTop(10);
        } else {
            for (int i = historial.size() - 1; i >= 0; i--) {
                HistorialPartida partida = historial.get(i);
 
                Label lblFecha       = new Label(partida.getFecha().toString(), skin);
                Label lblNivel       = new Label(Textos.get("nivel.nivel") + partida.getNivel(), skin);
                Label lblResultado   = new Label(partida.isVictoria() ? Textos.get("historial.victoria") : Textos.get("historial.intento"), skin);
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
 
        root.add(scroll).width(600).expandY().fillY().padBottom(15);
        root.row();
 
        TextButton btnVolver = new TextButton(Textos.get("historial.volver"), skin);
        btnVolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MiPerfilScreen(game, gestor));
            }
        });
 
        root.add(btnVolver).width(200).height(40).padBottom(20);
 
        Gdx.input.setInputProcessor(stage);
    }
 
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }
 
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
 
    @Override
    public void pause() {}
 
    @Override
    public void resume() {}
 
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
