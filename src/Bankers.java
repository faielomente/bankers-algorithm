import Jama.Matrix;

import java.util.*;

/**
 * Created by faielomente on 11/9/16.
 */
public class Bankers {
    int no_process;
    int no_resource;
    Matrix allocation = new Matrix(no_process, no_resource);
    Matrix maxAllocation = new Matrix(no_process, no_resource);
    Matrix needed = new Matrix(no_process, no_resource);
    double [] available = new double [no_resource];
    
    /**
     * Class constructor.
     * @param p
     * @param r
     * @param alloc
     * @param max
     * @param av 
     */
    Bankers(int p, int r, Matrix alloc, Matrix max, double [] av){
        this.no_process = p;
        this.no_resource = r;
        this.allocation = alloc;
        this.maxAllocation = max;
        this.needed = calcNeed(alloc, max);
        this.available = calcAvailable(this.allocation, av);
    }
    
    /**
     * Calculate the NEEDED MATRIX by subtracting the 
     * ALLOCATION from MAXALLOCATION.
     * @param alloc
     * @param maxAlloc
     * @return 
     */
    private Matrix calcNeed(Matrix alloc, Matrix maxAlloc){
        Matrix difference = new Matrix(alloc.getRowDimension(), alloc.getColumnDimension());
        
        difference = maxAlloc.minus(alloc);
        
        return difference;
    }
    
    /**
     * Generates the Safe Sequence.
     * @return 
     */
    public ArrayList generateSafeSequence(){
        Queue<Integer> processes = new LinkedList();
        ArrayList safeSequence = new ArrayList();
        double [] tmp = new double [no_resource];
        boolean stillSafe;
        
        //populate priority queue with the contents of the "needed" matrix
        for (int i = 0; i < this.no_process; i++){
            processes.add(i);
        }

        //Loops through all of the processes
        do {
//            System.out.println("PROCESSES: " + processes.toString());

            double [] current = getOneRow(this.needed, processes.peek());
            int i = 0;

            for (; i < this.no_resource; i++) {
                if (current[i] > this.available[i]){
                    processes.add(processes.remove());
                    break;
                }
            }

            if (i == current.length){
                Integer r = processes.remove();
                safeSequence.add(r);

                for (int j = 0; j < this.no_resource; j++) {
                    this.available[j] += this.allocation.get(r, j);
                }
            }
            
            //PRINT AVAILABLE RESOURCES
//            System.out.println("Available Resourcess: ");
//            for (int j = 0; j < this.no_resource; j++){
//                System.out.println(this.available[j]);
//            }

            //check if the sequence available is still safe
            stillSafe = isSafe(processes);
            if (stillSafe == false){
                System.out.println("DEADLOCK IS POSSIBLE.");
                safeSequence = new ArrayList();
                return safeSequence;
            }
            
        }while (processes.isEmpty() == false && stillSafe == true);

        return safeSequence;

    }
    
    /**
     * Get one row of a matrix.
     * @param m
     * @param process_no
     * @return 
     */
    private double [] getOneRow(Matrix m, int process_no){
        double [] p = new double [this.no_resource];

        for (int i = 0; i < this.no_resource; i++){
            p[i] = m.get(process_no, i);
        }

        //print
//        System.out.println("One allocation:");
//        for (int i = 0; i < this.no_resource; i++){
//            System.out.println(p[i]);
//        }

        return p;
    }
    
    /**
     * Checks if the DEADLOCK is possible with the processes in the queue.
     * @param processes
     * @return 
     */
    private boolean isSafe(Queue processes){
        Object[] p = processes.toArray();
        ArrayList cantAccommodate = new ArrayList();
        
        if(processes.isEmpty() == false){
            for(int i = 0; i < p.length; i++){
                for (int j = 0; j < this.no_resource; j++) {
                    if (this.needed.get((int)p[i],j) > this.available[j]){
                        cantAccommodate.add((int)p[i]);
                        break;
                    }
                }
            }
            if (cantAccommodate.size() == p.length)
                return false;
            else
                return true;
        }
        else
            return true;
    }
    
    /**
     * Updates the AVAILABLE RESOURCES to what is left of the RESOURCES.
     * The total AVAILABLE array will contain the values of what is left of 
     * the resources after it was used up by the processes.
     * @param allocation
     * @param av
     * @return 
     */
    private double [] calcAvailable(Matrix allocation, double [] av){
        int row = allocation.getRowDimension();
        int col = allocation.getColumnDimension();
        int [] totalAlloc = new int[col];

        for (int i = 0; i < col;i++){
            totalAlloc[i] = 0;
            for (int j = 0; j < row; j++)
                totalAlloc[i] += allocation.get(j, i);
        }

//        for (int i =0; i < col; i++) {
//            System.out.print("totalAlloc" + totalAlloc[i] + " ");
//        }

        for (int i = 0; i < col; i++)
            av[i] = av[i] - totalAlloc[i];

//        for (int i =0; i < col; i++) {
//            System.out.print("available " + av[i] + " ");
//        }

        return av;
    }

    public void processRequest(int p_id, double[] requested){

        for (int i = 0; i < this.no_resource; i++) {
            double temp = this.allocation.get(p_id, i) + requested[i];
            this.allocation.set(p_id, i, temp);
            this.available[i] -= requested[i];
        }
    }
}
