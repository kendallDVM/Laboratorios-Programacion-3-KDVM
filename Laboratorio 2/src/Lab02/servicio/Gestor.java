package Lab02.servicio;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Gestor<T> {
    private List<T> elementos;

    public Gestor() {
        this.elementos = new ArrayList<>();
    }

    public Gestor(List<T> elementos) {
        this.elementos = new ArrayList<>(elementos);
    }

    public void agregar(T elemento) { elementos.add(elemento); }

    @SafeVarargs
    public final void agregarTodos(T... elementos) {
        this.elementos.addAll(Arrays.asList(elementos));
    }

    public boolean eliminar(T elemento) { return elementos.remove(elemento); }

    public List<T> getTodos() { return new ArrayList<>(elementos); }

    public Optional<T> buscarPrimero(Predicate<T> condicion) {
        return elementos.stream().filter(condicion).findFirst();
    }


    public List<T> filtrar(Predicate<T> condicion) {
        return elementos.stream().filter(condicion).collect(Collectors.toList());
    }

    public long contarSi(Predicate<T> condicion) {
        return elementos.stream().filter(condicion).count();
    }

    public void ordenar(Comparator<T> comparador) { elementos.sort(comparador); }

    public boolean estaVacio() { return elementos.isEmpty(); }
    public int tamaño() { return elementos.size(); }
    public void limpiar() { elementos.clear(); }

    public void imprimirTodos() {
        if (elementos.isEmpty()) {
            System.out.println("No hay elementos para mostrar.");
            return;
        }
        elementos.forEach(System.out::println);
    }

}
