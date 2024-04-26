package co.uceva.edu.base.beans;

import co.uceva.edu.base.models.Ciudad;
import co.uceva.edu.base.models.Departamento;
import co.uceva.edu.base.models.Usuario;
import co.uceva.edu.base.reportes.ReporteUsuarioPorTipo;
import co.uceva.edu.base.services.*;
import co.uceva.edu.base.util.ConexionBaseDatos;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Named
@SessionScoped
public class CiudadForm implements Serializable {

    private  Long id;
    private String nombre;
    private Long idDepartamento;
    private List<SelectItem> departamentos;
    private Ciudad MunicipioEditar;


    public void iniciarDepartamentos() throws SQLException{

        System.out.println("Iniciando postConstructor.");
        DepartamentoService departamentoService = new DepartamentoServiceJdbcImpl(ConexionBaseDatos.getConnection());
        List<Departamento> departamentosList = departamentoService.listar();
        /*
         * inicializamos Departamentos
         *
         * */
        Iterator<Departamento> iterator= departamentosList.iterator();
        departamentos = new ArrayList<SelectItem>();
        while(iterator.hasNext()){
            Departamento departamento = iterator.next();
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(departamento.getNombre());
            selectItem.setValue(departamento.getId());
            departamentos.add(selectItem);
        }

    }

    public List<Ciudad> listar() throws SQLException {
        CiudadService ciudadService = new CiudadServiceJdbcImpl(ConexionBaseDatos.getConnection());
        return ciudadService.listar();
    }

    public String irCrear() throws SQLException {
        this.iniciarDepartamentos();
        return "crear-ciudad.xhtml?faces-redirect=true";
    }


    public String crear() {
        try {
            CiudadService ciudadService = new CiudadServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Ciudad ciudad = new Ciudad();
            ciudad.setId(this.id);
            ciudad.setNombre(this.nombre);
            ciudad.setIdDepartamento(this.idDepartamento);
            ciudadService.guardar(ciudad);
            return "listar-ciudades.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "crear-ciudad.xhtml?faces-redirect=true";
        }
    }


    public String irEditar(Long idEditar) throws SQLException {
        this.iniciarDepartamentos();
        try {
            CiudadService ciudadService = new CiudadServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Optional<Ciudad> ciudad = ciudadService.porId(idEditar);

            if(ciudad.isPresent()){
                this.MunicipioEditar = ciudad.get();
                return "editar-municipio.xhtml?faces-redirect=true";
            }else{
                return "listar-ciudades.xhtml?faces-redirect=true";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "crear-ciudad.xhtml?faces-redirect=true";
        }


    }


    public String Editar(String accion) {
        try {
            CiudadService ciudadService = new CiudadServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Ciudad ciudad = new Ciudad();;
            if ("ACTUALIZA".equals(accion)) {

                ciudad.setId(this.MunicipioEditar.getId());
                ciudad.setNombre(this.MunicipioEditar.getNombre());
                ciudad.setIdDepartamento(this.MunicipioEditar.getIdDepartamento());
            }
            ciudadService.guardar(ciudad);
            return "listar-ciudades.xhtml?faces-redirect=true";
        } catch (SQLException  e) {
            e.printStackTrace();
            return "crear-ciudad.xhtml?faces-redirect=true";
        }

    }



    public String borrar(Long id){
        try {
            CiudadService ciudadService = new CiudadServiceJdbcImpl(ConexionBaseDatos.getConnection());
            ciudadService.eliminar(id);
            return "listar-ciudades.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "listar-ciudades.xhtml?faces-redirect=true";
        }
    }


    public Ciudad getMunicipioEditar() {
        return MunicipioEditar;
    }

    public void setMunicipioEditar(Ciudad municipioEditar) {
        MunicipioEditar = municipioEditar;
    }

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

    public List<SelectItem> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<SelectItem> departamentos) {
        this.departamentos = departamentos;
    }

    public Long getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Long idDepartamento) {
        this.idDepartamento = idDepartamento;
    }



}
