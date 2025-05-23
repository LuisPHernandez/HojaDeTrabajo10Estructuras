import java.util.Scanner;
import java.util.List;

public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grafo grafo = CargadorDeGrafo.cargar("logistica.txt");
        boolean seguir = true;

        while (seguir) {
            System.out.println("1. Ruta más corta entre dos ciudades");
            System.out.println("2. Centro del grafo");
            System.out.println("3. Modificar el grafo");
            System.out.println("4. Salir");
            int opcion = scanner.nextInt(); 
            scanner.nextLine();
            switch (opcion) {
                    case 1:
                        System.out.println("Ciudad origen: ");
                        String c1 = scanner.nextLine();
                        System.out.println("Ciudad destino: ");
                        String c2 = scanner.nextLine();

                        Floyd.ejecutarFloyd(grafo);
                        int u = grafo.getCiudadIndice().get(c1);
                        int v = grafo.getCiudadIndice().get(c2);

                        List<Integer> camino = Floyd.getCamino(u, v);
                        if (camino == null) {
                            System.out.println("No hay ruta.");
                        } 
                        else {
                            System.out.println("Ruta más corta: ");
                            for (int indice : camino) {
                                System.out.print(grafo.getIndiceCiudad().get(indice) + " ");
                            }
                            System.out.println("\nDistancia: " + Floyd.menorPeso[u][v]);
                        }
                        break;
                case 2:
                    System.out.println("Centro del grafo: " + CentroDelGrafo.encontrarCentro(grafo));
                    break;
                case 3:
                    continue;
                case 4:
                    seguir = false;
                    System.out.println("Saliendo del programa...");
                    break;
            }
        }

    }
}
