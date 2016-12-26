/*
Cohort Question 4: 
Given an (large) array of strings (of grades), write a multi-threaded program, 
using CountDownLatch, to check whether the array contains 7 'F'. Stop all threads 
as soon as possible. 
*/

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class CountDownLatchExercise {

	public static ArrayList<String> arrayOfGrades = new ArrayList<String>(){{
		add("A");
		add("B");
		add("C");
		add("D");
		add("E");
		add("F");
		add("A");
		add("B");
		add("B");
		add("F");
		add("F");
		add("F");
		add("F");
		add("F");
		add("F");
		add("A");
		add("B");
	}};

	public static void main(String args[]) {
	    final CountDownLatch latch = new CountDownLatch(7); //check for 7 'F's
	    Thread thread1 = new Thread(new Service(new ArrayList<String>(arrayOfGrades.subList(0,6)), latch));
	    Thread thread2 = new Thread(new Service(new ArrayList<String>(arrayOfGrades.subList(6,11)), latch));
	    Thread thread3 = new Thread(new Service(new ArrayList<String>(arrayOfGrades.subList(11,17)), latch));
	      
	    thread1.start();
	    thread2.start();
	    thread3.start();
	      	      
	    try{
	        latch.await();  //main thread is waiting on CountDownLatch to finish
	        System.out.println("found 7 'F's");
	        thread1.interrupt(); 
	    	thread2.interrupt();
	    	thread3.interrupt();
	    	System.out.println("threads are interrupted");
	    }catch(InterruptedException ie){
	        ie.printStackTrace();
	    }  
	}	  
}

class Service implements Runnable{
	private final CountDownLatch latch;
	private ArrayList<String> arrayOfGrades;
	  
	public Service(ArrayList<String> arrayOfGrades, CountDownLatch latch){
	    this.arrayOfGrades = arrayOfGrades;
	    this.latch = latch;
	}
	  
	public void run() {
	    for(String grade:arrayOfGrades){
	    	if (Thread.currentThread().isInterrupted()){
	    		System.out.println("thread is interrupted");
	    		break;
	    	}
	    	else if (latch.getCount()==0){
	    		Thread.currentThread().interrupt();
	    	}
	    	else if (grade.equals("F")){
	    		latch.countDown(); //reduce count of CountDownLatch by 1
	    	}
	    }
	}  
}
