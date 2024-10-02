import java.util.*;

public class ff {
    public static void main(String[] args){
        Scanner scnr = new Scanner(System.in);

        int instances = scnr.nextInt();

        for (int instance = 0; instance < instances; instance++) {
            int cacheSize = scnr.nextInt();
            int numRequests = scnr.nextInt();
            int[] requests = new int[numRequests];
            
            for (int i = 0; i < numRequests; i++) {
                requests[i] = scnr.nextInt();
            }

            System.out.println(getPageFaults(cacheSize, requests));
        }
        
        scnr.close();
    

    }

    public static int getPageFaults(int cacheSize, int[] req){

        if(cacheSize == 0 ) return req.length; 

        Set<Integer> cache = new HashSet<>();
  
        int pageFault = 0 ;

        for(int i = 0 ; i < req.length; i++){
            int rq = req[i];

            if(!cache.contains(rq)){
                if(cache.size() < cacheSize){
                    //cache is not full yet
                    cache.add(rq);
                }
                else{
                    // fint the page in the cache that is furthest in future
                    int pageToRem = -1; // set def
                    int maxDist = -1 ; // set def

                    for(int page : cache){
                        int dist = Integer.MAX_VALUE;
                        for(int x = i + 1 ; x < req.length; x++){
                            if(req[x] == page){
                                dist = x ;
                                break;
                            }
                        }
                        if(dist > maxDist){
                            maxDist = dist ;
                            pageToRem = page;
                        }
                    }
                    cache.remove(pageToRem);
                    cache.add(rq);
                }
                pageFault++;
            }
        }

        return pageFault;
    }
}