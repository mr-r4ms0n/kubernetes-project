package mx.com.ktm.cloud.msccursos.controllers;

import feign.FeignException;
import mx.com.ktm.cloud.msccursos.entities.Curso;
import mx.com.ktm.cloud.msccursos.models.Usuario;
import mx.com.ktm.cloud.msccursos.services.CursoService;
import mx.com.ktm.cloud.msccursos.services.CursoServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PreUpdate;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/list")
    public ResponseEntity<List<Curso>> listar(){
        return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id){
        Optional<Curso> o = cursoService.porIdConUsuarios(id);
        if (o.isPresent()){
            return ResponseEntity.ok(o.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> agregarnuevo(@Valid @RequestBody Curso curso, BindingResult result){
        if (result.hasErrors()){
            return validar(result);
        }
        return ResponseEntity.ok(cursoService.guardar(curso));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizarRegistro(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validar(result);
        }
        Optional<Curso> update = cursoService.porId(id);
        if (update.isPresent()){
            Curso toUpdate = update.get();
            toUpdate.setCuName(curso.getCuName());
            return ResponseEntity.ok(cursoService.guardar(toUpdate));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminarRegistro(@PathVariable Long id){
        Optional<Curso> curso = cursoService.porId(id);
        if (curso.isPresent()){
            cursoService.eliminar(curso.get().getCuId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> u;
        try{
            u = cursoService.asignarUsuario(usuario,cursoId);
            //Cachar feign exception si falla
        }catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "No existe el usuario con el id o hubo un error en la comunicacion : "+ e.getMessage()));
        }

        if (u.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(u.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> u;
        try{
            u = cursoService.crearUsuario(usuario,cursoId);
            //Cachar feign exception si falla
        }catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "No se pudo crear el usuario o hubo un error en la comunicacion : "+ e.getMessage()));
        }

        if (u.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(u.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> u;
        try{
            u = cursoService.eliminarUsuario(usuario,cursoId);
            //Cachar feign exception si falla
        }catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "No existe el usuario con el id o hubo un error en la comunicacion : "+ e.getMessage()));
        }

        if (u.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(u.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    public ResponseEntity<?> eliminarCursoUsuario(@PathVariable Long id){
        cursoService.eliminarCursoUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errores.put(error.getField(), "El campo: " +  error.getField() + "tiene el siguiente error: " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
