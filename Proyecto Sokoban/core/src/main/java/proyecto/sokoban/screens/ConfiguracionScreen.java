package proyecto.sokoban.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import proyecto.sokoban.datos.ConfiguracionJuego;
import proyecto.sokoban.datos.GestorUsuarios;
import proyecto.sokoban.datos.Idioma;
import proyecto.sokoban.utilidad.MusicaManager;
import proyecto.sokoban.utilidad.Textos;
public class ConfiguracionScreen implements Screen{
 
    private final Game game;
    private final GestorUsuarios gestor;
    private Stage stage;
    private Skin skin;
    private Label lblArriba, lblAbajo, lblIzquierda, lblDerecha, lblReiniciar, lblError;
    private int reasignando = -1;
    private static final int ARRIBA     = 0;
    private static final int ABAJO      = 1;
    private static final int IZQUIERDA  = 2;
    private static final int DERECHA    = 3;
    private static final int REINICIAR  = 4;
 
    public ConfiguracionScreen(Game game, GestorUsuarios gestor) {
        this.game   = game;
        this.gestor = gestor;
    }
 
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin  = new Skin(Gdx.files.internal("uiskin.json"));
 
        ConfiguracionJuego config = ConfiguracionJuego.getInstance();
 
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        stage.addActor(table);
 
        table.add(new Label(Textos.get("config.titulo"), skin)).padTop(15).padBottom(25);
        table.row();
 
        table.add(new Label(Textos.get("config.volumen"), skin)).padBottom(8);
        table.row();
 
        Table volTable = new Table();
        Label lblVolPorcentaje = new Label(porcentaje(config.getVolumen()), skin);
 
        Slider sliderVol = new Slider(0f, 1f, 0.05f, false, skin);
        sliderVol.setValue(config.getVolumen());
 
        TextButton btnMenos = new TextButton("-", skin);
        TextButton btnMas = new TextButton("+", skin);
 
        sliderVol.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                float v = sliderVol.getValue();
                MusicaManager.getInstance().setVolumen(v);
                lblVolPorcentaje.setText(porcentaje(v));
                config.guardar(gestor.getLoggedIn().getUsername());
            }
        });
 
        btnMenos.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float v = Math.max(0f, sliderVol.getValue() - 0.1f);
                sliderVol.setValue(v);
            }
        });
 
        btnMas.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float v = Math.min(1f, sliderVol.getValue() + 0.1f);
                sliderVol.setValue(v);
            }
        });
 
        volTable.add(btnMenos).width(40).height(40).padRight(10);
        volTable.add(sliderVol).width(250).padRight(10);
        volTable.add(btnMas).width(40).height(40).padRight(10);
        volTable.add(lblVolPorcentaje).width(50);
 
        table.add(volTable).padBottom(25);
        table.row();
 
        table.add(new Label(Textos.get("config.idioma"), skin)).padBottom(8);
        table.row();
 
        Table idiomaTable = new Table();
        Label lblIdiomaActual = new Label(nombreIdioma(config.getIdioma()), skin);
        TextButton btnCambiarIdioma = new TextButton(Textos.get("config.cambiar"), skin);
 
        btnCambiarIdioma.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Idioma nuevo = config.getIdioma() == Idioma.ESPANOL? Idioma.INGLES: Idioma.ESPANOL;
                config.setIdioma(nuevo);
                config.guardar(gestor.getLoggedIn().getUsername());
                lblIdiomaActual.setText(nombreIdioma(nuevo));
            }
        });
 
        idiomaTable.add(lblIdiomaActual).width(150).padRight(20);
        idiomaTable.add(btnCambiarIdioma).width(130).height(40);
 
        table.add(idiomaTable).padBottom(25);
        table.row();
 
        table.add(new Label(Textos.get("config.controles"), skin)).padBottom(8);
        table.row();
 
        Table ctrlTable = new Table();
 
        lblArriba    = new Label(ConfiguracionJuego.nombreTecla(config.getTeclaArriba()),    skin);
        lblAbajo     = new Label(ConfiguracionJuego.nombreTecla(config.getTeclaAbajo()),     skin);
        lblIzquierda = new Label(ConfiguracionJuego.nombreTecla(config.getTeclaIzquierda()), skin);
        lblDerecha   = new Label(ConfiguracionJuego.nombreTecla(config.getTeclaDerecha()),   skin);
        lblReiniciar = new Label(ConfiguracionJuego.nombreTecla(config.getTeclaReiniciar()), skin);
 
        agregarFilaControl(ctrlTable, Textos.get("config.arriba"),     lblArriba,    ARRIBA);
        agregarFilaControl(ctrlTable, Textos.get("config.abajo"),      lblAbajo,     ABAJO);
        agregarFilaControl(ctrlTable, Textos.get("config.izquierda"),  lblIzquierda, IZQUIERDA);
        agregarFilaControl(ctrlTable, Textos.get("config.derecha"),    lblDerecha,   DERECHA);
        agregarFilaControl(ctrlTable, Textos.get("config.reiniciar"),  lblReiniciar, REINICIAR);
 
        table.add(ctrlTable).padBottom(25);
        table.row();
        
        lblError=new Label("",skin);
        lblError.setColor(Color.RED);
        table.add(lblError).padBottom(10);
        table.row();
 
        TextButton btnVolver = new TextButton(Textos.get("config.volver"), skin);
        btnVolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                config.guardar(gestor.getLoggedIn().getUsername());
                game.setScreen(new MenuScreen(game, gestor));
            }
        });
 
        table.add(btnVolver).width(200).height(40).padBottom(20);
 
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (reasignando < 0) 
                    return false;
 
                if (keycode == Input.Keys.ESCAPE|| keycode == Input.Keys.ENTER|| keycode == Input.Keys.BACK) {
                    reasignando = -1;
                    actualizarLabels();
                    lblError.setText("");
                    return true;
                }
                String conflicto=buscarConflicto(reasignando, keycode);
                
                if(conflicto!=null){
                    lblError.setText(Textos.get("config.teclaDuplicada")+ConfiguracionJuego.nombreTecla(keycode)+Textos.get("config.yaAsignada")+conflicto);
                    reasignando=-1;
                    actualizarLabels();
                    return true;
                }
                lblError.setText("");
                aplicarTecla(reasignando,keycode);
                reasignando=-1;
                actualizarLabels();
                config.guardar(gestor.getLoggedIn().getUsername());
                return true;
            }
            
        });
 
        Gdx.input.setInputProcessor(stage);
    }
 
    private void agregarFilaControl(Table tabla, String nombre, Label lblTecla, int accion) {
        TextButton btnReasignar = new TextButton(Textos.get("config.reasignar"), skin);
 
        btnReasignar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                reasignando = accion;
                lblTecla.setText(Textos.get("config.presionaTecla"));
            }
        });
 
        tabla.add(new Label(nombre, skin)).width(110).left().padRight(10).padTop(6);
        tabla.add(lblTecla).width(130).padRight(20).padTop(6);
        tabla.add(btnReasignar).width(130).height(35).padTop(6);
        tabla.row();
    }
 
    private void aplicarTecla(int accion, int keycode) {
        ConfiguracionJuego config = ConfiguracionJuego.getInstance();
        switch (accion) {
            case ARRIBA:    
                config.setTeclaArriba(keycode);    
                break;
            case ABAJO:     
                config.setTeclaAbajo(keycode);   
                break;
            case IZQUIERDA: 
                config.setTeclaIzquierda(keycode);
                break;
            case DERECHA:  
                config.setTeclaDerecha(keycode);  
                break;
            case REINICIAR: 
                config.setTeclaReiniciar(keycode); 
                break;
        }
    }
 
    private void actualizarLabels() {
        ConfiguracionJuego config = ConfiguracionJuego.getInstance();
        lblArriba.setText(ConfiguracionJuego.nombreTecla(config.getTeclaArriba()));
        lblAbajo.setText(ConfiguracionJuego.nombreTecla(config.getTeclaAbajo()));
        lblIzquierda.setText(ConfiguracionJuego.nombreTecla(config.getTeclaIzquierda()));
        lblDerecha.setText(ConfiguracionJuego.nombreTecla(config.getTeclaDerecha()));
        lblReiniciar.setText(ConfiguracionJuego.nombreTecla(config.getTeclaReiniciar()));
    }
 
    private String porcentaje(float v) {
        return Math.round(v * 100) + "%";
    }
 
    private String nombreIdioma(Idioma idioma) {
        return Textos.get("config.idiomaActual");
    }
    
    private String buscarConflicto(int accionActual, int keyCode){
        ConfiguracionJuego config=ConfiguracionJuego.getInstance();
        
        if(accionActual!=ARRIBA && config.getTeclaArriba()==keyCode)
            return Textos.get("config.arriba");
        
        if(accionActual!=ABAJO && config.getTeclaAbajo()==keyCode)
            return Textos.get("config.abajo");
        
        if(accionActual!=IZQUIERDA && config.getTeclaIzquierda()==keyCode)
            return Textos.get("config.izquierda");
        
        if(accionActual!=DERECHA && config.getTeclaDerecha()==keyCode)
            return Textos.get("config.derecha");
        
        if(accionActual!=REINICIAR && config.getTeclaReiniciar()==keyCode)
            return Textos.get("config.reiniciar");
        return null;
    }
 
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }
 
    @Override
    public void resize(int w, int h) {
        stage.getViewport().update(w, h, true);
    }
 
    @Override public void pause(){
    }
    
    @Override public void resume(){
    }
 
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
 
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }    
}