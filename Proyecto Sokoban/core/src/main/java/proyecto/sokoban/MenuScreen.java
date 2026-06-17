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
 
public class MenuScreen implements Screen {
 
    private Game game;
    private GestorUsuarios gestor;
    private Stage stage;
    private Skin skin;
 
    public MenuScreen(Game game, GestorUsuarios gestor) {
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
 
        Label titulo = new Label(Textos.get("menu.titulo"), skin);
 
        TextButton btnJugar = new TextButton(Textos.get("menu.jugar"), skin);
        TextButton btnMiPerfil = new TextButton(Textos.get("menu.perfil"), skin);
        TextButton btnRanking = new TextButton(Textos.get("menu.ranking"), skin);
        TextButton btnConfi = new TextButton(Textos.get("menu.config"), skin);
        TextButton btnLogOut = new TextButton(Textos.get("menu.logout"), skin);
 
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SeleccionNivelScreen(game, gestor));
            }
        });
 
        btnMiPerfil.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MiPerfilScreen(game, gestor));
            }
        });
 
        btnConfi.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ConfiguracionScreen(game, gestor));
            }
        });
 
        btnLogOut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gestor.logOut();
                MusicaManager.getInstance().detener();                
                game.setScreen(new FirstScreen(game, gestor));
            }
        });
 
        table.add(titulo).padBottom(40);
        table.row();
 
        table.add(btnJugar).width(250).height(40).padTop(20);
        table.row();
 
        table.add(btnMiPerfil).width(250).height(40).padTop(20);
        table.row();
 
        table.add(btnConfi).width(250).height(40).padTop(20);
        table.row();
 
        table.add(btnLogOut).width(250).height(40).padTop(20);
        table.row();
 
        Gdx.input.setInputProcessor(stage);
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
