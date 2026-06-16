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
        
        Label titulo=new Label("Amigos",skin);
        table.add(titulo);
        table.row();
        
        lblMensaje=new Label("",skin);
        table.add(lblMensaje);
        table.row();
        
        Table buscarTable=new Table();
        
        txtBuscar=new TextField("",skin);
        txtBuscar.setMessageText("Buscar usuario...");
        
        TextButton btnBuscar=new TextButton("Buscar",skin);
        
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
        
        Label tituloSolicitudes=new Label("Solicitudes recibidas",skin);
        table.add(tituloSolicitudes);
        table.row();
        
        tablaSolicitudes=new Table();
        ScrollPane scrollSolicitudes=new ScrollPane(tablaSolicitudes,skin);
        scrollSolicitudes.setFadeScrollBars(false);
        table.add(scrollSolicitudes).width(400).height(120);
        table.row();
        
        Label tituloAmigos=new Label("Mis amigos",skin);
        table.add(tituloAmigos);
        table.row();
        
        tablaAmigos=new Table();
        ScrollPane scrollAmigos=new ScrollPane(tablaAmigos, skin);
        scrollAmigos.setFadeScrollBars(false);
        table.add(scrollAmigos).width(400).height(150);
        table.row();
        
        TextButton btnVolver=new TextButton("Volver",skin);
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
            tablaResultados.add(new Label("Escribe un nombre de usuario para buscar",skin)).pad(5);
            return;
        }
        try{
            ArrayList<Usuario> resultados=gestor.buscarUsuarioPorNombre(texto);
            if(resultados.isEmpty()){
                tablaResultados.add(new Label("No se encontraron coincidencias",skin));
                return;
            }
            Usuario loggedIn=gestor.getLoggedIn();
            
            for(Usuario u: resultados){
                String username=u.getUsername();
                Label lbl=new Label(username,skin);
                
                String estado;
                if(loggedIn.isAmigo(username))
                    estado="Ya son amigos";
                else if(loggedIn.tieneSolicitudesEnviadasA(username))
                    estado="Ya hay una solicitud enviada";
                else if(loggedIn.tieneSolicitudesRecibidasDe(username))
                    estado="Te envio solicitud";
                else 
                    estado=null;
                tablaResultados.add(lbl).left().padRight(20).pad(5);
                
                if(estado!=null)
                    tablaResultados.add(new Label(estado,skin));
                else{
                    TextButton btnAgregar=new TextButton("Agregar",skin);
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
            Dialog dialog=new Dialog("Error al buscar usuarios: "+e.getMessage(),skin);
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
            Dialog dialog=new Dialog("Error al enviar solicitud: "+e.getMessage(),skin);
            dialog.show(stage);
        }
    }
    
    private void cargarSolicitudes(){
        tablaSolicitudes.clear();
        
        Usuario usuario=gestor.getLoggedIn();
        ArrayList<String> solicitudes=usuario.getSolicitudesRecibidas();
        
        if(solicitudes.isEmpty()){
            tablaSolicitudes.add(new Label("No tienes solicitudes pendientes",skin));
            return;
        }
        
        for(String username: solicitudes){
            Label lbl=new Label(username,skin);
            TextButton btnAceptar=new TextButton("Aceptar",skin);
            TextButton btnRechazar=new TextButton("Rechazar",skin);
            
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
                lblMensaje.setText("Ahora eres amigo de "+remitente);
            }
            else{
                gestor.rechazarSolicitudAmistad(remitente);
                lblMensaje.setText("Solicitud de "+remitente+" rechazada");
            }
            
            cargarSolicitudes();
            cargarAmigos();
            
            if(!txtBuscar.getText().isBlank())
                buscarUsuarios(txtBuscar.getText());
            
        }catch(IOException | ClassNotFoundException e){
            Dialog dialog=new Dialog("Error al procesar solicitud: "+e.getMessage(),skin);
            dialog.show(stage);
        }
    }
    
    private void cargarAmigos(){
        tablaAmigos.clear();
        
        try{
            ArrayList<String> amigos=gestor.getAmigosActivos();
        
            if(amigos.isEmpty()){
                tablaAmigos.add(new Label("Aun no tienes amigos",skin)).pad(5);
                return;
            }
        
            for(String username: amigos){
                Label lbl=new Label(username,skin);
                TextButton btnEstadisticas=new TextButton("Estadisticas",skin);
                TextButton btnRetar=new TextButton("Retar",skin);
                TextButton btnEliminar=new TextButton("Eliminar",skin);
            
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
                    
                    }
                });
            
                tablaAmigos.add(lbl).left().padRight(20).pad(5);
                tablaAmigos.add(btnEliminar).width(100).height(35).pad(5);
                tablaAmigos.add(btnEstadisticas).width(110).height(35).padRight(10).pad(5);
                tablaAmigos.add(btnRetar).width(90).height(35).padRight(10).pad(5);
                tablaAmigos.row();
            }
        }catch(IOException|ClassNotFoundException e){
            tablaAmigos.add(new Label("Error al cargar amigos: "+e.getMessage(),skin)).pad(5);
        }
    }
    
    private void eliminarAmigo(String amigo){
        try{
            gestor.eliminarAmigo(amigo);
            lblMensaje.setText(amigo+" fue eliminado de tu lista de amigos");
            cargarAmigos();
        }catch(IOException | ClassNotFoundException e){
            Dialog dialog=new Dialog("Error al eliminar amigo: "+e.getMessage(),skin);
            dialog.show(stage);
        }
    }
    
    private void verEstadisticas(String username){
        try{
            Usuario amigo=gestor.buscarUsuario(username);
            game.setScreen(new EstadisticasAmigoScreen(game, gestor, amigo));
        }catch(IOException | ClassNotFoundException e){
            Dialog dialog=new Dialog("Error al ver estadisticas: "+e.getMessage(),skin);
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
        stage.dispose();
        skin.dispose();
    }
    
    
}
