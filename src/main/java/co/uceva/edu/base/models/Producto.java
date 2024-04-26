package co.uceva.edu.base.models;

import javax.servlet.http.Part;
import javax.validation.Path;

public class Producto {

    private  Long UID_Producto;
    private Long id_pedido;
   private String imagen_producto;
   private String nombre_producto;
   private Double precio_producto;
   private String descripcion_producto;
    private String idproductos;
    private Long idcategorias;
   private String productos;
   private Long categorias;


    public String getFormato(){
        if(id_pedido==0){
            return "NULL";
        }else{
            return String.valueOf(id_pedido);

        }

    }




    public String getimagen_productoShow() {

        if(imagen_producto!=null) {
            String[] fotoArray = imagen_producto.split("\\\\");
            return fotoArray[fotoArray.length-1];
        }else{
            return "";
        }

    }

    public String getIdproductos() {
        return idproductos;
    }

    public void setIdproductos(String idproductos) {
        this.idproductos = idproductos;
    }

    public Long getIdcategorias() {
        return idcategorias;
    }

    public void setIdcategorias(Long idcategorias) {
        this.idcategorias = idcategorias;
    }

    public Long getCategorias() {
        return categorias;
    }

    public void setCategorias(Long categorias) {
        this.categorias = categorias;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
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

    public String getImagen_producto() {
        return imagen_producto;
    }

    public void setImagen_producto(String imagen_producto) {
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
