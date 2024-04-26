package co.uceva.edu.base.repositories;

import co.uceva.edu.base.models.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepositoryJdbcImpl implements RepositoryCliente<Cliente> {

    private Connection conn;

    public ClienteRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }


    @Override
    public List<Cliente> listar() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.* FROM cliente as p  order by p.cedula ASC")) {
            while (rs.next()) {
                Cliente  p= getCliente(rs);
                clientes.add(p);
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return clientes;
    }

    private Cliente getCliente(ResultSet rs) throws SQLException  {
        Cliente p = new Cliente();
        p.setCedula(rs.getLong("cedula"));
        p.setNombre(rs.getString("nombre"));
        p.setApellido(rs.getString("apellido"));
        p.setDireccion(rs.getString("direccion"));
        if (this.hasColumn(rs,"idmunicipio")) {
            p.setIdMunicipio(rs.getLong("idmunicipio"));
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

    @Override
    public void guardar(Cliente cliente) throws SQLException {
        System.out.println("Cedula"+cliente.getCedula());
        Cliente clienteConsulta = this.porCedula(cliente.getCedula());
        String sql;
        if (clienteConsulta!= null) {
            sql = "update cliente set nombre=?,apellido=?,direccion=?,idmunicipio=? where cedula=?";
        } else {

            sql = "insert into cliente (cedula,nombre,apellido,direccion,idmunicipio) values (?,?,?,?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1,cliente.getCedula());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellido());
            stmt.setString(4, cliente.getDireccion());
            stmt.setLong(5, cliente.getIdMunicipio());

            if (clienteConsulta!= null) {
                stmt.setLong(1, cliente.getCedula());
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
        String sql = "delete from cliente where cedula=?";
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
    public Cliente porCedula(Long id) throws SQLException {
        Cliente cliente = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM cliente as p  WHERE p.cedula = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = getCliente(rs);
                }
            }
        }
        return cliente;
    }

    @Override
    public List<Cliente> porIdDeparamento(Long id) throws SQLException {
        Cliente cliente = null;
        List<Cliente> clientes = new ArrayList<Cliente>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM cliente as p  WHERE p.id_departamento = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = getCliente(rs);
                    clientes.add(cliente);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return clientes;
    }
}
