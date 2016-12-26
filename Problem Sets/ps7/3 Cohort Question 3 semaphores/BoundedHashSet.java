/*(10 Points) Cohort Question 3: 
Use semaphore to implement a BoundedHashSet. The class skeleton is given below. 
Hint: you can initialize the semaphore to the desired maximum size of the set. */

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSet<T> {	
	private final Set<T> set;
	private final Semaphore semaphore;

	
	public BoundedHashSet (int bound) {
		this.set = Collections.synchronizedSet(new HashSet<T>());
		semaphore = new Semaphore(bound, true);
	}
	
	public boolean add(T o) throws InterruptedException {
		boolean wasAdded = false;
		try {
			semaphore.acquire();
			wasAdded = set.add(o);
			if (!wasAdded) {
				semaphore.release(); //release lock to prevent deadlock
			}
		} catch (Exception e) {
			System.out.println(e);
			semaphore.release(); //release lock to prevent deadlock
		} finally {             
			if (!wasAdded) {            
				sem.release(); 
			}        
		}     
		return wasAdded;
	}
	
	public boolean remove (Object o) {
		boolean wasRemoved = set.remove(o);
		if (wasRemoved) {
			semaphore.release();
		}
		return wasRemoved;
	}
}