import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class CachedFactorizerThreadSafe {
	public static void main (String[] args) {
		CachedFactorizer factorizer = new CachedFactorizer ();
		Thread tr1 = new Thread (new MyRunnable(factorizer));
		Thread tr2 = new Thread (new MyRunnable(factorizer));
		tr1.start();
		tr2.start();
		
		try {
			tr1.join();
			tr2.join();
		}
		catch (Exception e) {
			
		}
	}
}

class MyRunnable implements Runnable {
	private CachedFactorizer factorizer;
	
	public MyRunnable (CachedFactorizer factorizer) {
		this.factorizer = factorizer; 
	}
	
	public void run () {
		Random random = new Random ();
		factorizer.factor(random.nextInt(100));
	}
}

class CachedFactorizer {
	private int lastNumber;
	private List<Integer> lastFactors;

	// make the long variables hits and cacheHits volatile 
	private volatile long hits;
	private volatile long cacheHits;

	// create a lock
	private Object lock = new Object();

	// synchronized methods for getters
	public synchronized long getHits () {
		return hits;
	}
	
	public synchronized double getCacheHitRatio () {
		return (double) cacheHits/ (double) hits;
	}
	
	// This method is not synchronized by a lock as a whole, but only the hits and cacheHits increment is synchronized

	// the assumption/requirement here is that it is OK if some thread updated hits++ and cacheHit, 
	// and before lastNumber/factors is updated, some other thread comes in and update hits++ and cacheHit
	// because the arraylist of factors only correspond directly to the input, not to the hit count
	public List<Integer> service (int input) {
		List<Integer> factors = null;
		synchronized(this){
			++hits;
		}
		
		if (input == lastNumber) {
			synchronized(this){
				++cacheHits;
			}
			factors = new ArrayList<Integer>(lastFactors);
		}
		
		if (factors == null) {
			factors = factor(input);
			lastNumber = input;
			lastFactors = new ArrayList<Integer>(factors);
		}
		return factors;
	}

	// Synchronized method is not used for factor(int n) for liveness of the code
	public List<Integer> factor(int n) {		
		List<Integer> factors = new ArrayList<Integer>();
		for (int i = 2; i <= n; i++) {
			while (n % i == 0) {
		        factors.add(i);
		        n /= i;
		    }
		}
		return factors;
	}
}