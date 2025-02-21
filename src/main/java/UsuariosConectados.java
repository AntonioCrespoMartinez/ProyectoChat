import java.util.LinkedList;
import java.util.Scanner;

public class UsuariosConectados {
    // Lista enlazada para almacenar los nombres de los usuarios conectados
    static LinkedList<String> listaUsuarios = new LinkedList<>();

    // Scanner para leer la entrada del usuario
    static Scanner sc = new Scanner(System.in);

    /**
     * Método para solicitar el nombre de usuario y agregarlo a la lista.
     * Si el usuario ya existe, mostrará un mensaje de error y pedirá otro nombre.
     * @return El nombre de usuario que ha sido agregado a la lista.
     */
    public static String agregarUsuario() {
        String nombre;

        while (true) { // Bucle infinito hasta que se ingrese un nombre válido
            System.out.println("Ingrese el nombre de usuario:");
            nombre = sc.nextLine(); // Captura la entrada del usuario

            // Verifica si el usuario ya existe en la lista
            if (listaUsuarios.contains(nombre)) {
                System.out.println("❌ Usuario ya existente. Intente con otro nombre.");
            } else {
                // Agrega el nombre a la lista y sale del bucle
                listaUsuarios.add(nombre);
                System.out.println("✅ Usuario agregado correctamente.");
                break;
            }
        }

        return nombre; // Retorna el nombre del usuario registrado
    }

    /**
     * Método para eliminar un usuario de la lista.
     * Elimina al usuario si existe, sin distinguir entre mayúsculas y minúsculas.
     * @param usuarioLocal Nombre del usuario que se desea eliminar.
     */
    public static void borrarUsuario(String usuarioLocal) {
        // Utiliza removeIf para eliminar cualquier nombre que coincida sin importar mayúsculas/minúsculas
        listaUsuarios.removeIf(nombre -> nombre.equalsIgnoreCase(usuarioLocal));

        // Muestra mensaje de confirmación
        System.out.println("Usuario eliminado: " + usuarioLocal);
    }

    /**
     * Método para mostrar la lista de usuarios conectados.
     * Si no hay usuarios en la lista, muestra un mensaje indicando que no hay usuarios conectados.
     */
    public static void mostrarListaUsuarios() {
        if (listaUsuarios.isEmpty()) { // Verifica si la lista está vacía
            System.out.println("No hay usuarios conectados.");
        } else {
            System.out.println("Usuarios conectados:");

            // Recorre la lista y muestra cada usuario
            for (String nombre : listaUsuarios) {
                System.out.println(nombre + " está conectad@.");
            }
        }
    }

    /**
     * Método principal que ejecuta el programa.
     * - Solicita un nombre de usuario y lo agrega a la lista.
     * - Muestra la lista de usuarios conectados.
     * - Borra el usuario recién agregado.
     * - Muestra nuevamente la lista de usuarios.
     * @param args Argumentos de la línea de comandos (no utilizados en este programa).
     */
    public static void main(String[] args) {
        // Agrega un usuario y almacena su nombre
        String nombre = agregarUsuario();

        // Muestra la lista de usuarios después de agregar el nuevo usuario
        mostrarListaUsuarios();

        // Elimina el usuario recién agregado
        borrarUsuario(nombre);

        // Muestra la lista de usuarios después de la eliminación
        mostrarListaUsuarios();
    }
}
