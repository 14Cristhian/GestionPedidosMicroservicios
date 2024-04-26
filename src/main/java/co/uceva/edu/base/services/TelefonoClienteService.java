package co.uceva.edu.base.services;

import co.uceva.edu.base.models.TelefonoCliente;
import co.uceva.edu.base.models.Tienda;

import java.util.List;
import java.util.Optional;

public interface TelefonoClienteService {
    List<TelefonoCliente> listar();
    void guardar(TelefonoCliente telefonoCliente);
    void eliminar(Long id);
    Optional<TelefonoCliente> porId(Long id);
    List<TelefonoCliente> cedulaCliente(Long id);
}
