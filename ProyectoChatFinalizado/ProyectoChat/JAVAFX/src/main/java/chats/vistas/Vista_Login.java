package chats.vistas;

import chats.App;
import chats.controladores.Controlador_ChatPublico;
import chats.controladores.Controlador_Login;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Representa la vista del inicio de sesión en la aplicación. Esta clase gestiona la
 * interfaz de usuario de inicio de sesión y permite al usuario ingresar su nombre
 * de usuario para acceder al chat público.
 */
public class Vista_Login extends Vista {

    private Controlador_Login controlador;
    protected final String ficheroInterfaz = "login.fxml";
    private Scene escena;

    @FXML
    private AnchorPane root; // Nodo raíz del archivo FXML
    @FXML
    private TextField nombreUsuario;

    /**
     * Constructor de la vista de inicio de sesión. Carga el archivo FXML y configura
     * la escena asociada a esta vista.
     *
     * @param controlador El controlador asociado a esta vista.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    public Vista_Login(Controlador_Login controlador) throws IOException {
        super();
        this.controlador = controlador;
        escena = new Scene(cargarInterfaz(ficheroInterfaz, this));
    }

    /**
     * Inicia sesión con el nombre de usuario introducido en el cuadro de texto.
     * Si el cuadro no está vacío, se llama al método `iniciarSesion` del controlador
     * con el nombre de usuario proporcionado.
     */
    public void iniciarSesion() {
        if (!nombreUsuario.getText().isEmpty()) {
            controlador.iniciarSesion(nombreUsuario.getText());
        }
    }

    /**
     * Obtiene la escena asociada a esta vista.
     *
     * @return La escena que representa esta vista.
     */
    public Scene getEscena() {
        return escena;
    }

}
