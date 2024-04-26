package co.uceva.edu.base.services;
import co.uceva.edu.base.models.Pedido;
import co.uceva.edu.base.models.Tienda;
import java.util.List;
import java.util.Optional;

public interface PedidoService {
    List<Pedido> listar();
    void guardar(Pedido pedido);
    void eliminar(Long id);
    Optional<Pedido> porUID_Pedido(Long id);
    List<Pedido> porid_tienda(Long id);
}
