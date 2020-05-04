package server.entity.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entity.command.Body;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageGeneralDto {

    private String command;
    private String queueName;
    private String messageId;
    private Body body;
}
