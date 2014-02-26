package packageTwo_Strings;

import java.util.regex.Pattern;

public class StringReplace {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String input = "Hello World";
		System.out.println(stringReplacement(input));
	}

	private static String stringReplacement(String input) {
		String output = input.replaceAll(" ", "%20");
		return output;
	}
	
	private static String stringReplacement2(String input) {
		String output = null;
		
		return output;
	}
}
