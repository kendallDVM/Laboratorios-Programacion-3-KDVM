// Indica que esta clase vive dentro del paquete lab.flotavehicular.model
package lab.flotavehicular.model;

// Interfaz: define un CONTRATO, no la implementación
public interface Mantenible {

    // Todo vehículo que implemente esto DEBE saber evaluar su estado
    String evaluarEstadoGeneral();
}
         