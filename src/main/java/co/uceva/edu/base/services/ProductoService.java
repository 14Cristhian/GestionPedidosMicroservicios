package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Producto;
import co.uceva.edu.base.models.Tienda;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listar();
    void guardar(Producto producto);
    void eliminar(Long id);
    Optional<Producto>poridProducto(Long id);
    List<Producto>poridPedido(Long id);
}
