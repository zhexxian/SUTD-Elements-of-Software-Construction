/*Cohort Question 2:  
Assume a taxi tracking system which tracks taxis in Singapore. 
The updater threads would modify taxi locations and the view 
thread would fetch the locations of the taxis and display them. 
Examine the given Tracker.java class (shared by the updater 
threads and view thread) to determine if it is thread-safe 
and find a way to fix it if it is not thread-safe. */

import java.util.Map;

public class TrackerFixed {
	//@guarded by 'final'
	//this is an escape, as although the reference is 'final'
	//it's content may still be changed by changing content of the Map
	private final Map<String, MutablePoint> locations;
	
	//the reference locations is going to be an escape too,
	//as it creates a reference to the location object(itself an escape) and can be changed
	public Tracker(Map<String, MutablePoint> locations) {
		//use deepcoding to remove the pointers
		Set<String> keySet = locations.keySet();
		Map<String, MutablePoint> localVariable = new Map<String, MutablePoint>();
		for(String key : keySet) {
			localVariable.put(key, locations.get(key));
		}
		this.locations = localVariable;
	}
	
	//this is an escape, as it returns a reference to the location object(itself an escape) 
	//which may be altered
	//@guarded by 'this'
	public synchronized Map<String, MutablePoint> getLocations () {
		//use deepcoding to remove the pointers
		Set<String> keySet = locations.keySet();
		Map<String, MutablePoint> localVariable = new Map<String, MutablePoint>();
		for(String key : keySet) {
			localVariable.put(key, locations.get(key));
		}
		return localVariable;
	}
	
	//this is an escape, as one can change the locations object by changing MutablePoint loc
	//@guarded by 'this'
	public synchronized MutablePoint getLocation (String id) {
		//use deepcoding to remove the pointers
		//MutablePoint loc = locations.get(id);
		int localX = locations.get(id).x;
		int localY = locations.get(id).y;
		MutablePoint loc = new MutablePoint(localX, localY);
		return loc;
	}
	
	//@guarded by 'this'
	//There is no escape as the method does not return a reference to object,
	//and the parameters are all of primitive types
	public synchronized void setLocation (String id, int x, int y) {
		MutablePoint loc = locations.get(id);
		
		if (loc == null) {
			throw new IllegalArgumentException ("No such ID: " + id);			
		}
		
		loc.x = x;
		loc.y = y;
	}
	
	/*this class is not thread-safe 
	(why? -- because x and y are public they can be changed anytime) 
	and keep it unmodified.*/
	class MutablePoint {
		public int x, y;
		
		public MutablePoint (MutablePoint p) {
			this.x = p.x;
			this.y = p.y;
		}
	}
}
