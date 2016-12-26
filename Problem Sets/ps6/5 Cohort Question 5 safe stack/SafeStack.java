/*Cohort Question 5: 
Write a thread-safe class named SafeStack which extends java.util.Stack<E> 
with two operations pushIfNotFull(E e) and popIfNotEmpty(). Do it in 
different ways and design an experiment to compare the performance of the two. */

// try two methods, either composition or extension; think -- can do client side locking??

public class SafeStack {
	public static void main(String args[]) {
		//experiment and print the difference in speed

		//composition
		long startTime = System.nanoTime();
		SafeStackComposition composition = new SafeStackComposition(20);
		for (int i=0; i<20; i++){
			composition.pushIfNotFull(Long.valueOf(1));
		}
		long estimatedTime = System.nanoTime() - startTime;
		System.out.println("time spent using composition: "+estimatedTime);

		//extension
		startTime = System.nanoTime();
		SafeStackExtension extension = new SafeStackExtension(20);
		for (int i=0; i<20; i++){
			extension.pushIfNotFull(Long.valueOf(1));
		}
		estimatedTime = System.nanoTime() - startTime;
		System.out.println("time spent using extension: "+estimatedTime);

		//result: extension is faster than composition
	}
	
}

class SafeStackComposition {

	//private final Stack<E> stack;
	private final MyStack stack;

	public SafeStackComposition (int s) { // add a max size parameter for constructor
		this.stack = new MyStack(s);
	}

	public synchronized void pushIfNotFull(long j) {
		while(stack.isFull()){
			try {
				wait();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		stack.push(j);
		notifyAll();
	}

	public synchronized long popIfNotEmpty() {
		while(stack.isEmpty()){
			try {
				wait();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		notifyAll();
		return stack.pop();
	}

	public synchronized void push(long item) {
		stack.push(item);
	}

	public synchronized long pop() {
		return stack.pop();
	}

	public synchronized long peek() {
		return stack.peek();
	}

	public synchronized boolean isEmpty() {
		return stack.isEmpty();
	}

	public synchronized boolean isFull() {
		return stack.isFull();
	}

}

class SafeStackExtension extends MyStack {

	public SafeStackExtension (int s) { // add a max size parameter for constructor
		super(s);
	}

	public synchronized void pushIfNotFull(long j) {
		while(this.isFull()){
			try {
				wait();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		this.push(j);
		notifyAll();
	}

	public synchronized long popIfNotEmpty() {
		while(this.isEmpty()){
			try {
				wait();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		notifyAll();
		return this.pop();
	}

}


class MyStack {
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
	

	public synchronized void push(long j) {
		stackArray[++top] = j;
	}

	public synchronized long pop() {	
	    return stackArray[top--];
	}
	

	public synchronized long peek() {
	    return stackArray[top];
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