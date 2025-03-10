package chats;

import chats.controladores.ControladorPrincipal;
import chats.controladores.Controlador_ChatPublico;
import chats.modelo.Modelo;
import chats.modelo.Usuario;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Clase principal de la aplicación que gestiona la interfaz gráfica, el envío y la recepción
 * de mensajes a través de sockets UDP con broadcast. Inicia la aplicación y permite el chat
 * público entre usuarios conectados a la red.
 */
public class App extends Application {
    private static final int PUERTO = 12345;  // Puerto para enviar y recibir mensajes
    private static String BROADCAST_IP = "255.255.255.255";  // Dirección de broadcast por defecto

    /**
     * Método principal que lanza la aplicación JavaFX y gestiona la recepción de mensajes
     * en un hilo separado. También permite enviar mensajes a través de un broadcast en la red.
     *
     * @param args Argumentos de la línea de comandos (no utilizados en esta implementación).
     */
    public static void main(String[] args) {
        new Thread(() -> Application.launch(App.class)).start();

        Scanner scanner = new Scanner(System.in);

        // Hilo para recibir mensajes
        new Thread(() -> {
            try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
                socket.setBroadcast(true);  // Habilita la recepción de mensajes de broadcast
                byte[] buffer = new byte[1024];
                System.out.println("Escuchando en el puerto " + PUERTO + " (Broadcast)...");

                while (true) {
                    DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                    socket.receive(paquete);

                    String mensaje = new String(paquete.getData(), 0, paquete.getLength());
                    String ipRemitente = paquete.getAddress().getHostAddress();

                    // Procesa el mensaje recibido
                    String[] textoRecibido = mensaje.split("--");
                    InetAddress localHost = InetAddress.getLocalHost();
                    Modelo m = Modelo.getInstancia();
                    if (m.getlUsuariosConectados().isEmpty()) {
                        boolean encontrado = false;
                        for (int i = 0; i < m.getlUsuariosConectados().size(); i++) {
                            if (textoRecibido[0].equals(m.getlUsuariosConectados().get(i).getNombre())) {
                                encontrado = true;
                            }
                        }
                        if (!encontrado) {
                            m.getlUsuariosConectados().add(new Usuario(textoRecibido[0]));
                        }
                    }
                    Platform.runLater(() -> Controlador_ChatPublico.pintarMensajeRecibido(textoRecibido[1], textoRecibido[0]));

                    System.out.println("\nMensaje recibido desde " + ipRemitente + " → " + mensaje);
                    System.out.print("> ");
                }
            } catch (Exception e) {
                System.err.println("Error en la recepción: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();

        // Hilo para enviar mensajes por Broadcast
    }

    /**
     * Envia un mensaje a través de un broadcast UDP a todos los dispositivos en la misma red.
     *
     * @param mensaje El mensaje que se enviará a los otros usuarios.
     * @param usuarioEnviado El nombre del usuario que envía el mensaje.
     */
    public static void enviarMensaje(String mensaje, String usuarioEnviado) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);  // Habilita el envío por broadcast
            InetAddress direccionBroadcast = InetAddress.getByName(BROADCAST_IP);
            InetAddress localHost = InetAddress.getLocalHost();
            String localIp = localHost.getHostAddress();
            String mensajeCompleto = usuarioEnviado + "--" + mensaje + "--" + localIp;
            byte[] buffer = mensajeCompleto.getBytes();
            DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, direccionBroadcast, PUERTO);
            socket.send(paquete);
            System.out.println("Mensaje enviado por broadcast → " + mensaje);
        } catch (Exception e) {
            System.err.println("Error en el envío: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método de inicio de la aplicación JavaFX. Configura y muestra la interfaz gráfica
     * utilizando el controlador principal.
     *
     * @param stage El escenario principal de la aplicación JavaFX.
     * @throws IOException Si ocurre un error al cargar los recursos necesarios.
     */
    @Override
    public void start(Stage stage) throws IOException {
        new ControladorPrincipal(stage);
    }
}
