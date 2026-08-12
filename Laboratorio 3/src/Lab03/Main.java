// Paquete principal de la aplicación
package Lab03;

// Importa la ventana creada en el paquete vista
import Lab03.vista.VentanaRegistro;

// Importa utilidades de Swing para lanzar la interfaz en el hilo correcto (EDT)
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        // Lanza la creación de la GUI en el hilo de eventos de Swing (EDT)
        SwingUtilities.invokeLater(() -> {

            // Construye la ventana llamando a su constructor
            VentanaRegistro ventana = new VentanaRegistro();

            // La hace visible (por defecto las ventanas nacen invisibles)
            ventana.setVisible(true);
        });

    }
}