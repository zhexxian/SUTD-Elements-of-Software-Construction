import java.io.*;
import java.net.*;

public class ChatRoomClient2 {
    public static void main(String[] args) throws Exception {
        String hostName = "localhost";
        //String hostName = "fe80::7517:c1af:b2bb:da73%4";
        int portNumber = 43211;
        Socket echoSocket = new Socket(hostName, portNumber);

        System.out.println("(... connected to chat room. please talk within 5 seconds ...)");

        //the following is a pattern of busy-waiting.

        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true); 
        BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
       String userInput;
        do {
            userInput = stdIn.readLine();
            out.println(userInput);
            out.flush();
        } while (!userInput.equals("Bye"));
        System.out.println("Done chatting"); 
        echoSocket.close();
        out.close();
        stdIn.close();           
    }
}

