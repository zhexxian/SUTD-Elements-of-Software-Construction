 import java.lang.StringBuffer;

public class ABC {
	public static void main(String args[]){
		// initiate with char letter A --> charValue 65
		StringBuffer stringBuffer = new StringBuffer(String.valueOf( (char) (65)));	

		LetterPrinter lp1 = new LetterPrinter(stringBuffer);
		LetterPrinter lp2 = new LetterPrinter(stringBuffer);
		LetterPrinter lp3 = new LetterPrinter(stringBuffer);

		lp1.start();
		lp2.start();
		lp3.start();

		try {
			lp1.join();
			lp2.join();
			lp3.join();
		} catch (InterruptedException e) {
			System.out.println("some thread is not finished");
		}	
	}
}

class LetterPrinter extends Thread{
	StringBuffer stringBuffer;

	LetterPrinter (StringBuffer stringBuffer){
		this.stringBuffer = stringBuffer;
	}

	@Override
	public void run(){
		synchronized(stringBuffer){
			for(int i=0; i<100; i++){
		       	System.out.println(stringBuffer.toString());
			}
			stringBuffer.setCharAt(0,(char)(stringBuffer.charAt(0)+1));
		}	
	}
}