/* Homework Question 1:  
RejectedExecutionException is thrown by an Executor when a task 
cannot be accepted for execution, which could happen when (A) the 
executor has shutdown (B) when the task queue is full. Based on 
LifecycleWebServer.java, write two programs (according to A and B) 
which demonstrate how RejectedExecutionException occurs.  */

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
 
public class LifeCycleWebServerRejectedA {
	private static final ExecutorService exec = new ScheduledThreadPoolExecutor (100);
	
    public static void main(String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(43213);
		
		while (!exec.isShutdown()) {
			try {
				final Socket connection = socket.accept();
				Runnable[] tasks = new Runnable[10];
				for(int i=0; i<10; i++) {
					tasks[i] = new Runnable () {
						public void run() {
								handleRequest(connection);
						}
					};
					exec.execute(tasks[i]);
				}
				// Exception case: (A) call execute after the executor has shutdown
				exec.shutdown();
				exec.execute(tasks[0]);
	
				
			} catch (RejectedExecutionException e) {
				if (!exec.isShutdown()) {
					System.out.println("LOG: task submission is rejected.");
				}
			}			
		}
    }
    
    public void stop() {
    	exec.shutdown();
    }

	protected static void handleRequest(Socket connection) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			PrintWriter out = new PrintWriter(connection.getOutputStream(), true);                   
			BigInteger number = new BigInteger(in.readLine());
			BigInteger result = factor(number);
			//System.out.println("sending results: " + String.valueOf(result));
			out.println(result);
			out.flush();
			in.close();
			out.close();
			connection.close();
		}
		catch (Exception e) {
			System.out.println("Something went wrong with the connection");
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