import java.io.*;
import java.net.*;
import java.util.*;

public class ChatBidireccional {
    private static final int DEFAULT_PORT = 5000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Quieres actuar como servidor o cliente? (s/c)");
        String opcion = scanner.nextLine().trim().toLowerCase();

        if (opcion.equals("s")) {
            iniciarServidor();
        } else if (opcion.equals("c")) {
            System.out.print("Introduce la IP del servidor: ");
            String ipServidor = scanner.nextLine().trim();
            iniciarCliente(ipServidor);
        } else {
            System.out.println("Opción no válida. Debes ingresar 's' o 'c'.");
        }
    }

    // Servidor que maneja múltiples clientes
    private static void iniciarServidor() {
        Set<PrintWriter> clientes = new HashSet<>();
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            System.out.println("Servidor iniciado en el puerto " + DEFAULT_PORT + ". Esperando conexiones...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                // Crear un nuevo hilo para manejar cada cliente
                Thread hiloCliente = new Thread(() -> manejarCliente(socket, clientes));
                hiloCliente.start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    // Servidor maneja la conexión de un cliente específico
    private static void manejarCliente(Socket socket, Set<PrintWriter> clientes) {
        try (
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)
        ) {
            synchronized (clientes) {
                clientes.add(salida);
            }

            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("Mensaje recibido: " + mensaje);
                enviarAMultiplosClientes(mensaje, clientes);  // Enviar mensaje a todos los clientes conectados
            }
        } catch (IOException e) {
            System.err.println("Error en la comunicación con el cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el socket del cliente: " + e.getMessage());
            }
        }
    }

    // Función para hacer broadcast a todos los clientes conectados
    private static void enviarAMultiplosClientes(String mensaje, Set<PrintWriter> clientes) {
        synchronized (clientes) {
            for (PrintWriter cliente : clientes) {
                cliente.println(mensaje);
            }
        }
    }

    // Cliente se conecta al servidor y puede enviar y recibir mensajes
    private static void iniciarCliente(String ipServidor) {
        try (Socket socket = new Socket(ipServidor, DEFAULT_PORT);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado al servidor en " + ipServidor + ":" + DEFAULT_PORT);

            // Hilo para recibir mensajes de otros clientes
            Thread recibirMensajes = new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = entrada.readLine()) != null) {
                        System.out.println("\nMensaje recibido: " + mensaje);
                        System.out.print("Tú: ");
                    }
                } catch (IOException e) {
                    System.err.println("Error en la recepción de mensajes.");
                }
            });
            recibirMensajes.start();

            // Enviar mensajes al servidor
            while (true) {
                System.out.print("Tú: ");
                String mensaje = scanner.nextLine();
                if (mensaje.equalsIgnoreCase("salir")) {
                    break;
                }
                salida.println(mensaje);
            }

        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}
