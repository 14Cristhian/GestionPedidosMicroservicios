package co.uceva.edu.base.models;

public class Departamento {
    Long id;
    String nombre;

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

    public void guardar(Departamento departamento) {
    }
}
