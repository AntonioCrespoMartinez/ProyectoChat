import java.util.LinkedList;
import java.util.Scanner;

public class UsuariosConectados {
    static LinkedList<String> listaUsuarios = new LinkedList<>();
    static Scanner sc = new Scanner(System.in);

    public static String agregarUsuario() {
        String nombre;
        while (true) {
            System.out.println("Ingrese el nombre de usuario:");
            nombre = sc.nextLine();

            if (listaUsuarios.contains(nombre)) {
                System.out.println("❌ Usuario ya existente. Intente con otro nombre.");
            } else {
                listaUsuarios.add(nombre);
                System.out.println("✅ Usuario agregado correctamente.");
                break; // Sale del bucle cuando el usuario es válido
            }
        }
        return nombre;
    }

    public static void borrarUsuario(String usuarioLocal) {
        listaUsuarios.removeIf(nombre -> nombre.equalsIgnoreCase(usuarioLocal));
        System.out.println("Usuario eliminado: " + usuarioLocal);
    }

    public static void mostrarListaUsuarios() {
        if (listaUsuarios.isEmpty()) {
            System.out.println("No hay usuarios conectados.");
        } else {
            System.out.println("Usuarios conectados:");
            for (String nombre : listaUsuarios) {
                System.out.println(nombre + " está conectad@.");
            }
        }
    }
    public static void main(String[] args) {
        String nombre = agregarUsuario();
        mostrarListaUsuarios();
        borrarUsuario(nombre);
        mostrarListaUsuarios();
    }
}
