package diccionariovisual;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Hashtable;

public class DiccionarioVisual extends JFrame {
    private Hashtable<String, String> diccionario = new Hashtable<>();
    private JTextField txtPalabra = new JTextField(20);
    private JList<String> listaPalabras = new JList<>(new DefaultListModel<>());
    private JLabel lblImagen = new JLabel();

    public DiccionarioVisual() {
        setTitle("Diccionario Visual de Palabras");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        Color fondoPrincipal = new Color(60, 63, 65);
        Color fondoPanel = new Color(43, 43, 43);
        Color colorTexto = Color.WHITE;
        Color colorBotonAgregar = new Color(0, 123, 255);
        Color colorBotonBuscar = new Color(51, 204, 51);

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(fondoPanel);

        JLabel lblPalabra = new JLabel("Palabra:");
        lblPalabra.setForeground(colorTexto);
        panelSuperior.add(lblPalabra);

        txtPalabra.setFont(new Font("Arial", Font.PLAIN, 16));
        txtPalabra.setForeground(colorTexto);
        txtPalabra.setBackground(new Color(69, 73, 74));
        panelSuperior.add(txtPalabra);

        JButton btnAgregar = new JButton("Agregar Palabra");
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 16));
        btnAgregar.setBackground(colorBotonAgregar);
        btnAgregar.setForeground(colorTexto);
        panelSuperior.add(btnAgregar);

        listaPalabras.setFont(new Font("Arial", Font.PLAIN, 16));
        listaPalabras.setFixedCellHeight(30);
        listaPalabras.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(colorTexto), "Palabras Agregadas", 2, 2, null, colorTexto));
        listaPalabras.setBackground(new Color(69, 73, 74));
        listaPalabras.setForeground(colorTexto);
        JScrollPane scrollPane = new JScrollPane(listaPalabras);
        scrollPane.setPreferredSize(new Dimension(200, 300));
        scrollPane.setBackground(fondoPrincipal);

        lblImagen.setHorizontalAlignment(JLabel.CENTER);
        lblImagen.setPreferredSize(new Dimension(300, 300));
        lblImagen.setBorder(BorderFactory.createLineBorder(colorTexto));
        lblImagen.setOpaque(true);
        lblImagen.setBackground(fondoPrincipal);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(panelSuperior, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(scrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblImagen, gbc);

        btnAgregar.addActionListener(e -> agregarPalabra());

        listaPalabras.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                mostrarImagen();
            }
        });

        getContentPane().setBackground(fondoPrincipal);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void agregarPalabra() {
        String palabra = txtPalabra.getText().trim();

        if (palabra.isEmpty() || !palabra.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una palabra válida (solo letras).");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("Imágenes (JPG, JPEG, PNG)", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filtroImagen);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            String rutaImagen = archivo.getAbsolutePath();
            diccionario.put(palabra, rutaImagen);

            ((DefaultListModel<String>) listaPalabras.getModel()).addElement(palabra);

            txtPalabra.setText("");
        }
    }

    private void mostrarImagen() {
        String palabraSeleccionada = listaPalabras.getSelectedValue();
        if (palabraSeleccionada != null) {
            String rutaImagen = diccionario.get(palabraSeleccionada);
            if (rutaImagen != null) {
                ImageIcon imagen = new ImageIcon(rutaImagen);

                int labelWidth = lblImagen.getWidth();
                int labelHeight = lblImagen.getHeight();

                if (labelWidth > 0 && labelHeight > 0) {
                    lblImagen.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH)));
                } else {
                    lblImagen.setIcon(imagen);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DiccionarioVisual::new);
    }
}
