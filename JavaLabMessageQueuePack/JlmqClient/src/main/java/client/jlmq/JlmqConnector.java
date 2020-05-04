package client.jlmq;

import client.websocket.JlmqClient;
import client.websocket.JlmqStompClient;
import client.websocket.JlmqVanillaClient;

public class JlmqConnector {

    private String url;
    private String type;
    private JlmqClient jlmqClient;

    public JlmqConnector url(String url){
        this.url = url;
        return this;
    }

    public JlmqConnector type(String type){
        this.type = type;
        return this;
    }

    public JlmqConnector connect(){
        if(type.equals("vanilla")){
            this.jlmqClient = new JlmqVanillaClient();
            jlmqClient.connect(this.url);
        }else if(type.equals("STOMP")){
            this.jlmqClient = new JlmqStompClient();
            jlmqClient.connect(this.url);
        }else {
            throw new IllegalArgumentException("invalid type");
        }
        return this;
    }

    public JlmqProducer producer(){
        JlmqProducer producer = new JlmqProducer();
        producer.setClient(jlmqClient);
        jlmqClient.setUser(producer);
        return producer;
    }

    public JlmqConsumer consumer(){
        JlmqConsumer consumer = new JlmqConsumer();
        consumer.setJlmqClient(jlmqClient);
        jlmqClient.setUser(consumer);
        return consumer;
    }
}
