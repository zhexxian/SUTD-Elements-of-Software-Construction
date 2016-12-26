import java.io.*;
import java.util.Scanner;


public class FileLineCounter{

	public static void main(String[] args) throws FileNotFoundException{
		int count;
		int index = 1;
		try {
			for (String file:args){
			count = countLines(file);
			System.out.println(index+" "+file+": "+count+" lines");
			index++;
			}
		} catch (FileNotFoundException e){
			System.out.println(e);
		}
		
	}

	static int countLines(String fileName) throws FileNotFoundException{
		try {
			int count = 0;
			File file = new File(fileName);
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {    
	    		count++;
	    		sc.nextLine();
			}
			return count;
		}  catch (FileNotFoundException e){
			System.out.println(e);
			return -1;
		}
	}
}