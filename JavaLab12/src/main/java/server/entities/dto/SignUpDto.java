package server.entities.dto;

import lombok.Data;

@Data
public class SignUpDto {

    private String name;
    private String mail;
    private String password;
}
