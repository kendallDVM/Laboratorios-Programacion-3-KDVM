package lab.flotavehicular.repository;

import lab.flotavehicular.model.Vehiculo;
import java.util.List;

/**
 * Contrato de persistencia. El Service no sabe si los datos están en TXT o MySQL.
 * Cada operación expresa una intención de dominio y no una reescritura total de la tabla.
 */
public interface RepositorioVehiculo {
    List<Vehiculo> cargarTodos();
    void insertar(Vehiculo vehiculo);
    void actualizar(Vehiculo vehiculo);
    void eliminar(String placa);
}