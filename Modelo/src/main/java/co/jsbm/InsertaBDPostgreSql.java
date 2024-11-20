package co.jsbm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertaBDPostgreSql {

    private String mensaje;
    private String respuesta;
    private boolean error;
    private Evento ev;

    // Datos de conexión a PostgreSQL
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "123456";

    public InsertaBDPostgreSql() {
        this.error = false;
    }

    public InsertaBDPostgreSql(String mensaje) {
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
        String sql = "INSERT INTO public.evento (empleado, nombre, fecha_hora) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.ev.getEmpleado());
            pstmt.setString(2, this.ev.getNombre());
            pstmt.setString(3, this.ev.getFecha_hora());

            int filasInsertadas = pstmt.executeUpdate();
            if (filasInsertadas > 0) {
                this.error = false;
                this.respuesta = "Inserción exitosa.";
            }

        } catch (SQLException e) {
            this.error = true;
            this.respuesta = "Ocurrió un error en la escritura en PostgreSql: " + e.getMessage();
        }
    }
}
