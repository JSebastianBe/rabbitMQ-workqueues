package co.jsbm;

import java.sql.Timestamp;

public class Evento {
    private String empleado;
    private String nombre;
    private String fecha_hora;

    public String getFecha_hora() {
        return fecha_hora;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    @Override
    public String toString() {
        return nombre + "|" + empleado  + "|" + fecha_hora;
    }
}
