public class ResolucionCramer implements ResolverSistema {
    @Override
    public double[] resolver(double[][] coeficientes, double[] terminosIndependientes) {
        int n = coeficientes.length;
        double determinantePrincipal = calcularDeterminante(coeficientes);

        if (determinantePrincipal == 0) {
            throw new IllegalArgumentException("El sistema no tiene solución única (determinante es cero).");
        }

        double[] soluciones = new double[n];

        for (int i = 0; i < n; i++) {
            double[][] matrizModificada = reemplazarColumna(coeficientes, terminosIndependientes, i);
            soluciones[i] = calcularDeterminante(matrizModificada) / determinantePrincipal;
        }

        return soluciones;
    }

    private double calcularDeterminante(double[][] matriz) {
        int n = matriz.length;
        if (n == 2) {
            return matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0];
        }

        double determinante = 0;
        for (int i = 0; i < n; i++) {
            double[][] submatriz = new double[n - 1][n - 1];
            for (int j = 1; j < n; j++) {
                int colSubmatriz = 0;
                for (int k = 0; k < n; k++) {
                    if (k == i) continue;
                    submatriz[j - 1][colSubmatriz] = matriz[j][k];
                    colSubmatriz++;
                }
            }
            determinante += matriz[0][i] * calcularDeterminante(submatriz) * (i % 2 == 0 ? 1 : -1);
        }
        return determinante;
    }

    private double[][] reemplazarColumna(double[][] matriz, double[] columna, int indiceColumna) {
        int n = matriz.length;
        double[][] matrizModificada = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matriz[i], 0, matrizModificada[i], 0, n);
            matrizModificada[i][indiceColumna] = columna[i];
        }
        return matrizModificada;
    }
}
