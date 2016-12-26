/* Cohort Question 3: 
Given LifecycleWebServer.java, modify it such that it can be shut down 
through a client request by sending the server a specially formatted 
request. Test your program. */

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
            BufferedReader stdIn =
                    new BufferedReader(
                        new InputStreamReader(System.in());
            if (stdIn.readLine()==NULL || !stdIn.readLine().equals("stop")) {
                out.println(n.toString());
                out.flush();            
                in.readLine();
                //System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
                out.close();
                in.close();
                socket.close();    
            }
            // if "stop" is called
            else {
                stop();
            }        	
        }
        catch (Exception e) {
        }
	}
}
