//Test run:
//Time take for CAS counter is: 9 ms
//Time take for lock counter is: 10 ms

import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;

public class CasCounterTest {
    public static int NTHREAD = 100;
    //public void testPerformanceCASCounter () {
    public static void main(String[] args) {
        //Performance test for CAS based counter
        long startTime = System.nanoTime();
        ArrayList<AtomicCounterThread> threads = new ArrayList<AtomicCounterThread>();
        for(int i=0; i<NTHREAD; i++){
            threads.add(new AtomicCounterThread());
        }
        for(AtomicCounterThread thread:threads){
            thread.start();
        }
        for(AtomicCounterThread thread:threads){
            try {
                thread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        long endTime = System.nanoTime();
        //divide by 1000000 to get milliseconds.
        long duration = (endTime - startTime) / 1000000;  
        System.out.println("Time take for CAS counter is: "+duration+" ms");

        //Performance test for lock based counter
        startTime = System.nanoTime();
        ArrayList<LockCounterThread> lockThreads = new ArrayList<LockCounterThread>();
        for(int i=0; i<NTHREAD; i++){
            lockThreads.add(new LockCounterThread());
        }
        for(LockCounterThread thread:lockThreads){
            thread.start();
        }
        for(LockCounterThread thread:lockThreads){
            try {
                thread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        endTime = System.nanoTime();
        //divide by 1000000 to get milliseconds.
        duration = (endTime - startTime) / 1000000;  
        System.out.println("Time take for lock counter is: "+duration+" ms");
    }
}

//CAS based counter
//@ThreadSafe
class AtomicCounter {
    private AtomicInteger value = new AtomicInteger(0);

    public int getValue() {
        return value.get();
    }

    public int increment() {
        return value.addAndGet(1);
    }
}

class AtomicCounterThread extends Thread {
    private AtomicCounter counter;
    public AtomicCounterThread(AtomicCounter counter){
        this.counter = counter;
    }
    public AtomicCounterThread() {
        counter = new AtomicCounter();
    }
    public void run(){
        counter.increment();
    }
}

//Lock based counter
//@ThreadSafe
class LockCounter {

    private int value = 0; //@GuardedBy("this") 

    public LockCounter(int value) {
        this.value = value;
    }

    public synchronized int getValue() {
        return value;
    }

    public synchronized int increment() {
        return value++;
    }
}

class LockCounterThread extends Thread {
    private LockCounter counter ;
    public LockCounterThread(LockCounter counter){
        this.counter = counter;
    }
    public LockCounterThread() {
        counter = new LockCounter(0);
    }
    public void run(){
        counter.increment();
    }
}