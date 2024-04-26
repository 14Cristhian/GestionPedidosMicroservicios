package co.uceva.edu.base.beans;
import co.uceva.edu.base.models.Categoria;
import co.uceva.edu.base.models.Ciudad;
import co.uceva.edu.base.models.Cliente;
import co.uceva.edu.base.models.Tienda;
import co.uceva.edu.base.services.*;
import co.uceva.edu.base.util.ConexionBaseDatos;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Named
@SessionScoped

public class TiendaForm implements Serializable {
    private  Long id;
    private Long idmunicipio;
    private String direcciont;
    private List<Tienda> tiendas;
    private List<SelectItem> municipios;
    private Tienda edicarTienda;

    @PostConstruct
    public void init() throws SQLException {
//TODO:Agregar filto por departamento
        System.out.println("Iniciando postConstructor.");
        CiudadService ciudadService=new CiudadServiceJdbcImpl(ConexionBaseDatos.getConnection());
        List<Ciudad> municipioList = ciudadService.listar();
        /*
         * inicializamos Departamentos
         *
         * */
        Iterator<Ciudad> iterator= municipioList.iterator();
        municipios = new ArrayList<SelectItem>();
        while(iterator.hasNext()){
            Ciudad ciudad = iterator.next();
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(ciudad.getNombre());
            selectItem.setValue(ciudad.getId());
            municipios.add(selectItem);
        }

    }



    public List<Tienda> listar() throws SQLException {
        TiendaService tiendaService = new TiendaServiceJdbcImpl(ConexionBaseDatos.getConnection());
        return tiendaService.listar();
    }

    public String irCrear() throws SQLException {
        this.init();
        return "crear-tienda.xhtml?faces-redirect=true";
    }

    public String crear() {
        try {
            TiendaService tiendaService = new TiendaServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Tienda tienda = new Tienda();
            tienda.setId(this.id);
            tienda.setIdmunicipio(this.idmunicipio);
            tienda.setDirecciont(this.direcciont);
            tiendaService.guardar(tienda);
            return "listar-tienda.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "crear-tienda.xhtml?faces-redirect=true";
        }
    }


    public String borrar(Long id){
        try {
            TiendaService tiendaService = new TiendaServiceJdbcImpl(ConexionBaseDatos.getConnection());
            tiendaService.eliminar(id);
            return "listar-tienda.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "listar-tienda.xhtml?faces-redirect=true";
        }
    }




    public String irEditar(Long idEditar){
        try {
            TiendaService tiendaService = new TiendaServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Optional<Tienda> tienda = tiendaService.porId(idEditar);

            if(tienda.isPresent()){
                this.edicarTienda = tienda.get();
                return "editar-tienda.xhtml?faces-redirect=true";
            }else{
                return "listar-tienda.xhtml?faces-redirect=true";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "crear-tienda.xhtml?faces-redirect=true";
        }

    }

    public String Editar(String accion) {
        try {
            TiendaService tiendaService = new TiendaServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Tienda tienda = new Tienda();
            if ("ACTUALIZA".equals(accion)) {

                tienda.setId(this.edicarTienda.getId());
                tienda.setDirecciont(this.edicarTienda.getDirecciont());
                tienda.setIdmunicipio(this.edicarTienda.getIdmunicipio());
            }
            tiendaService.guardar(tienda);
            return "listar-tienda.xhtml?faces-redirect=true";
        } catch (SQLException  e) {
            e.printStackTrace();
            return "crear-tienda.xhtml?faces-redirect=true";
        }

    }


    public Tienda getEdicarTienda() {
        return edicarTienda;
    }

    public void setEdicarTienda(Tienda edicarTienda) {
        this.edicarTienda = edicarTienda;
    }

    public List<Tienda> getTiendas() {
        return tiendas;
    }

    public void setTiendas(List<Tienda> tiendas) {
        this.tiendas = tiendas;
    }

    public List<SelectItem> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<SelectItem> municipios) {
        this.municipios = municipios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long UID_Tienda) {
        this.id = UID_Tienda;
    }

    public Long getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(Long idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    public String getDirecciont () {
        return direcciont;
    }

    public void setDirecciont(String direcciont) {
        this.direcciont = direcciont;
    }


}
