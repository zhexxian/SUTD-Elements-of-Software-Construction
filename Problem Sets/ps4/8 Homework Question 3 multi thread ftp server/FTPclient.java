import java.io.*;
import java.net.*;

public class FTPclient {
    public static void main(String[] args) throws Exception {
         String hostName = "localhost";
        //String hostName = "fe80::7517:c1af:b2bb:da73%4";
        int portNumber = 43211;

        Socket echoSocket = new Socket(hostName, portNumber);

        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));


        // The name of the file to open.
        String fileName = "C:\\Users\\zhexian\\workspace\\week4\\src\\week4\\text.txt";
        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            boolean ok = true;

            while(ok==true&&(line = bufferedReader.readLine()) != null){
                try {
                    ok = false;
                    out.println(line);
                    while(!in.readLine().equals("Received")){
                        System.out.println("nope");
                    }
                    System.out.println("server has received the line");
                    ok = true;
                } catch (java.net.SocketTimeoutException e) 
                {
                    out.println(line);
                    out.flush();
                }            
            }
            System.out.println("File has successfully transferred.");
            // out.println("******Bye******");
            // out.flush();
            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
            ex.printStackTrace();
        }

           
        System.out.println("Client connection terminated"); 
        in.close();
        out.close();
        echoSocket.close();       
    }
}
