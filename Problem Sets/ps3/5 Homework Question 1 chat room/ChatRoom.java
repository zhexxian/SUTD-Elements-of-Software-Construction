import java.net.*;
import java.io.*;

public class ChatRoom {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(43211);
        serverSocket.setSoTimeout(5000000);
        System.out.println("(... welcome to the chat room ...)");

        //the following is a pattern of busy-waiting.
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                System.out.println("Client is typing...");
                BufferedReader newReader = new BufferedReader(
                    new InputStreamReader(newSocket.getInputStream()));
                String newOutput = newReader.readLine();
                System.out.println(newOutput);                 
            }
            catch (java.net.SocketTimeoutException e) {
                System.out.println("Timed out.");
                break;
            }           
        }                   

        System.out.println("Server connection terminated");   
        serverSocket.close();
    }
}
