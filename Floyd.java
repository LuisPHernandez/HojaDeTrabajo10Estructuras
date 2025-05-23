import java.util.List;
import java.util.ArrayList;

public class Floyd {
    // Atributos
    /**
     *
     */
    private double[][] menorPeso;
    /**
     *
     */
    private int[][] siguiente;
    
    // Métodos
    // Método para aplicar el algoritmo del Floyd al grafo
    /**
     * @param grafo
     */
    public void ejecutarFloyd(Grafo grafo) {
        int n = grafo.getIndiceCiudad().size();
        menorPeso = new double[n][n];
        siguiente = new int[n][n];
        double[][] matriz = grafo.getMatriz();

        // Se copia la matriz de adyacencia original del grafo a la matriz de menores pesos
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                menorPeso[i][j] = matriz[i][j];
                siguiente[i][j] = (matriz[i][j] != Double.MAX_VALUE) ? j : -1;
            }
        }

        // Se lleva a cabo el algoritmo de Floyd
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (menorPeso[i][k] != Double.MAX_VALUE && menorPeso[k][j] != Double.MAX_VALUE && menorPeso[i][j] > menorPeso[i][k] + menorPeso[k][j]) {
                        menorPeso[i][j] = menorPeso[i][k] + menorPeso[k][j];
                        siguiente[i][j] = siguiente[i][k];
                    }
                }
            }
        }
    }

    // Método para devolver el camino mas corto entre dos nodos en el grafo
    /**
     * @param u
     * @param v
     * @return
     */
    public List<Integer> getCamino(int u, int v) {
        // Si no hay camino se devuelve null
        if (siguiente[u][v] == -1) return null;

        List<Integer> camino = new ArrayList<>();
        while (u != v) {
            camino.add(u);
            u = siguiente[u][v];
        }
        camino.add(v);
        return camino;
    }
}
