/**
 * Classe responsável por operações em matrizes, incluindo o cálculo do determinante.
 */
public class Matriz {

    /**
     * Calcula o determinante de uma matriz quadrada de ordem 2 ou 3.
     * Para matrizes de ordem maior, o método de Laplace deve ser implementado.
     *
     * @param matriz uma matriz quadrada representada por um array bidimensional de números reais
     * @return o valor do determinante da matriz se a matriz for de ordem 2 ou 3; 0 caso contrário
     */
    public static double calcularDeterminante(double[][] matriz) {
        int n = matriz.length;
        if (n == 2) {
            return matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0];
        } else if (n == 3) {
            return matriz[0][0] * (matriz[1][1] * matriz[2][2] - matriz[1][2] * matriz[2][1]) -
                    matriz[0][1] * (matriz[1][0] * matriz[2][2] - matriz[1][2] * matriz[2][0]) +
                    matriz[0][2] * (matriz[1][0] * matriz[2][1] - matriz[1][1] * matriz[2][0]);
        }
        // Implementar o método de Laplace para matrizes de ordem maior
        return 0;
    }
}
