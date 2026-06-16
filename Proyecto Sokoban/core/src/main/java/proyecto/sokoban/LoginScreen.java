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

public class LoginScreen implements Screen{

    private final Game game;
    private Stage stage;
    private Skin skin;
    private final GestorUsuarios gestor;

    public LoginScreen(Game game, GestorUsuarios gestor) {
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
        
        Label titulo=new Label("Inicio de Sesion",skin);
        
        TextField txtUsuario=new TextField("",skin);
        txtUsuario.setMessageText("Usuario");
        
        TextField txtPassword=new TextField("",skin);
        txtPassword.setPasswordMode(true);
        txtPassword.setPasswordCharacter('*');
        
        TextButton btnLogin=new TextButton("Iniciar Sesion",skin);
        
        TextButton btnRegresar=new TextButton("Regresar",skin);
        
        table.add(titulo).padBottom(40);
        table.row(); 
        
        table.add(txtUsuario).width(250).height(40);
        table.row();  
        
        table.add(txtPassword).width(250).height(40).padTop(10);
        table.row();   
        
        table.add(chkMostrar).left().padTop(5);
        table.row();        
        
        table.add(btnLogin).width(250).height(40).padTop(20);
        table.row();
        
        table.add(btnRegresar).width(250).height(40).padTop(10);
        table.row();
        
        btnLogin.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x, float y){
                String usuario=txtUsuario.getText();
                String password=txtPassword.getText();
                try{
                    if(gestor.logIn(usuario, password))
                        game.setScreen(new MenuScreen(game,gestor));
                     else{
                        Dialog dialog;
                        String mensaje;
                        if(usuario.isBlank() || password.isBlank()){
                            mensaje="Parametros en blanco";
                            dialog=new Dialog(mensaje, skin){
                                @Override
                                protected void result(Object obj){
                                    this.hide();
                                }
                            };
                            dialog.button("Ok",true);
                            dialog.setMovable(false);
                            dialog.pack();
                            dialog.show(stage);
                        }
                        else if(!gestor.existeUsuario(usuario)){
                            mensaje="No existe este nombre de usuario";
                            dialog=new Dialog(mensaje,skin){
                                @Override
                                protected void result(Object obj){
                                    this.hide();
                                }
                            };
                            dialog.button("Ok",true);
                            dialog.setMovable(false);
                            dialog.pack();
                            dialog.show(stage);
                        }
                        else{
                            try{
                                Usuario u=gestor.buscarUsuario(usuario);
                                if(u.isCuentaDeshabilitada() && u.getPassword().equals(password)){
                                    Dialog dialogReactivar=new Dialog("Cuenta Deshabilitada",skin){
                                        @Override
                                        protected void result(Object obj){
                                            if((boolean)obj){
                                                try{
                                                    gestor.reactivarCuenta(usuario, password);
                                                    game.setScreen(new MenuScreen(game,gestor));
                                                }catch(Exception ex){
                                                    new Dialog("Error al reactivar: "+ex.getMessage(),skin).button("OK").show(stage);
                                                }
                                            }
                                        }
                                    };
                                    dialogReactivar.text("Tu cuenta esta deshabilitada \nDeseas reactivarla?");
                                    dialogReactivar.button("Cancelar",false);
                                    dialogReactivar.button("Reactivar",true);
                                    dialogReactivar.setMovable(false);
                                    dialogReactivar.pack();
                                    dialogReactivar.show(stage);
                                }
                                else{
                                    mensaje="Password incorrecto";
                                    dialog=new Dialog(mensaje,skin){
                                        @Override
                                        protected void result(Object obj){
                                            this.hide();
                                        }
                                    };
                                    dialog.button("Ok",true);
                                    dialog.setMovable(false);
                                    dialog.pack();
                                    dialog.show(stage);
                                }
                            }catch(Exception ex){
                                dialog=new Dialog("Password incorrecto",skin){
                                    @Override
                                    protected void result(Object obj){
                                        this.hide();
                                    }
                                };
                                dialog.button("Ok",true);
                                dialog.setMovable(false);
                                dialog.pack();
                                dialog.show(stage);
                            }
                        }
                    }
                }catch(IOException|ClassNotFoundException e){
                    Dialog dialog=new Dialog("Hubo un error al iniciar sesion: "+e.getMessage(),skin);
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
