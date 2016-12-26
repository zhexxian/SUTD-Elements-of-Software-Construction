import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws Exception {
        String hostName = "localhost";
        //String hostName = "fe80::7517:c1af:b2bb:da73%4";
        int portNumber = 4321;
 
        Socket echoSocket = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
        BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
        String userInput;
        do {
            userInput = stdIn.readLine();
            out.println(userInput);
            out.flush();
        } while (!userInput.equals("Bye"));
        System.out.println("Client connection terminated"); 
        echoSocket.close();
        in.close();
        out.close();
        stdIn.close();           
    }
}
