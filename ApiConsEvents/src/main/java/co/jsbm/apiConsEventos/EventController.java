package co.jsbm.apiConsEventos;

import co.jsbm.Evento;
import com.google.gson.Gson;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/eventos")
@CrossOrigin(origins = "http://localhost:8081")
public class EventController {

    // Datos de conexión a PostgreSQL
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "123456";

    String connectionString = "mongodb+srv://jbenavidesm1:jbenavidesm1@ucentral-jsbm.varw3.mongodb.net/";


    @GetMapping("/")
    public String mostrarVista() {
        return "redirect:/listaEventos.html";
    }

    @GetMapping("listarMongo")
    public List<Evento> consultarEventoMongo(){
        List<Evento> registros = new ArrayList<>();
        // Crear cliente de MongoDB
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            // Obtener la base de datos
            MongoDatabase database = mongoClient.getDatabase("RegistroEventos");
            // Obtener la colección
            MongoCollection<Document> collection = database.getCollection("Eventos");
            // Consultar todos los documentos en la colección
            System.out.println("Documentos en la colección:");
            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    String json = doc.toJson().replace("_id", "id");
                    try {
                        Gson gson = new Gson();
                        Evento evento = gson.fromJson(json, Evento.class);
                        System.out.println("Objeto deserializado: " + evento);
                        registros.add(evento);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return registros;
    }

    @GetMapping("/listar")
    public List<Evento> consultarEvento(){
        List<Evento> registros = new ArrayList<>();
        String sql = "SELECT empleado, nombre, fecha_hora FROM  public.evento";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Evento entidad = new Evento();
                entidad.setEmpleado(rs.getString("empleado"));
                entidad.setNombre(rs.getString("nombre"));
                entidad.setFecha_hora(rs.getString("fecha_hora"));
                // Establece todos los campos necesarios de TuEntidad
                registros.add(entidad);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar los registros de la base de datos");
            e.printStackTrace();
        }

        return registros;
    }
}
