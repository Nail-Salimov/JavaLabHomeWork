package server.entities.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SignUpForm {

    @NotEmpty
    @NotNull
    @Email
    private String mail;

    @NotEmpty
    @NotNull
    private String password;

    @NotEmpty
    @NotNull
    private String name;
}
