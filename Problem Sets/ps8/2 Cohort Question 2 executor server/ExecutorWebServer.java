/* Cohort Question 2:  
Given MultipleClient.java, complete ExecutorWebServer.java using 
newFixedThreadPool with 100 threads. Compare the performance with 
the sequential and thread-per-task web server (with 10, 100, 1000 
client threads). */

/*
--------sequential server
10 threads: 23729ms
100 threads: 215801ms
1000 threads: NA (taking too long)
--------one thread per task server
10 threads: 12536ms
100 threads: 117746ms
1000 threads: NA (taking too long)
--------executor server
10: 12328ms
100: 127248ms
1000 threads: NA (taking too long) */

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorWebServer {
	private static final int NTHREADS = 100;
	private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);
	
  public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(43211, 1000);

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