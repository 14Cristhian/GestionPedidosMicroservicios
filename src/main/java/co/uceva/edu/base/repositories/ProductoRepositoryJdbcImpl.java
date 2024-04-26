package co.uceva.edu.base.repositories;

import co.uceva.edu.base.models.Pedido;
import co.uceva.edu.base.models.Producto;
import co.uceva.edu.base.models.Tienda;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryJdbcImpl implements  RepositoryProducto<Producto>{


    private Connection conn;

    public ProductoRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.* FROM producto as p  order by p.idproducto ASC")) {
            while (rs.next()) {
                Producto  p= getProducto(rs);
                productos.add(p);
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return productos;
    }

    private Producto getProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setUID_Producto(rs.getLong("idproducto"));
        p.setNombre_producto(rs.getString("nombre_producto"));
        p.setPrecio_producto(rs.getDouble("precio_producto"));
        if (this.hasColumn(rs,"tipo_producto")) {
            p.setProductos(rs.getString("tipo_producto"));
        }
        p.setDescripcion_producto(rs.getString("descripcion_producto"));
        if (this.hasColumn(rs,"id_pedido")) {
            p.setId_pedido(rs.getLong("id_pedido"));
        }
        p.setImagen_producto(rs.getString("imagen_producto"));
        return p;
    }

    public boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equalsIgnoreCase(rsmd.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }



    @Override
    public void guardar(Producto producto) throws SQLException {

        System.out.println("Guardando producto "+producto.getProductos());
        String sql;
        if (producto.getUID_Producto() != null && producto.getUID_Producto() > 0) {

            sql = "update producto set nombre_producto=?,precio_producto=?,descripcion_producto=?,id_pedido=?,imagen_producto=?, tipo_producto=? where idproducto=?";

        } else {
            if("pizza".equalsIgnoreCase(producto.getProductos()))
            {
                sql = "insert into producto (nombre_producto,precio_producto,descripcion_producto,id_pedido,imagen_producto,tipo_producto) values (?,?,?,?,?,?);insert into pizza (\"UID_Producto\", id_categoria, cant_tipo) VALUES ( (SELECT MAX(idproducto) FROM producto), ?,20)";
            }else {
                sql = "insert into producto (nombre_producto,precio_producto,descripcion_producto,id_pedido,imagen_producto,tipo_producto) values (?,?,?,?,?,?)";

            }


        }
        try (PreparedStatement  stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre_producto());
            stmt.setDouble(2, producto.getPrecio_producto());
            stmt.setString(3, producto.getDescripcion_producto());
            stmt.setLong(4, producto.getId_pedido());
            stmt.setString(5,producto.getImagen_producto());
            stmt.setString(6,producto.getProductos());
            if (producto.getUID_Producto() != null && producto.getUID_Producto() > 0) {
                stmt.setLong(7, producto.getUID_Producto());
            }else {

                if("pizza".equalsIgnoreCase(producto.getProductos())) {
                    stmt.setLong(7, producto.getCategorias());
                }
            }
            stmt.executeUpdate();
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
    }




    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "delete from producto where idproducto=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }finally {
            if (this.conn !=null && !this.conn.isClosed()){
                this.conn.close();
            }
        }
    }

    @Override
    public Producto porId(Long id) throws SQLException {
        Producto producto = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM producto as p  WHERE p.idproducto = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = getProducto(rs);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return producto;
    }

    @Override
        public List<Producto> id_pedido(Long id) throws SQLException {
        Producto producto = null;
        List<Producto> productos = new ArrayList<Producto>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM producto as p  WHERE p.id_pedido = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = getProducto(rs);
                    productos.add(producto);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return productos;
    }

}
