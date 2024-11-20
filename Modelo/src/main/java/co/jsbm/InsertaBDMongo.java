package co.jsbm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class InsertaBDMongo {

    private String mensaje;
    private String respuesta;
    private boolean error;
    private Evento ev;

    String connectionString = "mongodb+srv://jbenavidesm1:jbenavidesm1@ucentral-jsbm.varw3.mongodb.net/RegistroEventos?retryWrites=true&w=majority";

    public InsertaBDMongo() {
        this.error = false;
    }

    public InsertaBDMongo(String mensaje) {
        this.mensaje = mensaje;
        this.error = false;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
        this.ev = new Evento();
        String[] textoSeparado = this.mensaje.split("\\|");
        this.ev.setEmpleado(textoSeparado[1]);
        this.ev.setNombre(textoSeparado[0]);
        this.ev.setFecha_hora(textoSeparado[2]);
    }

    public boolean isError() {
        return error;
    }

    public void Save(){

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            // Obtener la base de datos
            MongoDatabase database = mongoClient.getDatabase("RegistroEventos");

            // Obtener la colección
            MongoCollection<Document> collection = database.getCollection("Eventos");

            // Crear un documento para insertar
            Document document = new Document("empleado", this.ev.getEmpleado())
                    .append("nombre", this.ev.getNombre())
                    .append("fecha_hora", this.ev.getFecha_hora());

            // Insertar el documento en la colección
            collection.insertOne(document);
            this.error = false;
            this.respuesta = "Documento insertado exitosamente!";
        } catch (Exception e) {
            this.error = true;
            this.respuesta = "Ocurrió un error en la escritura en mongoDB: " + e.getMessage();
        }

    }
}
