package chats.controladores;

import java.io.IOException;

import javafx.scene.Scene;
import chats.vistas.Vista_ChatPublico;

/**
 * Esta clase representa el controlador encargado de gestionar la lógica de
 * interacción con el chat público en la aplicación.
 * Extiende de {@link Controlador} y maneja la visualización de mensajes,
 * usuarios conectados y el nombre del usuario en el chat.
 */
public class Controlador_ChatPublico extends Controlador {

    private static Vista_ChatPublico vista;
    private ControladorPrincipal controlador;

    /**
     * Constructor de la clase {@link Controlador_ChatPublico}.
     * Inicializa el controlador y la vista asociada.
     *
     * @param controlador El controlador principal que maneja la lógica general de la aplicación.
     * @throws IOException Si ocurre un error al cargar la vista del chat.
     */
    public Controlador_ChatPublico(ControladorPrincipal controlador) throws IOException{
        this.controlador = controlador;
        vista = new Vista_ChatPublico(this);
    }

    /**
     * Muestra el mensaje recibido en el chat público.
     * Este método invoca a la vista para pintar el mensaje en la interfaz.
     *
     * @param mensaje El contenido del mensaje recibido.
     * @param usuarioRecibido El nombre del usuario que envió el mensaje.
     */
    public static void pintarMensajeRecibido(String mensaje, String usuarioRecibido){
        vista.pintarMensajeRecibido(mensaje, usuarioRecibido);
    }

    /**
     * Establece el nombre del usuario en la vista del chat.
     *
     * @param nombreUsuario El nombre del usuario que se establece en la vista.
     */
    public void establecerUsuario(String nombreUsuario) {
        vista.establecerUsuario(nombreUsuario);
    }

    /**
     * Muestra la vista del chat público en la pantalla.
     * Este método es una implementación del método abstracto {@link Controlador#mostrar()}.
     */
    @Override
    public void mostrar() {
        vista.mostrar();
    }

    /**
     * Obtiene la escena que contiene la vista del chat público.
     *
     * @return La escena que muestra la vista del chat público.
     */
    public Scene getEscena1() {
        return vista.getEscena();
    }

    /**
     * Muestra los usuarios conectados en el chat público.
     * Este método invoca a la vista para pintar la lista de usuarios conectados.
     */
    public void pintarUsuariosConectados() {
        vista.pintarUsuariosConectados();
    }
}
