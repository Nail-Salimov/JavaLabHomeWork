package client.main;

import client.jlmq.Jlmq;
import client.jlmq.JlmqConnector;
import client.jlmq.JlmqConsumer;
import client.jlmq.JlmqProducer;
import client.messages.Body;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       stompProducer();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();


    }

    public static void stompProducer() {
        JlmqConnector connector = Jlmq.connector()
                .url("ws://localhost:8080/messages")
                .type("STOMP")
                .connect();

        JlmqProducer producer = connector.producer()
                .toQueue("document")
                .create();


        Body body = new Body("STOMP TEST");
        producer.send(body);
    }

    public static void stopConsumer() {
        JlmqConnector connector = Jlmq.connector()
                .url("ws://localhost:8080/messages")
                .type("STOMP")
                .connect();

        JlmqConsumer consumer = connector.consumer()
                .subscribe("document")
                .onReceive(message -> {
                    message.accepted();
                    System.out.println(message.getBody());
                    message.completed();
                })
                .create();
    }

    public static void vanillaProducer() {
        JlmqConnector connector = Jlmq.connector()
                .url("ws://localhost:8080/chat")
                .type("vanilla")
                .connect();

        JlmqProducer producer = connector.producer()
                .toQueue("document")
                .create();


        Body body = new Body("vanilla TEST");
        producer.send(body);
    }

    public static void vanillaConsumer() {
        JlmqConnector connector = Jlmq.connector()
                .url("ws://localhost:8080/chat")
                .type("vanilla")
                .connect();

        JlmqConsumer consumer = connector.consumer()
                .subscribe("document")
                .onReceive(message -> {
                    message.accepted();
                    System.out.println(message.getBody());
                    message.completed();
                })
                .create();
    }
}
