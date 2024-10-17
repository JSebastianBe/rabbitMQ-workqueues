package co.vinni.colaEventos;

import co.jsbm.Evento;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static co.vinni.Producer.sendEvent;

public class EventsManage {

    public static void sentEvent(Evento event ) {
        if (event != null) {
            try {
                System.out.println("Mensaje: " + event.toString());
                sendEvent(event);
            } catch (IOException | TimeoutException e) {
                System.err.println(" Error " + e.getMessage());
            }
        } else {
            System.out.println("Mensaje vacío");
        }
    }
}
