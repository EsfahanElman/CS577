import java.util.*;

public class LineIntersection {
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
 

        // Read the number of test cases
        int t = scanner.nextInt();

        for (int instance = 0; instance < t; instance++) {
            // Read the number of pairs
            int n = scanner.nextInt();

            // Read the positions on the top line (qi)
            int[] q = new int[n];
            for (int i = 0; i < n; i++) {
                q[i] = scanner.nextInt();
            }

            // Read the positions on the bottom line (pi)
            int[] p = new int[n];
            for (int i = 0; i < n; i++) {
                p[i] = scanner.nextInt();
            }

           // Step 1: Sort P and rearrange Q accordingly
           int[] sortedP = p.clone();
           Arrays.sort(sortedP);

           Map<Integer, Integer> indexMap = new HashMap<>();
           for (int i = 0; i < n; i++) {
               indexMap.put(p[i], i);
           }

           int[] rearrangedQ = new int[n];
           for (int i = 0; i < n; i++) {
               rearrangedQ[i] = q[indexMap.get(sortedP[i])];
           }

           // Step 2: Count inversions in rearrangedQ
           long inversionCount = countInversions(rearrangedQ);

           // Output the result for this test case
           System.out.println(inversionCount);
          // System.out.println("inv count: " + inversionCount);
        }

        scanner.close();
      
    }




    public static  long countInversions(int[] arr){
        if(arr == null || arr.length == 0) {
            return 0;
        }
        int[] temp = new int [arr.length];
        return mergeSortAndCount(arr, temp, 0, arr.length - 1);
    
    }
    
    // Merge sort and count inversions
    public static long mergeSortAndCount(int[] arr, int[] temp, int left, int right) {
        int mid = 0;
        long invCount = 0;
        if(left < right){
             mid = (left + right) / 2;
    
            // Recursively count inversions in the left and right halves
            invCount += mergeSortAndCount(arr, temp, left, mid);
            invCount += mergeSortAndCount(arr, temp, mid + 1, right);
    
            invCount +=  mergeAndCount(arr, temp, left, mid , right);
        }
        return invCount;
    }
    
    public static long sort(int[] arr, int arr_size){
        int[] temp = new int[arr_size];
    
        return mergeSortAndCount(arr, temp, arr_size, arr_size);
    
    }
    
    // Merge two sorted halves and count the inversions
    public static long mergeAndCount(int[] arr, int[] temp, int left, int mid, int right) {
        int i = left;   // Starting index for left subarray
        int j = mid + 1; // Starting index for right subarray
        int k = left;   // Starting index to be sorted
        long invCount = 0;
    
        // Merge the two halves while counting inversions
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) { // If no inversion
                temp[k++] = arr[i++];
            } else { // arr[i] > arr[j] means all elements from i to mid are greater than arr[j]
                temp[k++] = arr[j++];
                
                // All remaining elements in the left subarray form an inversion with arr[j]
                invCount += (mid + 1 - i); // Count the number of inversions
            }
        }
    
        // Copy the remaining elements of left subarray, if any
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
    
        // Copy the remaining elements of right subarray, if any
        while (j <= right) {
            temp[k++] = arr[j++];
        }
    
        // Copy the sorted subarray into original array
        for (i = left; i <= right; i++) {
            arr[i] = temp[i];
        }
    
        return invCount;
    }
}
