
import java.util.*;


public class flowFF {

    // A max value for capacity of flow
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args){

        Scanner scnr = new Scanner(System.in);

        int numInstance = scnr.nextInt(); // Number of instances

        for(int i = 0 ; i < numInstance ; i++){

            int numVertex = scnr.nextInt(); // Number of vertices
            int numEdge = scnr.nextInt(); // Number of edges

            int[][] capacity = new int[numVertex + 1 ][numVertex + 1]; // Capacity of flow
            
            // Read the edges and capacity
            for(int j = 0 ; j < numEdge ; j++){
                int u = scnr.nextInt();
                int v = scnr.nextInt();
                int c = scnr.nextInt();

                capacity[u][v] += c; // Add the capacity of flow in case of multiple edges
            }

            // now to calculate the max flow
            int maxFlow = fordFulkerson(capacity, 1, numVertex);
            System.out.println(maxFlow);   
        }
        scnr.close();
    }

    // Ford Fulkerson Algorithm
    private static int fordFulkerson(int[][] capacity, int source,int sink){

        int numOfVertex = capacity.length;
        int maxFlow = 0;

        // Find the residual graph
        int[][] residualGraph = new int[numOfVertex][numOfVertex];

        // Fill the residual graph with the capacity of flow
        for(int i = 0 ; i < numOfVertex ; i++){
            for(int j = 0 ; j < numOfVertex ; j++){
                residualGraph[i][j] = capacity[i][j];
            }
        }

        // An array to store the path from source to sink
        int[] path = new int[numOfVertex];

        // While there is a path from source to sink
        while(bfs(residualGraph,source,sink,path)){
            int pathFlow = INF;


            // Find the minimum flow in the path
            for(int v = sink ;v != source; v = path[v]){
                int u = path[v];
                pathFlow = Math.min(pathFlow,residualGraph[u][v]);
            }
            // pathflow is the minimum flow in the path

            // Update the residual graph to find the augmented path
            for(int v = sink ; v != source ; v = path[v]){
                int u = path[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }
        return maxFlow;

    }

    // Breadth First Search
    // This bfs function is referenced from geeksforgeeks website.
    // I took the idea of bfs from the website and implemented it in my code.
    private static boolean bfs(int[][] resGraph, int source, int sink, int[] path){

        int numOfVertex = resGraph.length;

        // Check if the vertex is visited
        boolean[] visitedNode = new boolean[numOfVertex];

        // Queue to store the vertex
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visitedNode[source] = true;
        path[source] = -1;

        // While there is a path from source to sink
        while(!queue.isEmpty()){
            int u = queue.poll();// Remove the vertex from the queue
            
            for(int v = 0 ; v < numOfVertex ; v++){
                if(!visitedNode[v] && resGraph[u][v] > 0){
                    queue.add(v);
                    path[v] = u;
                    visitedNode[v] = true;
                }
            }
        }

        // Return true if there is a path from source to sink
        return visitedNode[sink];
    }
    

    
}
