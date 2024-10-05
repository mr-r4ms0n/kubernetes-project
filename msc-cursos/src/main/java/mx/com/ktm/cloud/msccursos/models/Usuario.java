package mx.com.ktm.cloud.msccursos.models;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class Usuario {

    private Long usId;

    private String usName;

    private String usEmail;

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
