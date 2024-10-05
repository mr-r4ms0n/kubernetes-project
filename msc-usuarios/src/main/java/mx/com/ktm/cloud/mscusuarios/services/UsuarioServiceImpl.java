package mx.com.ktm.cloud.mscusuarios.services;

import mx.com.ktm.cloud.mscusuarios.client.CursoClienteRest;
import mx.com.ktm.cloud.mscusuarios.entities.Usuario;
import mx.com.ktm.cloud.mscusuarios.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("usuarioServiceImpl")
@Transactional
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private CursoClienteRest client;
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return (List<Usuario>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
        client.eliminarCursoUsuarioPorId(id);
    }

    @Override
    public List<Usuario> listarPorIds(Iterable<Long> ids) {
        return (List<Usuario>) repository.findAllById(ids);
    }

    @Override
    public Optional<Usuario> findUserByEmail(String email) {
        return repository.findByUsEmail(email);
    }
}
