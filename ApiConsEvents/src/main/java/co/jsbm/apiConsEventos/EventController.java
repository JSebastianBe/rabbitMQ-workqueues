package co.jsbm.apiConsEventos;

import co.jsbm.Evento;
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


    @GetMapping("/")
    public String mostrarVista() {
        return "redirect:/listaEventos.html";
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
