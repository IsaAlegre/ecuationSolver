import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcuacionesGUI extends JFrame {
    private JTextField numEcuacionesField;
    private JTextField numIncognitasField;
    private JPanel coeficientesPanel;
    private JTextField[][] coeficientesFields;
    private JTextField[] terminosIndependientesFields;
    private JButton resolverButton;
    private JTextArea resultadoArea;
    private JComboBox<String> metodoComboBox;

    public EcuacionesGUI() {
        setTitle("Sistema de Ecuaciones Lineales");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel configuracionPanel = new JPanel(new FlowLayout());
        configuracionPanel.add(new JLabel("Número de Ecuaciones:"));
        numEcuacionesField = new JTextField(5);
        configuracionPanel.add(numEcuacionesField);

        configuracionPanel.add(new JLabel("Número de Incógnitas:"));
        numIncognitasField = new JTextField(5);
        configuracionPanel.add(numIncognitasField);

        JButton generarMatrizButton = new JButton("Generar Matriz");
        configuracionPanel.add(generarMatrizButton);

        // ComboBox para elegir el método de resolución
        metodoComboBox = new JComboBox<>(new String[]{"Gauss-Jordan", "Cramer"});
        configuracionPanel.add(new JLabel("Método:"));
        configuracionPanel.add(metodoComboBox);

        add(configuracionPanel, BorderLayout.NORTH);

        coeficientesPanel = new JPanel();
        add(coeficientesPanel, BorderLayout.CENTER);

        JPanel resultadoPanel = new JPanel(new BorderLayout());
        resolverButton = new JButton("Resolver");
        resultadoPanel.add(resolverButton, BorderLayout.NORTH);

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoPanel.add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        add(resultadoPanel, BorderLayout.SOUTH);

        generarMatrizButton.addActionListener(e -> generarCamposCoeficientes());
        resolverButton.addActionListener(e -> resolverSistema());
    }

    private void generarCamposCoeficientes() {
        int numEcuaciones = Integer.parseInt(numEcuacionesField.getText());
        int numIncognitas = Integer.parseInt(numIncognitasField.getText());

        coeficientesPanel.removeAll();
        coeficientesPanel.setLayout(new GridLayout(numEcuaciones, numIncognitas + 1));

        coeficientesFields = new JTextField[numEcuaciones][numIncognitas];
        terminosIndependientesFields = new JTextField[numEcuaciones];

        for (int i = 0; i < numEcuaciones; i++) {
            for (int j = 0; j < numIncognitas; j++) {
                coeficientesFields[i][j] = new JTextField(5);
                coeficientesPanel.add(coeficientesFields[i][j]);
            }
            terminosIndependientesFields[i] = new JTextField(5);
            coeficientesPanel.add(terminosIndependientesFields[i]);
        }
        coeficientesPanel.revalidate();
        coeficientesPanel.repaint();
    }

    private void resolverSistema() {
        int numEcuaciones = Integer.parseInt(numEcuacionesField.getText());
        int numIncognitas = Integer.parseInt(numIncognitasField.getText());

        double[][] coeficientes = new double[numEcuaciones][numIncognitas];
        double[] terminosIndependientes = new double[numEcuaciones];

        for (int i = 0; i < numEcuaciones; i++) {
            for (int j = 0; j < numIncognitas; j++) {
                coeficientes[i][j] = Double.parseDouble(coeficientesFields[i][j].getText());
            }
            terminosIndependientes[i] = Double.parseDouble(terminosIndependientesFields[i].getText());
        }

        String metodo = (String) metodoComboBox.getSelectedItem();
        ResolverSistema solucionador;

        if ("Gauss-Jordan".equals(metodo)) {
            solucionador = new ResolucionGaussJordan();
        } else {
            solucionador = new ResolucionCramer();
        }

        try {
            double[] soluciones = solucionador.resolver(coeficientes, terminosIndependientes);
            StringBuilder resultado = new StringBuilder("Soluciones:\n");
            for (int i = 0; i < soluciones.length; i++) {
                resultado.append("I").append(i + 1).append(" = ").append(soluciones[i]).append("\n");
            }
            resultadoArea.setText(resultado.toString());
        } catch (IllegalArgumentException ex) {
            resultadoArea.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EcuacionesGUI frame = new EcuacionesGUI();
            frame.setVisible(true);
        });
    }
}
