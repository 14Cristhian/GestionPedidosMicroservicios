package co.uceva.edu.base.beans;

import co.uceva.edu.base.models.Categoria;
import co.uceva.edu.base.models.Departamento;
import co.uceva.edu.base.models.Usuario;
import co.uceva.edu.base.services.*;
import co.uceva.edu.base.util.ConexionBaseDatos;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Named
@SessionScoped
public class CategoriaForm implements Serializable {

    private  Long id;
    private String nombre;
    private Categoria edicarCategoria;



    public List<Categoria> listar() throws SQLException {
        CategoriaService categoriaService = new CategoriaServiceJdbcImpl(ConexionBaseDatos.getConnection());
        return categoriaService.listar();
    }

    public String crear() {
        try {
            CategoriaService categoriaService = new CategoriaServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Categoria categoria = new Categoria();
            categoria.setId(this.id);
            categoria.setNombre(this.nombre);
            categoriaService.guardar(categoria);
            return "listar-categoria.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "crear-categoria.xhtml?faces-redirect=true";
        }
    }


    public String irEditar(Long idEditar){
        try {
            CategoriaService categoriaService = new CategoriaServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Optional<Categoria> categoria = categoriaService.porId(idEditar);

            if(categoria.isPresent()){
                this.edicarCategoria = categoria.get();
                return "editar-categoria.xhtml?faces-redirect=true";
            }else{
                return "listar-categoria.xhtml?faces-redirect=true";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "crear-categoria.xhtml?faces-redirect=true";
        }

    }



    public String Editar(String accion) {
        try {
            CategoriaService categoriaService = new CategoriaServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Categoria categoria = new Categoria();
            if ("ACTUALIZA".equals(accion)) {

                categoria.setId(this.edicarCategoria.getId());
                categoria.setNombre(this.edicarCategoria.getNombre());

            }
            categoriaService.guardar(categoria);
            return "listar-categoria.xhtml?faces-redirect=true";
        } catch (SQLException  e) {
            e.printStackTrace();
            return "crear-categoria.xhtml?faces-redirect=true";
        }

    }



    public String borrar(Long id){
        try {
            CategoriaService categoriaService = new CategoriaServiceJdbcImpl(ConexionBaseDatos.getConnection());
            categoriaService.eliminar(id);
            return "listar-categoria.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "listar-categoria.xhtml?faces-redirect=true";
        }
    }


    public Categoria getEdicarCategoria() {
        return edicarCategoria;
    }

    public void setEdicarCategoria(Categoria edicarCategoria) {
        this.edicarCategoria = edicarCategoria;
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
