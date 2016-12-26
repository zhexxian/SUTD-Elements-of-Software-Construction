/*Cohort Question 1:  
Given WorkerThread.java which performs operations on a given Map, 
design an experiment to show the performance difference between 
Collections.synchronizedMap and ConcurrrentHashMap. 
*/

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


public class CompareMapsConcurrent {
    public static void main(String args[]) {

        long startTime = System.nanoTime();
        ArrayList<ConcurrentThread> concurrentThreads = new ArrayList<ConcurrentThread>();
        for(int index=0; index<CompareMapsSynchronized.numberOfThreads; index++){
            concurrentThreads.add(new ConcurrentThread(new ConcurrentHashMap<String, Integer>(CompareMapsSynchronized.compareMaps)));
        }
        for(ConcurrentThread concurrentThread:concurrentThreads){
            concurrentThread.start();
        }
        for(ConcurrentThread concurrentThread:concurrentThreads){
            try {
                concurrentThread.join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        
        long estimatedTime = System.nanoTime() - startTime;

        //Print out total time of n threads
        System.out.println("Total time of "+
            numberOfThreads+
            " threads on using concurrentMap: "+
            estimatedTime);

    }

}

class ConcurrentThread extends Thread {           
    private ConcurrentHashMap<String, Integer> map = null;

    public ConcurrentThread(ConcurrentHashMap<String, Integer> map) {
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