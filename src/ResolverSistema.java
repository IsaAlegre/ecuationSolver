/**
 * Interface que define o contrato para a resolução de sistemas lineares.
 * Qualquer classe que implemente esta interface deve fornecer uma implementação do método
 * {@link #resolver(double[][], double[])} para resolver um sistema linear.
 */
public interface ResolverSistema {

    /**
     * Resolve um sistema linear com base nos coeficientes e termos independentes fornecidos.
     *
     * @param coeficientes uma matriz de coeficientes do sistema, onde cada linha representa uma equação
     * @param termosIndependentes um vetor contendo os termos independentes de cada equação
     * @return um vetor contendo as soluções do sistema
     */
    double[] resolver(double[][] coeficientes, double[] termosIndependentes);
}
