package proyecto.sokoban.juego;

import java.util.ArrayList;

public class Nivel {

    private int numero;
    private String nombre;
    private String[] diseno;

    public Nivel(int numero, String nombre, String[] diseno) {
        this.numero = numero;
        this.nombre = nombre;
        this.diseno = diseno;
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }

    public String[] getDiseno() {
        return diseno.clone();
    }

    public static ArrayList<Nivel> crearNiveles() {
        ArrayList<Nivel> niveles = new ArrayList<>();

        niveles.add(new Nivel(
            1,
            "Primer empuje",
            new String[] {
                "#######",
                "#  .  #",
                "#  $  #",
                "#  @  #",
                "#     #",
                "#######"
            }
        ));

        niveles.add(new Nivel(
            2,
            "Doble caja",
            new String[] {
                "#########",
                "# .   . #",
                "# $ # $ #",
                "#   @   #",
                "#       #",
                "#########"
            }
        ));

        niveles.add(new Nivel(
            3,
            "Bodega trabada",
            new String[] {
                "#############",
                "#     #     #",
                "# .$  #  .  #",
                "#  $     $  #",
                "###  ###  ###",
                "#  $  @     #",
                "#  .     .  #",
                "#############"
            }
        ));

        niveles.add(new Nivel(
            4,
            "Cruce de cajas",
            new String[] {
                "#################",
                "# .   #     .   #",
                "#     #   $     #",
                "#     ###  $    #",
                "#           ### #",
                "# ##  $$        #",
                "#       @   #   #",
                "#   ###     #   #",
                "# .     $   # . #",
                "#     #       . #",
                "#   ###         #",
                "#################"
            }
        ));

        niveles.add(new Nivel(
            5,
            "Bodega final",
            new String[] {
                "###################",
                "#   #     #   .   #",
                "# $ #  .  #   $   #",
                "#   ## ## ##      #",
                "#      $    ##  . #",
                "## ##     $    ## #",
                "#    $  ###  $    #",
                "#  .    # .    #  #",
                "#    ## #    $ #  #",
                "# .     #  .      #",
                "#   @   ###    #  #",
                "###################"
            }
        ));

        return niveles;
    }
}
