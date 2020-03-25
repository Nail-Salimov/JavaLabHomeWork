package server.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entities.model.UserDataModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDataDto {
    private Long id;
    private String mail;
    private String password;
    private String name;
    private String role;
    private String state;

    public static UserDataDto getUserDataDto(UserDataModel model){
        return UserDataDto.builder()
                .id(model.getId())
                .mail(model.getMail())
                .name(model.getHashPassword())
                .role(model.getRole().toString())
                .state(model.getState().toString())
                .build();
    }
}
