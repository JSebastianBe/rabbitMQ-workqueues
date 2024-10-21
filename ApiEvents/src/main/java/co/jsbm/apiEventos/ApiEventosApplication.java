package co.jsbm.apiEventos;

import co.jsbm.Evento;
import co.jsbm.Respuesta;
import co.vinni.colaEventos.EventsManage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
@Service
public class ApiEventosApplication {


	public static void main(String[] args) {
		SpringApplication.run(ApiEventosApplication.class, args);
	}

	public Respuesta sendMessage(Evento mensaje) {
		EventsManage em = new EventsManage();
		em.sentEvent(mensaje);
		return new Respuesta("La petición fue enviada",false);
	}
}
