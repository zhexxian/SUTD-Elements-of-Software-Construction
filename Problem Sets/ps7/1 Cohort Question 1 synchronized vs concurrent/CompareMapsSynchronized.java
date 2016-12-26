/*Cohort Question 1:  
Given WorkerThread.java which performs operations on a given Map, 
design an experiment to show the performance difference between 
Collections.synchronizedMap and ConcurrrentHashMap. 
*/


import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;


public class CompareMapsSynchronized {
    public static Map<String,Integer> compareMaps = new HashMap<String,Integer>(){{
        put("Hello",1);
        put("Hi",2);
    }};

    // Adjust number of threads here
    public static int numberOfThreads = 1;

    public static void main(String args[]) {
        long startTime = System.nanoTime();
        ArrayList<WorkerThread> workerThreads = new ArrayList<WorkerThread>();
        for(int index=0; index<numberOfThreads; index++){
            workerThreads.add(new WorkerThread(Collections.synchronizedMap(compareMaps)));
        }
        for(WorkerThread workerThread:workerThreads){
            workerThread.start();
        }
        for(WorkerThread workerThread:workerThreads){
            try {
                workerThread.join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        long estimatedTime = System.nanoTime() - startTime;
        //Print out total time of n threads
        System.out.println("Total time of "+
            numberOfThreads+
            " threads on using synchronizedMap: "+
            estimatedTime);

    }

}

class WorkerThread extends Thread {    	      

    private Map<String, Integer> map = null;

    public WorkerThread(Map<String, Integer> map) {
          this.map = map;     
    }

    public void run() {                
          for (int i=0; i<500000; i++) {
                 // Return 2 integers between 1-1000000 inclusive
                 Integer newInteger1 = (int) Math.ceil(Math.random() * 1000000);
                 Integer newInteger2 = (int) Math.ceil(Math.random() * 1000000);                                           
                 // 1. Attempt to retrieve a random Integer element
                 map.get(String.valueOf(newInteger1));                       
                 // 2. Attempt to insert a random Integer element
                 map.put(String.valueOf(newInteger2), newInteger2);                
          }
    }
}