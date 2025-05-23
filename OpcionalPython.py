import networkx as nx

def leer_grafo(nombre_archivo, clima='normal'):
    clima_idx = {'normal': 2, 'lluvia': 3, 'nieve': 4, 'tormenta': 5}

    idx = clima_idx.get(clima.lower(), 2)
    grafo = nx.DiGraph()

    with open(nombre_archivo, 'r') as datos:
        for linea in datos:
            partes = linea.strip().split()
            if len(partes) != 6:
                continue
            origen = partes[0]
            destino = partes[1]
            peso = float(partes[idx])
            grafo.add_edge(origen, destino, weight=peso)

    return grafo

def floyd_mas_corto(grafo):
    return dict(nx.floyd_warshall(grafo, weight='weight'))

def camino_mas_corto(grafo, origen, destino):
    distancias = nx.floyd_warshall(grafo, weight='weight')
    if origen not in grafo.nodes() or destino not in grafo.nodes():
        return None, float('inf')
    if distancias[origen][destino] == float('inf'):
        return None, float('inf')
    camino = nx.reconstruct_path(origen, destino, 
                                nx.floyd_warshall_predecessor_and_distance(grafo, weight='weight')[0])
    return camino, distancias[origen][destino]

def centro_del_grafo(grafo):
    dist = floyd_mas_corto(grafo)
    excentricidades = {}

    for nodo in grafo.nodes():
        max_dist = max([dist[nodo][otro] for otro in grafo.nodes() if dist[nodo][otro] != float('inf')])
        excentricidades[nodo] = max_dist

    return min(excentricidades, key=excentricidades.get)

def menu_Principal():
    clima = 'normal'
    grafo = leer_grafo('logistica.txt', clima)
    print(f"Grafo cargado con clima: {clima}")

    while True:
        print("\n--- Menú ---")
        print("1. Ver ruta más corta entre dos ciudades")
        print("2. Ver ciudad centro del grafo")
        print("3. Cambiar clima")
        print("4. Salir")
        opcion = input("Seleccione una opción: ")

        if opcion == '1':
            origen = input("Ciudad origen: ")
            destino = input("Ciudad destino: ")
            camino, peso = camino_mas_corto(grafo, origen, destino)
            if camino:
                print("Ruta más corta:", ' -> '.join(camino))
                print("Tiempo total:", peso)
            else:
                print("No hay ruta entre las ciudades")

        elif opcion == '2':
            centro = centro_del_grafo(grafo)
            print("El centro del grafo es:", centro)

        elif opcion == '3':
            clima = input("Nuevo clima (normal, lluvia, nieve, tormenta): ")
            grafo = leer_grafo('logistica.txt', clima)
            print("Se actualizó el clima y el grafo")

        elif opcion == '4':
            print("Saliendo")
            break

        else:
            print("Por favor ingresa una opcion valida")
