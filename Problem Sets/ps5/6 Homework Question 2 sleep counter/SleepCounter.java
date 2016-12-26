// Analysis: since the program cannot make any assumption on when the user will press the key,
// it should be constantly checking for console input

import java.io.*;

public class SleepCounter {

	public static void main(String args[]){ 

		// read console input
		BufferedReader stdIn =
            new BufferedReader(
                new InputStreamReader(System.in));

		SleepCounterThread thread = new SleepCounterThread();
		thread.start();
		try{
			if (stdIn.readLine().equals("0")) {
				thread.interrupt();	
			} 
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		try {
			thread.join();
		} catch (InterruptedException e) {
			System.out.println("some thread is not finished");
		}  	 
	}
}

class SleepCounterThread extends Thread {
	// initial counter value
	int start = 0;

	public void run(){
		while(true) {
			if (this.isInterrupted()){
				System.out.println("Stop");
				break;
			}
			try {
				System.out.println(String.valueOf(start));
				start++;
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// Auto-generated catch block
				e.printStackTrace();
				if(e instanceof InterruptedException) {
	                // just in case this Runnable is actually called directly,
	                // rather than in a new thread, don't want to swallow the
	                // flag:
	                Thread.currentThread().interrupt();
	            }
			} 
		}
	}
}

