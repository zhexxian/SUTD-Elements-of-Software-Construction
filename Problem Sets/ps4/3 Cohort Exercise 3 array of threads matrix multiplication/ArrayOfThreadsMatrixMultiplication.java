// The program is for multiplying a square matrix by itself

public class ArrayOfThreadsMatrixMultiplication {
	// the square matrix to be multiplied
	public static int[][] A = getMatrix(1000);

	// initialize the result matrix
	public static int[][] result = new int[A.length][A.length];

	public static void main (String[] args) {
		
		// change number of thread here

		int numberOfThreads = 1;

		// uppers, lowers, helperthreads

		int[] lowers = new int[numberOfThreads];
		int[] uppers = new int[numberOfThreads];
		HelperThread[] helperThreads = new HelperThread[numberOfThreads];

		//assign values for the lowers
		lowers[0] = 0;
		for(int i=1; i<numberOfThreads; i++){
            lowers[i] = i*(A.length/numberOfThreads);
        }
        //assign values for the uppers
        uppers[numberOfThreads-1] = A.length;
        for(int i=0; i<numberOfThreads-1; i++){
            uppers[i] = (i+1)*(A.length/numberOfThreads);
        }
        // initiating and starting threads
		for(int i=0; i<numberOfThreads; i++){
            helperThreads[i] = new HelperThread(A,lowers[i],uppers[i]);
            helperThreads[i].start();
        }

		// join threads and print the resulting matrix
		try {
			for(int i=0; i<numberOfThreads; i++){
	            helperThreads[i].join();
	        }

	        PrintMatrix(result);

		}
		catch (InterruptedException e) {
			System.out.println("A thread didn't finish!");
		}
		catch (Exception e) {
			System.out.println("Exception captured.");
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

    // auto generate square matrix with n rows

	public static int[][] getMatrix (int rows) {
        int[][] result = new int[rows][rows];
        int counter = 0;
        for (int i = 0; i < rows; i++) {
              for (int j = 0; j < rows; j++) {
                    result[i][j] = counter++; 
              }
        }

       return result;
	}
}
// Thread1 class
class HelperThread extends Thread {
	private int lower;
	private int upper;
	private int[][] matrix;
	
	public HelperThread (int[][] matrix, int lower, int upper) {
		this.matrix = matrix;
		this.lower = lower;
		this.upper = upper;
	}
	
	public void run () {
		for (int i = 0; i < matrix.length; i++) {
        	for (int j = 0; j < matrix[0].length; j++) {
            	for (int k = lower; k < upper; k++) {
            		ArrayOfThreadsMatrixMultiplication.result[i][j] += matrix[i][k]*matrix[k][j]; 
            	}
            }
        }
	}
	
	// public int[][] getValue() {
	// 	return ArrayOfThreadsMatrixMultiplication.result[i][j];
	// }
}