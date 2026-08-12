package Lab03.vista;

// Importa el modelo Estudiante para crear objetos con los datos ingresados
import Lab03.modelo.Estudiante;
// Importa todos los componentes gráficos de Swing (JFrame, JPanel, JButton...)
import javax.swing.*;
// Importa las herramientas de maquetación AWT (BorderLayout, Font, Color...)
import java.awt.*;

public class VentanaRegistro extends JFrame {

    // Caja de texto donde el usuario escribe el carnet
    private JTextField txtCarnet;

    // Caja de texto para el nombre completo
    private JTextField txtNombre;

    // Caja de texto para la carrera
    private JTextField txtCarrera;

    // Botón para procesar e inscribir al estudiante
    private JButton btnGuardar;

    // Botón para borrar las cajas de texto
    private JButton btnLimpiar;

    public VentanaRegistro() {
        // Título que aparece en la barra superior de la ventana
        setTitle("Sistema de Gestión de Estudiantes — UNA");

        // Dimensiones: 450 píxeles de ancho por 300 de alto
        setSize(450, 300);

        // Al tocar la 'X', el programa se detiene por completo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centra la ventana en la pantalla del monitor
        setLocationRelativeTo(null);

        // Contenedor principal con BorderLayout (5 regiones) y 10px de separación
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        // Margen interno de 15px para no pegar el contenido con el borde
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Etiqueta de texto centrada para el título del formulario
        JLabel lblTitulo = new JLabel("Inscripción de Estudiantes", SwingConstants.CENTER);

        // Aplica tipografía en negrita (BOLD) y tamaño 18
        lblTitulo.setFont(new Font("Nunito", Font.BOLD, 18));

        // Color del texto: rojo institucional UNA (RGB: 200, 16, 46)
        lblTitulo.setForeground(new Color(200, 16, 46));

        // Lo coloca en la región superior (NORTH) del panel principal
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Panel del formulario en cuadrícula de 3 filas x 2 columnas con 8px de separación
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 8, 8));

        // [Fila 1] Etiqueta + caja de texto para el carnet
        panelFormulario.add(new JLabel("Carnet:"));
        txtCarnet = new JTextField();
        panelFormulario.add(txtCarnet);

        // [Fila 2] Etiqueta + caja de texto para el nombre completo
        panelFormulario.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        // [Fila 3] Etiqueta + caja de texto para la carrera
        panelFormulario.add(new JLabel("Carrera:"));
        txtCarrera = new JTextField();
        panelFormulario.add(txtCarrera);

        // Coloca el formulario en el área central (CENTER) del panel principal
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Panel con FlowLayout alineado a la derecha, separación horizontal de 10px
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        // Botón para borrar los campos (etiqueta 'Limpiar')
        btnLimpiar = new JButton("Limpiar");

        // Botón para procesar e inscribir (etiqueta 'Guardar Estudiante')
        btnGuardar = new JButton("Guardar Estudiante");

        // Agrega ambos botones al panel secundario
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnGuardar);

        // Coloca el panel de botones en la región inferior (SOUTH)
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Registra un oyente: el código se ejecuta al hacer clic en 'Guardar'
        btnGuardar.addActionListener(e -> {

            // Lee el texto escrito en la caja del carnet
            String carnet  = txtCarnet.getText();

            // Lee el texto escrito en la caja del nombre
            String nombre  = txtNombre.getText();

            // Lee el texto escrito en la caja de la carrera
            String carrera = txtCarrera.getText();

            // Si alguna de las tres cadenas está vacía...
            if (carnet.isEmpty() || nombre.isEmpty() || carrera.isEmpty()) {

                // Muestra una ventana emergente de advertencia (WARNING)
                JOptionPane.showMessageDialog(this,
                        "Por favor complete todos los campos.",
                        "Campos Incompletos",
                        JOptionPane.WARNING_MESSAGE);
            }// Si todos los campos están completos...
            else {

                // Crea el objeto Estudiante en la capa de modelo con los datos leídos
                Estudiante nuevo = new Estudiante(carnet, nombre, carrera);

                // Muestra una ventana emergente informativa con los datos del registro
                JOptionPane.showMessageDialog(this,
                        "Estudiante registrado con éxito:\n" + nuevo.toString(),
                        "Registro Exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        });

        // Registra un oyente: se ejecuta al hacer clic en 'Limpiar'
        btnLimpiar.addActionListener(e -> {

            // Blanquea la caja del carnet
            txtCarnet.setText("");

            // Blanquea la caja del nombre
            txtNombre.setText("");

            // Blanquea la caja de la carrera
            txtCarrera.setText("");
        });

        // Ensambla el panel principal como contenido visible de la ventana
        this.add(panelPrincipal);

    }


}
