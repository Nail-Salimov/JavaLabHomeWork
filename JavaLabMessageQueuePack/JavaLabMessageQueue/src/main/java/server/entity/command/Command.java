package server.entity.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Command {
    private String command;
    private String queueName;
    private Body body;
    private String messageId;
    private String state;
}
