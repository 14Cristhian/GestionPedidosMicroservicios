package co.uceva.edu.base.repositories;


import co.uceva.edu.base.models.Usuario;
import co.uceva.edu.base.reportes.ReporteUsuarioPorTipo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositoryJdbcImpl implements  RepositoryUser<Usuario> {

    private Connection conn;

    public UsuarioRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }


    @Override
    public List<Usuario> listar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.* FROM usuarios as p  order by p.id ASC")) {
            while (rs.next()) {
                Usuario  p= getUsuario(rs);
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
    public List<ReporteUsuarioPorTipo> usuarioPorTipo() throws SQLException {
        List<ReporteUsuarioPorTipo> reporte = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT tipo,count(*) as cantidad FROM usuarios GROUP BY tipo")) {
            while (rs.next()) {
                ReporteUsuarioPorTipo  p= getReportePortipo(rs);
                reporte.add(p);
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return reporte;

    }
    @Override
    public Usuario login(String login, String password) throws SQLException {
        Usuario usuario = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios  WHERE login = ? and password = ?")) {
            stmt.setString(1, login);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = getUsuario(rs);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return usuario;
    }

    @Override
    public void guardar(Usuario usuario) throws SQLException {
        String sql;
        if (usuario.getId() != null && usuario.getId() > 0) {
            sql = "update usuarios set nombre=?, login=?, password=? where id=?";
        } else {

            sql = "insert into usuarios (nombre, login, password) values (?,?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getPassword());

            if (usuario.getId() != null && usuario.getId() > 0) {
                stmt.setLong(4, usuario.getId());
            }
            stmt.executeUpdate();
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
    }

    @Override
    public Usuario porId(Long id) throws SQLException {
        Usuario usuario = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM usuarios as p  WHERE p.id = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = getUsuario(rs);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return usuario;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "delete from usuarios where id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
    }

    private Usuario getUsuario(ResultSet rs) throws SQLException {
        Usuario p = new Usuario();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setPassword(rs.getString("password"));
        p.setLogin(rs.getString("login"));

        return p;
    }

    private ReporteUsuarioPorTipo getReportePortipo(ResultSet rs) throws SQLException {
        ReporteUsuarioPorTipo p = new ReporteUsuarioPorTipo();
        p.setCantidad(rs.getInt("cantidad"));
        p.setTipo(rs.getString("tipo"));

        return p;
    }
}
