package client.messages;

import client.messages.Body;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataRequest extends Command{

    private String command;
    private String queueName;
    private String messageId;
    private String state;
    private Body body;

    private DataRequest(String state){
        this.state = state;
    }
}
