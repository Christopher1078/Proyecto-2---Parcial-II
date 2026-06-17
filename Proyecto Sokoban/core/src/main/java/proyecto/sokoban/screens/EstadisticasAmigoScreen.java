package proyecto.sokoban.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.Random;
import proyecto.sokoban.datos.GestorUsuarios;
import proyecto.sokoban.datos.Usuario;
import proyecto.sokoban.utilidad.Textos;
public class EstadisticasAmigoScreen implements Screen{
    
    private final Game game;
    private final GestorUsuarios gestor;
    private final Usuario amigo;
    private Stage stage;
    private Skin skin;
 
    public EstadisticasAmigoScreen(Game game, GestorUsuarios gestor, Usuario amigo) {
        this.game = game;
        this.gestor = gestor;
        this.amigo = amigo;
    }
 
    @Override
    public void show() {
        stage=new Stage(new ScreenViewport());
        skin=new Skin(Gdx.files.internal(("uiskin.json")));
        Usuario yo=gestor.getLoggedIn();
        
        Table table=new Table();
        table.setFillParent(true);
        table.top();
        stage.addActor(table);
        
        Label titulo=new Label(Textos.get("estAmigo.titulo"),skin);
        table.add(titulo).padTop(15).padBottom(20).colspan(3);
        table.row();
        
        table.add(new Label(Textos.get("estAmigo.columna"),skin)).left().padRight(30).padBottom(10);
        table.add(new Label(Textos.get("estAmigo.tu")+yo.getUsername()+")",skin)).padRight(30).padBottom(10);
        table.add(new Label(amigo.getUsername(),skin)).padBottom(10);
        table.row();
        
        agregarFila(table, Textos.get("estAmigo.partidas"), String.valueOf(yo.getPartidasJugadas()),String.valueOf(amigo.getPartidasJugadas()));
        agregarFila(table, Textos.get("estAmigo.niveles"),String.valueOf(yo.getNivelesCompletados()),String.valueOf(amigo.getNivelesCompletados()));
        agregarFila(table, Textos.get("estAmigo.tiempoTotal"), formatearTiempoTotal(yo.getTiempoJugado()), formatearTiempoTotal(amigo.getTiempoJugado()));
        agregarFila(table, Textos.get("estAmigo.mejorTiempo"), yo.getMejorTiempo(), amigo.getMejorTiempo());
        
        TextButton btnRetar=new TextButton(Textos.get("estAmigo.retar"),skin);
        btnRetar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mostrarDialogoNivel();
            }
        });
        table.add(btnRetar).width(220).height(40).padTop(30).colspan(3);
        table.row();
        
        TextButton btnVolver=new TextButton(Textos.get("estAmigo.volver"),skin);
        btnVolver.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new AmigosScreen(game,gestor));
            }
        });
        table.add(btnVolver).width(200).height(40).padTop(15).padBottom(20).colspan(3);
        Gdx.input.setInputProcessor(stage);
    }
    
    private void agregarFila(Table table, String etiqueta, String valorPropio, String valorAmigo){
        table.add(new Label(etiqueta+":",skin)).left().padRight(30).padTop(5);
        table.add(new Label(valorPropio, skin)).padRight(30).padTop(5);
        table.add(new Label(valorAmigo, skin)).padTop(5);
        table.row();
    }
    
    private String formatearTiempoTotal(long tiempoTotalSegundos){
        long horas=tiempoTotalSegundos/3600;
        long minutos=(tiempoTotalSegundos%3600)/60;
        
        if(horas>0)
            return horas+"h "+minutos+"m";
        return minutos+"m "+(tiempoTotalSegundos%60)+"s";
    }
    
    private void mostrarDialogoNivel(){
        Dialog dialogo=new Dialog(Textos.get("estAmigo.elegir"),skin);
        dialogo.getContentTable().add(new Label(Textos.get("amigos.elegirNivel")+amigo.getUsername()+"?",skin)).padTop(10).padBottom(10).padLeft(15).padRight(15);
        
        Table botonesNivel=new Table();
        for(int n=1;n<=5;n++){
            final int nivel=n;
            TextButton btn=new TextButton(Textos.get("amigos.btnNivel")+n,skin);
            btn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    dialogo.hide();
                    enviarReto(nivel);
                }
            });
            botonesNivel.add(btn).width(100).height(35).pad(5);
        }
        
        TextButton btnAleatorio=new TextButton(Textos.get("amigos.nivelAleatorio"),skin);
        btnAleatorio.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               dialogo.hide();
               enviarReto(new Random().nextInt(5)+1);
           }
        });
        botonesNivel.add(btnAleatorio).width(110).height(35).pad(5);
        
        dialogo.getContentTable().add(botonesNivel).padBottom(10);
        dialogo.button(Textos.get("amigos.retoCancelar"));
        dialogo.setMovable(false);
        dialogo.show(stage);
    }
    
    private void enviarReto(int nivel){
        try{
            String resultado=gestor.enviarRetoDuelo(amigo.getUsername(), nivel);
            String msg=resultado.equals("OK")?Textos.get("amigos.enviado")+amigo.getUsername()+Textos.get("amigos.enNivel")+nivel:resultado;
            new Dialog("Duelo",skin).text(msg).button("Ok").show(stage);
        }catch(Exception e){
            new Dialog("Error",skin).text(e.getMessage()).button("Ok").show(stage);
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
