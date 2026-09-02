package modelo;

public abstract class ElementoComercial<K> implements Identificable <K>{

    protected String codigo;
    protected String nombre;
    protected double precioBase;
    protected K categoria;

    public ElementoComercial(String codigo, String nombre, double precioBase , K categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.categoria = categoria;

    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    @Override
    public K getCategoria() {
        return categoria;
    }
}
