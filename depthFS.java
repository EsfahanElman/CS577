import java.util.*;
import java.util.stream.Collectors;

class DFSGraph {
    private Map<String, List<String>> adjList;
    private Set<String> visited; // Use a HashSet for quick lookups
    private List<String> traversal;

    public DFSGraph() {
        this.adjList = new LinkedHashMap<>();
        this.visited = new HashSet<>();
        this.traversal = new ArrayList<>();
    }

    // Method to add a node and its adjacent nodes
    public void addNode(String inputRow) {
        String[] inputList = inputRow.split(" "); // Split the row string by " "
        String node = inputList[0];
        List<String> adjNodes = new ArrayList<>(Arrays.asList(inputList).subList(1, inputList.length));
        adjList.put(node, adjNodes);
    }

   // DFS traversal using a stack
   public void DFS() {
    for (String node : adjList.keySet()) {
        if (!visited.contains(node)) {
            dfs(node);
        }
    }
}

private void dfs(String node) {
    Stack<String> stack = new Stack<>();
    stack.push(node);

    while (!stack.isEmpty()) {
        String currentNode = stack.pop();

        if (!visited.contains(currentNode)) {
            visited.add(currentNode);
            traversal.add(currentNode);

            // Get adjacent nodes of the current node
            List<String> adjNodes = adjList.getOrDefault(currentNode, Collections.emptyList());

            // Push adjacent nodes to the stack in reverse order to visit them in the correct order
            for (int i = adjNodes.size() - 1; i >= 0; i--) {
                String adjNode = adjNodes.get(i);
                if (!visited.contains(adjNode)) {
                    stack.push(adjNode);
                }
            }
        }
    }
}

    // Method to return the DFS traversal as a space-separated string
    public String printTraversal() {
        return traversal.stream().collect(Collectors.joining(" "));
    }
}


public class depthFS{
    public static void main(String[] args) {

        Scanner scnr = new Scanner(System.in);

        int numInstance = scnr.nextInt();
        scnr.nextLine(); // Consume new line character

        List<DFSGraph> insList = new ArrayList<>();
        // Process each graph instance
        for (int i = 0; i < numInstance; i++) {
            int numNodes = scnr.nextInt(); // Number of nodes in instance
            scnr.nextLine();

            DFSGraph g = new DFSGraph();

            // Read and add all nodes for the current instance
            for (int nodeIndex = 0; nodeIndex < numNodes; nodeIndex++) {
                String nodeInputLine = scnr.nextLine();
                g.addNode(nodeInputLine); // Add nodes to graph
            }
            insList.add(g);
           
        }

        for (DFSGraph instance : insList) {
            instance.DFS();
            System.out.println(instance.printTraversal());
        }
        

        scnr.close();
    }
}
