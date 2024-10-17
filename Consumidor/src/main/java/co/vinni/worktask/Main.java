package co.vinni.worktask;

import co.vinni.Receiver;
import com.rabbitmq.tools.json.JSONUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Consumer Task");
        try {
            Receiver.receiveWork();
        } catch (IOException | TimeoutException e) {
            System.err.println("Error to receive message "+e);
        }
    }
}
