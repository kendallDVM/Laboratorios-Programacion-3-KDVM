package Lab02;

import Lab02.modelo.Estudiante;
import Lab02.modelo.Profesor;
import Lab02.servicio.Gestor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {

        Estudiante ana = new Estudiante("Ana Rodríguez", 20, "B20250001", "Ingeniería en Sistemas");
        ana.agregarNota(85.5);
        ana.agregarNota(92.0);
        ana.agregarNota(78.0);
// ... crea más estudiantes

        Profesor prof1 = new Profesor("Dra. Elena Rojas", 45, "P-001", "Ingeniería en Sistemas");
        Profesor prof2 = new Profesor("Dr. Mario Castro", 52, "P-002", "Administración");

        Gestor<Estudiante> gestorEstudiantes = new Gestor<>();
        gestorEstudiantes.agregarTodos(ana, carlos, maria, luis, laura);

        gestorEstudiantes.imprimirTodos();

        List<Estudiante> destacados = gestorEstudiantes.filtrar(e -> e.getPromedio() > 85);
        destacados.forEach(System.out::println);

        List<Estudiante> ordenados = gestorEstudiantes.getTodos();
        ordenados.sort(Comparator.naturalOrder());
        ordenados.forEach(e -> System.out.println(e.getNombre() + " → " + e.getPromedio()));


        Optional<Estudiante> encontrado = gestorEstudiantes.buscarPrimero(e -> e.getNombre().contains("Luis"));
        encontrado.ifPresentOrElse(
                e -> System.out.println("Estudiante encontrado: " + e.getDescripcion()),
                () -> System.out.println("No se encontró ningún estudiante con ese nombre.")
        );

        var conteoPorCarrera = gestorEstudiantes.getTodos().stream()
                .collect(Collectors.groupingBy(Estudiante::getCarrera, Collectors.counting()));
        conteoPorCarrera.forEach((carrera, cantidad) ->
                System.out.println(carrera + " → " + cantidad + " estudiante(s)")
        );

        double promedioGeneral = gestorEstudiantes.getTodos().stream()
                .mapToDouble(Estudiante::getPromedio)
                .average()
                .orElse(0.0);
        System.out.println("El promedio general de todos los estudiantes es: " + promedioGeneral);

        Gestor<Profesor> gestorProfesores = new Gestor<>();
        gestorProfesores.agregarTodos(prof1, prof2);
        gestorProfesores.imprimirTodos();



    }
}