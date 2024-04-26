package co.uceva.edu.base.models;

public class Cliente {

    private  Long cedula;
    private Long idMunicipio;
    private String nombre;
    private String apellido;
    private  String direccion;

    public Long getCedula() {return cedula;}

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public Long getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Long idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
