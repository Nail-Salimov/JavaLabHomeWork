package client.jlmq;

import client.messages.Body;
import client.messages.DataRequest;
import client.websocket.JlmqClient;
import lombok.Data;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

@Data
public class JlmqProducer implements JlmqUser{

    private JlmqClient client;
    private String queueName;

    public void send(Body body){
       DataRequest request = DataRequest.builder()
                .body(body)
                .queueName(queueName)
                .command("send")
                .build();
        client.sendMessage(request);
        System.out.println("send");
    }

    public JlmqProducer toQueue(String queueName){
        this.queueName = queueName;
        return this;
    }

    public JlmqProducer create(){
        return this;
    }

    @Override
    public void transmission(String message) {
        System.out.println(message);
    }
}
