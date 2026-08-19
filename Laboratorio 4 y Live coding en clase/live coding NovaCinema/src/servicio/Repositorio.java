package servicio;

import modelo.Identificable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repositorio <T extends Identificable<?>> {
    private List<T> items = new ArrayList<>();

    public void agregar(T item){
        this.items.add(item);
    }

    public List<T> optenerTodos(){
        return this.items;
    }

    public Optional<T> buscarPorCodigo(String codigo){

        for(T item : items){

            if(item.getCodigo().equalsIgnoreCase(codigo)){
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }
}


