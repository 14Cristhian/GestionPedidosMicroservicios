package co.uceva.edu.base.beans;

import co.uceva.edu.base.models.Pedido;
import co.uceva.edu.base.models.Tienda;
import co.uceva.edu.base.models.Usuario;
import co.uceva.edu.base.services.*;
import co.uceva.edu.base.util.ConexionBaseDatos;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

@Named
@SessionScoped
public class PedidoForm implements Serializable {

    private  Long UID_Pedido;
    private Long id_tienda;
    private double precio_total;
    private String fecha;
    private List<SelectItem> tiendas;
    private Pedido pedidoeditar;

    String timeStamp = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(Calendar.getInstance().getTime());




    @PostConstruct
    public void init() throws SQLException {
//TODO:Agregar filto por departamento
        System.out.println("Iniciando postConstructor.");
        TiendaService tiendaService=new TiendaServiceJdbcImpl(ConexionBaseDatos.getConnection());
        List<Tienda> TiendaList = tiendaService.listar();
        /*
         * inicializamos Departamentos
         *
         * */
        Iterator<Tienda> iterator= TiendaList.iterator();
        tiendas = new ArrayList<SelectItem>();
        while(iterator.hasNext()){
            Tienda tienda = iterator.next();
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(tienda.getDirecciont());
            selectItem.setValue(tienda.getId());
            tiendas.add(selectItem);
        }

    }

    public List<Pedido> listar() throws SQLException {
        PedidoService pedidoService = new PedidoServiceJdbcImpl(ConexionBaseDatos.getConnection());
        return pedidoService.listar();
    }




    public String irCrear() throws SQLException {
        this.init();
        return "crear-pedido.xhtml?faces-redirect=true";
    }



    public String irEditar(Long idEditar){
        try {
            PedidoService pedidoServiced = new PedidoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Optional<Pedido> pedido = pedidoServiced.porUID_Pedido(idEditar);

            if(pedido.isPresent()){
                this.pedidoeditar = pedido.get();
                return "editar-pedido.xhtml?faces-redirect=true";
            }else{
                return "listar-pedido.xhtml?faces-redirect=true";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "crear-pedido.xhtml?faces-redirect=true";
        }
    }


    public String Editar(String accion) {
        try {
            PedidoService pedidoService = new PedidoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Pedido pedido = new Pedido();
            if ("ACTUALIZA".equals(accion)) {

                pedido.setUID_Pedido(this.pedidoeditar.getUID_Pedido());
                pedido.setId_tienda(this.pedidoeditar.getId_tienda());
                pedido.setPrecio_total(this.pedidoeditar.getPrecio_total());
                pedido.setTimeStamp(this.pedidoeditar.getTimeStamp());
                System.out.println("Hora"+ timeStamp);
            }
            pedidoService.guardar(pedido);
            return "listar-pedido.xhtml?faces-redirect=true";
        } catch (SQLException  e) {
            e.printStackTrace();
            return "crear-pedido.xhtml?faces-redirect=true";
        }

    }

    public String crear() {
        try {
            PedidoService pedidoService = new PedidoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Pedido pedido = new Pedido();
            pedido.setUID_Pedido(this.UID_Pedido);
            pedido.setId_tienda(this.id_tienda);
            pedido.setPrecio_total(this.precio_total);
            pedido.setTimeStamp(timeStamp);
            System.out.println("Hora"+ timeStamp);
            pedidoService.guardar(pedido);
            return "listar-pedido.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "crear-pedido.xhtml?faces-redirect=true";
        }
    }

    public String borrar(Long id){
        try {
            PedidoService pedidoService = new PedidoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            pedidoService.eliminar(id);
            return "listar-pedido.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "listar-pedido.xhtml?faces-redirect=true";
        }
    }

    public Pedido getPedidoeditar() {
        return pedidoeditar;
    }

    public void setPedidoeditar(Pedido pedidoeditar) {
        this.pedidoeditar = pedidoeditar;
    }

    public Long getUID_Pedido() {
        return UID_Pedido;
    }

    public void setUID_Pedido(Long UID_Pedido) {
        this.UID_Pedido = UID_Pedido;
    }

    public Long getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(Long id_tienda) {
        this.id_tienda = id_tienda;
    }

    public double getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(double precio_total) {
        this.precio_total = precio_total;
    }

    public List<SelectItem> getTiendas() {
        return tiendas;
    }

    public void setTiendas(List<SelectItem> tiendas) {
        this.tiendas = tiendas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}
