package co.uceva.edu.base.models;

import javax.faces.model.SelectItem;
import java.util.List;

public class Categoria {

    private  Long id;
    private String nombre;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
