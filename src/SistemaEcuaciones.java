/**
 * Classe que representa um sistema de equações lineares.
 * A classe armazena os coeficientes e os termos independentes do sistema,
 * e fornece um método para resolver o sistema utilizando o método de Gauss-Jordan.
 */
public class SistemaEcuaciones {
    private double[][] coeficientes;
    private double[] terminosIndependientes;

    /**
     * Construtor que inicializa os coeficientes e os termos independentes do sistema.
     *
     * @param coeficientes uma matriz que contém os coeficientes das equações
     * @param termosIndependentes um vetor que contém os termos independentes das equações
     */
    public SistemaEcuaciones(double[][] coeficientes, double[] termosIndependentes) {
        this.coeficientes = coeficientes;
        this.terminosIndependientes = termosIndependentes;
    }

    /**
     * Resolve o sistema de equações lineares utilizando o método de Gauss-Jordan.
     * O método transforma a matriz aumentada do sistema em forma escalonada e
     * calcula as soluções do sistema.
     *
     * @return um vetor contendo as soluções do sistema
     */
    public double[] resolverComGaussJordan() {
        int n = coeficientes.length;

        // Criar a matriz aumentada unindo os coeficientes e os termos independentes
        double[][] matrizAumentada = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(coeficientes[i], 0, matrizAumentada[i], 0, n);
            matrizAumentada[i][n] = terminosIndependientes[i];
        }

        // Aplicar o método de Gauss-Jordan
        for (int i = 0; i < n; i++) {
            // Fazer o elemento da diagonal ser 1
            double fator = matrizAumentada[i][i];
            for (int j = 0; j <= n; j++) {
                matrizAumentada[i][j] /= fator;
            }

            // Tornar os elementos na coluna atual (exceto na diagonal) iguais a 0
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    fator = matrizAumentada[k][i];
                    for (int j = 0; j <= n; j++) {
                        matrizAumentada[k][j] -= fator * matrizAumentada[i][j];
                    }
                }
            }
        }

        // Extrair as soluções
        double[] solucoes = new double[n];
        for (int i = 0; i < n; i++) {
            solucoes[i] = matrizAumentada[i][n];
        }

        return solucoes;
    }
}
