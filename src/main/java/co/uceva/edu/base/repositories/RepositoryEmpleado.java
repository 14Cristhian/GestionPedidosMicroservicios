package co.uceva.edu.base.repositories;
import co.uceva.edu.base.models.Cliente;
import co.uceva.edu.base.models.Empleado;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryEmpleado<C> {
    List<C> listar() throws SQLException;
    void guardar(C c) throws SQLException;
    void eliminar(Long id) throws SQLException;
    Empleado porCedula(Long id) throws SQLException;
    public List<Empleado>  porIdMunicipio(Long id) throws SQLException;
    public List<Empleado>  porIdtienda(Long id) throws SQLException;
}
