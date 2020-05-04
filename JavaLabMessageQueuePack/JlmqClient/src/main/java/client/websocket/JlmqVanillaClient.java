package client.websocket;

import client.jlmq.JlmqUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

@Data
public class JlmqVanillaClient implements JlmqClient {

    private JlmqUser jlmqUser;
    private WebSocketSession webSocketSession;
    private ObjectMapper objectMapper;
    private Object object;

    public JlmqVanillaClient() {
        this.objectMapper = new ObjectMapper();
        this.object = new Object();
    }

    @Override
    public void connect(String url) {
        try {
            WebSocketClient webSocketClient = new StandardWebSocketClient();

            webSocketSession = webSocketClient.doHandshake(new TextWebSocketHandler() {
                @Override
                public void handleTextMessage(WebSocketSession session, TextMessage message) {

                    synchronized (object) {
                        jlmqUser.transmission(message.getPayload());
                        object.notifyAll();
                    }
                }

                @Override
                public void afterConnectionEstablished(WebSocketSession session) {
                    System.out.println("Connected: " + session.toString());
                }
            }, new WebSocketHttpHeaders(), URI.create(url)).get();

        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void sendMessage(Object message) {
        try {
            String json = objectMapper.writeValueAsString(message);
            TextMessage textMessage = new TextMessage(json);
            webSocketSession.sendMessage(textMessage);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    @Override
    public void setUser(JlmqUser jlmqUser) {
        this.jlmqUser = jlmqUser;
    }

    @Override
    public void subscribe() {
        try {
            while (true) {
                synchronized (object) {
                    object.wait();
                }
            }
        }catch (InterruptedException e){
            throw new IllegalArgumentException(e);
        }
    }

}
