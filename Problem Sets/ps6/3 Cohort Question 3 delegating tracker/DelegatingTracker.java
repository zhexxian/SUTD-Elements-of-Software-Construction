/*Cohort Question 3: 
Continue cohort question 2. Examine the modified class DelegatingTracker.java 
and discuss whether it is thread-safe or not and fix it if it is not. */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap; //this is thread-safe!
import java.util.concurrent.ConcurrentMap; //this is thread-safe!

public class DelegatingTracker {
	private final ConcurrentMap<String, Point> locations; //@guarded by 'final'
	
	//no escape due to deepcode
	public DelegatingTracker(Map<String, Point> locations) {
		this.locations = new ConcurrentHashMap<String, Point>(locations);
	}
	
	//@guarded by 'this'
	//no escape due to deepcode with unmodifiableMap
	public synchronized Map<String, Point> getLocations () {
		return Collections.unmodifiableMap(new HashMap<String, Point>(locations));
	}
	
	//this is an escape, as a pointer to Point in locations is published
	public synchronized Point getLocation (String id) {
		//to prevent the escape, use deepcode
		Point localVariable = new Point(locations.get(id));
		//return locations.get(id);
		return localVariable;
	}
	
	public synchronized void setLocation (String id, int x, int y) {		
		if (!locations.containsKey(id)) {
			throw new IllegalArgumentException ("No such ID: " + id);			
		}
		
		locations.get(id).set(x, y);
	}
	
	//a Point object is mutable but only when x and y are changed at the same time
	class Point {
		private int x, y;
		
		private Point (int[] a) { 
			this(a[0], a[1]);
		}
		
		public Point (Point p) {
			this(p.get());
		}
		
		public Point (int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		//the public get() is not thread safe, as x and y may be changed through int[] pointer
		// public synchronized int[] get() {
		// 	return new int[] {x, y};
		// }
		//change it to private instead
		private synchronized int[] get() {
			return new int[] {x, y};
		}
		
		public synchronized void set(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
