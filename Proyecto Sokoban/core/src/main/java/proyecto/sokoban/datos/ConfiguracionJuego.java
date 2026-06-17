package proyecto.sokoban.datos;
import com.badlogic.gdx.Input;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
public class ConfiguracionJuego implements Serializable {

    private static final long serialVersionUID = 1L;
    private float volumen;
    private Idioma idioma;
    private int teclaArriba;
    private int teclaAbajo;
    private int teclaIzquierda;
    private int teclaDerecha;
    private int teclaReiniciar;
    private static ConfiguracionJuego instancia;
    private static String usuarioCargado=null;
 
    private ConfiguracionJuego(){
        volumen=0.5f;
        idioma=Idioma.ESPANOL;
        teclaArriba=Input.Keys.W;
        teclaAbajo=Input.Keys.S;
        teclaIzquierda=Input.Keys.A;
        teclaDerecha=Input.Keys.D;
        teclaReiniciar=Input.Keys.R;
    }
 
    public static ConfiguracionJuego cargar(String username){
        if (instancia != null && username.equals(usuarioCargado)) {
            return instancia;
        }
 
        File archivo = new File("Usuarios/" + username + "/config.skb");
        if (archivo.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
                instancia      = (ConfiguracionJuego) in.readObject();
                usuarioCargado = username;
                return instancia;
            } catch (Exception e) {
                System.out.println("No se pudo cargar config de " + username
                        + ", usando valores por defecto.");
            }
        }
 
        instancia = new ConfiguracionJuego();
        usuarioCargado = username;
        return instancia;
    }
 
    public static ConfiguracionJuego getInstance(){
        if (instancia == null) 
            instancia=new ConfiguracionJuego();
        return instancia;
    }
    
    public static void limpiar() {
        instancia=null;
        usuarioCargado=null;
    }    
 
    public void guardar(String username){
        File carpeta = new File("Usuarios/" + username);
        if (!carpeta.exists()) 
            carpeta.mkdirs();
 
        File archivo = new File(carpeta, "config.skb");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(this);
        } catch (IOException e) {
            System.out.println("No se pudo guardar la configuracion: " + e.getMessage());
        }
    }
  
    public float getVolumen(){ 
        return volumen; 
    }
    
    public void  setVolumen(float volumen){
        this.volumen = Math.max(0f, Math.min(1f, volumen));
    }
 
    public Idioma getIdioma(){ 
        return idioma; 
    }
    
    public void   setIdioma(Idioma idioma){ 
        this.idioma = idioma; 
    }
 
    public int getTeclaArriba(){
        return teclaArriba;    
    }
    
    public int getTeclaAbajo(){ 
        return teclaAbajo;    
    }
    
    public int getTeclaIzquierda(){ 
        return teclaIzquierda;
    }
    
    public int getTeclaDerecha(){ 
        return teclaDerecha;  
    }
    
    public int getTeclaReiniciar(){
        return teclaReiniciar; 
    }
 
    public void setTeclaArriba(int k){
        teclaArriba= k; 
    }
    
    public void setTeclaAbajo(int k){ 
        teclaAbajo= k; 
    }
    
    public void setTeclaIzquierda(int k){
        teclaIzquierda= k; 
    }
    
    public void setTeclaDerecha(int k){
        teclaDerecha= k;
    }
    
    public void setTeclaReiniciar(int k){ 
        teclaReiniciar= k;
    }
 
    public static String nombreTecla(int keycode) {
        return Input.Keys.toString(keycode);
    }    
}
