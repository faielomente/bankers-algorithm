import Jama.Matrix;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by faielomente on 11/9/16.
 */
public class Main {
    public static void main(String[] args){
        int no_process = 5;
        int no_resource = 3;
        DeadlockAvoidance da;
        double [][] alloc_vals = {{0,1,0}, {2,0,0}, {3,0,2}, {2,1,1}, {0,0,2}};
        double [][] max_vals = {{7,5,3}, {3,2,2}, {9,0,2}, {2,2,2}, {4,3,3}};

        Scanner sc = new Scanner(System.in);

        System.out.println("===================BANKER'S ALGORITHM===================");
//        System.out.println("Number of PROCESS/ES: ");
//        no_process = sc.nextInt();
//        System.out.println("Number of RESOURCE/S: ");
//        no_resource = sc.nextInt();
//
        Matrix allocation = new Matrix(no_process, no_resource);
        Matrix maxAllocation = new Matrix(no_process, no_resource);
        double [] available = {10, 5, 7};
//
//        //available
//        for (int i = 0; i < no_resource; i++){
//            System.out.println("Available for RESOURCE " + i + ": ");
//            available [i] = sc.nextInt();
//        }
//
//        //allocation
//        for (int row = 0; row < no_process; row++) {
//            for (int col = 0; col < no_resource; col++) {
//                System.out.println("PROCESS " + row + ": Allocate RESOURCE " + col);
//                double temp= sc.nextDouble();
//                allocation.set(row, col, temp);
//            }
//        }
//
//        //max allocation input
//        for (int row = 0; row < no_process; row++) {
//            for (int col = 0; col < no_resource; col++) {
//                System.out.println("Resource" + col + ": Max allocate for P" + row);
//                double temp= sc.nextInt();
//                maxAllocation.set(row, col, temp);
//            }
//        }


        allocation = new Matrix(alloc_vals);
        maxAllocation = new Matrix(max_vals);

        da = new DeadlockAvoidance(no_process,no_resource, allocation, maxAllocation, available);

        //print
        System.out.println("Allocation:");
        da.allocation.print(no_resource, 0);
        System.out.println("Max Allocation:");
        da.maxAllocation.print(no_resource, 0);
        System.out.println("Need Allocation:");
        da.needed.print(no_resource, 0);
        System.out.print("AVAILABLE: ");
        for (int i = 0; i < no_resource; i++){
            System.out.print(da.available[i] + " ");
        }
        System.out.println("\n");

        ArrayList safeSeq = da.generateSafeSequence();
        System.out.println("\nSAFE SEQUENCE: " + safeSeq.toString());
    }
}
