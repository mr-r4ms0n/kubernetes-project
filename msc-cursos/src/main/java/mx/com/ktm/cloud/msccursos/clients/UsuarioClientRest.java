package mx.com.ktm.cloud.msccursos.clients;

import mx.com.ktm.cloud.msccursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Se saca del application.name del servicio a consumir
@FeignClient(name = "msvc-usuarios")
public interface UsuarioClientRest {

    @GetMapping("{/id}")
    public Usuario detalle(@PathVariable Long id);

    @PostMapping("/")
    public Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/usuarios-por-curso")
    public List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);
}
