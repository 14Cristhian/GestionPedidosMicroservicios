package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Cliente;
import co.uceva.edu.base.repositories.ClienteRepositoryJdbcImpl;
import co.uceva.edu.base.repositories.RepositoryCliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClienteServiceJdbcImpl implements ClienteService {

    private RepositoryCliente<Cliente> repositoryJdbc;

    public ClienteServiceJdbcImpl(Connection connection) {
        this.repositoryJdbc = new ClienteRepositoryJdbcImpl(connection);
    }

    @Override
    public List<Cliente> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void guardar(Cliente cliente) {
        try {
            repositoryJdbc.guardar(cliente);
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
    public Optional<Cliente> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porCedula(id));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());

        }
    }

    @Override
    public List<Cliente> porIdDeparamento(Long id) {
        try {
            return repositoryJdbc.porIdDeparamento(id);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }
}
