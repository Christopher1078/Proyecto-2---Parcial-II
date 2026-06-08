package proyecto.sokoban;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class GestorUsuarios {
    private ArrayList<Usuario> usuarios;

    public GestorUsuarios() {
        usuarios=new ArrayList<>();
    }
    
    public void crearCarpetaUsuario(String username){
        File carpeta=new File("Usuarios/"+username);
        if(!carpeta.exists())
            carpeta.mkdirs();
    }
    
    public void guardarUsuario(Usuario usuario){
        try{
            crearCarpetaUsuario(usuario.getUsername());
            File archivo=new File("Usuarios/"+usuario.getUsername()+"/usuario.skb");
            if(!archivo.exists())
                archivo.createNewFile();
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(archivo));
            oos.writeObject(usuario);
            oos.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al guardar usuario: "+e.getMessage());
        }
    }
    
    public Usuario buscarUsuario(String username){
        try{
            File archivo=new File("Usuarios/"+username+"/usuario.skb");
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream(archivo));
            Usuario usuario=(Usuario)ois.readObject();
            ois.close();
            return usuario;
        }catch(IOException| ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "Error al buscar usuario: "+e.getMessage());
        }
        return null;
    }
    
    public boolean existeUsuario(String username){
        File archivo=new File("Usuarios/"+username+"/usuario.skb");
        return archivo.exists();
    }
    
    public void crearUsuario(String username, String password, String nombreCompleto){
        Usuario usuario=new Usuario(username, password, nombreCompleto);
        guardarUsuario(usuario);            
    }
}
