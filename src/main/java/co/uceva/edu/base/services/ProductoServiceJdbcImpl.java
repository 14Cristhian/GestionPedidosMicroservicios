package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Pedido;
import co.uceva.edu.base.models.Producto;
import co.uceva.edu.base.repositories.PedidoRepositoryJdbcImpl;
import co.uceva.edu.base.repositories.ProductoRepositoryJdbcImpl;
import co.uceva.edu.base.repositories.RepositoryPedido;
import co.uceva.edu.base.repositories.RepositoryProducto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductoServiceJdbcImpl implements ProductoService {

    private RepositoryProducto<Producto> repositoryJdbc;

    public ProductoServiceJdbcImpl(Connection connection) {
        this.repositoryJdbc = new ProductoRepositoryJdbcImpl(connection);
    }

    @Override
    public List<Producto> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void guardar(Producto producto) {
        try {
            repositoryJdbc.guardar(producto);
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
    public Optional<Producto> poridProducto(Long id) {

        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());

        }
    }

    @Override
    public List<Producto> poridPedido(Long id) {

        try {
            return repositoryJdbc.id_pedido(id);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }
}
