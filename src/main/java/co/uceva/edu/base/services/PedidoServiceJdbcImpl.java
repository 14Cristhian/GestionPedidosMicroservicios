package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Pedido;
import co.uceva.edu.base.models.Tienda;
import co.uceva.edu.base.repositories.PedidoRepositoryJdbcImpl;
import co.uceva.edu.base.repositories.RepositoryPedido;
import co.uceva.edu.base.repositories.RepositoryTienda;
import co.uceva.edu.base.repositories.TiendaRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PedidoServiceJdbcImpl implements PedidoService{

    private RepositoryPedido<Pedido> repositoryJdbc;

    public PedidoServiceJdbcImpl(Connection connection) {
        this.repositoryJdbc = new PedidoRepositoryJdbcImpl(connection);
    }




    @Override
    public List<Pedido> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void guardar(Pedido pedido) {
        try {
            repositoryJdbc.guardar(pedido);
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
    public Optional<Pedido> porUID_Pedido(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porUID_Pedido(id));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());

        }
    }



    @Override
    public List<Pedido> porid_tienda(Long id) {

        try {
            return repositoryJdbc.poridmunicipio(id);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }
}
