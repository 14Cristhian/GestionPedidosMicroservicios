package co.uceva.edu.base.beans;
import co.uceva.edu.base.models.Ciudad;
import co.uceva.edu.base.models.Cliente;
import co.uceva.edu.base.models.TelefonoCliente;
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
public class ClienteForm implements Serializable {

    private  Long cedula;
    private Long idMunicipio;
    private String nombre;
    private String apellido;
    private  String direccion;
    private List<Cliente> clientes;
    private List<SelectItem> municipios;
    private Cliente clienteVer;
    private Long telefono;

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

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

    public List<TelefonoCliente> listarTelefonos() throws SQLException {
        //TODO: Consultar los telefonos de un cliente (porCliente)
        //Todo: Repository(todo menos Form)
        TelefonoClienteService telefonoClienteService = new TelefonoClienteServiceJdbcImpl(ConexionBaseDatos.getConnection());
        List<TelefonoCliente> telefonos =  telefonoClienteService.listar();
        List<TelefonoCliente> telefonosCliente = new ArrayList<>();
        Iterator<TelefonoCliente> iterator= telefonos.iterator();
        while(iterator.hasNext()){
            TelefonoCliente telefono1= iterator.next();
            if(telefono1.getIdCliente().equals(clienteVer.getCedula())){
             telefonosCliente.add(telefono1);
            }
        }
        return  telefonosCliente;

    }
    public List<Cliente> listar() throws SQLException {
        ClienteService clienteService = new ClienteServiceJdbcImpl(ConexionBaseDatos.getConnection());
        return clienteService.listar();
    }


    public String irCrear() throws SQLException {
        this.init();
        return "crear-cliente.xhtml?faces-redirect=true";
    }

    public void agregarTelefono() throws SQLException {
     TelefonoClienteService telefonoClienteService = new TelefonoClienteServiceJdbcImpl(ConexionBaseDatos.getConnection());

        System.out.println("Agregando "+this.telefono);

        TelefonoCliente telefono= new TelefonoCliente();
        telefono.setTelefono(this.telefono);
        telefono.setIdCliente(this.clienteVer.getCedula());
         telefonoClienteService.guardar(telefono);


    }

    public String crear() {
        try {
            ClienteService clienteService = new ClienteServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Cliente cliente = new Cliente();
            cliente.setCedula(this.cedula);
            cliente.setNombre(this.nombre);
            cliente.setApellido(this.apellido);
            cliente.setDireccion(this.direccion);
            cliente.setIdMunicipio(this.idMunicipio);
            clienteService.guardar(cliente);
            return "listar-cliente.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "crear-cliente.xhtml?faces-redirect=true";
        }
    }

    public String ver (Long id){
        try {
            ClienteService clienteService = new ClienteServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Optional<Cliente> cliente = clienteService.porId(id);
            if(cliente.isPresent()){
                this.clienteVer=cliente.get();
                return "ver-cliente.xhtml?faces-redirect=true";
            }else{
                return "listar-cliente.xhtml?faces-redirect=true";
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return "listar-cliente.xhtml?faces-redirect=true";
        }
    }
    public String borrar(Long id){
        try {
            ClienteService clienteService = new ClienteServiceJdbcImpl(ConexionBaseDatos.getConnection());
            clienteService.eliminar(id);
            return "listar-cliente.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "listar-cliente.xhtml?faces-redirect=true";
        }
    }


    public List<SelectItem> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<SelectItem> municipios) {
        this.municipios = municipios;
    }
    public Long getCedula() {
        return cedula;
    }

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

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
    public Cliente getClienteVer() {
        return clienteVer;
    }

    public void setClienteVer(Cliente clienteVer) {
        this.clienteVer = clienteVer;
    }

}
