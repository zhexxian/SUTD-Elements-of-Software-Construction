class MultiThreadSearch extends Thread{  
	private int id; 
	int[] searchArray = getArray(1000);
	int number = 30;
	int upper;
	int lower;
	static boolean foundNumber = false;

	public int[] getArray(int element){
		int[] output = new int[element];
		int counter = 0;
		for (int i=0; i<element; i++){
			output[i] = counter;
			counter++;
		}
		return output;
	}

	public MultiThreadSearch (int id, int upper, int lower) {
		this.id = id; 
		this.upper = upper;
		this.lower = lower;
	}
	
	public void run(){  
		for(int i=upper;i<lower;i++){  
			if (searchArray[i]==number){
				foundNumber = true;
				System.out.println("number "+number+" is found");
			}
			if(isInterrupted()){  
				System.out.println("thread " + id + " interrupted");  
				break;
			}
		}
	}  
  
	public static void main(String args[]){  
  

		MultiThreadSearch t1=new MultiThreadSearch(1, 0, 500);    
		MultiThreadSearch t2=new MultiThreadSearch(2, 500, 1000);    
		t1.start();  
		t2.start();
		while(foundNumber==false){
			System.out.println("thread 1 is interrupted? " + t1.isInterrupted());
			System.out.println("thread 2 is interrupted? " + t2.isInterrupted());	
			System.out.println("thread 1 is alive? " + t1.isAlive());
			System.out.println("thread 2 is alive? " + t2.isAlive());	
		}
		t2.interrupt();
		t1.interrupt();  	
		System.out.println("thread 1 is interrupted? " + t1.isInterrupted());
		System.out.println("thread 2 is interrupted? " + t2.isInterrupted());	
		System.out.println("thread 1 is alive? " + t1.isAlive());
		System.out.println("thread 2 is alive? " + t2.isAlive());	
	}  
}  