package proyecto.sokoban;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
public class MusicaManager {

    private static MusicaManager instancia;
    private Music musica;
    private boolean iniciada = false;
    private static final String RUTA_MUSICA = "musica/fondo.mp3";
 
    private MusicaManager() {
    }
 
    public static MusicaManager getInstance() {
        if (instancia == null) 
            instancia = new MusicaManager();
        return instancia;
    }
 
    public void iniciar(){
        if (iniciada) 
            return;
 
        try {
            if (!Gdx.files.internal(RUTA_MUSICA).exists()) {
                System.out.println("Archivo de musica no encontrado: " + RUTA_MUSICA);
                return;
            }
 
            musica = Gdx.audio.newMusic(Gdx.files.internal(RUTA_MUSICA));
            musica.setLooping(true);
            musica.setVolume(ConfiguracionJuego.getInstance().getVolumen());
            musica.play();
            iniciada = true;
 
        } catch (Exception e) {
            System.out.println("No se pudo cargar la musica: " + e.getMessage());
        }
    }
 
    public void setVolumen(float volumen) {
        ConfiguracionJuego.getInstance().setVolumen(volumen);
        
        if (musica != null) {
            musica.setVolume(volumen);
        }
    }
 
    public float getVolumen() {
        return ConfiguracionJuego.getInstance().getVolumen();
    }
 
    public boolean isIniciada() {
        return iniciada;
    }
 
    public void detener() {
        if (musica != null) {
            musica.stop();
            musica.dispose();
            musica   = null;
            iniciada = false;
        }
    }
 
    public void pausar() {
        if (musica != null && musica.isPlaying())
            musica.pause();
    }
 
    public void reanudar() {
        if (musica != null && !musica.isPlaying() && iniciada)
            musica.play();
    }    
}
