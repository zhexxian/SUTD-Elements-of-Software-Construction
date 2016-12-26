public class FrurtMarket {
	public static void main(String args[]){  

		Buffer appleBuffer = new Buffer ();
		Buffer orangeBuffer = new Buffer ();
		Buffer grapeBuffer = new Buffer ();
		Buffer watermelonBuffer = new Buffer ();

		MyProducer appleProd = new MyProducer(appleBuffer);
		MyProducer orangeProd = new MyProducer(orangeBuffer);
		MyProducer grapeProd = new MyProducer(grapeBuffer);
		MyProducer watermelonProd = new MyProducer(watermelonBuffer);

		
		MyConsumer appleCons = new MyConsumer(appleBuffer);
		MyConsumer orangeCons = new MyConsumer(orangeBuffer);
		MyConsumer grapeCons = new MyConsumer(grapeBuffer);
		MyConsumer watermelonCons = new MyConsumer(watermelonBuffer);

		
		appleProd.start();
		orangeProd.start();
		grapeProd.start();
		watermelonProd.start();

		appleCons.start();
		orangeCons.start();
		grapeCons.start();
		watermelonCons.start();
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
		
		buffer.removeItem();
	}
}

class Buffer {
	public static int SIZE = 5;	
	private Object[] objects = new Object[SIZE];
	private static int count = 0;

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