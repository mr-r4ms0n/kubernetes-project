package mx.com.ktm.cloud.mscusuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos")
public interface CursoClienteRest {

    @DeleteMapping("/api/v1/cursos/eliminar-curso-usuario/{id}")
    public void eliminarCursoUsuarioPorId(@PathVariable Long id);
}

