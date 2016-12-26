//package Week12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*
 * Apply SPMD (Single Program, Multiple Data) design pattern for concurrent 
 * programming to parallelize the program which approximates $\pi$ by 
 * integrating the following formula $4/(1+x^2 )$. Hint: In the SPMD design 
 * pattern, all threads run the same program, operating on different data.
 */

public class Exercise1 {
	static double result;
	static int NTHREADS = 5;
	static double step = 1d / (NTHREADS-1);
	public static void main(String[] args) throws Exception {

		ArrayList<FutureTask<Double>> futureTasks = new ArrayList<FutureTask<Double>>();

		// complete the program by writing your code below.
		for(int idx=0; idx<NTHREADS-1; idx++) {
			final int temp = idx;
			//assign each future task with different integration boundary for PI approximation
			futureTasks.add(new FutureTask<Double>(new CallableTask(temp)));
		}
		for(FutureTask<Double> future:futureTasks){
			future.run();
		}
		for(FutureTask<Double> future:futureTasks){
			try {
	            result += future.get();
	        } catch (InterruptedException | ExecutionException e) {
	            e.printStackTrace();
	        }
		}
		System.out.println("Resulted approximates for PI is: "+result);
	}

}

class CallableTask implements Callable<Double>{
	int temp;
	CallableTask(int temp){
		this.temp = temp;
	}
	public Double call() throws Exception {
		System.out.println("Executing call()");
        return integrate(temp*Exercise1.step,(temp+1)*Exercise1.step);
    }
    public static double f(double x) {
		return 4.0 / (1 + x * x);
	}

	// the following does numerical integration using Trapezoidal rule.
	public static double integrate(double a, double b) {
		int N = 10000; // preciseness parameter
		double h = (b - a) / (N - 1); // step size
		double sum = 1.0 / 2.0 * (f(a) + f(b)); // 1/2 terms

		for (int i = 1; i < N - 1; i++) {
			double x = a + h * i;
			sum += f(x);
		}
		return sum * h;
	}
}
