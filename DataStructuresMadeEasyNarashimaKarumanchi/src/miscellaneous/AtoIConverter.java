package miscellaneous;

import java.util.Scanner;

/**
 * This class performs the AtoI Conversion functionality.
 * 
 * </p> It gets the String input from the user and converts it into Integer.
 * This class can work for both Positive and Negative numbers. Illegal argument
 * Exception will be thrown when given illegal input.
 * 
 * @author Vikram Suriyanarayanan
 * 
 */
public class AtoIConverter {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out
				.println("Enter the String input which needs to be converted: ");
		String input = scanner.next();
		scanner.close();
		System.out.println("Converted Output: " + atoi(input));
	}

	protected static int atoi(String input) {

		// Precondition check
		if (null == input || input.equalsIgnoreCase(""))
			throw new IllegalArgumentException();

		// Initialization
		int result = 0;
		boolean negative = false;

		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '-') {
				negative = true;
				i++;
			} else if (input.charAt(i) == '+') {
				i++;
			}
			result = result * 10 + input.charAt(i) - '0';
		}
		if (negative)
			result = result * -1;

		return result;
	}
}