package mx.com.ktm.cloud.mscusuarios.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private Long usId;

    @NotBlank
    @Column(name = "u_nombre")
    private String usName;

    @NotEmpty
    @Email
    @Column(name = "u_correo", unique = true)
    private String usEmail;

    @NotBlank
    @Column(name = "u_password")
    private String password;

    public Long getUsId() {
        return usId;
    }

    public void setUsId(Long usId) {
        this.usId = usId;
    }

    public String getUsName() {
        return usName;
    }

    public void setUsName(String usName) {
        this.usName = usName;
    }

    public String getUsEmail() {
        return usEmail;
    }

    public void setUsEmail(String usEmail) {
        this.usEmail = usEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
