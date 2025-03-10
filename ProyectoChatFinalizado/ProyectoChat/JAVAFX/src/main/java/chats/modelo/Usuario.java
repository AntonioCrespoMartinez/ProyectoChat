package chats.modelo;

/**
 * Esta clase representa un usuario en la aplicación, con un nombre asociado.
 * Proporciona métodos para acceder y modificar el nombre del usuario.
 */
public class Usuario {

    private String nombre;

    /**
     * Constructor de la clase {@link Usuario}.
     * Inicializa el usuario con el nombre proporcionado.
     *
     * @param nombre El nombre del usuario.
     */
    public Usuario(String nombre){
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre El nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
