import java.util.*;

public class intervalScheduling {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int numInstance = sc.nextInt();

        // Process each Instance
        for(int i = 0 ; i < numInstance ; i++){
            int numJob = sc.nextInt();
            List<int[]> jobs = new ArrayList<>();

            for(int x = 0 ; x < numJob ; x++){
                int startTime = sc.nextInt();
                int endTime = sc.nextInt();
                jobs.add(new int[]{startTime, endTime});
            }

            // Sort jobs by end time
            Collections.sort(jobs, (a, b) -> Integer.compare(a[1], b[1]));


            int count = 0;
            int lastEndTime = 0;

            // Greedy algo

            for(int[] job: jobs){
                if(job[0] >= lastEndTime){
                    count++;
                    lastEndTime = job[1];
                }
            }

            System.out.println(count);

        }
        sc.close();
    }
}
