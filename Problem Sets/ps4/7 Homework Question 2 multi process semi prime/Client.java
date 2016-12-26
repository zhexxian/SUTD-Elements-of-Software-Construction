package week5;

import java.io.*;
import java.net.*;
import java.math.BigInteger;

public class Client {
    public static void main(String[] args) throws Exception {
        String hostName = "localhost";
        //String hostName = "fe80::7517:c1af:b2bb:da73%4";
        int portNumber = 43201;
 
        Socket echoSocket = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        ObjectInputStream in = new ObjectInputStream(echoSocket.getInputStream());
        BufferedReader inin = new BufferedReader(
                new InputStreamReader(echoSocket.getInputStream()));
        
        BigInteger receivedSemiPrime = (BigInteger)in.readObject();
        BigInteger lower = (BigInteger)in.readObject();
        BigInteger upper = (BigInteger)in.readObject();

        HelperThread helperThread = new HelperThread(receivedSemiPrime,lower,upper);
        echoSocket.setSoTimeout(1000);
        helperThread.start();

        // looping back and forth in 1 second interval to check thread & server interrupt signal

        while (true) {
            try {
                if(inin.readLine().equals("stop")){
                    helperThread.interrupt();
                    helperThread.interrupt();
                    System.out.println("Thread interrupted");
                    out.println("Thread interrupted");
                    out.flush();
                    break;
                }
                throw new SocketTimeoutException("time out");
            }
            catch (java.net.SocketTimeoutException e) {
                helperThread.join();
                if(!helperThread.getValue().equals(BigInteger.ZERO)){
                    BigInteger anotherFactor = receivedSemiPrime.divide(helperThread.getValue());
                    out.println("Found");
                    System.out.println("Found");
                    out.flush();
                    out.println("The prime factors are "+helperThread.getValue()+" and "+anotherFactor);
                    out.flush();
                    break;
                }
            }         
        }     
    }
}


// the thread class

class HelperThread extends Thread {
    BigInteger zero = new BigInteger("0");
    BigInteger one = new BigInteger("1");

    private BigInteger inputSemiPrime, lower, upper;
    BigInteger factor = BigInteger.ZERO;
    
    public HelperThread (BigInteger inputSemiPrime, BigInteger lower, BigInteger upper) {
        this.inputSemiPrime = inputSemiPrime;
        this.lower = lower;
        this.upper = upper;
    }
    
    public void run () {
        for(BigInteger i = lower; 
            i.compareTo(upper) <= 0; 
            i=i.add(one)){
            if (inputSemiPrime.remainder(i).compareTo(zero) == 0){
                factor = i;
                break;
            }

            // handle interrupt

            if (this.isInterrupted()){
                System.out.println("I am interrupted");
                break;
            }
        }
    }
    
    public BigInteger getValue() {
        return factor;
    }
}