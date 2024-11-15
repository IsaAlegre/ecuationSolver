import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class EcuacionesGUI extends JFrame {
    private JTextField numEcuacionesField;
    private JTextField numIncognitasField;
    private JPanel coeficientesPanel;
    private JTextField[][] coeficientesFields;
    private JTextField[] terminosIndependientesFields;
    private JButton resolverButton;
    private JButton limpiarButton;
    private JTextArea resultadoArea;
    private JComboBox<String> metodoComboBox;
    private JButton idiomasButton;
    private ResourceBundle mensajes;
    private JLabel labelEcuaciones;
    private JLabel labelIncognitas;
    private JButton generarMatrizButton;

    public EcuacionesGUI() {
        setIdioma("es", "ES");
        setTitle(mensajes.getString("titulo"));
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 228, 242));

        JPanel configuracionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        configuracionPanel.setBackground(new Color(255, 228, 242));

        labelEcuaciones = new JLabel("🔢 " + mensajes.getString("numero_ecuaciones"));
        labelEcuaciones.setForeground(new Color(100, 50, 50));
        configuracionPanel.add(labelEcuaciones);

        numEcuacionesField = new JTextField(5);
        numEcuacionesField.setFont(new Font("Arial", Font.PLAIN, 16));
        configuracionPanel.add(numEcuacionesField);

        labelIncognitas = new JLabel("❓ " + mensajes.getString("numero_incognitas"));
        labelIncognitas.setForeground(new Color(100, 50, 50));
        configuracionPanel.add(labelIncognitas);

        numIncognitasField = new JTextField(5);
        numIncognitasField.setFont(new Font("Arial", Font.PLAIN, 16));
        configuracionPanel.add(numIncognitasField);

        generarMatrizButton = new JButton(mensajes.getString("generar_matriz"));
        generarMatrizButton.setBackground(new Color(255, 192, 203));
        generarMatrizButton.setForeground(new Color(100, 50, 50));
        generarMatrizButton.setFont(new Font("Arial", Font.BOLD, 12));
        configuracionPanel.add(generarMatrizButton);

        metodoComboBox = new JComboBox<>(new String[]{"Gauss-Jordan", "Cramer"});
        metodoComboBox.setBackground(new Color(255, 192, 203));
        metodoComboBox.setForeground(new Color(100, 50, 50));
        metodoComboBox.setFont(new Font("Arial", Font.BOLD, 12));
        configuracionPanel.add(new JLabel("📐 " + mensajes.getString("metodo")));
        configuracionPanel.add(metodoComboBox);

        limpiarButton = new JButton("🧹 Limpiar");
        limpiarButton.setBackground(new Color(255, 192, 203));
        limpiarButton.setForeground(new Color(100, 50, 50));
        limpiarButton.setFont(new Font("Arial", Font.BOLD, 12));
        configuracionPanel.add(limpiarButton);

        idiomasButton = new JButton("🌐 Idiomas");
        idiomasButton.setBackground(new Color(255, 192, 203));
        idiomasButton.setForeground(new Color(100, 50, 50));
        idiomasButton.setFont(new Font("Arial", Font.BOLD, 12));
        configuracionPanel.add(idiomasButton);

        add(configuracionPanel, BorderLayout.NORTH);

        coeficientesPanel = new JPanel();
        coeficientesPanel.setBackground(new Color(255, 240, 245));
        coeficientesPanel.setBorder(BorderFactory.createTitledBorder("Matriz de Coeficientes"));

        JScrollPane scrollPane = new JScrollPane(coeficientesPanel);
        scrollPane.setPreferredSize(new Dimension(700, 250));
        add(scrollPane, BorderLayout.CENTER);

        JPanel resultadoPanel = new JPanel(new BorderLayout());
        resultadoPanel.setBackground(new Color(255, 228, 242));

        resolverButton = new JButton(mensajes.getString("resolver"));
        resolverButton.setBackground(new Color(255, 182, 193));
        resolverButton.setForeground(Color.WHITE);
        resolverButton.setFont(new Font("Arial", Font.BOLD, 16));
        resultadoPanel.add(resolverButton, BorderLayout.NORTH);

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setBackground(new Color(218, 123, 172));
        resultadoArea.setForeground(new Color(100, 50, 50));
        resultadoArea.setFont(new Font("Arial", Font.BOLD, 14));
        resultadoArea.setMargin(new Insets(10, 10, 10, 10));
        resultadoPanel.add(new JScrollPane(resultadoArea), BorderLayout.SOUTH);

        add(resultadoPanel, BorderLayout.SOUTH);

        generarMatrizButton.addActionListener(e -> generarCamposCoeficientes());
        resolverButton.addActionListener(e -> resolverSistema());
        limpiarButton.addActionListener(e -> limpiarCampos());
        idiomasButton.addActionListener(e -> cambiarIdioma());
    }

    private void setIdioma(String idioma, String pais) {
        Locale locale = new Locale(idioma, pais);
        mensajes = ResourceBundle.getBundle("messages", locale);
    }

    private void cambiarIdioma() {
        String[] opciones = {"Español", "Portugués"};
        int seleccion = JOptionPane.showOptionDialog(this, "Seleccione un idioma", "Idioma", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
        if (seleccion == 0) {
            setIdioma("es", "ES");
        } else if (seleccion == 1) {
            setIdioma("pt", "PT");
        }

        setTitle(mensajes.getString("titulo"));
        labelEcuaciones.setText("🔢 " + mensajes.getString("numero_ecuaciones"));
        labelIncognitas.setText("❓ " + mensajes.getString("numero_incognitas"));
        generarMatrizButton.setText(mensajes.getString("generar_matriz"));
        metodoComboBox.setToolTipText(mensajes.getString("metodo"));
        limpiarButton.setText(mensajes.getString("limpiar"));
        idiomasButton.setText(mensajes.getString("idiomas"));
        resolverButton.setText(mensajes.getString("resolver"));
    }

    private void generarCamposCoeficientes() {
        int numEcuaciones = Integer.parseInt(numEcuacionesField.getText());
        int numIncognitas = Integer.parseInt(numIncognitasField.getText());

        coeficientesPanel.removeAll();
        coeficientesPanel.setLayout(new GridLayout(numEcuaciones, numIncognitas + 1, 5, 5));
        coeficientesFields = new JTextField[numEcuaciones][numIncognitas];
        terminosIndependientesFields = new JTextField[numEcuaciones];

        for (int i = 0; i < numEcuaciones; i++) {
            for (int j = 0; j < numIncognitas; j++) {
                coeficientesFields[i][j] = new JTextField(5);
                coeficientesFields[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                coeficientesFields[i][j].setHorizontalAlignment(JTextField.CENTER);
                coeficientesFields[i][j].setBackground(new Color(255, 240, 245));
                coeficientesFields[i][j].setForeground(new Color(100, 50, 50));
                coeficientesPanel.add(coeficientesFields[i][j]);
            }
            terminosIndependientesFields[i] = new JTextField(5);
            terminosIndependientesFields[i].setFont(new Font("Arial", Font.BOLD, 18));
            terminosIndependientesFields[i].setHorizontalAlignment(JTextField.CENTER);
            terminosIndependientesFields[i].setBackground(new Color(255, 240, 245));
            terminosIndependientesFields[i].setForeground(new Color(100, 50, 50));
            coeficientesPanel.add(terminosIndependientesFields[i]);
        }
        coeficientesPanel.revalidate();
        coeficientesPanel.repaint();
    }

    private void resolverSistema() {
        if (!validarCampos()) return;

        int numEcuaciones = Integer.parseInt(numEcuacionesField.getText());
        int numIncognitas = Integer.parseInt(numIncognitasField.getText());

        double[][] coeficientes = new double[numEcuaciones][numIncognitas];
        double[] terminosIndependientes = new double[numEcuaciones];

        for (int i = 0; i < numEcuaciones; i++) {
            for (int j = 0; j < numIncognitas; j++) {
                try {
                    coeficientes[i][j] = Double.parseDouble(coeficientesFields[i][j].getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Solo se aceptan números en la matriz", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            try {
                terminosIndependientes[i] = Double.parseDouble(terminosIndependientesFields[i].getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Solo se aceptan números en la matriz", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String metodo = (String) metodoComboBox.getSelectedItem();
        ResolverSistema solucionador;

        if ("Gauss-Jordan".equals(metodo)) {
            solucionador = new ResolucionGaussJordan();
        } else {
            solucionador = new ResolucionCramer();
        }

        double[] solucion = solucionador.resolver(coeficientes, terminosIndependientes);
        mostrarResultado(solucion);
    }

    private void limpiarCampos() {
        numEcuacionesField.setText("");
        numIncognitasField.setText("");
        coeficientesPanel.removeAll();
        coeficientesPanel.revalidate();
        coeficientesPanel.repaint();
        resultadoArea.setText("");
    }

    private boolean validarCampos() {
        try {
            int numEcuaciones = Integer.parseInt(numEcuacionesField.getText());
            int numIncognitas = Integer.parseInt(numIncognitasField.getText());

            // Verifica si el número de ecuaciones o incógnitas es negativo o cero
            if (numEcuaciones <= 0 || numIncognitas <= 0) {
                JOptionPane.showMessageDialog(this, mensajes.getString("error_faltan_numeros"),
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Validar que todos los campos estén rellenados
            for (int i = 0; i < numEcuaciones; i++) {
                for (int j = 0; j < numIncognitas; j++) {
                    String texto = coeficientesFields[i][j].getText().trim();
                    if (texto.isEmpty()) {
                        JOptionPane.showMessageDialog(this, mensajes.getString("error_faltan_numeros"),
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    try {
                        Double.parseDouble(texto); // Verificar si es un número
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, mensajes.getString("error_numeros_matriz"),
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
                // Validar términos independientes
                String textoTerminoIndependiente = terminosIndependientesFields[i].getText().trim();
                if (textoTerminoIndependiente.isEmpty()) {
                    JOptionPane.showMessageDialog(this, mensajes.getString("error_faltan_numeros"),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                try {
                    Double.parseDouble(textoTerminoIndependiente); // Verificar si es un número
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, mensajes.getString("error_numeros_matriz"),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, mensajes.getString("error_faltan_numeros"),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }



    private void mostrarResultado(double[] solucion) {
        resultadoArea.setText("Solución:\n");
        for (int i = 0; i < solucion.length; i++) {
            resultadoArea.append("x" + (i + 1) + " = " + solucion[i] + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EcuacionesGUI frame = new EcuacionesGUI();
            frame.setVisible(true);
        });
    }
}
