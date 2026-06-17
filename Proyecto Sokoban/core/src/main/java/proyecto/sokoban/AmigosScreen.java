package proyecto.sokoban;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
public class AmigosScreen implements Screen{
    private final Game game;
    private final GestorUsuarios gestor;
    private Stage stage;
    private Skin skin;
    private Table tablaAmigos;
    private Table tablaSolicitudes;
    private Table tablaResultados;
    private Label lblMensaje;
    private TextField txtBuscar;
 
    public AmigosScreen(Game game, GestorUsuarios gestor) {
        this.game = game;
        this.gestor = gestor;
    }
 
    @Override
    public void show() {
        stage=new Stage(new ScreenViewport());
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        
        Table table=new Table();
        table.setFillParent(true);
        table.top();
        stage.addActor(table);
        
        Label titulo=new Label(Textos.get("amigos.titulo"),skin);
        table.add(titulo);
        table.row();
        
        lblMensaje=new Label("",skin);
        table.add(lblMensaje);
        table.row();
        
        Table buscarTable=new Table();
        
        txtBuscar=new TextField("",skin);
        txtBuscar.setMessageText(Textos.get("amigos.buscar"));
        
        TextButton btnBuscar=new TextButton(Textos.get("amigos.btnBuscar"),skin);
        
        buscarTable.add(txtBuscar).width(250).height(40).padRight(5);
        buscarTable.add(btnBuscar).width(100).height(40);
        
        table.add(buscarTable);
        table.row();
        
        tablaResultados=new Table();
        ScrollPane scrollResultados=new ScrollPane(tablaResultados,skin);
        scrollResultados.setFadeScrollBars(false);
        table.add(scrollResultados).width(400).height(120);
        table.row();
        
        btnBuscar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                buscarUsuarios(txtBuscar.getText());
            }
        });
        
        Label tituloSolicitudes=new Label(Textos.get("amigos.solicitudes"),skin);
        table.add(tituloSolicitudes);
        table.row();
        
        tablaSolicitudes=new Table();
        ScrollPane scrollSolicitudes=new ScrollPane(tablaSolicitudes,skin);
        scrollSolicitudes.setFadeScrollBars(false);
        table.add(scrollSolicitudes).width(400).height(120);
        table.row();
        
        Label tituloAmigos=new Label(Textos.get("amigos.misAmigos"),skin);
        table.add(tituloAmigos);
        table.row();
        
        tablaAmigos=new Table();
        ScrollPane scrollAmigos=new ScrollPane(tablaAmigos, skin);
        scrollAmigos.setFadeScrollBars(false);
        table.add(scrollAmigos).width(400).height(150);
        table.row();
        
        TextButton btnVolver=new TextButton(Textos.get("amigos.volver"),skin);
        btnVolver.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MiPerfilScreen(game,gestor));
            }
        });
        
        table.add(btnVolver).width(200).height(40).padTop(10).padBottom(10);
        table.row();
        
        cargarAmigos();
        cargarSolicitudes();
        
        Gdx.input.setInputProcessor(stage);
    }
    
    private void buscarUsuarios(String texto){
        tablaResultados.clear();
        lblMensaje.setText("");
        
        if(texto==null || texto.isBlank()){
            tablaResultados.add(new Label(Textos.get("amigos.escribir"),skin)).pad(5);
            return;
        }
        try{
            ArrayList<Usuario> resultados=gestor.buscarUsuarioPorNombre(texto);
            if(resultados.isEmpty()){
                tablaResultados.add(new Label(Textos.get("amigos.sinResultados"),skin));
                return;
            }
            Usuario loggedIn=gestor.getLoggedIn();
            
            for(Usuario u: resultados){
                String username=u.getUsername();
                Label lbl=new Label(username,skin);
                
                String estado;
                if(loggedIn.isAmigo(username))
                    estado=Textos.get("amigos.yaAmigos");
                else if(loggedIn.tieneSolicitudesEnviadasA(username))
                    estado=Textos.get("amigos.yaEnviada");
                else if(loggedIn.tieneSolicitudesRecibidasDe(username))
                    estado=Textos.get("amigos.teEnvio");
                else 
                    estado=null;
                tablaResultados.add(lbl).left().padRight(20).pad(5);
                
                if(estado!=null)
                    tablaResultados.add(new Label(estado,skin));
                else{
                    TextButton btnAgregar=new TextButton(Textos.get("amigos.agregar"),skin);
                    btnAgregar.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y){
                            enviarSolicitud(username);
                        }
                    }); 
                    tablaResultados.add(btnAgregar).width(100).height(35).pad(5);
                }
                tablaResultados.row();
            }
        }catch(IOException | ClassNotFoundException e){
            Dialog dialog=new Dialog("Error: "+e.getMessage(),skin);
            dialog.show(stage);
        }
    }
    
    private void enviarSolicitud(String destinatario){
        try{
            String resultado=gestor.enviarSolicitudAmistad(destinatario);
            
            lblMensaje.setText(resultado);
            
            buscarUsuarios(txtBuscar.getText());
            cargarSolicitudes();
        }catch(IOException|ClassNotFoundException e){
            Dialog dialog=new Dialog("Error: "+e.getMessage(),skin);
            dialog.show(stage);
        }
    }
    
    private void cargarSolicitudes(){
        tablaSolicitudes.clear();
        
        Usuario usuario=gestor.getLoggedIn();
        ArrayList<String> solicitudes=usuario.getSolicitudesRecibidas();
        
        if(solicitudes.isEmpty()){
            tablaSolicitudes.add(new Label(Textos.get("amigos.sinSolicitudes"),skin));
            return;
        }
        
        for(String username: solicitudes){
            Label lbl=new Label(username,skin);
            TextButton btnAceptar=new TextButton(Textos.get("amigos.aceptar"),skin);
            TextButton btnRechazar=new TextButton(Textos.get("amigos.rechazar"),skin);
            
            btnAceptar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    responderSolicitud(username,true);
                }
            });
            
            btnRechazar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    responderSolicitud(username,false);
                }
            });
            
            tablaSolicitudes.add(lbl).left().padRight(20).pad(5);
            tablaSolicitudes.add(btnAceptar).width(100).height(35).padRight(10).pad(5);
            tablaSolicitudes.add(btnRechazar).width(100).height(35).pad(5);
            tablaSolicitudes.row();
        }
    }
    
    private void responderSolicitud(String remitente, boolean aceptar){
        try{
            if(aceptar){
                gestor.aceptarSolicitudAmistad(remitente);
                lblMensaje.setText(Textos.get("amigos.ahoraAmigos")+remitente);
            }
            else{
                gestor.rechazarSolicitudAmistad(remitente);
                lblMensaje.setText(Textos.get("amigos.rechazado")+remitente);
            }
            
            cargarSolicitudes();
            cargarAmigos();
            
            if(!txtBuscar.getText().isBlank())
                buscarUsuarios(txtBuscar.getText());
            
        }catch(IOException | ClassNotFoundException e){
            Dialog dialog=new Dialog("Error: "+e.getMessage(),skin);
            dialog.show(stage);
        }
    }
    
    private void cargarAmigos(){
        tablaAmigos.clear();
        
        try{
            ArrayList<String> amigos=gestor.getAmigosActivos();
        
            if(amigos.isEmpty()){
                tablaAmigos.add(new Label(Textos.get("amigos.sinAmigos"),skin)).pad(5);
                return;
            }
        
            for(String username: amigos){
                Label lbl=new Label(username,skin);
                TextButton btnEstadisticas=new TextButton(Textos.get("amigos.estadisticas"),skin);
                TextButton btnRetar=new TextButton(Textos.get("amigos.retar"),skin);
                TextButton btnEliminar=new TextButton(Textos.get("amigos.eliminar"),skin);
            
                btnEliminar.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y){
                        eliminarAmigo(username);
                    }
                });
            
                btnEstadisticas.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y){
                        verEstadisticas(username);
                    }
                });
            
                btnRetar.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y){
                        mostrarDialogoNivel(username);
                    }
                });
            
                tablaAmigos.add(lbl).left().padRight(20).pad(5);
                tablaAmigos.add(btnEliminar).width(100).height(35).pad(5);
                tablaAmigos.add(btnEstadisticas).width(110).height(35).padRight(10).pad(5);
                tablaAmigos.add(btnRetar).width(90).height(35).padRight(10).pad(5);
                tablaAmigos.row();
            }
        }catch(IOException|ClassNotFoundException e){
            tablaAmigos.add(new Label("Error: "+e.getMessage(),skin)).pad(5);
        }
    }
    
    private void eliminarAmigo(String amigo){
        try{
            gestor.eliminarAmigo(amigo);
            lblMensaje.setText(amigo+Textos.get("amigos.eliminado"));
            cargarAmigos();
        }catch(IOException | ClassNotFoundException e){
            Dialog dialog=new Dialog("Error: "+e.getMessage(),skin);
            dialog.show(stage);
        }
    }
    
    private void verEstadisticas(String username){
        try{
            Usuario amigo=gestor.buscarUsuario(username);
            game.setScreen(new EstadisticasAmigoScreen(game, gestor, amigo));
        }catch(IOException | ClassNotFoundException e){
            Dialog dialog=new Dialog("Error: "+e.getMessage(),skin);
            dialog.show(stage);
        }
    }
    
    private void mostrarDialogoNivel(String rivalUsername){
        Dialog dialogo=new Dialog(Textos.get("amigos.elegir"),skin){
            @Override
            protected void result(Object obj){
                if(obj instanceof Integer)
                    enviarReto(rivalUsername, (Integer) obj);
            }
        };
        dialogo.getContentTable().add(new Label(Textos.get("amigos.elegirNivel")+rivalUsername+"?",skin)).padTop(10).padBottom(10).padLeft(15).padRight(15);
        dialogo.getContentTable().row();
        
        
        Table botonesNivel=new Table();
        for(int n=1;n<=5;n++){
            final int nivel=n;
            TextButton btn=new TextButton(Textos.get("amigos.btnNivel")+n,skin);
            btn.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y){
                    dialogo.hide();
                    enviarReto(rivalUsername, nivel);
                }
            });
            botonesNivel.add(btn).width(100).height(35).pad(5);
        }
        
        TextButton btnAleatorio=new TextButton(Textos.get("amigos.nivelAleatorio"),skin);
        btnAleatorio.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                dialogo.hide();
                int nivelAleatorio=new Random().nextInt(5)+1;
                enviarReto(rivalUsername, nivelAleatorio);
            }
        });
        botonesNivel.add(btnAleatorio).width(110).height(35).pad(5);
        
        dialogo.getContentTable().add(botonesNivel).padBottom(10);
        dialogo.button("Textos.get(\"amigos.retoCancelar\")");
        dialogo.setMovable(false);
        dialogo.show(stage);
    }
    
    private void enviarReto(String rival, int nivel){
        try{
            String resultado=gestor.enviarRetoDuelo(rival, nivel);
            
            if(resultado.equals("OK"))
                lblMensaje.setText(Textos.get("amigos.enviado")+rival+Textos.get("amigos.enNivel")+nivel);
            else
                lblMensaje.setText(resultado);
        }catch(Exception e){
            lblMensaje.setText("Error: "+e.getMessage());
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
        stage.dispose();
        skin.dispose();
    }
    
    
}
