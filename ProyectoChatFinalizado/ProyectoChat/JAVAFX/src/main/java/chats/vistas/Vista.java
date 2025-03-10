package chats.vistas;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Clase abstracta que sirve como base para las vistas en la aplicación.
 * Proporciona un método común para cargar una interfaz FXML y asociarla con un controlador.
 */
public abstract class Vista {

    /**
     * Carga la interfaz definida en un archivo FXML y asocia el controlador de la vista.
     *
     * @param ficheroInterfaz El nombre del archivo FXML que contiene la definición de la interfaz.
     * @param vista El controlador asociado a la vista que se cargará.
     * @return El objeto {@link Parent} que representa la interfaz cargada.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    protected Parent cargarInterfaz(String ficheroInterfaz, Vista vista) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Vista.class.getResource(ficheroInterfaz));
        fxmlLoader.setController(vista);
        return fxmlLoader.load();
    }

    /**
     * Método para mostrar la vista. Este método puede ser sobrecargado por las clases hijas
     * para implementar la lógica de visualización específica.
     */
    public void mostrar(){}
}
