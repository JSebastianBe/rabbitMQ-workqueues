package co.vinni.colaEventos;

import co.vinni.Receiver;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Consumidor Eventos");
        try {
            Receiver.receiveEvent();
        } catch (IOException | TimeoutException e) {
            System.err.println("Error al recibir el mensaje: "+e);
        }
    }
}
