import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FTPserver {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(43211);
        serverSocket.setSoTimeout(10000);
        List<Socket> sockets = new ArrayList<Socket>();
        List<PrintWriter> outChannels = new ArrayList<PrintWriter>();
        List<BufferedReader> inChannels = new ArrayList<BufferedReader>();
        System.out.println("(... expecting connection for 10 seconds ...)");

        //the following is a pattern of busy-waiting.
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                sockets.add(newSocket);
                System.out.println(sockets.size() + " clients connected.");
                inChannels.add(new BufferedReader(
                    new InputStreamReader(newSocket.getInputStream())));  
                outChannels.add(new PrintWriter(newSocket.getOutputStream(), true));                 
            }
            catch (java.net.SocketTimeoutException e) {
                System.out.println("Timed out, server closed for new connections. Now start file transferring!");
                break;
            }           
        }

        // create seperate thread for each FTP client

        FTPthread[] ftpThreads = new FTPthread[sockets.size()];

        // initiating and starting threads
		for(int i=0; i<sockets.size(); i++){
            ftpThreads[i] = new FTPthread(i+1, inChannels.get(i), outChannels.get(i));
            ftpThreads[i].start();
        }

        // join threads
		try {
			for(int i=0; i<sockets.size(); i++){
	            ftpThreads[i].join();
	        }
		}
		catch (InterruptedException e) {
			System.out.println("A thread didn't finish!");
		}
		catch (Exception e) {
			System.out.println("Exception captured.");
		}

		System.out.println("All files have been successfully transferred!");             

        System.out.println("Server connection terminated");   
        serverSocket.close();
    }
}

// Thread class
class FTPthread extends Thread {
	private int id;
	private BufferedReader in;
	private PrintWriter out;
	
	public FTPthread (int id, BufferedReader in, PrintWriter out) {
		this.id = id;
		this.in = in;
		this.out = out;
	}
	
	public void run () {
		File file = new File("client " + id + " file.txt");
		BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter(file));
			String newLine = in.readLine();
	        while(newLine!=null){
	            output.write(newLine);
	            out.println("Received");
	            out.flush();
	            newLine = in.readLine();
	        }
	        output.close();  
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}