package modelo;

public class ProductoDulceria extends ElementoComercial <TipoProducto>{

    public ProductoDulceria(String codigo, String nombre, double precioBase, TipoProducto categoria) {
        super(codigo, nombre, precioBase, categoria);
    }
    @Override
    public double calcularPrecio(){
        if (categoria == TipoProducto.COMBO){
            return precioBase * 0.90;
        }
        return precioBase;
    }

    @Override
    public String getDetalle(){
        return String.format("Codigo: %s\n Producto: %s\n Tipo: %s\n Precio: %.2f",
                codigo,nombre,categoria,calcularPrecio()
        );
    }
}
