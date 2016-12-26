public class TwoRunablesMatrixMultiplication {
	public static void main (String[] args) {

        // the two matrices to be multiplied
		int[][] A = {{1,2,3},{4,5,6},{7,8,9},{7,8,9}};
    	int[][] B = {{1,2,3},{4,5,6},{7,8,9}};

		// Initialize the runnables
		Multiplexer multiplexer1 = new Multiplexer (A, B, 0, (A[0].length)/2);
    	Multiplexer multiplexer2 = new Multiplexer (A, B, (A[0].length)/2, A[0].length);
    	
        // start two threads
		Thread thread1 = new Thread(multiplexer1);
    	Thread thread2 = new Thread(multiplexer2);
		thread1.start();
		thread2.start();
		
        // join threads and print the resulting matrix
		try {
			thread1.join();
			thread2.join();
			int[][] result = Add(multiplexer1.getValue(),multiplexer2.getValue());
			PrintMatrix(result);
		}
		catch (InterruptedException e) {
			System.out.println("A thread didn't finish!");
		}
	}

    // matrix multiplication method
	public static int[][] Multi(int[][] first, int[][] second) {
    	//assume that the number of columns in first and the number of rows in second is the same   
    	//assume that both first and second are not []
    	int[][] result = new int[first.length][second[0].length];
    	
    	for (int i = 0; i < first.length; i++) {
        	for (int j = 0; j < second[0].length; j++) {
            	for (int k = 0; k < first[0].length; k++) {
            		result[i][j] += first[i][k]*second[k][j]; 
            	}
            }
        }
            	
        return result;
    }

    // matrix addition method
    public static int[][] Add(int[][] first, int[][] second) {
    	//assume that the number of columns in first and the number of rows in second is the same   
    	//assume that both first and second are not []
    	int[][] result = new int[first.length][first[0].length];
    	
    	for (int i = 0; i < first.length; i++) {
        	for (int j = 0; j < first[0].length; j++) {
            	result[i][j] = first[i][j]+second[i][j]; 
            }
        }
            	
        return result;
    }
	
    // print matrix method
    public static void PrintMatrix (int[][] toprint) {
    	//assume that toprint is a square matrix
    	//assume that toprint is not []
    	for (int i = 0; i < toprint.length; i++) {
        	for (int j = 0; j < toprint[0].length; j++) {
        		System.out.print(toprint[i][j] + "\t");
        	}
    		System.out.println();
    	}
    }
}

// Runnable subclass
class Multiplexer implements Runnable {
	private int upper;
	private int lower;
	private int[][] first;
	private int[][] second;
	private int[][] value;
	

	public Multiplexer (int[][] first, int[][] second, int upper, int lower) {
    	this.first = first;
		this.second = second;
		this.upper = upper;
		this.lower = lower;
    }
	
	public void run () {
		value = new int[first.length][second.length];
		for (int i = 0; i < first.length; i++) {
        	for (int j = 0; j < second[0].length; j++) {
            	for (int k = upper; k < lower; k++) {
            		value[i][j] += first[i][k]*second[k][j]; 
            	}
            }
        }
	}
	
	public int[][] getValue() {
		return value;
	}
}