package proyecto.sokoban;
public enum GeneroAvatar {
    MASCULINO("Avatares/Avatar1_pos"),
    FEMENINO("Avatares/Avatar2_pos");

    private GeneroAvatar(String ruta) {
        this.ruta = ruta;
    }
    private final String ruta;

    public String getRuta() {
        return ruta;
    }
    
}
