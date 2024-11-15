/**
 * Classe que implementa a resolução de sistemas lineares usando a Regra de Cramer.
 * Esta classe implementa a interface ResolverSistema.
 */
public class ResolucionCramer implements ResolverSistema {

    /**
     * Resolve um sistema linear usando a Regra de Cramer.
     *
     * @param coeficientes uma matriz de coeficientes do sistema
     * @param termosIndependentes um vetor com os termos independentes do sistema
     * @return um vetor contendo as soluções do sistema
     * @throws IllegalArgumentException se o sistema não tiver uma solução única (determinante principal igual a zero)
     */
    @Override
    public double[] resolver(double[][] coeficientes, double[] termosIndependentes) {
        int n = coeficientes.length;
        double determinantePrincipal = calcularDeterminante(coeficientes);

        if (determinantePrincipal == 0) {
            throw new IllegalArgumentException("O sistema não possui solução única (determinante principal é zero).");
        }

        double[] solucoes = new double[n];

        for (int i = 0; i < n; i++) {
            double[][] matrizModificada = substituirColuna(coeficientes, termosIndependentes, i);
            solucoes[i] = calcularDeterminante(matrizModificada) / determinantePrincipal;
        }

        return solucoes;
    }

    /**
     * Calcula o determinante de uma matriz quadrada.
     *
     * @param matriz uma matriz quadrada representada por um array bidimensional
     * @return o valor do determinante da matriz
     */
    private double calcularDeterminante(double[][] matriz) { //Este método calcula el determinante de una matriz cuadrada de cualquier tamaño.
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

    /**
     * Substitui uma coluna de uma matriz pelos valores de um vetor.
     *
     * @param matriz a matriz original
     * @param coluna o vetor que substituirá a coluna na matriz
     * @param indiceColuna o índice da coluna a ser substituída
     * @return uma nova matriz com a coluna substituída
     */
    private double[][] substituirColuna(double[][] matriz, double[] coluna, int indiceColuna) {
        int n = matriz.length;
        double[][] matrizModificada = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matriz[i], 0, matrizModificada[i], 0, n);
            matrizModificada[i][indiceColuna] = coluna[i];
        }
        return matrizModificada;
    }
}
