import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Receptor {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(5000)) {
            byte[] buffer = new byte[1024];
            DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);

            System.out.println("Esperando mensajes...");
            socket.receive(paquete);

            String mensaje = new String(paquete.getData(), 0, paquete.getLength());
            System.out.println("Mensaje recibido: " + mensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

