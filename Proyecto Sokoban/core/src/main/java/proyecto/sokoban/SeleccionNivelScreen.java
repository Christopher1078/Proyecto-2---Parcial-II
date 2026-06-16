package proyecto.sokoban;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SeleccionNivelScreen implements Screen {

    private Game game;
    private GestorUsuarios gestor;
    private Stage stage;
    private Skin skin;

    public SeleccionNivelScreen(Game game, GestorUsuarios gestor) {
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

        Label titulo = new Label("Seleccion de nivel", skin);

        table.add(titulo).padBottom(25);
        table.row();

        for (int nivel = 1; nivel <= 5; nivel++) {
            crearBotonNivel(table, nivel);
        }

        TextButton btnRegresar = new TextButton("Regresar", skin);

        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game, gestor));
            }
        });

        table.add(btnRegresar).width(260).height(40).padTop(25);
        table.row();

        Gdx.input.setInputProcessor(stage);
    }

    private void crearBotonNivel(Table table, int nivel) {
        final int nivelSeleccionado = nivel;

        String texto = "Nivel " + nivel;

        if (gestor.getLoggedIn() != null && !gestor.getLoggedIn().nivelDesbloqueado(nivel)) {
            texto += " - Bloqueado";
        }

        TextButton botonNivel = new TextButton(texto, skin);

        botonNivel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                intentarAbrirNivel(nivelSeleccionado);
            }
        });

        table.add(botonNivel).width(260).height(40).padTop(10);
        table.row();
    }

    private void intentarAbrirNivel(int nivel) {
        Usuario usuario = gestor.getLoggedIn();

        if (usuario != null && !usuario.nivelDesbloqueado(nivel)) {
            Dialog dialog = new Dialog("Nivel bloqueado", skin);
            dialog.text("Aun no has desbloqueado este nivel.");
            dialog.button("Ok");
            dialog.show(stage);
            return;
        }

        game.setScreen(new MapaScreen(game, gestor, nivel));
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
