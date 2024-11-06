
import java.util.*;

public class weightedInvSc {
    
    static class Job {
        int start,end,weight;

        public Job(int start, int end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }   
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        
        //StringBuilder result = new StringBuilder();
        for (int instance = 0; instance < t; instance++) {
            int n = scanner.nextInt();
            Job[] jobs = new Job[n];
            for (int i = 0; i < n; i++) {
                int start = scanner.nextInt();
                int end = scanner.nextInt();
                int weight = scanner.nextInt();
                jobs[i] = new Job(start, end, weight);
            }
           Arrays.sort(jobs, Comparator.comparingInt(job -> job.end));
           System.out.println(findMaxWeight(jobs));
           //result.append(findMaxWeight(jobs)).append("\n");
        }


        scanner.close();

        //System.out.println(result);
    }


    private static long findMaxWeight(Job[] jobs) {
        int n = jobs.length;
        long[] dp = new long[n];
        dp[0] = (long) jobs[0].weight;
        
        for(int i = 1 ; i < n ; i++){
            long including = (long) jobs[i].weight;
            int latestNonConflict = findLatestNonConflict(jobs, i);
            if(latestNonConflict != -1){
                including += dp[latestNonConflict];
            }
            dp[i] = Math.max(including, dp[i-1]);
        }
        return dp[n-1];
    }

    private static int findLatestNonConflict(Job[] jobs, int i) {
        int low = 0, high = i-1;

        while(low <= high){
            int mid = low + (high - low)/2;
            if(jobs[mid].end <= jobs[i].start){
                if(jobs[mid+1].end <= jobs[i].start){
                    low = mid + 1;
                }else{
                    return mid;
                }
            }else{
                high = mid - 1;
            }
        }
        return -1;
    }
}
