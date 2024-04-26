package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Categoria;
import co.uceva.edu.base.models.Departamento;
import co.uceva.edu.base.repositories.CategoriaRepositoryJdbcImpl;
import co.uceva.edu.base.repositories.DepartamentoRepositoryJdbcImpl;
import co.uceva.edu.base.repositories.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CategoriaServiceJdbcImpl implements  CategoriaService {
    private Repository<Categoria> repositoryJdbc;

    public CategoriaServiceJdbcImpl(Connection connection) {
        this.repositoryJdbc = new CategoriaRepositoryJdbcImpl(connection);
    }

    @Override
    public List<Categoria> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    public void guardar(Categoria categoria) {
        try {
            repositoryJdbc.guardar(categoria);
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
    public Optional<Categoria> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());

        }
    }


}
