package Lab02.modelo;
import java.util.Objects;

public class Profesor extends Persona {
    private String idEmpleado;
    private String departamento;

    public Profesor(String nombre, int edad, String idEmpleado, String departamento) {
        super(nombre, edad);
        this.idEmpleado = idEmpleado;
        this.departamento = departamento;
    }

    public String getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(String idEmpleado) { this.idEmpleado = idEmpleado; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    @Override
    public String getDescripcion() {
        return "Profesor: " + getNombre() +
                " | ID: " + idEmpleado +
                " | Departamento: " + departamento;
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
        Profesor profesor = (Profesor) obj;
        return Objects.equals(idEmpleado, profesor.idEmpleado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idEmpleado);
    }



}
