package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Ciudad;
import co.uceva.edu.base.models.Departamento;
import co.uceva.edu.base.repositories.CiudadRepositoryJdbcImpl;
import co.uceva.edu.base.repositories.DepartamentoRepositoryJdbcImpl;
import co.uceva.edu.base.repositories.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DepartamentoServiceJdbcImpl implements  DepartamentoService {
    private Repository<Departamento> repositoryJdbc;

    public DepartamentoServiceJdbcImpl(Connection connection) {
        this.repositoryJdbc = new DepartamentoRepositoryJdbcImpl(connection);
    }

    @Override
    public List<Departamento> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    public void guardar(Departamento departamento) {
        try {
            repositoryJdbc.guardar(departamento);
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
    public Optional<Departamento> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());

        }
    }


}
