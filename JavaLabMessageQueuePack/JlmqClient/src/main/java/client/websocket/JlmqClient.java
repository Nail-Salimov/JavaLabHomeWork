package client.websocket;

import client.jlmq.JlmqUser;

public interface JlmqClient {
    void connect(String url);
    void sendMessage(Object message);
    void setUser(JlmqUser jlmqUser);
    void subscribe();
}
