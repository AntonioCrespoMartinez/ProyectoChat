import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserManager {
    private static final int PORT = 5000; // Puerto de comunicación
    private static final String MULTICAST_ADDRESS = "255.255.255.255"; // Dirección multicast
    private static final Map<String, String> users = new ConcurrentHashMap<>();
    private String localIP;
    private String username;
    private ScheduledExecutorService executor;
    private MulticastSocket socket;
    private InetAddress group;

    public UserManager(String username) {
        this.username = username;
        this.localIP = getLocalIPAddress();
        users.put(localIP, username);
        startListening();
        startBroadcasting();
    }

    private String getLocalIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    private void startListening() {
        new Thread(() -> {
            try {
                socket = new MulticastSocket(PORT);
                group = InetAddress.getByName(MULTICAST_ADDRESS);
                socket.joinGroup(group);

                byte[] buffer = new byte[256];
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());

                    if (message.startsWith("JOIN:")) {
                        String[] parts = message.substring(5).split(",");
                        if (parts.length == 2) {
                            users.put(parts[0], parts[1]);
                            showConnectedUsers();
                        }
                    } else if (message.startsWith("LEAVE:")) {
                        String leavingIP = message.substring(6);
                        users.remove(leavingIP);
                        showConnectedUsers();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startBroadcasting() {
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            try {
                DatagramSocket socket = new DatagramSocket();
                String message = "JOIN:" + localIP + "," + username;
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                socket.send(packet);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void stop() {
        try {
            executor.shutdown();
            String message = "LEAVE:" + localIP;
            DatagramSocket socket = new DatagramSocket();
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
            socket.send(packet);
            socket.close();

            users.remove(localIP);
            showConnectedUsers();

            if (this.socket != null) {
                this.socket.leaveGroup(group);
                this.socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showConnectedUsers() {
        System.out.println("Usuarios conectados:");
        users.forEach((ip, name) -> System.out.println(ip + " -> " + name));
    }
}

