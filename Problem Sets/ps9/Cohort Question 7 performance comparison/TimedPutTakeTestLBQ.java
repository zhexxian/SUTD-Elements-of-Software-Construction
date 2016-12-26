package week11;

/*
Cohort Question 7: 
Design and implement a test program to compare the performance of 
• BoundedBuffer 
• ArrayBlockingQueue  
• LinkedBlockingQueue  
*/

/*
Result:
Capacity: 1
Pairs: 1    Throughput: 9507 ns/item    Throughput: 13274 ns/item
Pairs: 2    Throughput: 14030 ns/item   Throughput: 10896 ns/item
Pairs: 4    Throughput: 10486 ns/item   Throughput: 12651 ns/item
Pairs: 8    Throughput: 11033 ns/item   Throughput: 11721 ns/item
Pairs: 16   Throughput: 9135 ns/item    Throughput: 8339 ns/item
Pairs: 32   Throughput: 8034 ns/item    Throughput: 9734 ns/item
Capacity: 10
Pairs: 1    Throughput: 2250 ns/item    Throughput: 1813 ns/item
Pairs: 2    Throughput: 1238 ns/item    Throughput: 974 ns/item
Pairs: 4    Throughput: 598 ns/item Throughput: 619 ns/item
Pairs: 8    Throughput: 578 ns/item Throughput: 589 ns/item
Pairs: 16   Throughput: 567 ns/item Throughput: 565 ns/item
Pairs: 32   Throughput: 592 ns/item Throughput: 617 ns/item
Capacity: 100
Pairs: 1    Throughput: 514 ns/item Throughput: 497 ns/item
Pairs: 2    Throughput: 389 ns/item Throughput: 376 ns/item
Pairs: 4    Throughput: 217 ns/item Throughput: 255 ns/item
Pairs: 8    Throughput: 225 ns/item Throughput: 235 ns/item
Pairs: 16   Throughput: 208 ns/item Throughput: 209 ns/item
Pairs: 32   Throughput: 183 ns/item Throughput: 180 ns/item
Capacity: 1000
Pairs: 1    Throughput: 603 ns/item Throughput: 221 ns/item
Pairs: 2    Throughput: 234 ns/item Throughput: 341 ns/item
Pairs: 4    Throughput: 212 ns/item Throughput: 166 ns/item
Pairs: 8    Throughput: 185 ns/item Throughput: 218 ns/item
Pairs: 16   Throughput: 194 ns/item Throughput: 175 ns/item
Pairs: 32   Throughput: 193 ns/item Throughput: 187 ns/item
*/

import java.util.concurrent.*;

import java.util.concurrent.atomic.*;

import junit.framework.TestCase;

class BarrierTimer implements Runnable {
    private boolean started;
    private long startTime, endTime;

    public synchronized void run() {
        long t = System.nanoTime();
        if (!started) {
            started = true;
            startTime = t;
        } else
            endTime = t;
    }

    public synchronized void clear() {
        started = false;
    }

    public synchronized long getTime() {
        return endTime - startTime;
    }
}

//Compare performance of LinkedBlockingQueue
public class TimedPutTakeTestLBQ extends PutTakeTest {
    private BarrierTimer timer = new BarrierTimer();

    public TimedPutTakeTestLBQ(int cap, int pairs, int trials) {
        super(cap, pairs, trials);
        barrier = new CyclicBarrier(nPairs * 2 + 1, timer);
    }

    public void test() {
        try {
            timer.clear();
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new PutTakeTest.Producer());
                pool.execute(new PutTakeTest.Consumer());
            }
            barrier.await();
            barrier.await();
            long nsPerItem = timer.getTime() / (nPairs * (long) nTrials);
            System.out.print("Throughput: " + nsPerItem + " ns/item");
            assert(putSum.get() == takeSum.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        int tpt = 100000; // trials per thread
        for (int cap = 1; cap <= 1000; cap *= 10) {
            System.out.println("Capacity: " + cap);
            for (int pairs = 1; pairs <= 32; pairs *= 2) {
                TimedPutTakeTestLBQ t = new TimedPutTakeTestLBQ(cap, pairs, tpt);
                System.out.print("Pairs: " + pairs + "\t");
                t.test();
                System.out.print("\t");
                Thread.sleep(1000);
                t.test();
                System.out.println();
                Thread.sleep(1000);
            }
        }
        PutTakeTest.pool.shutdown();
    }
}
   
class PutTakeTest {
    protected static final ExecutorService pool = Executors.newCachedThreadPool();
    protected CyclicBarrier barrier;
    protected final LinkedBlockingQueue<Integer> bb;
    protected final int nTrials, nPairs;
    protected final AtomicInteger putSum = new AtomicInteger(0); //for testing
    protected final AtomicInteger takeSum = new AtomicInteger(0);//for testing

    public static void main(String[] args) throws Exception {
        new PutTakeTest(10, 10, 100000).test(); // sample parameters
        pool.shutdown();
    }
    
    public PutTakeTest(int capacity, int npairs, int ntrials) {
        this.bb = new LinkedBlockingQueue<Integer>(capacity);
        this.nTrials = ntrials;
        this.nPairs = npairs;
        this.barrier = new CyclicBarrier(npairs * 2 + 1);
    }

    void test() {
        try {
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
            }
            barrier.await(); // wait for all threads to be ready
            barrier.await(); // wait for all threads to finish
            assert(putSum.get() == takeSum.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }

    class Producer implements Runnable {
        public void run() {
            try {
                int seed = (this.hashCode() ^ (int) System.nanoTime());
                int sum = 0;
                barrier.await();
                for (int i = nTrials; i > 0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Consumer implements Runnable {
        public void run() {
            try {
                barrier.await();
                int sum = 0;
                for (int i = nTrials; i > 0; --i) {
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}