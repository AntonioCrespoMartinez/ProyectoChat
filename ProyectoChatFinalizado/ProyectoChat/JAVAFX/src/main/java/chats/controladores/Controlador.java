package chats.controladores;

/**
 * Esta clase abstracta representa un controlador que gestiona la visualización
 * de contenido en la aplicación.
 * Las clases que extienden esta clase deben implementar el método {@link #mostrar()}
 * para definir cómo se debe mostrar el contenido.
 */
public abstract class Controlador {

    /**
     * Muestra el contenido gestionado por el controlador.
     * Este método debe ser implementado por las clases que extiendan esta clase
     * para definir el comportamiento específico de la visualización.
     */
    public abstract void mostrar();
}
