package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Usuario;
import co.uceva.edu.base.reportes.ReporteUsuarioPorTipo;
import co.uceva.edu.base.repositories.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsuarioServiceJdbcImpl implements  UsuarioService {
    private RepositoryUser<Usuario> repositoryJdbc;

    public UsuarioServiceJdbcImpl(Connection connection) {
        this.repositoryJdbc = new UsuarioRepositoryJdbcImpl(connection);
    }

    @Override
    public List<Usuario> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public List<ReporteUsuarioPorTipo> usuarioPorTipo() {
        try {
            return repositoryJdbc.usuarioPorTipo();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public Optional<Usuario> login(String usuario, String password) {
        try {
            return Optional.ofNullable(repositoryJdbc.login(usuario,password));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());

        }
    }

    public void guardar(Usuario usuario) {
        try {
            repositoryJdbc.guardar(usuario);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            repositoryJdbc.eliminar(id);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public Optional<Usuario> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());

        }
    }

}
