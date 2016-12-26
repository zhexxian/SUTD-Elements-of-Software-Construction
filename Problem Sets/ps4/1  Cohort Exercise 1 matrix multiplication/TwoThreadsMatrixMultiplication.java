public class TwoThreadsMatrixMultiplication {
	public static void main (String[] args) {

		// the two matrices to be multiplied
		int[][] A = {{1,2,3},{4,5,6},{7,8,9},{7,8,9}};
    	int[][] B = {{1,2,3},{4,5,6},{7,8,9}};
		
		// start two threads
		Thread2 thread2 = new Thread2(A,B);
		Thread1 thread1 = new Thread1(A,B);
		thread1.start();
		thread2.start();

		// join threads and print the resulting matrix
		try {
			thread1.join();
			thread2.join();
			int[][] result = Add(thread1.getValue(),thread2.getValue());
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

// Thread1 class
class Thread1 extends Thread {
	private int[][] first;
	private int[][] second;
	private int[][] value;
	
	public Thread1 (int[][] first, int[][] second) {
		this.first = first;
		this.second = second;
	}
	
	public void run () {
		value = new int[first.length][second.length];
		for (int i = 0; i < first.length; i++) {
        	for (int j = 0; j < second[0].length; j++) {
            	for (int k = 0; k < first[0].length/2; k++) {
            		value[i][j] += first[i][k]*second[k][j]; 
            	}
            }
        }
	}
	
	public int[][] getValue() {
		return value;
	}
}

// Thread2 class
class Thread2 extends Thread {
	private int[][] first;
	private int[][] second;
	private int[][] value;
	
	public Thread2 (int[][] first, int[][] second) {
		this.first = first;
		this.second = second;
	}
	
	public void run () {
		value = new int[first.length][second.length];
		for (int i = 0; i < first.length; i++) {
        	for (int j = 0; j < second[0].length; j++) {
            	for (int k = first[0].length/2; k < first[0].length; k++) {
            		value[i][j] += first[i][k]*second[k][j]; 
            	}
            }
        }
	}
	
	public int[][] getValue() {
		return value;
	}
}