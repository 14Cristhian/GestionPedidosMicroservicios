package co.uceva.edu.base.beans;
import co.uceva.edu.base.models.Categoria;
import co.uceva.edu.base.models.Departamento;
import co.uceva.edu.base.models.Usuario;
import co.uceva.edu.base.services.*;
import co.uceva.edu.base.util.ConexionBaseDatos;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Named
@SessionScoped
public class DepartamentoForm implements Serializable {

    private  Long id;
    private String nombre;
    private Departamento DepartamentoEditar;



    public List<Departamento> listar() throws SQLException {
        DepartamentoService departamentoService = new DepartamentoServiceJdbcImpl(ConexionBaseDatos.getConnection());
        return departamentoService.listar();
    }

    public String crear() {
        try {
            DepartamentoService departamentoService = new DepartamentoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Departamento departamento = new Departamento();
            departamento.setId(this.id);
            departamento.setNombre(this.nombre);
            departamentoService.guardar(departamento);
            return "listar-departamento.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "listar-departamento.xhtml?faces-redirect=true";
        }
    }


    public String irEditar(Long idEditar){

        try {
            DepartamentoService departamentoService = new DepartamentoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Optional<Departamento> departamento = departamentoService.porId(idEditar);

            if(departamento.isPresent()){
                this.DepartamentoEditar = departamento.get();
                return "editar-departamento.xhtml?faces-redirect=true";
            }else{
                return "listar-departamento.xhtml?faces-redirect=true";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "crear-departamento.xhtml?faces-redirect=true";
        }
    }


    public String Editar(String accion) {
        try {
            DepartamentoService departamentoService = new DepartamentoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Departamento departamento = new Departamento();
            if ("ACTUALIZA".equals(accion)) {

                departamento.setId(this.DepartamentoEditar.getId());
                departamento.setNombre(this.DepartamentoEditar.getNombre());


            }
            departamentoService.guardar(departamento);
            return "listar-departamento.xhtml?faces-redirect=true";
        } catch (SQLException  e) {
            e.printStackTrace();
            return "crear-departamento.xhtml?faces-redirect=true";
        }

    }

    public String borrar(Long id){
        try {
            DepartamentoService departamentoService = new DepartamentoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            departamentoService.eliminar(id);
            return "listar-departamento.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "listar-departamento.xhtml?faces-redirect=true";
        }
    }


    public Departamento getDepartamentoEditar() {
        return DepartamentoEditar;
    }

    public void setDepartamentoEditar(Departamento departamentoEditar) {
        DepartamentoEditar = departamentoEditar;
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



}
