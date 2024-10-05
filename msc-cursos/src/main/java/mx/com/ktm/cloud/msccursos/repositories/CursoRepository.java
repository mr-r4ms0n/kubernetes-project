package mx.com.ktm.cloud.msccursos.repositories;

import mx.com.ktm.cloud.msccursos.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("cursoRepository")
public interface CursoRepository extends CrudRepository<Curso, Long> {

    @Modifying
    @Query("DELETE FROM CursoUsuario cu WHERE cu.usuarioId=?1")
    public void eliminarCursoUsuarioPorId(Long id);
}
