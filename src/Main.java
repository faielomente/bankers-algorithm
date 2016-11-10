import Jama.Matrix;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by faielomente on 11/9/16.
 */
public class Main {
    public static void main(String[] args){
        int no_process;
        int no_resource;
        int choice;
        double[] available;
        Matrix allocation;
        Matrix maxAllocation;
        Bankers bankers;
        
        Scanner sc = new Scanner(System.in);

        System.out.println("===================BANKER'S ALGORITHM===================");
        do {
            System.out.println("\n\nChoose:\n1. Deadlock PREVENTION\n2. Deadlock AVOIDANCE\n3. Close");
            choice = sc.nextInt();
        
            if(choice == 1){
                System.out.println("===================Deadlock Prevention===================");
                System.out.println("NOTE: In Prevention simulation, TIME is your RESOURCE 0.");
                System.out.println("Number of PROCESS/ES: ");
                no_process = sc.nextInt();
                no_resource = 1;
                
                available = getAvailable(sc, no_resource);
                allocation = getAllocation(sc, no_process, no_resource);
                maxAllocation = getMaxAllocation(sc, no_process, no_resource);
                
                
                
                bankers = new Bankers(no_process, no_resource, allocation, maxAllocation, available);
                
                System.out.println("Allocation:");
                bankers.allocation.print(no_resource, 0);
                System.out.println("Maximum Demand:");
                bankers.maxAllocation.print(no_resource, 0);
                System.out.println("Need Resources:");
                bankers.needed.print(no_resource, 0);
                System.out.print("AVAILABLE: ");
                for (int i = 0; i < no_resource; i++){
                    System.out.print(bankers.available[i] + " ");
                }
                System.out.println("\n");

                ArrayList safeSeq = bankers.generateSafeSequence();
                System.out.println("\nSAFE SEQUENCE: " + safeSeq.toString());
                
            }
            else if(choice == 2){
                System.out.println("===================Deadlock Avoidance===================");
                System.out.println("Number of PROCESS/ES: ");
                no_process = sc.nextInt();
                System.out.println("Number of RESOURCE/S: ");
                no_resource = sc.nextInt();

                available = getAvailable(sc, no_resource);
                allocation = getAllocation(sc, no_process, no_resource);
                maxAllocation = getMaxAllocation(sc, no_process, no_resource);

                bankers = new Bankers(no_process, no_resource, allocation, maxAllocation, available);

                //print
                System.out.println("Allocation:");
                bankers.allocation.print(no_resource, 0);
                System.out.println("Max Allocation:");
                bankers.maxAllocation.print(no_resource, 0);
                System.out.println("Need Allocation:");
                bankers.needed.print(no_resource, 0);
                System.out.print("AVAILABLE: ");
                for (int i = 0; i < no_resource; i++){
                    System.out.print(bankers.available[i] + " ");
                }
                System.out.println("\n");

                ArrayList safeSeq = bankers.generateSafeSequence();
                System.out.println("\nSAFE SEQUENCE: " + safeSeq.toString());
            }
            else if (choice == 3){
                break;
            }
        }while (choice < 3);
    }
    
    public static double[] getAvailable(Scanner sc, int no_resource){
        double [] available = new double [no_resource];
        //test inputs for AVOIDANCE
//        double [] available = {10, 5, 7};
        //TEST INPUTS FOR PREVENTION
//        double [] available = {10};
        for (int i = 0; i < no_resource; i++){
            System.out.println("Available for RESOURCE " + i + ": ");
            available[i] = sc.nextDouble();
        }
        
        return available;
    }
    
    public static Matrix getAllocation(Scanner sc, int no_process, int no_resource){
        Matrix allocation = new Matrix(no_process, no_resource);
        //allocation
        //TEST INPUTS FOR AVOIDANCE
//        double [][] alloc_vals = {{0,1,0}, {2,0,0}, {3,0,2}, {2,1,1}, {0,0,2}};
        //TEST INPUTS FOR PREVENTION
//        double [][] alloc_vals = {{0}, {2}, {3}, {2}, {0}};     
//        allocation = new Matrix(alloc_vals);
        
        for (int row = 0; row < no_process; row++) {
            for (int col = 0; col < no_resource; col++) {
                System.out.println("PROCESS " + row + ": Allocate RESOURCE " + col);
                double temp= sc.nextDouble();
                allocation.set(row, col, temp);
            }
        }
        
        return allocation;
    }
    
    public static Matrix getMaxAllocation(Scanner sc, int no_process, int no_resource){
        Matrix maxAllocation = new Matrix(no_process, no_resource);
        
        //TEST INPUTS FOR AVOIDANCE
//        double [][] max_vals = {{7,5,3}, {3,2,2}, {9,0,2}, {2,2,2}, {4,3,3}};
        //TEST INPUTS FOR PREVENTION
//        double [][] max_vals = {{7}, {3}, {9}, {2}, {4}};
//        maxAllocation = new Matrix(max_vals);
        
        //max allocation input
        for (int row = 0; row < no_process; row++) {
            for (int col = 0; col < no_resource; col++) {
                System.out.println("RESOURCE" + col + ": Max allocation for P" + row);
                double temp= sc.nextInt();
                maxAllocation.set(row, col, temp);
            }
        }
        
        return maxAllocation;
    }
}
