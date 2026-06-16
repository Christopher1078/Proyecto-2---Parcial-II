package proyecto.sokoban;

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

public class ConfiguracionScreen implements Screen {

    private Game game;
    private GestorUsuarios gestor;
    private Stage stage;
    private Skin skin;
    private Label mensaje;

    public ConfiguracionScreen(Game game, GestorUsuarios gestor) {
        this.game = game;
        this.gestor = gestor;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Usuario usuario = gestor.getLoggedIn();

        Label titulo = new Label("Configuracion", skin);
        mensaje = new Label("", skin);

        String idiomaActual = "Espanol";

        if (usuario != null && usuario.getIdioma() == Idioma.INGLES) {
            idiomaActual = "Ingles";
        }

        Label lblIdioma = new Label("Idioma actual: " + idiomaActual, skin);

        TextButton btnEspanol = new TextButton("Idioma Espanol", skin);
        TextButton btnIngles = new TextButton("Idioma Ingles", skin);
        TextButton btnRegresar = new TextButton("Regresar", skin);

        btnEspanol.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cambiarIdioma(Idioma.ESPANOL);
            }
        });

        btnIngles.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cambiarIdioma(Idioma.INGLES);
            }
        });

        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game, gestor));
            }
        });

        table.add(titulo).padBottom(30);
        table.row();

        table.add(lblIdioma).padBottom(15);
        table.row();

        table.add(btnEspanol).width(260).height(40).padTop(10);
        table.row();

        table.add(btnIngles).width(260).height(40).padTop(10);
        table.row();

        table.add(btnRegresar).width(260).height(40).padTop(25);
        table.row();

        table.add(mensaje).padTop(20);
        table.row();

        Gdx.input.setInputProcessor(stage);
    }

    private void cambiarIdioma(Idioma idioma) {
        Usuario usuario = gestor.getLoggedIn();

        if (usuario == null) {
            mensaje.setText("No hay usuario activo.");
            return;
        }

        usuario.setIdioma(idioma);
        gestor.guardarUsuarioActual();

        game.setScreen(new ConfiguracionScreen(game, gestor));
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