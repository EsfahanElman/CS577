import java.util.*;

public class FurthestInFuture {

    private Set<String> cache; // Stores pages in the cache
    private PriorityQueue<CacheEntry> minHeap; // Min-Heap to track pages based on future use
    private String[] requestSequence; // Request sequence
    private int cacheSize;
    private int pageFaults;

    private static class CacheEntry implements Comparable<CacheEntry> {
        String page;
        int nextUse;

        CacheEntry(String page, int nextUse) {
            this.page = page;
            this.nextUse = nextUse;
        }

        @Override
        public int compareTo(CacheEntry other) {
            return Integer.compare(this.nextUse, other.nextUse);
        }
    }

    public FurthestInFuture(int cacheSize, String[] requestSequence) {
        this.cache = new HashSet<>(cacheSize);
        this.minHeap = new PriorityQueue<>();
        this.requestSequence = requestSequence;
        this.cacheSize = cacheSize;
        this.pageFaults = 0;
    }

    public int getPageFaults() {
        for (int i = 0; i < requestSequence.length; i++) {
            String currentPage = requestSequence[i];

            // If the page is already in the cache, continue
            if (cache.contains(currentPage)) {
                // Update the next usage in the min-heap
                updateNextUse(currentPage, i);
                continue;
            }

            // Page fault occurs since the page is not in the cache
            pageFaults++;

            if (cache.size() == cacheSize) {
                // Evict a page if the cache is full
                evictPage();
            }

            // Add new page to cache
            cache.add(currentPage);
            // Add the page to the min-heap with its next use index
            updateNextUse(currentPage, i);
        }

        return pageFaults;
    }

    private void evictPage() {
        // Remove the page with the furthest next use from the cache and heap
        CacheEntry pageToEvict = minHeap.poll();
        if (pageToEvict != null) {
            cache.remove(pageToEvict.page);
        }
    }

    private void updateNextUse(String page, int currentIndex) {
        // Find the next use of the current page, return Integer.MAX_VALUE if it doesn't appear again
        int nextUseIndex = Integer.MAX_VALUE;
        for (int i = currentIndex + 1; i < requestSequence.length; i++) {
            if (requestSequence[i].equals(page)) {
                nextUseIndex = i;
                break;
            }
        }

        // Add to the min-heap with its next use index
        minHeap.add(new CacheEntry(page, nextUseIndex));
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        int instances = scnr.nextInt(); // Read number of instances

        for (int instance = 0; instance < instances; instance++) {
            int cacheSize = scnr.nextInt(); // Read cache size
            int numRequests = scnr.nextInt(); // Read number of requests
            String[] requests = new String[numRequests]; // Array to hold requests
            
            for (int i = 0; i < numRequests; i++) {
                requests[i] = scnr.next(); // Read each request
            }

            // Create an instance of FurthestInFuture
            FurthestInFuture pageReplacement = new FurthestInFuture(cacheSize, requests);
            // Get and print the number of page faults for the current instance
            System.out.println(pageReplacement.getPageFaults());
        }

        scnr.close();
    }
}
