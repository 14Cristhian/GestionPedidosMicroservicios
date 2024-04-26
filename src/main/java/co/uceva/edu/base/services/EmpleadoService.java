package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Empleado;
import co.uceva.edu.base.models.Producto;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {
    List<Empleado>listar();
    void guardar(Empleado empleado);
    void eliminar(Long id);
    Optional<Empleado>porCedula(Long id);
    List<Empleado>porIdTienda(Long id);
    List<Empleado>porIdMunicipio(Long id);
}
