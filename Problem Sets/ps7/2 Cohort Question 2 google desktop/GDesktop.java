/*Cohort Question 2: 
Analyze class GDesktopProb.java which implements the skeleton of 
Google Desktop, and fix potential problems with BlockingQueue. */

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.*;

public class GDesktop {
	private final static int N_CONSUMERS = 4;
	private final static int BOUND = 5;
	//it starts here
	public static void startIndexing (File[] roots) {
		//change Queue to BlockingQueue
		//Queue<File> queue = new LinkedList<File>();
		BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {return true;}
		};
		
		for (File root : roots) {
			(new FileCrawler(queue, filter, root)).start();;
		}
		
		for (int i = 0; i < N_CONSUMERS; i++) {
			(new Indexer(queue)).start();
		}
	}
}

class FileCrawler extends Thread {
	//change Queue to BlockingQueue
	//private final Queue<File> fileQueue; 
	private final BlockingQueue<File> fileQueue;
	private final FileFilter fileFilter; 	
	private final File root;
	
	// FileCrawlerProb (Queue<File> queue, FileFilter filter, File root) {
	// 	this.fileQueue = queue;
	// 	this.fileFilter = filter;
	// 	this.root = root;
	// }
	FileCrawler (BlockingQueue<File> queue, FileFilter filter, File root) {
		this.fileQueue = queue;
		this.fileFilter = filter;
		this.root = root;
	}
	
	public void run() {
		try {
			crawl(root);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	private void crawl(File root) throws InterruptedException {
		File[] entries = root.listFiles(fileFilter);
		
		if (entries != null) {
			for (File entry : entries) {
				if (entry.isDirectory()) {
					crawl(entry);
				}
				else {
					//fileQueue.offer(entry);	
					fileQueue.put(entry);
				}
			}
		}
	}
}

class Indexer extends Thread {
	//change Queue to BlockingQueue
	//private final Queue<File> queue;
	private final BlockingQueue<File> queue;
	
	// public IndexerProb (Queue<File> queue) {
	// 	this.queue = queue;
	// }
	public Indexer (BlockingQueue<File> queue) {
		this.queue = queue;
	}
	
	public void run() {
		try {
			while (true) {
				//indexFile(queue.poll());
				indexFile(queue.take());
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} 
		
	}

	private void indexFile(File file) {
		// code for analyzing the context of the file is skipped for simplicity	
	}
}