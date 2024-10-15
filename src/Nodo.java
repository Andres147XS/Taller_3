public class Nodo {
    String nombre;
    String telefono;
    String celular;
    String direccion;
    String correo;
    Nodo izquierdo, derecho;

    public Nodo(String nombre, String telefono, String celular, String direccion, String correo) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.celular = celular;
        this.direccion = direccion;
        this.correo = correo;
        this.izquierdo = this.derecho = null;
    }
}