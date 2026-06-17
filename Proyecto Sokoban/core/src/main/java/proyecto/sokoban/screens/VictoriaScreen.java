package proyecto.sokoban.screens;
 
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import proyecto.sokoban.datos.GestorUsuarios;
import proyecto.sokoban.utilidad.Textos;
 
public class VictoriaScreen implements Screen {
 
    private Game game;
    private GestorUsuarios gestor;
    private Stage stage;
    private Skin skin;
 
    private int nivel;
    private int totalNiveles;
    private int movimientos;
    private int tiempoSegundos;
    private int reinicios;
 
    public VictoriaScreen(
        Game game,
        GestorUsuarios gestor,
        int nivel,
        int totalNiveles,
        int movimientos,
        int tiempoSegundos,
        int reinicios
    ) {
        this.game = game;
        this.gestor = gestor;
        this.nivel = nivel;
        this.totalNiveles = totalNiveles;
        this.movimientos = movimientos;
        this.tiempoSegundos = tiempoSegundos;
        this.reinicios = reinicios;
    }
 
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
 
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
 
        Label titulo;
 
        if (nivel == totalNiveles) {
            titulo = new Label(Textos.get("victoria.juegoCompleto"), skin);
        } else {
            titulo = new Label(Textos.get("victoria.titulo"), skin);
        }
 
        Label nivelTexto       = new Label(Textos.get("victoria.nivel")       + nivel,                      skin);
        Label movimientosTexto = new Label(Textos.get("victoria.movimientos") + movimientos,                 skin);
        Label tiempoTexto      = new Label(Textos.get("victoria.tiempo")      + formatearTiempo(tiempoSegundos), skin);
        Label reiniciosTexto   = new Label(Textos.get("victoria.reinicios")   + reinicios,                   skin);
 
        TextButton btnPrincipal;
        TextButton btnRepetir = new TextButton(Textos.get("victoria.repetir"), skin);
 
        if (nivel == totalNiveles) {
            btnPrincipal = new TextButton(Textos.get("victoria.volverMenu"), skin);
        } else {
            btnPrincipal = new TextButton(Textos.get("victoria.siguiente"), skin);
        }
 
        btnPrincipal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (nivel == totalNiveles) {
                    game.setScreen(new MenuScreen(game, gestor));
                } else {
                    game.setScreen(new MapaScreen(game, gestor, nivel + 1));
                }
            }
        });
 
        btnRepetir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MapaScreen(game, gestor, nivel));
            }
        });
 
        table.add(titulo).padBottom(30);
        table.row();
 
        table.add(nivelTexto).padTop(8);
        table.row();
 
        table.add(movimientosTexto).padTop(8);
        table.row();
 
        table.add(tiempoTexto).padTop(8);
        table.row();
 
        table.add(reiniciosTexto).padTop(8);
        table.row();
 
        table.add(btnPrincipal).width(280).height(40).padTop(30);
        table.row();
 
        table.add(btnRepetir).width(280).height(40).padTop(12);
        table.row();
 
        if (nivel < totalNiveles) {
            TextButton btnMenuNiveles = new TextButton(Textos.get("victoria.menuNiveles"), skin);
 
            btnMenuNiveles.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new SeleccionNivelScreen(game, gestor));
                }
            });
 
            table.add(btnMenuNiveles).width(280).height(40).padTop(12);
            table.row();
        }
 
        Gdx.input.setInputProcessor(stage);
    }
 
    private String formatearTiempo(int segundosTotales) {
        int minutos = segundosTotales / 60;
        int segundos = segundosTotales % 60;
 
        return String.format("%02d:%02d", minutos, segundos);
    }
 
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }
 
    @Override
    public void resize(int ancho, int alto) {
        stage.getViewport().update(ancho, alto, true);
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