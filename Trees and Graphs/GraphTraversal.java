/* code for BFS & DFS */ /* beyond this code u can even do dfs iterative way,we can also find bfs and dfs for disconnectred graphs too but its not present in this code*/ 
import java.util.*;
public class GraphTraversal{
     static class Graph 
     {
        private int vertices; //no.of vertices
        private LinkedList<Integer>[] adjacencyList;
        private boolean isDirected;  // directed or undirected graph
          
         @SuppressWarnings("unchecked") 
        public Graph(int vertices,boolean isDirected)
        {
          this.vertices=vertices;
          this.isDirected=isDirected;

          //initializing adjacency list
          adjacencyList=new LinkedList[vertices];
          for(int i=0;i<vertices;i++){
            adjacencyList[i]=new LinkedList<>();
          }
        }

        public void addEdge(int src,int dest){
          //edge from srcto dest
          adjacencyList[src].add(dest);

          // in undirected graph,edge from dest to src
          if(!isDirected){
            adjacencyList[dest].add(src);
          }
        }

        public void bfs(int startVertex)
        {

         //mark all vertices as not visited.
         boolean[] visited=new boolean[vertices];

         //queue for BFS
         Queue<Integer> q=new LinkedList<>();

         visited[startVertex]=true;
         q.add(startVertex);

         // to just print bfs i used out statements (3 in bfs) 
         System.out.print("BFS starting from vertex " + startVertex + ": ");

         while(!q.isEmpty())
         {
          int currentVertex=q.poll();
          System.out.print(currentVertex + " ");

          // Get all adjacent vertices of the dequeued vertex
          // If an adjacent vertex has not been visited, mark it as visited and enqueue it
          for(Integer neighbor:adjacencyList[currentVertex]){
            if(!visited[neighbor]){
              visited[neighbor]=true;
              q.add(neighbor);
            }
          }
         }
         System.out.println();
        }

        //Utility function for DFS recursive traversal
        private void dfsUtil(int vertex,boolean[] visited){
          visited[vertex]=true;
          System.out.print(vertex + " ");

          for(Integer neighbor:adjacencyList[vertex]){
            if(!visited[neighbor]){
              dfsUtil(neighbor,visited);
            }
          }
        }

        public void dfs(int startVertex){
          //mark all vertices as not visited.
          boolean[] visited=new boolean[vertices];

          System.out.print("DFS starting from vertex " + startVertex + ": ");

          // Call the recursive helper function to print DFS traversal
          dfsUtil(startVertex, visited);

          System.out.println();
        }

         //Prints the graph representation
         public void printGraph() {
            System.out.println("Graph Adjacency List Representation:");
            for (int i = 0; i < vertices; i++) {
                System.out.print("Vertex " + i + " is connected to: ");
                for (Integer vertex : adjacencyList[i]) {
                    System.out.print(vertex + " ");
                }
                System.out.println();
            }
        }
     }
     public static void main(String[] args) {
        // Example usage with a directed graph
        System.out.println("--- Directed Graph Example ---");
        Graph directedGraph = new Graph(7, true);


        // Add edges
        directedGraph.addEdge(3, 2);
        directedGraph.addEdge(3, 1);
        directedGraph.addEdge(2, 4);
        directedGraph.addEdge(4, 5);
        directedGraph.addEdge(1, 5);
        directedGraph.addEdge(1, 6);
       

        // Print the graph
        directedGraph.printGraph();

        // Perform BFS and DFS
        directedGraph.bfs(3);
        directedGraph.dfs(3);

        // Example with an undirected graph
        System.out.println("\n--- Undirected Graph Example ---");
        Graph undirectedGraph = new Graph(7, false);
        
        // Add edges
        undirectedGraph.addEdge(0, 1);
        undirectedGraph.addEdge(0, 2);
        undirectedGraph.addEdge(1, 3);
        undirectedGraph.addEdge(2, 4);
        undirectedGraph.addEdge(3, 5);
        undirectedGraph.addEdge(4, 5);
        undirectedGraph.addEdge(5, 6);
        
        // Print the graph
        undirectedGraph.printGraph();
        
        // Perform BFS and DFS
        undirectedGraph.bfs(0);
        undirectedGraph.dfs(0);
     }

}