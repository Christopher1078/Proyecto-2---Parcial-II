package proyecto.sokoban.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.io.IOException;
import java.util.ArrayList;
import proyecto.sokoban.datos.GestorUsuarios;
import proyecto.sokoban.datos.Usuario;
import proyecto.sokoban.utilidad.Textos;
public class RankingScreen implements Screen{
 
    private final Game game;
    private final GestorUsuarios gestor;
    private Stage stage;
    private Skin skin;
    private boolean tabGlobal = true;
    private Table tablaRanking;
    private TextButton btnTabGlobal;
    private TextButton btnTabAmigos;
    private Label lblError;
 
    public RankingScreen(Game game, GestorUsuarios gestor) {
        this.game   = game;
        this.gestor = gestor;
    }
 
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin  = new Skin(Gdx.files.internal("uiskin.json"));
 
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        stage.addActor(table);
 
        Label titulo = new Label(Textos.get("ranking.titulo"), skin);
        table.add(titulo).padTop(15).padBottom(20);
        table.row();
 
        Table tabs = new Table();
        btnTabGlobal = new TextButton(Textos.get("ranking.global"), skin);
        btnTabAmigos = new TextButton(Textos.get("ranking.amigos"), skin);
 
        btnTabGlobal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tabGlobal = true;
                actualizarTabs();
                cargarRanking();
            }
        });
 
        btnTabAmigos.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tabGlobal = false;
                actualizarTabs();
                cargarRanking();
            }
        });
 
        tabs.add(btnTabGlobal).width(180).height(40).padRight(10);
        tabs.add(btnTabAmigos).width(180).height(40);
 
        table.add(tabs).padBottom(15);
        table.row();
 
        lblError = new Label("", skin);
        lblError.setColor(Color.RED);
        table.add(lblError).padBottom(5);
        table.row();
 
        tablaRanking = new Table();
        tablaRanking.top();
 
        ScrollPane scroll = new ScrollPane(tablaRanking, skin);
        scroll.setFadeScrollBars(false);
        scroll.setScrollingDisabled(true, false);
 
        table.add(scroll).width(620).expandY().fillY().padBottom(15);
        table.row();
 
        TextButton btnVolver = new TextButton(Textos.get("ranking.volver"), skin);
        btnVolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game, gestor));
            }
        });
 
        table.add(btnVolver).width(200).height(40).padBottom(20);
 
        actualizarTabs();
        cargarRanking();
 
        Gdx.input.setInputProcessor(stage);
    }
 
    private void actualizarTabs() {
        if (tabGlobal) {
            btnTabGlobal.setColor(Color.WHITE);
            btnTabAmigos.setColor(Color.GRAY);
        } else {
            btnTabGlobal.setColor(Color.GRAY);
            btnTabAmigos.setColor(Color.WHITE);
        }
    }
 
    private void cargarRanking() {
        tablaRanking.clear();
        lblError.setText("");
 
        try {
            ArrayList<Usuario> lista = tabGlobal? gestor.getRankingGlobal(): gestor.getRankingAmigos();
 
            if (lista.isEmpty()) {
                tablaRanking.add(new Label(Textos.get("ranking.vacio"), skin)).colspan(6).pad(20);
                return;
            }
 
            agregarEncabezado(Textos.get("ranking.col.pos"),            50);
            agregarEncabezado(Textos.get("ranking.col.usuario"),      150);
            agregarEncabezado(Textos.get("ranking.col.niveles"),      90);
            agregarEncabezado(Textos.get("ranking.col.mejor"), 120);
            agregarEncabezado(Textos.get("ranking.col.partidas"),     90);
            agregarEncabezado(Textos.get("ranking.col.total"), 120);
            tablaRanking.row();
 
            tablaRanking.add(new Label("--------------------------------------------------------------"+ "--------------------", skin)).colspan(6).padBottom(6);
            tablaRanking.row();
 
            String miNombre = gestor.getLoggedIn().getUsername();
 
            for (int i = 0; i < lista.size(); i++) {
                Usuario u = lista.get(i);
                boolean soyYo = u.getUsername().equals(miNombre);
                int pos = i + 1;
 
                Label lblPos      = new Label(pos + ".", skin);
                Label lblUser     = new Label(u.getUsername(), skin);
                Label lblNiveles  = new Label(String.valueOf(u.getNivelesCompletados()), skin);
                Label lblMejor    = new Label(u.getMejorTiempo(), skin);
                Label lblPartidas = new Label(String.valueOf(u.getPartidasJugadas()), skin);
                Label lblTotal    = new Label(formatearTiempoTotal(u.getTiempoJugado()), skin);
 
                switch (pos) {
                    case 1:
                        lblPos.setColor(Color.GOLD);
                        lblUser.setColor(Color.GOLD);
                        break;
                    case 2:
                        Color plata = new Color(0.75f, 0.75f, 0.75f, 1f);
                        lblPos.setColor(plata);
                        lblUser.setColor(plata);
                        break;
                    case 3:
                        Color bronce = new Color(0.8f, 0.5f, 0.2f, 1f);
                        lblPos.setColor(bronce);
                        lblUser.setColor(bronce);
                        break;
                    default:
                        break;
                }
 
                if (soyYo) {
                    lblUser.setColor(Color.CYAN);
                    lblNiveles.setColor(Color.CYAN);
                    lblMejor.setColor(Color.CYAN);
                    lblPartidas.setColor(Color.CYAN);
                    lblTotal.setColor(Color.CYAN);
                    if (pos > 3) lblPos.setColor(Color.CYAN);
                }
 
                tablaRanking.add(lblPos).width(50).left().padRight(5).padTop(5);
                tablaRanking.add(lblUser).width(150).left().padRight(10).padTop(5);
                tablaRanking.add(lblNiveles).width(90).center().padRight(10).padTop(5);
                tablaRanking.add(lblMejor).width(120).center().padRight(10).padTop(5);
                tablaRanking.add(lblPartidas).width(90).center().padRight(10).padTop(5);
                tablaRanking.add(lblTotal).width(120).center().padTop(5);
                tablaRanking.row();
            }
 
        } catch (IOException | ClassNotFoundException e) {
            lblError.setText("Error: " + e.getMessage());
        }
    }
 
    private void agregarEncabezado(String texto, int ancho) {
        Label lbl = new Label(texto, skin);
        lbl.setColor(Color.LIGHT_GRAY);
        tablaRanking.add(lbl).width(ancho).left().padRight(texto.equals(Textos.get("ranking.col.pos")) ? 5 : 10).padBottom(4);
    }
 
    private String formatearTiempoTotal(long segundos) {
        long h = segundos / 3600;
        long m = (segundos % 3600) / 60;
        if (h > 0) return h + "h " + m + "m";
        return m + "m " + (segundos % 60) + "s";
    }
 
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }
 
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
 
    @Override public void pause()  {
    }
    
    @Override public void resume() {
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
