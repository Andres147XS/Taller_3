import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class App {

    private JFrame frame;
    private JTable tabla;
    private Arbol arbol;
    private JTextField campoBusqueda;

    public App() {
        arbol = new Arbol();
        inicializarInterfaz();
    }

    private void inicializarInterfaz() {
        frame = new JFrame("Agenda de Contactos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Crear tabla de contactos
        tabla = new JTable();
        arbol.mostrar(tabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel para botones y campo de búsqueda
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Ajuste del layout

        // Crear botones con texto y sus íconos
        JButton btnCargar = new JButton("Cargar", new ImageIcon("cargar.png"));
        JButton btnAgregar = new JButton("Agregar", new ImageIcon("agregar.png"));
        JButton btnBuscar = new JButton("Buscar", new ImageIcon("buscar.png"));

        // Ajustar el tamaño del botón al contenido del texto
        btnCargar.setPreferredSize(new Dimension(btnCargar.getPreferredSize().width + 30, 30));
        btnAgregar.setPreferredSize(new Dimension(btnAgregar.getPreferredSize().width + 30, 30));
        btnBuscar.setPreferredSize(new Dimension(btnBuscar.getPreferredSize().width + 30, 30));

        // Añadir botones y campo de texto al panel
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCargar);
        campoBusqueda = new JTextField(10); // Campo de texto para buscar
        panelBotones.add(campoBusqueda);
        panelBotones.add(btnBuscar);

        frame.add(panelBotones, BorderLayout.NORTH);

        // Acciones de botones
        btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarArchivo();
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarContacto();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarContacto();
            }
        });

        frame.setVisible(true);
    }

    private void cargarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(frame);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            arbol.desdeArchivo(archivoSeleccionado.getAbsolutePath());
            arbol.mostrar(tabla);
        }
    }

    private void agregarContacto() {
        JTextField nombreField = new JTextField();
        JTextField telefonoField = new JTextField();
        JTextField celularField = new JTextField();
        JTextField direccionField = new JTextField();
        JTextField correoField = new JTextField();

        Object[] campos = {
            "Nombre:", nombreField,
            "Teléfono:", telefonoField,
            "Celular:", celularField,
            "Dirección:", direccionField,
            "Correo:", correoField
        };

        int option = JOptionPane.showConfirmDialog(null, campos, "Agregar Contacto", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Nodo nuevoNodo = new Nodo(
                    nombreField.getText(),
                    telefonoField.getText(),
                    celularField.getText(),
                    direccionField.getText(),
                    correoField.getText());
            arbol.insertarNodo(nuevoNodo);
            arbol.mostrar(tabla);
        }
    }

    private void buscarContacto() {
        String nombreBuscado = campoBusqueda.getText().trim();
        if (!nombreBuscado.isEmpty()) {
            Nodo nodoEncontrado = arbol.buscarPorNombre(nombreBuscado);
            if (nodoEncontrado != null) {
                int fila = arbol.obtenerPosicionNodo(nodoEncontrado);
                tabla.setRowSelectionInterval(fila, fila); // Resalta la fila en la tabla
            } else {
                JOptionPane.showMessageDialog(frame, "Contacto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Por favor, ingrese un nombre para buscar.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }
}
