/**
 * 
 */
package packageOne_Arrays;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
/**
 * Class to find duplicates. Given an array, find if duplicates exists in array. 
 * 
 * 
 * @return true If Duplicates doesnt exist, False if Duplicates exists.
 * @author Remoz World
 *
 */
public class C1_ClassForDuplicates {

	public static void main(String[] args) {
		int[] arrayA = { 1, 2, 3, 4, 5, 6, 1, 3 };
		System.out.println("INPUT: " + Arrays.toString(arrayA));
		System.out.println("OUTPUT: The input doesn't contain Duplicates, True or False? " +getDuplicatesByHashTable(arrayA));
	}

	public static boolean getDuplicatesByHashTable(int array[]) {
		LinkedHashMap<Integer, Integer> hashTableCount = new LinkedHashMap<Integer, Integer>();

		for (int i : array) {
			int count = 0;
			if (!(hashTableCount.containsKey(i))) {
				hashTableCount.put(i, 1);
			} else {
				count = hashTableCount.get(i) + 1;
				hashTableCount.put(i, count);
			}
		}
		System.out.println(hashTableCount);
		Iterator<Entry<Integer, Integer>> it = hashTableCount.entrySet()
				.iterator();
		while (it.hasNext()) {
			if(it.next().getValue() != 1)
				return false;
		}

		return true;
	}
}
