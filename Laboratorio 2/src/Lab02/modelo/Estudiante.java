package Lab02.modelo;

// Para crear la lista dinámica de notas (new ArrayList<>())
import java.util.ArrayList;

// Para declarar el tipo de la variable (List<Double> notas)
import java.util.List;

// Para equals() y hashCode() seguros (soporta null)
import java.util.Objects;



public class Estudiante extends Persona implements Comparable<Estudiante> {


    private String carnet;
    private String carrera;
    private List<Double> notas;

    public Estudiante(String nombre, int edad, String carnet, String carrera) {
        super(nombre, edad);
        this.carnet = carnet;
        this.carrera = carrera;
        this.notas = new ArrayList<>();
    }

    public Estudiante(String nombre, int edad, String carnet, String carrera, List<Double> notas) {
        this(nombre, edad, carnet, carrera);
        this.notas = new ArrayList<>(notas);
    }

    public String getCarnet() { return carnet; }
    public void setCarnet(String carnet) { this.carnet = carnet; }
    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
    public List<Double> getNotas() { return notas; }
    public void setNotas(List<Double> notas) { this.notas = new ArrayList<>(notas); }


    public void agregarNota(double nota) {
        if (nota < 0 || nota > 100) {
            throw new IllegalArgumentException("La nota debe estar entre 0 y 100");
        }
        this.notas.add(nota);
    }

    public double getPromedio() {
        if (notas.isEmpty()) return 0.0;
        return notas.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    @Override
    public String getDescripcion() {
        return "Estudiante: " + getNombre() +
                " | Carnet: " + carnet +
                " | Carrera: " + carrera +
                " | Promedio: " + getPromedio();
    }

    @Override
    public int compareTo(Estudiante otro) {
        return Double.compare(otro.getPromedio(), this.getPromedio());
    }

    @Override
    public String toString() {
        return getDescripcion();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Estudiante that = (Estudiante) obj;
        return Objects.equals(carnet, that.carnet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), carnet);
    }


}