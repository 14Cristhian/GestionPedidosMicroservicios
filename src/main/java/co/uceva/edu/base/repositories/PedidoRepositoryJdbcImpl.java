package co.uceva.edu.base.repositories;

import co.uceva.edu.base.models.Departamento;
import co.uceva.edu.base.models.Pedido;
import co.uceva.edu.base.models.Tienda;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PedidoRepositoryJdbcImpl implements  RepositoryPedido<Pedido>{
    private Connection conn;




    public PedidoRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }
     String timeStamp = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(Calendar.getInstance().getTime());

    @Override
    public List<Pedido> listar() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.* FROM pedido as p  order by p.id ASC")) {
            while (rs.next()) {
                Pedido  p= getPedidos(rs);
                pedidos.add(p);
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return pedidos;
    }

    @Override
    public void guardar(Pedido pedido) throws SQLException {

        String sql;
        if (pedido.getUID_Pedido() != null && pedido.getUID_Pedido() > 0) {
            sql = "update pedido set precio_total=?,id_tienda=? where id=?";
        } else {

            sql = "SET TIMEZONE='America/Bogota'; insert into pedido (precio_total,id_tienda) values (?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, pedido.getPrecio_total());
            stmt.setLong(2,pedido.getId_tienda());


            if (pedido.getUID_Pedido() != null && pedido.getUID_Pedido() > 0) {
                stmt.setLong(3, pedido.getUID_Pedido());
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
        String sql = "delete from pedido where id=?";
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
    public Pedido porUID_Pedido(Long id) throws SQLException {
        Pedido pedido = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM pedido as p  WHERE p.id = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pedido = getPedidos(rs);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return pedido;
    }

    @Override
    public List<Pedido> poridmunicipio(Long id) throws SQLException {
        Pedido pedido = null;
        List<Pedido> pedidos = new ArrayList<Pedido>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM pedido as p  WHERE p.idmunicipio = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pedido = getPedidos(rs);
                    pedidos.add(pedido);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return pedidos;
    }

    private Pedido getPedidos(ResultSet rs) throws SQLException  {
        Pedido p = new Pedido();
        p.setUID_Pedido(rs.getLong("id"));
        p.setTimeStamp(rs.getString("fecha"));
        p.setPrecio_total(rs.getDouble("precio_total"));
        if (this.hasColumn(rs,"id_tienda")) {
            p.setId_tienda(rs.getLong("id_tienda"));
        }
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
