package modelo;

public class FuncionPelicula extends ElementoComercial <TipoFormato>{

    private int sala;
    private int duracionMinutos;
    private int capacidadTotal;
    private int asientosOcupados;

    public FuncionPelicula(String codigo, String nombre, double precioBase, TipoFormato categoria, int sala, int duracionMinutos, int capacidadTotal) {

        super(codigo, nombre, precioBase, categoria);
        this.sala = sala;
        this.duracionMinutos = duracionMinutos;
        this.capacidadTotal = capacidadTotal;
        this.asientosOcupados = 0;
    }

    public int getSala() {
        return sala;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public int getCapacidadTotal() {
        return capacidadTotal;
    }

    public int getAsientosOcupados() {
        return asientosOcupados;
    }

    public int getAsientosDisponibles(){
        return capacidadTotal - asientosOcupados;
    }

    public boolean venderBoletos(int cantidad) {
        if (cantidad > 0 && cantidad <= getAsientosDisponibles()) {
            this.asientosOcupados += cantidad;
            return true;
        }
        return false;
    }

    @Override
    public double calcularPrecio() {
        return switch (categoria){
            case DOS_D -> precioBase;
            case TRES_D ->  precioBase + 1000;
            case IMAX -> precioBase + 2000;
            case VIP -> precioBase + 3000;
        };

    }

    @Override
    public String getDetalle(){
        return String.format("Codigo: %s\n Pelicula: %s\n Formato: %s\n Sala: %d\n Duracion: %d minutos\n Butacas: %d / %d\n Precio: $%.2f",
                codigo,nombre,categoria,sala,duracionMinutos,getAsientosDisponibles(), capacidadTotal ,calcularPrecio()

        );
    }
}
