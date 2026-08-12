package Lab03.modelo;

public class Estudiante {

    private String carnet;
    private String nombre;
    private String carrera;

    public Estudiante(String carnet, String nombre, String carrera) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.carrera = carrera;
    }

    public String getCarnet(){
        return carnet;
    }
    public String getNombre(){
        return nombre;
    }
    public String getCarrera(){
        return carrera;
    }

    @Override
    public String toString(){
        return carnet + " " + nombre + " " + carrera;
    }
}
