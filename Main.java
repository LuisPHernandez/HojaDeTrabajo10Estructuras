import java.util.Scanner;
import java.io.IOException;
import java.util.List;

public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grafo grafo = null;
        try {
            grafo = GestorDeGrafo.cargar("logistica.txt");
        }
        catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            scanner.close();
            return;
        }
        boolean seguir = true;

        while (seguir) {
            System.out.println("1. Ruta más corta entre dos ciudades");
            System.out.println("2. Centro del grafo");
            System.out.println("3. Modificar el grafo");
            System.out.println("4. Salir");
            System.out.print("Ingrese el número de la opción que desea ejecutar: ");
            int opcion = scanner.nextInt(); 
            scanner.nextLine();
            switch (opcion) {
                    case 1:
                        System.out.println("\nCiudad origen: ");
                        String c1 = scanner.nextLine();
                        System.out.println("Ciudad destino: ");
                        String c2 = scanner.nextLine();

                        Floyd.ejecutarFloyd(grafo);
                        int u = grafo.getCiudadIndice().get(c1);
                        int v = grafo.getCiudadIndice().get(c2);

                        List<Integer> camino = Floyd.getCamino(u, v);
                        if (camino == null) {
                            System.out.println("\nNo hay ruta.");
                        } 
                        else {
                            System.out.println("\nRuta más corta: ");
                            for (int indice : camino) {
                                System.out.print(grafo.getIndiceCiudad().get(indice) + " ");
                            }
                            System.out.println("\n\nDistancia: " + Floyd.menorPeso[u][v]);
                        }
                        System.out.println("Presione <ENTER> para regresar al menú");
                        scanner.nextLine();
                        break;
                case 2:
                    System.out.println("\nCentro del grafo: " + CentroDelGrafo.encontrarCentro(grafo));
                    System.out.println("Presione <ENTER> para regresar al menú");
                        scanner.nextLine();
                    break;
                case 3:
                    System.out.println("\nOpciones de modificación:");
                    System.out.println("1. Interrumpir tráfico entre ciudades");
                    System.out.println("2. Agregar nueva conexión");
                    System.out.println("3. Actualizar clima entre ciudades");
                    System.out.print("Ingrese el número de la opción que desea ejecutar: ");
                    int modificacion = scanner.nextInt();
                    scanner.nextLine();
                    switch (modificacion) {
                        case 1:
                            System.out.println("\nCiudad origen: ");
                            c1 = scanner.nextLine();
                            System.out.println("Ciudad destino: ");
                            c2 = scanner.nextLine();
                            if (!grafo.eliminarConexion(c1, c2) || !GestorDeGrafo.borrarConexion(c1, c2, "logistica.txt")) {
                                System.out.println("Hubo un error al interrumpir la conexión.");
                                break;
                            }
                            System.out.println("La conexión entre " + c1 + " y " + c2 + " fue interrumpida.");
                            break;
                        case 2:
                            System.out.println("\nCiudad origen: ");
                            c1 = scanner.nextLine();
                            System.out.println("Ciudad destino: ");
                            c2 = scanner.nextLine();
                            System.out.println("Tiempo con clima normal en horas: ");
                            String tiempoNormal = scanner.nextLine();
                            System.out.println("Tiempo con lluvia en horas: ");
                            String tiempoLluvia = scanner.nextLine();
                            System.out.println("Tiempo con nieve en horas: ");
                            String tiempoNieve = scanner.nextLine();
                            System.out.println("Tiempo con tormenta en horas: ");
                            String tiempoTormenta = scanner.nextLine();

                            try {
                                if (!GestorDeGrafo.crearConexion(grafo, c1, c2, tiempoNormal, tiempoLluvia, tiempoNieve, tiempoTormenta, "logistica.txt")) {
                                    System.out.println("Error al crear conexión, alguna de las ciudades no existe");
                                    break;
                                }
                                else {
                                    System.out.println("Nueva conexión creada entre " + c1 + " y " + c2 + " con tiempo de " + tiempoNormal + " horas.");
                                }
                            }
                            catch (IOException e) {
                                System.out.println("Error al escribir en el archivo: " + e.getMessage());
                            }
                            break;
                        case 3:
                            System.out.println("\nCiudad origen: ");
                            c1 = scanner.nextLine();
                            System.out.println("Ciudad destino: ");
                            c2 = scanner.nextLine();
                            System.out.println("Climas:");
                            System.out.println("1. Normal");
                            System.out.println("2. Lluvia");
                            System.out.println("3. Nieve");
                            System.out.println("4. Tormenta");
                            System.out.print("Ingrese el número que corresponde al clima entre " + c1 + " y " + c2 +  ": ");
                            int clima = scanner.nextInt();
                            scanner.nextLine();
                            try {
                                if (!GestorDeGrafo.cambiarClima(grafo, c1, c2, clima, "logistica.txt")) {
                                    System.out.println("No existe una conexión entre " + c1 + " y " + c2 + ".");
                                    break;
                                }
                                else {
                                    System.out.println("Clima actualizado entre " + c1 + " y " + c2 + ".");
                                }
                            }
                            catch (IOException e) {
                                System.out.println("Error al leer el archivo: " + e.getMessage());
                                break;
                            }
                            break;
                        default:
                            System.out.println("\nOpción inválida");
                            break;
                    }
                    System.out.println("Presione <ENTER> para regresar al menú");
                    scanner.nextLine();
                    break;
                case 4:
                    seguir = false;
                    System.out.println("\nSaliendo del programa...");
                    break;
                default:
                    System.out.println("\nOpción inválida, intente de nuevo");
                    System.out.println("Presione <ENTER> para regresar al menú");
                    scanner.nextLine();
                    break;
            }
        }
        scanner.close();
    }
}
