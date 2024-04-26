package co.uceva.edu.base.repositories;
import co.uceva.edu.base.models.Cliente;
import co.uceva.edu.base.models.Departamento;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryCliente<C> {
    List<C> listar() throws SQLException;
    void guardar(C c) throws SQLException;
    void eliminar(Long id) throws SQLException;
    Cliente porCedula(Long id) throws SQLException;
    public List<Cliente>  porIdDeparamento(Long id) throws SQLException;
}
