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
        assertTrue(grafo.getIndiceCiudad().contains("D"));
    }

    @Test
    public void testAgregarConexion() {
        double[][] matriz = grafo.getMatriz();
        int i = grafo.getCiudadIndice().get("A");
        int j = grafo.getCiudadIndice().get("B");
        assertEquals(10.0, matriz[i][j], 0.001);
    }

    @Test
    public void testEliminarConexion() {
        grafo.eliminarConexion("A", "B");
        double[][] matriz = grafo.getMatriz();
        int i = grafo.getCiudadIndice().get("A");
        int j = grafo.getCiudadIndice().get("B");
        assertEquals(Double.MAX_VALUE, matriz[i][j], 0.001);
    }

    @Test
    public void testFloydDistanciaMinima() {
        Floyd.ejecutarFloyd(grafo);
        int a = grafo.getCiudadIndice().get("A");
        int c = grafo.getCiudadIndice().get("C");
        assertEquals(15.0, Floyd.menorPeso[a][c], 0.001); // A → B → C: 10 + 5
    }

    @Test
    public void testFloydRutaCorrecta() {
        Floyd.ejecutarFloyd(grafo);
        int a = grafo.getCiudadIndice().get("A");
        int c = grafo.getCiudadIndice().get("C");
        List<Integer> ruta = Floyd.getCamino(a, c);
        assertNotNull(ruta);
        assertEquals(3, ruta.size()); // A → B → C
        assertEquals((Integer)a, ruta.get(0));
        assertEquals((Integer)c, ruta.get(2));
    }
}
