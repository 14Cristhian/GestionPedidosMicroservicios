package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Ciudad;

import java.util.List;
import java.util.Optional;

public interface CiudadService {

    List<Ciudad> listar();

    void guardar(Ciudad ciudad);

    void eliminar(Long id);

    Optional<Ciudad> porId(Long id);

    List<Ciudad> porIdDeparamento(Long id);

}
