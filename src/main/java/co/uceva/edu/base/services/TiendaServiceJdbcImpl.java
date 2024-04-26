package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Cliente;
import co.uceva.edu.base.models.Tienda;
import co.uceva.edu.base.repositories.ClienteRepositoryJdbcImpl;
import co.uceva.edu.base.repositories.RepositoryCliente;
import co.uceva.edu.base.repositories.RepositoryTienda;
import co.uceva.edu.base.repositories.TiendaRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TiendaServiceJdbcImpl implements TiendaService{

    private RepositoryTienda<Tienda> repositoryJdbc;


    public TiendaServiceJdbcImpl(Connection connection) {
        this.repositoryJdbc = new TiendaRepositoryJdbcImpl(connection);
    }


    @Override
    public List<Tienda> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void guardar(Tienda tienda) {
        try {
            repositoryJdbc.guardar(tienda);
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
    public Optional<Tienda> porId(Long id) {

        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());

        }
    }

    @Override
    public List<Tienda> porid_municipio(Long id) {
        try {
            return repositoryJdbc.poridmunicipio(id);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

}
