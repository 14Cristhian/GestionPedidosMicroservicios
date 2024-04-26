package co.uceva.edu.base.repositories;

import co.uceva.edu.base.models.Producto;
import co.uceva.edu.base.models.Tienda;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryProducto <T> {
    List<T> listar() throws SQLException;
    void guardar(T t) throws SQLException;
    void eliminar(Long id) throws SQLException;
    Producto porId(Long id) throws SQLException;
    public List<Producto>id_pedido(Long id) throws SQLException;

}
