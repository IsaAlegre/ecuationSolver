public class MetodoCramer {
    private double[][] coeficientes;
    private double[] terminosIndependientes;

    public MetodoCramer(double[][] coeficientes, double[] terminosIndependientes) {
        this.coeficientes = coeficientes;
        this.terminosIndependientes = terminosIndependientes;
    }

    public void resolver() {
        double determinantePrincipal = Matriz.calcularDeterminante(coeficientes);
        if (determinantePrincipal == 0) {
            System.out.println("El sistema no tiene solución única.");
            return;
        }

        double[] soluciones = new double[coeficientes.length];
        for (int i = 0; i < coeficientes.length; i++) {
            double[][] matrizTemporal = reemplazarColumna(coeficientes, terminosIndependientes, i);
            soluciones[i] = Matriz.calcularDeterminante(matrizTemporal) / determinantePrincipal;
        }

        mostrarSoluciones(soluciones);
    }

    private double[][] reemplazarColumna(double[][] matriz, double[] columna, int index) {
        double[][] resultado = new double[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            System.arraycopy(matriz[i], 0, resultado[i], 0, matriz[i].length);
            resultado[i][index] = columna[i];
        }
        return resultado;
    }

    private void mostrarSoluciones(double[] soluciones) {
        System.out.println("Soluciones del sistema (Método de Cramer):");
        for (int i = 0; i < soluciones.length; i++) {
            System.out.println("x" + (i + 1) + " = " + soluciones[i]);
        }
    }
}
