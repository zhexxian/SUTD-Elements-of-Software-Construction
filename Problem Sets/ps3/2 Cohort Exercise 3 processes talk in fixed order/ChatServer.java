import java.net.*;
import java.io.*;

public class ChatServer {
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
        
        BufferedReader in1 = new BufferedReader(
                new InputStreamReader(clientSocket1.getInputStream()));
        BufferedReader in2 = new BufferedReader(
                new InputStreamReader(clientSocket2.getInputStream()));
        BufferedReader in3 = new BufferedReader(
                new InputStreamReader(clientSocket3.getInputStream()));
        
        BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
        String inputLine;
        
        do {
            inputLine = stdIn.readLine();
            String inputLine1 = in1.readLine();
            System.out.println("Message from client 1 is received");
            String inputLine2 = in2.readLine();
            System.out.println("Message from client 1 is received");
            String inputLine3 = in3.readLine();
            System.out.println("Message from client 1 is received");
            System.out.println("Client 1 says:" + inputLine1);
            System.out.println("Client 2 says:" + inputLine2);
            System.out.println("Client 3 says:" + inputLine3);
        }while (!inputLine.equals("Bye"));
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
