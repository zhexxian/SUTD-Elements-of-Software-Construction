//package Week12;

import Week12.StripedMapSolution.Node;
import java.util.concurrent.atomic.AtomicInteger;


public class StripedMap {
	//synchronization policy: buckets[n] guarded by locks[n%N_LOCKS]
	private static final int N_LOCKS = 16;
	private final Node[] buckets;
	private final Object[] locks;
	
	public StripedMap (int numBuckets) {
		buckets = new Node[numBuckets];
		locks = new Object[N_LOCKS];
		
		for (int i = 0; i < N_LOCKS; i++) {
			locks[i] = new Object();
		}
	}
	
    public Object put(Object key, Object value) {
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]) {
            for (Node m = buckets[hash]; m != null; m = m.next)
                if (m.key.equals(key)) {
                    m.value = value;
                    return m.value;
                }
            buckets[hash] = new Node(key,value,buckets[hash]);
        }
        return null;
    }
	
	public Object get (Object key) {
		// get the item with the given key in the map
		// get method only needs to acquire one lock
		int hash = hash(key);         
		synchronized (locks[hash % N_LOCKS]) {             
			for (Node m = buckets[hash]; m != null; m = m.next) {
			    if (m.key.equals(key)){
			        return m.value;         
			    }
	    	}
    	}
		return null;		
	}
	
	private final int hash (Object key) {
		return Math.abs(key.hashCode() % buckets.length);
	}
	
	public void clear () {
		// remove all objects in the map
		// clear method needs to acquire all locks, but not necessarily simultaneously
		 for (int i = 0; i < buckets.length; i++) {             
		 	synchronized (locks[i % N_LOCKS]) {                 
		 		buckets[i] = null;             
		 	}         
		 }   
	}

	public int size () {
		// count the number of elements in the map
		// size method needs to acquire all locks, but not necessarily simultaneously
		AtomicInteger totalSize = new AtomicInteger(0);
		for (int i = 0; i < buckets.length; i++) {             
		 	synchronized (locks[i % N_LOCKS]) {                 
			 	for (Node m = buckets[hash]; m != null; m = m.next) {
				    totalSize.getAndIncrement();
		    	}         
		 	}         
		}  
		return totalSize.intValue();
	}

    class Node {
        Node next;
        Object key;
        Object value;
        Node(Object key, Object value, Node next) {
            this.next = next;
            this.key = key;
            this.value = value;
        }
    }
}

