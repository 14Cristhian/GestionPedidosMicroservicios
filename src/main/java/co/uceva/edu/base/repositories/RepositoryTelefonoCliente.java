package co.uceva.edu.base.repositories;

import co.uceva.edu.base.models.Producto;
import co.uceva.edu.base.models.TelefonoCliente;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryTelefonoCliente <T>{
    List<T> listar() throws SQLException;
    void guardar(T t) throws SQLException;
    void eliminar(Long id) throws SQLException;
    TelefonoCliente porId(Long id) throws SQLException;
    public List<TelefonoCliente>cedula_cliente(Long id) throws SQLException;


}
