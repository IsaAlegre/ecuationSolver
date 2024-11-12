import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Classe EcuacionesGUI - Interface gráfica para resolver sistemas de equações lineares.
 * Permite ao usuário inserir coeficientes e termos independentes, escolher um método
 * de resolução (Gauss-Jordan ou Regra de Cramer) e visualizar os resultados.
 * A interface também permite alternar entre idiomas (espanhol e português).
 */
public class EcuacionesGUI extends JFrame {
    private JTextField numEcuacionesField;
    private JTextField numIncognitasField;
    private JPanel coeficientesPanel;
    private JTextField[][] coeficientesFields;
    private JTextField[] terminosIndependientesFields;
    private JButton resolverButton;
    private JTextArea resultadoArea;
    private JComboBox<String> metodoComboBox;
    private ResourceBundle mensajes;

    /**
     * Construtor da interface gráfica EcuacionesGUI. Define os componentes da interface
     * e configura o layout e as funcionalidades de troca de idioma, geração da matriz e resolução.
     */
    public EcuacionesGUI() {

        setIdioma("es", "ES");
        setTitle(mensajes.getString("titulo"));
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 228, 242));


        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(255, 192, 203));
        JMenu menuIdioma = new JMenu("🌐 Idioma");
        JMenuItem espanol = new JMenuItem("Español 🇪🇸");
        JMenuItem portugues = new JMenuItem("Portugués 🇵🇹");

        espanol.addActionListener(e -> cambiarIdioma("es", "ES"));
        portugues.addActionListener(e -> cambiarIdioma("pt", "PT"));

        menuIdioma.add(espanol);
        menuIdioma.add(portugues);
        menuBar.add(menuIdioma);
        setJMenuBar(menuBar);

        JPanel configuracionPanel = new JPanel(new FlowLayout());
        configuracionPanel.setBackground(new Color(255, 228, 242));

        JLabel labelEcuaciones = new JLabel("🔢 Número de Ecuaciones:");
        labelEcuaciones.setForeground(new Color(100, 50, 50));
        configuracionPanel.add(labelEcuaciones);

        numEcuacionesField = new JTextField(5);
        numEcuacionesField.setFont(new Font("Arial", Font.PLAIN, 16));
        configuracionPanel.add(numEcuacionesField);

        JLabel labelIncognitas = new JLabel("❓ Número de Incógnitas:");
        labelIncognitas.setForeground(new Color(100, 50, 50));
        configuracionPanel.add(labelIncognitas);

        numIncognitasField = new JTextField(5);
        numIncognitasField.setFont(new Font("Arial", Font.PLAIN, 16));
        configuracionPanel.add(numIncognitasField);


        JButton generarMatrizButton = new JButton("Generar Matriz");
        generarMatrizButton.setBackground(new Color(255, 192, 203));
        generarMatrizButton.setForeground(new Color(100, 50, 50));
        generarMatrizButton.setFont(new Font("Arial", Font.BOLD, 12));
        configuracionPanel.add(generarMatrizButton);

        metodoComboBox = new JComboBox<>(new String[]{"Gauss-Jordan", "Cramer"});
        metodoComboBox.setBackground(new Color(255, 192, 203));
        metodoComboBox.setForeground(new Color(100, 50, 50));
        metodoComboBox.setFont(new Font("Arial", Font.BOLD, 12));
        configuracionPanel.add(new JLabel("📐 Método:"));
        configuracionPanel.add(metodoComboBox);

        add(configuracionPanel, BorderLayout.NORTH);

        coeficientesPanel = new JPanel();
        coeficientesPanel.setBackground(new Color(255, 240, 245));
        add(coeficientesPanel, BorderLayout.CENTER);

        JPanel resultadoPanel = new JPanel(new BorderLayout());
        resultadoPanel.setBackground(new Color(255, 228, 242));

        resolverButton = new JButton("Resolver");
        resolverButton.setBackground(new Color(255, 182, 193));
        resolverButton.setForeground(Color.WHITE);
        resolverButton.setFont(new Font("Arial", Font.BOLD, 16));
        resultadoPanel.add(resolverButton, BorderLayout.NORTH);

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setBackground(new Color(218, 123, 172));
        resultadoArea.setForeground(new Color(100, 50, 50));
        resultadoArea.setFont(new Font("Arial", Font.BOLD, 14));
        resultadoPanel.add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        add(resultadoPanel, BorderLayout.SOUTH);

        generarMatrizButton.addActionListener(e -> generarCamposCoeficientes());
        resolverButton.addActionListener(e -> resolverSistema());
    }

    private void setIdioma(String idioma, String pais) {
        Locale locale = new Locale(idioma, pais);
        mensajes = ResourceBundle.getBundle("messages", locale);
    }

    private void cambiarIdioma(String idioma, String pais) {
        setIdioma(idioma, pais);
        setTitle(mensajes.getString("titulo"));

        ((JLabel) ((JPanel) getContentPane().getComponent(0)).getComponent(0)).setText(mensajes.getString("numero_ecuaciones"));
        ((JLabel) ((JPanel) getContentPane().getComponent(0)).getComponent(2)).setText(mensajes.getString("numero_incognitas"));
        ((JButton) ((JPanel) getContentPane().getComponent(0)).getComponent(4)).setText( mensajes.getString("generar_matriz"));
        ((JLabel) ((JPanel) getContentPane().getComponent(0)).getComponent(6)).setText("📐 " + mensajes.getString("metodo"));
        resolverButton.setText( mensajes.getString("resolver"));
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
                coeficientesFields[i][j].setBackground(new Color(255, 240, 245));
                coeficientesFields[i][j].setForeground(new Color(100, 50, 50));
                coeficientesFields[i][j].setFont(new Font("Arial", Font.BOLD, 14));
                coeficientesPanel.add(coeficientesFields[i][j]);
            }
            terminosIndependientesFields[i] = new JTextField(5);
            terminosIndependientesFields[i].setBackground(new Color(255, 240, 245));
            terminosIndependientesFields[i].setForeground(new Color(100, 50, 50));
            terminosIndependientesFields[i].setFont(new Font("Arial", Font.BOLD, 14));
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
