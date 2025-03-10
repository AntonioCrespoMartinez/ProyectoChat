package chats.modelo;

import java.util.ArrayList;

/**
 * Esta clase representa el modelo de la aplicación, encargándose de gestionar
 * la lista de usuarios conectados. Implementa el patrón de diseño Singleton
 * para asegurar que haya una única instancia de {@link Modelo}.
 */
public class Modelo {

    // Instancia única del modelo (Singleton)
    private static Modelo m;
    private ArrayList<Usuario> lUsuariosConectados;

    /**
     * Constructor privado para evitar la creación de nuevas instancias de {@link Modelo}.
     * Inicializa la lista de usuarios conectados.
     */
    private Modelo(){
        lUsuariosConectados = new ArrayList<>();
    }

    /**
     * Obtiene la instancia única del modelo.
     * Si no existe una instancia, se crea una nueva.
     *
     * @return La instancia única de {@link Modelo}.
     */
    public static Modelo getInstancia(){
        if(m == null){
            m = new Modelo();
        }
        return m;
    }

    /**
     * Obtiene la lista de usuarios conectados.
     *
     * @return La lista de usuarios actualmente conectados.
     */
    public ArrayList<Usuario> getlUsuariosConectados() {
        return lUsuariosConectados;
    }

    /**
     * Establece la lista de usuarios conectados.
     *
     * @param lUsuariosConectados La nueva lista de usuarios conectados.
     */
    public void setlUsuariosConectados(ArrayList<Usuario> lUsuariosConectados) {
        this.lUsuariosConectados = lUsuariosConectados;
    }
}
