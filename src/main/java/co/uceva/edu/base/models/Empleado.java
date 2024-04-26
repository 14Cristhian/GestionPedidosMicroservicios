package co.uceva.edu.base.models;

public class Empleado {

    private  Long cedula;
    private Long idtienda;
    private Long idmunicipio;
    private String tipo;
    private String apellido;
    private  String nombre;

    public String getFormato(){
        if(idmunicipio==0){
            return "NULL";
        } else{
            return String.valueOf(idmunicipio);

        }

    }

    public String getFormato2(){
        if(idtienda==0){
            return "NULL";
        } else{
            return String.valueOf(idtienda);

        }

    }



    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public Long getIdtienda() {
        return idtienda;
    }

    public void setIdtienda(Long idtienda) {
        this.idtienda = idtienda;
    }

    public Long getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(Long idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
