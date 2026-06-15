package proyecto.sokoban;
import java.io.*;
import java.util.ArrayList;
public class GestorUsuarios {
    private Usuario loggedIn;
    
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
        loggedIn=user;
        return true;
    }
    
    public boolean signIn(String username, String password, String nombreCompleto) throws IOException, ClassNotFoundException{
        if(existeUsuario(username) || !passwordValido(password))
            return false;
        Usuario user=new Usuario(username,password,nombreCompleto);
        loggedIn=user;
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
    
    public void logOut(){
        loggedIn=null;
    }
    
    public Usuario getLoggedIn(){
        return loggedIn;
    }
    
    public String enviarSolicitudAmistad(String destinatario)throws IOException, ClassNotFoundException{
        if(loggedIn.getUsername().equals(destinatario))
            return "No puedes enviarte solicitudes a ti mismo";
        
        if(!existeUsuario(destinatario))
            return "Este usuario no existe";
        
        if(loggedIn.isAmigo(destinatario))
            return "Ya eres amigo de "+destinatario;
        
        if(loggedIn.tieneSolicitudesEnviadasA(destinatario))
            return "Ya tienes una solicitud enviada a "+destinatario;
        
        if(loggedIn.tieneSolicitudesRecibidasDe(destinatario))
            return "Ya tienes una solicitud recibida de "+destinatario;
        
        Usuario destino=buscarUsuario(destinatario);
        loggedIn.agregarSolicitudEnviada(destinatario);
        destino.agregarSolicitudRecibida(loggedIn.getUsername());
        guardarUsuario(loggedIn);
        guardarUsuario(destino);
        return "Se envio la solicitud exitosamente";
    }
    
    public boolean aceptarSolicitudAmistad(String remitente)throws IOException, ClassNotFoundException{
        if(!loggedIn.tieneSolicitudesRecibidasDe(remitente) || !existeUsuario(remitente))
            return false;
        
        Usuario otro=buscarUsuario(remitente);
        
        loggedIn.eliminarSolicitudRecibida(remitente);
        loggedIn.agregarAmigo(remitente);
        otro.eliminarSolicitudEnviada(loggedIn.getUsername());
        otro.agregarAmigo(loggedIn.getUsername());
        
        guardarUsuario(loggedIn);
        guardarUsuario(otro);
        
        return true;
    }
    
    public boolean rechazarSolicitudAmistad(String remitente)throws IOException, ClassNotFoundException{
        if(!loggedIn.tieneSolicitudesRecibidasDe(remitente))
            return false;
        
        if(existeUsuario(remitente)){
            Usuario otro=buscarUsuario(remitente);
            otro.eliminarSolicitudEnviada(loggedIn.getUsername());
            guardarUsuario(otro);        
        }
        
        loggedIn.eliminarSolicitudRecibida(remitente);        
        guardarUsuario(loggedIn);
        return true;
    }
    
    public boolean eliminarAmigo(String amigo)throws IOException, ClassNotFoundException{
        if(!loggedIn.isAmigo(amigo))
            return false;
        
        loggedIn.eliminarAmigo(amigo);
        guardarUsuario(loggedIn);
        
        if(existeUsuario(amigo)){
            Usuario otro=buscarUsuario(amigo);
            otro.eliminarAmigo(loggedIn.getUsername());
            guardarUsuario(otro);
        }
        
        return true;
    }
    
    public ArrayList<Usuario> buscarUsuarioPorNombre(String texto)throws IOException, ClassNotFoundException{
        ArrayList<Usuario> resultado=new ArrayList<>();
        String textoLower=texto.toLowerCase();
        
        for(Usuario u: getUsuarios()){
            if(u.getUsername().equals(loggedIn.getUsername()))
                continue;
            if(u.getUsername().toLowerCase().contains(textoLower))
                resultado.add(u);
        }
        return resultado;
    }
}
