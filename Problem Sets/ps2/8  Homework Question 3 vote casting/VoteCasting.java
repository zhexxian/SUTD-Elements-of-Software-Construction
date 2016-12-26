import java.util.Scanner;
import java.util.ArrayList;

public class VoteCasting {

	static Scanner sc = new Scanner(System.in);
	static int countA = 0;
	static int countB = 0;
	static ArrayList<String> voteList = new ArrayList<String>();

	public static void main(String[] args){
		String prompt = "Welcome to vote. Please enter 'A' or 'B' to vote for the candidates: ";
		boolean votingNotCompleted = true;

			while (votingNotCompleted)
		{

			String newVote = getVote(prompt);
			updateVoteCount(newVote);
			displayVoteCount();
			if(isVotingCompleted()==true){
				votingNotCompleted=false;
				if (countA>countB){
					System.out.println("Candidate A won!");
				}
				else {
					System.out.println("Candidate B won!");
				}
				
			}
		}	

	}

	public static String getVote(String prompt) {
		String vote;
		System.out.print(prompt);
		do
		{
			vote = sc.nextLine();
			if (!isValidVote(vote))
			{
				System.out.println("That is not a valid vote.");
			}
		} while (!isValidVote(vote));
		return vote;
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

	public static void updateVoteCount(String vote) {
		if (vote.equalsIgnoreCase("A")){
			countA++;
			voteList.add("A");
		}
		else if (vote.equalsIgnoreCase("B")){
			countB++;
			voteList.add("B");
		}
	}

	public static void displayVoteCount(){
		System.out.println("A: "+countA+" votes;\nB: "+countB+" votes.");
	}

	public static boolean isVotingCompleted() {
		if(voteList.size()>=5){
			return true;
		}
		return false;
	}
}

