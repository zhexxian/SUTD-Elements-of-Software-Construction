import java.math.BigInteger;

public class FactorThread {
	public static BigInteger one = new BigInteger("1");
		public static BigInteger two = new BigInteger("2");

	public static void main (String[] args) {
		BigInteger inputSemiPrime = new BigInteger("15");
		int numberOfThreads = 1;
		System.out.println("Number of threads is: "+numberOfThreads);
		BigInteger[] lowers = new BigInteger[numberOfThreads];
		BigInteger[] uppers = new BigInteger[numberOfThreads];
		HelperThread[] helperThreads = new HelperThread[numberOfThreads]; 
		//assign values for the lowers
		lowers[0] = two;
		for(int i=1; i<numberOfThreads; i++){
            lowers[i] = BigInteger.valueOf(i).multiply(inputSemiPrime.divide(BigInteger.valueOf(numberOfThreads)));
        }
        //assign values for the uppers
        uppers[numberOfThreads-1] = inputSemiPrime;
        for(int i=0; i<numberOfThreads-1; i++){
            uppers[i] = BigInteger.valueOf(i+1).multiply(inputSemiPrime.divide(BigInteger.valueOf(numberOfThreads)));
        }
        // initiating and starting threads
		for(int i=0; i<numberOfThreads; i++){
            helperThreads[i] = new HelperThread(inputSemiPrime,lowers[i],uppers[i]);
            helperThreads[i].start();
        }
		
		
		try {
			for(int i=0; i<numberOfThreads; i++){
	            helperThreads[i].join();
	        }

	       	for(int i=0; i<numberOfThreads; i++){
	       		if(!helperThreads[i].getValue().equals(null)){
	       			BigInteger anotherFactor = inputSemiPrime.divide(helperThreads[i].getValue());
                    System.out.println("The prime factors are "+helperThreads[i].getValue()+" and "+anotherFactor);
                    break;
	       		}
	        }
	        for(int i=0; i<numberOfThreads; i++){
				while(!helperThreads[i].foundFactor()) {
					// do nothing, patiently wait
				}
	            helperThreads[i].interrupt();
	        }
		}
		catch (InterruptedException e) {
			System.out.println("A thread didn't finish!");
		}
		catch (Exception e) {
			System.out.println("Exception captured.");
		}
	}
}

class HelperThread extends Thread {
	private boolean foundFactor;
	BigInteger zero = new BigInteger("0");
	BigInteger one = new BigInteger("1");

	private BigInteger inputSemiPrime, lower, upper, factor;
	
	public HelperThread (BigInteger inputSemiPrime, BigInteger lower, BigInteger upper) {
		this.inputSemiPrime = inputSemiPrime;
		this.lower = lower;
		this.upper = upper;
	}
	
	public void run () {
		for(BigInteger i = lower; 
            i.compareTo(upper) <= 0; 
            i=i.add(one)){
			if (foundFactor){
				break;
			}
            else if (inputSemiPrime.remainder(i).compareTo(zero) == 0){
                factor = i;
                foundFactor = true;
                break;
            }
        }
	}

	public boolean foundFactor(){
		return foundFactor;
	}
	
	public BigInteger getValue() {
		return factor;
	}
}