/*
Cohort Question 6: 
Write a program to simulate the following. During exam, the examiner (a thread) 
who waits patiently for a known number of students (one thread for each student) 
to get ready; the exam starts when all students are ready, and at the same time 
for all. Afterwards, the examiner patiently waits for all students to hand in their 
scripts. Students are free to leave once they hand in the exam. Once the last 
student hands in the exam (or the time limit expires), the examiner stops waiting 
and leaves with the scripts.   
*/

import java.util.concurrent.Phaser;

public class Examiner {
	public static void main (String[] args) throws InterruptedException {
		Phaser phaser = new Phaser();
		phaser.register();
		
		Thread student1 = new Student(phaser,"student 1");
		Thread student2 = new Student(phaser,"student 2");
		Thread student3 = new Student(phaser,"student 3");

		
		student1.start();
		student2.start();
		student3.start();
		
		phaser.arriveAndDeregister();
		System.out.println("All students are ready. Exam starts.");
		phaser.register();
		phaser.arriveAndDeregister();
		System.out.println("All students have handed in the scripts.");

		
	}
}

// class Examiner extends Thread {

// 	private final Phaser phaser;

// 	public Examiner(final Phaser phaser){
// 		this.phaser = phaser;
// 	}

// 	public void run() {
// 		phaser.register();
// 		phaser.arriveAndAwaitAdvance();//threads register arrival to the phaser.
// 		System.out.println("All students are ready. Exam starts.");
// 		collect();

// 	}
// 	public void collect() {
// 		phaser.arriveAndAwaitAdvance();//threads register arrival to the phaser.
// 		System.out.println("All students have handed in the scripts.");
// 	}
// }

class Student extends Thread {

	private final Phaser phaser;
	private final String name;

	public Student(final Phaser phaser, final String name){
		this.phaser = phaser;
		this.name = name;
	}

	public void run() {
		phaser.register();
		//System.out.println(name+" is ready for exam");
		phaser.arriveAndAwaitAdvance();//threads register arrival to the phaser.
		submit();	
	}

	public void submit() {
		//System.out.println(name+" has handed in the script");
		phaser.arriveAndAwaitAdvance();//threads register arrival to the phaser.
	}
}