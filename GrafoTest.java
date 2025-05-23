import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class GrafoTest {        
    private Grafo grafo;

    @Before
    public void setUp() {
        grafo = new Grafo(5);
        grafo.agregarCiudad("A");
        grafo.agregarCiudad("B");
        grafo.agregarCiudad("C");
        grafo.agregarConexion("A", "B", 10);
        grafo.agregarConexion("B", "C", 5);
    }

    @Test
    public void testAgregarCiudad() {
        grafo.agregarCiudad("D");
        assertTrue(grafo.getIndiceCiudad().containsKey("D"));
    }

    @Test
    public void testAgregarConexion() {
        double[][] matriz = grafo.getMatrizAdyacencia();
        int i = grafo.getIndiceCiudad().get("A");
        int j = grafo.getIndiceCiudad().get("B");
        assertEquals(10.0, matriz[i][j], 0.001);
    }

    @Test
    public void testEliminarConexion() {
        grafo.eliminarConexion("A", "B");
        double[][] matriz = grafo.getMatrizAdyacencia();
        int i = grafo.getIndiceCiudad().get("A");
        int j = grafo.getIndiceCiudad().get("B");
        assertEquals(Double.MAX_VALUE, matriz[i][j], 0.001);
    }

    @Test
    public void testFloydDistanciaMinima() {
        Floyd.calcular(grafo);
        int a = grafo.getIndiceCiudad().get("A");
        int c = grafo.getIndiceCiudad().get("C");
        assertEquals(15.0, Floyd.distancia[a][c], 0.001); // A → B → C: 10 + 5
    }

    @Test
    public void testFloydRutaCorrecta() {
        Floyd.calcular(grafo);
        int a = grafo.getIndiceCiudad().get("A");
        int c = grafo.getIndiceCiudad().get("C");
        List<Integer> ruta = Floyd.getRuta(a, c);
        assertNotNull(ruta);
        assertEquals(3, ruta.size()); // A → B → C
        assertEquals((Integer)a, ruta.get(0));
        assertEquals((Integer)c, ruta.get(2));
    }
}
