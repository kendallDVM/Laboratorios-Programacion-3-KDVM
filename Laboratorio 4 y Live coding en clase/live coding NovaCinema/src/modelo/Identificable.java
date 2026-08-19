package modelo;

public interface Identificable <K> {

    String getCodigo();
    String getNombre();
    K getCategoria();
    double calcularPrecio();
    String getDetalle();

}
