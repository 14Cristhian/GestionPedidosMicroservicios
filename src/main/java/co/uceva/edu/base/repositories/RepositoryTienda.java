package co.uceva.edu.base.repositories;

import co.uceva.edu.base.models.Ciudad;
import co.uceva.edu.base.models.Cliente;
import co.uceva.edu.base.models.Tienda;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryTienda <T>{
    List<T> listar() throws SQLException;
    void guardar(T t) throws SQLException;
    void eliminar(Long id) throws SQLException;
    Tienda porId(Long id) throws SQLException;
    public List<Tienda>poridmunicipio(Long id) throws SQLException;

}
