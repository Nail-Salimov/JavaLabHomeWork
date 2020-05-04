package client.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmMessage extends Command{
    private String command;
    private String messageId;
    private String queueName;

    public ConfirmMessage(String command, String messageId){
        this.command = command;
        this.messageId = messageId;
    }
}