package server.addition.chat.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entities.user.dto.UserDataDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private Long senderId;
    private String text;
    private UserDataDto sender;
}