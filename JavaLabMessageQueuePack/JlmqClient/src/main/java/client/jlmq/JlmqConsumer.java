package client.jlmq;

import client.messages.ProcessMessage;
import client.processing.Processing;
import client.messages.ConfirmMessage;
import client.messages.RequestMessage;
import client.websocket.JlmqClient;
import lombok.Data;
import org.springframework.web.socket.WebSocketMessage;

@Data
public class JlmqConsumer implements JlmqUser {

    private JlmqClient jlmqClient;
    private String queueName;
    private Processing processing;

    public JlmqConsumer subscribe(String queueName) {
        this.queueName = queueName;
        return this;
    }

    public JlmqConsumer create() {
        RequestMessage subscribeMessage = new RequestMessage("subscribe", queueName);

        jlmqClient.sendMessage(subscribeMessage);
        jlmqClient.subscribe();
        return this;
    }

    @Override
    public void transmission(String message) {
        ProcessMessage processMessage = new ProcessMessage(this, message);
        if (processMessage.isTask()) {
            processing.process(processMessage);
        }
    }

    public JlmqConsumer onReceive(Processing processing) {
        this.processing = processing;
        return this;
    }

    public void accepted(String messageId) {
        ConfirmMessage message = new ConfirmMessage("accept", messageId);
        message.setQueueName(queueName);
        jlmqClient.sendMessage(message);
    }

    public void completed(String messageId) {
        ConfirmMessage message = new ConfirmMessage("completed", messageId);
        message.setQueueName(queueName);
        jlmqClient.sendMessage(message);
    }

}
