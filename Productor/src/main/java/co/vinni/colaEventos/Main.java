package co.vinni.colaEventos;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static co.vinni.Producer.sendEvent;

public class Main {
    public static void main(String[] args) {
        System.out.print("Initiated Producer");
        Scanner sc = new Scanner(System.in);
        String msg = "";
        while(msg.isEmpty()) {
            System.out.print("Ingrese mensaje a enviar : ");
            msg = sc.next();
            if (msg.isEmpty()) {
                System.out.println("Mensaje vacío");
            } else {
                try {
                    sendEvent(msg);
                } catch (IOException | TimeoutException e) {
                    System.err.println(" Error " + e);
                }
            }
        }
    }
}
