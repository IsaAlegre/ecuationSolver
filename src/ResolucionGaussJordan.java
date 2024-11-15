/**
 * Classe que implementa a resolução de sistemas lineares usando o método de Gauss-Jordan.
 * Esta classe implementa a interface ResolverSistema.
 */
public class ResolucionGaussJordan implements ResolverSistema {

    /**
     * Resolve um sistema linear usando o método de Gauss-Jordan.
     * O método transforma a matriz aumentada do sistema em forma escalonada e
     * calcula as soluções do sistema.
     *
     * @param coeficientes uma matriz de coeficientes do sistema
     * @param termosIndependentes um vetor com os termos independentes do sistema
     * @return um vetor contendo as soluções do sistema
     */
    @Override
    public double[] resolver(double[][] coeficientes, double[] termosIndependentes) {
        int n = coeficientes.length;
        double[][] matrizAumentada = new double[n][n + 1];

        // Cria a matriz aumentada, unindo a matriz de coeficientes com os termos independentes
        for (int i = 0; i < n; i++) {
            System.arraycopy(coeficientes[i], 0, matrizAumentada[i], 0, n);
            matrizAumentada[i][n] = termosIndependentes[i];
        }

        // Aplica o método de Gauss-Jordan
        for (int i = 0; i < n; i++) {
            double fator = matrizAumentada[i][i];
            // Divide a linha i pelo fator (elemento pivô)
            for (int j = 0; j <= n; j++) {
                matrizAumentada[i][j] /= fator;
            }

            // Elimina os elementos abaixo e acima do pivô
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    fator = matrizAumentada[k][i];
                    for (int j = 0; j <= n; j++) {
                        matrizAumentada[k][j] -= fator * matrizAumentada[i][j];
                    }
                }
            }
        }

        // Extrai as soluções da matriz aumentada
        double[] solucoes = new double[n];
        for (int i = 0; i < n; i++) {
            solucoes[i] = matrizAumentada[i][n];
        }

        return solucoes;
    }
}
