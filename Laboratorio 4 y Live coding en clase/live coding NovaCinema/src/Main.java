import vista.VentanaCine;

import javax.swing.*;

public static void main(String[] args) {
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignore) {
        // Si falla el Look & Feel, continuamos con el predeterminado
    }

    SwingUtilities.invokeLater(() -> {
        VentanaCine ventana = new VentanaCine();
        ventana.setVisible(true);
    });
}
