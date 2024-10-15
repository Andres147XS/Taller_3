import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Arbol {
    private Nodo raiz;

    public Arbol() {
        this.raiz = null;
    }

    // Método para insertar un nodo en el ABB
    public void insertarNodo(Nodo nuevoNodo) {
        if (raiz == null) {
            raiz = nuevoNodo;
        } else {
            insertarRecursivo(raiz, nuevoNodo);
        }
    }

    private void insertarRecursivo(Nodo actual, Nodo nuevoNodo) {
        if (nuevoNodo.nombre.compareToIgnoreCase(actual.nombre) < 0) {
            if (actual.izquierdo == null) {
                actual.izquierdo = nuevoNodo;
            } else {
                insertarRecursivo(actual.izquierdo, nuevoNodo);
            }
        } else {
            if (actual.derecho == null) {
                actual.derecho = nuevoNodo;
            } else {
                insertarRecursivo(actual.derecho, nuevoNodo);
            }
        }
    }

    // Método para buscar un contacto por nombre
    public Nodo buscarPorNombre(String nombre) {
        return buscarPorNombreRecursivo(raiz, nombre.toUpperCase());
    }

    private Nodo buscarPorNombreRecursivo(Nodo nodo, String nombre) {
        if (nodo == null) {
            return null;
        }
        int comparacion = nombre.compareTo(nodo.nombre.toUpperCase());
        if (comparacion == 0) {
            return nodo;
        } else if (comparacion < 0) {
            return buscarPorNombreRecursivo(nodo.izquierdo, nombre);
        } else {
            return buscarPorNombreRecursivo(nodo.derecho, nombre);
        }
    }

    // Mostrar los contactos en la tabla
    public void mostrar(JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");
        modelo.addColumn("Telefono");
        modelo.addColumn("Celular");
        modelo.addColumn("Direccion");
        modelo.addColumn("Correo");

        llenarTabla(raiz, modelo);
        tabla.setModel(modelo);
    }

    private void llenarTabla(Nodo nodo, DefaultTableModel modelo) {
        if (nodo != null) {
            llenarTabla(nodo.izquierdo, modelo);
            modelo.addRow(new Object[]{nodo.nombre, nodo.telefono, nodo.celular, nodo.direccion, nodo.correo});
            llenarTabla(nodo.derecho, modelo);
        }
    }

    // Cargar contactos desde un archivo
    public void desdeArchivo(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                Nodo nuevoNodo = new Nodo(datos[0], datos[1], datos[2], datos[3], datos[4]);
                insertarNodo(nuevoNodo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Obtener la posición del nodo en InOrden
    public int obtenerPosicionNodo(Nodo nodoBuscado) {
        return buscarPosicionInOrden(raiz, nodoBuscado, -1);
    }

    private int buscarPosicionInOrden(Nodo nodo, Nodo nodoBuscado, int posicionActual) {
        if (nodo != null) {
            posicionActual = buscarPosicionInOrden(nodo.izquierdo, nodoBuscado, posicionActual);
            posicionActual++;
            if (nodo == nodoBuscado) {
                return posicionActual;
            }
            posicionActual = buscarPosicionInOrden(nodo.derecho, nodoBuscado, posicionActual);
        }
        return posicionActual;
    }
}
