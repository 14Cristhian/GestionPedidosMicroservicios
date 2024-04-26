package co.uceva.edu.base.repositories;


import co.uceva.edu.base.models.Ciudad;
import co.uceva.edu.base.models.Usuario;
import co.uceva.edu.base.reportes.ReporteUsuarioPorTipo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CiudadRepositoryJdbcImpl implements  RepositoryCiudad<Ciudad> {

    private Connection conn;

    public CiudadRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }


    @Override
    public List<Ciudad> listar() throws SQLException {
        List<Ciudad> usuarios = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT m.*,d.nombre as nombre_departamento FROM municipios as m, departamentos as d WHERE m.id_departamento = d.id order by m.id ASC")) {
            while (rs.next()) {
                Ciudad  p= getCiudad(rs);
                usuarios.add(p);
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return usuarios;
    }


    @Override
    public void guardar(Ciudad municipios) throws SQLException {
        String sql;
        if (municipios.getId() != null && municipios.getId() > 0) {
            sql = "update municipios set nombre=?,id_departamento=? where id=?";
        } else {

            sql = "insert into municipios (nombre,id_departamento ) values (?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, municipios.getNombre());
            stmt.setLong(2, municipios.getIdDepartamento());

            if (municipios.getId() != null && municipios.getId() > 0) {
                stmt.setLong(3, municipios.getId());
            }
            stmt.executeUpdate();
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
    }

    @Override
    public Ciudad porId(Long id) throws SQLException {
        Ciudad ciudad = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM municipios as p  WHERE p.id = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ciudad = getCiudad(rs);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return ciudad;
    }

    @Override
    public List<Ciudad> porIdDeparamento(Long id) throws SQLException {
        Ciudad ciudad = null;
        List<Ciudad> ciudades = new ArrayList<Ciudad>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM municipios as p  WHERE p.id_departamento = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ciudad = getCiudad(rs);
                    ciudades.add(ciudad);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return ciudades;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "delete from municipios where id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }finally {
            if (this.conn !=null && !this.conn.isClosed()){
                this.conn.close();
            }
        }
    }

    private Ciudad getCiudad(ResultSet rs) throws SQLException {
        Ciudad p = new Ciudad();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setIdDepartamento(rs.getLong("id_departamento"));
        if (this.hasColumn(rs,"nombre_departamento")) {
            p.setDepartamentoDescripcion(rs.getString("nombre_departamento"));
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


}
