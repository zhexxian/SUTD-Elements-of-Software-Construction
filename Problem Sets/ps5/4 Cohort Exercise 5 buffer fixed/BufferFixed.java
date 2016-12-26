import java.util.Random;


// the big picture is that the producer and consumer share one buffer,
// so the buffer access should be atomic

// also, use wait()/nofityAll() instead of sleep() to avoid busy waiting and ensure thread safety

public class BufferFixed {
	public static void main (String[] args) throws Exception {
		Buffer buffer = new Buffer (10);
		MyProducer prod = new MyProducer(buffer);
		prod.start();
		MyConsumer cons = new MyConsumer(buffer);
		cons.start();
	}
}

class MyProducer extends Thread {
	private Buffer buffer;
	
	public MyProducer (Buffer buffer) {
		this.buffer = buffer;
	}
	
	public void run () {

		synchronized (buffer) { //the synchronized lock is buffer
			while (buffer.getSize() == buffer.SIZE) { //check that it is notified correctly, condition is indeed fulfilled
				try {
					wait();
				} catch (InterruptedException e) {
					// Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// sleep might not be the best way to ensure thread safety
		// try {
		// 	Thread.sleep(random.nextInt(1000));
		// } catch (InterruptedException e) {
		// 	e.printStackTrace();
		// }
		
		// add item after coming out of the while loop
		buffer.addItem(new Object());
	}
}

class MyConsumer extends Thread {
	private Buffer buffer;
	
	public MyConsumer (Buffer buffer) {
		this.buffer = buffer;
	}
	
	public void run () {

		synchronized (buffer) { //the synchronized lock is buffer
			while (buffer.getSize() == 0) { //check that it is notified correctly, condition is indeed fulfilled
				try {
					wait();
				} catch (InterruptedException e) {
					// Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


		// try {
		// 	Thread.sleep(random.nextInt(1000));
		// } catch (InterruptedException e) {
		// 	e.printStackTrace();
		// }
		
		buffer.removeItem();
	}
}

class Buffer {
	public static int SIZE;	
	private Object[] objects;
	private static int count = 0;

	public Buffer (int size) {
		SIZE = size;
		objects = new Object[SIZE];
	}
	

	// need to notify after add or remove method
	public void addItem (Object object) {
		synchronized(this){
			if (count < SIZE) {
				objects[count] = object;
				count++;
				notifyAll();
			}	
		}	
	}
	
	public Object removeItem() {
		synchronized(this){
			if (count > 0) {
				count--;
				notifyAll();
				return objects[count];
			}
			else {
				return null; 
			}	
		}	
	}

	public static int getSize(){
		return count;
	}
}