import java.util.*;

public class TopologicalSort {
    static class Graph {
        int vertices;
        List<List<Integer>> adj;

        Graph(int v) {
            vertices = v;
            adj = new ArrayList<>();
            for (int i = 0; i < v; i++) {
                adj.add(new ArrayList<>());
            }
        }

        void addEdge(int u, int v) {
            adj.get(u).add(v); // Directed edge u → v
        }

        void topologicalSort() {
            boolean[] visited = new boolean[vertices];
            Stack<Integer> stack = new Stack<>();

            for (int i = 0; i < vertices; i++) {
                if (!visited[i]) {
                    dfs(i, visited, stack);
                }
            }

            // Print result
            System.out.print("Topological Sort: ");
            while (!stack.isEmpty()) {
                System.out.print(stack.pop() + " ");
            }
        }

        void dfs(int v, boolean[] visited, Stack<Integer> stack) {
            visited[v] = true;

            for (int neighbor : adj.get(v)) {
                if (!visited[neighbor]) {
                    dfs(neighbor, visited, stack);
                }
            }

            // Push current vertex after visiting all its neighbors
            stack.push(v);
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph(6);
        g.addEdge(5, 0);
        g.addEdge(5, 2);
        g.addEdge(4, 0);
        g.addEdge(4, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 1);

        g.topologicalSort();
    }
}
/**
 * What is Topological Sorting?
Topological sorting is an algorithm for ordering the vertices of a directed acyclic graph (DAG) in such a way that for every directed edge (u → v), vertex u comes before vertex v in the ordering.
In simpler terms, it's a way to arrange tasks or nodes in a sequence where all dependencies come before the dependent tasks.
Key Characteristics:

Only possible on Directed Acyclic Graphs (DAGs) - graphs with no cycles
There can be multiple valid topological orderings for the same graph
If a graph has a cycle, topological sorting is impossible.
 */