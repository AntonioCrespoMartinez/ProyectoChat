import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Emisor {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress ip = InetAddress.getByName("255.255.255.255"); // Broadcast
            int puerto = 5000;
            Scanner scanner = new Scanner(System.in);

            System.out.println("Introduce tu mensaje:");
            String mensaje = scanner.nextLine();
            byte[] buffer = mensaje.getBytes();

            DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, ip, puerto);
            socket.send(paquete);

            System.out.println("Mensaje enviado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

