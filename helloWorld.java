import java.util.Scanner;

public class helloWorld{
    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        

        int n = scn.nextInt();
        scn.nextLine();


        String[] names = new String[n];

        for(int i = 0 ; i < n ; i++){
         names[i] = scn.nextLine();
        }


        for(int i = 0 ; i < n ; i++){
            System.out.println("Hello, " + names[i] + "!");

        }
        
        scn.close();
    }
}
