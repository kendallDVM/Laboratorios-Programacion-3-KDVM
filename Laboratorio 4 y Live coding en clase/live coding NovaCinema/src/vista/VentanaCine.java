package vista;

import modelo.FuncionPelicula;
import modelo.ProductoDulceria;
import modelo.TipoFormato;
import modelo.TipoProducto;
import servicio.CineServicio;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class VentanaCine extends JFrame {
    private JPanel panelRaiz;
    private JPanel panelEncabezado;
    private JLabel lblTitulo;
    private JTabbedPane tabPrincipal;
    private JPanel tabCartelera;
    private JPanel pnlFormPelicula;
    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtDuracion;
    private JTextField txtSala;
    private JTextField txtCapacidad;
    private JTextField txtPrecioBase;
    private JComboBox cbxFormato;
    private JScrollPane pnlTablaCartelera;
    private JTextArea txaCartelera;
    private JButton btnPromoEstudiante;
    private JPanel pnlBotones;
    private JButton btnVenderBoletos;
    private JButton btnBuscarFunciones;
    private JButton btnRegistrarPeli;
    private JPanel pnlFromCartelera;
    private JTextField txtNombreDulceria;
    private JComboBox cbxTipoDulceria;
    private JTextField txtPrecioDulceria;
    private JTextArea txaDulceria;
    private JButton btnRegistrarDulceria;
    private JButton btnMostrarCatalogoDulceria;
    private JButton btnVenderDulceria;
    private JButton btnPromoDulceria;
    private JTextField txtCodigoDulceria;

    private final CineServicio cineServicio = new CineServicio();

    public VentanaCine() {
        setTitle("NovaCinemas - Gestion de cine");
        setContentPane(panelRaiz);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);

        setLocationRelativeTo(null);

        cbxFormato.setModel(new DefaultComboBoxModel<>(TipoFormato.values()));
        cbxFormato.setModel(new DefaultComboBoxModel<>(TipoFormato.values()));

        configurarEventos();


    }

    private void configurarEventos() {
        btnRegistrarPeli.addActionListener(e -> {
            try {
                String codigo = txtCodigo.getText().trim();
                String titulo = txtTitulo.getText().trim();
                int duracion = Integer.parseInt(txtDuracion.getText().trim());
                int sala = Integer.parseInt(txtSala.getText().trim());
                int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
                double precio = Double.parseDouble(txtPrecioBase.getText().trim());
                TipoFormato formato = (TipoFormato) cbxFormato.getSelectedItem();

                FuncionPelicula pelicula = new FuncionPelicula(
                        codigo, titulo, precio, formato, sala, duracion, capacidad
                );

                cineServicio.registrarPelicula(pelicula);
                actualizarCartelera();
                limpiarCamposCartelera();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Revise los campos numericos",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVenderBoletos.addActionListener(e -> {
            String codigo = JOptionPane.showInputDialog(this,
                    "Digite el codigo de la funcion:");
            if (codigo == null || codigo.isBlank()) {
                return;
            }

            Optional<FuncionPelicula> resultado = cineServicio.buscarPelicula(codigo.trim());

            if (resultado.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No se encontro una funcion con el codigo",
                        "Sin Resultado",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            FuncionPelicula pelicula = resultado.get();

            String textoCantidad = JOptionPane.showInputDialog(this,
                    "Pelicula: " + pelicula.getNombre() +
                            "\nButacas Disponibles: " + pelicula.getAsientosDisponibles() +
                            "\n\nCantidad de Boletos: "
            );
            if (textoCantidad == null || textoCantidad.isBlank()) {
                return;
            }
            try {
                int cantidad = Integer.parseInt(textoCantidad.trim());
                Double total = cineServicio.venderBoletos(codigo.trim(), cantidad);
                if (total == null) {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo realizar la venta\n" +
                                    "Verifique la cantidad y las butacas disponibles",
                            "venta no realizada",
                            JOptionPane.ERROR_MESSAGE

                    );
                    return;
                }

                JOptionPane.showMessageDialog(this,
                        String.format("Venta realizada correctamente" +
                                        "\n\nPelicula: %s" + "\n\nBoletos: %s" + "\ntotal: $%.2f",
                                pelicula.getNombre(), cantidad, total), "taquilla",
                        JOptionPane.ERROR_MESSAGE
                );
                actualizarCartelera();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Debe ingresar una cantidad valida",
                        "Error", JOptionPane.ERROR_MESSAGE
                );
            }

        });

        btnRegistrarDulceria.addActionListener(e -> {
            try {
                String codigo = txtCodigoDulceria.getText().trim();
                String nombre = txtNombreDulceria.getText().trim();
                double precio = Double.parseDouble(txtPrecioDulceria.getText().trim());
                TipoProducto tipo = (TipoProducto) cbxTipoDulceria.getSelectedItem();

                ProductoDulceria producto = new ProductoDulceria(codigo, nombre, precio, tipo);

                cineServicio.registrarProducto(producto);
                actualizarDulceria();
                limpiarCamposDulceria();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "ingrese un precio valido",
                        "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });
        btnVenderDulceria.addActionListener(e -> {
            String codigo = JOptionPane.showInputDialog(this, "Ingrese el codigo del producto o cambio");
            if (codigo == null || codigo.isBlank()) {
                return;
            }
            Optional<ProductoDulceria> resultado = cineServicio.buscarProducto(codigo.trim());

            if (resultado.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No se encontro un producto con el codigo" + codigo,
                        "Sin resultados",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            ProductoDulceria producto = resultado.get();

            String textoCantidad = JOptionPane.showInputDialog(this,
                    "Producto: " + producto.getNombre() + "\nTipo: " + producto.getCategoria()
                            + "\nPrecio: " + String.format("%.2f", producto.calcularPrecio()) + "\nCantidad");


            if (textoCantidad == null || textoCantidad.isBlank()) {
                return;
            }


            try {
                int cantidad = Integer.parseInt(textoCantidad.trim());
                Double total = cineServicio.vendeProducto(codigo.trim(), cantidad);

                if (total == null) {

                    JOptionPane.showMessageDialog(this,
                            "venta realizada correctamente",
                            "Sin resultados",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "ingrese una cantidad valida",
                        "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });

        btnBuscarFunciones.addActionListener(e -> {
            actualizarCartelera();
        });
        btnMostrarCatalogoDulceria.addActionListener(e -> {
            actualizarDulceria();
        });
        btnPromoEstudiante.addActionListener(e -> {
            txaCartelera.setText("----------Promocion Estudiante ---------");

            for (FuncionPelicula pelicula : cineServicio.obtenerCartelera()) {
                double precioNormal = pelicula.calcularPrecio();
                double precioPromocional = pelicula.calcularPrecio() * 0.9;

                txaCartelera.append(String.format("%s\n Precio normal: $%.2f\n Precio Estudiante: $%.2f\n ahorro: $%.2f\n------------------------\n\n",
                        pelicula.getNombre(), precioNormal, precioPromocional, precioNormal - precioPromocional));

            }
        });

        btnPromoDulceria.addActionListener(e -> {
            txaDulceria.setText("----------Promocion Dulceria ---------");
            for (ProductoDulceria producto : cineServicio.obtenerDulceria()) {
                double precioNormal = producto.calcularPrecio();
                double precioPromocional = producto.calcularPrecio() * 0.9;

                txaDulceria.append(String.format("%s\n Tipo: %s\n Precio Normal: $%.2f\n Precio Estudiante: $%.2f\n ahorro: $%.2f\n------------------------\n\n",
                        producto.getNombre(), producto.getCategoria(), precioNormal, precioPromocional, precioNormal - precioPromocional));

            }


        });


    }

    private void actualizarCartelera() {
        txaCartelera.setText("---------Cartelera ---------");
        for (FuncionPelicula pelicula : cineServicio.obtenerCartelera()) {
            txaCartelera.append(pelicula.getDetalle() + "\n\n");
        }


    }

    private void actualizarDulceria() {
        txaDulceria.setText("---------Dulceria ---------");
        for (ProductoDulceria producto : cineServicio.obtenerDulceria()) {
            txaDulceria.append(producto.getDetalle() + "\n\n");
        }
    }

    private void limpiarCamposCartelera() {
        txtCodigo.setText("");
        txtTitulo.setText("");
        txtDuracion.setText("");
        txtSala.setText("");
        txtCapacidad.setText("");
        txtPrecioBase.setText("");
        cbxFormato.setSelectedIndex(0);

        txtCodigo.requestFocus();
    }

    private void limpiarCamposDulceria () {
        txtCodigoDulceria.setText("");
        txtNombreDulceria.setText("");
        txtPrecioDulceria.setText("");
        cbxTipoDulceria.setSelectedIndex(0);

        txtCodigoDulceria.requestFocus();
    }
}





