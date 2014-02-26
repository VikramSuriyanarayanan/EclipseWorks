package packageOne_Arrays;

import java.util.Hashtable;
import java.util.Iterator;

public class DuplicateOccurance {

	private static Hashtable<Integer, Integer> helper = new Hashtable<Integer, Integer>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] a = { 1, 2, 1, 4, 1 };
		System.out.println(findDuplicates(a));

	}

	private static int findDuplicates(int[] a) {

		int max = 0;
		for (int i = 0; i < a.length; i++) {
			int value = 1;
			if (!(helper.contains(a[i]))) {
				helper.put(a[i], value);
			} else {
				value = (helper.get(a[i]));
				value++;
				helper.put(a[i], value);
			}
			
			if(value > max)
				max = value;
		}
		return max;
	}
}
