package proyecto.sokoban;
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
public class SeleccionarAvatarScreen implements Screen{
    
    private final Game game;
    private Stage stage;
    private Skin skin;
    private GestorUsuarios gestor;
    private Texture AvatarMasculino;
    private Texture AvatarFemenino;

    public SeleccionarAvatarScreen(Game game, GestorUsuarios gestor) {
        this.game = game;
        this.gestor = gestor;
    }

    @Override
    public void show() {
        stage=new Stage(new ScreenViewport());
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        AvatarMasculino=new Texture("Avatares/Avatar1_pos1.PNG");
        AvatarFemenino=new Texture("Avatares/Avatar2_pos1.PNG");
        
        Table table=new Table();
        table.setFillParent(true);
        stage.addActor(table);
        
        Label titulo=new Label("Elige tu avatar",skin);
        Image imgMasculino=new Image(AvatarMasculino);
        Image imgFemenino=new Image(AvatarFemenino);
        
        TextButton btnMasculino=new TextButton("Masculino",skin);
        TextButton btnFemenino=new TextButton("Femenino",skin);
        
        table.add(titulo).padBottom(40).colspan(2);
        table.row();
        
        table.add(imgMasculino).size(100,200).padRight(40);
        table.add(imgFemenino).size(100,200);
        table.row();
        
        table.add(btnMasculino).width(150).height(40).padTop(10);
        table.add(btnFemenino).width(150).height(40).padTop(10);
        table.row();
        
        btnMasculino.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                seleccionarAvatar(GeneroAvatar.MASCULINO);
            }
        });
        
        btnFemenino.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                seleccionarAvatar(GeneroAvatar.FEMENINO);
            }
        });
        Gdx.input.setInputProcessor(stage);
    }
    
    private void seleccionarAvatar(GeneroAvatar genero){
        try{
            Usuario usuario=gestor.getLoggedIn();
            usuario.setGenero(genero);
            gestor.guardarUsuario(usuario);
            game.setScreen(new MenuScreen(game,gestor));
        }catch(IOException | ClassNotFoundException e){
            Dialog dialog=new Dialog("Error al seleccionar avatar: "+e.getMessage(),skin);
            dialog.show(stage);            
        }
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
        AvatarMasculino.dispose();
        AvatarFemenino.dispose();
    }
    
}
