package packageOne_Arrays;

import java.util.Arrays;
import java.util.Random;

public class InsertionDeletion {
	private char[] data;

	// generates an array of random letters
	public InsertionDeletion(int size) {
		Random rand = new Random();
		data = new char[size];

		for (int i = 0; i < size; i++)
			data[i] = (char) ('a' + rand.nextInt(26));
	}

	// removes an item at the specified position
	public void delete(int pos) {
		// validation check
		if (pos >= 0 && pos < data.length) {
			char[] tmp = new char[data.length - 1];
			System.arraycopy(data, 0, tmp, 0, pos);
			System.arraycopy(data, pos + 1, tmp, pos, data.length - pos - 1);
			data = tmp;
		}
	}

	// removes an item at the specified position and returns the new array
	public char[] delete(char[] data, int pos) {
		if (pos >= 0 && pos < data.length) {
			char[] tmp = new char[data.length - 1];
			System.arraycopy(data, 0, tmp, 0, pos);
			System.arraycopy(data, pos + 1, tmp, pos, data.length - pos - 1);
			return tmp;
		} else
			return data;
	}

	// inserts an item at the specified position.
	public void insert(int pos, char x) {
		// validation check
		if (pos >= 0 && pos < data.length) {
			char[] tmp = new char[data.length + 1];
			System.arraycopy(data, 0, tmp, 0, pos);
			System.arraycopy(data, pos, tmp, pos + 1, data.length - pos);
			tmp[pos] = x;
			data = tmp;
		}
	}

	// string representation for this class
	public String toString() {
		return Arrays.toString(data);
	}

	public static void main(String args[]) {
		InsertionDeletion obj = new InsertionDeletion(10);

		// print this array
		System.out.println(obj);

		// remove element at position index
		System.out.println("\ndelete at index 5");
		obj.delete(5);

		System.out.println(obj);

		// remove element at fifth index
		System.out.println("\ninsert 'x' at index 5");
		obj.insert(5, 'x');
		System.out.println(obj);
	}
}
