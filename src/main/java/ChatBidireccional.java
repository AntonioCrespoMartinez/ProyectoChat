import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ChatBidireccional {
//d
    private static final int PUERTO = 5000;
    private static final String BROADCAST_IP = "255.255.255.255";

    public static void main(String[] args) {
        // Iniciar hilo para recibir mensajes
        new Thread(ChatBidireccional::recibirMensajes).start();

        // Iniciar el envío de mensajes
        enviarMensajes();
    }

    public static void recibirMensajes() {
        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                socket.receive(paquete);
                String mensaje = new String(paquete.getData(), 0, paquete.getLength());
                System.out.println("\nMensaje recibido: " + mensaje);
                System.out.print("Tú: ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void enviarMensajes() {
        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {
            InetAddress direccion = InetAddress.getByName(BROADCAST_IP);
            socket.setBroadcast(true);

            while (true) {
                System.out.print("Tú: ");
                String mensaje = scanner.nextLine();
                byte[] buffer = mensaje.getBytes();
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, direccion, PUERTO);
                socket.send(paquete);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

