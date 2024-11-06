import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;



public class invCount {

    // Main method to handle input and output
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
       //try {
        //Scanner scanner = new Scanner(new File("/Users/joeblack/Desktop/CS_577/CS577/large_test_case.txt"));
        // Read number of test cases
        int k = scanner.nextInt();
        //test1();
        // Process each test case
        for (int i = 0; i < k; i++) {
            int j = scanner.nextInt(); // Number of elements in the list
            int[] arr = new int[j];

            // Read the list elements
            for (int l = 0; l < j; l++) {
                arr[l] = scanner.nextInt();
            }

            // Calculate the number of inversions
            long inversions = countInversions(arr);
            
            // Output the result
            System.out.println(inversions);
        }

        scanner.close();
       // } catch (FileNotFoundException e) {
       //     System.out.println("Test case file not found.");
        //    e.printStackTrace();
       // }
    }


     public static void test1() {
        int k = 1;  // One test case
        int j = 100000;  // Maximum number of elements

        try (FileWriter writer = new FileWriter("large_test_case.txt")) {
            // Write the number of test cases
            writer.write(k + "\n");

            // Write the number of elements in the array
            writer.write(j + "\n");

            // Generate the array in descending order and write it to the file
            for (int i = j; i >= 1; i--) {
                writer.write(i + " ");
            }

            writer.write("\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the test case.");
            e.printStackTrace();
        }

        System.out.println("Test case generated successfully!");
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