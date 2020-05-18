package server.entities.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entities.user.UserDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentDto {

    private Long id;
    private String originalName;
    private String storageName;
    private String type;
    private Long size;

    private UserDto user;
}
