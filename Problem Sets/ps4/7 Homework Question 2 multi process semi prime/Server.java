package week5;

import java.net.*;
import java.io.*;
import java.math.BigInteger;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(43201);

        serverSocket.setSoTimeout(100000);

        System.out.println("(... expecting connection ...)");
        Socket clientSocket1 = serverSocket.accept();   
        System.out.println("(... connection with client 1 established ...)");
        Socket clientSocket2 = serverSocket.accept(); 
        System.out.println("(... connection with client 2 established ...)");
        Socket clientSocket3 = serverSocket.accept();   
        System.out.println("(... connection with client 3 established ...)");
        
        ObjectOutputStream out1 =
                new ObjectOutputStream(clientSocket1.getOutputStream());  
        ObjectOutputStream out2 =
                new ObjectOutputStream(clientSocket2.getOutputStream());
        ObjectOutputStream out3 =
                new ObjectOutputStream(clientSocket3.getOutputStream());

        PrintWriter outt1 = new PrintWriter(clientSocket1.getOutputStream(), true); 
        PrintWriter outt2 = new PrintWriter(clientSocket2.getOutputStream(), true);
        PrintWriter outt3 = new PrintWriter(clientSocket3.getOutputStream(), true);                
        
        BufferedReader in1 = new BufferedReader(
                new InputStreamReader(clientSocket1.getInputStream()));
        BufferedReader in2 = new BufferedReader(
                new InputStreamReader(clientSocket2.getInputStream()));
        BufferedReader in3 = new BufferedReader(
                new InputStreamReader(clientSocket3.getInputStream()));
        
        BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
        String semiPrime;
        BigInteger inputSemiPrime;
        BigInteger one = new BigInteger("1");
        BigInteger two = new BigInteger("2");
        BigInteger[] lowers = new BigInteger[3];
        BigInteger[] uppers = new BigInteger[3];

        semiPrime = stdIn.readLine();
        inputSemiPrime = new BigInteger(semiPrime);

        //assign values for the lowers
        lowers[0] = two;
        for(int i=1; i<3; i++){
            lowers[i] = BigInteger.valueOf(i).multiply(inputSemiPrime.divide(BigInteger.valueOf(3)));
        }
        //assign values for the uppers
        uppers[2] = inputSemiPrime.subtract(one);
        for(int i=0; i<2; i++){
            uppers[i] = BigInteger.valueOf(i+1).multiply(inputSemiPrime.divide(BigInteger.valueOf(3)));
        }

        // pass values to clients

        out1.writeObject(inputSemiPrime);
        out1.writeObject(lowers[0]);
        out1.writeObject(uppers[0]);
        out1.flush();

        out2.writeObject(inputSemiPrime);
        out2.writeObject(lowers[1]);
        out2.writeObject(uppers[1]);
        out2.flush();

        out3.writeObject(inputSemiPrime);
        out3.writeObject(lowers[2]);
        out3.writeObject(uppers[2]);
        out3.flush();

        // if found, stop other clients
        while(true){
            try{
                if(in1.readLine().equals("Found")){
                    outt1.println("stop");
                    outt1.flush();
                    System.out.println("out1");
                    outt2.println("stop");
                    outt2.flush();
                    System.out.println("out2");
                    outt3.println("stop");
                    outt3.flush();
                    System.out.println("out3");
                    break;
                }
                else if(in2.readLine().equals("Found")){
                    outt1.println("stop");
                    outt1.flush();
                    System.out.println("out1");
                    outt2.println("stop");
                    outt2.flush();
                    System.out.println("out2");
                    outt3.println("stop");
                    outt3.flush();
                    System.out.println("out3");
                    break;
                }
                else if(in3.readLine().equals("Found")){
                    outt1.println("stop");
                    outt1.flush();
                    System.out.println("out1");
                    outt2.println("stop");
                    outt2.flush();
                    System.out.println("out2");
                    outt3.println("stop");
                    outt3.flush();
                    System.out.println("out3");
                    break;
                }
            }catch (java.net.SocketTimeoutException e){
                System.out.println(e);
                break;
            }  
        }

        String inputLine1 = in1.readLine();
        System.out.println("Message from client 1: "+inputLine1);
        String inputLine2 = in2.readLine();
        System.out.println("Message from client 2: "+inputLine2);
        String inputLine3 = in3.readLine();
        System.out.println("Message from client 3: "+inputLine3);

        System.out.println("Server connection terminated");   
        serverSocket.close();
        clientSocket1.close();
        clientSocket2.close();
        clientSocket3.close();
        out1.close();
        out2.close();
        out3.close();
        in1.close();
        in2.close();
        in3.close();
    }
}