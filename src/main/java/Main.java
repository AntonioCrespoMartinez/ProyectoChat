import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/layout.fxml"));
        AnchorPane root = loader.load(); // Usamos AnchorPane porque es el contenedor raíz

        // Crear la escena
        Scene scene = new Scene(root);

        // Agregar el CSS (si no está en el FXML)
        scene.getStylesheets().add(getClass().getResource("/estilos/estilos.css").toExternalForm());

        // Configurar la ventana principal
        primaryStage.setTitle("Aplicación JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        System.out.println("HOLA");
    }
}
