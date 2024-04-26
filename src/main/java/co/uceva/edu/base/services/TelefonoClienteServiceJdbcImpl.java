package co.uceva.edu.base.services;

import co.uceva.edu.base.models.TelefonoCliente;
import co.uceva.edu.base.models.Tienda;
import co.uceva.edu.base.repositories.RepositoryTelefonoCliente;
import co.uceva.edu.base.repositories.RepositoryTienda;
import co.uceva.edu.base.repositories.TelefonoClienteRepositoryJdbcImpl;
import co.uceva.edu.base.repositories.TiendaRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TelefonoClienteServiceJdbcImpl implements  TelefonoClienteService{


    private RepositoryTelefonoCliente<TelefonoCliente> repositoryJdbc;
    public TelefonoClienteServiceJdbcImpl(Connection connection) {
        this.repositoryJdbc = new TelefonoClienteRepositoryJdbcImpl(connection);
    }


    @Override
    public List<TelefonoCliente> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void guardar(TelefonoCliente telefonoCliente) {
        try {
            repositoryJdbc.guardar(telefonoCliente);
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
    public Optional<TelefonoCliente> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());

        }
    }

    @Override
    public List<TelefonoCliente> cedulaCliente(Long id) {
        try {
            return repositoryJdbc.cedula_cliente(id);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

}
