package co.uceva.edu.base.repositories;
import co.uceva.edu.base.models.Ciudad;
import co.uceva.edu.base.models.Departamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoRepositoryJdbcImpl implements  Repository<Departamento> {

    private Connection conn;

    public DepartamentoRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }


    @Override
    public List<Departamento> listar() throws SQLException {
        List<Departamento> departamentos = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.* FROM departamentos as p  order by p.id ASC")) {
            while (rs.next()) {
                Departamento  p= getDepartamento(rs);
                departamentos.add(p);
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return departamentos;
    }


    @Override
    public void guardar(Departamento departamento) throws SQLException {
        String sql;
        if (departamento.getId() != null && departamento.getId() > 0) {
            sql = "update departamentos set nombre=? where id=?";
        } else {

            sql = "insert into departamentos (nombre ) values (? )";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, departamento.getNombre());

            if (departamento.getId() != null && departamento.getId() > 0) {
                stmt.setLong(2, departamento.getId());
            }
            stmt.executeUpdate();
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
    }

    @Override
    public Departamento porId(Long id) throws SQLException {
        Departamento departamento = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.* FROM departamentos as p  WHERE p.id = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    departamento = getDepartamento(rs);
                }
            }
        }finally {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        return departamento;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "delete from departamentos where id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }finally {
            if (this.conn !=null && !this.conn.isClosed()){
                this.conn.close();
            }
        }
    }

    private Departamento getDepartamento(ResultSet rs) throws SQLException {
        Departamento p = new Departamento();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));

        return p;
    }

}
