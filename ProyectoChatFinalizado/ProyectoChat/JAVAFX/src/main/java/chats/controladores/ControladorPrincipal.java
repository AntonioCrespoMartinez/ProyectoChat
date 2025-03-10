package chats.controladores;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Esta clase representa el controlador principal de la aplicación.
 * Se encarga de gestionar las transiciones entre las vistas de inicio de sesión
 * y el chat público, además de controlar la lógica de navegación y gestión de escenas.
 */
public class ControladorPrincipal extends Controlador {

    private Stage escenario;
    private Scene escena;
    private Controlador_ChatPublico controladorChatPublico;
    private Controlador_Login controladorLogin;

    /**
     * Constructor de la clase {@link ControladorPrincipal}.
     * Inicializa el controlador principal y configura la escena inicial de la aplicación.
     *
     * @param stage El escenario donde se mostrarán las vistas de la aplicación.
     * @throws IOException Si ocurre un error al cargar las vistas del chat o de inicio de sesión.
     */
    public ControladorPrincipal(Stage stage) throws IOException {
        this.escenario = stage;

        // Creación de controladores y vistas
        controladorChatPublico = new Controlador_ChatPublico(this);
        controladorLogin = new Controlador_Login(this);

        // La primera escena se carga de forma especial
        escenario.setScene(controladorLogin.getEscena1());
        escenario.show();
    }

    /**
     * Inicia sesión con el nombre de usuario proporcionado y cambia a la vista del chat público.
     * Este método también establece el nombre del usuario en el chat y pinta la lista de usuarios conectados.
     *
     * @param nombreUsuario El nombre de usuario ingresado en el inicio de sesión.
     */
    public void iniciarSesion(String nombreUsuario) {
        escenario.setScene(controladorChatPublico.getEscena1());
        controladorChatPublico.establecerUsuario(nombreUsuario);
        controladorChatPublico.pintarUsuariosConectados();
        controladorChatPublico.mostrar();
    }

    /**
     * Este método no está implementado en esta clase.
     * Se lanza una excepción {@link UnsupportedOperationException} si se llama.
     */
    @Override
    public void mostrar() {
        throw new UnsupportedOperationException("Unimplemented method 'mostrar'");
    }
}
