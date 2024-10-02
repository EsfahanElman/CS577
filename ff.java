import java.util.*;

public class ff {

    private String[] cache;
    private String[] request; // sequence of request
    private int idPage = 0; // int showed how many pages have already been requested
    private int cacheSize = 0; // size of oversized array cache
    private int pageFaults = 0; // the last result we want to output

    public ff(int numInCache, int numReq) {
        this.cache = new String[numInCache];
        this.request = new String[numReq];
    }

    public boolean isFault(String newRequest) {
        for (int i = 0; i < cache.length; i++) {
            if (newRequest.equals(cache[i])) {
                return false;
            }
        }
        return true;
    }

    public int findFurthestPagePosition(String page) {
        int furthestPosition = -1;
        int position = idPage;
        while (position < request.length) {
            if (request[position].equals(page)) {
                furthestPosition = position;
                break;
            }
            position++;
        }
        return furthestPosition;
    }

    public int findEvictedPagePosition() {
        int cacheID = 0; // find which page in cache should be evicted
        for (int i = 1; i < cacheSize; i++) {
            if (this.findFurthestPagePosition(cache[cacheID]) == -1) return cacheID; // if didn't find cacheID, evict cacheID this time
            if (this.findFurthestPagePosition(cache[i]) == -1) return i; // if didn't find i, evict i this time
            if (this.findFurthestPagePosition(cache[i]) > this.findFurthestPagePosition(cache[cacheID])) {
                cacheID = i;
            }
        }
        return cacheID;
    }

    public void getNewPageRequest() {
        // check whether cache is full, if not, when we encounter a fault, add it directly
        if (cacheSize < cache.length) {
            if (this.isFault(request[idPage])) {
                pageFaults++;
                cache[cacheSize] = request[idPage];
                cacheSize++;
            }
            return;
        }

        // in following case, cache is full, cacheSize = cache.length
        if (this.isFault(request[idPage])) {  // encounter a fault
            pageFaults++;
            cache[this.findEvictedPagePosition()] = request[idPage]; // replace evicted one with new requested one
        }
    }

    public int getNumFaults() {
        while (idPage < request.length) {
            this.getNewPageRequest();
            idPage++;
        }
        return pageFaults;
    }

    public void setRequestSequence(String[] requestSequence) {
        this.request = requestSequence;
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        int instances = scnr.nextInt();

        for (int instance = 0; instance < instances; instance++) {
            int cacheSize = scnr.nextInt();
            int numRequests = scnr.nextInt();
            String[] requests = new String[numRequests];
            
            for (int i = 0; i < numRequests; i++) {
                requests[i] = scnr.next(); // Use next() for reading each request
            }

            // Create an instance of the ff class
            ff pageReplacement = new ff(cacheSize, numRequests);
            // Set the request sequence
            pageReplacement.setRequestSequence(requests);
            // Get and print the number of page faults for the current instance
            System.out.println(pageReplacement.getNumFaults());
        }

        scnr.close();
    }
}
