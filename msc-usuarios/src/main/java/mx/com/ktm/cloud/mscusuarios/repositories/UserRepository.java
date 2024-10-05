package mx.com.ktm.cloud.mscusuarios.repositories;

import mx.com.ktm.cloud.mscusuarios.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByUsEmail(String email);
}
