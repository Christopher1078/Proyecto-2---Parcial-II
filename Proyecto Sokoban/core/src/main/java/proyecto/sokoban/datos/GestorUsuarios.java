package proyecto.sokoban.datos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
public class GestorUsuarios {

    private Usuario loggedIn;

    public void crearCarpetaUsuario(String username) {
        File carpeta = new File("Usuarios/" + username);

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    public ArrayList<Usuario> getUsuarios() throws IOException, ClassNotFoundException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        File carpetaUsuarios = new File("Usuarios");

        if (!carpetaUsuarios.exists()) {
            carpetaUsuarios.mkdirs();
            return usuarios;
        }

        File[] carpetas = carpetaUsuarios.listFiles();

        if (carpetas == null) {
            return usuarios;
        }

        for (File carpeta : carpetas) {
            File archivo = new File(carpeta, "usuario.skb");

            if (archivo.exists()) {
                ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo));
                Usuario usuario = (Usuario) entrada.readObject();
                entrada.close();

                usuarios.add(usuario);
            }
        }

        return usuarios;
    }

    public void guardarUsuario(Usuario usuario) throws IOException, ClassNotFoundException {
        crearCarpetaUsuario(usuario.getUsername());

        File archivo = new File("Usuarios/" + usuario.getUsername() + "/usuario.skb");

        ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo));
        salida.writeObject(usuario);
        salida.close();
    }

    public void guardarUsuarioActual() {
        try {
            if (loggedIn != null) {
                guardarUsuario(loggedIn);
            }
        } catch (IOException | ClassNotFoundException error) {
            System.out.println("No se pudo guardar el usuario actual.");
        }
    }

    public Usuario buscarUsuario(String username) throws IOException, ClassNotFoundException {
        File archivo = new File("Usuarios/" + username + "/usuario.skb");

        ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo));
        Usuario usuario = (Usuario) entrada.readObject();
        entrada.close();

        return usuario;
    }

    public boolean existeUsuario(String username) {
        File archivo = new File("Usuarios/" + username + "/usuario.skb");
        return archivo.exists();
    }

    public void crearUsuario(String username, String password, String nombreCompleto)throws IOException, ClassNotFoundException {

        Usuario usuario = new Usuario(username, password, nombreCompleto);
        guardarUsuario(usuario);
    }

    public boolean logIn(String username, String password)throws IOException, ClassNotFoundException {

        if (!existeUsuario(username)) {
            return false;
        }

        Usuario usuario = buscarUsuario(username);

        if (!usuario.getPassword().equals(password) || usuario.isCuentaDeshabilitada()) {
            return false;
        }

        usuario.iniciarSesion();
        loggedIn = usuario;
        guardarUsuario(usuario);
        ConfiguracionJuego.cargar(username);
        return true;
    }

    public boolean signIn(String username, String password, String nombreCompleto)throws IOException, ClassNotFoundException {

        if (existeUsuario(username) || !passwordValido(password)) {
            return false;
        }

        Usuario usuario = new Usuario(username, password, nombreCompleto);
        loggedIn = usuario;
        guardarUsuario(usuario);
        ConfiguracionJuego.cargar(username);
        return true;
    }

    public boolean passwordValido(String password) {
        if (password == null || password.length() < 5) {
            return false;
        }

        boolean mayuscula = false;
        boolean minuscula = false;
        boolean numero = false;
        boolean especial = false;

        for (char letra : password.toCharArray()) {
            if (!mayuscula) 
                mayuscula = Character.isUpperCase(letra);

            if (!minuscula) 
                minuscula = Character.isLowerCase(letra);

            if (!numero) 
                numero = Character.isDigit(letra);
            
            if(!especial)
                especial = !Character.isLetterOrDigit(letra);
        }

        return mayuscula && minuscula && numero;
    }

    public void logOut() {
        guardarUsuarioActual();
        ConfiguracionJuego.limpiar();
        loggedIn = null;
    }

    public Usuario getLoggedIn() {
        return loggedIn;
    }

    public String enviarSolicitudAmistad(String destinatario)throws IOException, ClassNotFoundException {

        if (loggedIn.getUsername().equals(destinatario)) {
            return "No puedes enviarte solicitudes a ti mismo";
        }

        if (!existeUsuario(destinatario)) {
            return "Este usuario no existe";
        }

        if (loggedIn.isAmigo(destinatario)) {
            return "Ya eres amigo de " + destinatario;
        }

        if (loggedIn.tieneSolicitudesEnviadasA(destinatario)) {
            return "Ya tienes una solicitud enviada a " + destinatario;
        }

        if (loggedIn.tieneSolicitudesRecibidasDe(destinatario)) {
            return "Ya tienes una solicitud recibida de " + destinatario;
        }

        Usuario destino = buscarUsuario(destinatario);

        loggedIn.agregarSolicitudEnviada(destinatario);
        destino.agregarSolicitudRecibida(loggedIn.getUsername());

        guardarUsuario(loggedIn);
        guardarUsuario(destino);

        return "Se envio la solicitud exitosamente";
    }

    public boolean aceptarSolicitudAmistad(String remitente)throws IOException, ClassNotFoundException {

        if (!loggedIn.tieneSolicitudesRecibidasDe(remitente) || !existeUsuario(remitente)) {
            return false;
        }

        Usuario otro = buscarUsuario(remitente);

        loggedIn.eliminarSolicitudRecibida(remitente);
        loggedIn.agregarAmigo(remitente);

        otro.eliminarSolicitudEnviada(loggedIn.getUsername());
        otro.agregarAmigo(loggedIn.getUsername());

        guardarUsuario(loggedIn);
        guardarUsuario(otro);

        return true;
    }

    public boolean rechazarSolicitudAmistad(String remitente)throws IOException, ClassNotFoundException {

        if (!loggedIn.tieneSolicitudesRecibidasDe(remitente)) {
            return false;
        }

        if (existeUsuario(remitente)) {
            Usuario otro = buscarUsuario(remitente);
            otro.eliminarSolicitudEnviada(loggedIn.getUsername());
            guardarUsuario(otro);
        }

        loggedIn.eliminarSolicitudRecibida(remitente);
        guardarUsuario(loggedIn);

        return true;
    }

    public boolean eliminarAmigo(String amigo)throws IOException, ClassNotFoundException {

        if (!loggedIn.isAmigo(amigo)) {
            return false;
        }

        loggedIn.eliminarAmigo(amigo);
        guardarUsuario(loggedIn);

        if (existeUsuario(amigo)) {
            Usuario otro = buscarUsuario(amigo);
            otro.eliminarAmigo(loggedIn.getUsername());
            guardarUsuario(otro);
        }

        return true;
    }

    public ArrayList<Usuario> buscarUsuarioPorNombre(String texto)throws IOException, ClassNotFoundException {

        ArrayList<Usuario> resultado = new ArrayList<>();
        String textoLower = texto.toLowerCase();

        for (Usuario usuario : getUsuarios()) {
            if (usuario.getUsername().equals(loggedIn.getUsername())) 
                continue;
            if(usuario.isCuentaDeshabilitada())
                continue;
            if (usuario.getUsername().toLowerCase().contains(textoLower)) {
                resultado.add(usuario);
            }
        }

        return resultado;
    }
    
    public boolean reactivarCuenta(String username, String password)throws IOException, ClassNotFoundException{
        if(!existeUsuario(username))
            return false;
        Usuario usuario=buscarUsuario(username);
        if(!usuario.getPassword().equals(password))
            return false;
        usuario.habilitar();
        usuario.iniciarSesion();
        loggedIn=usuario;
        guardarUsuario(usuario);
        ConfiguracionJuego.cargar(username);
        return true;
    }
    
    public void deshabilitarCuenta()throws IOException, ClassNotFoundException{
        if(loggedIn==null)
            return;
        
        loggedIn.deshabilitar();
        guardarUsuario(loggedIn);
        ConfiguracionJuego.limpiar();
        loggedIn=null;
    }
    
    public void eliminarCuenta()throws IOException, ClassNotFoundException{
        if(loggedIn==null)
            return;
        String username=loggedIn.getUsername();
        
        for(Usuario otro: getUsuarios()){
            if(otro.getUsername().equals(username))
                continue;
            boolean modificado=false;
            
            if(otro.isAmigo(username)){
                otro.eliminarAmigo(username);
                modificado=true;
            }
            
            if(otro.tieneSolicitudesEnviadasA(username)){
                otro.eliminarSolicitudEnviada(username);
                modificado=true;
            }
            
            if(otro.tieneSolicitudesRecibidasDe(username)){
                otro.eliminarSolicitudRecibida(username);
                modificado=true;
            }
            
            if(modificado)
                guardarUsuario(otro);
        }
        
        File archivo=new File("Usuarios/"+username+"/usuario.skb");
        archivo.delete();
        File historialArchivo=new File("Usuarios/"+username+"/historial.skb");
        historialArchivo.delete();
        File configArchivo=new File("Usuarios/"+username+"/config.skb");
        configArchivo.delete();
        File carpeta=new File("Usuarios/"+username);
        carpeta.delete();
        ConfiguracionJuego.limpiar();
        loggedIn=null;
    }
    
    public ArrayList<String> getAmigosActivos()throws IOException, ClassNotFoundException{
        ArrayList<String> activos=new ArrayList<>();
        
        for(String username: loggedIn.getAmigos()){
            if(!existeUsuario(username))
                continue;
            Usuario amigo=buscarUsuario(username);
            if(!amigo.isCuentaDeshabilitada())
                activos.add(username);
        }
        return activos;
    }
    
    public void guardarHistorial(Usuario usuario)throws IOException{
        crearCarpetaUsuario(usuario.getUsername());
        File archivo=new File("Usuarios/"+usuario.getUsername()+"/historial.skb");
        ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream(archivo));
        salida.writeObject(usuario.getHistorial());
        salida.close();
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<HistorialPartida> cargarHistorial(String username)throws IOException, ClassNotFoundException{
        File archivo=new File("Usuarios/"+username+"/historial.skb");
        
        if(!archivo.exists())
            return new ArrayList<>();
        
        ObjectInputStream entrada=new ObjectInputStream(new FileInputStream(archivo));
        ArrayList<HistorialPartida> historial=(ArrayList<HistorialPartida>)entrada.readObject();
        entrada.close();
        return historial;
    }
    
    public void guardarUsuarioConHistorial(Usuario usuario)throws IOException, ClassNotFoundException{
        guardarUsuario(usuario);
        guardarHistorial(usuario);
    }
    
    public void guardarUsuarioActualConHistorial(){
        try{
            guardarUsuarioConHistorial(loggedIn);
        }catch(IOException | ClassNotFoundException e){
            System.out.println("No se pudo guardar el usuario actual con su historial");
        }
    }
    
    public String enviarRetoDuelo(String nombreRetado, int nivel)throws IOException, ClassNotFoundException{
        if(loggedIn==null)
            return "No hay sesion activa";
        
        if(loggedIn.getUsername().equals(nombreRetado))
            return "No puedes retarte a ti mismo";
        
        if(!existeUsuario(nombreRetado))
            return "El usuario no existe";
        
        if(!loggedIn.isAmigo(nombreRetado))
            return "Solo puedes retar a tus amigos";
        
        SolicitudDuelo existente=loggedIn.buscarDuelo(loggedIn.getUsername(), nombreRetado, nivel);
        
        if(existente!=null && existente.getEstado()!=SolicitudDuelo.Estado.COMPLETADO)
            return "Ya tienes un duelo activo en ese nivel con ese jugador";
        
        SolicitudDuelo duelo=new SolicitudDuelo(loggedIn.getUsername(), nombreRetado, nivel);
        Usuario retado=buscarUsuario(nombreRetado);
        loggedIn.agregarDuelo(duelo);
        retado.agregarDuelo(duelo);
        guardarUsuario(loggedIn);
        guardarUsuario(retado);
        return "OK";
    }
    
    public void aceptarDuelo(SolicitudDuelo duelo, long tiempoRetado)throws IOException, ClassNotFoundException{
        duelo.registrarTiempoRetado(tiempoRetado);
        guardarUsuario(loggedIn);
        
        if(existeUsuario(duelo.getRetador())){
            Usuario retador=buscarUsuario(duelo.getRetador());
            SolicitudDuelo dueloEnRetador=retador.buscarDuelo(duelo.getRetador(), duelo.getRetado(), duelo.getNivel());
            
            if(dueloEnRetador!=null)
                dueloEnRetador.registrarTiempoRetado(tiempoRetado);
            guardarUsuario(retador);
        }
    }
    
    public void completarDuelo(SolicitudDuelo duelo, long TiempoRetador)throws IOException, ClassNotFoundException{
        duelo.registrarTiempoRetador(TiempoRetador);
        guardarUsuario(loggedIn);
        
        if(existeUsuario(duelo.getRetado())){
            Usuario retado=buscarUsuario(duelo.getRetado());
            SolicitudDuelo dueloEnRetado=retado.buscarDuelo(duelo.getRetador(), duelo.getRetado(), duelo.getNivel());
            
            if(dueloEnRetado!=null)
                dueloEnRetado.registrarTiempoRetador(TiempoRetador);
            guardarUsuario(retado);
        }
    }
    
    public ArrayList<Usuario> getRankingGlobal()throws IOException, ClassNotFoundException{
        ArrayList<Usuario>lista=new ArrayList<>();
        
        for(Usuario u:getUsuarios()){
            if(!u.isCuentaDeshabilitada())
                lista.add(u);
        }
        lista.sort((a,b)->{
            if(b.getNivelesCompletados() != a.getNivelesCompletados())
                return b.getNivelesCompletados()-a.getNivelesCompletados();
            
            long tA=getMejorTiempoSegundos(a);
            long tB=getMejorTiempoSegundos(b);
            
            if(tA!=tB){
                if(tA<0)
                    return 1;
                
                if(tB<0)
                    return -1;
                return Long.compare(tA, tB);
            }
            
            return b.getPartidasJugadas()-a.getPartidasJugadas();
        });
        return lista;
    }
    
    public ArrayList<Usuario> getRankingAmigos()throws IOException, ClassNotFoundException{
        ArrayList<String>  incluidos=new ArrayList<>();
        incluidos.add(loggedIn.getUsername());
        
        for(String username: loggedIn.getAmigos()){
            if(existeUsuario(username)){
                Usuario amigo=buscarUsuario(username);
                if(!amigo.isCuentaDeshabilitada())
                    incluidos.add(username);
            }
        }
        
        ArrayList<Usuario> lista=new ArrayList<>();
        
        for(Usuario u: getUsuarios()){
            if(incluidos.contains(u.getUsername()))
                lista.add(u);
        }
        
        lista.sort((a,b)->{
            if(b.getNivelesCompletados()!=a.getNivelesCompletados())
                return b.getNivelesCompletados()-a.getNivelesCompletados();
            
            long tA=getMejorTiempoSegundos(a);
            long tB=getMejorTiempoSegundos(b);
            
            if(tA!=tB){
                if(tA<0)
                    return 1;
                
                if(tB<0)
                    return -1;
                
                return Long.compare(tA, tB);
            }
            
            return b.getPartidasJugadas()-a.getPartidasJugadas();
        });
        return lista;
    }
    
    private long getMejorTiempoSegundos(Usuario u){
        long mejor= Long.MAX_VALUE;
        for(HistorialPartida p: u.getHistorial()){
            if(p.isVictoria() && p.getTiempo()<mejor)
                mejor=p.getTiempo();
        }
        return mejor==Long.MAX_VALUE? -1 : mejor;
    }
}
