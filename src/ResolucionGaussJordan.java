public class ResolucionGaussJordan implements ResolverSistema {
    @Override
    public double[] resolver(double[][] coeficientes, double[] terminosIndependientes) {
        int n = coeficientes.length;
        double[][] matrizAumentada = new double[n][n + 1];

        for (int i = 0; i < n; i++) {
            System.arraycopy(coeficientes[i], 0, matrizAumentada[i], 0, n);
            matrizAumentada[i][n] = terminosIndependientes[i];
        }

        for (int i = 0; i < n; i++) {
            double factor = matrizAumentada[i][i];
            for (int j = 0; j <= n; j++) {
                matrizAumentada[i][j] /= factor;
            }

            for (int k = 0; k < n; k++) {
                if (k != i) {
                    factor = matrizAumentada[k][i];
                    for (int j = 0; j <= n; j++) {
                        matrizAumentada[k][j] -= factor * matrizAumentada[i][j];
                    }
                }
            }
        }

        double[] soluciones = new double[n];
        for (int i = 0; i < n; i++) {
            soluciones[i] = matrizAumentada[i][n];
        }

        return soluciones;
    }
}
