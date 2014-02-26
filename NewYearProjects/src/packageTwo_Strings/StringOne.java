package packageTwo_Strings;

import java.util.Arrays;

public class StringOne {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s1 = "helloh";
		int [] a = {1,2,3};
		int [] b = (int[])a.clone();
		if (a == b) 
			b[0]++;
		else
		System.out.println(b[0]);
		System.out.println((int)'a');
		System.out.println(s1.compareTo("hell"));
	}

}
