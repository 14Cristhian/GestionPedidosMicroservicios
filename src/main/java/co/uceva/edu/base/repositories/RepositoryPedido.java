package co.uceva.edu.base.repositories;

import co.uceva.edu.base.models.Pedido;
import co.uceva.edu.base.models.Tienda;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryPedido <T>{
    List<T> listar() throws SQLException;
    void guardar(T t) throws SQLException;
    void eliminar(Long id) throws SQLException;
    Pedido porUID_Pedido(Long id) throws SQLException;
    public List<Pedido>poridmunicipio(Long id) throws SQLException;
}
