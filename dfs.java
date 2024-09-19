import java.util.*;

public class dfs{
    // Iterative DFS using a stack
    public static List<String> depthFirstSearch(String start, Map<String, List<String>> graph) {
        Set<String> visited = new HashSet<>();  // To track visited nodes
        Stack<String> stack = new Stack<>();    // Stack for DFS
        List<String> result = new ArrayList<>(); // To store DFS order
        
        stack.push(start);  // Start DFS from the first node

        while (!stack.isEmpty()) {
            String node = stack.pop(); // Get the top node from the stack
            
            if (!visited.contains(node)) {
                visited.add(node);    // Mark the node as visited
                result.add(node);     // Add node to result list

                // Get the neighbors of the node
                List<String> neighbors = graph.get(node);
                
                // Push neighbors onto the stack in reverse input order
                // so that we visit them in the original input order
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    if (!visited.contains(neighbors.get(i))) {
                        stack.push(neighbors.get(i));
                    }
                }
            }
        }
        
        return result; // Return the list of visited nodes in DFS order
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();  // Number of instances
        scanner.nextLine(); // Move to the next line

        for (int i = 0; i < t; i++) {
            int n = scanner.nextInt();  // Number of nodes in the graph
            scanner.nextLine(); // Move to the next line
            
            Map<String, List<String>> graph = new LinkedHashMap<>();  // To store the graph in input order

            // Reading nodes and their adjacency list
            for (int j = 0; j < n; j++) {
                String[] line = scanner.nextLine().split(" ");
                String node = line[0];
                List<String> neighbors = new ArrayList<>();
                
                // Add neighbors in the input order
                for (int k = 1; k < line.length; k++) {
                    neighbors.add(line[k]);
                }
                
                graph.put(node, neighbors); // Add node and its neighbors to the graph
            }

            // Start DFS from the first node in input order
            String startNode = graph.keySet().iterator().next();
            List<String> result = depthFirstSearch(startNode, graph);

            // Output the result
            System.out.println(String.join(" ", result));
        }

        scanner.close();
    }
}
