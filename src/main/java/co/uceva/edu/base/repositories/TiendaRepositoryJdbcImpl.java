package co.uceva.edu.base.repositories;

import co.uceva.edu.base.models.Ciudad;
import co.uceva.edu.base.models.Cliente;
import co.uceva.edu.base.models.Departamento;
import co.uceva.edu.base.models.Tienda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TiendaRepositoryJdbcImpl implements RepositoryTienda<Tienda> {

    private Connection conn;
    public TiendaRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }



    private Tienda getTienda(ResultSet rs) throws SQLException  {
        Tienda p = new Tienda();
        p.setId(rs.getLong("id"));
        if (this.hasColumn(rs,"idmunicipio")) {
            p.setIdmunicipio(rs.getLong("idmunicipio"));
        }
        p.setDirecciont(rs.getString("direcciont"));
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
    public List<Tienda> listar() throws SQLException {
        List<Tienda> tiendas = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.* FROM tienda as p  order by p.id ASC")) {
            while (rs.next()) {
                Tienda  p= getTienda(rs);
                tiendas.add(p);
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return tiendas;
    }

    @Override
    public void guardar(Tienda tienda) throws SQLException {
        String sql;
        if (tienda.getId() != null && tienda.getId() > 0) {
            sql = "update tienda set direcciont=?,idmunicipio=? where id=?";
        } else {

            sql = "insert into tienda (direcciont,idmunicipio) values (?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tienda.getDirecciont());
            stmt.setLong(2, tienda.getIdmunicipio());
            if (tienda.getId() != null && tienda.getId() > 0) {
                stmt.setLong(3, tienda.getId());
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
        String sql = "delete from tienda where id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
    }

    @Override
    public Tienda porId(Long id) throws SQLException {
        Tienda tienda = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM tienda as p  WHERE p.id = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tienda = getTienda(rs);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return tienda;
    }

    @Override
    public List<Tienda> poridmunicipio(Long id) throws SQLException {
        Tienda tienda = null;
        List<Tienda> tiendas = new ArrayList<Tienda>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM tienda as p  WHERE p.idmunicipio = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tienda = getTienda(rs);
                    tiendas.add(tienda);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return tiendas;
    }
}
