package proyecto.sokoban;
public enum GeneroAvatar {
    MASCULINO("Avatar1_"),
    FEMENINO("Avatar2_");

    private GeneroAvatar(String ruta) {
        this.ruta = ruta;
    }
    private final String ruta;

    public String getRuta() {
        return ruta;
    }
    
}
