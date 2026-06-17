package proyecto.sokoban.screens;
 
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.io.IOException;
import proyecto.sokoban.datos.GeneroAvatar;
import proyecto.sokoban.datos.GestorUsuarios;
import proyecto.sokoban.datos.Usuario;
import proyecto.sokoban.utilidad.Textos;
 
public class SeleccionarAvatarScreen implements Screen {
 
    private final Game game;
    private Stage stage;
    private Skin skin;
    private final GestorUsuarios gestor;
    private Texture AvatarMasculino;
    private Texture AvatarFemenino;
 
    public SeleccionarAvatarScreen(Game game, GestorUsuarios gestor) {
        this.game = game;
        this.gestor = gestor;
    }
 
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
 
        AvatarMasculino = new Texture("Avatares/Avatar1_pos1.PNG");
        AvatarFemenino = new Texture("Avatares/Avatar2_pos1.PNG");
 
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
 
        Label titulo = new Label(Textos.get("avatar.titulo"), skin);
 
        Image imgMasculino = new Image(AvatarMasculino);
        Image imgFemenino = new Image(AvatarFemenino);
 
        TextButton btnMasculino = new TextButton(Textos.get("avatar.btn1"), skin);
        TextButton btnFemenino = new TextButton(Textos.get("avatar.btn2"), skin);
        TextButton btnRegresar = new TextButton(Textos.get("avatar.regresar"), skin);
 
        btnMasculino.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                seleccionarAvatar(GeneroAvatar.MASCULINO);
            }
        });
 
        btnFemenino.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                seleccionarAvatar(GeneroAvatar.FEMENINO);
            }
        });
 
        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game, gestor));
            }
        });
 
        table.add(titulo).padBottom(40).colspan(2);
        table.row();
 
        table.add(imgMasculino).size(120, 240).padRight(40);
        table.add(imgFemenino).size(120, 240);
        table.row();
 
        table.add(btnMasculino).width(150).height(40).padTop(10).padRight(40);
        table.add(btnFemenino).width(150).height(40).padTop(10);
        table.row();
 
        table.add(btnRegresar).width(250).height(40).padTop(30).colspan(2);
        table.row();
 
        Gdx.input.setInputProcessor(stage);
    }
 
    private void seleccionarAvatar(GeneroAvatar genero) {
        try {
            Usuario usuario = gestor.getLoggedIn();
            usuario.setGenero(genero);
            gestor.guardarUsuario(usuario);
            game.setScreen(new MenuScreen(game, gestor));
        } catch (IOException | ClassNotFoundException error) {
            Dialog dialog = new Dialog("Error", skin);
            dialog.text("Error: "+error.getMessage());
            dialog.button("Ok");
            dialog.show(stage);
        }
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
        AvatarMasculino.dispose();
        AvatarFemenino.dispose();
    }
}
