package co.jsbm;

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

    @Override
    public String toString() {
        return "empleado='" + empleado + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fecha_hora='" + fecha_hora + '\'';
    }
}