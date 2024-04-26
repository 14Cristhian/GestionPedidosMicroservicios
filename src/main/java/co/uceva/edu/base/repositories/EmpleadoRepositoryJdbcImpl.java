package co.uceva.edu.base.repositories;

import co.uceva.edu.base.models.Cliente;
import co.uceva.edu.base.models.Departamento;
import co.uceva.edu.base.models.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepositoryJdbcImpl implements  RepositoryEmpleado<Empleado>{


    private Connection conn;
    public EmpleadoRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Empleado> listar() throws SQLException {
        List<Empleado> empleados = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.* FROM empleado as p  order by p.cedula_empleado ASC")) {
            while (rs.next()) {
                Empleado  p= getEmpleado(rs);
                empleados.add(p);
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return empleados;
    }

    private Empleado getEmpleado(ResultSet rs) throws SQLException {
        Empleado p = new Empleado();

        p.setCedula(rs.getLong("cedula_empleado"));
        p.setApellido(rs.getString("apellido_empleado"));
        p.setNombre(rs.getString("nombre_empleado"));
        p.setTipo(rs.getString("tipo_empleado"));
        if (this.hasColumn(rs,"id_tienda")) {
            p.setIdtienda(rs.getLong("id_tienda"));
        } if (this.hasColumn(rs,"id_municipio")) {
            p.setIdmunicipio(rs.getLong("id_municipio"));
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
    public void guardar(Empleado empleado) throws SQLException {
        String sql;
        Empleado empleadoConsulta = this.porCedula(empleado.getCedula());
        System.out.println("Id Tienda:" + empleado.getIdtienda());
        if (empleadoConsulta!= null) {
            sql = "update empleado set apellido_empleado=?,nombre_empleado=?,tipo_empleado=?,id_tienda=?,id_municipio=? where cedula_empleado=?";
        } else {

            sql = "insert into empleado (apellido_empleado,nombre_empleado, tipo_empleado,id_tienda,id_municipio,cedula_empleado) values (?,?,?,?,?,? )";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,empleado.getApellido());
            stmt.setString(2,empleado.getNombre());
            stmt.setString(3,empleado.getTipo());
            stmt.setLong(4,empleado.getIdtienda());
            stmt.setLong(5,empleado.getIdmunicipio());
            stmt.setLong(6, empleado.getCedula());

          /*  if (empleado != null) {
                stmt.setLong(6, empleado.getCedula());

            }*/
            stmt.executeUpdate();
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {   
                this.conn.close();
            }
        }

    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "delete from empleado where cedula_empleado=?";
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
    public Empleado porCedula(Long id) throws SQLException {
        Empleado empleado = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM empleado as p  WHERE p.cedula_empleado = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    empleado = getEmpleado(rs);
                }
            }
        }
        return empleado;
    }

    @Override
    public List<Empleado> porIdMunicipio(Long id) throws SQLException {
        Empleado empleado = null;
        List<Empleado> empleados = new ArrayList<Empleado>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM empleado as p  WHERE p.id_municipio = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    empleado = getEmpleado(rs);
                    empleados.add(empleado);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return empleados;
    }


    @Override
    public List<Empleado> porIdtienda(Long id) throws SQLException {
        Empleado empleado = null;
        List<Empleado> empleados = new ArrayList<Empleado>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM empleado as p  WHERE p.id_tienda = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    empleado = getEmpleado(rs);
                    empleados.add(empleado);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return empleados;
    }
}
