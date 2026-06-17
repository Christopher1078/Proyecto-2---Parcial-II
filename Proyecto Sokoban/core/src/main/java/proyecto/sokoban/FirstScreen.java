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
 
public class FirstScreen implements Screen {
    
    private final Game game;
    private Stage stage;
    private Skin skin;
    private final GestorUsuarios gestor;
 
    public FirstScreen(Game game, GestorUsuarios gestor) {
        this.game = game;
        this.gestor=gestor;
    }
    
    @Override
    public void show() {
        stage=new Stage(new ScreenViewport());
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        
        Table table=new Table();
        table.setFillParent(true);
        stage.addActor(table);
        
        Label titulo=new Label(Textos.get("first.titulo"), skin);
        
        TextButton btnInicio=new TextButton(Textos.get("first.login"),skin);
        btnInicio.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new LoginScreen(game,gestor));
            }
        });
        
        TextButton btnRegistro=new TextButton(Textos.get("first.registro"),skin);
        btnRegistro.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new SigninScreen(game,gestor));
            }
        });
        
        TextButton btnSalir=new TextButton(Textos.get("first.salir"),skin);
        btnSalir.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });
        
        table.add(titulo).padBottom(40);
        table.row();
        table.add(btnInicio).width(250).height(40).padTop(20);
        table.row();
        table.add(btnRegistro).width(250).height(40).padTop(20);
        table.row();
        table.add(btnSalir).width(250).height(40).padTop(20);
        table.row();
        Gdx.input.setInputProcessor(stage);
    }
 
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        stage.act(delta);
        stage.draw();        
    }
 
    @Override
    public void resize(int width, int height) {
    }
 
    @Override
    public void pause() {
    }
 
    @Override
    public void resume() {
    }
 
    @Override
    public void hide() {
    }
 
    @Override
    public void dispose() { 
    }
}