package proyecto.sokoban.utilidad;

import proyecto.sokoban.datos.ConfiguracionJuego;
import proyecto.sokoban.datos.Idioma;

public final class Textos {

    private Textos() {}
 
    public static String get(String clave) {
        Idioma idioma = ConfiguracionJuego.getInstance().getIdioma();
        return idioma == Idioma.INGLES ? en(clave) : es(clave);
    }
 
    private static String es(String clave) {
        switch (clave) {
 
            case "first.titulo":        
                return "Sokoban";
            case "first.login":         
                return "Inicio de Sesion";
            case "first.registro":     
                return "Registro";
            case "first.salir":         
                return "Salir";
 
            case "login.titulo":        
                return "Inicio de Sesion";
            case "login.usuario":       
                return "Usuario";
            case "login.password":      
                return "Password";
            case "login.mostrar":       
                return "Mostrar password";
            case "login.btn":           
                return "Iniciar Sesion";
            case "login.regresar":      
                return "Regresar";
            case "login.vacio":         
                return "Parametros en blanco";
            case "login.noExiste":      
                return "No existe este nombre de usuario";
            case "login.incorrecto":    
                return "Password Incorrecto";
            case "login.deshabilitada": 
                return "Esta cuenta esta deshabilitada";
            case "login.reactivar":     
                return "Tu cuenta esta deshabilitada. ¿Deseas reactivarla?";
            case "login.cancelar":      
                return "Cancelar";
            case "login.reactivarBtn":  
                return "Reactivar";
 
            case "signin.titulo":       
                return "Registrarse";
            case "signin.nombre":       
                return "Nombre Completo";
            case "signin.usuario":      
                return "Usuario";
            case "signin.password":     
                return "Confirmar password";
            case "signin.longitud":    
                return "Al menos 5 caracteres";
            case "signin.mayuscula":    
                return "Al menos una mayuscula";
            case "signin.minuscula":    
                return "Al menos una minuscula";
            case "signin.numero":       
                return "Al menos un numero";
            case "signin.btn":          
                return "Registrarse";
            case "signin.regresar":     
                return "Regresar";
            case "signin.vacio":        
                return "Parametros en blanco";
            case "signin.existe":       
                return "El usuario ya existe";
            case "signin.passInvalido": 
                return "Password invalido";
            case "signin.especial":    
                return "Al menos un caracter especial (!@#...)";
            case "signin.confirmar":    
                return "Confirmar password";
            case "signin.noCoincide":   
                return "Los passwords no coinciden";                
 
            case "menu.titulo":         
                return "Menu Inicio";
            case "menu.jugar":          
                return "Jugar";
            case "menu.perfil":         
                return "Mi Perfil";
            case "menu.ranking":        
                return "Ranking";
            case "menu.config":         
                return "Configuracion";
            case "menu.logout":         
                return "Cerrar Sesion";
 
            case "nivel.titulo":        
                return "Seleccion de nivel";
            case "nivel.regresar":      
                return "Regresar";
            case "nivel.nivel":         
                return "Nivel ";
            case "nivel.bloqueado":
                return "Bloqueado";
            case "nivel.noDesbloqueado":
                return "No has desbloqueado este nivel";                
 
            case "avatar.titulo":       
                return "Elige tu avatar";
            case "avatar.btn1":         
                return "Avatar 1";
            case "avatar.btn2":         
                return "Avatar 2";
            case "avatar.regresar":     
                return "Regresar";
 
            case "perfil.titulo":           
                return "Mi Perfil";
            case "perfil.username":        
                return "Username: ";
            case "perfil.nombre":          
                return "Nombre: ";
            case "perfil.amigos":         
                return "Amigos: ";
            case "perfil.cambiarAvatar":   
                return "Cambiar Avatar";
            case "perfil.verAmigos":      
                return "Ver Amigos";
            case "perfil.historial":       
                return "Ver Historial";
            case "perfil.duelos":         
                return "Mis Duelos";
            case "perfil.estadisticas":    
                return "Estadisticas";
            case "perfil.partidas":        
                return "Partidas jugadas: ";
            case "perfil.niveles":          
                return "Niveles completados: ";
            case "perfil.tiempoTotal":     
                return "Tiempo total: ";
            case "perfil.mejorTiempo":    
                return "Mejor tiempo: ";
            case "perfil.registro":       
                return "Registro: ";
            case "perfil.ultimaSesion":   
                return "Ultima sesion: ";
            case "perfil.deshabilitar":  
                return "Deshabilitar cuenta";
            case "perfil.eliminar":        
                return "Eliminar cuenta";
            case "perfil.volver":          
                return "Volver";
            case "perfil.deshabConfirm":   
                return "Tu cuenta quedara deshabilitada.\nNo podras iniciar sesion hasta reactivarla.\n\n¿Confirmas?";
            case "perfil.elimConfirm":     
                return "Esta accion es PERMANENTE.\nSe borraran todos tus datos.\n\n¿Estas seguro?";
            case "perfil.confirmarBtn":    
                return "Confirmar";
            case "perfil.cancelarBtn":     
                return "Cancelar";
            case "perfil.deshabBtn":       
                return "Deshabilitar";
            case "perfil.elimBtn":         
                return "Eliminar";
            case "perfil.cambiarUsername":  
                return "Cambiar usuario";
            case "perfil.cambiarPassword":  
                return "Cambiar password";
            case "perfil.usernameActual":  
                return "Nuevo usuario:";
            case "perfil.passActual":      
                return "Password actual:";
            case "perfil.passNuevo":       
                return "Nuevo password:";
            case "perfil.passConfirmar":   
                return "Confirmar password:";
            case "perfil.cambiarBtn":     
                return "Cambiar";
            case "perfil.err.vacio":     
                return "Los campos no pueden estar vacios";
            case "perfil.err.existe":     
                return "Ese nombre de usuario ya existe";
            case "perfil.err.incorrecto": 
                return "Password actual incorrecto";
            case "perfil.err.invalido":    
                return "El nuevo password no cumple los requisitos";
            case "perfil.err.noCoincide":  
                return "Los passwords no coinciden";
            case "perfil.ok.username":     
                return "Usuario cambiado correctamente";
            case "perfil.ok.password":     
                return "Password cambiado correctamente";
            case "perfil.tiempoPromedio":   
                return "Tiempo promedio: ";  
            case "perfil.subirFoto":       
                return "Subir foto";
            case "perfil.usarPredefinido":  
                return "Usar predefinido";
            case "perfil.rutaFoto":        
                return "Ruta de la imagen...";
            case "perfil.rutaFotoInfo":   
                return "Escribe la ruta del archivo (ej: fotos/mifoto.png):";
            case "perfil.fotoNoEncontrada":
                return "Imagen no encontrada en la ruta";                
             
            case "amigos.titulo":           
                return "Amigos";
            case "amigos.buscar":           
                return "Buscar usuario...";
            case "amigos.btnBuscar":        
                return "Buscar";
            case "amigos.solicitudes":      
                return "Solicitudes recibidas";
            case "amigos.misAmigos":        
                return "Mis amigos";
            case "amigos.volver":          
                return "Volver";
            case "amigos.sinResultados":    
                return "No se encontraron coincidencias";
            case "amigos.escribir":         
                return "Escribe un nombre de usuario para buscar";
            case "amigos.agregar":          
                return "Agregar";
            case "amigos.aceptar":          
                return "Aceptar";
            case "amigos.rechazar":         
                return "Rechazar";
            case "amigos.estadisticas":     
                return "Estadisticas";
            case "amigos.retar":            
                return "Retar";
            case "amigos.eliminar":         
                return "Eliminar";
            case "amigos.sinSolicitudes":   
                return "No tienes solicitudes pendientes";
            case "amigos.sinAmigos":        
                return "Aun no tienes amigos";
            case "amigos.yaAmigos":         
                return "Ya son amigos";
            case "amigos.solicitudEnviada": 
                return "Solicitud enviada";
            case "amigos.teEnvio":          
                return "Te envio solicitud";
            case "amigos.retoCancelar":     
                return "Cancelar";
            case "amigos.nivelAleatorio":   
                return "Aleatorio";
            case "amigos.elegirNivel":      
                return "En qué nivel quieres retar a ";
            case "amigos.btnNivel":         
                return "Nivel ";
            case "amigos.ahoraAmigos":
                return "Ahora eres amigo de: ";
            case "amigos.rechazado":
                return "Rechazaste la solicitud de: ";
            case "amigos.eliminado":
                return " fue eliminado de tu lista de amigos";
            case "amigos.elegir":
                return "Elegir nivel de duelo";
            case "amigos.enviado":
                return "Reto enviado a ";
            case "amigos.enNivel":
                return "en nivel ";  
            case "amigos.yaEnviada":
                return "Ya hay una solicitud enviada";                  
                
            case "estAmigo.titulo":         
                return "Comparar estadisticas";
            case "estAmigo.columna":        
                return "Estadistica";
            case "estAmigo.tu":             
                return "Tu (";
            case "estAmigo.retar":          
                return "Retar a duelo";
            case "estAmigo.volver":         
                return "Volver";
            case "estAmigo.partidas":       
                return "Partidas jugadas";
            case "estAmigo.niveles":        
                return "Niveles completados";
            case "estAmigo.tiempoTotal":    
                return "Tiempo total";
            case "estAmigo.mejorTiempo":    
                return "Mejor tiempo";
            case "estAmigo.nivelAlto":      
                return "Nivel mas alto";
            case "estAmigo.elegir":
                return "Elegir el nivel del duelo";
 
            case "duelos.titulo":           
                return "Duelos";
            case "duelos.retosRecibidos":   
                return "Retos recibidos";
            case "duelos.sinRetos":         
                return "No tienes retos pendientes.";
            case "duelos.tuTurno":          
                return "Tu turno de jugar";
            case "duelos.sinTurno":         
                return "No hay duelos esperando tu turno.";
            case "duelos.resultados":       
                return "Resultados";
            case "duelos.sinResultados":    
                return "Aun no has completado ningun duelo.";
            case "duelos.aceptarJugar":     
                return "Aceptar y jugar";
            case "duelos.jugar":            
                return "Jugar";
            case "duelos.ganaste":         
                return "Ganaste";
            case "duelos.perdiste":         
                return "Perdiste";
            case "duelos.volver":           
                return "Volver";
            case "duelos.nivel":            
                return "Nivel ";
            case "duelos.tu":               
                return "Tu: ";
            case "duelos.tiempoRival":      
                return "Tiempo rival: ";
 
        case "historial.titulo":        
            return "Historial de Partidas";
            case "historial.fecha":     
                return "Fecha";
            case "historial.nivel":      
                return "Nivel";
            case "historial.resultado":   
                return "Resultado";
            case "historial.movimientos": 
                return "Movimientos";
            case "historial.tiempo":      
                return "Tiempo";
            case "historial.vacio":        
                return "Aun no has jugado ninguna partida.";
            case "historial.victoria":    
                return "Victoria";
            case "historial.intento":       
                return "Intento";
            case "historial.promedioTitulo":
                return "Tiempo promedio por nivel:";
            case "historial.volver":     
                return "Volver";
 
            case "ranking.titulo":          
                return "Ranking";
            case "ranking.global":          
                return "Global";
            case "ranking.amigos":          
                return "Amigos";
            case "ranking.vacio":           
                return "No hay jugadores para mostrar.";
            case "ranking.col.pos":         
                return "#";
            case "ranking.col.usuario":    
                return "Usuario";
            case "ranking.col.niveles":     
                return "Niveles";
            case "ranking.col.mejor":       
                return "Mejor tiempo";
            case "ranking.col.partidas":    
                return "Partidas";
            case "ranking.col.total":       
                return "Tiempo total";
            case "ranking.volver":          
                return "Volver";
 
            case "config.titulo":           
                return "Configuracion";
            case "config.volumen":         
                return "Volumen";
            case "config.idioma":           
                return "Idioma";
            case "config.idiomaActual":     
                return "Espanol";
            case "config.cambiar":          
                return "Cambiar";
            case "config.controles":        
                return "Controles";
            case "config.arriba":           
                return "Arriba:";
            case "config.abajo":           
                return "Abajo:";
            case "config.izquierda":       
                return "Izquierda:";
            case "config.derecha":          
                return "Derecha:";
            case "config.reiniciar":        
                return "Reiniciar:";
            case "config.reasignar":        
                return "Reasignar";
            case "config.presionaTecla":    
                return "Presiona una tecla...";
            case "config.volver":           
                return "Volver";
            case "config.teclaDuplicada":   
                return "La tecla '";
            case "config.yaAsignada":       
                return "' ya esta asignada a: ";
 
            case "comun.ok":                
                return "Ok";
            case "comun.error":             
                return "Error";
            case "comun.idioma":          
                return "EN";
                
                
            case "mapa.titulo":            
                return "SOKOBAN | Nivel ";
            case "mapa.de":                 
                return " de ";
            case "mapa.movimientos":  
                return "Movimientos: ";
            case "mapa.fallos":        
                return " | Fallos: ";
            case "mapa.reinicios":     
                return " | Reinicios: ";
            case "mapa.tiempoNivel":    
                return "Tiempo del nivel: ";
            case "mapa.tiempoTotal":    
                return " | Tiempo total: ";
            case "mapa.controles":       
                return "Controles: ";
            case "mapa.oFlechas":        
                return " o flechas | ";
            case "mapa.reiniciarTecla":   
                return " reiniciar | ESC menu";
 
            case "victoria.titulo":       
                return "Victoria";
            case "victoria.juegoCompleto":
                return "Felicidades, terminaste el juego";
            case "victoria.nivel":         
                return "Nivel completado: ";
            case "victoria.movimientos":  
                return "Movimientos: ";
            case "victoria.tiempo":       
                return "Tiempo: ";
            case "victoria.reinicios":    
                return "Reinicios: ";
            case "victoria.siguiente":    
                return "Siguiente nivel";
            case "victoria.volverMenu":   
                return "Volver al menu principal";
            case "victoria.repetir":      
                return "Repetir nivel";
            case "victoria.menuNiveles":  
                return "Menu de niveles";
            
            case "duelo.hud.titulo":      
                return "DUELO | Nivel ";
            case "duelo.hud.retadoPor":    
                return "Retado por: ";
            case "duelo.hud.retastea":     
                return "Retaste a: ";
            case "duelo.hud.movimientos": 
                return "Movimientos: ";
            case "duelo.hud.tiempo":       
                return " | Tiempo: ";
            case "duelo.hud.tiempoRival": 
                return "Tiempo de ";
            case "duelo.hud.superalo":     
                return "  (superalo!)";
            case "duelo.hud.noJugo":       
                return " aun no ha jugado.";
            case "duelo.hud.controles":   
                return "Controles: ";
            case "duelo.hud.oFlechas":    
                return " o flechas | ";
            case "duelo.hud.reiniciarEsc": 
                return " reiniciar | ESC salir";            
            
            default: return "?" + clave + "?";
        }
    }
 
    private static String en(String clave) {
        switch (clave) {
 
            case "first.titulo":        
                return "Sokoban";
            case "first.login":         
                return "Sign In";
            case "first.registro":      
                return "Register";
            case "first.salir":         
                return "Exit";
 
            case "login.titulo":        
                return "Sign In";
            case "login.usuario":       
                return "Username";
            case "login.password":      
                return "Password";
            case "login.mostrar":       
                return "Show password";
            case "login.btn":           
                return "Log In";
            case "login.regresar":      
                return "Back";
            case "login.vacio":         
                return "Fields cannot be blank";
            case "login.noExiste":      
                return "Username does not exist";
            case "login.incorrecto":    
                return "Incorrect password";
            case "login.deshabilitada": 
                return "This account is disabled";
            case "login.reactivar":     
                return "Your account is disabled. Do you want to reactivate it?";
            case "login.cancelar":      
                return "Cancel";
            case "login.reactivarBtn":  
                return "Reactivate";
 
            case "signin.titulo":       
                return "Register";
            case "signin.nombre":       
                return "Full Name";
            case "signin.usuario":      
                return "Username";
            case "signin.password":     
                return "Confirm password";
            case "signin.longitud":     
                return "At least 5 characters";
            case "signin.mayuscula":    
                return "At least one uppercase";
            case "signin.minuscula":   
                return "At least one lowercase";
            case "signin.numero":       
                return "At least one number";
            case "signin.btn":          
                return "Register";
            case "signin.regresar":     
                return "Back";
            case "signin.vacio":        
                return "Fields cannot be blank";
            case "signin.existe":      
                return "Username already exists";
            case "signin.passInvalido": 
                return "Invalid password";
            case "signin.especial":     
                return "At least one special character (!@#...)";
            case "signin.confirmar":    
                return "Confirm password";
            case "signin.noCoincide":  
                return "Passwords do not match";                
                
            case "menu.titulo":         
                return "Main Menu";
            case "menu.jugar":          
                return "Play";
            case "menu.perfil":         
                return "My Profile";
            case "menu.ranking":        
                return "Ranking";
            case "menu.config":         
                return "Settings";
            case "menu.logout":         
                return "Log Out";
 
            case "nivel.titulo":        
                return "Select Level";
            case "nivel.regresar":      
                return "Back";
            case "nivel.nivel":         
                return "Level ";
            case "nivel.bloqueado":
                return "Locked";   
            case "nivel.noDesbloqueado":
                return "You haven't unlocked this level";
 
            case "avatar.titulo":       
                return "Choose your avatar";
            case "avatar.btn1":         
                return "Avatar 1";
            case "avatar.btn2":         
                return "Avatar 2";
            case "avatar.regresar":     
                return "Back";
 
            case "perfil.titulo":          
                return "My Profile";
            case "perfil.username":        
                return "Username: ";
            case "perfil.nombre":        
                return "Name: ";
            case "perfil.amigos":        
                return "Friends: ";
            case "perfil.cambiarAvatar":  
                return "Change Avatar";
            case "perfil.verAmigos":     
                return "View Friends";
            case "perfil.historial":      
                return "Match History";
            case "perfil.duelos":         
                return "My Duels";
            case "perfil.estadisticas":   
                return "Statistics";
            case "perfil.partidas":       
                return "Matches played: ";
            case "perfil.niveles":        
                return "Levels completed: ";
            case "perfil.tiempoTotal":    
                return "Total time: ";
            case "perfil.mejorTiempo":     
                return "Best time: ";
            case "perfil.tiempoPromedio":  
                return "Avg time: ";
            case "perfil.registro":         
                return "Registered: ";
            case "perfil.ultimaSesion":    
                return "Last session: ";
            case "perfil.deshabilitar":    
                return "Disable account";
            case "perfil.eliminar":       
                return "Delete account";
            case "perfil.volver":          
                return "Back";
            case "perfil.deshabConfirm":   
                return "Your account will be disabled.\nYou won't be able to log in until you reactivate it.\n\nConfirm?";
            case "perfil.elimConfirm":     
                return "This action is PERMANENT.\nAll your data will be deleted.\n\nAre you sure?";
            case "perfil.confirmarBtn":   
                return "Confirm";
            case "perfil.cancelarBtn":     
                return "Cancel";
            case "perfil.deshabBtn":       
                return "Disable";
            case "perfil.elimBtn":         
                return "Delete";
            case "perfil.cambiarUsername": 
                return "Change username";
            case "perfil.cambiarPassword":  
                return "Change password";
            case "perfil.usernameActual":   
                return "New username:";
            case "perfil.passActual":       
                return "Current password:";
            case "perfil.passNuevo":      
                return "New password:";
            case "perfil.passConfirmar":  
                return "Confirm password:";
            case "perfil.cambiarBtn":     
                return "Change";
            case "perfil.err.vacio":      
                return "Fields cannot be blank";
            case "perfil.err.existe":      
                return "That username already exists";
            case "perfil.err.incorrecto":  
                return "Current password is incorrect";
            case "perfil.err.invalido":     
                return "New password does not meet requirements";
            case "perfil.err.noCoincide":  
                return "Passwords do not match";
            case "perfil.ok.username":    
                return "Username changed successfully";
            case "perfil.ok.password":    
                return "Password changed successfully";   
            case "perfil.subirFoto":       
                return "Upload picture";
            case "perfil.usarPredefinido":  
                return "Use default";
            case "perfil.rutaFoto":        
                return "Image path...";
            case "perfil.rutaFotoInfo":   
                return "Enter the file path (e.g: photos/myphoto.png):"; 
            case "perfil.fotoNoEncontrada":
                return "Image not found at that path";
 
            case "amigos.titulo":           
                return "Friends";
            case "amigos.buscar":           
                return "Search user...";
            case "amigos.btnBuscar":       
                return "Search";
            case "amigos.solicitudes":      
                return "Friend requests";
            case "amigos.misAmigos":        
                return "My friends";
            case "amigos.volver":           
                return "Back";
            case "amigos.sinResultados":    
                return "No users found";
            case "amigos.escribir":         
                return "Type a username to search";
            case "amigos.agregar":          
                return "Add";
            case "amigos.aceptar":          
                return "Accept";
            case "amigos.rechazar":         
                return "Decline";
            case "amigos.estadisticas":    
                return "Stats";
            case "amigos.retar":      
                return "Challenge";
            case "amigos.eliminar":  
                return "Remove";
            case "amigos.sinSolicitudes":
                return "No pending friend requests";
            case "amigos.sinAmigos":  
                return "You have no friends yet";
            case "amigos.yaAmigos":  
                return "Already friends";
            case "amigos.solicitudEnviada":
                return "Request sent";
            case "amigos.teEnvio": 
                return "Sent you a request";
            case "amigos.retoCancelar":  
                return "Cancel";
            case "amigos.nivelAleatorio": 
                return "Random";
            case "amigos.elegirNivel":    
                return "Which level do you want to challenge ";
            case "amigos.btnNivel":      
                return "Level ";
            case "amigos.ahoraAmigos":
                return "Now you're friends with: ";
            case "amigos.rechazado":
                return "You rejected a request by: ";
            case "amigos.eliminado":
                return " was eliminated of your friend list";
            case "amigos.elegir":
                return "Choose duel's level";
            case "amigos.enviado":
                return "Duel sent to ";
            case "amigos.enNivel":
                return "in level ";
            case "amigos.yaEnviada":
                return "You already have a request sent";             
 
            case "estAmigo.titulo":   
                return "Compare Statistics";
            case "estAmigo.columna":     
                return "Statistic";
            case "estAmigo.tu":          
                return "You (";
            case "estAmigo.retar":       
                return "Challenge to duel";
            case "estAmigo.volver":      
                return "Back";
            case "estAmigo.partidas":     
                return "Matches played";
            case "estAmigo.niveles":      
                return "Levels completed";
            case "estAmigo.tiempoTotal":  
                return "Total time";
            case "estAmigo.mejorTiempo": 
                return "Best time";
            case "estAmigo.nivelAlto":    
                return "Highest level";
            case "estAmigo.elegir":
                return "Choose duel's level";                
 
            case "duelos.titulo":         
                return "Duels";
            case "duelos.retosRecibidos":  
                return "Challenges received";
            case "duelos.sinRetos":       
                return "No pending challenges.";
            case "duelos.tuTurno":        
                return "Your turn to play";
            case "duelos.sinTurno":        
                return "No duels waiting for your turn.";
            case "duelos.resultados":     
                return "Results";
            case "duelos.sinResultados":  
                return "You haven't completed any duel yet.";
            case "duelos.aceptarJugar":   
                return "Accept & play";
            case "duelos.jugar":           
                return "Play";
            case "duelos.ganaste":        
                return "You won";
            case "duelos.perdiste":        
                return "You lost";
            case "duelos.volver":          
                return "Back";
            case "duelos.nivel":           
                return "Level ";
            case "duelos.tu":              
                return "You: ";
            case "duelos.tiempoRival":    
                return "Rival's time: ";
 
            case "historial.titulo":      
                return "Match History";
            case "historial.fecha":      
                return "Date";
            case "historial.nivel":       
                return "Level";
            case "historial.resultado":    
                return "Result";
            case "historial.movimientos": 
                return "Moves";
            case "historial.tiempo":      
                return "Time";
            case "historial.vacio":       
                return "You haven't played any match yet.";
            case "historial.victoria":    
                return "Victory";
            case "historial.intento":     
                return "Attempt";
            case "historial.promedioTitulo": 
                return "Average time per level:";
            case "historial.volver":       
                return "Back";
 
            case "ranking.titulo":         
                return "Ranking";
            case "ranking.global":         
                return "Global";
            case "ranking.amigos":         
                return "Friends";
            case "ranking.vacio":          
                return "No players to show.";
            case "ranking.col.pos":       
                return "#";
            case "ranking.col.usuario":   
                return "Username";
            case "ranking.col.niveles":  
                return "Levels";
            case "ranking.col.mejor":    
                return "Best time";
            case "ranking.col.partidas": 
                return "Matches";
            case "ranking.col.total":   
                return "Total time";
            case "ranking.volver":      
                return "Back";
 
            case "config.titulo":       
                return "Settings";
            case "config.volumen":        
                return "Volume";
            case "config.idioma":        
                return "Language";
            case "config.idiomaActual":   
                return "English";
            case "config.cambiar":        
                return "Change";
            case "config.controles":     
                return "Controls";
            case "config.arriba":        
                return "Up:";
            case "config.abajo":          
                return "Down:";
            case "config.izquierda":     
                return "Left:";
            case "config.derecha":        
                return "Right:";
            case "config.reiniciar":      
                return "Restart:";
            case "config.reasignar":      
                return "Reassign";
            case "config.presionaTecla":   
                return "Press a key...";
            case "config.volver":         
                return "Back";
            case "config.teclaDuplicada": 
                return "The key '";
            case "config.yaAsignada":     
                return "' is already assigned to: ";
 
            case "comun.ok":                
                return "Ok";
            case "comun.error":             
                return "Error";
            case "comun.idioma":  
                return "ES";
                
            case "mapa.titulo":            
                return "SOKOBAN | Level ";
            case "mapa.de":                
                return " of ";
            case "mapa.movimientos":       
                return "Moves: ";
            case "mapa.fallos":             
                return " | Fails: ";
            case "mapa.reinicios":          
                return " | Restarts: ";
            case "mapa.tiempoNivel":       
                return "Level time: ";
            case "mapa.tiempoTotal":       
                return " | Total time: ";
            case "mapa.controles":          
                return "Controls: ";
            case "mapa.oFlechas":           
                return " or arrows | ";
            case "mapa.reiniciarTecla":    
                return " restart | ESC menu";
 
            case "victoria.titulo":        
                return "Victory";
            case "victoria.juegoCompleto": 
                return "Congratulations, you finished the game!";
            case "victoria.nivel":          
                return "Level completed: ";
            case "victoria.movimientos":   
                return "Moves: ";
            case "victoria.tiempo":        
                return "Time: ";
            case "victoria.reinicios":     
                return "Restarts: ";
            case "victoria.siguiente":     
                return "Next level";
            case "victoria.volverMenu":    
                return "Back to main menu";
            case "victoria.repetir":        
                return "Replay level";
            case "victoria.menuNiveles":   
                return "Level select";       
            
            case "duelo.hud.titulo":       
                return "DUEL | Level ";
            case "duelo.hud.retadoPor":   
                return "Challenged by: ";
            case "duelo.hud.retastea":     
                return "You challenged: ";
            case "duelo.hud.movimientos":  
                return "Moves: ";
            case "duelo.hud.tiempo":       
                return " | Time: ";
            case "duelo.hud.tiempoRival":  
                return "Time of ";
            case "duelo.hud.superalo":     
                return "  (beat it!)";
            case "duelo.hud.noJugo":       
                return " hasn't played yet.";
            case "duelo.hud.controles":    
                return "Controls: ";
            case "duelo.hud.oFlechas":     
                return " or arrows | ";
            case "duelo.hud.reiniciarEsc": 
                return " restart | ESC exit";            
 
            default: return "?" + clave + "?";
        }
    }    
}
