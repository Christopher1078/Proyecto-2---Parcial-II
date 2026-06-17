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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import proyecto.sokoban.datos.GestorUsuarios;
import proyecto.sokoban.datos.Usuario;
import proyecto.sokoban.utilidad.Textos;
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
        
        Label titulo=new Label(Textos.get("perfil.titulo"),skin);
        table.add(titulo).padTop(5).padBottom(5).colspan(2);
        table.row();
        
        String rutaAvatar=usuario.getGenero().getRuta()+"1.PNG";
        avatarTexture=new Texture(rutaAvatar);
        Image avatar=new Image(avatarTexture);
        
        Table datosTable=new Table();
        Label lblUsuario=new Label(Textos.get("perfil.username")+usuario.getUsername(),skin);
        Label lblNombre=new Label(Textos.get("perfil.nombre")+usuario.getNombreCompleto(),skin);
        Label lblAmigos=new Label(Textos.get("perfil.amigos")+usuario.getAmigos().size(),skin);
        Label lblFechas=new Label(Textos.get("perfil.registro")+usuario.getFechaRegistro()+"  |  "+Textos.get("perfil.ultimaSesion")+usuario.getUltimaSesion(),skin);
        
        datosTable.add(lblUsuario).left();
        datosTable.row();
        
        datosTable.add(lblNombre).left().padTop(5);
        datosTable.row();
        
        datosTable.add(lblAmigos).left().padTop(5);
        datosTable.row();
        
        datosTable.add(lblFechas).left().padTop(5);
        
        table.add(avatar).size(100,200).padRight(30).padLeft(200);
        table.add(datosTable).left();
        table.row();
        
        TextButton btnCambiarAvatar=new TextButton(Textos.get("perfil.cambiarAvatar"),skin);
        TextButton btnVerAmigos=new TextButton(Textos.get("perfil.verAmigos"),skin);
        TextButton btnHistorial=new TextButton(Textos.get("perfil.historial"),skin);
        TextButton btnDuelos=new TextButton(Textos.get("perfil.duelos"),skin);
        
        Table botonesTable=new Table();
        botonesTable.add(btnCambiarAvatar).width(180).height(40).padRight(20);
        botonesTable.add(btnVerAmigos).width(180).height(40).padRight(20);
        
        table.add(botonesTable).padTop(5).padBottom(10).colspan(2);
        table.row();
        
        Table botonesTable2=new Table();
        botonesTable2.add(btnHistorial).width(180).height(40).padRight(20);
        botonesTable2.add(btnDuelos).width(180).height(40);
        
        table.add(botonesTable2).padBottom(15).colspan(2);
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
        
        btnHistorial.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new HistoriaScreen(game,gestor));
            }
        });
        
        btnDuelos.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new DuelosScreen(game,gestor));
            }
        });
        
        Label tituloStats=new Label(Textos.get("perfil.estadisticas"),skin);
        table.add(tituloStats).padBottom(5).colspan(2);
        table.row();
        
        Table statsTable=new Table();
        
        long tiempoTotalSegundos=usuario.getTiempoJugado();
        long horas=tiempoTotalSegundos/3600;
        long minutos=(tiempoTotalSegundos%3600)/60;
        String tiempoTotal=horas>0? horas+"h "+minutos+"m" : minutos+"m "+(tiempoTotalSegundos%60)+"s";
        
        statsTable.add(new Label(Textos.get("perfil.partidas")+usuario.getPartidasJugadas(),skin)).left();
        statsTable.row();
        
        statsTable.add(new Label(Textos.get("perfil.niveles")+usuario.getNivelesCompletados(),skin)).left();
        statsTable.row();
        
        statsTable.add(new Label(Textos.get("perfil.tiempoTotal")+tiempoTotal,skin)).left();
        statsTable.row();
        
        statsTable.add(new Label(Textos.get("perfil.mejorTiempo")+usuario.getMejorTiempo(),skin)).left();
        statsTable.row();
 
        statsTable.add(new Label(Textos.get("perfil.tiempoPromedio")+usuario.getTiempoPromedioGlobal(),skin)).left();
        statsTable.row();
                
        table.add(statsTable).colspan(2);
        table.row();
        
        TextButton btnCambiarUser=new TextButton(Textos.get("perfil.cambiarUsername"),skin);
        TextButton btnCambiarPass=new TextButton(Textos.get("perfil.cambiarPassword"),skin);
        TextButton btnDeshabilitar=new TextButton(Textos.get("perfil.deshabilitar"),skin);
        TextButton btnEliminar=new TextButton(Textos.get("perfil.eliminar"),skin);
 
        btnCambiarUser.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Dialog dialogo=new Dialog(Textos.get("perfil.cambiarUsername"),skin){
                    @Override
                    protected void result(Object obj){}
                };
                TextField txtNuevoUser=new TextField("",skin);
                txtNuevoUser.setMessageText(Textos.get("perfil.usernameActual"));
                dialogo.getContentTable().add(txtNuevoUser).width(220).height(35).pad(10);
                TextButton btnOk=new TextButton(Textos.get("perfil.cambiarBtn"),skin);
                btnOk.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y){
                        try{
                            String resultado=gestor.cambiarUsername(txtNuevoUser.getText().trim());
                            dialogo.hide();
                            switch(resultado){
                                case "OK":
                                    mostrarInfo(Textos.get("perfil.ok.username"));
                                    game.setScreen(new MiPerfilScreen(game,gestor));
                                    break;
                                case "VACIO":   mostrarInfo(Textos.get("perfil.err.vacio"));   break;
                                case "EXISTE":  mostrarInfo(Textos.get("perfil.err.existe"));  break;
                                default:        mostrarInfo(Textos.get("comun.error"));         break;
                            }
                        }catch(Exception e){
                            mostrarInfo("Error: "+e.getMessage());
                        }
                    }
                });
                dialogo.button(Textos.get("perfil.cancelarBtn"));
                dialogo.getButtonTable().add(btnOk).width(120).height(35).padLeft(10);
                dialogo.setMovable(false);
                dialogo.show(stage);
            }
        });
 
        btnCambiarPass.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Dialog dialogo=new Dialog(Textos.get("perfil.cambiarPassword"),skin){
                    @Override
                    protected void result(Object obj){}
                };
                TextField txtActual=new TextField("",skin);
                txtActual.setPasswordMode(true); txtActual.setPasswordCharacter('*');
                txtActual.setMessageText(Textos.get("perfil.passActual"));
 
                TextField txtNuevo=new TextField("",skin);
                txtNuevo.setPasswordMode(true); txtNuevo.setPasswordCharacter('*');
                txtNuevo.setMessageText(Textos.get("perfil.passNuevo"));
 
                TextField txtConfirmar=new TextField("",skin);
                txtConfirmar.setPasswordMode(true); txtConfirmar.setPasswordCharacter('*');
                txtConfirmar.setMessageText(Textos.get("perfil.passConfirmar"));
 
                Table ct=dialogo.getContentTable();
                ct.add(txtActual).width(220).height(35).pad(5); ct.row();
                ct.add(txtNuevo).width(220).height(35).pad(5);  ct.row();
                ct.add(txtConfirmar).width(220).height(35).pad(5);
 
                TextButton btnOk=new TextButton(Textos.get("perfil.cambiarBtn"),skin);
                btnOk.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y){
                        try{
                            String resultado=gestor.cambiarPassword(
                                txtActual.getText(), txtNuevo.getText(), txtConfirmar.getText());
                            dialogo.hide();
                            switch(resultado){
                                case "OK":          mostrarInfo(Textos.get("perfil.ok.password"));    break;
                                case "VACIO":       mostrarInfo(Textos.get("perfil.err.vacio"));       break;
                                case "INCORRECTO":  mostrarInfo(Textos.get("perfil.err.incorrecto"));  break;
                                case "INVALIDO":    mostrarInfo(Textos.get("perfil.err.invalido"));    break;
                                case "NO_COINCIDE": mostrarInfo(Textos.get("perfil.err.noCoincide"));  break;
                                default:            mostrarInfo(Textos.get("comun.error"));             break;
                            }
                        }catch(Exception e){
                            mostrarInfo("Error: "+e.getMessage());
                        }
                    }
                });
                dialogo.button(Textos.get("perfil.cancelarBtn"));
                dialogo.getButtonTable().add(btnOk).width(120).height(35).padLeft(10);
                dialogo.setMovable(false);
                dialogo.show(stage);
            }
        });
 
        btnDeshabilitar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Dialog dialogo=new Dialog(Textos.get("perfil.deshabilitar"),skin){
                    @Override
                    protected void result(Object obj){
                        if((boolean)obj){
                            try{
                                gestor.deshabilitarCuenta();
                                game.setScreen(new FirstScreen(game,gestor));
                            }catch(Exception e){
                                new Dialog(Textos.get("comun.error"),skin).button(Textos.get("comun.ok")).show(stage);
                            }
                        }
                    }
                };
                dialogo.text(Textos.get("perfil.deshabConfirm"));
                dialogo.button(Textos.get("perfil.cancelarBtn"),false);
                dialogo.button(Textos.get("perfil.deshabBtn"),true);
                dialogo.setMovable(false);
                dialogo.show(stage);
            }
        });
        
        btnEliminar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Dialog dialogo=new Dialog(Textos.get("perfil.eliminar"),skin){
                    @Override
                    protected void result(Object obj){
                        if((boolean) obj){
                            try{
                                gestor.eliminarCuenta();
                                game.setScreen(new FirstScreen(game,gestor));
                            }catch(Exception e){
                                new Dialog(Textos.get("comun.error"),skin).button(Textos.get("comun.ok")).show(stage);
                            }
                        }
                    }
                };
                dialogo.text(Textos.get("perfil.elimConfirm"));
                dialogo.button(Textos.get("perfil.cancelarBtn"),false);
                dialogo.button(Textos.get("perfil.elimBtn"),true);
                dialogo.setMovable(false);
                dialogo.show(stage);
            }
        });
        
        Table botonesGestion=new Table();
        botonesGestion.add(btnCambiarUser).width(190).height(35).padRight(10);
        botonesGestion.add(btnCambiarPass).width(190).height(35).padRight(10);
        botonesGestion.add(btnDeshabilitar).width(190).height(35).padRight(10);
        botonesGestion.add(btnEliminar).width(190).height(35);
        table.add(botonesGestion).padTop(8).padBottom(8).colspan(2);
        table.row();
        
        TextButton btnVolver=new TextButton(Textos.get("perfil.volver"),skin);
        btnVolver.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MenuScreen(game,gestor));
            }
        });
        
        table.add(btnVolver).width(200).height(40).padTop(5).colspan(2);
        
        Gdx.input.setInputProcessor(stage);
    }
 
    private void mostrarInfo(String mensaje){
        Dialog d=new Dialog(mensaje,skin){
            @Override
            protected void result(Object obj){ this.hide(); }
        };
        d.button(Textos.get("comun.ok"),true);
        d.setMovable(false);
        d.pack();
        d.show(stage);
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
