//package week11;

/*
Cohort Question 6: 
Complete TestThreadPoolSample.java so as to test that a thread pool indeed 
created a given number of threads which is less than or equal to the maximum 
thread pool size. 
*/

import java.util.concurrent.*;
import junit.framework.TestCase;

public class TestThreadPool extends TestCase {
    
    public void testPoolExpansion() throws InterruptedException {
        int max_pool_size = 10;
        ExecutorService exec = Executors.newFixedThreadPool(max_pool_size);
        int numThreads = 0;
        for(int i=0; i<20; i++){
            exec.execute(new Runnable () {
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            if (exec instanceof ThreadPoolExecutor) {
                numThreads = ((ThreadPoolExecutor) exec).getActiveCount();
            }
            assertTrue(numThreads<=10);
        }
        
        exec.shutdown();
    }
}