package co.uceva.edu.base.repositories;

import co.uceva.edu.base.models.Ciudad;
import co.uceva.edu.base.models.Usuario;
import co.uceva.edu.base.reportes.ReporteUsuarioPorTipo;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryCiudad<T> {

    List<T> listar() throws SQLException;
    void guardar(T t) throws SQLException;
    void eliminar(Long id) throws SQLException;
    Ciudad porId(Long id) throws SQLException;
    public List<Ciudad>  porIdDeparamento(Long id) throws SQLException;

}
