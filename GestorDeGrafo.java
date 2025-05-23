import java.io.*;
import java.util.*;

public class GestorDeGrafo {
    /**
     * @param nombreArchivo
     * @return
     */
    public static Grafo cargar(String nombreArchivo) throws IOException {
        Set<String> ciudades = new HashSet<>();
        List<String[]> conexiones = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(" ");
                if (partes.length != 6) continue;

                ciudades.add(partes[0]);
                ciudades.add(partes[1]);
                conexiones.add(partes);
            }
        } 

        Grafo grafo = new Grafo(ciudades.size());

        for (String ciudad : ciudades) {
            grafo.agregarCiudad(ciudad);
        }

        for (String[] partes : conexiones) {
            String origen = partes[0];
            String destino = partes[1];
            double tiempoNormal = Double.parseDouble(partes[2]);
            grafo.agregarConexion(origen, destino, tiempoNormal);
        }

        return grafo;
    }

    /**
     * @param grafo
     * @param c1
     * @param c2
     * @param tiempoNormal
     * @param tiempoLluvia
     * @param tiempoNieve
     * @param tiempoTormenta
     * @param nombreArchivo
     * @return
     * @throws IOException
     */
    public static boolean crearConexion(Grafo grafo, String c1, String c2, String tiempoNormal, String tiempoLluvia, 
                                        String tiempoNieve, String tiempoTormenta, String nombreArchivo) throws IOException {
        if (!grafo.agregarConexion(c1, c2, Double.parseDouble(tiempoNormal))) {
            return false;
        }
        String nuevaLinea = c1 + " " + c2 + " " + tiempoNormal + " " + tiempoLluvia + " " + tiempoNieve + " " + tiempoTormenta;
        try (FileWriter fw = new FileWriter(nombreArchivo, true)) {
            fw.write("\n" + nuevaLinea);
        } 
        return true;
    }

    public static boolean cambiarClima(Grafo grafo, String c1, String c2, int clima, String nombreArchivo) throws IOException {
        double peso;
        Map<String, String[]> conexiones = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(" ");
                if (partes.length != 6) continue;
                String conexion = partes[0] + ", " + partes[1];
                String[] tiempos = new String[] { partes[2], partes[3], partes[4], partes[5] };
                conexiones.put(conexion, tiempos);
            }
        }

        String conexion = c1 + ", " + c2;
        if (!conexiones.containsKey(conexion)) {
            return false;
        }

        switch (clima) {
            case 1:
                peso = Double.parseDouble(conexiones.get(conexion)[0]);
                break;
            case 2:
                peso = Double.parseDouble(conexiones.get(conexion)[1]);
                break;
            case 3:
                peso = Double.parseDouble(conexiones.get(conexion)[2]);
                break;
            case 4:
                peso = Double.parseDouble(conexiones.get(conexion)[3]);
                break;
            default:
                return false;
        }
        
        grafo.agregarConexion(c1, c2, peso);
        return true;
    }

    public static boolean borrarConexion(String ciudad1, String ciudad2, String nombreArchivo) {
        File archivoOriginal = new File(nombreArchivo);
        File archivoTemporal = new File("temp_" + nombreArchivo);
        boolean conexionEliminada = false;

        try (
            BufferedReader br = new BufferedReader(new FileReader(archivoOriginal));
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemporal))
        ) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(" ");
                if (partes.length != 6) continue;

                String origen = partes[0];
                String destino = partes[1];

                if (origen.equals(ciudad1) && destino.equals(ciudad2)) {
                    conexionEliminada = true;
                    continue; // no escribir esta línea
                }

                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            return false;
        }

        // Reemplazar el archivo original por el temporal
        if (conexionEliminada) {
            if (!archivoOriginal.delete()) return false;
            if (!archivoTemporal.renameTo(archivoOriginal)) return false;
        } else {
            archivoTemporal.delete(); // No hubo nada que eliminar
        }

        return conexionEliminada;
}
}