public class SistemaEcuaciones {
    private double[][] coeficientes;
    private double[] terminosIndependientes;

    // Constructor que inicializa los coeficientes y términos independientes
    public SistemaEcuaciones(double[][] coeficientes, double[] terminosIndependientes) {
        this.coeficientes = coeficientes;
        this.terminosIndependientes = terminosIndependientes;
    }

    // Método para resolver el sistema de ecuaciones utilizando Gauss-Jordan
    public double[] resolverConGaussJordan() {
        int n = coeficientes.length;

        // Crear la matriz aumentada
        double[][] matrizAumentada = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(coeficientes[i], 0, matrizAumentada[i], 0, n);
            matrizAumentada[i][n] = terminosIndependientes[i];
        }

        // Aplicar el método de Gauss-Jordan
        for (int i = 0; i < n; i++) {
            // Hacer que el elemento diagonal sea 1
            double factor = matrizAumentada[i][i];
            for (int j = 0; j <= n; j++) {
                matrizAumentada[i][j] /= factor;
            }

            // Hacer que todos los elementos en la columna actual (excepto la diagonal) sean 0
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    factor = matrizAumentada[k][i];
                    for (int j = 0; j <= n; j++) {
                        matrizAumentada[k][j] -= factor * matrizAumentada[i][j];
                    }
                }
            }
        }

        // Extraer las soluciones
        double[] soluciones = new double[n];
        for (int i = 0; i < n; i++) {
            soluciones[i] = matrizAumentada[i][n];
        }

        return soluciones;
    }
}
