package Lab02.modelo;
import java.util.Objects;

// ver si edad es int o string

public abstract class Persona {
    private String nombre;
    private int edad;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {return this.nombre;}
    public int getEdad() {return this.edad;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setEdad(int edad) {this.edad = edad;}

    public abstract String getDescripcion();

    @Override
    public String toString() {
        return "Persona{nombre='" + nombre + "', edad=" + edad + "}";
    }


    @Override
    public boolean equals(Object obj) {
        // 1. ¿Es el MISMO objeto en memoria? Si es la misma referencia, son iguales
        if (this == obj) return true;

        // 2. ¿Es null o es de otra clase? Si no es Persona, no pueden ser iguales
        if (obj == null || getClass() != obj.getClass()) return false;

        // 3. Convertimos (casteamos) el Object a Persona para comparar atributos
        Persona persona = (Persona) obj;

        // 4. Comparamos atributo por atributo:
        //    edad == persona.edad → compara el int directamente (primitivo)
        //    Objects.equals(nombre, persona.nombre) → compara Strings de forma segura (soporta null)
        return edad == persona.edad && Objects.equals(nombre, persona.nombre);
    }

    // Genera un código hash basado en los mismos atributos que usa equals()
// Si dos objetos son iguales (equals=true), DEBEN tener el mismo hashCode
    @Override
    public int hashCode() {
        return Objects.hash(nombre, edad);
    }
}
