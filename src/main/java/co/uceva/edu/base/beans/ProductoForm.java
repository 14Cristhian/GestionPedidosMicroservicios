package co.uceva.edu.base.beans;

import co.uceva.edu.base.models.Categoria;
import co.uceva.edu.base.models.Ciudad;
import co.uceva.edu.base.models.Pedido;
import co.uceva.edu.base.models.Producto;
import co.uceva.edu.base.services.*;
import co.uceva.edu.base.util.ConexionBaseDatos;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
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
public class ProductoForm implements Serializable {

    private  Long UID_Producto;
    private Long id_pedido;
    private String idproductos;
    private Long idcategorias;
    private Part imagen_producto;
    private String nombre_producto;
    private Double precio_producto;
    private String descripcion_producto;
    private List<SelectItem> pedidos;
    private List<SelectItem> productos;
    private List<SelectItem> categorias;
    private Producto editarProducto;
    boolean flagCategoria = true;
    @PostConstruct
    public void init() throws SQLException {

        System.out.println("Iniciando postConstructor.");
        PedidoService pedidoService=new PedidoServiceJdbcImpl(ConexionBaseDatos.getConnection());
        List<Pedido> pedidoList = pedidoService.listar();
        /*
         * inicializamos Departamentos
         *
         * */
        Iterator<Pedido> iterator= pedidoList.iterator();
        pedidos = new ArrayList<SelectItem>();
        while(iterator.hasNext()){
            Pedido pedido = iterator.next();
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(String.valueOf(pedido.getUID_Pedido()));
            selectItem.setValue(pedido.getUID_Pedido());
            pedidos.add(selectItem);

        }
        CategoriaService categoriaService=new CategoriaServiceJdbcImpl(ConexionBaseDatos.getConnection());
        List<Categoria> categoriaList = categoriaService.listar();
        Iterator<Categoria> iterator2= categoriaList.iterator();
        categorias = new ArrayList<SelectItem>();
        while(iterator2.hasNext()) {
            Categoria categoria = iterator2.next();
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(categoria.getNombre());
            selectItem.setValue(categoria.getId());
            categorias.add(selectItem);

        }

        productos = new ArrayList<SelectItem>();
        SelectItem selectItem = new SelectItem();
        selectItem.setLabel("bebida");
        selectItem.setValue("bebida");
        productos.add(selectItem);

       selectItem = new SelectItem();
       selectItem.setLabel("pizza");
       selectItem.setValue("pizza");
       productos.add(selectItem);

        selectItem = new SelectItem();
       selectItem.setLabel("hamburguesa");
       selectItem.setValue("hamburguesa");
       productos.add(selectItem);
    }
    //Solo sirve cuando va a crear uno nuevo
    public void cambioproducto(AjaxBehaviorEvent event) throws SQLException {
        categorias = new ArrayList<SelectItem>();
        System.out.println("id productos" +idproductos);
        CategoriaService categoriaService=new CategoriaServiceJdbcImpl(ConexionBaseDatos.getConnection());
        List<Categoria> categoriaList = categoriaService.listar();
        Iterator<Categoria> iterator= categoriaList.iterator();
        if("pizza".equals(idproductos)){
            while(iterator.hasNext()) {
                Categoria categoria = iterator.next();
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(categoria.getNombre());
                selectItem.setValue(categoria.getId());
                categorias.add(selectItem);
            }
            this.flagCategoria=false;
            System.out.println("ingresa al if" +categorias.size());

        }else {
            this.flagCategoria=true;
            categorias = new ArrayList<SelectItem>();
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel("No pertenece");
            selectItem.setValue(-1);
            categorias.add(selectItem);

            this.idcategorias= Long.valueOf(-1);
        }


    }

    public void cambioproductoActualizar(AjaxBehaviorEvent event) throws SQLException {
        categorias = new ArrayList<SelectItem>();
        System.out.println("id productos" +this.editarProducto.getIdproductos());
        CategoriaService categoriaService=new CategoriaServiceJdbcImpl(ConexionBaseDatos.getConnection());
        List<Categoria> categoriaList = categoriaService.listar();
        Iterator<Categoria> iterator= categoriaList.iterator();
        if("pizza".equals(this.editarProducto.getIdproductos())){
            while(iterator.hasNext()) {
                Categoria categoria = iterator.next();
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(categoria.getNombre());
                selectItem.setValue(categoria.getId());
                categorias.add(selectItem);
            }
            this.flagCategoria=false;
            System.out.println("ingresa al if" +categorias.size());

        }else {
            this.flagCategoria=true;
            categorias = new ArrayList<SelectItem>();
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel("No pertenece");
            selectItem.setValue(-1);
            categorias.add(selectItem);

            this.idcategorias= Long.valueOf(-1);
            this.editarProducto.setIdcategorias(Long.valueOf(-1));
        }


    }
    public String irEditar(Long idEditar){
        try {
            ProductoService productoService     = new ProductoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Optional<Producto> producto = productoService.poridProducto(idEditar);

            if(producto.isPresent()){
                this.editarProducto = producto.get();
                return "editar-producto.xhtml?faces-redirect=true";
            }else{
                return "listar-producto.xhtml?faces-redirect=true";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "crear-producto.xhtml?faces-redirect=true";
        }

    }


    public String Editar(String accion) {
        try {
            ProductoService productoService = new ProductoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Producto producto = new Producto();
            if ("ACTUALIZA".equals(accion)) {
                producto.setUID_Producto(this.editarProducto.getUID_Producto());
                producto.setId_pedido(this.editarProducto.getId_pedido());
                producto.setNombre_producto(this.editarProducto.getNombre_producto());
                producto.setPrecio_producto(this.editarProducto.getPrecio_producto());
                producto.setDescripcion_producto(this.editarProducto.getDescripcion_producto());
                producto.setImagen_producto(this.editarProducto.getImagen_producto());
                producto.setProductos(this.editarProducto.getProductos());
            }
            productoService.guardar(producto);
            return "listar-producto.xhtml?faces-redirect=true";
        } catch (SQLException  e) {
            e.printStackTrace();
            return "crear-producto.xhtml?faces-redirect=true";
        }

    }




    public List<Producto> listar() throws SQLException {
        ProductoService productoService = new ProductoServiceJdbcImpl(ConexionBaseDatos.getConnection());
        return productoService.listar();
    }


    public String irCrear() throws SQLException {
        this.init();
        return "crear-producto.xhtml?faces-redirect=true";
    }


    public String guardar(String accion) {
        System.out.println("Guardando Producto INIT");
        String nombreFoto = null;
        File file = null;
        try {
            if(imagen_producto!= null) {
                String header = imagen_producto.getHeader("content-disposition");
                System.out.println("header " + header);
                for (String content : header.split(";")) {
                    if (content.trim().startsWith("filename")) {
                        nombreFoto = content.substring(content.indexOf("=") + 1).trim().replace("\"", "");
                    }
                }

                file = new File("D:\\img_upload", nombreFoto);

                try (InputStream input = imagen_producto.getInputStream()) {
                    Files.copy(input, file.toPath());
                }

                System.out.println("Nombre foto " + nombreFoto);
            }

            ProductoService productoService = new ProductoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            Producto producto = new Producto();
            if("CREA".equals(accion)) {
                producto.setUID_Producto(this.UID_Producto);
                producto.setId_pedido(this.id_pedido);
                producto.setNombre_producto(this.nombre_producto);
                producto.setPrecio_producto(this.precio_producto);
                producto.setDescripcion_producto(this.descripcion_producto);
                producto.setProductos(this.idproductos);
                producto.setCategorias(this.idcategorias);
                if (file != null) {
                    producto.setImagen_producto(file.toPath().toString());
                }
            }
            System.out.println("Guardando Producto Servicio");
            productoService.guardar(producto);

            return "listar-producto.xhtml?faces-redirect=true";
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return "crear-producto.xhtml?faces-redirect=true";
        }
    }




    public String borrar(Long id){
        try {
            ProductoService productoService = new ProductoServiceJdbcImpl(ConexionBaseDatos.getConnection());
            productoService.eliminar(id);
            return "listar-producto.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "listar-producto.xhtml?faces-redirect=true";
        }
    }


    public Producto getEditarProducto() {
        return editarProducto;
    }

    public void setEditarProducto(Producto editarProducto) {
        this.editarProducto = editarProducto;
    }

    public boolean isFlagCategoria() {
        return flagCategoria;
    }

    public void setFlagCategoria(boolean flagCategoria) {
        this.flagCategoria = flagCategoria;
    }

    public Long getIdcategorias() {
        return idcategorias;
    }

    public void setIdcategorias(Long idcategorias) {
        this.idcategorias = idcategorias;
    }

    public List<SelectItem> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<SelectItem> categorias) {
        this.categorias = categorias;
    }

    public String getIdproductos() {
        return idproductos;
    }

    public void setIdproductos(String idproductos) {
        this.idproductos = idproductos;
    }

    public List<SelectItem> getProductos() {
        return productos;
    }

    public void setProductos(List<SelectItem> productos) {
        this.productos = productos;
    }

    public List<SelectItem> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<SelectItem> pedidos) {
        this.pedidos = pedidos;
    }

    public Long getUID_Producto() {
        return UID_Producto;
    }

    public void setUID_Producto(Long UID_Producto) {
        this.UID_Producto = UID_Producto;
    }

    public Long getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Long id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Part getImagen_producto() {
        return imagen_producto;
    }

    public void setImagen_producto(Part imagen_producto) {
        this.imagen_producto = imagen_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public Double getPrecio_producto() {
        return precio_producto;
    }

    public void setPrecio_producto(Double precio_producto) {
        this.precio_producto = precio_producto;
    }

    public String getDescripcion_producto() {
        return descripcion_producto;
    }

    public void setDescripcion_producto(String descripcion_producto) {
        this.descripcion_producto = descripcion_producto;
    }

}
