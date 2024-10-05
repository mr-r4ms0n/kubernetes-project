package mx.com.ktm.cloud.mscusuarios.controllers;

import mx.com.ktm.cloud.mscusuarios.entities.Usuario;
import mx.com.ktm.cloud.mscusuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Environment env;

    @GetMapping("/crash")
    public void crash(){
        ((ConfigurableApplicationContext) context).close();
    }

    @GetMapping()
    public ResponseEntity<?> listar(){

        Map<String, Object> body = new HashMap<>();
        body.put("users", usuarioService.listar());
        body.put("podinfo", env.getProperty("MY_POD_NAME") + ": " + env.getProperty("MY_POD_IP"));
        body.put("texto", env.getProperty("config.texto"));
        //return Collections.singletonMap("users", usuarioService.listar());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.porId(id);
        if(usuario.isPresent()){
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result){

        if (result.hasErrors()){
            return validar(result);
        }

        if (!usuario.getUsEmail().isEmpty() && usuarioService.findUserByEmail(usuario.getUsEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Ya existe un usuario con ese correo electronico"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){

        if (result.hasErrors()){
            return validar(result);
        }
        Optional<Usuario> userFound = usuarioService.porId(id);
        if(userFound.isPresent()){
            Usuario newUser = userFound.get();
            if (!usuario.getUsEmail().isEmpty() && usuarioService.findUserByEmail(usuario.getUsEmail()).isPresent() && !usuario.getUsEmail().equalsIgnoreCase(newUser.getUsEmail())){
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Ya existe un usuario con ese correo electronico"));
            }
            newUser.setUsName(usuario.getUsName());
            newUser.setUsEmail(usuario.getUsEmail());
            newUser.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(newUser));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Usuario> o = usuarioService.porId(id);
        if(o.isPresent()){
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(usuarioService.listarPorIds(ids));
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errores.put(error.getField(), "El campo: " +  error.getField() + "tiene el siguiente error: " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
