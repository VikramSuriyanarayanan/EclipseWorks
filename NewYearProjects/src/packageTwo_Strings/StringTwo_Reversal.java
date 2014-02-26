package packageTwo_Strings;

import java.util.StringTokenizer;

public class StringTwo_Reversal {

	public static final String EXAMPLE_TEST = "This is my small example "
		      + "string which I'm going to " + "use for pattern matching.";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println(reversalOne("User Input"));
		//System.out.println(reversalTwo("User Input Two"));
		regexCheck();
	}

	private static String reversalOne(String str) {
		String reversedString = new String();

		for (int i = str.length() - 1; i >= 0; i--) {
			reversedString += str.charAt(i);
		}
		return reversedString;
	}

	private static StringBuffer reversalTwo(String input) {
		StringBuffer output = new StringBuffer();
		for (int i = input.length() - 1; i >= 0; i--) {
			output.append(input.charAt(i));
		}
		return output;
	}

	private static StringBuffer reversalModified(String input) {
		StringBuffer output = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(input,"");
		
		while(tokenizer.hasMoreTokens()) {
			output.append(tokenizer.nextToken());
		}
		return output;
	}
	
	private static void regexCheck() {
		String regex = "[abc][de]";

		System.out.println("ae".matches(regex));
		
	    System.out.println(EXAMPLE_TEST.matches("\\w.*"));
	    String[] splitString = (EXAMPLE_TEST.split(" "));
	    System.out.println(splitString.length);// should be 14
	    for (String string : splitString) {
	      System.out.println(string);
	    }
	    // replace all whitespace with tabs
	    System.out.println(EXAMPLE_TEST.replaceAll("\\s+", "\t"));
	}
}
