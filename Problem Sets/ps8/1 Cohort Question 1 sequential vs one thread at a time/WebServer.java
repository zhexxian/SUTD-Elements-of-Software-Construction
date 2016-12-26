/* Cohort Question 1: 
Given MultipleClient.java and WebServer.java, implement the sequential 
server and one thread per task server and compare their performance. 
Vary the number of threads (10,100,1000) and see the trend.   */

/* Result:
--------sequential server
10 threads: 23729ms
100 threads: 215801ms
1000 threads: NA (taking too long)
--------one thread per task server
10 threads: 12536ms
100 threads: 117746ms
1000 threads: NA (taking too long) */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WebServer {
	public static void main (String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(43211, 1000);
		
// sequential server
		while (true) {
			final Socket connection = socket.accept();
		 //System.out.println("connected");
			handleRequest(connection);
		}
		
// one thread per task
		// while (true) {
		// 	final Socket connection = socket.accept();
		// 	System.out.println("connected");
		// 	Runnable task = new Runnable () {
	 //        	public void run() {
	 //        		try{
	 //        			handleRequest(connection);
	 //        		} catch (Exception e){
	 //        			System.out.println(e);
	 //        		}
	 //        	}
		// 	};
		// 	new Thread(task).start();
  //       }
	}
	
	private static void handleRequest (Socket connection) throws Exception {

		BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
		PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
		
		BigInteger result = factor(new BigInteger(in.readLine()));

		//System.out.println("factored");
		out.println(result.toString());
		out.flush();

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
