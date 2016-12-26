/* Cohort Question 4: 
Given MultipleClient.java (with the bigger number and 5 clients) 
and ExecutorWebServer.java, tune the thread pool size in the 
factor web server example for optimal performance. */

/*
[N] Number of CUPs: 4

[U] Target CPU utilization: 0.8

    Computation time: 2423ms
    Total time: 2451ms
    Wait time: 2451-2423 = 28ms
[W/C] 
    28/2423

[M] Optimal pool size:
	M = N * U * (1 + W/C)
	M = 4 * 0.8 * (2451/2423)
	M = 3.24 = 4
*/

//new time: 2649ms

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorWebServerOptimalPerformance {
	
	// change the number of threads to M = 4
	private static final int NTHREADS = 4;
	private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);
	
  	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(43215, 1000);

		// System.out.println("There are "+
		// 	Runtime.getRuntime().availableProcessors()+
		// 	" CPUs");

		while (true) {
			final Socket connection = serverSocket.accept();
			System.out.println("connected");
			Runnable task = new Runnable () {
				public void run() {
					handleRequest(connection);
				}
			};
			exec.execute(task);
		}
    }

	protected static void handleRequest(Socket connection) {
		try {
			BufferedReader in = new BufferedReader(
	                  new InputStreamReader(connection.getInputStream()));
			PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
			
			BigInteger result = factor(new BigInteger(in.readLine()));
			System.out.println("factored");
			out.println(result.toString());
			out.flush();
		} catch (IOException e){
			System.out.println(e);
		}
	}

	private static BigInteger factor(BigInteger n) {
		BigInteger i = new BigInteger("2");
		BigInteger zero = new BigInteger("0");
		
		while (i.compareTo(n) < 0) {			
			if (n.remainder(i).compareTo(zero) == 0) {
				return i;
			}
			
			i = i.add(new BigInteger("1"));
		}
		
		assert(false);
		return null;
	}
}