package cr.ac.una.est.minidiscord.network;

import cr.ac.una.est.minidiscord.business.model.Message;
import cr.ac.una.est.minidiscord.business.model.User;
import java.time.LocalDateTime;

public final class ChatProtocol {

    public static final int PUERTO = 5050;
    private ChatProtocol() {}

    public static String codificar(Message mensaje) {
        return "MSG|" + mensaje.getAuthor().getUsername() + "|"
                + mensaje.getSentAt() + "|" + mensaje.getContent();
    }

    public static Message decodificar(String linea) {
        String[] partes = linea.split("\\|", 4);

        if (partes.length != 4 || !"MSG".equals(partes[0])) {
            throw new IllegalArgumentException("El servidor envio un mensaje invalido");
        }
        return new Message(new User(partes[1]), partes[3], LocalDateTime.parse(partes[2]));
    }

    public static String saludo(String username) { return "HELLO|" + username; }

    public static String solicitudMensaje(String contenido) { return "SEND|" + contenido; }
}
