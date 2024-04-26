package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Empleado;
import co.uceva.edu.base.models.Tienda;
import co.uceva.edu.base.repositories.EmpleadoRepositoryJdbcImpl;
import co.uceva.edu.base.repositories.RepositoryEmpleado;
import co.uceva.edu.base.repositories.RepositoryTienda;
import co.uceva.edu.base.repositories.TiendaRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmpleadoServiceJdbcImpl  implements EmpleadoService{


    private RepositoryEmpleado<Empleado> repositoryJdbc;
    public EmpleadoServiceJdbcImpl(Connection connection) {
        this.repositoryJdbc = new EmpleadoRepositoryJdbcImpl(connection);
    }


    @Override
    public List<Empleado> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void guardar(Empleado empleado) {
        try {
            repositoryJdbc.guardar(empleado);
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
    public Optional<Empleado> porCedula(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porCedula(id));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());

        }
    }

    @Override
    public List<Empleado> porIdTienda(Long id) {
        try {
            return repositoryJdbc.porIdtienda(id);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }


    @Override
    public List<Empleado> porIdMunicipio(Long id) {
        try {
            return repositoryJdbc.porIdMunicipio(id);
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

}
