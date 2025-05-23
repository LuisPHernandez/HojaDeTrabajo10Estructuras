import java.util.*;

public class Grafo{
    // Atributos
    /**
     *
     */
    private Map<String, Integer> ciudadIndice;
    /**
     *
     */
    private List<String> indiceCiudad;
    /**
     *
     */
    private double[][] matriz;
    /**
     *
     */
    private double infinito = Double.MAX_VALUE;

    // Métodos
    /**
     * @param n
     */
    public Grafo(int n) {
        this.ciudadIndice = new HashMap<>();
        this.indiceCiudad = new ArrayList<>();
        this.matriz = new double[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(this.matriz[i], this.infinito);
        for (int i = 0; i < n; i++) this.matriz[i][i] = 0;
    }

    /**
     * @param ciudad
     */
    public void agregarCiudad(String ciudad) {
        if (!this.ciudadIndice.containsKey(ciudad)) {
            this.ciudadIndice.put(ciudad, this.ciudadIndice.size());
            this.indiceCiudad.add(ciudad);
        }
    }

    /**
     * @param c1
     * @param c2
     * @param peso
     * @return
     */
    public boolean agregarConexion(String c1, String c2, double peso) {
        if (!this.ciudadIndice.containsKey(c1) || !this.ciudadIndice.containsKey(c2)) {
            return false;
        }
        int i = this.ciudadIndice.get(c1);
        int j = this.ciudadIndice.get(c2);
        this.matriz[i][j] = peso;
        return true;
    }

    /**
     * @param c1
     * @param c2
     * @return
     */
    public boolean eliminarConexion(String c1, String c2) {
        if (!this.ciudadIndice.containsKey(c1) || !this.ciudadIndice.containsKey(c2)) {
            return false;
        }
        int i = this.ciudadIndice.get(c1);
        int j = this.ciudadIndice.get(c2);
        this.matriz[i][j] = this.infinito;
        return true;
    }

    /**
     * @return
     */
    public double[][] getMatriz() {
        return this.matriz;
    }

    /**
     * @return
     */
    public List<String> getIndiceCiudad() {
        return this.indiceCiudad;
    }

    /**
     * @return
     */
    public Map<String, Integer> getCiudadIndice() {
        return this.ciudadIndice;
    }
}