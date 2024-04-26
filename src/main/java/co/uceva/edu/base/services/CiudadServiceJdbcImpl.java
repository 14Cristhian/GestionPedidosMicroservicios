package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Ciudad;
import co.uceva.edu.base.models.Usuario;
import co.uceva.edu.base.repositories.CiudadRepositoryJdbcImpl;
import co.uceva.edu.base.repositories.Repository;
import co.uceva.edu.base.repositories.RepositoryCiudad;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CiudadServiceJdbcImpl implements  CiudadService {
    private RepositoryCiudad<Ciudad> repositoryJdbc;

    public CiudadServiceJdbcImpl(Connection connection) {
        this.repositoryJdbc = new CiudadRepositoryJdbcImpl(connection);
    }

    @Override
    public List<Ciudad> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    public void guardar(Ciudad ciudad) {
        try {
            repositoryJdbc.guardar(ciudad);
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
    public Optional<Ciudad> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());

        }
    }

    @Override
    public List<Ciudad>  porIdDeparamento(Long id) {
        try {
            return repositoryJdbc.porIdDeparamento(id);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

}
