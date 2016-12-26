package Week12;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class NonblockingCounter {
    private AtomicInteger value = new AtomicInteger(); 
    private int initialStamp = 0;

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int newStamp = initialStamp;
        int oldValue = 0; 
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<Integer>(oldValue,newStamp); 
        do{
          oldValue = value.get();
          //attemptStamp(oldValue, newStamp++);
        } while (!atomicStampedReference.compareAndSet(oldValue, oldValue + 1, newStamp, newStamp++)); 
        return oldValue + 1;
    }
}
