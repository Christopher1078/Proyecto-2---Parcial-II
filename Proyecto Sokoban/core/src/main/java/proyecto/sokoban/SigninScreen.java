package proyecto.sokoban;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.io.IOException;
import com.badlogic.gdx.graphics.Color;
public class SigninScreen implements Screen {
    
    private final Game game;
    private Stage stage;
    private Skin skin;
    private final GestorUsuarios gestor;
    private Label lblLongitud;
    private Label lblMayuscula;
    private Label lblMinuscula;
    private Label lblNumero;

    public SigninScreen(Game game, GestorUsuarios gestor) {
        this.game = game;
        this.gestor=gestor;
    }
    
    @Override
    public void show() {
        stage=new Stage(new ScreenViewport());
        skin=new Skin(Gdx.files.internal("uiskin.json"));        
        CheckBox chkMostrar=new CheckBox("  Mostrar password",skin);
        
        Table table=new Table();
        table.setFillParent(true);
        stage.addActor(table);
        
        Label titulo=new Label("Registrarse",skin);
        
        TextField txtNombre=new TextField("",skin);
        txtNombre.setMessageText("Nombre Completo");
        
        TextField txtUsuario=new TextField("",skin);
        txtUsuario.setMessageText("Usuario");
        
        TextField txtPassword=new TextField("",skin);
        txtPassword.setPasswordMode(true);
        txtPassword.setPasswordCharacter('*');
        
        lblLongitud=new Label("Exactamente 5 caracteres",skin);
        lblLongitud.setColor(Color.RED);
        
        lblMayuscula=new Label("Al menos una mayuscula",skin);
        lblMayuscula.setColor(Color.RED);
        
        lblMinuscula=new Label("Al menos una minuscula",skin);
        lblMinuscula.setColor(Color.RED);
         
        lblNumero=new Label("Al menos un numero",skin);
        lblNumero.setColor(Color.RED);
        
        TextButton btnSignIn=new TextButton("Registrarse",skin);
        
        TextButton btnRegresar=new TextButton("Regresar",skin);
        
        table.add(titulo).padBottom(40);
        table.row(); 
        
        table.add(txtNombre).width(250).height(40);
        table.row();
        
        table.add(txtUsuario).width(250).height(40).padTop(10);
        table.row();  
        
        table.add(txtPassword).width(250).height(40).padTop(10);
        table.row();       
        
        table.add(chkMostrar).left().padTop(5);
        table.row();
        
        table.add(lblLongitud).left();
        table.row();
        
        table.add(lblMayuscula).left();
        table.row();
        
        table.add(lblMinuscula).left();
        table.row();
        
        table.add(lblNumero).left();
        table.row();
        
        table.add(btnSignIn).width(250).height(40).padTop(20);
        table.row();
        
        table.add(btnRegresar).width(250).height(40).padTop(10);
        table.row();
        
        btnSignIn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x, float y){
                String usuario=txtUsuario.getText();
                String password=txtPassword.getText();
                String nombreCompleto=txtNombre.getText();
                try {
                    if(gestor.signIn(usuario, password, nombreCompleto)){
                        MusicaManager.getInstance().iniciar();
                        game.setScreen(new SeleccionarAvatarScreen(game,gestor));
                    }
                    else {
                        Dialog dialog;
                        String mensaje="";
                        if(usuario.isBlank() || password.isBlank() || nombreCompleto.isBlank()){
                            mensaje="Parametros en blanco";
                        }
                        else if(gestor.existeUsuario(usuario)){
                            mensaje="Ya existe ese nombre de usuario";
                        }
                        else if(!gestor.passwordValido(password)){
                            mensaje="Password no valido";
                        }
                        if(!mensaje.isBlank()){
                            dialog=new Dialog(mensaje, skin){
                                @Override
                                protected void result(Object obj){
                                    this.hide();
                                }
                            };
                            dialog.show(stage);
                            dialog.button("Ok", true);
                            dialog.setSize(400, 200);
                            dialog.invalidate();
                            dialog.pack();
                            dialog.setMovable(false);
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    Dialog dialog=new Dialog("Error al iniciar sesion: "+e.getMessage(),skin);
                    dialog.show(stage);
                }
            }
            
        });
        
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new FirstScreen(game,gestor));
                
            }
        });
        
        chkMostrar.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor){
                if(chkMostrar.isChecked())
                    txtPassword.setPasswordMode(false);
                else txtPassword.setPasswordMode(true);
            }
        });
        
        txtPassword.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor){                
                actualizarValidacion(txtPassword.getText());
            }            
        });
        Gdx.input.setInputProcessor(stage);        
    }
    
    private void actualizarValidacion(String password){
        boolean mayuscula=false,minuscula=false,numero=false,longitud;
        
        longitud=password.length()==5;
        
        for(char letra: password.toCharArray()){
            if(!mayuscula)
                mayuscula=Character.isUpperCase(letra);
            if(!minuscula)
                minuscula=Character.isLowerCase(letra);
            if(!numero)
                numero=Character.isDigit(letra);
        }

        if(longitud)
            lblLongitud.setColor(Color.GREEN);
        else lblLongitud.setColor(Color.RED);
        
        if(mayuscula)
            lblMayuscula.setColor(Color.GREEN);
        else lblMayuscula.setColor(Color.RED);
        
        if(minuscula)
            lblMinuscula.setColor(Color.GREEN);
        else lblMinuscula.setColor(Color.RED);
        
        if(numero)
            lblNumero.setColor(Color.GREEN);
        else lblNumero.setColor(Color.RED);

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
