import java.util.*;
public class roughWork {
	
	 private static Hashtable<Character, Integer> h1 = new Hashtable(); 

	/**
	 * @FINDING if given array is unique
	 */
	public static void main(String[] args) {
		//******************************** Array Unique O(n^2) *************************************
		int arr[] = {10,20,30,40,50};
		System.out.println("Is the array Unique??" + isUnique(arr));
		//******************************* String Duplicate removal **********************
		String str = "abad";
		System.out.println("New String after removing all duplicates is "+ strDuplicate(str));

		//***************** MATRIX Processing (set zeros to entire row and column where that row/column has zero entry****************//

		int[][] matrix = new int[3][4];
		matrix = matrixProcessing(matrix); 
		
		// ********** 1st Non Repeated character in STRING *********************
		String s = "TEETER";
		char answer = nonRepeatedCharacter(s);
		int res = fibinocci(7);

	}

	public static  int fibinocci(int i) {
		if(i == 0)
		return 0;
		
		if(i == 1)
			return 1;
		
		int sum = 0;
		for(int j = 1; j < i;j++){
			sum = j + sum;
		}
		return sum;
	}
	

	//*******************************************************************************************************
	public static boolean isUnique(int[] arr) {
		for(int i = 0;i<arr.length;i++)
			for(int j = i+1; j < arr.length;j++){
				if(arr[i] == arr[j]){
					return false;
				}
			}
		return true;
	}
	//*******************************************************************************************************
	public static String strDuplicate(String str){
		String newstr = "";
		boolean[] booleanArray = new boolean[256];
		int val = 0;

		for(int i = 0; i < str.length();i++){
			val = str.charAt(i);

			if(!(booleanArray[val]))
			{
				newstr = newstr + str.charAt(i); 
				booleanArray[val] = true;
			}
		}

		return newstr;
	}
	//*******************************************************************************************************
	public static int[][] matrixProcessing(int[][] matrix){

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		boolean[][] flagMatrix = new boolean[3][4];
		System.out.println("Enter the values for Matrix [3][4]");
		for(int a = 0; a < 3 ; a++){
			for(int b = 0; b < 4 ; b++){
				System.out.println("MATRIX["+a+"]"+"["+b+"] : ");
				matrix[a][b] = sc.nextInt();
			}
		}

		for(int c = 0; c < 3 ; c++){
			for(int d = 0; d < 4 ; d++){
				if(matrix[c][d]==0)
					flagMatrix[c][d] = true;
			}
		}
		
		return matrix;

	}
	//********************** to FIND NON REPEATED CHARACTER ****************************************************
	public static  char nonRepeatedCharacter(String s) {
		//use hashTable
		Integer intr = 0;
		Character chr;
		
		
		for(int i=0;i<s.length();i++){
			
			chr = new Character(s.charAt(i));
			if(h1.containsKey(chr)){
				h1.put(chr, intr+1);
			}
			else
				h1.put(chr, intr);
		}
//		System.out.println(h1);
		
		for(int j = 0; j < s.length();j++){
			chr = new Character(s.charAt(j));
			if(h1.get(chr).equals(0)){
				System.out.println(chr);
				return chr;
			}
		}
		System.out.println("No Unique characters in string");
		return 0;
	}
}
