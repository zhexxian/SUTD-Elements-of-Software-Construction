/*
Cohort Question 5: 
Fix the minor problem on the previous slide using ConcurrentMap.putIfAbsent(). 
Notice that you can’t apply client-side locking on ConcurrentMap.  
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CacheV3 {

	private final ConcurrentHashMap<Integer, Future<List<Integer>>> results = new ConcurrentHashMap<Integer, Future<List<Integer>>>(); //the last factors must be the factors of the last number

	public List<Integer> service (final int input) throws Exception {
		Future<List<Integer>> f = results.get(input);
				
		if (f == null) {
			Callable<List<Integer>> eval = new Callable<List<Integer>>() {
				public List<Integer> call () throws InterruptedException {
					return factor(input);
				}
			};
			
			FutureTask<List<Integer>> ft = new FutureTask<List<Integer>>(eval);
			f = results.putIfAbsent(new Integer(input), ft);
			if(f==null){
				ft.run();
				return ft.get();
			}						
		}

		return f.get();
	}

	public List<Integer> factor(int n) {		
		List<Integer> factors = new ArrayList<Integer>();
		for (int i = 2; i <= n; i++) {
			while (n % i == 0) {
		        factors.add(i);
		        n /= i;
		    }
		}
		
		return factors;
	}
}