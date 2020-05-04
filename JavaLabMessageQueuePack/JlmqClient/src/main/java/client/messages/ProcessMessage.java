package client.messages;

import client.jlmq.JlmqConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketMessage;

import java.io.IOException;

@Data
@NoArgsConstructor
public class ProcessMessage {

    private JlmqConsumer consumer;
    private DataRequest message;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ProcessMessage(JlmqConsumer consumer, String json){
        try {
            this.consumer = consumer;
            this.message = objectMapper.readValue(json, DataRequest.class);
        }catch (IOException e){
            throw new IllegalArgumentException(e);
        }
    }

    public void accepted(){
        consumer.accepted(message.getMessageId());
    }

    public String getBody(){
        return message.getBody().toString();
    }

    public void completed(){
        consumer.completed(message.getMessageId());
    }

    public boolean isTask(){
        if(message.getCommand() == null){
            return false;
        }
        return message.getCommand().equals("receive");
    }

    public boolean isNewMessage(){
        if(message.getCommand() == null){
            return false;
        }
        return message.getCommand().equals("new message");
    }

}
