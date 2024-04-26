package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Departamento;

import java.util.List;
import java.util.Optional;

public interface DepartamentoService {

    List<Departamento> listar();
    void guardar(Departamento departamento);
    void eliminar(Long id);
    Optional<Departamento> porId(Long id);

}
