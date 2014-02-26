/**
 * 
 */
package packageOne_Arrays;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

public class ClassForDuplicates {

	public static void main(String[] args) {
		int[] arrayA = {1,2,3,4,5,6,1,3};
		System.out.println(getDuplicatesByHashTable(arrayA));
	}

	public static boolean getDuplicatesByHashTable(int array[]) {
		Hashtable<Integer,Integer> hashTableCount = new Hashtable<Integer,Integer>();
		
		for(int i:array){
			int count = 0;
			if(!(hashTableCount.containsKey(i))) {
				hashTableCount.put(i,1);
			}
			else {
				count = hashTableCount.get(i)+1;
				hashTableCount.put(i, count);
			}
		}
			System.out.println(hashTableCount);
			Iterator<Entry<Integer, Integer>> it = hashTableCount.entrySet().iterator();
			while(it.hasNext()) {
				System.out.println(it.next());
			}
			
		return false;
	}
}
