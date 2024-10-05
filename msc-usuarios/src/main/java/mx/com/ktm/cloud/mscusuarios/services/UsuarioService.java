package mx.com.ktm.cloud.mscusuarios.services;

import mx.com.ktm.cloud.mscusuarios.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    List<Usuario> listarPorIds(Iterable<Long> ids);
    Optional<Usuario> findUserByEmail(String email);
}
