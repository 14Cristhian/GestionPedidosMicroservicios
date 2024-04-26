package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Ciudad;
import co.uceva.edu.base.models.Tienda;

import java.util.List;
import java.util.Optional;

public interface TiendaService {

    List<Tienda> listar();
    void guardar(Tienda tienda);
    void eliminar(Long id);
    Optional<Tienda> porId(Long id);
    List<Tienda> porid_municipio(Long id);
}
