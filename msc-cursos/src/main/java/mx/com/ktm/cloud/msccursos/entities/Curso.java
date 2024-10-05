package mx.com.ktm.cloud.msccursos.entities;

import mx.com.ktm.cloud.msccursos.models.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long cuId;

    @NotEmpty
    @Column(name = "c_nombre")
    private String cuName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cuCursoUsuario;

    @Transient
    private List<Usuario> cuUsuarios;

    public Curso(){
        cuCursoUsuario = new ArrayList<>();
        cuUsuarios = new ArrayList<>();
    }

    public Long getCuId() {
        return cuId;
    }

    public void setCuId(Long cuId) {
        this.cuId = cuId;
    }

    public String getCuName() {
        return cuName;
    }

    public void setCuName(String cuName) {
        this.cuName = cuName;
    }

    public void addCursoUsuario(CursoUsuario cursoUsuario){
        cuCursoUsuario.add(cursoUsuario);
    }

    public void removeCursoUsuario(CursoUsuario cursoUsuario){
        cuCursoUsuario.remove(cursoUsuario);
    }

    public List<CursoUsuario> getCuCursoUsuario() {
        return cuCursoUsuario;
    }

    public void setCuCursoUsuario(List<CursoUsuario> cuCursoUsuario) {
        this.cuCursoUsuario = cuCursoUsuario;
    }

    public List<Usuario> getCuUsuarios() {
        return cuUsuarios;
    }

    public void setCuUsuarios(List<Usuario> cuUsuarios) {
        this.cuUsuarios = cuUsuarios;
    }
}
