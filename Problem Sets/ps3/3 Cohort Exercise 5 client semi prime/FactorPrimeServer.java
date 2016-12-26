import java.net.*;
import java.io.*;
import java.math.BigInteger;

public class FactorPrimeServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(4321);
        System.out.println("(... expecting connection ...)");
        Socket clientSocket1 = serverSocket.accept();   
        System.out.println("(... connection with client 1 established ...)");
        Socket clientSocket2 = serverSocket.accept(); 
        System.out.println("(... connection with client 2 established ...)");
        Socket clientSocket3 = serverSocket.accept();   
        System.out.println("(... connection with client 3 established ...)");
        
        PrintWriter out1 =
                new PrintWriter(clientSocket1.getOutputStream(), true);  
        PrintWriter out2 =
                new PrintWriter(clientSocket2.getOutputStream(), true);
        PrintWriter out3 =
                new PrintWriter(clientSocket3.getOutputStream(), true);                 
        
        BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
        String inputLine;
        BigInteger inputSemiPrime;
        BigInteger two;
        BigInteger borderValue1;
        BigInteger borderValue2;
        do {
            inputLine = stdIn.readLine();
            inputSemiPrime = new BigInteger(inputLine);
            two = new BigInteger("2");
            borderValue1 = inputSemiPrime.divide(BigInteger.valueOf(2));
            borderValue2 = borderValue1.add(borderValue1);

            out1.println(inputSemiPrime);
            out1.println(two);
            out1.println(borderValue1);
            out1.flush();

            out2.println(inputSemiPrime);
            out2.println(borderValue1);
            out2.println(borderValue2);
            out2.flush();

            out3.println(inputSemiPrime);
            out3.println(borderValue2);
            out3.println(inputSemiPrime.subtract(BigInteger.valueOf(1)));
            out3.flush();

        } while (!inputSemiPrime.equals("Bye"));

        System.out.println("Server connection terminated");   
        serverSocket.close();
        clientSocket1.close();
        clientSocket2.close();
        clientSocket3.close();
        out1.close();
        out2.close();
        out3.close();
    }
}
