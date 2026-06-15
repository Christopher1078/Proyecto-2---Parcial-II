package proyecto.sokoban;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
public class MiPerfilScreen implements Screen{
    private final Game game;
    private final GestorUsuarios gestor;
    private Stage stage;
    private Skin skin;
    private Texture avatarTexture;

    public MiPerfilScreen(Game game, GestorUsuarios gestor) {
        this.game = game;
        this.gestor = gestor;
    }

    @Override
    public void show() {
        stage=new Stage(new ScreenViewport());
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        Usuario usuario=gestor.getLoggedIn();
        
        Table table=new Table();
        table.setFillParent(true);
        table.top();
        stage.addActor(table);
        
        Label titulo=new Label("Mi Perfil",skin);
        table.add(titulo).padTop(10).padBottom(10).colspan(2);
        table.row();
        
        String rutaAvatar=usuario.getGenero().getRuta()+"1.PNG";
        avatarTexture=new Texture(rutaAvatar);
        Image avatar=new Image(avatarTexture);
        
        Table datosTable=new Table();
        Label lblUsuario=new Label("Username: "+usuario.getUsername(),skin);
        Label lblNombre=new Label("Nombre: "+usuario.getNombreCompleto(),skin);
        Label lblAmigos=new Label("Amigos: "+usuario.getAmigos().size(),skin);
        
        datosTable.add(lblUsuario).left();
        datosTable.row();
        
        datosTable.add(lblNombre).left().padTop(5);
        datosTable.row();
        
        datosTable.add(lblAmigos).left().padTop(5);
        
        table.add(avatar).size(100,200).padRight(30);
        table.add(datosTable).left();
        table.row();
        
        TextButton btnCambiarAvatar=new TextButton("Cambiar Avatar",skin);
        TextButton btnVerAmigos=new TextButton("Ver Amigos",skin);
        
        Table botonesTable=new Table();
        botonesTable.add(btnCambiarAvatar).width(180).height(40).padRight(20);
        botonesTable.add(btnVerAmigos).width(180).height(40);
        
        table.add(botonesTable).padTop(20).padBottom(30).colspan(2);
        table.row();
        
        btnCambiarAvatar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new SeleccionarAvatarScreen(game,gestor));
            }
        });
        
        btnVerAmigos.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new AmigosScreen(game,gestor));
            }
        });
        
        Label tituloStats=new Label("Estadisticas",skin);
        table.add(tituloStats).padBottom(10).colspan(2);
        table.row();
        
        Table statsTable=new Table();
        
        long tiempoTotalSegundos=usuario.getTiempoJugado();
        long horas=tiempoTotalSegundos/3600;
        long minutos=(tiempoTotalSegundos%3600)/60;
        String tiempoTotal=horas>0? horas+"h "+minutos+"m" : minutos+"m "+(tiempoTotalSegundos%60)+"s";
        
        statsTable.add(new Label("Partidas jugadas: "+usuario.getPartidasJugadas(),skin)).left();
        statsTable.row();
        
        statsTable.add(new Label("Niveles completados: "+usuario.getNivelesCompletados(),skin)).left().padTop(5);
        statsTable.row();
        
        statsTable.add(new Label("Tiempo total: "+tiempoTotal,skin)).left().padTop(5);
        statsTable.row();
        
        statsTable.add(new Label("Mejor tiempo: "+usuario.getMejorTiempo(),skin)).left().padTop(5);
        statsTable.row();
                
        table.add(statsTable).colspan(2);
        table.row();
        
        TextButton btnVolver=new TextButton("Volver",skin);
        btnVolver.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MenuScreen(game,gestor));
            }
        });
        
        table.add(btnVolver).width(200).height(40).padTop(30).colspan(2);
        
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float f) {
        ScreenUtils.clear(0, 0, 0, 1);
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
