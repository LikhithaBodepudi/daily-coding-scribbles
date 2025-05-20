/**
 * Dijkstra's algorithm is a graph search algorithm 
 * that solves the single-source shortest path problem 
 * for a graph with non-negative edge weights. 
 */
import java.util.*;
public class DijkstraAlgorithm {

    // Class to represent a weighted edge in the graph
    static class Edge {
        int destination;
        int weight;
        public Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    // Class to represent a weighted graph
    static class Graph {
        int vertices;
        List<List<Edge>> adjacencyList;

        // Constructor to initialize the graph
        public Graph(int vertices) {
            this.vertices = vertices;
            adjacencyList = new ArrayList<>();
            for (int i = 0; i < vertices; i++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        // Adds a weighted edge to the graph
        public void addEdge(int source, int destination, int weight) {
            Edge edge = new Edge(destination, weight);
            adjacencyList.get(source).add(edge);
        }

        // Adds a bidirectional (undirected) weighted edge to the graph
        public void addBidirectionalEdge(int source, int destination, int weight) {
            addEdge(source, destination, weight);
            addEdge(destination, source, weight);
        }

        // Finds the shortest path from source to all other vertices using Dijkstra's algorithm
        // @return An array of shortest distances from source to all vertices
        public int[] dijkstra(int source) {
            int[] distance = new int[vertices];
            int[] previous = new int[vertices];
            Arrays.fill(distance, Integer.MAX_VALUE);
            Arrays.fill(previous, -1);
            distance[source] = 0;

            PriorityQueue<Integer> minHeap = new PriorityQueue<>((v1, v2) -> distance[v1] - distance[v2]);
            minHeap.add(source);

            Set<Integer> settled = new HashSet<>();

            while (!minHeap.isEmpty()) {
                int current = minHeap.poll();
                if (settled.contains(current)) continue;
                settled.add(current);

                for (Edge edge : adjacencyList.get(current)) {
                    int neighbor = edge.destination;
                    if (settled.contains(neighbor)) continue;
                    int newDistance = distance[current] + edge.weight;
                    if (newDistance < distance[neighbor]) {
                        distance[neighbor] = newDistance;
                        previous[neighbor] = current;
                        minHeap.add(neighbor);
                    }
                }
            }
            return distance;
        }

        // Reconstructs the shortest path from source to destination using the previous array
        public List<Integer> getPath(int source, int destination, int[] previous) {
            List<Integer> path = new ArrayList<>();
            if (previous[destination] == -1 && source != destination) {
                return path;
            }
            for (int vertex = destination; vertex != -1; vertex = previous[vertex]) {
                path.add(vertex);
            }
            Collections.reverse(path);
            return path;
        }

        // Finds the shortest path from source to a specific destination
        public Map.Entry<Integer, List<Integer>> shortestPath(int source, int destination) {
            int[] distance = new int[vertices];
            int[] previous = new int[vertices];
            Arrays.fill(distance, Integer.MAX_VALUE);
            Arrays.fill(previous, -1);
            distance[source] = 0;

            PriorityQueue<Integer> minHeap = new PriorityQueue<>((v1, v2) -> distance[v1] - distance[v2]);
            minHeap.add(source);

            boolean destinationReached = false;

            while (!minHeap.isEmpty() && !destinationReached) {
                int current = minHeap.poll();
                if (current == destination) {
                    destinationReached = true;
                    break;
                }

                if (distance[current] == Integer.MAX_VALUE) break;

                for (Edge edge : adjacencyList.get(current)) {
                    int neighbor = edge.destination;
                    int newDistance = distance[current] + edge.weight;
                    if (newDistance < distance[neighbor]) {
                        distance[neighbor] = newDistance;
                        previous[neighbor] = current;
                        minHeap.add(neighbor);
                    }
                }
            }

            List<Integer> path = getPath(source, destination, previous);
            return new AbstractMap.SimpleEntry<>(
                distance[destination] == Integer.MAX_VALUE ? -1 : distance[destination],
                path
            );
        }

        // Prints the shortest path from source to all vertices
        public void printShortestPaths(int source, String[] vertexLabels) {
            int[] distances = dijkstra(source);
            System.out.println("Shortest paths from " + (vertexLabels != null ? vertexLabels[source] : "vertex " + source) + ":");
            for (int i = 0; i < vertices; i++) {
                if (i != source) {
                    System.out.print("To " + (vertexLabels != null ? vertexLabels[i] : "vertex " + i) + ": ");
                    if (distances[i] == Integer.MAX_VALUE) {
                        System.out.println("No path exists");
                    } else {
                        System.out.println("Distance = " + distances[i]);
                    }
                }
            }
        }

        // Prints the graph representation
        public void printGraph(String[] vertexLabels) {
            System.out.println("Graph Representation:");
            for (int i = 0; i < vertices; i++) {
                System.out.print((vertexLabels != null ? vertexLabels[i] : "Vertex " + i) + " -> ");
                List<Edge> edges = adjacencyList.get(i);
                if (edges.isEmpty()) {
                    System.out.println("No outgoing edges");
                } else {
                    for (Edge edge : edges) {
                        System.out.print((vertexLabels != null ? vertexLabels[edge.destination] : edge.destination) +
                                "(" + edge.weight + ") ");
                    }
                    System.out.println();
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Example 1: Road Network");
        Graph roadNetwork = new Graph(6);
        roadNetwork.addBidirectionalEdge(0, 1, 7);
        roadNetwork.addBidirectionalEdge(0, 2, 9);
        roadNetwork.addBidirectionalEdge(0, 5, 14);
        roadNetwork.addBidirectionalEdge(1, 2, 10);
        roadNetwork.addBidirectionalEdge(1, 3, 15);
        roadNetwork.addBidirectionalEdge(2, 3, 11);
        roadNetwork.addBidirectionalEdge(2, 5, 2);
        roadNetwork.addBidirectionalEdge(3, 4, 6);
        roadNetwork.addBidirectionalEdge(4, 5, 9);

        String[] cityNames = {"A", "B", "C", "D", "E", "F"};
        roadNetwork.printGraph(cityNames);
        roadNetwork.printShortestPaths(0, cityNames);

        Map.Entry<Integer, List<Integer>> result = roadNetwork.shortestPath(0, 4);
        int shortestDistance = result.getKey();
        List<Integer> path = result.getValue();

        System.out.println("\nShortest path from A to E:");
        System.out.println("Distance: " + shortestDistance + " km");
        System.out.print("Path: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(cityNames[path.get(i)]);
            if (i < path.size() - 1) System.out.print(" -> ");
        }
        System.out.println("\n");

        System.out.println("Example 2: Network Routing");
        Graph network = new Graph(5);
        network.addEdge(0, 1, 5);
        network.addEdge(0, 2, 3);
        network.addEdge(1, 2, 2);
        network.addEdge(1, 3, 6);
        network.addEdge(2, 1, 1);
        network.addEdge(2, 3, 7);
        network.addEdge(2, 4, 4);
        network.addEdge(3, 4, 2);
        network.addEdge(4, 3, 4);

        String[] routerNames = {"Router A", "Router B", "Router C", "Router D", "Router E"};
        network.printGraph(routerNames);
        network.printShortestPaths(0, routerNames);

        result = network.shortestPath(0, 4);
        int minLatency = result.getKey();
        path = result.getValue();

        System.out.println("\nOptimal routing from Router A to Router E:");
        System.out.println("Minimum latency: " + minLatency + " ms");
        System.out.print("Path: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(routerNames[path.get(i)]);
            if (i < path.size() - 1) System.out.print(" -> ");
        }
        System.out.println();
    }
}
