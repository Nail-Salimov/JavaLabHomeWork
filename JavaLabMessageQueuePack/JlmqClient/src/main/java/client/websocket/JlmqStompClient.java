package client.websocket;

import client.jlmq.JlmqUser;
import client.messages.Command;
import client.messages.RequestMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JlmqStompClient implements JlmqClient {

    private JlmqUser jlmqUser;
    private StompSession stompSession;
    private StompSessionHandler sessionHandler;
    private final ObjectMapper objectMapper;
    private String subscribeUrl;
    private Lock lock = new ReentrantLock();

    public JlmqStompClient() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void connect(String url) {
        try {
            WebSocketClient client = new StandardWebSocketClient();
            WebSocketStompClient stompClient = new WebSocketStompClient(client);
            stompClient.setMessageConverter(new StringMessageConverter());
            stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

            sessionHandler = new MyHandler();
            stompSession = stompClient.connect(url, sessionHandler).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void sendMessage(Object message) {
        if (message instanceof RequestMessage) {
            RequestMessage request = (RequestMessage) message;
            if (request.getCommand().equals("subscribe")) {
                subscribeUrl = request.getQueueName();
                return;
            }
        }

        try {
            Command command = (Command) message;
            stompSession.send("/app/" + command.getCommand(), objectMapper.writeValueAsString(message));

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
            stompSession.subscribe("/topic/consumer/" + subscribeUrl, sessionHandler);
            RequestMessage subscribeMessage = new RequestMessage("subscribe", subscribeUrl);
            stompSession.send("/app/" + subscribeMessage.getCommand(), objectMapper.writeValueAsString(subscribeMessage));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    private class MyHandler extends StompSessionHandlerAdapter {


        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            System.out.println("Connected: " + session.toString());
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            jlmqUser.transmission((String) payload);
        }

    }

}
