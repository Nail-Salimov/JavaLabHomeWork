package client.processing;

import client.messages.ProcessMessage;

public interface Processing {
    void process(ProcessMessage message);
}
