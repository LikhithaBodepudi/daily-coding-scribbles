/**
 * Kahn's Algorithm Implementation
The core of Kahn's algorithm is in the topologicalSortKahn() method. Here's how it works:

Calculate in-degrees:

Count how many incoming edges each vertex has
This tells us which vertices depend on others


Start with independent vertices:

Find all vertices with in-degree = 0 (no prerequisites)
Add them to a queue for processing


Process vertices in order:

Remove a vertex from the queue
Add it to the result list
"Remove" its outgoing edges by decreasing in-degrees of neighbors
When a neighbor's in-degree becomes 0, add it to the queue


Detect cycles:

If we can't visit all vertices, there must be a cycle
Return null to indicate topological sort is impossible
 */
import java.util.*;

public class KahnsTopologicalSort {
    private int vertices;
    private List<List<Integer>> adjacencyList;

    // Constructor
    public KahnsTopologicalSort(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    // Add edge to the graph
    public void addEdge(int src, int dest) {
        adjacencyList.get(src).add(dest);
    }

    // Kahn's topological sort algorithm
    public List<Integer> topologicalSortKahn() {
        int[] inDegree = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            for (int neighbor : adjacencyList.get(i)) {
                inDegree[neighbor]++;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < vertices; i++) {
            if (inDegree[i] == 0) {
                q.add(i);
            }
        }

        int visited = 0;
        List<Integer> result = new ArrayList<>();

        while (!q.isEmpty()) {
            int vertex = q.poll();
            result.add(vertex);
            visited++;

            for (int neighbor : adjacencyList.get(vertex)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    q.add(neighbor);
                }
            }
        }

        return visited == vertices ? result : null;
    }

    public void printGraph() {
        System.out.println("Graph Adjacency List Representation:");
        for (int i = 0; i < vertices; i++) {
            System.out.print("Vertex " + i + " -> ");
            for (Integer vertex : adjacencyList.get(i)) {
                System.out.print(vertex + " ");
            }
            System.out.println();
        }
    }

    // Detects if the graph has a cycle using DFS
    public boolean hasCycle() {
        boolean[] visited = new boolean[vertices];
        boolean[] recursionStack = new boolean[vertices];
        for (int i = 0; i < vertices; i++) {
            if (hasCycleUtil(i, visited, recursionStack)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCycleUtil(int vertex, boolean[] visited, boolean[] recursionStack) {
        if (recursionStack[vertex]) return true;
        if (visited[vertex]) return false;

        visited[vertex] = true;
        recursionStack[vertex] = true;

        for (Integer neighbor : adjacencyList.get(vertex)) {
            if (hasCycleUtil(neighbor, visited, recursionStack)) return true;
        }

        recursionStack[vertex] = false;
        return false;
    }


    public static void main(String[] args) {
        System.out.println("Example: Course prerequisites");
        KahnsTopologicalSort courseGraph = new KahnsTopologicalSort(6);

        courseGraph.addEdge(0, 1);
        courseGraph.addEdge(0, 2);
        courseGraph.addEdge(1, 3);
        courseGraph.addEdge(2, 3);
        courseGraph.addEdge(3, 4);
        courseGraph.addEdge(1, 5);

        courseGraph.printGraph();

        boolean hasCycle = courseGraph.hasCycle();
        System.out.println("Graph has cycle: " + hasCycle);

        if (!hasCycle) {
            System.out.println("\nTopological Sort (Kahn's algorithm):");
            List<Integer> topOrder = courseGraph.topologicalSortKahn();
            System.out.println(topOrder);

            System.out.println("\nCourse order to complete all requirements:");
            String[] courses = {
                "Intro to Programming",
                "Data Structures",
                "Algorithms",
                "Advanced Algorithms",
                "System Design",
                "Database Systems"
            };
            for (Integer course : topOrder) {
                System.out.println("Take: " + courses[course]);
            }
        } else {
            System.out.println("Cannot perform topological sort on a cyclic graph");
        }

        System.out.println("\n\nExample: Graph with a cycle");
        KahnsTopologicalSort cyclicGraph = new KahnsTopologicalSort(4);

        cyclicGraph.addEdge(0, 1);
        cyclicGraph.addEdge(1, 2);
        cyclicGraph.addEdge(2, 3);
        cyclicGraph.addEdge(3, 0); // Cycle

        cyclicGraph.printGraph();

        System.out.println("\nGraph has cycle: " + cyclicGraph.hasCycle());

        List<Integer> cyclicResult = cyclicGraph.topologicalSortKahn();
        if (cyclicResult != null) {
            System.out.println("Topological Sort: " + cyclicResult);
        } else {
            System.out.println("Topological sort not possible due to cycle in graph");
        }
    }
}
