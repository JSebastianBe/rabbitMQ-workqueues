package co.vinni;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static co.vinni.Producer.sendMsg;

public class Main {
    public static void main(String[] args) {
        System.out.print("Initiated Producer");
        Scanner sc = new Scanner(System.in);
        String msg = "";
        while(msg.isEmpty()) {
            System.out.print("Enter the message to be sent : ");
            msg = sc.next();
            if (msg.isEmpty()) {
                System.out.println(" The message is empty");
            } else {
                try {
                    sendMsg(msg);
                } catch (IOException | TimeoutException e) {
                    System.err.println(" Error " + e);
                }
            }
        }
    }
}
