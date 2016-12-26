import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChatRoom2 {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(43211);
        serverSocket.setSoTimeout(10000);
        List<Socket> sockets = new ArrayList<Socket>();
        List<BufferedReader> inChannels = new ArrayList<BufferedReader>();
        System.out.println("(... expecting connection for 10 seconds ...)");

        // there are 10 seconds for clients to join the chat room
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                sockets.add(newSocket);
                System.out.println(sockets.size() + " clients connected.");
                inChannels.add(new BufferedReader(
                    new InputStreamReader(newSocket.getInputStream())));                   
            }
            catch (java.net.SocketTimeoutException e) {
                System.out.println("Timed out, chat room closed for new connections. Now start chatting!");
                break;
            }           
        }

        //every client has 5 seconds to chat

        int n = sockets.size();
        int count = 0;
        while (count<n) {
            try { 
                sockets.get(0).setSoTimeout(5000);
                String newChat = inChannels.get(0).readLine();
                System.out.println(newChat);
                count++;
                sockets.remove(0);  
                inChannels.remove(0);
                continue;
            }
            catch (java.net.SocketTimeoutException e) {
                System.out.println("5 seconds' up, next client's turn."); 
                count++;
                sockets.remove(0);  
                inChannels.remove(0);
            }
        }                    
                                    
        System.out.println("Every client has talked, now closing chat room."); 
        serverSocket.close();
    }
}
