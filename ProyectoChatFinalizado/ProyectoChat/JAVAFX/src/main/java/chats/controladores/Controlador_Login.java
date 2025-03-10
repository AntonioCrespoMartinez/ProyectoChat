package chats.controladores;

import chats.vistas.Vista_ChatPublico;
import chats.vistas.Vista_Login;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Esta clase representa el controlador encargado de gestionar la lógica de
 * inicio de sesión en la aplicación.
 * Extiende de {@link Controlador} y se encarga de interactuar con la vista de
 * inicio de sesión para que el usuario ingrese sus credenciales.
 */
public class Controlador_Login extends Controlador {

    private static Vista_Login vista;
    private ControladorPrincipal controlador;

    /**
     * Constructor de la clase {@link Controlador_Login}.
     * Inicializa el controlador y la vista de inicio de sesión.
     *
     * @param controlador El controlador principal que maneja la lógica general de la aplicación.
     * @throws IOException Si ocurre un error al cargar la vista de inicio de sesión.
     */
    public Controlador_Login(ControladorPrincipal controlador) throws IOException{
        this.controlador = controlador;
        vista = new Vista_Login(this);
    }

    /**
     * Inicia sesión en la aplicación con el nombre de usuario proporcionado.
     * Este método invoca al controlador principal para procesar el inicio de sesión.
     *
     * @param nombreUsuario El nombre de usuario ingresado por el usuario.
     */
    public void iniciarSesion(String nombreUsuario){
        controlador.iniciarSesion(nombreUsuario);
    }

    /**
     * Muestra la vista de inicio de sesión en la pantalla.
     * Este método es una implementación del método abstracto {@link Controlador#mostrar()}.
     */
    @Override
    public void mostrar() {
        vista.mostrar();
    }

    /**
     * Obtiene la escena que contiene la vista de inicio de sesión.
     *
     * @return La escena que muestra la vista de inicio de sesión.
     */
    public Scene getEscena1() {
        return vista.getEscena();
    }
}
