import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileTransfer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(4321);
        serverSocket.setSoTimeout(500000);
        List<Socket> sockets = new ArrayList<Socket>();
        List<PrintWriter> outChannels = new ArrayList<PrintWriter>();
        List<BufferedReader> inChannels = new ArrayList<BufferedReader>();
        List<String> file = new ArrayList<String>();
        System.out.println("(... expecting connection ...)");

        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                sockets.add(newSocket);
                System.out.println(sockets.size() + " clients connected.");
                PrintWriter out = new PrintWriter(newSocket.getOutputStream(), true);
                outChannels.add(out);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(newSocket.getInputStream()));
                inChannels.add(in);
                String newLine = in.readLine();
                while(newLine!=null){
                    file.add(newLine);
                    System.out.println("received");
                    out.println("Received");
                    out.flush();
                    newLine = in.readLine();
                }                 
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
