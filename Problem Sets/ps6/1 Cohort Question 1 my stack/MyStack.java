/*Cohort Question 1: 
Identify the pre-condition/post-condition of the methods in MyStack.java
and make the class thread-safe. 
*/

public class MyStack {
	private final int maxSize; //immutable (final state) variable is safe 
	private long[] stackArray; //@guarded by 'this'; @invariant: 0 =< stackArray.length() <= maxSize
	private int top; //@guarded by 'this'; @invariant: -1 <= top <= stackArray.length()-1
	

	//@requires: pre-condition s>=0
	//@ensures: post-condition stackArray.length() = s; top = -1
	public MyStack(int s) { 
		if (s < 0) {
		}
		// only proceed if s >= 0
		maxSize = s;
	    stackArray = new long[maxSize];
	    top = -1;
	}
	
	//@requires: pre-condition top < stackArray.length()-1 (myStack is not full)
	//@ensures: post-condition top++, top < stackArray.length()

	// use lock, condition, and wait
	public synchronized void push(long j) {
		while(myStack.isFull()){
			try {
				wait();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		stackArray[++top] = j;
		// notify other threads that top and stackArray property have changed
		notifyAll();
	}

	//@requires: pre-condition top > 0
	//@ensures: post-condition top--, top >= 0

	// use lock, condition, and wait
	public synchronized long pop() {	
		while(myStack.isEmpty()){
			try {
				wait();
			} catch (Exception e) {
				System.out.println(e);
			}
		}	
	    return stackArray[top--];
	    // notify other threads that top and stackArray property have changed
		notifyAll();
	}
	
	//@requires: pre-condition top >= 0
	//@ensures: post-condition top and stackArray remain unchanged
	public synchronized long peek() {
		while(myStack.isEmpty()) {
			try {
				wait();
			} catch (Exceptions e) {
				System.out.println(e);
			}
		}
	    return stackArray[top];
	    // does not need to call notify()
	}

	//@requires: pre-condition top >= -1
	//@ensures: post-condition top and stackArray remain unchanged

	// use a lock, so it will not check while top is being updated
	public synchronized boolean isEmpty() {
		return (top == -1);
	}

	//@requires: pre-condition top >= -1
	//@ensures: post-conditio top and stackArray remain unchanged

	// use a lock, so it will not check while top is being updated
	public synchronized boolean isFull() {
		return (top == maxSize - 1);
	}
}