public class CentroDelGrafo {
    /**
     * @param grafo
     * @return
     */
    public static String encontrarCentro(Grafo grafo) {
        // Se encuentra la matriz de pesos mínimos
        Floyd.ejecutarFloyd(grafo);
        double[][] menorPeso = Floyd.menorPeso;
        double excentricidadMinima = Double.MAX_VALUE;
        int centro = -1;

        for (int i = 0; i < menorPeso.length; i++) {
            double excentricidad = 0;
            // Se obtiene el peso de un nodo a todos los demás y se guarda el máximo
            for (int j = 0; j < menorPeso.length; j++) {
                if (i != j && menorPeso[i][j] != Double.MAX_VALUE) {
                    excentricidad = Math.max(excentricidad, menorPeso[i][j]);
                }
            }
            // Se obtiene la mínima excentricidad (menor máximo peso)
            if (excentricidad < excentricidadMinima) {
                excentricidadMinima = excentricidad;
                centro = i;
            }
        }
        // Se devuelve el nombre del nodo centro
        return grafo.getIndiceCiudad().get(centro);
    }
}
