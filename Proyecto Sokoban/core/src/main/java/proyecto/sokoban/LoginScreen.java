package proyecto.sokoban;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import static com.badlogic.gdx.graphics.g3d.particles.ParticleChannels.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoginScreen implements Screen{

    private Game game;
    private Stage stage;
    private Skin skin;
    private TextField txtUsuario;
    private TextField txtPassword;
    private TextButton btnLogin;

    public LoginScreen(Game game) {
        this.game = game;
    }
    
    
    @Override
    public void show() {
        stage=new Stage(new ScreenViewport());
        skin=new Skin(Gdx.files.internal("uiskin.json"));        
        
        Table table=new Table();
        table.setFillParent(true);
        stage.addActor(table);
        
        Label titulo=new Label("SOKOBAN",skin);
        
        txtUsuario=new TextField("",skin);
        txtUsuario.setMessageText("Usuario");
        
        txtPassword=new TextField("",skin);
        txtPassword.setPasswordMode(true);
        txtPassword.setPasswordCharacter('*');
        
        btnLogin=new TextButton("Iniciar Sesion",skin);
        
        table.add(titulo).padBottom(40);
        table.row(); 
        
        table.add(txtUsuario).width(250).height(40);
        table.row();  
        
        table.add(txtPassword).width(250).height(40).padTop(10);
        table.row();   
        
        table.add(btnLogin).width(250).height(40).padTop(20);
        table.row();
        
        btnLogin.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x, float y){
                String usuario=txtUsuario.getText();
                String password=txtPassword.getText();
                game.setScreen(new MenuScreen(game));
                dispose();            
            }
            
        });
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float f) {
        ScreenUtils.clear(0,0,0,1);
        stage.act(f);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
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
