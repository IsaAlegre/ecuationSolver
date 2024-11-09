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

    public EcuacionesGUI() {
        setTitle("Sistema de Ecuaciones Lineales");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para ingresar el número de ecuaciones e incógnitas
        JPanel configuracionPanel = new JPanel(new FlowLayout());
        configuracionPanel.add(new JLabel("Número de Ecuaciones:"));
        numEcuacionesField = new JTextField(5);
        configuracionPanel.add(numEcuacionesField);

        configuracionPanel.add(new JLabel("Número de Incógnitas:"));
        numIncognitasField = new JTextField(5);
        configuracionPanel.add(numIncognitasField);

        JButton generarMatrizButton = new JButton("Generar Matriz");
        configuracionPanel.add(generarMatrizButton);
        add(configuracionPanel, BorderLayout.NORTH);

        // Panel para los coeficientes
        coeficientesPanel = new JPanel();
        add(coeficientesPanel, BorderLayout.CENTER);

        // Panel de resultados
        JPanel resultadoPanel = new JPanel(new BorderLayout());
        resolverButton = new JButton("Resolver");
        resultadoPanel.add(resolverButton, BorderLayout.NORTH);

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoPanel.add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        add(resultadoPanel, BorderLayout.SOUTH);

        // Acción para generar la matriz de coeficientes
        generarMatrizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarCamposCoeficientes();
            }
        });

        // Acción para resolver el sistema de ecuaciones
        resolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resolverSistema();
            }
        });
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

        // Obtener valores de los campos de texto
        for (int i = 0; i < numEcuaciones; i++) {
            for (int j = 0; j < numIncognitas; j++) {
                coeficientes[i][j] = Double.parseDouble(coeficientesFields[i][j].getText());
            }
            terminosIndependientes[i] = Double.parseDouble(terminosIndependientesFields[i].getText());
        }

        // Crear una instancia del solucionador de sistemas de ecuaciones y resolver
        SistemaEcuaciones sistema = new SistemaEcuaciones(coeficientes, terminosIndependientes);
        double[] soluciones = sistema.resolverConGaussJordan(); // Puedes cambiar al método de Cramer si lo deseas

        // Mostrar resultados
        StringBuilder resultado = new StringBuilder("Soluciones:\n");
        for (int i = 0; i < soluciones.length; i++) {
            resultado.append("I").append(i + 1).append(" = ").append(soluciones[i]).append("\n");
        }
        resultadoArea.setText(resultado.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EcuacionesGUI frame = new EcuacionesGUI();
            frame.setVisible(true);
        });
    }
}

