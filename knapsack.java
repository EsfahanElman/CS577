import java.util.Scanner;

public class knapsack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
   
        int k = scanner.nextInt();
      
        for (int i = 0; i < k; i++) {
            int numItem = scanner.nextInt(); // Number of elements in the list
            int capacity = scanner.nextInt();
            
            
            int[] weight = new int[numItem];
            int[] value = new int[numItem]; 

            // Read the list elements
            for (int l = 0; l < numItem; l++) {
                weight[l] = scanner.nextInt();
                value[l] = scanner.nextInt();
            }

            // initialize dp array
            int[] dp = new int[capacity + 1];

            // process each item

            for(int x = 0; x < numItem ; x++){

                int w = weight[x];
                int v = value[x];


                for(int y = capacity; y >= weight[x]; y--){
                    dp[y] = Math.max(dp[y], dp[y - w] + v);
                }
            }

            System.out.println(dp[capacity]);
            
          
        }

        scanner.close();

    }
}
