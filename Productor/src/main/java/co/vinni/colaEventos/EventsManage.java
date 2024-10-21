package co.vinni.colaEventos;

import co.jsbm.Evento;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static co.vinni.Producer.sendEventP;
import static co.vinni.Producer.sendEventStringP;

public class EventsManage {

    public void sentEvent(Evento event ) {
        if (event != null) {
            try {
                System.out.println("Mensaje: " + event.toString());
                sendEventP(event);
            } catch (IOException | TimeoutException e) {
                System.err.println(" Error " + e.getMessage());
            }
        } else {
            System.out.println("Mensaje vacío");
        }
    }

    public void sentEventString(String event ) {
        if (!event.equals("") || event.isEmpty()) {
            try {
                System.out.println("Mensaje: " + event.toString());
                sendEventStringP(event);
            } catch (IOException | TimeoutException e) {
                System.err.println(" Error " + e.getMessage());
            }
        } else {
            System.out.println("Mensaje vacío");
        }
    }
}
