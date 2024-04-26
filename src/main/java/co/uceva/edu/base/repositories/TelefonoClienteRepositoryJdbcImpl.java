package co.uceva.edu.base.repositories;

import co.uceva.edu.base.models.TelefonoCliente;
import co.uceva.edu.base.models.Tienda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelefonoClienteRepositoryJdbcImpl implements  RepositoryTelefonoCliente<TelefonoCliente>{

    private Connection conn;
    public TelefonoClienteRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }



    @Override
    public List<TelefonoCliente> listar() throws SQLException {
        List<TelefonoCliente> telefonoClientes = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.* FROM telefono_cliente as p ")) {
            while (rs.next()) {
                TelefonoCliente  p= getTelefonoCliente(rs);
                telefonoClientes.add(p);
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return telefonoClientes;
    }

    private TelefonoCliente getTelefonoCliente(ResultSet rs) throws SQLException{
        TelefonoCliente p = new TelefonoCliente();

        if (this.hasColumn(rs,"cedula_cliente")) {
            p.setIdCliente(rs.getLong("cedula_cliente"));
        }
        p.setTelefono(rs.getLong("telefono"));
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
    public void guardar(TelefonoCliente telefonoCliente) throws SQLException {
        String sql;
//        if (telefonoCliente.getIdCliente() != null && telefonoCliente.getIdCliente() > 0) {
//            sql = "update telefono_cliente set telefono=? , cedula_cliente=?";
//        } else {

            sql = "insert into telefono_cliente (telefono,cedula_cliente) values (?,?)";
//        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, telefonoCliente.getTelefono());
            stmt.setLong(2,telefonoCliente.getIdCliente());
//             if (telefonoCliente.getIdCliente() != null && telefonoCliente.getIdCliente() > 0) {
//                stmt.setLong(2, telefonoCliente.getIdCliente());
//            }
            stmt.executeUpdate();
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "delete from telefono_cliente where cedula_cliente=?";
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
    public TelefonoCliente porId(Long id) throws SQLException {
        TelefonoCliente telefonoCliente = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM telefono_cliente as p  WHERE p.cedula_cliente = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    telefonoCliente = getTelefonoCliente(rs);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return telefonoCliente;
    }

    @Override
    public List<TelefonoCliente> cedula_cliente(Long id) throws SQLException {
        TelefonoCliente telefonoCliente = null;
        List<TelefonoCliente> telefonoClientes = new ArrayList<TelefonoCliente>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM telefono_cliente as p  WHERE p.cedula_cliente = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    telefonoCliente = getTelefonoCliente(rs);
                    telefonoClientes.add(telefonoCliente);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return telefonoClientes;
    }

}
