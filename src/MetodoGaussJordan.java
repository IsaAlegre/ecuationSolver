public class MetodoGaussJordan {
    private double[][] matrizAumentada;

    public MetodoGaussJordan(double[][] coeficientes, double[] terminosIndependientes) {
        int n = coeficientes.length;
        matrizAumentada = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(coeficientes[i], 0, matrizAumentada[i], 0, n);
            matrizAumentada[i][n] = terminosIndependientes[i];
        }
    }

    public void resolver() {
        int n = matrizAumentada.length;

        for (int i = 0; i < n; i++) {
            double factor = matrizAumentada[i][i];
            for (int j = 0; j < n + 1; j++) {
                matrizAumentada[i][j] /= factor;
            }

            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double coef = matrizAumentada[k][i];
                    for (int j = 0; j < n + 1; j++) {
                        matrizAumentada[k][j] -= coef * matrizAumentada[i][j];
                    }
                }
            }
        }

        mostrarSoluciones();
    }

    private void mostrarSoluciones() {
        System.out.println("Soluciones del sistema (Método de Gauss-Jordan):");
        for (int i = 0; i < matrizAumentada.length; i++) {
            System.out.println("x" + (i + 1) + " = " + matrizAumentada[i][matrizAumentada.length]);
        }
    }
}
