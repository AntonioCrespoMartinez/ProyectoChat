module chats.vistas {
    requires javafx.controls;
    requires javafx.fxml;

    opens chats.vistas to javafx.fxml;
    exports chats;
}
