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
    private Label lblEspecial;
    private Label lblConfirmar;
 
    public SigninScreen(Game game, GestorUsuarios gestor) {
        this.game = game;
        this.gestor=gestor;
    }
    
    @Override
    public void show() {
        stage=new Stage(new ScreenViewport());
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        CheckBox chkMostrar=new CheckBox("  "+Textos.get("login.mostrar"),skin);
 
        Table cornerTable=new Table();
        cornerTable.setFillParent(true);
        cornerTable.top().right();
        stage.addActor(cornerTable);
 
        TextButton btnIdioma=new TextButton(Textos.get("comun.idioma"),skin);
        btnIdioma.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ConfiguracionJuego config=ConfiguracionJuego.getInstance();
                config.setIdioma(config.getIdioma()==Idioma.ESPANOL ? Idioma.INGLES : Idioma.ESPANOL);
                game.setScreen(new SigninScreen(game,gestor));
            }
        });
        cornerTable.add(btnIdioma).width(60).height(35).pad(10);
 
        Table table=new Table();
        table.setFillParent(true);
        stage.addActor(table);
        
        Label titulo=new Label(Textos.get("signin.titulo"),skin);
        
        TextField txtNombre=new TextField("",skin);
        txtNombre.setMessageText(Textos.get("signin.nombre"));
        
        TextField txtUsuario=new TextField("",skin);
        txtUsuario.setMessageText(Textos.get("signin.usuario"));
        
        TextField txtPassword=new TextField("",skin);
        txtPassword.setPasswordMode(true);
        txtPassword.setPasswordCharacter('*');
        txtPassword.setMessageText("Password");
 
        TextField txtConfirmar=new TextField("",skin);
        txtConfirmar.setPasswordMode(true);
        txtConfirmar.setPasswordCharacter('*');
        txtConfirmar.setMessageText(Textos.get("signin.confirmar"));
        
        lblLongitud=new Label(Textos.get("signin.longitud"),skin);
        lblLongitud.setColor(Color.RED);
        
        lblMayuscula=new Label(Textos.get("signin.mayuscula"),skin);
        lblMayuscula.setColor(Color.RED);
        
        lblMinuscula=new Label(Textos.get("signin.minuscula"),skin);
        lblMinuscula.setColor(Color.RED);
         
        lblNumero=new Label(Textos.get("signin.numero"),skin);
        lblNumero.setColor(Color.RED);
 
        lblEspecial=new Label(Textos.get("signin.especial"),skin);
        lblEspecial.setColor(Color.RED);
 
        lblConfirmar=new Label(Textos.get("signin.noCoincide"),skin);
        lblConfirmar.setColor(Color.RED);
        lblConfirmar.setVisible(false);
        
        TextButton btnSignIn=new TextButton(Textos.get("signin.btn"),skin);
        
        TextButton btnRegresar=new TextButton(Textos.get("signin.regresar"),skin);
        
        table.add(titulo).padBottom(20);
        table.row(); 
        
        table.add(txtNombre).width(250).height(40);
        table.row();
        
        table.add(txtUsuario).width(250).height(40).padTop(10);
        table.row();  
        
        table.add(txtPassword).width(250).height(40).padTop(10);
        table.row();
 
        table.add(txtConfirmar).width(250).height(40).padTop(10);
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
 
        table.add(lblEspecial).left();
        table.row();
 
        table.add(lblConfirmar).left();
        table.row();
        
        table.add(btnSignIn).width(250).height(40).padTop(15);
        table.row();
        
        table.add(btnRegresar).width(250).height(40).padTop(10);
        table.row();
        
        btnSignIn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x, float y){
                String usuario=txtUsuario.getText();
                String password=txtPassword.getText();
                String confirmar=txtConfirmar.getText();
                String nombreCompleto=txtNombre.getText();
 
                String mensaje="";
 
                if(usuario.isBlank() || password.isBlank() || nombreCompleto.isBlank() || confirmar.isBlank()){
                    mensaje=Textos.get("signin.vacio");
                } else if(!password.equals(confirmar)){
                    mensaje=Textos.get("signin.noCoincide");
                } else if(!gestor.passwordValido(password)){
                    mensaje=Textos.get("signin.passInvalido");
                } else {
                    try {
                        if(gestor.existeUsuario(usuario)){
                            mensaje=Textos.get("signin.existe");
                        } else if(gestor.signIn(usuario, password, nombreCompleto)){
                            MusicaManager.getInstance().iniciar();
                            game.setScreen(new SeleccionarAvatarScreen(game,gestor));
                            return;
                        }
                    } catch(IOException | ClassNotFoundException e){
                        mensaje="Error: "+e.getMessage();
                    }
                }
 
                if(!mensaje.isBlank()){
                    mostrarDialog(mensaje);
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
                boolean mostrar = chkMostrar.isChecked();
                txtPassword.setPasswordMode(!mostrar);
                txtConfirmar.setPasswordMode(!mostrar);
            }
        });
        
        txtPassword.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor){                
                actualizarValidacion(txtPassword.getText(), txtConfirmar.getText());
            }            
        });
 
        txtConfirmar.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor){
                actualizarValidacion(txtPassword.getText(), txtConfirmar.getText());
            }
        });
 
        Gdx.input.setInputProcessor(stage);        
    }
    
    private void actualizarValidacion(String password, String confirmar){
        boolean mayuscula=false, minuscula=false, numero=false, longitud, especial=false;
        
        longitud = password.length() >= 6;
        
        for(char letra: password.toCharArray()){
            if(!mayuscula) mayuscula = Character.isUpperCase(letra);
            if(!minuscula) minuscula = Character.isLowerCase(letra);
            if(!numero)    numero    = Character.isDigit(letra);
            if(!especial)  especial  = !Character.isLetterOrDigit(letra);
        }
 
        lblLongitud.setColor(longitud   ? Color.GREEN : Color.RED);
        lblMayuscula.setColor(mayuscula ? Color.GREEN : Color.RED);
        lblMinuscula.setColor(minuscula ? Color.GREEN : Color.RED);
        lblNumero.setColor(numero       ? Color.GREEN : Color.RED);
        lblEspecial.setColor(especial   ? Color.GREEN : Color.RED);
 
        if(!confirmar.isEmpty()){
            boolean coinciden = password.equals(confirmar);
            lblConfirmar.setColor(coinciden ? Color.GREEN : Color.RED);
            lblConfirmar.setText(coinciden
                ? Textos.get("signin.confirmar") + " ✓"
                : Textos.get("signin.noCoincide"));
            lblConfirmar.setVisible(true);
        } else {
            lblConfirmar.setVisible(false);
        }
    }
 
    private void mostrarDialog(String mensaje){
        Dialog dialog=new Dialog(mensaje, skin){
            @Override
            protected void result(Object obj){ this.hide(); }
        };
        dialog.button(Textos.get("comun.ok"), true);
        dialog.setMovable(false);
        dialog.pack();
        dialog.show(stage);
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
