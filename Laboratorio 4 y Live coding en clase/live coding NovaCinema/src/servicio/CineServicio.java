package servicio;

import modelo.FuncionPelicula;
import modelo.ProductoDulceria;

import java.util.List;
import java.util.Optional;

public class CineServicio {
    private final Repositorio<FuncionPelicula> repoCartelera;
    private final Repositorio<ProductoDulceria> repoDulceria;

    public CineServicio() {
        this.repoCartelera = new Repositorio<>();
        this.repoDulceria = new Repositorio<>();
    }
    public void registrarPelicula(FuncionPelicula pelicula){
        repoCartelera.agregar(pelicula);
    }


    public List<FuncionPelicula> obtenerCartelera(){
        return repoCartelera.optenerTodos();
    }

    public Optional<FuncionPelicula> buscarPelicula(String codigo){
        return repoCartelera.buscarPorCodigo(codigo);
    }

    public Double venderBoletos(String codigo, int cantidad){
        Optional<FuncionPelicula> resultado = buscarPelicula(codigo);
        if(resultado.isEmpty()){
            return null;
        }
        FuncionPelicula pelicula = resultado.get();

        if(!pelicula.venderBoletos(cantidad)){
            return null;
        }
        return pelicula.calcularPrecio() * cantidad;
    }

    public Double aplicarDescuentoPelicula(FuncionPelicula pelicula){
        return pelicula.calcularPrecio() * 0.80;
    }

    public void registrarProducto(ProductoDulceria producto){
        repoDulceria.agregar(producto);
    }

    public List<ProductoDulceria> obtenerDulceria(){
        return repoDulceria.optenerTodos();
    }

    public Optional<ProductoDulceria> buscarProducto(String codigo){
        return repoDulceria.buscarPorCodigo(codigo);
    }

    public Double vendeProducto(String codigo, int cantidad){
        Optional<ProductoDulceria> resultado = buscarProducto(codigo);

        if (resultado.isEmpty()){
            return null;
        }
        ProductoDulceria producto = resultado.get();

        if (cantidad <= 0) {
            return null;
        }
        return producto.calcularPrecio() * cantidad;
    }

    public Double aplicarDescuentoDulceria(ProductoDulceria producto){
        return producto.calcularPrecio() * 0.90;
    }
}
