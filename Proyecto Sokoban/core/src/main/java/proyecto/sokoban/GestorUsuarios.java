package proyecto.sokoban;
import java.io.*;
import java.util.ArrayList;
public class GestorUsuarios {
    private Usuario logIn;
    
    public void crearCarpetaUsuario(String username){
        File carpeta=new File("Usuarios/"+username);
        if(!carpeta.exists())
            carpeta.mkdirs();
    }
    
    public ArrayList<Usuario> getUsuarios() throws IOException, ClassNotFoundException{
        ArrayList<Usuario> usuarios=new ArrayList<>();
        File archivo=new File("Usuarios");
        for(File carpeta: archivo.listFiles()){
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("Usuarios/"+carpeta.getName()+"/usuario.skb")));
            usuarios.add((Usuario)ois.readObject());
        }
        return usuarios;
    }
    
    public void guardarUsuario(Usuario usuario) throws IOException, ClassNotFoundException{
        crearCarpetaUsuario(usuario.getUsername());
        File archivo=new File("Usuarios/"+usuario.getUsername()+"/usuario.skb");
        if(!archivo.exists())
            archivo.createNewFile();
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(archivo));
        oos.writeObject(usuario);
        oos.close();
    }
    
    public Usuario buscarUsuario(String username) throws IOException, ClassNotFoundException{
        File archivo=new File("Usuarios/"+username+"/usuario.skb");
        ObjectInputStream ois=new ObjectInputStream(new FileInputStream(archivo));
        Usuario usuario=(Usuario)ois.readObject();
        ois.close();
        return usuario;
    }
    
    public boolean existeUsuario(String username){
        File archivo=new File("Usuarios/"+username+"/usuario.skb");
        return archivo.exists();
    }
    
    public void crearUsuario(String username, String password, String nombreCompleto) throws IOException, ClassNotFoundException{
        Usuario usuario=new Usuario(username, password, nombreCompleto);
        guardarUsuario(usuario);            
    }
    
    public boolean logIn(String username, String password) throws IOException, ClassNotFoundException{
        if(!existeUsuario(username))
            return false;
        Usuario user=buscarUsuario(username);
        if(!user.getPassword().equals(password))
            return false;
        logIn=user;
        return true;
    }
    
    public boolean signIn(String username, String password, String nombreCompleto) throws IOException, ClassNotFoundException{
        if(existeUsuario(username) || !passwordValido(password))
            return false;
        Usuario user=new Usuario(username,password,nombreCompleto);
        logIn=user;
        guardarUsuario(user);
        return true;
    }
    
    public boolean passwordValido(String password){
        if(password.length()!=5)
            return false;
        
        boolean mayuscula=false,minuscula=false,numero=false;
        
        for(char letra: password.toCharArray()){
            if(!mayuscula)
                mayuscula=Character.isUpperCase(letra);
            if(!minuscula)
                minuscula=Character.isLowerCase(letra);
            if(!numero)
                numero=Character.isDigit(letra);
        }
        
        return mayuscula && minuscula && numero;
    }
}
