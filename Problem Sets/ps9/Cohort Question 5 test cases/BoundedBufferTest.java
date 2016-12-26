//package week11;

/*Cohort Question 5: 
Given BoundedBufferTest.java, 
write two more test cases and document what you are testing for. */

import java.util.concurrent.Semaphore;

import junit.framework.TestCase;

//this class is thread safe
public class BoundedBufferTest<E> extends TestCase {	
	private static final long LOCKUP_DETECT_TIMEOUT = 1000;

	public void testIsEmptyWhenConstructued () {
		BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		assertTrue(bb.isEmpty());
		assertFalse(bb.isFull());
	}
	
	public void testIsFullAfterPuts () throws InterruptedException {
		BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		for (int i = 0; i < 10; i++) {
			bb.put(i);
		}
		
		assertTrue(bb.isFull());
		assertFalse(bb.isEmpty());
	}
	
	public void testTakeBlocksWhenEmpty () {
		final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		Thread taker = new Thread() {
			public void run() {
				try {
					int unused = bb.take();
					fail();
				} catch (InterruptedException success) {} //if interrupted, the exception is caught here
			}
		};
		
		try {
			taker.start();
			Thread.sleep(LOCKUP_DETECT_TIMEOUT);
			taker.interrupt();
			taker.join(LOCKUP_DETECT_TIMEOUT);
			assertFalse(taker.isAlive()); //the taker should not be alive for some time
		} catch (Exception unexpected) {
			fail(); //it fails for other exceptions
		}
	}

	// Additional test case #1: test if Put method is blocked when the buffer is full
	
	public void testPutBlocksWhenFull () throws InterruptedException {
		final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		for (int i = 0; i < 10; i++) {
			bb.put(i);
		}
		Thread puter = new Thread() {
			public void run() {
				try {
					bb.put(2);
					fail();
				} catch (InterruptedException success) {} //if interrupted, the exception is caught here
			}
		};
		
		try {
			puter.start();
			Thread.sleep(LOCKUP_DETECT_TIMEOUT);
			puter.interrupt();
			puter.join(LOCKUP_DETECT_TIMEOUT);
			assertFalse(puter.isAlive()); //the puter should not be alive for some time
		} catch (Exception unexpected) {
			fail(); //it fails for other exceptions
		}
	}

	// Additional test case #2: test if test if Put method is blocked when the buffer is not full
	public void testPutAvailableWhenNotFull () throws InterruptedException {
		final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		for (int i = 0; i < 5; i++) {
			bb.put(i);
		}
		Thread puter = new Thread() {
			public void run() {
				try {
					bb.put(2);
					assertTrue(true);
				} catch (InterruptedException success) {} //if interrupted, the exception is caught here
			}
		};
		
		try {
			puter.start();
			Thread.sleep(LOCKUP_DETECT_TIMEOUT);
			puter.interrupt();
			puter.join(LOCKUP_DETECT_TIMEOUT);
			assertFalse(puter.isAlive()); //the puter should not be alive for some time
		} catch (Exception unexpected) {
			fail(); //it fails for other exceptions
		}
	}

}

//this class is thread safe
class BoundedBuffer<E> {	
	//class invariant: availableItems + availableSpaces = items.length
	//class invariant: availableItems >= 0
	//class invariant: avaliableSpaces > = 0
	//class invariant: putPosition >= 0 && putPosition < items.length
	//class invariant: takePosition >= 0 && takePosition < items.length
	private final Semaphore availableItems, availableSpaces;	
	//@GuardedBy("this") 
	private final E[] items;	
	//@GuardedBy("this") 
	private int putPosition = 0,	takePosition = 0;	
	
	public BoundedBuffer(int capacity) {	
		availableItems = new Semaphore(0);	
		availableSpaces = new Semaphore(capacity);	
		items = (E[]) new Object[capacity];	
	}	

	public boolean isEmpty() {	
		return availableItems.availablePermits() == 0;	
	}	
	//postcondition: return true if and only if availableSpaces = items.length
	
	public boolean isFull() {
		return availableSpaces.availablePermits() == 0;	
	}

	//pre-condition: items is not null
	public void put(E x) throws InterruptedException {	
		availableSpaces.acquire();	
		doInsert(x);	
		availableItems.release();	
	}	
	//postcondition: availableSpaces'.availablePermits() = availableSpaces.availablePermits() - 1
	//postcondition: availableItems'.availablePermits() = availableItems.availablePermits() + 1
	//postcondition: putPosition' = putPosition + 1 if putPosition < items.length-1; 0 otherwise
 
	public E take() throws InterruptedException {	
		availableItems.acquire();	
		E item = doExtract();	
		availableSpaces.release();	
		return item;	
	}	
 
	private synchronized void doInsert(E x) {	
		int i = putPosition;	
		items[i] = x;	
		putPosition = (++i == items.length)? 0 : i;	
	}
	
	private synchronized E doExtract() {	
		int i = takePosition;	
		E x = items[i];	
		items[i] = null;	
		takePosition = (++i == items.length)? 0 : i;	
		return x;	
	}	
}
