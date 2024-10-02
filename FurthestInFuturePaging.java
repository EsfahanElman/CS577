// import java.util.*;

// public class FurthestInFuturePaging {
//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);
//         int instances = scanner.nextInt();

//         for (int i = 0; i < instances; i++) {
//             int cacheSize = scanner.nextInt();
//             int numRequests = scanner.nextInt();
//             int[] requests = new int[numRequests];
//             for (int j = 0; j < numRequests; j++) {
//                 requests[j] = scanner.nextInt();
//             }
//             System.out.println(countPageFaults(cacheSize, requests));
//         }
//         scanner.close();
//     }

//     private static int countPageFaults(int cacheSize, int[] requests) {
//         // Initialize cache and page fault counter
//         Set<Integer> cache = new HashSet<>();
//         int pageFaults = 0;

//         // Step 1: Precompute the next occurrence for each page using an ArrayList
//         int maxPage = Arrays.stream(requests).max().orElse(0);
//         List<Integer> nextOccurrence = new ArrayList<>(Collections.nCopies(maxPage + 1, -1));

//         // Fill the next occurrence array
//         for (int i = requests.length - 1; i >= 0; i--) {
//             int page = requests[i];
//             nextOccurrence.set(page, i);
//         }

//         // Step 2: Process each request
//         for (int i = 0; i < requests.length; i++) {
//             int request = requests[i];

//             // If the page is not in the cache, we have a page fault
//             if (!cache.contains(request)) {
//                 pageFaults++;

//                 // If cache is not full, simply add the new page
//                 if (cache.size() < cacheSize) {
//                     cache.add(request);
//                 } else {
//                     // Cache is full, find the page to evict
//                     int furthestPage = -1;
//                     int furthestIndex = -1;

//                     // Check each page in the cache for its next occurrence
//                     for (int page : cache) {
//                         // Check the next occurrence for the current page
//                         int nextOccurrenceIndex = findNextOccurrence(requests, i, page);
//                         if (nextOccurrenceIndex > furthestIndex) {
//                             furthestIndex = nextOccurrenceIndex;
//                             furthestPage = page;
//                         }
//                     }

//                     // Remove the furthest page and add the new page
//                     cache.remove(furthestPage);
//                     cache.add(request);
//                 }
//             }
//         }

//         return pageFaults;
//     }

//     // Helper method to find the next occurrence of a page
//     private static int findNextOccurrence(int[] requests, int currentIndex, int page) {
//         for (int i = currentIndex + 1; i < requests.length; i++) {
//             if (requests[i] == page) {
//                 return i; // Return the index of the next occurrence
//             }
//         }
//         return Integer.MAX_VALUE; // If it doesn't occur again
//     }
// }


import java.util.*;

public class FurthestInFuturePaging {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int instances = scanner.nextInt();

        for (int i = 0; i < instances; i++) {
            int cacheSize = scanner.nextInt();
            int numRequests = scanner.nextInt();
            int[] requests = new int[numRequests];
            for (int j = 0; j < numRequests; j++) {
                requests[j] = scanner.nextInt();
            }
            System.out.println(countPageFaults(cacheSize, requests));
        }
        scanner.close();
    }

    private static int countPageFaults(int cacheSize, int[] requests) {
        // Initialize cache and page fault counter
        Set<Integer> cache = new HashSet<>();
        int pageFaults = 0;

        // Step 1: Create an ArrayList to track indices of each page's occurrences
        Map<Integer, Queue<Integer>> occurrenceIndices = new HashMap<>();

        // Fill the occurrence indices map
        for (int i = 0; i < requests.length; i++) {
            occurrenceIndices.computeIfAbsent(requests[i], k -> new LinkedList<>()).add(i);
        }

        // Step 2: Process each request
        for (int i = 0; i < requests.length; i++) {
            int request = requests[i];

            // If the page is not in the cache, we have a page fault
            if (!cache.contains(request)) {
                pageFaults++;

                // If cache is not full, simply add the new page
                if (cache.size() < cacheSize) {
                    cache.add(request);
                } else {
                    // Cache is full, find the page to evict
                    int furthestPage = -1;
                    int furthestIndex = -1;

                    // Check each page in the cache for its next occurrence
                    for (int page : cache) {
                        Queue<Integer> indices = occurrenceIndices.get(page);
                        // Remove indices that have already occurred
                        while (!indices.isEmpty() && indices.peek() <= i) {
                            indices.poll(); // Remove indices that are not in the future
                        }
                        
                        // Check if there are any future occurrences
                        if (!indices.isEmpty()) {
                            int nextIndex = indices.peek(); // Look at the next occurrence
                            if (nextIndex > furthestIndex) {
                                furthestIndex = nextIndex;
                                furthestPage = page; // Keep track of the page to evict
                            }
                        } else {
                            // If no future occurrences, we can evict this page
                            furthestPage = page;
                            break; // No need to check other pages, we can evict this one
                        }
                    }

                    // Remove the furthest page and add the new page
                    cache.remove(furthestPage);
                    cache.add(request);
                }
            } else {
                // If the request is already in the cache, just continue
                Queue<Integer> indices = occurrenceIndices.get(request);
                // Remove the current index since it's no longer in the future
                if (!indices.isEmpty() && indices.peek() == i) {
                    indices.poll();
                }
            }
        }

        return pageFaults;
    }
}
