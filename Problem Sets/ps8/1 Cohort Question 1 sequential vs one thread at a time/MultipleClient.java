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

import java.io.*;
import java.math.BigInteger;
import java.net.*;
 
public class MultipleClient {
    public static void main(String[] args) throws Exception {
    	int numberOfClients = 100; //vary this number here
    	long startTime = System.currentTimeMillis();    	
    	//BigInteger n = new BigInteger("4294967297");
    	BigInteger n = new BigInteger("239839672845043");
    	Thread[] clients = new Thread[numberOfClients];
    	
    	for (int i = 0; i < numberOfClients; i++) {
        	clients[i] = new Thread(new Client(n));
        	clients[i].start();
        }
        
        for (int i = 0; i < numberOfClients; i++) {
        	clients[i].join();
        }
        System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
    }
}

class Client implements Runnable {
	private final BigInteger n;
	
	public Client (BigInteger n) {
		this.n = n;
	}
	
	public void run() {
		String hostName = "localhost";
        int portNumber = 4321;

        try {
        	//long startTime = System.currentTimeMillis();
        	Socket socket = new Socket(hostName, portNumber);        	
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        	BufferedReader in =
                    new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
        	out.println(n.toString());
        	out.flush();        	
        	in.readLine();
            //System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
            out.close();
            in.close();
            socket.close();
        }
        catch (Exception e) {
        }
	}
}
