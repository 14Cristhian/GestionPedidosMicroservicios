package co.uceva.edu.base.beans;

import co.uceva.edu.base.models.Usuario;
import co.uceva.edu.base.reportes.ReporteUsuarioPorTipo;
import co.uceva.edu.base.services.UsuarioService;
import co.uceva.edu.base.services.UsuarioServiceJdbcImpl;
import co.uceva.edu.base.util.ConexionBaseDatos;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
@SessionScoped
public class UsuarioForm implements Serializable {

    private  Long id;
    private String nombre;
    private String login;
    private String password;
    private Usuario usuarioEditar;
    private List<SelectItem> departamentos;
    private List<SelectItem> ciudades;
    private List<Usuario> usuarios;
    private String departamentoSeleccionado;
    private String ciudadSeleccionada;

    @PostConstruct
    public void init(){

        System.out.println("Iniciando postConstructor");

        /*
         * inicializamos Departamentos
         *
         * */
        departamentos = new ArrayList<SelectItem>();
        SelectItem selectItem = new SelectItem();
        selectItem.setLabel("Valle del Cauca");
        selectItem.setValue(1);
        departamentos.add(selectItem);

        selectItem = new SelectItem();
        selectItem.setLabel("Antioquia");
        selectItem.setValue(2);

        departamentos.add(selectItem);

    }


    public List<Usuario> listar() throws SQLException {
        UsuarioService usuarioService = new UsuarioServiceJdbcImpl(ConexionBaseDatos.getConnection());
        return usuarioService.listar();
    }

    public List<ReporteUsuarioPorTipo> usuarioPorTipo() throws SQLException {
        UsuarioService usuarioService = new UsuarioServiceJdbcImpl(ConexionBaseDatos.getConnection());
        return usuarioService.usuarioPorTipo();
    }

    public String crear() {
        String nombreFoto = null;
        try {

            UsuarioService usuarioService = new UsuarioServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Usuario usuario = new Usuario();
            usuario.setId(this.id);
            usuario.setLogin(this.login);
            usuario.setNombre(this.nombre);
            usuario.setPassword(this.password);

            usuarioService.guardar(usuario);

            System.out.println(id);
            System.out.println(nombre);
            System.out.println(login);
            System.out.println(password);
            return "listar-usuarios.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "registrar-usuario.xhtml?faces-redirect=true";
        }
    }


    public String Editar(String accion) {
        try {
            UsuarioService usuarioService = new UsuarioServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Usuario usuario = new Usuario();
            if ("ACTUALIZA".equals(accion)) {

                usuario.setId(this.usuarioEditar.getId());
                usuario.setLogin(this.usuarioEditar.getLogin());
                usuario.setNombre(this.usuarioEditar.getNombre());
                usuario.setPassword(this.usuarioEditar.getPassword());
            }
            usuarioService.guardar(usuario);
            return "listar-usuarios.xhtml?faces-redirect=true";
        } catch (SQLException  e) {
            e.printStackTrace();
            return "registrar-usuario.xhtml?faces-redirect=true";
        }

    }


        public String irEditar(Long idEditar){

        try {
            UsuarioService usuarioService = new UsuarioServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Optional<Usuario> usuario = usuarioService.porId(idEditar);

            if(usuario.isPresent()){
                this.usuarioEditar = usuario.get();
                return "editar-usuario.xhtml?faces-redirect=true";
            }else{
                return "listar-usuarios.xhtml?faces-redirect=true";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "registrar-usuario.xhtml?faces-redirect=true";
        }
    }



    public String borrar(Long id){

        try {
            UsuarioService usuarioService = new UsuarioServiceJdbcImpl(ConexionBaseDatos.getConnection());
            usuarioService.eliminar(id);
            return "listar-usuarios.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "listar-usuarios.xhtml?faces-redirect=true";
        }
    }

    public void cambioDepartamento(AjaxBehaviorEvent event){
        System.out.println(departamentoSeleccionado);

        /*
         * inicializamos Municipios
         *
         * */
        SelectItem selectItem = new SelectItem();


        if("1".equals(departamentoSeleccionado)){
            ciudades = new ArrayList<SelectItem>();
            selectItem = new SelectItem();
            selectItem.setLabel("Tulua");
            selectItem.setValue(1);
            ciudades.add(selectItem);

            selectItem = new SelectItem();
            selectItem.setLabel("Cali");
            selectItem.setValue(2);
            ciudades.add(selectItem);
        }

        if("2".equals(departamentoSeleccionado)) {
            ciudades = new ArrayList<SelectItem>();
            selectItem = new SelectItem();
            selectItem.setLabel("Medellín");
            selectItem.setValue(3);
            ciudades.add(selectItem);

            selectItem = new SelectItem();
            selectItem.setLabel("Ituango");
            selectItem.setValue(4);

            ciudades.add(selectItem);
        }

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SelectItem> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<SelectItem> departamentos) {
        this.departamentos = departamentos;
    }

    public List<SelectItem> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<SelectItem> ciudades) {
        this.ciudades = ciudades;
    }
    public Usuario getUsuarioEditar() {
        return usuarioEditar;
    }

    public void setUsuarioEditar(Usuario usuarioEditar) {
        this.usuarioEditar = usuarioEditar;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getDepartamentoSeleccionado() {
        return departamentoSeleccionado;
    }

    public void setDepartamentoSeleccionado(String departamentoSeleccionado) {
        this.departamentoSeleccionado = departamentoSeleccionado;
    }
    public String getCiudadSeleccionada() {
        return ciudadSeleccionada;
    }

    public void setCiudadSeleccionada(String ciudadSeleccionada) {
        this.ciudadSeleccionada = ciudadSeleccionada;
    }



}