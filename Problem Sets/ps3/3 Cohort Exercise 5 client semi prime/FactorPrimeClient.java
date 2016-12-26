import java.io.*;
import java.net.*;
import java.math.BigInteger;

public class  FactorPrimeClient {
    public static void main(String[] args) throws Exception {
        String hostName = "localhost";
        //String hostName = "fe80::7517:c1af:b2bb:da73%4";
        int portNumber = 4321;
 
        Socket echoSocket = new Socket(hostName, portNumber);
        BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
        String value1;
        String value2;
        String value3;
        BigInteger receivedSemiPrime;
        BigInteger borderValue1;
        BigInteger borderValue2;
        BigInteger zero = new BigInteger("0");

        do {
            value1 = in.readLine();
            value2 = in.readLine();
            value3 = in.readLine();

            receivedSemiPrime = new BigInteger(value1);
            borderValue1 = new BigInteger(value2);
            borderValue2 = new BigInteger(value3);


            for(BigInteger i = borderValue1; 
                i.compareTo(borderValue2) <= 0; 
                i=i.add(new BigInteger("1"))){
                if (receivedSemiPrime.remainder(i).compareTo(zero) == 0){
                    BigInteger anotherFactor = receivedSemiPrime.divide(i);
                    System.out.println("The prime factors is "+i+" and "+anotherFactor);
                    break;
                }
            }

        } while (!value1.equals("Bye"));

        System.out.println("Client connection terminated"); 
        echoSocket.close();
        in.close();        
    }
}
