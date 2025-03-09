package com.example.proyectochat;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {
    private static final int PORT = 9876;
    private DatagramSocket socket;
    private InetAddress broadcastAddress;
    private TextArea chatArea;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private volatile boolean running = true;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat Grupal");

        // Contenedor principal
        BorderPane root = new BorderPane();

        // Sección de usuarios conectados
        VBox userBox = new VBox(10);
        userBox.setPadding(new Insets(10));
        userBox.setStyle("-fx-background-color: #f4f4f4;");
        Label userLabel = new Label("Personas Conectadas");
        ListView<String> userList = new ListView<>();
        userList.getItems().addAll("Usuario 1", "Usuario 2", "Usuario 3");
        userBox.getChildren().addAll(userLabel, userList);

        // Sección de chat
        VBox chatBox = new VBox(10);
        chatBox.setPadding(new Insets(10));
        Label chatLabel = new Label("Chat Grupal");
        chatArea = new TextArea();
        chatArea.setEditable(false);
        TextField chatInput = new TextField();
        Button sendButton = new Button("Enviar");

        HBox inputBox = new HBox(5, chatInput, sendButton);
        chatBox.getChildren().addAll(chatLabel, chatArea, inputBox);

        // Evento de envío de mensaje
        sendButton.setOnAction(e -> {
            String message = chatInput.getText();
            if (!message.isEmpty()) {
                sendMessage("Tú: " + message);
                chatInput.clear();
            }
        });

        root.setLeft(userBox);
        root.setCenter(chatBox);

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        setupNetworking();
    }

    private void setupNetworking() {
        try {
            socket = new DatagramSocket(PORT);
            broadcastAddress = getBroadcastAddress();
            executorService.execute(this::receiveMessages);
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public void stop() throws Exception {
        running = false;
        socket.close();
        executorService.shutdown();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

