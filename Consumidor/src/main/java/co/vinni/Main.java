package co.vinni;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Consumer");
        try {
            Receiver.receive();
        } catch (IOException | TimeoutException e) {
            System.err.println("Error to receive message "+e);
        }
    }
}
