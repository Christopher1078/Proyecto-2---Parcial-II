package proyecto.sokoban;

import com.badlogic.gdx.Game;
import proyecto.sokoban.datos.GestorUsuarios;
import proyecto.sokoban.screens.FirstScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class ProyectoSokoban extends Game {
    @Override
    public void create() {
        setScreen(new FirstScreen(this, new GestorUsuarios()));
    }
}