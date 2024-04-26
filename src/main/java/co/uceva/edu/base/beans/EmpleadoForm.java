package co.uceva.edu.base.beans;

import co.uceva.edu.base.models.*;
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
public class EmpleadoForm implements Serializable {

    private  Long cedula;
    private Long idtienda;
    private Long idmunicipio;
    private String tipoSeleccionado;
    private String apellido;
    private  String nombre;
    private List<SelectItem> municipios;
    private List<SelectItem> tiendas;
    private List<SelectItem> tipos;
    private Empleado EmpleadoEditar;

    @PostConstruct
    public void init() throws SQLException {
//TODO:Agregar filto por departamento
        System.out.println("Iniciando postConstructor.");
        CiudadService ciudadService=new CiudadServiceJdbcImpl(ConexionBaseDatos.getConnection());
        List<Ciudad> municipioList = ciudadService.listar();
        Iterator<Ciudad> iterator= municipioList.iterator();
        municipios = new ArrayList<SelectItem>();
        while(iterator.hasNext()){
            Ciudad ciudad = iterator.next();
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(ciudad.getNombre());
            selectItem.setValue(ciudad.getId());
            municipios.add(selectItem);
        }
        System.out.println("Cantidad de municipios:" +municipios.size());
        TiendaService tiendaService=new TiendaServiceJdbcImpl(ConexionBaseDatos.getConnection());
        List<Tienda> tiendaList = tiendaService.listar();

        Iterator<Tienda> iterator2= tiendaList.iterator();
        tiendas = new ArrayList<SelectItem>();
        while(iterator2.hasNext()){
            Tienda tienda = iterator2.next();
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(tienda.getDirecciont());
//            selectItem.setLabel(String.valueOf(tienda.getId()));
            selectItem.setValue(tienda.getId());
            tiendas.add(selectItem);
        }


        tipos = new ArrayList<SelectItem>();
        SelectItem selectItem = new SelectItem();
        selectItem.setLabel("Cocinero");
        selectItem.setValue("Cocinero");
        tipos.add(selectItem);

        selectItem = new SelectItem();
        selectItem.setLabel("Repartidor");
        selectItem.setValue("Repartidor");
        tipos.add(selectItem);

    }





    public List<Empleado> listar() throws SQLException {
        EmpleadoService empleadoService = new EmpleadoServiceJdbcImpl(ConexionBaseDatos.getConnection());
        return empleadoService.listar();
    }


    public String irCrear() throws SQLException {
        this.init();
        return "crear-empleado.xhtml?faces-redirect=true";
    }


    public String crear() {
        try {
            EmpleadoService empleadoService = new EmpleadoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Empleado empleado = new Empleado();
            empleado.setCedula(this.cedula);
            empleado.setNombre(this.nombre);
            empleado.setApellido(this.apellido);
            empleado.setTipo(this.tipoSeleccionado);
            empleado.setIdtienda(this.idtienda);
            empleado.setIdmunicipio(this.idmunicipio);
            empleadoService.guardar(empleado);
            return "listar-empleado.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "crear-empleado.xhtml?faces-redirect=true";
        }
    }




    public String Editar(String accion) {
        try {
            EmpleadoService empleadoService = new EmpleadoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Empleado empleado = new Empleado();
            if ("ACTUALIZA".equals(accion)) {

                empleado.setCedula(this.EmpleadoEditar.getCedula());
                empleado.setNombre(this.EmpleadoEditar.getNombre());
                empleado.setApellido(this.EmpleadoEditar.getApellido());
                empleado.setIdtienda(this.EmpleadoEditar.getIdtienda());
                empleado.setTipo(this.EmpleadoEditar.getTipo());
                empleado.setIdmunicipio(this.EmpleadoEditar.getIdmunicipio());

            }
            empleadoService.guardar(empleado);
            return "listar-empleado.xhtml?faces-redirect=true";
        } catch (SQLException  e) {
            e.printStackTrace();
            return "crear-empleado.xhtml?faces-redirect=true";
        }

    }


    public String irEditar(Long idEditar) throws SQLException {
                try {
            EmpleadoService empleadoService = new EmpleadoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Optional<Empleado> empleado = empleadoService.porCedula(idEditar);

            if(empleado.isPresent()){
                this.EmpleadoEditar = empleado.get();
                return "editar-empleado.xhtml?faces-redirect=true";
            }else{
                return "listar-empleado.xhtml?faces-redirect=true";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "crear-empleado.xhtml?faces-redirect=true";
        }


    }



    public String borrar(Long id){
        try {
            EmpleadoService empleadoService = new EmpleadoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            empleadoService.eliminar(id);
            return "listar-empleado.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "listar-empleado.xhtml?faces-redirect=true";
        }
    }

    public Empleado getEmpleadoEditar() {
        return EmpleadoEditar;
    }

    public void setEmpleadoEditar(Empleado empleadoEditar) {
        this.EmpleadoEditar = empleadoEditar;
    }

    public List<SelectItem> getTipos() {
        return tipos;
    }

    public void setTipos(List<SelectItem> tipos) {
        this.tipos = tipos;
    }

    public List<SelectItem> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<SelectItem> municipios) {
        this.municipios = municipios;
    }

    public List<SelectItem> getTiendas() {
        return tiendas;
    }

    public void setTiendas(List<SelectItem> tiendas) {
        this.tiendas = tiendas;
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

    public String getTipoSeleccionado() {
        return tipoSeleccionado;
    }

    public void setTipoSeleccionado(String tipoSeleccionado) {
        this.tipoSeleccionado = tipoSeleccionado;
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
