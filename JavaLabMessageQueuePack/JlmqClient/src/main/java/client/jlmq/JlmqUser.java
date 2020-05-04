package client.jlmq;

import org.springframework.web.socket.WebSocketMessage;

public interface JlmqUser {
    void transmission(String message);
}
