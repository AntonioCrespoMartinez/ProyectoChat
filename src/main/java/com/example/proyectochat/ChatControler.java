package com.example.proyectochat;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatControler {
    private static final int PORT = 9876;
    private DatagramSocket socket;
    private InetAddress broadcastAddress;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private volatile boolean running = true;

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField chatInput;

    @FXML
    private ListView<String> userList;

    public void initialize() {
        try {
            socket = new DatagramSocket(PORT);
            broadcastAddress = getBroadcastAddress();
            executorService.execute(this::receiveMessages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSendMessage() {
        String message = chatInput.getText();
        if (!message.isEmpty()) {
            sendMessage("Tú: " + message);
            chatInput.clear();
        }
    }

    private void receiveMessages() {
        try {
            byte[] buffer = new byte[1024];
            while (running) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                chatArea.appendText("\n" + receivedMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, PORT);
            socket.send(packet);
            chatArea.appendText("\n" + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private InetAddress getBroadcastAddress() throws SocketException, UnknownHostException {
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
            InetAddress broadcast = interfaceAddress.getBroadcast();
            if (broadcast != null) {
                return broadcast;
            }
        }
        throw new UnknownHostException("No se pudo determinar la dirección de broadcast");
    }

    public void shutdown() {
        running = false;
        socket.close();
        executorService.shutdown();
    }
}

