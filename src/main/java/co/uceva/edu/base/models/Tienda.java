package co.uceva.edu.base.models;

public class Tienda {

    private  Long id;
    private Long idmunicipio;
    private String direcciont;

    public String getFormato(){
        if(idmunicipio==0){
            return "NULL";
        }else{
            return String.valueOf(idmunicipio);

        }

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(Long id_municipio) {
        this.idmunicipio = id_municipio;
    }

    public String getDirecciont() {
        return direcciont;
    }

    public void setDirecciont(String direcciont) {
        this.direcciont = direcciont;
    }
}
