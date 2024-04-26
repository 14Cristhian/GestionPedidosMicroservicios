package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Ciudad;
import co.uceva.edu.base.models.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<Cliente> listar();
    void guardar(Cliente cliente);
    void eliminar(Long id);
    Optional<Cliente> porId(Long id);
    List<Cliente> porIdDeparamento(Long id);
}
