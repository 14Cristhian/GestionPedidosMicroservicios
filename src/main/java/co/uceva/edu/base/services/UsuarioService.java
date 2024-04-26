package co.uceva.edu.base.services;

import co.uceva.edu.base.models.Usuario;
import co.uceva.edu.base.reportes.ReporteUsuarioPorTipo;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listar();

    List<ReporteUsuarioPorTipo> usuarioPorTipo();

    Optional<Usuario> login(String usuario, String password);

    void guardar(Usuario usuario);

    void eliminar(Long id);

    Optional<Usuario> porId(Long id);

}
