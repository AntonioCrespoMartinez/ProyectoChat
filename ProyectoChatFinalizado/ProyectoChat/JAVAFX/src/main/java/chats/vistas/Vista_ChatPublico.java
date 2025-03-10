package chats.vistas;

import java.io.IOException;

import chats.App;
import chats.modelo.Modelo;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import chats.controladores.Controlador_ChatPublico;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Representa la vista del chat público. Esta clase gestiona la interfaz de usuario
 * del chat y permite interactuar con el controlador correspondiente. También maneja
 * el envío y la recepción de mensajes.
 */
public class Vista_ChatPublico extends Vista {

    private Controlador_ChatPublico controlador;
    protected final String ficheroInterfaz = "chatPublico.fxml";
    private Scene escena;
    private String usuario;

    @FXML
    private AnchorPane root; // Nodo raíz del archivo FXML
    @FXML
    private TextField cuadroMensaje;
    @FXML
    private AnchorPane vistaMensajes;
    @FXML
    private VBox listaUsuarios;

    /**
     * Inicializa la vista, configurando el evento para detectar la pulsación de teclas.
     */
    public void initialize() {
        // Escucha los eventos de teclado cuando el nodo raíz tiene el foco
        root.setOnKeyPressed(this::detectarTeclas);
    }

    /**
     * Constructor de la vista del chat público. Carga el archivo FXML y configura
     * la escena asociada a esta vista.
     *
     * @param controlador El controlador asociado a esta vista.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    public Vista_ChatPublico(Controlador_ChatPublico controlador) throws IOException {
        super();
        this.controlador = controlador;
        escena = new Scene(cargarInterfaz(ficheroInterfaz, this));
    }

    /**
     * Detecta las teclas presionadas en el teclado. Si se presiona la tecla Enter,
     * se envía el mensaje.
     *
     * @param event El evento de teclado.
     */
    private void detectarTeclas(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                enviarMensaje();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Muestra un mensaje recibido en el chat, añadiéndolo al área de mensajes.
     * Se crea una etiqueta para el usuario y el mensaje recibido, que se añaden
     * al contenedor de mensajes.
     *
     * @param mensaje El mensaje recibido.
     * @param usuarioRecibido El nombre del usuario que envió el mensaje.
     */
    public void pintarMensajeRecibido(String mensaje, String usuarioRecibido) {
        Label usuarioMensaje = new Label();
        Label texto = new Label();

        usuarioMensaje.setStyle("-fx-padding: 10; -fx-border-radius: 15; -fx-background-radius: 15; -fx-text-fill: black; -fx-background-color: #DCDCDC; -fx-opacity: 1; -fx-control-inner-background: lightgreen;");
        texto.setStyle("-fx-padding: 10; -fx-border-radius: 15; -fx-background-radius: 15; -fx-text-fill: white; -fx-background-color: #3b33b2; -fx-opacity: 1; -fx-control-inner-background: lightgreen;");

        usuarioMensaje.setText(usuarioRecibido);
        texto.setText(mensaje);
        usuarioMensaje.setAlignment(Pos.CENTER);
        texto.setAlignment(Pos.CENTER);

        usuarioMensaje.setMaxWidth(70);
        texto.setMaxWidth(350);
        texto.setMinWidth(100);

        // Calcular la posición de los elementos
        double yPosition = vistaMensajes.getChildren().size() * 50.0 + 20.0; // Espaciado ajustado
        double textoOffset = 40.0; // Espaciado adicional para el mensaje

        // Posicionar el nombre del usuario
        AnchorPane.setTopAnchor(usuarioMensaje, yPosition);
        AnchorPane.setLeftAnchor(usuarioMensaje, 10.0);

        // Posicionar el mensaje debajo del usuario
        AnchorPane.setTopAnchor(texto, yPosition + textoOffset);
        AnchorPane.setLeftAnchor(texto, 10.0); // Separar el texto del usuario

        // Añadir los mensajes al contenedor
        vistaMensajes.getChildren().add(usuarioMensaje);
        vistaMensajes.getChildren().add(texto);

        pintarUsuariosConectados();
    }

    /**
     * Envía un mensaje introducido en el cuadro de texto al chat público.
     * El mensaje es enviado a través de la clase {@link App}, y luego se añade
     * a la interfaz de usuario.
     *
     * @throws IOException Si ocurre un error al enviar el mensaje.
     */
    @FXML
    private void enviarMensaje() throws IOException {
        if (!cuadroMensaje.getText().isEmpty()) {
            Label label = new Label();
            label.setStyle("-fx-padding: 10; -fx-border-radius: 15; -fx-background-radius: 15; -fx-text-fill: white; -fx-background-color: #c182f2; -fx-opacity: 1; -fx-control-inner-background: #c182f2;");
            label.setText(cuadroMensaje.getText());
            label.setAlignment(Pos.CENTER);

            label.setMaxWidth(350);
            label.setMinWidth(100);

            // Posicionar dinámicamente el mensaje
            double yPosition = vistaMensajes.getChildren().size() * 40.0 + 20.0;
            AnchorPane.setTopAnchor(label, yPosition);
            AnchorPane.setRightAnchor(label, 10.0);

            // Enviar el mensaje
            App.enviarMensaje(cuadroMensaje.getText(), usuario);

            // Añadir el mensaje a la interfaz
            vistaMensajes.getChildren().add(label);

            // Limpiar el cuadro de mensaje
            cuadroMensaje.setText("");
        }
    }

    /**
     * Establece el nombre de usuario del usuario actual en el chat.
     *
     * @param nombreUsuario El nombre del usuario.
     */
    public void establecerUsuario(String nombreUsuario) {
        this.usuario = nombreUsuario;
    }

    /**
     * Muestra todos los usuarios conectados en el chat público. Crea etiquetas para
     * cada usuario y las añade al contenedor de usuarios.
     */
    public void pintarUsuariosConectados() {
        Modelo m = Modelo.getInstancia();
        for(int i = 0; i < listaUsuarios.getChildren().size(); i++){
            listaUsuarios.getChildren().remove(i);
        }
        for (int i = 0; i < m.getlUsuariosConectados().size(); i++) {
            Label label = new Label();
            label.setStyle("-fx-padding: 10; -fx-border-radius: -15; -fx-background-radius: 15; -fx-text-fill: black; -fx-background-color: white; -fx-border-color: #5e5e5e; -fx-border-width: 1;");
            label.setText(m.getlUsuariosConectados().get(i).getNombre());
            label.setAlignment(Pos.CENTER);

            label.setMinWidth(220);

            double yPosition = vistaMensajes.getChildren().size() * 0.0;
            AnchorPane.setTopAnchor(label, yPosition);
            AnchorPane.setRightAnchor(label, 10.0);

            listaUsuarios.getChildren().add(label);
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
