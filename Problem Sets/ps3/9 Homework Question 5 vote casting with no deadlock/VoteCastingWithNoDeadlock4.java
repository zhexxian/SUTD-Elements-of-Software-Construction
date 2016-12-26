import java.net.*;
import java.io.*;

// circular wait in deadlock situation is solved by having a fixed order of talking

public class VoteCastingWithNoDeadlock4 {
    public static void main(String[] args) throws Exception {

        String[] votes = new String[5];

    // voter#1's turn, join voter#1's server

        String hostName = "localhost";
        int portNumber = 22221;
 
        Socket echoSocket = new Socket(hostName, portNumber);
        BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
        String fromVoter1 = in.readLine();
        System.out.println(fromVoter1); 
        votes[0] = fromVoter1.substring(19);
        echoSocket.close();
        in.close();

    // voter#2's turn, join voter#2's server

        portNumber = 22222;
 
        echoSocket = new Socket(hostName, portNumber);
        in = new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
        String fromVoter2 = in.readLine();
        System.out.println(fromVoter2); 
        votes[1] = fromVoter2.substring(19);
        echoSocket.close();
        in.close();

    // voter#3's turn, join voter#3's server

        portNumber = 22223;
 
        echoSocket = new Socket(hostName, portNumber);
        in = new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
        String fromVoter3 = in.readLine();
        System.out.println(fromVoter3); 
        votes[2] = fromVoter3.substring(19);
        echoSocket.close();
        in.close();

    // voter#4 (self)'s turn

        ServerSocket serverSocket = new ServerSocket(22224);
        System.out.println("(... expecting connection ...)");
        Socket clientSocket1 = serverSocket.accept();   
        System.out.println("(... connection with client 1 established ...)");
        Socket clientSocket2 = serverSocket.accept(); 
        System.out.println("(... connection with client 2 established ...)");
        Socket clientSocket3 = serverSocket.accept();   
        System.out.println("(... connection with client 3 established ...)");
        Socket clientSocket4 = serverSocket.accept();   
        System.out.println("(... connection with client 4 established ...)");
        
        PrintWriter out1 =
                new PrintWriter(clientSocket1.getOutputStream(), true);  
        PrintWriter out2 =
                new PrintWriter(clientSocket2.getOutputStream(), true);
        PrintWriter out3 =
                new PrintWriter(clientSocket3.getOutputStream(), true);  
        PrintWriter out4 =
                new PrintWriter(clientSocket4.getOutputStream(), true);              
        
        BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
        String vote;

        System.out.println("Welcome to vote. Please enter 'A' or 'B' to vote for the candidates: ");

        do {
            vote = stdIn.readLine();
            if (!isValidVote(vote))
            {
                System.out.println("That is not a valid vote. Please enter 'A' or 'B': ");
            }
        } while (!isValidVote(vote));

        votes[3] = vote;

        System.out.println("You voted for " + vote);
        out1.println("Voter #4 voted for " + vote);
        out1.flush();
        out2.println("Voter #4 voted for " + vote);
        out2.flush();
        out3.println("Voter #4 voted for " + vote);
        out3.flush();
        out4.println("Voter #4 voted for " + vote);
        out4.flush();

        System.out.println("Server connection terminated");   
        serverSocket.close();
        clientSocket1.close();
        clientSocket2.close();
        clientSocket3.close();
        clientSocket4.close();
        out1.close();
        out2.close();
        out3.close();
        out4.close();

    // voter#5's turn, join voter#5's server

        portNumber = 22225;
 
        echoSocket = new Socket(hostName, portNumber);
        in = new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
        String fromVoter5 = in.readLine();
        System.out.println(fromVoter5); 
        votes[4] = fromVoter5.substring(19);
        echoSocket.close();
        in.close();

        // display voting result

        int countA = 0;
        for(String v:votes){
            if(v.equals("A")){
                countA++;
            }
        }
        if (countA>2) {
            System.out.println("Candidate A won!");
        } else {
            System.out.println("Candidate A won!");
        }

    }
    public static boolean isValidVote(String vote) {
        if(vote.equalsIgnoreCase("A")){
            return true;
        }
        else if (vote.equalsIgnoreCase("B")){
            return true;
        }
        return false;
    }
}
