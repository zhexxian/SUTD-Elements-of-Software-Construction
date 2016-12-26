public class BadThreads { 
	static String message; 

	private static class CorrectorThread extends Thread { 
	 	public void run() { 
	  	 	// try { 
	  	 	//  	//sleep(1000); 
	  	 	// } catch (InterruptedException e) {
	  	 	// 	System.out.println(e);
	  	 	// } 
	  	 	// Key statement 1: 
  	 	 	message = "Mares do eat oats."; 
  	 	}
	}

	public static void main(String args[])  throws InterruptedException {
		CorrectorThread newThread = new CorrectorThread(); 
	  	
	  	newThread.start(); 
	  	
	  	message = "Mares do not eat oats."; 

	  	
        synchronized (newThread) {
            newThread.wait();
        }

        synchronized (newThread) {
            newThread.notify();
        }
        
	  	//Thread.sleep(2000); 
	  	// Key statement 2: 
	  	System.out.println(message); 
	}
}