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

        tabla = new JTable();
        arbol.mostrar(tabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnCargar = new JButton("Cargar", new ImageIcon("cargar.png"));
        JButton btnAgregar = new JButton("Agregar", new ImageIcon("agregar.png"));
        JButton btnBuscar = new JButton("Buscar", new ImageIcon("buscar.png"));

        btnCargar.setPreferredSize(new Dimension(btnCargar.getPreferredSize().width + 30, 30));
        btnAgregar.setPreferredSize(new Dimension(btnAgregar.getPreferredSize().width + 30, 30));
        btnBuscar.setPreferredSize(new Dimension(btnBuscar.getPreferredSize().width + 30, 30));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnCargar);
        campoBusqueda = new JTextField(10);
        panelBotones.add(campoBusqueda);
        panelBotones.add(btnBuscar);

        frame.add(panelBotones, BorderLayout.NORTH);

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
                tabla.setRowSelectionInterval(fila, fila);
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
